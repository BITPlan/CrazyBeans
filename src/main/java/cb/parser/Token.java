/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.parser;

import java.util.Stack;

public class Token {
  public static final Token LPAREN = new Token(Lexer.LPAREN, "(", -1);
  public static final Token RPAREN = new Token(Lexer.RPAREN, ")", -1);
  public static final Token COMMA  = new Token(Lexer.COMMA, ",", -1);
  public static final Token TRUE   = new Token(Lexer.BOOLEAN, "TRUE", -1);
  public static final Token FALSE  = new Token(Lexer.BOOLEAN, "FALSE", -1);
  public static final Token EOF    = new Token(Lexer.EOF, "<EOF>", -1);

  private Token() {}

  private Token(int kind, String image, int line) {
    init(kind, image, line);
  }

  private void init(int kind, String image, int line) {
    this.kind  = kind;
    this.image = image;
    this.line  = line;
  }
  
  private static Stack stack = new Stack();

  public static Token createToken(int kind, String image, int line) {
    Token t;

    if(stack.isEmpty())
      t = new Token();
    else
      t = (Token)stack.pop();

    t.init(kind, image, line);
    return t;
  }

  public static void dispose(Token t) {
    if(t != null)
      stack.push(t);
  }

  public int    kind;
  public String image;
  public int    line;
}
