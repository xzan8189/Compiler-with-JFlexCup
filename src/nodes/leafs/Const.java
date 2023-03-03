package nodes.leafs;

import nodes.ExprNode;
import tree.Visitor;

public class Const extends ExprNode {
    private String constType;
    private String value;

    //metodi costruttori
    public Const(String constType, String value) {
        super.type = constType;
        this.constType = constType;
        this.value = value;
    }

    //metodi getter
    public String getConstType() {
        return constType;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Const{" +
                "constType='" + constType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
