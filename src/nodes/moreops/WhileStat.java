package nodes.moreops;

import nodes.ExprNode;
import nodes.statements.Body;
import tree.Visitor;
import tree.tables.Scoping;

public class WhileStat {
    private ExprNode expr;
    private Body body;
    private Scoping pointerToTable;
    private boolean firstVisit;

    //metodi costruttori
    public WhileStat(ExprNode expr, Body body) {
        this.expr = expr;
        this.body = body;
        this.firstVisit = true;
    }

    //metodi getter
    public ExprNode getExpr() {
        return expr;
    }
    public Body getBody() {
        return body;
    }
    public Scoping getPointerToTable() {
        return pointerToTable;
    }
    public void setPointerToTable(Scoping pointerToTable) {
        this.pointerToTable = pointerToTable;
    }
    public boolean isFirstVisit() {
        return firstVisit;
    }
    public void setFirstVisit(boolean firstVisit) {
        this.firstVisit = firstVisit;
    }

    @Override
    public String toString() {
        return "WhileStat{" +
                "expr=" + expr +
                ", body=" + body +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
