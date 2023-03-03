package nodes.moreops;

import nodes.ExprNode;
import nodes.leafs.IdLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class ReadStat {
    private ArrayList<IdLeaf> idLeafList;
    private ExprNode expr;

    //metodi costruttori
    public ReadStat(ArrayList<IdLeaf> idLeafList, ExprNode expr) {
        this.idLeafList = idLeafList;
        this.expr = expr;
    }

    //metodi getter
    public ArrayList<IdLeaf> getIdLeafList() {
        return idLeafList;
    }
    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "ReadStat{" +
                "idLeafList=" + idLeafList +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
