package com.bazaarvoice.jless.ast.visitor;

import com.bazaarvoice.jless.ast.node.ExpressionGroupNode;
import com.bazaarvoice.jless.ast.node.ExpressionNode;
import com.bazaarvoice.jless.ast.node.ExpressionPhraseNode;
import com.bazaarvoice.jless.ast.node.FunctionNode;
import com.bazaarvoice.jless.ast.node.LineBreakNode;
import com.bazaarvoice.jless.ast.node.MultipleLineCommentNode;
import com.bazaarvoice.jless.ast.node.Node;
import com.bazaarvoice.jless.ast.node.PropertyNode;
import com.bazaarvoice.jless.ast.node.RuleSetNode;
import com.bazaarvoice.jless.ast.node.ScopeNode;
import com.bazaarvoice.jless.ast.node.SelectorGroupNode;
import com.bazaarvoice.jless.ast.node.SelectorNode;
import com.bazaarvoice.jless.ast.node.SelectorSegmentNode;
import com.bazaarvoice.jless.ast.node.SimpleNode;
import com.bazaarvoice.jless.ast.node.SingleLineCommentNode;
import com.bazaarvoice.jless.ast.node.VariableDefinitionNode;
import com.bazaarvoice.jless.ast.node.VariableReferenceNode;

/**
 *
 */
public interface NodeTraversalVisitor {

    // Base visitor methods
    // These eliminate the need for methods that no visitor uses

    boolean enter(Node node);

    boolean visit(Node node);

    // Concrete visitor methods

    boolean enter(ExpressionGroupNode node);

    boolean visit(ExpressionGroupNode node);

    boolean visit(ExpressionNode node);

    boolean visit(ExpressionPhraseNode node);

    boolean enter(FunctionNode node);

    boolean visit(FunctionNode node);

    boolean visit(LineBreakNode node);

    boolean visit(MultipleLineCommentNode node);

    boolean enter(PropertyNode node);

    boolean visit(PropertyNode node);

    boolean enter(RuleSetNode node);

    boolean visit(RuleSetNode node);

    boolean enter(ScopeNode node);

    boolean visit(ScopeNode node);

    boolean enter(SelectorNode node);

    boolean visit(SelectorNode node);

    boolean enter(SelectorGroupNode node);

    boolean visit(SelectorGroupNode node);

    boolean visit(SelectorSegmentNode node);

    boolean visit(SimpleNode node);

    boolean visit(SingleLineCommentNode node);

    boolean enter(VariableDefinitionNode node);

    boolean visit(VariableDefinitionNode node);

    boolean visit(VariableReferenceNode node);
}