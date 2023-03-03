package nodes.moreops;

import nodes.ExprNode;
import nodes.leafs.IdLeaf;
import tree.Visitor;
import tree.tables.Scoping;

public class AssignStat {
    private IdLeaf id;
    private ExprNode expr;
    private Scoping pointerToTable;

    //metodi costruttori
    public AssignStat(IdLeaf id, ExprNode expr) {
        this.id = id;
        this.expr = expr;
    }

    //metodi getter and setter
    public IdLeaf getId() {
        return id;
    }
    public ExprNode getExpr() {
        return expr;
    }
    public Scoping getPointerToTable() {
        return pointerToTable;
    }
    public void setPointerToTable(Scoping pointerToTable) {
        this.pointerToTable = pointerToTable;
    }

    @Override
    public String toString() {
        return "AssignStat{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
