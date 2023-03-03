# Compiler-with-JFlexCup

> **SDK**: Oracle OpenSDK 12.0.2<br>
> **Language level**: SDK default (12)

Implementation of the front-end of a compiler for a specified version of language Funlang ([funlang.org](https://funlang.org/), [Manual MyFun](https://funlang.org/man.htm)) with JFlex and JavaCup. The compiler takes a source code of the specified language and after the lexical, syntax and semantic analysis produces an object code in C language trough the pattern visitor. He also produces Abstract Syntax Tree in an XML file. To run the application configure the path of the source file as args[0] for the main.

*Prof. Costagliola<br>
Corso di Compilatori – Anno accademico 2021/2022*

### Specifica del linguaggio MyFun

1.  **Introduzione al linguaggio**

    L’attività progettuale assegnatoci per il corso di Compilatori ha previsto
    la creazione del front-end di un compilatore basato su una specifica
    derivata dal linguaggio Fun [(https://funlang.org/)](https://funlang.org/),
    un linguaggio semplice, strutturato, imperativo e dinamico che mira alla
    semplicità d’utilizzo.

    Lo svolgimento è stato effettuato interamente dal sottoscritto con l’apporto
    di opportune scelte progettuali che sono state necessarie per la creazione
    del compilatore.

    Il risultato finale di quest’attività permette dunque la creazione di file
    *.c* a partire da semplici file di testo che rispettano appunto la specifica
    del linguaggio MyFun.

2. **Regole di inferenza (alcune)**

    **Operatori unari (vedi Table 1)**

    **Operatori binari (vedi Table 2)**

**Table 1 per operazioni unarie:**

| **Operazione** | **Tipo** | **Tipo risultato** |
|----------------|----------|--------------------|
| UMinus         | int      | int                |
| UMinus         | real     | real               |
| Not            | bool     | Bool               |

**Table 2 per operazioni binarie:**

| **Operazione**          | **Tipo op1**         | **Tipo op2**         | **Tipo risultato** |
|-------------------------|----------------------|----------------------|--------------------|
| Plus, Minus, Times, Pow | int                  | int                  | int                |
| Plus, Minus, Times, Pow | int                  | real                 | real               |
| Plus, Minus, Times, Pow | real                 | int                  | real               |
| Plus, Minus, Times, Pow | real                 | real                 | real               |
| Div                     | int                  | int                  | real               |
| Div                     | real                 | int                  | real               |
| Div                     | int                  | real                 | real               |
| Div                     | real                 | real                 | real               |
| DivInt                  | int                  | int                  | int                |
| DivInt                  | real                 | int                  | int                |
| DivInt                  | int                  | real                 | int                |
| DivInt                  | real                 | real                 | int                |
| StrConcat               | string               | int/real/bool/string | string             |
| StrConcat               | int/real/bool/string | string               | string             |
| And                     | bool                 | bool                 | bool               |
| Or                      | bool                 | bool                 | bool               |
| Lt, Le, Gt, Ge          | int                  | int                  | bool               |
| Lt, Le, Gt, Ge          | int                  | real                 | bool               |
| Lt, Le, Gt, Ge          | real                 | int                  | bool               |
| Lt, Le, Gt, Ge          | real                 | real                 | bool               |
| Lt, Le, Gt, Ge          | int                  | int                  | bool               |
| Eq, Neq                 | int                  | int                  | bool               |
| Eq, Neq                 | int                  | real                 | bool               |
| Eq, Neq                 | real                 | int                  | bool               |
| Eq, Neq                 | real                 | real                 | bool               |
| Eq, Neq                 | bool                 | bool                 | bool               |
| Eq, Neq                 | string               | string               | bool               |
