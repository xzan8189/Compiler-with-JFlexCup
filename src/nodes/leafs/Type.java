package nodes.leafs;

import tree.Visitor;

public class Type {
    private String type;

    //metodi costruttori
    public Type(String type) {
        this.type = type;
    }

    //metodi getter
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type{" +
                "type='" + type + '\'' +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
