# Compiler-with-JFlexCup

> **SDK**: Oracle OpenSDK 12.0.2<br>
> **Language level**: SDK default (12)

Implementation of the front-end of a compiler for a specified version of language Funlang (funlang.org) with JFlex and JavaCup. The compiler takes a source code of the specified language and after the lexical, syntax and semantic analysis produces an object code in C language trough the pattern visitor. He also produces Abstract Syntax Tree in an XML file. To run the application configure the path of the source file as args[0] for the main.

Specifica del linguaggio MyFun

Prof. Costagliola

Corso di Compilatori – Anno accademico 2021/2022

1.  Introduzione al linguaggio

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

2.  Struttura del linguaggio

    1.  Tipi primitivi e casting

        All’interno di MyFun si hanno a disposizione quattro tipologie di tipi:
        *int, real, bool e string*. Con il tipo *int* vengono classificati tutti
        i numeri appartenenti alla classe degli interi, quindi sia positivi che
        negativi. Il tipo *real* contraddistingue l’insieme dei numeri reali,
        anch’essi sia positivi che negativi. Il tipo *bool* indica una variabile
        che può assumere valore *true* o *false* e corrispondono ai normali
        booleani. Infine, si ha il tipo *string* che consta in una successione
        di caratteri.

        Inoltre, è supportata l’inferenza di tipo con l’utilizzo del tipo:
        *var*. L’uso dell’inferenza di tipo è vincolato all’assegnazione del
        valore alla variabile nel momento della dichiarazione. Di seguito viene
        riportato un esempio per maggiore chiarezza.

        Esempio di vincolo rispettato: var variabile := “stringaprova”;

        Esempio di vincolo non rispettato: var variabile; variabile :=
        “stringaprova”;

        Infine, vengono fornite di seguito le regular expression per la
        creazione di numeri, stringhe ed identificatori:

    -   Numeri interi: 0 \| [1-9][0-9]\*

    -   Numeri reali: (0 \| [1-9][0-9]\*) . (0 \| [1-9][0-9]\*)

    -   Identificatori: [_A-Za-z][_A-Za-z0-9]\*

    -   Stringhe: “\~” \| ’\~’

    Il linguaggio prevede la possibilità di utilizzare commenti su una singola
    linea o su linee multiple: rispettivamente con l’utilizzo di “*\#”* e “*//”*
    per il primo caso e con l’utilizzo di *“\#\* \#”* come simboli di inizio e
    fine commento su più linee di codice.

    Riguardo il casting: non è previsto in nessun modo il casting esplicito
    mentre è previsto quello implicito in particolare per la concatenazione di
    stringhe con interi, reali o booleani.

    1.  Variabili globali e funzioni

        Il linguaggio MyFun prevede la possibilità di definire variabili con
        scope globale, dunque visibili in qualsiasi punto del codice (ovviamente
        se non oscurate all’interno di altri scope).

        È prevista anche la definizione di funzioni, necessariamente prima del
        main, utilizzabili successivamente all’interno del codice. La
        definizione della firma di una funzione si affida alla seguente
        sintassi: *fun nomefunzione(tipopar par1, tipopar par2, out tipopar
        par3):tiporitorno.*

        Ovviamente, una funzione può essere definita anche senza parametri
        quindi omettendo la lista degli stessi, presente all’interno delle
        parentesi tonde. Il tipo di ritorno specificato dopo i due punti può
        assumere i seguenti tipi: *int, real, bool* e *string.* Quest’ultimo non
        è necessario e può essere anche omesso (assieme ai ‘:’), quando la
        funzione non prevede il ritorno di nessun valore. L’utilizzo di *out*
        permette la possibilità di definire un passaggio per riferimento alla
        variabile annotata con questa keyword, la chiamata a funzione in questo
        caso prevede l’utilizzo del simbolo ‘@’ per i parametri annotati con
        *out*. Dopo aver scritto il corpo della funzione è necessario scrivere
        *end fun;* per la chiusura formale della stessa. Di seguito un esempio
        di dichiarazione di funzione (a sinistra) e chiamata alla stessa (a
        destra).

1.  Vincoli del linguaggio

    Il linguaggio MyFun presenta diversi vincoli, tra i più importanti:

    -   Il main non possiede un tipo di ritorno e non possiede parametri, dev’essere
    necessariamente scritto dopo la definizione delle funzioni. Inoltre, non può
    essere invocata come procedura e non possiede nessun statement di *return*.

    -   L’utilizzo del tipo *var* nella dichiarazione di una variabile dev’essere
    sempre accompagnato dall’assegnazione alla stessa, come nell’esempio fornito
    precedentemente.

    -   Non è possibile utilizzare keywords del linguaggio *C* come identificatori
    in quanto il loro utilizzo comporterebbe problemi nella compilazione del
    tradotto in *C.*

    I restanti vincoli si attengono alla grammatica e alle specifiche concordate
    in classe con il professore Costagliola.

1.  Scelte implementative

    1.  Creazione albero sintattico astratto

        L’albero sintattico astratto è stato creato tramite il pattern visitor,
        che ha previsto l’esplorazione dell’albero sintattico, creato tramite le
        action della grammatica, con la creazione di un documento XML.

    2.  Analisi semantica

        Per l’analisi semantica che consta di due fasi: scoping e type checking,
        sono state effettuate le seguenti scelte: tramite una singola visita
        dell’albero, grazie sempre al pattern visitor, vengono create le symbol
        table, una per ogni ambiente di scope, organizzate tramite uno stack tra
        loro. Contemporaneamente vengono effettuati controlli di tipo, elencati
        in seguito tramite le regole di inferenza.

    3.  Generazione codice C

        Per facilitare la creazione del codice sono state definite alcune
        procedure di supporto utili per il casting dei tipi *int,
        double(corrispondente al real di MyFun)* e *bool* in *char \*
        (corrispondente a string di MyFun)*. È stata creata anche una funzione
        di supporto per la concatenazione di stringhe non costanti (a differenza
        di strcat).

        Per i booleani è stat utilizzata la libreria stdbool.h

        Inoltre, data la scarsa flessibilità del C riguardo le variabili
        globali, quest’ultime sono state trattate diversamente per renderle
        costanti, e quindi utilizzabili in C.

2.  Regole di inferenza (alcune)

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
