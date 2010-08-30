package com.bazaarvoice.jless;

import com.bazaarvoice.jless.ast.node.Node;
import com.bazaarvoice.jless.ast.node.ScopeNode;
import com.bazaarvoice.jless.ast.visitor.FlattenNestedRuleSets;
import com.bazaarvoice.jless.ast.visitor.Printer;
import com.bazaarvoice.jless.exception.LessTranslationException;
import com.bazaarvoice.jless.parser.Parser;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.commons.io.IOUtils;
import org.parboiled.Parboiled;
import org.parboiled.ParseRunner;
import org.parboiled.ReportingParseRunner;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.support.DefaultValueStack;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ValueStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LessProcessor is JLESS's parsing and translation engine that is used to convert input files that
 * use the <a href="http://lesscss.org/">LESS</a> styling framework syntax into standard CSS.
 * This implementation is based on the Ruby version of LESS by Alexis Sellier.
 *
 * At this time, the following LESS features are supported:
 * <ul>
 *   <li>Variables</li>
 *   <li>Mixins</li>
 *   <li>Mixin Arguments</li>
 *   <li>Nesting</li>
 * </ul>
 *
 * The following LESS features are not currently supported:
 * <ul>
 *   <li>Operations</li>
 *   <li>Accessors</li>
 *   <li>Imports</li>
 * </ul>
 *
 * This implementation does not attempt to generate the same output as the Ruby version.
 * Translation differences from LESS Ruby include:
 * <ul>
 *   <li>Equivalent adjacent rule sets are not grouped</li>
 *   <li>Empty rule sets are preserved</li>
 *   <li>Numbers and colors are not reformatted</li>
 * </ul>
 *
 * This list only notes changes in the <em>translation</em> stage. See {@link com.bazaarvoice.jless.parser.Parser} for details
 * on any changes to the <em>parsing</em> stage.
 *
 * @see com.bazaarvoice.jless.parser.Parser
 */
public class LessProcessor {

    // Controls whether only parsing or both parsing and translation are performed.
    private boolean _translationEnabled = true;

    // Controls whether a compressed version of the output is printed.
    // There is no performance penalty for enabling compression.
    private boolean _compressionEnabled = false;

    public boolean isTranslationEnabled() {
        return _translationEnabled;
    }

    public void setTranslationEnabled(boolean translationEnabled) {
        _translationEnabled = translationEnabled;
    }

    public boolean isCompressionEnabled() {
        return _compressionEnabled;
    }

    public void setCompressionEnabled(boolean compressionEnabled) {
        _compressionEnabled = compressionEnabled;
    }

    public Result process(InputStream input) throws IOException {
        return process(Collections.<Result>emptyList(), input);
    }

    public Result process(Result parent, InputStream input) throws IOException {
        return process(Collections.<Result>singletonList(parent), input);
    }

    /**
     * The {@link ScopeNode}s from any parent results are placed on the parser's {@link ValueStack} and joined
     * together to allow for variable and mixin resolution across scopes.
     * @return A printable {@link Result} of processing the given input.
     */
    public Result process(List<Result> parents, InputStream input) throws IOException {
        ValueStack<Node> stack = new DefaultValueStack<Node>();

        // Make the scope of each parent result accessible for variable and mixin resolution during parsing
        ScopeNode parentScope = null;
        for (Result parent : parents) {
            ScopeNode scope = parent.getScope();
            stack.push(scope);
            if (parentScope != null) {
                scope.setParentScope(parentScope);
            }
            parentScope = scope;
        }

        // Parse the input
        ParseRunner<Node> parseRunner = new ReportingParseRunner<Node>(Parboiled.createParser(Parser.class, _translationEnabled).Document(), stack);
        ParsingResult<Node> result = parseRunner.run(IOUtils.toString(input, "UTF-8"));

        if (result.hasErrors()) {
            throw new LessTranslationException("An error occurred while parsing a LESS input file:\n" +
                    ErrorUtils.printParseErrors(result));
        }

        // Retrieve the processed result
        ScopeNode scope = (ScopeNode) stack.pop();

        // Link the new scope to the last parent for later variable resolution
        scope.setParentScope(parentScope);

        return new Result(scope);
    }

    public Result process(List<InputStream> inputs) throws IOException {
        List<Result> results = new ArrayList<Result>();

        for (InputStream input : inputs) {
            results.add(process(results, input));
        }

        return results.get(results.size() - 1);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("You must specify at least one input file.");
            System.exit(1);
        }

        LessProcessor translator = new LessProcessor();

        List<File> files = new ArrayList<File>();
        for (String fileName : args) {
            files.add(new File(fileName));
        }

        try {
            List<InputStream> streams = new ArrayList<InputStream>();
            for (File file : files) {
                streams.add(new FileInputStream(file));
            }
            System.out.println(translator.process(streams));
        } catch (IOException e) {
            System.err.println("Unable to read input file.");
        }
    }
    
    public class Result {
        private final ScopeNode _scope;

        public Result(ScopeNode scope) {
            _scope = scope;
        }

        public ScopeNode getScope() {
            return _scope;
        }

        @Override
        public String toString() {
            return Suppliers.memoize(new Supplier<String>() {
                @Override
                public String get() {
                    // Perform additional translation steps if needed
                    if (_translationEnabled) {
                        _scope.traverse(new FlattenNestedRuleSets());
                    }

                    // Print the output nodes
                    Printer printer = new Printer(_compressionEnabled);
                    _scope.traverse(printer);
                    return printer.toString();
                }
            }).get();
        }
    }
}
