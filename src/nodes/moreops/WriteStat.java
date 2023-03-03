package nodes.moreops;

import nodes.ExprNode;
import tree.Visitor;

public class WriteStat {
    private String type;
    private ExprNode expr;

    //metodi costruttori
    public WriteStat(String type, ExprNode expr) {
        this.type = type;
        this.expr = expr;
    }

    //metodi getter
    public String getType() {
        return type;
    }
    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "WriteStat{" +
                "type='" + type + '\'' +
                ", expr=" + expr +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
