// jflex -d src srcjflexcup/lexer.flex

import java_cup.runtime.*;

%%

%class Lexer
%unicode
%cupsym sym
%cup
%line
%column

%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object o) {
        return new Symbol(type, yyline, yycolumn, o);
    }
%}

Identifier = [$_A-Za-z][$_A-Za-z0-9]*

Digit = 0 | [1-9][0-9]*
//Number = {Digit} (\.[0-9]+)? (E[+-]?[0-9]+)?
IntegerConst = {Digit}
RealConst = {Digit}\.{Digit}
LineTerminator = \r\n | \r | \n
WhiteSpace = {LineTerminator} | [ \t\f]


%state DOUBLE_QUOTE_STRING
%state SINGLE_QUOTE_STRING
%state BLOCK_COMMENT
%state LINE_COMMENT
%state ERROR_STRING
%state ERROR

%%

// Lexical rules
/* keywords */

<YYINITIAL> "main"    {return symbol(sym.MAIN);}
<YYINITIAL> "if"      {return symbol(sym.IF);}
<YYINITIAL> "then"    {return symbol(sym.THEN);}
<YYINITIAL> "else"    {return symbol(sym.ELSE);}
<YYINITIAL> "while"   {return symbol(sym.WHILE);}
<YYINITIAL> "end"     {return symbol(sym.END);}
<YYINITIAL> "loop"    {return symbol(sym.LOOP);}

<YYINITIAL> "fun"     {return symbol(sym.FUN);}

<YYINITIAL> "@"       {return symbol(sym.OUTPAR);}
<YYINITIAL> "var"     {return symbol(sym.VAR);}
<YYINITIAL> "out"     {return symbol(sym.OUT);}
<YYINITIAL> "integer" {return symbol(sym.INTEGER);}
<YYINITIAL> "real"    {return symbol(sym.REAL);}
<YYINITIAL> "string"  {return symbol(sym.STRING);}
<YYINITIAL> "bool"    {return symbol(sym.BOOL);}

<YYINITIAL> ":="      {return symbol(sym.ASSIGN);}
<YYINITIAL> "+"       {return symbol(sym.PLUS);}
<YYINITIAL> "-"       {return symbol(sym.MINUS);}
<YYINITIAL> "*"       {return symbol(sym.TIMES);}
<YYINITIAL> "div"     {return symbol(sym.DIVINT);}
<YYINITIAL> "/"       {return symbol(sym.DIV);}
<YYINITIAL> "^"       {return symbol(sym.POW);}
<YYINITIAL> "&"       {return symbol(sym.STR_CONCAT);}
<YYINITIAL> "="       {return symbol(sym.EQ);}
<YYINITIAL> "<>"      {return symbol(sym.NE);}
<YYINITIAL> "!="      {return symbol(sym.NE);}
<YYINITIAL> "<"       {return symbol(sym.LT);}
<YYINITIAL> "<="      {return symbol(sym.LE);}
<YYINITIAL> ">"       {return symbol(sym.GT);}
<YYINITIAL> ">="      {return symbol(sym.GE);}
<YYINITIAL> "and"     {return symbol(sym.AND);}
<YYINITIAL> "or"      {return symbol(sym.OR);}
<YYINITIAL> "not"     {return symbol(sym.NOT);}
<YYINITIAL> "true"    {return symbol(sym.TRUE);}
<YYINITIAL> "false"   {return symbol(sym.FALSE);}

<YYINITIAL> "%"       {return symbol(sym.READ);}
<YYINITIAL> "?"       {return symbol(sym.WRITE);}
<YYINITIAL> "?."      {return symbol(sym.WRITELN);}
<YYINITIAL> "?:"      {return symbol(sym.WRITET);}
<YYINITIAL> "?,"      {return symbol(sym.WRITEB);}

<YYINITIAL> "return"  {return symbol(sym.RETURN);}

<YYINITIAL> "("       {return symbol(sym.LPAR);}
<YYINITIAL> ")"       {return symbol(sym.RPAR);}
<YYINITIAL> ":"       {return symbol(sym.COLON);}
<YYINITIAL> ";"       {return symbol(sym.SEMI);}
<YYINITIAL> ","       {return symbol(sym.COMMA);}

<YYINITIAL> {
    /* identifier */
    {Identifier}                        { return new Symbol(sym.ID, yytext()); }

    /* numbers */
    {IntegerConst}                      { return new Symbol(sym.INTEGER_CONST, yytext()); }
    {RealConst}                         { return new Symbol(sym.REAL_CONST, yytext()); }

    /* strings */
    \"                                  { string.setLength(0); yybegin(DOUBLE_QUOTE_STRING); }
    '                                   { string.setLength(0); yybegin(SINGLE_QUOTE_STRING); }
    /* comments */
    "#*"                                { yybegin(BLOCK_COMMENT); }
    "#" | "//"                          { yybegin(LINE_COMMENT); }

    /* whitespace */
    {WhiteSpace}                        { /* ignore */ }
}

<DOUBLE_QUOTE_STRING> {
    \"                                  { yybegin(YYINITIAL);
                                          return new Symbol(sym.STRING_CONST, string.toString());
                                        }
    [^\n\r\"\\]+                        { string.append( yytext() ); }
    \\t                                 { string.append('\t'); }
    \\n                                 { string.append('\n'); }
    \\r                                 { string.append('\r'); }
    \\\"                                { string.append('\"'); }
    [^]                                 { //string.append(yytext());
                                          yybegin(ERROR_STRING);
                                        } //se togli questa istruzione esce un errore strano, quindi l'ho rimasta
    <<EOF>>                             { throw new Error("String costante non chiusa"); }
}

<SINGLE_QUOTE_STRING> {
    '                                   { yybegin(YYINITIAL);
                                          return new Symbol(sym.STRING_CONST, string.toString());
                                        }
    [^\n\r'\"\\]+                       { string.append( yytext() ); }
    \\t                                 { string.append("\\t"); }
    \\n                                 { string.append("\\n"); }
    \\r                                 { string.append("\\r"); }
    \\'                                 { string.append("'"); }
    \"                                  { string.append("\""); }
    \\                                  { string.append("\\"); }
    [^]                                 { //string.append(yytext());
                                          yybegin(ERROR_STRING);
                                        } //se togli questa istruzione esce un errore strano, quindi l'ho rimasta
    <<EOF>>                             { throw new Error("String costante non chiusa"); }
}

<BLOCK_COMMENT> {
    #                                   { yybegin(YYINITIAL); }
    [^#]                                { /* ignore */ }

    <<EOF>>                             { throw new Error("Commento non chiuso"); }
}

<LINE_COMMENT> {
    {LineTerminator}                    { yybegin(YYINITIAL); }
    [^]                                 { /* ignore */ }
}



/* Stampe particolari di errori*/
<ERROR_STRING> {
    [^]                                 { int line = yyline + 1;
                                          int column = yycolumn + 1;
                                          throw new Error("Illegal character: '" + yytext() + "' and the String starts in Line:" + line + ", Column:" + column);
                                        }
}

<ERROR> {
    [^]                                 { int line = yyline + 1;
                                          int column = yycolumn + 1;
                                          throw new Error("Illegal character: '" + yytext() + "' in Line:" + line + ", Column:" + column);
                                        }
}

/* errors and it takes all the rest */
[^]                                     { int line = yyline + 1;
                                          int column = yycolumn + 1;
                                          throw new Error("Illegal character: '" + yytext() + "' in Line:" + line + ", Column:" + column);
                                        }
<<EOF>>                                 { return new Symbol(sym.EOF); }