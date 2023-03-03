package nodes.statements;

import nodes.ExprNode;
import nodes.leafs.IdLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class IdListInit {
    private ArrayList<IdLeaf> ids;
    private ArrayList<ExprNode> exprs;

    //metodi costruttori
    public IdListInit(IdLeaf id, ExprNode expr) {
        this.ids = new ArrayList<IdLeaf>();
        this.exprs = new ArrayList<ExprNode>();

        ids.add(id);
        exprs.add(expr);
    }

    public void addElement(IdLeaf id, ExprNode expr){
        ids.add(id);
        exprs.add(expr);
    }

    //metodi getter
    public ArrayList<IdLeaf> getIds() {
        return ids;
    }
    public ArrayList<ExprNode> getExprs() {
        return exprs;
    }

    @Override
    public String toString() {
        return "IdListInit{" +
                "ids=" + ids +
                ", exprs=" + exprs +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
