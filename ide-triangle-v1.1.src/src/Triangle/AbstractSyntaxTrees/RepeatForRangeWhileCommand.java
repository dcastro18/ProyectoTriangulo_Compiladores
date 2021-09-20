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
public class RepeatForRangeWhileCommand extends Command {
    
    public RepeatForRangeWhileCommand (Declaration dAST, Expression e1AST, Expression e2AST, 
                                    Command cAST, SourcePosition thePosition) {
        super (thePosition);
        D = dAST;
        E1 = e1AST;
        E2 = e2AST;
        C = cAST;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitRepeatForRangeWhileCommand(this, o);
    }
    
    public Declaration D;
    public Expression E1, E2;
    public Command C;
    
}
