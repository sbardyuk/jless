package com.bazaarvoice.jless;

import com.bazaarvoice.jless.ast.Node;
import com.bazaarvoice.jless.ast.visitor.FlattenNestedRuleSets;
import com.bazaarvoice.jless.parser.Parser;
import org.parboiled.Parboiled;
import org.parboiled.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class LessTranslator {

    private static Parser _parser = Parboiled.createParser(Parser.class);

    public static ParsingResult<Node> parse(String input) {
        return ReportingParseRunner.run(_parser.Document(), input);
    }

    public static void translate(Node root) {
        root.accept(new FlattenNestedRuleSets());
    }
}
