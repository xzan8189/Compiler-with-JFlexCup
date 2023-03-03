import java_cup.runtime.Symbol;
import nodes.statements.Program;
import tree.CodeGeneratorInC;
import tree.SemanticVisitor;
import tree.XMLTreeGenerator;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Tester {
    public static void main(String[] args) throws Exception {
        System.out.println("--- COMPILAZIONE PARTITA ---");

        String filepath = args[0];
        String filename = new File(args[0]).getName().replaceAll(".txt",".c");

        FileReader file = new FileReader(filepath);
        parser p = new parser(new Lexer(file));


        XMLTreeGenerator xml = new XMLTreeGenerator();
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        CodeGeneratorInC codeGenerator = new CodeGeneratorInC("test_files" + File.separator + "c_out" + File.separator + filename);

        Program programOP = (Program) p.parse().value;

        programOP.accept(xml);
        xml.saveInto("test_files" + File.separator + "AST" + File.separator + filename.replaceAll(".c",".xml"));
        programOP.accept(semanticVisitor); /* sono collegati ovviamente  */
        programOP.accept(codeGenerator);

        //istruzione system dependent
        //Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"gcc -lm " + "test_files" + File.separator + "c_out" + File.separator + filename + " \"");
    }
}

// End of file
