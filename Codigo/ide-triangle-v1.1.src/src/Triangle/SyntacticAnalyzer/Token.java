/*
 * @(#)Token.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;


public final class Token extends Object {

  public int kind;
  public String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if (kind == Token.IDENTIFIER) {
      int currentKind = firstReservedWord;
      boolean searching = true;

      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,

    // reserved words - must be in alphabetical order...
    ARRAY		= 4,
    CONST		= 5,
    DO			= 6,
    ELSE		= 7,
    END			= 8,
    FOR                 = 9, // nuevo
    FROM                = 10,
    FUNC		= 11,
    IF			= 12,
    IN			= 13,
    LET			= 14,
    LOCAL               = 15, // nuevo
    OF			= 16,
    PROC		= 17,
    RANGE               = 18, // nuevo
    RECORD		= 19,
    RECURSIVE           = 20, // nuevo
    REPEAT		= 21, // nuevo
    SELECT              = 22, // nuevo
    SKIP                = 23, // nuevo
    THEN		= 24,
    TO                  = 25, // nuevo
    TYPE                = 26,
    UNTIL               = 27, // nuevo 
    VAR                 = 28,
    WHEN                = 29, // nuevo
    WHILE		= 30,

    // punctuation...
    DOT			= 31,
    DOUBLEDOT           = 32, // nuevo
    COLON		= 33,
    SEMICOLON           = 34,
    COMMA		= 35,
    BECOMES		= 36,
    IS			= 37,
    PIPE                = 38, // nuevo

    // brackets...
    LPAREN		= 39,
    RPAREN		= 40,
    LBRACKET            = 41,
    RBRACKET            = 42,
    LCURLY		= 43,
    RCURLY		= 44,

    // special tokens...
    EOT			= 45,
    ERROR		= 46;

  public static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<operator>",
    "array",
    "const",
    "do",
    "else",
    "end",
    "for", // nuevo
    "from", // nuevo
    "func",
    "if",
    "in",
    "let",
    "local", // nuevo
    "of",
    "proc",
    "range", // nuevo
    "record", 
    "recursive", // nuevo
    "repeat", // nuevo
    "select", // nuevo
    "skip", // nuevo
    "then",
    "to", // nuevo
    "type",
    "until", // nuevo
    "var",
    "when", // nuevo
    "while",
    ".",
    "..", // nuevo
    ":",
    ";",
    ",",
    ":=",
    "~",
    "|", // nuevo
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "",
    "<error>"
  };

    public static final int firstReservedWord = Token.ARRAY;
    public static final int lastReservedWord = Token.WHILE;

}
