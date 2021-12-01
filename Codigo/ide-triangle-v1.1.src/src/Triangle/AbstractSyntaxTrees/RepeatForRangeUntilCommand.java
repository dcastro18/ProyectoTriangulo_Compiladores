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
public class RepeatForRangeUntilCommand extends Command {
    
    public RepeatForRangeUntilCommand (ForRangeIdentifierExpression dAST, Expression e1AST, Expression e2AST, 
                                        Command cAST, SourcePosition thePosition) {
        super (thePosition);
        D = dAST;
        E1 = dAST.E;
        E2 = e1AST;
        E3 = e2AST;
        C = cAST;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitRepeatForRangeUntilCommand(this, o);
    }
    
    public ForRangeIdentifierExpression D;
    public Expression E1, E2, E3;
    public Command C;
    
}
