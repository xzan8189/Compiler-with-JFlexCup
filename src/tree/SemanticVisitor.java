package tree;

import nodes.ExprNode;
import nodes.leafs.Const;
import nodes.leafs.IdLeaf;
import nodes.leafs.Type;
import nodes.moreops.*;
import nodes.ops.BinaryOP;
import nodes.ops.UnaryOP;
import nodes.statements.*;
import org.w3c.dom.Element;
import tree.tables.EntryCategory;
import tree.tables.FunEntryCategory;
import tree.tables.ScopingTables;
import tree.tables.VarEntryCategory;

import java.util.ArrayList;

public class SemanticVisitor implements Visitor{

    // metodi ausiliari della classe
    private String getTypeExprNode(ExprNode expr) {
        String type = "";

        if (expr instanceof BinaryOP) {
            type = String.valueOf(((BinaryOP) expr).accept(this));
        } else if (expr instanceof UnaryOP) {
            type = String.valueOf(((UnaryOP) expr).accept(this));
        } else if (expr instanceof Const) {
            type = String.valueOf(((Const) expr).accept(this));
        } else if (expr instanceof IdLeaf) {
            type = String.valueOf(((IdLeaf) expr).accept(this));
        } else if (expr instanceof CallFun) {
            type = String.valueOf(((CallFun) expr).accept(this));
        }

        return type;
    }

    private void justCallVisitOnExprNode(ExprNode exprNode) {
        if (exprNode instanceof BinaryOP) {
            ((BinaryOP) exprNode).accept(this);
        } else if (exprNode instanceof UnaryOP) {
            ((UnaryOP) exprNode).accept(this);
        } else if (exprNode instanceof Const) {
            ((Const) exprNode).accept(this);
        } else if (exprNode instanceof IdLeaf) {
            ((IdLeaf) exprNode).accept(this);
        } else if (exprNode instanceof CallFun) {
            ((CallFun) exprNode).accept(this);
        }
    }

    // METODI INTERFACCIA
    @Override
    public Object visit(Const constLeaf) {
        return constLeaf.getConstType();
    }

    @Override
    public Object visit(IdLeaf id) {
        EntryCategory entryCategory = ScopingTables.lookUp(id.getId());

        if (entryCategory != null) { // Identificatore già definito precedentemente
            if (entryCategory instanceof VarEntryCategory) { // è una Variabile
                VarEntryCategory varEntry = (VarEntryCategory) entryCategory;
                id.setTypeOf(varEntry.getType());
                if (varEntry.isOut()) {
                    id.setOutParam(true);
                }
                return varEntry.getType();
            } else { // è una Funzione
                FunEntryCategory funEntry = (FunEntryCategory) entryCategory;
                return funEntry.getReturnedType();
            }
        } else {
            throw new Error("Identificatore " + id.getId() + " non definito precedentemente!");
        }
    }

    @Override
    public Object visit(Type typeLeaf) {
        return typeLeaf.getType();
    }

