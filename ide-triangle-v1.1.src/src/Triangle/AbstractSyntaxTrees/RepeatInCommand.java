/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Keila
 */
public class RepeatInCommand extends Command {
 
    public RepeatInCommand (Identifier iAST, Expression eAST, Command cAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
        E = eAST;
        C = cAST;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitRepeatInCommand(this, o);
    }
    
    public Identifier I;
    public Expression E;
    public Command C;
    
    
}
