package nodes.statements;

import nodes.leafs.IdLeaf;
import nodes.leafs.Type;
import tree.Visitor;

public class ParDecl {
    private Type type;
    private IdLeaf id;
    private boolean isOut;

    //metodi costruttori
    public ParDecl(Type type, IdLeaf id) {
        this.type = type;
        this.id = id;
    }

    public ParDecl(boolean isOut, Type type, IdLeaf id) {
        this.isOut = isOut;
        this.type = type;
        this.id = id;
    }

    //metodi getter and setter
    public boolean isOut() {
        return isOut;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public IdLeaf getId() {
        return id;
    }
    public void setId(IdLeaf id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ParDecl{" +
                "type=" + type +
                ", id=" + id +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
