package tree.tables;

import java.util.HashMap;
import java.util.Stack;

public class ScopingTables {
    private static final Stack<Scoping> SCOPES = new Stack<Scoping>();

    //metodi ausiliari
    public static void newScope() {
        SCOPES.push(new Scoping());
    }
    public static Scoping getScope() {
        return SCOPES.lastElement();
    }
    public static void removeScope() {
        SCOPES.pop();
    }

    public static void addEntryCategory(EntryCategory entryCategory) {
        if (entryCategory instanceof FunEntryCategory) { // caso in cui sia una Funzione
            SCOPES.lastElement().addFunEntryCategory((FunEntryCategory) entryCategory);
        } else if (entryCategory instanceof VarEntryCategory) { // caso in cui sia una Variabile
            SCOPES.lastElement().addVarEntryCategory((VarEntryCategory) entryCategory);
        }
    }

    public static EntryCategory lookUp(String entryCategoryName) {
        for (int i = SCOPES.size()-1; i >= 0; i--) { //partiamo dall'alto e andiamo verso il basso, come un vero stack
            HashMap<String, EntryCategory> currentTable = SCOPES.get(i).getScope();
            if (currentTable != null) {
                if (currentTable.containsKey(entryCategoryName)) {
                    return currentTable.get(entryCategoryName);
                }
            }
        }
        return null; /* nel caso in cui non è stata trovata l'entryCategory negli scopes */
    }

}
