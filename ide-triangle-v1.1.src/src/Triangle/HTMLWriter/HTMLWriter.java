/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.HTMLWriter;

import Triangle.SyntacticAnalyzer.SourceFile;
import Triangle.SyntacticAnalyzer.SourcePosition;
import Triangle.SyntacticAnalyzer.Token;
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
    private boolean currentlyScanningToken;
    private String comment = ""; 
    
    private SourceFile sourceFile;
    
    private char currentChar;
    private StringBuffer currentSpelling;
    
    public HTMLWriter(String fileName, SourceFile source) {
        this.fileName = (fileName.substring(0, fileName.length()-4))+".html";  
        sourceFile = source;
        currentChar = sourceFile.getSource();
        createHTMLFile();
    }
    
    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isDigit(char c) {
      return (c >= '0' && c <= '9');
    }
    
    private boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' ||
                c == '=' || c == '<' || c == '>' || c == '\\' ||
                c == '&' || c == '@' || c == '%' || c == '^' ||
                c == '?');
    }
    
    private void takeIt() {
    if (currentlyScanningToken){
      currentSpelling.append(currentChar);}
      currentChar = sourceFile.getSource();
    }
    
    private void scanSeparator() {
        
        switch (currentChar) {
        case '!':
          {
            comment+=currentChar;            //almacenamiento de comentario
            takeIt();
            while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT)){
              comment+=currentChar;              //MODIFICADO
              takeIt();
            }
            if (currentChar == SourceFile.EOL){
              comment+=currentChar;              //MODIFICADO
              takeIt();
            }
            writeComment(comment);        //escribir comentario
            comment = "";                        //MODIFICADO
          }
          break;

        case ' ': case '\n': case '\r': case '\t':
            {
                try {
                    writeSpace(currentChar);           //MODIFICADO
                } catch (IOException ex) {
                    Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
          takeIt();
          break;

        }
      }
    
