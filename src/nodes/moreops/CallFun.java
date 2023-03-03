package nodes.moreops;

import nodes.ExprNode;
import nodes.leafs.IdLeaf;
import tree.Visitor;

import java.util.ArrayList;

public class CallFun extends ExprNode {
    private IdLeaf id;
    private ArrayList<ExprNode> list;
    private String returnType;

    //metodi costruttori
    public CallFun(IdLeaf id, ArrayList<ExprNode> list) {
        super.type = "CallFun";
        this.id = id;
        this.list = list;
    }

    //metodi getter and setter
    public IdLeaf getId() {
        return id;
    }
    public void setId(IdLeaf id) {
        this.id = id;
    }
    public ArrayList<ExprNode> getList() {
        return list;
    }
    public void setList(ArrayList<ExprNode> list) {
        this.list = list;
    }
    public String getReturnType() {
        return returnType;
    }
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "CallFun{" +
                "id=" + id +
                ", list=" + list +
                ", returnType='" + returnType + '\'' +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
