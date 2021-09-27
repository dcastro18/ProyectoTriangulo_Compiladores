/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.HTMLWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anner
 */
public class HTMLWriter {
    private String fileName;
    private FileWriter file;
    
    public HTMLWriter(String fileName) {
        this.fileName = (fileName.substring(0, fileName.length()-4))+".html";
    }
    
    public void createHTMLFile() throws IOException{
        file = new FileWriter(fileName);
        
        // HTML Header and style
        file.write("<!DOCTYPE html>\n"
                + "<html>\n"
                + "\t<head>\n"
                + "\t\t<style>\n"
                + "\t\t\tp{font-size: 1em; font-family: \"Courier New\", monospaced;}\n"
                + "\t\t\t.literal{color : #004080;}\n"
                + "\t\t\t.comment{color: #009933;}\n"
                + "\t\t\t.reservedword {font-weight:bold;}\n"
                + "\t\t</style>\n"
                + "\t<body>\n");
    }
    
    public void writeComment(String comment) throws IOException{
        file.write("<span class='comment'>"+comment+"</span><br></br>");
    }

    public void writeSpace(char currentChar) throws IOException {
        switch (currentChar) {
                case '\n':
                {
                    file.write("<br></br>");
                }
                break;
                case '\t':
                {
                    file.write("<span>"+"&nbsp;&nbsp;"+"</span>");
                }
                break;
                default:
                    file.write("<span>"+"&nbsp;"+"</span>");
                
            }
    }

    public void writeReservedWord(String spelling) throws IOException {
        file.write("<span class='reservedword'>"+spelling+"</span>");
    }

    public void writeLiteralWord(String spelling) throws IOException {
        file.write("<span class='literal'>"+spelling+"</span>");
    }

    public void writeNormalWord(String spelling) throws IOException {
        file.write("<span>"+spelling+"</span>");
    }

    public void closeHTMLFile() throws IOException {
        file.write("\t</body>\n"
                + "\t<head>\n");
    }
    
}