private int scanToken() {

    switch (currentChar) {

    case 'a':  case 'b':  case 'c':  case 'd':  case 'e':
    case 'f':  case 'g':  case 'h':  case 'i':  case 'j':
    case 'k':  case 'l':  case 'm':  case 'n':  case 'o':
    case 'p':  case 'q':  case 'r':  case 's':  case 't':
    case 'u':  case 'v':  case 'w':  case 'x':  case 'y':
    case 'z':
    case 'A':  case 'B':  case 'C':  case 'D':  case 'E':
    case 'F':  case 'G':  case 'H':  case 'I':  case 'J':
    case 'K':  case 'L':  case 'M':  case 'N':  case 'O':
    case 'P':  case 'Q':  case 'R':  case 'S':  case 'T':
    case 'U':  case 'V':  case 'W':  case 'X':  case 'Y':
    case 'Z':
      takeIt();
      while (isLetter(currentChar) || isDigit(currentChar))
        takeIt();
      return Token.IDENTIFIER;

    case '0':  case '1':  case '2':  case '3':  case '4':
    case '5':  case '6':  case '7':  case '8':  case '9':
      takeIt();
      while (isDigit(currentChar))
        takeIt();
      return Token.INTLITERAL;

    case '+':  case '-':  case '*': case '/':  case '=':
    case '<':  case '>':  case '\\':  case '&':  case '@':
    case '%':  case '^':  case '?':
      takeIt();
      while (isOperator(currentChar))
        takeIt();
      return Token.OPERATOR;

    case '\'':
      takeIt();
      takeIt(); // the quoted character
      if (currentChar == '\'') {
      	takeIt();
        return Token.CHARLITERAL;
      } else
        return Token.ERROR;

    case '.':
      takeIt();
      return Token.DOT;

    case ':':
      takeIt();
      if (currentChar == '=') {
        takeIt();
        return Token.BECOMES;
      } else
        return Token.COLON;

    case ';':
      takeIt();
      return Token.SEMICOLON;

    case ',':
      takeIt();
      return Token.COMMA;

    case '~':
      takeIt();
      return Token.IS;

    case '(':
      takeIt();
      return Token.LPAREN;

    case ')':
      takeIt();
      return Token.RPAREN;

    case '[':
      takeIt();
      return Token.LBRACKET;

    case ']':
      takeIt();
      return Token.RBRACKET;

    case '{':
      takeIt();
      return Token.LCURLY;

    case '}':
      takeIt();
      return Token.RCURLY;

    case SourceFile.EOT:
      return Token.EOT;

    default:
      takeIt();
      return Token.ERROR;
    }
  }

    public boolean isReserved(String word){
      for(int currentRW = Token.firstReservedWord ; true ; currentRW++){
          int comparison = Token.tokenTable[currentRW].compareTo(word);
          if (comparison == 0) {
              return true;
          } else if (comparison > 0 || currentRW == Token.lastReservedWord) {
              return false;
          }
      }
    }
    
    /*
    private void scan() {
    writeComment("Si");
    
    switch (currentChar) {
        case 'a':  case 'b':  case 'c':  case 'd':  case 'e':
        case 'f':  case 'g':  case 'h':  case 'i':  case 'j':
        case 'k':  case 'l':  case 'm':  case 'n':  case 'o':
        case 'p':  case 'q':  case 'r':  case 's':  case 't':
        case 'u':  case 'v':  case 'w':  case 'x':  case 'y':
        case 'z':
        case 'A':  case 'B':  case 'C':  case 'D':  case 'E':
        case 'F':  case 'G':  case 'H':  case 'I':  case 'J':
        case 'K':  case 'L':  case 'M':  case 'N':  case 'O':
        case 'P':  case 'Q':  case 'R':  case 'S':  case 'T':
        case 'U':  case 'V':  case 'W':  case 'X':  case 'Y':
        case 'Z':
            
            writeReservedWord("Entro");
            takeIt();
            while (isLetter(currentChar) || isDigit(currentChar))
                takeIt();
            if (this.isReserved(this.currentSpelling.toString())){
                writeReservedWord(this.currentSpelling.toString());
            }else{
                writeNormalWord(this.currentSpelling.toString());
            }
            break;
        case '0':  case '1':  case '2':  case '3':  case '4':
        case '5':  case '6':  case '7':  case '8':  case '9':
            takeIt();
            while (isDigit(currentChar))
                takeIt();
            writeLiteralWord(this.currentSpelling.toString());
            break;
        case '+':  case '-':  case '*': case '/':  case '=':
        case '<':  case '>':  case '\\':  case '&':  case '@':
        case '%':  case '^':  case '?':
            takeIt();
            while (isOperator(currentChar))
                takeIt();
                writeNormalWord(this.currentSpelling.toString());
            break;
        case '\'':
            takeIt();
            takeIt(); // the quoted character'
            //PREGUNTA
            if (currentChar == '\'') {
                takeIt();
            writeLiteralWord(this.currentSpelling.toString());
            }else{
                System.out.println("Error while writing HTML file for print the AST");
            }
            break;
        case '.':
            takeIt();
            if(currentChar == '.'){
                takeIt();
            }
                writeNormalWord(this.currentSpelling.toString());
            break;
        case ':':
            takeIt();
            if (currentChar == '=') {
                takeIt();
            }
                writeNormalWord(this.currentSpelling.toString());
            break;
        case ',': case '~': case '|': case '$': case '(': 
        case ')': case '[': case ']': case '{': case '}': case ';':
            takeIt();
                writeNormalWord(this.currentSpelling.toString());
            break;  
        case ' ':
            takeIt();
            writeNormalWord("&nbsp;");
            break;
        case SourceFile.EOT:
            break;    
        case '!':{
            takeIt();
            while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT))
                takeIt();
                writeComment(this.currentSpelling.toString());
                break;
            }
        default:
        {
            try {
                writeSpace(currentChar);
            } catch (IOException ex) {
                Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  }
    */
  

   public Token scan () {
        Token tok;
        SourcePosition pos;
        int kind;
        
        currentlyScanningToken = false;
        while (currentChar == '!'
               || currentChar == ' '
               || currentChar == '\n'
               || currentChar == '\r'
               || currentChar == '\t')
        {   
          scanSeparator();
        }

        currentlyScanningToken = true;
        currentSpelling = new StringBuffer("");
        pos = new SourcePosition();
        pos.start = sourceFile.getCurrentLine();

        kind = scanToken();
        pos.finish = sourceFile.getCurrentLine();
        tok = new Token(kind, currentSpelling.toString(), pos);
        
        if(tok.kind>=4&&tok.kind<=30){
            System.out.println(tok.spelling);
            writeReservedWord(tok.spelling);       //Escribir palabra reservada
        }else if(tok.kind <=1){
            System.out.println(tok.spelling);
            writeLiteralWord(tok.spelling);         //escribir literal
        }else{
            System.out.println(tok.spelling);
            writeNormalWord(tok.spelling);           //escribir palabra normal
        }
    return tok;
  }

    
    /////////////////////////////////////////////////
    ///
    ///              HTML CREATION
    ///
    /////////////////////////////////////////////////
    public void createHTMLFile() {
        try {
            this.file = new FileWriter(fileName);
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
                    + "\t</head>\n"
                    + "\t<body>\n");
            
            //currentSpelling = new StringBuffer("");   
            scan();
            closeHTMLFile();
        } catch (IOException ex) {
            Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeComment(String comment) {
        try {
            file.write("<span class='comment'>"+comment+"</span><br></br>\n");
        } catch (IOException ex) {
            Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeSpace(char currentChar) throws IOException {
        switch (currentChar) {
                case '\n':
                {
                    file.write("<br></br>\n");
                }
                break;
                case '\t':
                {
                    file.write("<span>"+"&nbsp;&nbsp;"+"</span>\n");
                }
                break;
                default:
                    file.write("<span>"+"&nbsp;"+"</span>\n");
                
            }
    }

    public void writeReservedWord(String spelling)  {
        try {
            file.write("<span class='reservedword'>"+spelling+"</span>\n");
        } catch (IOException ex) {
            Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeLiteralWord(String spelling) {
        try {
            file.write("<span class='literal'>"+spelling+"</span>\n");
        } catch (IOException ex) {
            Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeNormalWord(String spelling)  {
        try {
            file.write("<span>"+spelling+"</span>\n");
        } catch (IOException ex) {
            Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeHTMLFile()  {
        try {
            file.write("\n\t</body>\n"
                    + "</html>\n");
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(HTMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
