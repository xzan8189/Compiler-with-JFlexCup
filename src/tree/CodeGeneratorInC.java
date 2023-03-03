package tree;

import nodes.ExprNode;
import nodes.leafs.Const;
import nodes.leafs.IdLeaf;
import nodes.leafs.Type;
import nodes.moreops.*;
import nodes.ops.BinaryOP;
import nodes.ops.UnaryOP;
import nodes.statements.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
* Ho migliorato dove c'è scritto "//MODIFICATO"
* E ci sono delle parti dove probabilmente possono essere modificate, le trovi con "//PROBLEMA"
*
* Queste modifiche servono per non far stampare troppi ';'.
*
* */

public class CodeGeneratorInC implements Visitor{
    private static File FILE;
    private static FileWriter WRITER;
    private static int numberTab;

    public CodeGeneratorInC(String pathfile) throws IOException {
        FILE = new File(pathfile);

        if(!FILE.exists())
            FILE.createNewFile();

        WRITER = new FileWriter(FILE);

        //inserimento import necessari per C
        WRITER.write("#include <stdio.h>\n");
        WRITER.write("#include <stdlib.h>\n");
        WRITER.write("#include <math.h>\n");
        WRITER.write("#include <stdbool.h>\n");
        WRITER.write("#include <string.h>\n\n");

        WRITER.write("//funzioni di supporto\n");
        WRITER.write("char * intToString(int n) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*16);\n");
        WRITER.write("\tsprintf(dest, \"%d\", n);\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        WRITER.write("char * doubleToString(double n) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*16);\n");
        WRITER.write("\tsprintf(dest, \"%lf\", n);\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        WRITER.write("char * boolToString(bool b) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*2);\n");
        WRITER.write("\tif(b == true)\n");
        WRITER.write("\t\tdest = \"true\";\n");
        WRITER.write("\telse\n");
        WRITER.write("\t\tdest = \"false\";\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        WRITER.write("char * strConcat(char * str1, char * str2) {\n");
        WRITER.write("\tchar * dest = malloc(sizeof(char)*256);\n");
        WRITER.write("\tstrcat(dest, str1);\n");
        WRITER.write("\tstrcat(dest, str2);\n");
        WRITER.write("\treturn dest;\n");
        WRITER.write("}\n\n");

        numberTab = 0;
    }

    @Override
    public Object visit(Const constLeaf) {
        try {
            if (!constLeaf.getConstType().equals("string"))
                WRITER.write(constLeaf.getValue());
            else
                WRITER.write("\"" + constLeaf.getValue() + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(IdLeaf idLeaf) {
        try{
            if(idLeaf.isOutParam())
                if(!(idLeaf.getTypeOf().equals("string")))
                    WRITER.write("* "); // sarebbe il passaggio per riferimento in C, così se modifichi la variabile dentro la funzione allora il valore risulterà modificato anche al di fuori della funzione
            WRITER.write(idLeaf.getId());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(Type typeLeaf) {
        try {
            String type = "";
            switch (typeLeaf.getType()) {
                case "integer":
                    type = "int";
                    break;
                case "real":
                    type = "double";
                    break;
                case "string":
                    type = "char *";
                    break;
                case "bool":
                    type = "bool";
                    break;
                default:
                    type = "void";
                    break;
            }
            WRITER.write(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(BinaryOP binaryOP) {
        try {
            ExprNode expr1 = binaryOP.getExpr1(), expr2 = binaryOP.getExpr2();
            String operation = binaryOP.getType();

            if (operation.equals("StrConcatOP")) { // Concatenazione stringhe
                WRITER.write("strConcat(");
                exprNodeToString(expr1);
                WRITER.write(", ");
                exprNodeToString(expr2);
                WRITER.write(")");
                return null;
            }

            if (operation.equals("PowOP")) { // Potenza
                WRITER.write("pow(");
                justCallVisitOnExprNode(expr1);
                WRITER.write(", ");
                justCallVisitOnExprNode(expr2);
                WRITER.write(")");
                return null;
            }

            if(expr1 instanceof IdLeaf && ((IdLeaf) expr1).getTypeOf().equals("string") ||
                    expr1 instanceof Const && ((Const) expr1).getConstType().equals("string")){
                WRITER.write("strcmp(");
                justCallVisitOnExprNode(expr1);
                WRITER.write(", ");
                justCallVisitOnExprNode(expr2);
                WRITER.write(")");

                if (operation.equals("EqOP"))
                    WRITER.write(" == ");
                else if (operation.equals("NeOP"))
                    WRITER.write(" != ");
                WRITER.write(" 0"); // sarebbe "strcmp("ciao", "ciao") == 0", se esce 0 allora significa che sono uguali le stringhe, invece se esce un numero diverso da 0 allora quel numero indicherà il numero di caratteri diversi

                return null;
            }

            justCallVisitOnExprNode(expr1);

            switch (operation) {
                case "PlusOP":
                    WRITER.write(" + ");
                    break;
                case "MinusOP":
                    WRITER.write(" - ");
                    break;
                case "TimesOP":
                    WRITER.write(" * ");
                    break;
                case "DivOP":
                case "DivIntOP":
                    WRITER.write(" / ");
                    break;
                case "AndOP":
                    WRITER.write(" && ");
                    break;
                case "OrOP":
                    WRITER.write(" || ");
                    break;
                case "LtOP":
                    WRITER.write(" < ");
                    break;
                case "LeOP":
                    WRITER.write(" <= ");
                    break;
                case "GtOP":
                    WRITER.write(" > ");
                    break;
                case "GeOP":
                    WRITER.write(" >= ");
                    break;
                case "EqOP":
                    WRITER.write(" == ");
                    break;
                case "NeOP":
                    WRITER.write(" != ");
                    break;
                default:
                    break;
            }

            justCallVisitOnExprNode(expr2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(UnaryOP unaryOP) {
        try {
            String type = unaryOP.getType();
            ExprNode expr = unaryOP.getExpr1();
            switch (type) {
                case "UMinusOP":
                    WRITER.write(" -");
                    justCallVisitOnExprNode(expr);
                    break;
                case "NotOP":
                    WRITER.write(" !");
                    justCallVisitOnExprNode(expr);
                    break;
                case "ParOP":
                    WRITER.write("(");
                    justCallVisitOnExprNode(expr);
                    WRITER.write(")");
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(AssignStat assignStatOP) {
        try {
            WRITER.write("\t".repeat(numberTab));

            //una stringa in C non può essere assegnata, può essere solo copiata
            if (assignStatOP.getId().getTypeOf().equals("string")) {
                WRITER.write("strcpy(");
                assignStatOP.getId().accept(this);
                WRITER.write(", ");
                ExprNode expr = assignStatOP.getExpr();
                justCallVisitOnExprNode(expr);
                WRITER.write(")");
                return null;
            }

            assignStatOP.getId().accept(this);

            WRITER.write(" = ");

            ExprNode expr = assignStatOP.getExpr();
            justCallVisitOnExprNode(expr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(CallFun callFunOP) {
        try {
            WRITER.write("\t".repeat(numberTab));
            WRITER.write(callFunOP.getId().getId() + "(");

            ArrayList<ExprNode> parametri = callFunOP.getList();
            if (parametri != null) {
                int i = 0;
                for (; i < parametri.size()-1; i++) {
                    ExprNode exprNode = parametri.get(i);
                    if (exprNode instanceof BinaryOP) {
                        ((BinaryOP) exprNode).accept(this);
                    } else if (exprNode instanceof UnaryOP) {
                        ((UnaryOP) exprNode).accept(this);
                    } else if (exprNode instanceof Const) {
                        ((Const) exprNode).accept(this);
                    } else if (exprNode instanceof IdLeaf) {
                        if (((IdLeaf) exprNode).isOutParam() && !((IdLeaf) exprNode).getTypeOf().equals("string"))
                            WRITER.write("&");
                        WRITER.write(((IdLeaf) exprNode).getId());
                    } else if (exprNode instanceof CallFun) {
                        ((CallFun) exprNode).accept(this);
                    }
                    WRITER.write(", ");
                }

                ExprNode expr = parametri.get(i);

                if (expr instanceof BinaryOP) {
                    ((BinaryOP) expr).accept(this);
                } else if (expr instanceof UnaryOP) {
                    ((UnaryOP) expr).accept(this);
                } else if (expr instanceof Const) {
                    ((Const) expr).accept(this);
                } else if (expr instanceof IdLeaf) {
                    if (((IdLeaf) expr).isOutParam() && !((IdLeaf) expr).getTypeOf().equals("string"))
                        WRITER.write("&");
                    WRITER.write(((IdLeaf) expr).getId());
                } else if (expr instanceof CallFun) {
                    ((CallFun) expr).accept(this);
                }
            }
            WRITER.write(")");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ElseStat elseStatOP) {
        return null;
    }

    @Override
    public Object visit(IfStat ifStatOP) {
        try {
            WRITER.write("\t".repeat(numberTab));
            WRITER.write("if(");

            ExprNode expr = ifStatOP.getExpr();
            justCallVisitOnExprNode(expr);
            WRITER.write("){\n");
            numberTab++;
            ifStatOP.getBody().accept(this);
            numberTab--;
            WRITER.write("\t".repeat(numberTab));
            WRITER.write("}\n");

            if (ifStatOP.getElseStat() != null) {
                WRITER.write("\t".repeat(numberTab));
                WRITER.write("else {\n");
                numberTab++;
                ifStatOP.getElseStat().getBody().accept(this);
                numberTab--;
                WRITER.write("\t".repeat(numberTab));
                WRITER.write("}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ReadStat readStatOP) {
        try {
            ExprNode expr = readStatOP.getExpr();
            if (expr != null) {
                WRITER.write("\t".repeat(numberTab));
                WRITER.write("printf(");
                justCallVisitOnExprNode(expr);
                WRITER.write(");\n");
            }

            ArrayList<IdLeaf> ids = readStatOP.getIdLeafList();
            for (IdLeaf item : ids) {
                if(item.getTypeOf().equals("integer") || item.getTypeOf().equals("bool")) {
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("scanf(\" %d\", &");
                    item.accept(this);
                } else if (item.getTypeOf().equals("real")) {
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("scanf(\" %lf\", &");
                    item.accept(this);
                } else if (item.getTypeOf().equals("string")) {
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("scanf(\" %s\", ");
                    item.accept(this);
                }
                WRITER.write(");\n");
                WRITER.write("\t".repeat(numberTab));
                WRITER.write("fflush(stdin)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(WhileStat whileStatOP) {
        try {
            WRITER.write("\t".repeat(numberTab));
            WRITER.write("while(");

            ExprNode expr = whileStatOP.getExpr();
            justCallVisitOnExprNode(expr);

            WRITER.write("){\n");
            numberTab++;
            whileStatOP.getBody().accept(this);
            numberTab--;
            WRITER.write("\t".repeat(numberTab));
            WRITER.write("}\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(WriteStat writeStatOP) {
        try {
            WRITER.write("\t".repeat(numberTab));
            WRITER.write("printf(\"%s\", ");
            ExprNode expr = writeStatOP.getExpr();
            justCallVisitOnExprNode(expr);

            //questa parte di codice serve per evitare la ridondanza dei ';'
            switch (writeStatOP.getType()) {
                case "WriteLn":
                    WRITER.write(");\n");
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("printf(\"\\n\")");
                    break;
                case "WriteB":
                    WRITER.write(");\n");
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("printf(\" \")");
                    break;
                case "WriteT":
                    WRITER.write(");\n");
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("printf(\"\\t\")");
                    break;
                default:
                    WRITER.write(")");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(Fun funOP) {
        try {
            if (funOP.getTypeLeaf() != null) { // si va a scrivere il tipo di ritorno della funzione sul prototipo
                switch (funOP.getTypeLeaf().getType()) {
                    case "real":
                        WRITER.write("double ");
                        break;
                    case "integer":
                        WRITER.write("int ");
                        break;
                    case "string":
                        WRITER.write("char *");
                        break;
                    case "bool":
                        WRITER.write("bool ");
                        break;
                    default:
                        break;
                }
            } else
                WRITER.write("void ");

            IdLeaf funID = funOP.getId();
            WRITER.write(funID.getId() + "(");

            ArrayList<ParDecl> parametri = funOP.getParamDeclList();
            if (parametri != null) {
                int i = 0;
                for (; i < parametri.size() - 1; i++) {
                    parametri.get(i).accept(this);
                    WRITER.write(", ");
                }
                parametri.get(i).accept(this);
            }
            WRITER.write("){\n");
            numberTab++;
            funOP.getBody().accept(this);
            numberTab--;
            WRITER.write("}\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(IdListInit idListInit) {
        try {
            ArrayList<IdLeaf> ids = idListInit.getIds();
            ArrayList<ExprNode> exprNodes = idListInit.getExprs();
            int i = 0;
            for (; i < ids.size() - 1; i++) {
                ids.get(i).accept(this);
                ExprNode expr = exprNodes.get(i);

                if (ids.get(i).getTypeOf().equals("string")) {
                    WRITER.write(" = malloc (sizeof(char) * 128);\n");

                    if (expr != null) {
                        WRITER.write("\t".repeat(numberTab));
                        WRITER.write("strcpy(");
                        ids.get(i).accept(this);
                        WRITER.write(", ");

                        justCallVisitOnExprNode(expr);
                        WRITER.write(");\n");
                    }
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("char * ");
                } else {
                    if (expr != null) {
                        WRITER.write(" = ");
                        justCallVisitOnExprNode(expr);
                    }
                    WRITER.write(", ");
                }
            }
            ids.get(i).accept(this);
            ExprNode expr = exprNodes.get(i);

            if (ids.get(i).getTypeOf().equals("string")) {
                WRITER.write(" = malloc (sizeof(char) * 128);\n");

                if (expr != null) {
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("strcpy(");
                    ids.get(i).accept(this);
                    WRITER.write(", ");
                    justCallVisitOnExprNode(expr);
                    WRITER.write(");\n");
                }
            } else {
                if (expr != null) {
                    WRITER.write(" = ");
                    justCallVisitOnExprNode(expr);
                }
                WRITER.write(";\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(IdListInitObbl idListInitObbl) {
        try {
            for (AssignStat item : idListInitObbl.getList()) {
                WRITER.write("\t".repeat(numberTab));
                switch (item.getId().getTypeOf()) {
                    case "string":
                        WRITER.write("char * ");
                        item.getId().accept(this);
                        WRITER.write(" = malloc (sizeof(char) * 128);\n");
                        break;
                    case "real":
                        WRITER.write("double ");
                        break;
                    case "integer":
                        WRITER.write("int ");
                        break;
                    case "bool":
                        WRITER.write("bool ");
                        break;
                    default:
                        break;
                }
                item.accept(this);
                WRITER.write(";\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object visit(Body bodyOP) {
        try {
            ArrayList<VarDecl> varDeclList = bodyOP.getVarDeclList();
            if (varDeclList != null) {
                for (VarDecl item : varDeclList) {
                    item.accept(this);
                }
            }
            WRITER.write("\n");
            ArrayList<Stat> statList = bodyOP.getStatList();
            if (statList != null) {
                for (Stat item : statList) {
                    item.accept(this);
                    //WRITER.write(";\n");
                    WRITER.write("\n"); //MODIFICATO
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ParDecl parDeclOP) {
        try {
            parDeclOP.getType().accept(this);
            WRITER.write(" ");
            parDeclOP.getId().accept(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(Program programOP) {
        try {
            WRITER.write("//dichiarazioni variabili globali\n");

            //gestione delle variabili globali (che in C sono costanti)
            if (programOP.getVarDeclList() != null) {
                for (VarDecl item : programOP.getVarDeclList()) {

                    //gestione dei casi di dichiarazione con var
                    if (item.getIdInitObblList() != null) {
                        ArrayList<AssignStat> assignStatList = item.getIdInitObblList().getList();
                        if (assignStatList != null) {
                            for (AssignStat assignStat : assignStatList) {
                                WRITER.write("\t".repeat(numberTab));
                                switch (assignStat.getId().getTypeOf()) {
                                    case "string":
                                        WRITER.write("char ");
                                        assignStat.getId().accept(this);
                                        WRITER.write("[128] = ");
                                        ExprNode expr = assignStat.getExpr();
                                        justCallVisitOnExprNode(expr);
                                        break;
                                    case "real":
                                        WRITER.write("double ");
                                        assignStat.accept(this);
                                        break;
                                    case "integer":
                                        WRITER.write("int ");
                                        assignStat.accept(this);
                                        break;
                                    case "bool":
                                        WRITER.write("bool ");
                                        assignStat.accept(this);
                                        break;
                                    default:
                                        break;
                                }
                                WRITER.write(";\n");
                            }
                        }
                        //gestione dei casi in cui si dichiarano col tipo
                    } else if (item.getIdListInit() != null) {
                        ArrayList<IdLeaf> idLeafList = item.getIdListInit().getIds();
                        ArrayList<ExprNode> exprList = item.getIdListInit().getExprs();

                        if (idLeafList != null) {
                            int i = 0;
                            for(; i < idLeafList.size(); i++) {
                                WRITER.write("\t".repeat(numberTab));
                                switch (idLeafList.get(i).getTypeOf()){
                                    case "string":
                                        WRITER.write("char ");
                                        WRITER.write(idLeafList.get(i).getId());
                                        WRITER.write("[128]");
                                        break;
                                    case "real":
                                        WRITER.write("double ");
                                        WRITER.write(idLeafList.get(i).getId());
                                        break;
                                    case "integer":
                                        WRITER.write("int ");
                                        WRITER.write(idLeafList.get(i).getId());
                                        break;
                                    case "bool":
                                        WRITER.write("bool ");
                                        WRITER.write(idLeafList.get(i).getId());
                                        break;
                                    default:
                                        break;
                                }
                                if (exprList.get(i) != null) {
                                    WRITER.write(" = ");
                                    ExprNode expr = exprList.get(i);
                                    justCallVisitOnExprNode(expr);
                                }
                                WRITER.write(";\n");
                            }
                        }
                    }
                }
            }

            WRITER.write("\n//dichiarazioni funzioni\n");
            if (programOP.getFunOpList() != null)
                for (Fun fun : programOP.getFunOpList())
                    fun.accept(this);

            WRITER.write("\n//main\n");
            WRITER.write("int main(){\n");
            numberTab++;
            programOP.getMain().accept(this);
            numberTab--;
            WRITER.write("}");

            WRITER.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(Stat statOP) {
        try {
            String typeStat = statOP.getTypeStat();
            switch (typeStat){
                case "IF":
                    ((IfStat) statOP.getNode()).accept(this);
                    break;
                case "WHILE":
                    ((WhileStat) statOP.getNode()).accept(this);
                    break;
                case "ReadStat":
                    ((ReadStat) statOP.getNode()).accept(this);
                    break;
                case "WriteStat":
                    ((WriteStat) statOP.getNode()).accept(this);
                    break;
                case "AssignStat":
                    ((AssignStat) statOP.getNode()).accept(this);
                    break;
                case "CallFun":
                    ((CallFun) statOP.getNode()).accept(this);
                    break;
                case "ExprStat":
                    WRITER.write("\t".repeat(numberTab));
                    WRITER.write("return ");

                    ExprNode expr = (ExprNode) statOP.getNode();
                    justCallVisitOnExprNode(expr);
                    break;
                default:
                    break;
            }
            WRITER.write(";\n"); //PROBLEMA
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(VarDecl varDeclOP) {
        try {
            IdListInit idListInit = varDeclOP.getIdListInit();
            IdListInitObbl idListInitObbl = varDeclOP.getIdInitObblList();

            if (idListInit != null) {
                WRITER.write("\t".repeat(numberTab));
                varDeclOP.getType().accept(this);
                WRITER.write(" ");
                idListInit.accept(this);
            } else if (idListInitObbl != null) {
                idListInitObbl.accept(this);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //metodi ausiliari
    private void exprNodeToString(ExprNode exprNode) throws IOException{
        if(exprNode instanceof UnaryOP){
            UnaryOP unaryOP = (UnaryOP) exprNode;
            String type = unaryOP.getExprType();
            if (type.equals("integer"))
                WRITER.write("intToString(");
            else if (type.equals("real"))
                WRITER.write("doubleToString(");
            else if (type.equals("bool"))
                WRITER.write("boolToString(");
            unaryOP.accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        } else if (exprNode instanceof BinaryOP){
            BinaryOP binaryOP = (BinaryOP) exprNode;
            String type = binaryOP.getExprType();
            if (type.equals("integer"))
                WRITER.write("intToString(");
            else if (type.equals("real"))
                WRITER.write("doubleToString(");
            else if (type.equals("bool"))
                WRITER.write("boolToString(");
            binaryOP.accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        } else if (exprNode instanceof CallFun){
            CallFun callFunOP = (CallFun) exprNode;
            if(callFunOP.getReturnType().equals("integer")){
                WRITER.write("intToString(");
            } else if(callFunOP.getReturnType().equals("real")){
                WRITER.write("doubleToString(");
            } else if(callFunOP.getReturnType().equals("bool")) {
                WRITER.write("boolToString(");
            }
            callFunOP.accept(this);
            if (!callFunOP.getReturnType().equals("string"))
                WRITER.write(")");
        } else if (exprNode instanceof IdLeaf){
            String type = ((IdLeaf) exprNode).getTypeOf();
            if (type.equals("integer"))
                WRITER.write("intToString(");
            else if (type.equals("real"))
                WRITER.write("doubleToString(");
            else if (type.equals("bool"))
                WRITER.write("boolToString(");
            ((IdLeaf) exprNode).accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        } else if (exprNode instanceof Const){
            Const constLeaf = (Const) exprNode;
            String type = constLeaf.getConstType();
            if (type.equals("integer"))
                WRITER.write("intToString(");
            else if (type.equals("real"))
                WRITER.write("doubleToString(");
            else if (type.equals("bool"))
                WRITER.write("boolToString(");
            constLeaf.accept(this);
            if (!type.equals("string"))
                WRITER.write(")");
        }
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
}
