/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.HTMLWriter;

import Triangle.SyntacticAnalyzer.SourceFile;
import Triangle.SyntacticAnalyzer.Token;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author anner
 */
public class HTML {
    private SourceFile sourceFile;
  public boolean isDone,error = false;
  private FileWriter fileWriter;
  private String content = "";

  private char currentChar;
  private StringBuffer currentSpelling;
  
  public HTML(SourceFile source){
      sourceFile = source;
      currentChar = sourceFile.getSource();
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
  
  public void generateHTML(String fileName) {
      this.content+=("<!DOCTYPE html>\n" +
                        "<head>\n" +
                        "  \t<meta charset=\"utf-8\">\n" +
                        "  \t<title>FileHTML</title>\n" +
                        "</head>\n"+
                        "<style type=\"text/css\">\n"
               + "div {display: inline;" +
                        "      font-family: courier;" +
                        "      font-size:1em;" +
                        "    }"
               + "p {display: inline;" +
                        "      font-family: courier;" +
                        "      font-size:1em;" +
                        "    }" +
                        "\n" +
                        "</style>" +
                        "<body>");
      this.scan();
      this.content+=("</body>\n" +
                  "</html>");
      try {
          if (!this.error){
              fileWriter = new FileWriter(fileName + ".html");
              fileWriter.write(this.content);
              fileWriter.close();
          }
      }catch (IOException e) {
          System.err.println("Error while writing HTML file for print the AST");
          e.printStackTrace();
      }
  }

  private void takeIt() {
    currentSpelling.append(currentChar);
    currentChar = sourceFile.getSource();
  }

  private void scanToken() {

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
            if (this.isReserved(this.currentSpelling.toString())){
                writeLine(this.currentSpelling.toString(), HTML.BOLD);
            }else{
                writeLine(this.currentSpelling.toString(), HTML.SIMPLE);
            }
            break;
        case '0':  case '1':  case '2':  case '3':  case '4':
        case '5':  case '6':  case '7':  case '8':  case '9':
            takeIt();
            while (isDigit(currentChar))
                takeIt();
            writeLine(this.currentSpelling.toString(), HTML.BLUE);
            break;
        case '+':  case '-':  case '*': case '/':  case '=':
        case '<':  case '>':  case '\\':  case '&':  case '@':
        case '%':  case '^':  case '?':
            takeIt();
            while (isOperator(currentChar))
                takeIt();
            writeLine(this.currentSpelling.toString(), HTML.SIMPLE);
            break;
        case '\'':
            takeIt();
            takeIt(); // the quoted character'
            //PREGUNTA
            if (currentChar == '\'') {
                takeIt();
                writeLine(this.currentSpelling.toString(), HTML.BLUE);
            }else{
                this.error = true;
                System.out.println("Error while writing HTML file for print the AST");
            }
            break;
        case '.':
            takeIt();
            if(currentChar == '.'){
                takeIt();
            }
            writeLine(this.currentSpelling.toString(), HTML.SIMPLE);
            break;
        case ':':
            takeIt();
            if (currentChar == '=') {
                takeIt();
            }
            writeLine(this.currentSpelling.toString(), HTML.SIMPLE);
            break;
        case ',': case '~': case '|': case '$': case '(': 
        case ')': case '[': case ']': case '{': case '}': case ';':
            takeIt();
            writeLine(this.currentSpelling.toString(), HTML.SIMPLE);
            break;  
        case ' ':
            takeIt();
            writeLine("&nbsp;", HTML.SIMPLE);
            break;
        case SourceFile.EOT:
            this.isDone = true;
            break;    
        case '!':{
            takeIt();
            while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT))
                takeIt();
            writeLine(this.currentSpelling.toString(), HTML.GREEN);
            break;
        }  
        case '\n': case '\r': 
            takeIt();
            writeLine("<br>",HTML.EMPTY);
            break;
        case '\t':
            takeIt();
            writeLine("&emsp;",HTML.SIMPLE);
            break;
        default:
            takeIt();
            this.error = true;
            System.out.println("Error while writing HTML file for print the AST");
            break;
    }
  }
  
  public void scan(){
      while(!this.isDone && !this.error){
          currentSpelling = new StringBuffer("");
          this.scanToken();
      }
  }
  
  public void writeLine(String line, int kind){
      switch(kind){
          case HTML.SIMPLE: this.content+=("<p>"+line+"</p>");
          break;
          case HTML.BOLD: this.content+=("<strong>"+line+"</strong>");
          break;
          case HTML.GREEN: this.content+=("<span style = \"color:#00b300;\">"+line+"</span>");
          break;
          case HTML.BLUE: this.content+=("<span style = \"color:#0000cd;\">"+line+"</span>");
          break;
          case HTML.EMPTY: this.content+=(line);
          break;
      }
  }
  
  public static final int
    SIMPLE  = 0,BOLD    = 1,
    GREEN   = 2,BLUE    = 3,
    EMPTY   = 4;
}
