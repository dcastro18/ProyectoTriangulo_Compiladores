/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.XMLWriter;

import Triangle.AbstractSyntaxTrees.Program;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Keila
 */
public class XMLWriterTree {
    
    private String fileName;
    
    public XMLWriterTree(String fileName) {
        this.fileName = fileName;
    }
    
    public void writer(Program ast) {
        //Preparar el archivo para escribir
        try {
            FileWriter fileWriter = new FileWriter(fileName+".xml");
            
            //Encabezado XML
            fileWriter.write("<?xml version=\"1.0\" standalone=\"yes\"?>\n");

            XMLWriterVisitor layout = new XMLWriterVisitor(fileWriter);
            ast.visit(layout, null);
            
            fileWriter.close();
        
        } catch (IOException e) {
            System.err.println("Error while creating file for print the AST");
            e.printStackTrace();
        }     
    }
    
}
