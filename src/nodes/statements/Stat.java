package nodes.statements;

import nodes.ExprNode;
import nodes.moreops.*;
import tree.Visitor;

public class Stat {
    private String typeStat;
    private Object node;

    public Stat(IfStat op){
        this.typeStat = "IF";
        this.node = op;
    }

    public Stat(WhileStat op){
        this.typeStat = "WHILE";
        this.node = op;
    }

    public Stat(ReadStat op){
        this.typeStat = "ReadStat";
        this.node = op;
    }

    public Stat(WriteStat op){
        this.typeStat = "WriteStat";
        this.node = op;
    }

    public Stat(AssignStat op){
        this.typeStat = "AssignStat";
        this.node = op;
    }

    public Stat(CallFun op){
        this.typeStat = "CallFun";
        this.node = op;
    }

    public Stat(ExprNode op){
        this.typeStat = "ExprStat";
        this.node = op;
    }

    //metodi getter
    public String getTypeStat() {
        return typeStat;
    }
    public Object getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "typeStat='" + typeStat + '\'' +
                ", node=" + node +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
