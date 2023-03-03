package nodes.moreops;

import nodes.ExprNode;
import nodes.statements.Body;
import tree.Visitor;
import tree.tables.Scoping;

public class IfStat {
    private ExprNode expr;
    private Body body;
    private ElseStat elseStat;
    private Scoping pointerToTable;
    private boolean firstVisit;

    //metodi costruttori
    public IfStat(ExprNode expr, Body body, ElseStat elseStat) {
        this.expr = expr;
        this.body = body;
        this.elseStat = elseStat;
        this.firstVisit = true;
    }

    //metodi getter
    public ExprNode getExpr() {
        return expr;
    }
    public Body getBody() {
        return body;
    }
    public ElseStat getElseStat() {
        return elseStat;
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
        return "IfStat{" +
                "expr=" + expr +
                ", body=" + body +
                ", elseStat=" + elseStat +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
