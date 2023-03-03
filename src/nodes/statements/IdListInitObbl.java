package nodes.statements;

import nodes.leafs.Const;
import nodes.leafs.IdLeaf;
import nodes.moreops.AssignStat;
import tree.Visitor;

import java.util.ArrayList;

// Lista di Assegnazioni
public class IdListInitObbl {
    private ArrayList<AssignStat> list;

    //metodi costruttori
    public IdListInitObbl(IdLeaf id, Const value) {
        this.list = new ArrayList<AssignStat>();
        this.list.add(new AssignStat(id, value));
    }

    public void addElement(IdLeaf id, Const value) {
        list.add(new AssignStat(id, value));
    }

    //metodi getter
    public ArrayList<AssignStat> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "IdListInitObbl{" +
                "list=" + list +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
