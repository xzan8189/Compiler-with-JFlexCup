package nodes.statements;

import nodes.leafs.IdLeaf;
import nodes.leafs.Type;
import tree.Visitor;
import tree.tables.Scoping;

import java.util.ArrayList;

public class Fun {
    private IdLeaf id;
    private ArrayList<ParDecl> paramDeclList;
    private Type typeLeaf;
    private Body body;
    private Scoping pointerToTable;
    private boolean firstVisit;

    //metodi costruttori
    public Fun(IdLeaf id, ArrayList<ParDecl> paramDeclList, Type typeLeaf, Body body) {
        this.id = id;
        this.paramDeclList = paramDeclList;
        this.typeLeaf = typeLeaf;
        this.body = body;
        this.firstVisit = true;
    }

    public Fun(IdLeaf id, ArrayList<ParDecl> paramDeclList, Body body) {
        this.id = id;
        this.paramDeclList = paramDeclList;
        this.typeLeaf = null;
        this.body = body;
        this.firstVisit = true;
    }

    //metodi getter
    public IdLeaf getId() {
        return id;
    }
    public ArrayList<ParDecl> getParamDeclList() {
        return paramDeclList;
    }
    public Type getTypeLeaf() {
        return typeLeaf;
    }
    public Body getBody() {
        return body;
    }
    public Scoping getPointerToTable() {
        return pointerToTable;
    }
    public void setPointerToTable(Scoping pointerToTable) {
        this.pointerToTable = pointerToTable;
    }
    public boolean isFirstVisit() {
        return firstVisit;
    }
    public void setFirstVisit(boolean firstVisit) {
        this.firstVisit = firstVisit;
    }

    @Override
    public String toString() {
        return "Fun{" +
                "id=" + id +
                ", paramDeclList=" + paramDeclList +
                ", typeLeaf=" + typeLeaf +
                ", body=" + body +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
