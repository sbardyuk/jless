package com.bazaarvoice.jless.ast.visitor;

import com.bazaarvoice.jless.ast.ExpressionNode;
import com.bazaarvoice.jless.ast.ExpressionsNode;
import com.bazaarvoice.jless.ast.MultipleLineCommentNode;
import com.bazaarvoice.jless.ast.Node;
import com.bazaarvoice.jless.ast.PropertyNode;
import com.bazaarvoice.jless.ast.RuleSetNode;
import com.bazaarvoice.jless.ast.ScopeNode;
import com.bazaarvoice.jless.ast.SelectorGroupNode;
import com.bazaarvoice.jless.ast.SelectorNode;
import com.bazaarvoice.jless.ast.SelectorSegmentNode;
import com.bazaarvoice.jless.ast.SimpleNode;
import com.bazaarvoice.jless.ast.SingleLineCommentNode;

/**
 * Visitors default to visiting every node.
 */
public abstract class BaseNodeVisitor implements NodeVisitor {

    // Base visit methods
    // These eliminate the need for visit methods that no visitor uses

    @Override
    public boolean visitEnter(Node node) {
        return true;
    }

    @Override
    public boolean visit(Node node) {
        return true;
    }

    // Concrete visit methods

    @Override
    public boolean visit(ExpressionNode node) {
        return true;
    }

    @Override
    public boolean visit(ExpressionsNode node) {
        return true;
    }

    @Override
    public boolean visit(MultipleLineCommentNode node) {
        return true;
    }

    @Override
    public boolean visitEnter(PropertyNode node) {
        return true;
    }

    @Override
    public boolean visit(PropertyNode node) {
        return true;
    }

    @Override
    public boolean visitEnter(RuleSetNode node) {
        return true;
    }

    @Override
    public boolean visit(RuleSetNode node) {
        return true;
    }

    @Override
    public boolean visitEnter(ScopeNode node) {
        return true;
    }

    @Override
    public boolean visit(ScopeNode node) {
        return true;
    }

    @Override
    public boolean visit(SelectorNode node) {
        return true;
    }

    @Override
    public boolean visit(SelectorGroupNode node) {
        return true;
    }

    @Override
    public boolean visit(SelectorSegmentNode node) {
        return true;
    }

    @Override
    public boolean visit(SimpleNode node) {
        return true;
    }

    @Override
    public boolean visit(SingleLineCommentNode node) {
        return true;
    }
}