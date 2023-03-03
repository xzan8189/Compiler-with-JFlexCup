package tree.tables;

import java.util.HashMap;

public class Scoping {
    private HashMap<String, EntryCategory> scope;

    //metodi costruttori
    public Scoping() {
        this.scope = new HashMap<String, EntryCategory>();
    }

    public Scoping(HashMap<String, EntryCategory> scope) {
        this.scope = scope;
    }

    //metodi ausiliari
    public void addVarEntryCategory(VarEntryCategory varEntryCategory) {
        if (!scope.containsKey(varEntryCategory.getName())) {
            scope.put(varEntryCategory.getName(), varEntryCategory);
        } else {
            throw new Error("Variabile " + varEntryCategory.getName() + " già dichiarata nello stack!");
        }
    }

    public void addFunEntryCategory(FunEntryCategory funEntryCategory) {
        if (!scope.containsKey(funEntryCategory.getName())) {
            scope.put(funEntryCategory.getName(), funEntryCategory);
        } else {
            throw new Error("Funzione " + funEntryCategory.getName() + " già dichiarata nello stack!");
        }
    }

    //metodi getter
    public HashMap<String, EntryCategory> getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "Scoping{" +
                "scope=" + scope +
                '}';
    }
}
