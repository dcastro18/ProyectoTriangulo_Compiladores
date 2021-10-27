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
public class ElseCaseCommand extends Command {
    
    public ElseCaseCommand (Command cAST, SourcePosition thePosition) {
        super (thePosition);
        C = cAST;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitElseCaseCommand(this, o);
    }
    
    public Command C;
    
}
