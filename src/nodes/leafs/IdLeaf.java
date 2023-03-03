package nodes.leafs;

import nodes.ExprNode;
import tree.Visitor;

public class IdLeaf extends ExprNode {
    private String id;
    private boolean isOutParam;
    private String typeOf; // mai utilizzato, forse puoi toglierlo

    //metodi costruttori
    public IdLeaf(String id) {
        super.type = "IDLeaf";
        this.id = id;
        this.isOutParam = false;
    }

    public IdLeaf(String id, boolean isOutParam) {
        super.type = "IDLeaf";
        this.id = id;
        this.isOutParam = isOutParam;
    }

    //metodi getter and setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTypeOf() {
        return typeOf;
    }
    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }
    public boolean isOutParam() {
        return isOutParam;
    }
    public void setOutParam(boolean outParam) {
        isOutParam = outParam;
    }

    @Override
    public String toString() {
        return "IdLeaf{" +
                "id='" + id + '\'' +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
