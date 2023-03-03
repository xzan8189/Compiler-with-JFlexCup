package nodes.statements;

import nodes.leafs.Type;
import tree.Visitor;

public class VarDecl {
    private Type type;
    private IdListInit idListInit;
    private IdListInitObbl idInitObblList;

    //metodi costruttori
    public VarDecl(Type type, IdListInit idListInit) {
        this.type = type;
        this.idListInit = idListInit;
    }

    public VarDecl(IdListInitObbl idInitObblList) {
        this.type = null; //aggiunto - VINCENZO
        this.idInitObblList = idInitObblList;
    }

    //metodi getter and setter
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public IdListInit getIdListInit() {
        return idListInit;
    }
    public IdListInitObbl getIdInitObblList() {
        return idInitObblList;
    }

    @Override
    public String toString() {
        return "VarDecl{" +
                "type=" + type +
                ", idListInit=" + idListInit +
                ", idInitObblList=" + idInitObblList +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
