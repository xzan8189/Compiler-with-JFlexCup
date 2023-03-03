package nodes.statements;

import tree.Visitor;
import tree.tables.Scoping;

import java.util.ArrayList;

public class Program {
    private ArrayList<VarDecl> varDeclList;
    private ArrayList<Fun> funOpList;
    private Body main;
    private Scoping pointerToTable;
    private boolean firstVisit;

    //metodi costruttori
    public Program(ArrayList<VarDecl> varDeclList, ArrayList<Fun> funOpList, Body main) {
        this.varDeclList = varDeclList;
        this.funOpList = funOpList;
        this.main = main;
        this.firstVisit = true;
    }

    //metodi getter
    public ArrayList<VarDecl> getVarDeclList() {
        return varDeclList;
    }
    public ArrayList<Fun> getFunOpList() {
        return funOpList;
    }
    public Body getMain() {
        return main;
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
        return "Program{" +
                "varDeclList=" + varDeclList +
                ", funOpList=" + funOpList +
                ", main=" + main +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