    @Override
    public Object visit(BinaryOP binaryOP) {
        ExprNode expr1 = binaryOP.getExpr1(), expr2 = binaryOP.getExpr2();
        String type1 = getTypeExprNode(expr1);
        String type2 = getTypeExprNode(expr2);
        String op = binaryOP.getType();

        if (op.equals("PlusOP") || op.equals("MinusOP") || op.equals("TimesOP") || op.equals("PowOP")) {
            if (type1.equals("integer") && type2.equals("integer")) {
                binaryOP.setExprType("integer");
                return "integer";
            } else if (type1.equals("real") && type2.equals("integer")) {
                binaryOP.setExprType("real");
                return "real";
            } else if (type1.equals("integer") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("real") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("DivOP")) { //ritorno sempre un "real" perché è una divisione normale
            if (type1.equals("integer") && type2.equals("integer")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("real") && type2.equals("integer")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("integer") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else if (type1.equals("real") && type2.equals("real")) {
                binaryOP.setExprType("real");
                return "real";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("DivIntOP")) { //ritorno sempre un "integer" perché è una divisione INTERA
            if (type1.equals("integer") && type2.equals("integer")) {
                binaryOP.setExprType("integer");
                return "integer";
            }else if (type1.equals("real") && type2.equals("integer")) {
                binaryOP.setExprType("integer");
                return "integer";
            }else if (type1.equals("integer") && type2.equals("real")) {
                binaryOP.setExprType("integer");
                return "integer";
            }else if (type1.equals("real") && type2.equals("real")) {
                binaryOP.setExprType("integer");
                return "integer";
            }else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("StrConcatOP")) {
            if (type1.equals("string")) {
                if (type2.equals("integer") || type2.equals("real") || type2.equals("bool") || type2.equals("string")) {
                    binaryOP.setExprType("string");
                    return "string";
                }
            } else if (type2.equals("string")) {
                if (type1.equals("integer") || type1.equals("real") || type1.equals("bool") || type1.equals("string")) {
                    binaryOP.setExprType("string");
                    return "string";
                }
            } else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("AndOP") || op.equals("OrOP")) {
            if (type1.equals("bool") && type2.equals("bool")) {
                binaryOP.setExprType("bool");
                return "bool";
            } else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("LtOP") || op.equals("LeOP") || op.equals("GtOP") || op.equals("GeOP")) {
            binaryOP.setExprType("bool");
            if (type1.equals("integer") && type2.equals("integer"))
                return "bool";
            else if (type1.equals("real") && type2.equals("integer"))
                return "bool";
            else if (type1.equals("integer") && type2.equals("real"))
                return "bool";
            else if (type1.equals("real") && type2.equals("real"))
                return "bool";
            else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        } else if (op.equals("EqOP") || op.equals("NeOP")) {
            binaryOP.setExprType("bool");
            if (type1.equals("integer") && type2.equals("integer"))
                return "bool";
            else if (type1.equals("real") && type2.equals("integer"))
                return "bool";
            else if (type1.equals("integer") && type2.equals("real"))
                return "bool";
            else if (type1.equals("real") && type2.equals("real"))
                return "bool";
            else if (type1.equals("string") && type2.equals("string"))
                return "bool";
            else if (type1.equals("bool") && type2.equals("bool"))
                return "bool";
            else
                throw new Error("Type error: " + type1 + " " + op + " " + type2);
        }

        return null;
    }

    @Override
    public Object visit(UnaryOP unaryOP) {
        ExprNode expr = unaryOP.getExpr1();
        String type = getTypeExprNode(expr);
        unaryOP.setExprType(type);

        if (unaryOP.getType().equals("UMinusOP")) {
            if (type.equals("integer"))
                return "integer";
            else if (type.equals("real"))
                return "real";
            else
                throw new Error("Type error: " + unaryOP.getType() + " " + type + " is not exist.");
        } else if (unaryOP.getType().equals("NotOP")) {
            if (type.equals("bool")) // è il caso in cui troviamo ad esempio "not true"
                return "bool";
            else
                throw new Error("Type error: " + unaryOP.getType() + " " + type + " is not exist.");
        } else if (unaryOP.getType().equals("ParOP")) {
            return type;
        }

        return null;
    }

    @Override
    public Object visit(AssignStat assignStatOP) {
        assignStatOP.setPointerToTable(ScopingTables.getScope());
        ExprNode expr = assignStatOP.getExpr();
        String type1 = String.valueOf(assignStatOP.getId().accept(this));
        String type2 = getTypeExprNode(expr);

        if (type1.equals(type2))
            return "notype";
        else
            throw new Error("Type error: " + type1 + " = " + type2 + " is not exist.");
    }

    @Override
    public Object visit(CallFun callFunOP) {
        callFunOP.getId().accept(this); // bisogna vedere se si sta chiamando una funzione che esiste
        ArrayList<ExprNode> exprNodeList = callFunOP.getList();

        if (exprNodeList != null) {
            for (ExprNode item : exprNodeList) {
                justCallVisitOnExprNode(item);
            }
        }

        IdLeaf idFun = callFunOP.getId();
        FunEntryCategory funEntryCategory = (FunEntryCategory) ScopingTables.lookUp(idFun.getId());
        callFunOP.setReturnType(funEntryCategory.getReturnedType());

        ArrayList<ExprNode> parameters = callFunOP.getList();
        ArrayList<String> parametersType = funEntryCategory.getParametersType();
        ArrayList<String> parametersMode = funEntryCategory.getParametersMode();

        int i = 0;
        if (parameters != null) {
            for (ExprNode item : parameters) {
                String paramType = "";
                String mode = "false";
                if (item instanceof BinaryOP) {
                    paramType = String.valueOf(((BinaryOP) item).accept(this));
                } else if (item instanceof UnaryOP) {
                    paramType = String.valueOf(((UnaryOP) item).accept(this));
                } else if (item instanceof Const) {
                    paramType = String.valueOf(((Const) item).accept(this));
                } else if (item instanceof IdLeaf) {
                    paramType = String.valueOf(((IdLeaf) item).accept(this));
                    if (((IdLeaf) item).isOutParam())
                        mode = "true";
                } else if (item instanceof CallFun) {
                    paramType = String.valueOf(((CallFun) item).accept(this));
                }

                //controllo se i parametri dati in input alla funzione combaciano con il prototipo della funzione,
                //cioè controllo se rispettano i tipi e l'out/in dei parametri specificati nel prototipo.
                if (!paramType.equals(parametersType.get(i)) || !mode.equals(parametersMode.get(i)))
                    throw new Error("Type error: metodo [" + funEntryCategory.getName() + "], parametri non sono compatibili con la dichiarazione della funzione");

                i++;
            }
        }
        return funEntryCategory.getReturnedType();
    }

    @Override
    public Object visit(ElseStat elseStatOP) {
        return elseStatOP.getBody().accept(this);
    }

    @Override
    public Object visit(IfStat ifStatOP) {
        ArrayList<String> bodyType = null;
        ArrayList<String> elseType = null;
        ExprNode expr = ifStatOP.getExpr();
        String condType = getTypeExprNode(expr);

        if (ifStatOP.isFirstVisit()) {
            ifStatOP.setFirstVisit(false);

            ScopingTables.newScope();
            bodyType = (ArrayList<String>) ifStatOP.getBody().accept(this);
            ifStatOP.setPointerToTable(ScopingTables.getScope());
            ScopingTables.removeScope();

            if (ifStatOP.getElseStat() != null) {
                ScopingTables.newScope();
                elseType = (ArrayList<String>) ifStatOP.getElseStat().accept(this);
                ifStatOP.getElseStat().setPointerToTable(ScopingTables.getScope());
                ScopingTables.removeScope();
            }
        }

        if (ifStatOP.getElseStat() == null) {
            if (condType.equals("bool"))
                return bodyType;
            else
                throw new Error("Type error: condType must be of 'bool' type, but is of " + condType + " type.");
        } else {
            if (condType.equals("bool")) {
                bodyType.addAll(elseType);
                return bodyType;
            } else {
                throw new Error("Type error: condType must be of 'bool' type, but is of " + condType + " type.");
            }
        }
    }

    @Override
    public Object visit(ReadStat readStatOP) {
        ArrayList<IdLeaf> idLeafList = readStatOP.getIdLeafList();
        ExprNode expr = readStatOP.getExpr();

        for (IdLeaf item : idLeafList)
            item.accept(this);

        String type = getTypeExprNode(expr);
        if (type.equals("string") || type.equals(""))
            return "notype";
        else
            throw new Error("Type error: ReadStat " + type + " not correct.");
    }

    @Override
    public Object visit(WhileStat whileStatOP) {
        ArrayList<String> bodyType = null;
        ExprNode expr = whileStatOP.getExpr();

        String conditionType = getTypeExprNode(expr);

        if (whileStatOP.isFirstVisit()) {
            ScopingTables.newScope();
            bodyType = (ArrayList<String>) whileStatOP.getBody().accept(this);
            whileStatOP.setPointerToTable(ScopingTables.getScope());
            ScopingTables.removeScope();
        }

        if (conditionType.equals("bool"))
            return bodyType;
        else
            throw new Error("Type error: condType must be of 'bool' type, but is of " + conditionType + " type.");
    }

    @Override
    public Object visit(WriteStat writeStatOP) {
        ExprNode expr = writeStatOP.getExpr();
        String type = getTypeExprNode(expr);

        if (type.equals("string"))
            return "notype";
        else
            throw new Error("Type error: WriteStatOP needs a 'string' expr, but the expr is of '" + type + "' type.");
    }

    @Override
    public Object visit(Fun funOP) {
        IdLeaf funID = funOP.getId();
        String nameFun = funID.getId();
        ArrayList<String> paramsName = new ArrayList<>();
        ArrayList<String> paramsMode = new ArrayList<>();
        ArrayList<String> paramsType = new ArrayList<>();
        String returnType = "void";

        Type funTypeLeaf = funOP.getTypeLeaf();
        if (funTypeLeaf != null) // se ha un tipo di ritorno la funzione allora lo prendo
            returnType = funTypeLeaf.getType();

        ArrayList<ParDecl> paramDeclList = funOP.getParamDeclList();
        if (paramDeclList != null) {
            for (ParDecl item : paramDeclList) {
                paramsName.add(item.getId().getId());
                paramsMode.add(String.valueOf(item.isOut()));
                paramsType.add(item.getType().getType());
                item.getId().setTypeOf(item.getType().getType());
            }
        }
        FunEntryCategory funEntryCategory = new FunEntryCategory(nameFun, paramsName, paramsMode, paramsType, returnType);
        ScopingTables.addEntryCategory(funEntryCategory);

        if (funOP.isFirstVisit()) {
            funOP.setFirstVisit(false);
            ScopingTables.newScope();
            funOP.setPointerToTable(ScopingTables.getScope());

            for (int i = 0; i< paramsName.size(); i++) {
                if (paramsMode.get(i).equals("true"))
                    ScopingTables.addEntryCategory(new VarEntryCategory(paramsName.get(i), paramsType.get(i), true));
                else
                    ScopingTables.addEntryCategory(new VarEntryCategory(paramsName.get(i), paramsType.get(i), false));
            }

            ArrayList<String> returnBody = (ArrayList<String>) funOP.getBody().accept(this);

            for (String item : returnBody) {
                if (!returnType.equals(item) && !returnType.equals("void"))
                    throw new Error("Type error: [" + funOP.getId().getId() + "] method returns {" + returnBody + "}, but it should returns " + returnType);
                else if (returnType.equals("void")) {
                    if (!returnType.equals(item))
                        throw new Error("Type error: [" + funOP.getId().getId() + "] method returns {" + returnBody + "}, but it should returns " + returnType);
                }
            }
            ScopingTables.removeScope();
        }

        return "notype";
    }

    @Override
    public Object visit(IdListInit idListInit) { return null; }

    @Override
    public Object visit(IdListInitObbl idListInitObbl) { return null; }

    @Override
    public Object visit(Body bodyOP) {
        bodyOP.setPointerToTable(ScopingTables.getScope());
        ArrayList<VarDecl> varDeclList = bodyOP.getVarDeclList();

        if (varDeclList != null)
            for (VarDecl item : varDeclList)
                item.accept(this);

        ArrayList<String> returnsType = new ArrayList<String>();
        ArrayList<Stat> statList = bodyOP.getStatList();

        if (statList != null) {
            for (Stat item : statList) {
                //questo controllo serve per non aggiungere il tipo di ritorno di una chiamata a funzione
                if (!item.getTypeStat().equals("CallFun")) {
                    Object obj = item.accept(this);
                    if (obj instanceof ArrayList) {
                        returnsType.addAll((ArrayList<String>) obj);
                    } else if (!String.valueOf(obj).equals("notype")) {
                        returnsType.add(String.valueOf(obj));
                    }
                } else
                    item.accept(this);
            }
        }

        return returnsType;
    }

    @Override
    public Object visit(ParDecl parDeclOP) { return null; }

    @Override
    public Object visit(Program programOP) {
        if (programOP.isFirstVisit()) {
            programOP.setFirstVisit(false);
            ScopingTables.newScope();
            programOP.setPointerToTable(ScopingTables.getScope());

            ArrayList<VarDecl> varDeclList = programOP.getVarDeclList();
            ArrayList<Fun> funList = programOP.getFunOpList();

            if (varDeclList != null)
                for (VarDecl item : varDeclList)
                    item.accept(this);

            if (funList != null)
                for (Fun item : funList)
                    item.accept(this);

            ScopingTables.newScope();

            ArrayList<String> returnsType = (ArrayList<String>) programOP.getMain().accept(this);

            if (returnsType.size() != 0) {
                for (String item : returnsType) {
                    if (!item.equals("void"))
                        throw new Error("Main function cannot returns a value!");
                }
            }

            ScopingTables.removeScope();
            ScopingTables.removeScope();
        }

        return "notype";
    }

    @Override
    public Object visit(Stat statOP) {
        String typeStat = statOP.getTypeStat();

        if (typeStat.equals("IF")){
            return ((IfStat) statOP.getNode()).accept(this);
        } else if (typeStat.equals("WHILE")){
            return ((WhileStat) statOP.getNode()).accept(this);
        } else if (typeStat.equals("ReadStat")){
            return ((ReadStat) statOP.getNode()).accept(this);
        } else if (typeStat.equals("WriteStat")){
            return ((WriteStat) statOP.getNode()).accept(this);
        } else if (typeStat.equals("AssignStat")){
            return ((AssignStat) statOP.getNode()).accept(this);
        } else if (typeStat.equals("CallFun")){
            return ((CallFun) statOP.getNode()).accept(this);
        } else if (typeStat.equals("ExprStat")) {//return statement
            ExprNode exprNode = (ExprNode) statOP.getNode();
            if(exprNode instanceof BinaryOP){
                return ((BinaryOP) exprNode).accept(this);
            } else if (exprNode instanceof UnaryOP){
                return ((UnaryOP) exprNode).accept(this);
            } else if (exprNode instanceof Const){
                return ((Const) exprNode).accept(this);
            } else if (exprNode instanceof IdLeaf){
                return ((IdLeaf) exprNode).accept(this);
            } else if (exprNode instanceof CallFun){
                return ((CallFun) exprNode).accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visit(VarDecl varDeclOP) {
        IdListInit idListInit = varDeclOP.getIdListInit();
        IdListInitObbl idListInitObbl = varDeclOP.getIdInitObblList();

        //controlli per scoping
        if (idListInit != null) {
            ArrayList<IdLeaf> ids = idListInit.getIds();
            for (IdLeaf item : ids) {
                Type varType = varDeclOP.getType();
                ScopingTables.addEntryCategory(new VarEntryCategory(item.getId(), varType.getType(), false));
            }
        } else if (idListInitObbl != null) {
            ArrayList<AssignStat> assignStatList = idListInitObbl.getList();
            for (AssignStat item : assignStatList) {
                item.getId().setTypeOf(item.getExpr().type);
                IdLeaf assignStatIdLeaf = item.getId();
                ScopingTables.addEntryCategory(new VarEntryCategory(assignStatIdLeaf.getId(), item.getExpr().type, false));
            }
        }

        //controlli per il type-checking
        String type = "";
        if (idListInit != null) {
            ArrayList<IdLeaf> ids = idListInit.getIds();
            ArrayList<ExprNode> exprList = idListInit.getExprs();

            for (int i=0; i<ids.size(); i++) {
                type = String.valueOf(ids.get(i).accept(this));
                Type varType = varDeclOP.getType();

                if (!varType.getType().equals(type))
                    throw new Error("Type error: " + varType.getType() + " " + ids.get(i).getId() + " = " + type);

                ExprNode expr = exprList.get(i);
                if (expr != null) {
                    String exprType = getTypeExprNode(expr);
                    if (!exprType.equals(varType.getType()))
                        throw new Error("Type error: '" + ids.get(i).getId() + "' variable is of " + varType.getType() + " type, but it should be of " + exprType + " type.");
                }

            }
        } else if (idListInitObbl != null) {
            ArrayList<AssignStat> assignStatList = idListInitObbl.getList();
            for (AssignStat item : assignStatList) {
                type = String.valueOf(item.accept(this));
                IdLeaf assignStatId = item.getId();
                if (!type.equals("notype"))
                    throw new Error("Type error: the assignStat must not have a type: [" + assignStatId.getId() + " = " + type + "]");
            }
        }

        return "notype";
    }
}
