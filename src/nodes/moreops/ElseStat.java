package nodes.moreops;

import nodes.statements.Body;
import tree.Visitor;
import tree.tables.Scoping;

public class ElseStat {
    private Body body;
    private Scoping pointerToTable;

    //metodi costruttori
    public ElseStat(Body body) {
        this.body = body;
    }

    //metodi getter
    public Body getBody() {
        return body;
    }
    public Scoping getPointerToTable() {
        return pointerToTable;
    }
    public void setPointerToTable(Scoping pointerToTable) {
        this.pointerToTable = pointerToTable;
    }

    @Override
    public String toString() {
        return "ElseStat{" +
                "body=" + body +
                '}';
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
