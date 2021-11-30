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
public class RepeatForRangeDoCommand extends Command{
    
    public RepeatForRangeDoCommand (ForRangeIdentifierExpression dAST, Expression eAST, 
            Command cAST, SourcePosition thePosition) {
        super (thePosition);
        D = dAST;
        E1 = dAST.E ;
        E2 = eAST ;
        C = cAST;
               
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitRepeatForRangeDoCommand(this, o);
    }
    
    public ForRangeIdentifierExpression D;
    public Expression E1;
    public Expression E2;
    public Command C;
    
}
