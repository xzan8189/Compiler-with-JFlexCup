package nodes;

/* Se una classe estenderà questa classe "ExprNode",
*  allora significa che quella classe è un oggetto che restituisce un valore.
*
* Ad esempio quando fai "1 + 1", questa è una espressione che restituisce
* un valore, cioè "2" (poiché "1+1 = 2") e per tale motivo la classe "BinaryOP"
* estenderà questa classe.
* Anche quando chiami una funzione, e quindi usi "CallFun", restituisci
* un valore, che sarebbe il valore ritornato dalla funzione, e per tale motivo
* anche la classe "CallFun" estenderà tale classe.
* Anche quando fai "1 + a", l'identificatore 'a' restituisce un valore e quindi
* anche la classe "IdLeaf" estenderà tale classe.
*
* Quindi le classi che restituiscono valori e che quindi estenderanno tale classe, sono:
* BinaryOP
* UnaryOP
* Const
* IdLeaf
* CallFun
* */
public abstract class ExprNode {
    public String type;
}
