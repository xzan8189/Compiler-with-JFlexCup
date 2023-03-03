package nodes.ops;

import nodes.ExprNode;
import tree.Visitor;

public class BinaryOP extends ExprNode {
    private String type;
    private ExprNode expr1, expr2;
    private String exprType;

    //metodi costruttori
    public BinaryOP(String type, ExprNode expr1, ExprNode expr2) {
        super.type = "BinaryOP";
        this.type = type;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    //metodi getter
    public String getType() {
        return type;
    }
    public ExprNode getExpr1() {
        return expr1;
    }
    public ExprNode getExpr2() {
        return expr2;
    }
    public String getExprType() {
        return exprType;
    }
    public void setExprType(String exprType) {
        this.exprType = exprType;
    }

    @Override
    public String toString() {
        return "BinaryOP{" +
                "type='" + type + '\'' +
                ", expr1=" + expr1 +
                ", expr2=" + expr2 +
                ", exprType='" + exprType + '\'' +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
