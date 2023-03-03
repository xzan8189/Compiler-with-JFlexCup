package tree;

import nodes.ExprNode;
import nodes.leafs.Const;
import nodes.leafs.IdLeaf;
import nodes.leafs.Type;
import nodes.moreops.*;
import nodes.ops.BinaryOP;
import nodes.ops.UnaryOP;
import nodes.statements.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XMLTreeGenerator implements Visitor{
    private Document doc;

    //metodo costruttore
    public XMLTreeGenerator() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        this.doc = docBuilder.newDocument();
    }

    public void saveInto(String filepath) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(this.doc), new StreamResult(new File(filepath)));
    }

    // metodi ausiliari della classe
    private Element exprNodeChecks(ExprNode expr) {
        Element e = null;
        if (expr instanceof BinaryOP) {
            e = (Element) ((BinaryOP) expr).accept(this);
        } else if (expr instanceof UnaryOP) {
            e = (Element) ((UnaryOP) expr).accept(this);
        } else if (expr instanceof Const) {
            e = (Element) ((Const) expr).accept(this);
        } else if (expr instanceof IdLeaf) {
            e = (Element) ((IdLeaf) expr).accept(this);
        } else if (expr instanceof CallFun) {
            e = (Element) ((CallFun) expr).accept(this);
        }

        return e;
    }

    // METODI INTERFACCIA
    @Override
    public Object visit(Const constLeaf) {
        Element constLeafElement = doc.createElement("Const");

        Element constLeafTypeElement = doc.createElement("ConstType");
        Element constLeafValueElement = doc.createElement("Value");

        constLeafTypeElement.appendChild(doc.createTextNode(constLeaf.getConstType()));
        constLeafValueElement.appendChild(doc.createTextNode(constLeaf.getValue()));
        constLeafElement.appendChild(constLeafTypeElement);
        constLeafElement.appendChild(constLeafValueElement);

        /*constLeafElement.appendChild(doc.createTextNode(constLeaf.getConstType()));
        constLeafElement.appendChild(doc.createTextNode(constLeaf.getValue()));*/

        return constLeafElement;
    }

    @Override
    public Object visit(IdLeaf idLeaf) {
        Element idLeafElement = doc.createElement("ID");

        Element e1 = doc.createElement(idLeaf.getId());

        idLeafElement.appendChild(e1);
        return idLeafElement;
    }

    @Override
    public Object visit(Type typeLeaf) {
        Element typeLeafElement = doc.createElement("Type");

        Element e1 = doc.createElement(typeLeaf.getType());

        typeLeafElement.appendChild(e1);
        return typeLeafElement;
    }

    @Override
    public Object visit(BinaryOP binaryOP) {
        Element binaryOPElement = doc.createElement(binaryOP.getType());
        ExprNode expr1 = binaryOP.getExpr1();
        ExprNode expr2 = binaryOP.getExpr2();

        Element e1 = exprNodeChecks(expr1), e2 = exprNodeChecks(expr2);

        binaryOPElement.appendChild(e1);
        binaryOPElement.appendChild(e2);
        return binaryOPElement;
    }

    @Override
    public Object visit(UnaryOP unaryOP) {
        Element unaryOPElement = doc.createElement(unaryOP.getType());
        ExprNode expr = unaryOP.getExpr1();

        Element e = exprNodeChecks(expr);

        unaryOPElement.appendChild(e);
        return unaryOPElement;
    }

    @Override
    public Object visit(AssignStat assignStatOP) {
        Element unaryOPElement = doc.createElement("AssignStatOP");
        IdLeaf idLeaf = assignStatOP.getId();
        ExprNode expr = assignStatOP.getExpr();

        Element e1 = (Element) idLeaf.accept(this);
        Element e2 = exprNodeChecks(expr);

        unaryOPElement.appendChild(e1);
        unaryOPElement.appendChild(e2);
        return unaryOPElement;
    }

    @Override
    public Object visit(CallFun callFunOP) {
        Element callFunOPElement = doc.createElement("CallFunOP");
        IdLeaf idLeaf = callFunOP.getId();
        ArrayList<ExprNode> exprNodeList = callFunOP.getList();

        Element e1 = (Element) idLeaf.accept(this);
        Element e2 = doc.createElement("ExprList");

        if (exprNodeList != null) {
            for (ExprNode expr : exprNodeList) {
                Element e_tmp = exprNodeChecks(expr);
                e2.appendChild(e_tmp);
            }
        }

        callFunOPElement.appendChild(e1);
        callFunOPElement.appendChild(e2);
        return callFunOPElement;
    }

    @Override
    public Object visit(ElseStat elseStatOP) {
        Element elseStatOPElement = doc.createElement("ElseOP");
        Body body = elseStatOP.getBody();

        Element e = (Element) body.accept(this);

        elseStatOPElement.appendChild(e);
        return elseStatOPElement;
    }

    @Override
    public Object visit(IfStat ifStatOP) {
        Element ifStatOPElement = doc.createElement("IfOP");
        ExprNode expr = ifStatOP.getExpr();
        Body body = ifStatOP.getBody();
        ElseStat elseStat = ifStatOP.getElseStat();

        Element e1 = exprNodeChecks(expr);
        Element e2 = (Element) body.accept(this);
        Element e3 = null;

        /* e3 */
        if (elseStat != null) {
            e3 = (Element) elseStat.accept(this);
        } else {
            e3 = doc.createElement("ElseOP"); // creo comunque il tag, ma senza niente dentro
        }

        ifStatOPElement.appendChild(e1);
        ifStatOPElement.appendChild(e2);
        ifStatOPElement.appendChild(e3);
        return ifStatOPElement;
    }

    @Override
    public Object visit(ReadStat readStatOP) { // PROBLEMA CHE POTRESTI CHIEDERE
        Element readStatOPElement = doc.createElement("ReadStatOP");
        ArrayList<IdLeaf> idLeafList = readStatOP.getIdLeafList();
        ExprNode expr = readStatOP.getExpr();

        Element e1 = doc.createElement("IDList");
        Element e2 = null;

        /* e1 */
        for (IdLeaf idLeaf : idLeafList) {
            Element e_tmp = (Element) idLeaf.accept(this);
            e1.appendChild(e_tmp);
        }

        /* e2 */
        if (expr != null) {
            e2 = exprNodeChecks(expr);
            readStatOPElement.appendChild(e2); // lo metto qui, perché se "expr" vale 'null' non posso appendere un elemento che è null. Quindi appendo l'elemento solo se è diverso da null, perciò metto l'istruzione qui.
        } else {
            /* non faccio nulla (guarda sempre il parser.cup cosa fa)
            * infatti guarda in "public Object visit(IfStat ifStatOP)"
            * in 'e3' ho messo comunque un tag perchè 'elseStat' può ritornare anche 'null'
            * dal parser.
            *  */
        }

        readStatOPElement.appendChild(e1);
        return readStatOPElement;
    }

    @Override
    public Object visit(WhileStat whileStatOP) {
        Element whileStatOPElement = doc.createElement("WhileOP");
        ExprNode expr = whileStatOP.getExpr();
        Body body = whileStatOP.getBody();

        Element e1 = exprNodeChecks(expr);
        Element e2 = (Element) body.accept(this);

        whileStatOPElement.appendChild(e1);
        whileStatOPElement.appendChild(e2);
        return whileStatOPElement;
    }

    @Override
    public Object visit(WriteStat writeStatOP) {
        Element writeStatOPElement = doc.createElement("WriteStatOP");
        String type = writeStatOP.getType();
        ExprNode expr = writeStatOP.getExpr();

        Element e1 = doc.createElement(type);
        Element e2 = exprNodeChecks(expr);

        writeStatOPElement.appendChild(e1);
        writeStatOPElement.appendChild(e2);
        return writeStatOPElement;
    }

    @Override
    public Object visit(Fun funOP) {
        Element funOPElement = doc.createElement("FunOP");
        IdLeaf nameFun = funOP.getId();
        ArrayList<ParDecl> parDeclList = funOP.getParamDeclList();
        Type typeReturnedByFun = funOP.getTypeLeaf();
        Body body = funOP.getBody();

        Element e1 = (Element) nameFun.accept(this);
        Element e2 = doc.createElement("ParDeclList");
        Element e3 = doc.createElement("returnType");
        Element e4 = (Element) body.accept(this);

        /* e2 - parametri funzione */
        if (parDeclList != null) {
            for (ParDecl item : parDeclList) {
                Element e_tmp = (Element) item.accept(this);
                e2.appendChild(e_tmp);
            }
        } else {
            e2.appendChild(doc.createElement("null"));
        }

        /* e3 */
        if (typeReturnedByFun != null) {
            e3.appendChild(doc.createElement(typeReturnedByFun.getType()));
        } else {
            e3.appendChild(doc.createElement("noTypeReturned"));
        }

        funOPElement.appendChild(e1);
        funOPElement.appendChild(e2);
        funOPElement.appendChild(e3);
        funOPElement.appendChild(e4);
        return funOPElement;
    }

    @Override
    public Object visit(IdListInit idListInit) {
        Element idListInitElement = doc.createElement("IDListInit");
        ArrayList<IdLeaf> idLeafList = idListInit.getIds();
        ArrayList<ExprNode> exprList = idListInit.getExprs();

        Element e1 = null;
        Element e2 = null;

        /* e1 */
        for (int i=0; i<idLeafList.size(); i++) {
            e1 = (Element) idLeafList.get(i).accept(this);
            idListInitElement.appendChild(e1);

            /* e2 */
            ExprNode expr = exprList.get(i);
            if (expr != null) {
               e2 = exprNodeChecks(expr);
               idListInitElement.appendChild(e2);
            }
        }

        return idListInitElement;
    }

    @Override
    public Object visit(IdListInitObbl idListInitObbl) {
        Element idListInitObblElement = doc.createElement("IDListInitObbl");
        ArrayList<AssignStat> list = idListInitObbl.getList();

        Element e = null;

        for (AssignStat item : list) {
            e = (Element) item.accept(this);
            idListInitObblElement.appendChild(e);
        }

        return idListInitObblElement;
    }

    @Override
    public Object visit(Body bodyOP) {
        Element bodyOPElement = doc.createElement("BodyOP");
        ArrayList<VarDecl> varDeclList = bodyOP.getVarDeclList();
        ArrayList<Stat> statList = bodyOP.getStatList();

        Element e1 = doc.createElement("VarDeclList");
        Element e2 = doc.createElement("StatList");

        /* e1 */
        if (varDeclList != null) {
            for (VarDecl item : varDeclList) {
                Element e_tmp = (Element) item.accept(this);
                e1.appendChild(e_tmp);
            }
        }

        /* e2 */
        if (statList != null) {
            for (Stat item : statList) {
                Element e_tmp = (Element) item.accept(this);
                e2.appendChild(e_tmp);
            }
        }

        bodyOPElement.appendChild(e1);
        bodyOPElement.appendChild(e2);
        return bodyOPElement;
    }

    @Override
    public Object visit(ParDecl parDeclOP) {
        Element parDeclOPElement = doc.createElement("ParDeclOP");
        Type type = parDeclOP.getType();
        IdLeaf id = parDeclOP.getId();
        boolean isOut = parDeclOP.isOut();

        Element e1 = (Element) type.accept(this);
        Element e2 = (Element) id.accept(this);
        Element e3 = null;

        /* e3 */
        if (isOut) {
            e3 = doc.createElement("out");
        } else {
            e3 = doc.createElement("in");
        }

        parDeclOPElement.appendChild(e3);
        parDeclOPElement.appendChild(e1);
        parDeclOPElement.appendChild(e2);
        return parDeclOPElement;
    }

    @Override
    public Object visit(Program programOP) {
        Element programOPElement = doc.createElement("ProgramOP");
        ArrayList<VarDecl> varDeclList = programOP.getVarDeclList();
        ArrayList<Fun> funList = programOP.getFunOpList();
        Body bodyMain = programOP.getMain(); //Il body di program sarebbe solo ed unicamente il Main

        Element e1 = doc.createElement("VarDeclList");
        Element e2 = doc.createElement("FunList");
        Element e3 = (Element) bodyMain.accept(this);

        /* e1 */
        if (varDeclList != null) {
            for (VarDecl item : varDeclList) {
                Element e_tmp = (Element) item.accept(this);
                e1.appendChild(e_tmp);
            }
        }

        /* e2 */
        if (funList != null) {
            for (Fun item : funList) {
                Element e_tmp = (Element) item.accept(this);
                e2.appendChild(e_tmp);
            }
        }

        programOPElement.appendChild(e1);
        programOPElement.appendChild(e2);
        programOPElement.appendChild(e3);

        this.doc.appendChild(programOPElement);

        return this.doc;
    }

    @Override
    public Object visit(Stat statOP) {
        Element statOPElement = doc.createElement("StatOP");
        String typeStat = statOP.getTypeStat();
        Object childNode = statOP.getNode();

        Element e_childNode = null;

        /* e_childNode */
        if (typeStat.equals("IF")){
            e_childNode = (Element) ((IfStat) childNode).accept(this);
        } else if (typeStat.equals("WHILE")){
            e_childNode = (Element) ((WhileStat) childNode).accept(this);
        } else if (typeStat.equals("ReadStat")){
            e_childNode = (Element) ((ReadStat) childNode).accept(this);
        } else if (typeStat.equals("WriteStat")){
            e_childNode = (Element) ((WriteStat) childNode).accept(this);
        } else if (typeStat.equals("AssignStat")){
            e_childNode = (Element) ((AssignStat) childNode).accept(this);
        } else if (typeStat.equals("CallFun")){
            e_childNode = (Element) ((CallFun) childNode).accept(this);
        } else if (typeStat.equals("ExprStat")) {
            statOPElement.appendChild(doc.createTextNode("ReturnOP"));
            ExprNode expr = (ExprNode) childNode;
            e_childNode = exprNodeChecks(expr);
        }

        statOPElement.appendChild(e_childNode);
        return statOPElement;
    }

    @Override
    public Object visit(VarDecl varDeclOP) {
        Element varDeclOPElement = doc.createElement("VarDeclOP");
        Type type = varDeclOP.getType();
        IdListInit idListInit = varDeclOP.getIdListInit();
        IdListInitObbl idListInitObbl = varDeclOP.getIdInitObblList();

        Element e1 = null;
        Element e2 = null;
        Element e3 = null;

        /* e1 - e2 */
        if (type != null) {
            e1 = (Element) type.accept(this);
            e2 = (Element) idListInit.accept(this);

            varDeclOPElement.appendChild(e1);
            varDeclOPElement.appendChild(e2);
        } else { /* e3 */
            e3 = (Element) idListInitObbl.accept(this);
            varDeclOPElement.appendChild(e3);
        }

        return varDeclOPElement;
    }
}
