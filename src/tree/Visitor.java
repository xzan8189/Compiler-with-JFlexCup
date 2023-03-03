package tree;

import nodes.leafs.Const;
import nodes.leafs.IdLeaf;
import nodes.leafs.Type;
import nodes.moreops.*;
import nodes.ops.BinaryOP;
import nodes.ops.UnaryOP;
import nodes.statements.*;

public interface Visitor {

    //leafs
    Object visit(Const constLeaf);
    Object visit(IdLeaf idLeaf);
    Object visit(Type typeLeaf);

    //ops
    Object visit(BinaryOP binaryOP);
    Object visit(UnaryOP unaryOP);

    //moreops
    Object visit(AssignStat assignStatOP);
    Object visit(CallFun callFunOP);
    Object visit(ElseStat elseStatOP);
    Object visit(IfStat ifStatOP);
    Object visit(ReadStat readStatOP);
    Object visit(WhileStat whileStatOP);
    Object visit(WriteStat writeStatOP);

    //statements
    Object visit(Fun funOP);
    Object visit(IdListInit idListInit);
    Object visit(IdListInitObbl idListInitObbl);
    Object visit(Body bodyOP);
    Object visit(ParDecl parDeclOP);
    Object visit(Program programOP);
    Object visit(Stat statOP);
    Object visit(VarDecl varDeclOP);

}
