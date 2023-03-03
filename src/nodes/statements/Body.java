package nodes.statements;

import tree.Visitor;
import tree.tables.Scoping;

import java.util.ArrayList;

public class Body {
    private ArrayList<VarDecl> varDeclList;
    private ArrayList<Stat> statList;
    private Scoping pointerToTable;
    private boolean firstVisit;

    //metodi costruttori
    public Body(ArrayList<VarDecl> varDeclList, ArrayList<Stat> statList) {
        this.varDeclList = varDeclList;
        this.statList = statList;
        this.firstVisit = true;
    }

    //metodi getter and setter
    public ArrayList<VarDecl> getVarDeclList() {
        return varDeclList;
    }
    public void setVarDeclList(ArrayList<VarDecl> varDeclList) {
        this.varDeclList = varDeclList;
    }
    public ArrayList<Stat> getStatList() {
        return statList;
    }
    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
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
        return "Body{" +
                "varDeclList=" + varDeclList +
                ", statList=" + statList +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
