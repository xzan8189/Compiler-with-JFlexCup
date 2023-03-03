package tree.tables;

public class VarEntryCategory extends EntryCategory {
    private String type;
    private boolean isOut;

    //metodi costruttori
    public VarEntryCategory(String name, String type, boolean isOut) {
        super(name, "Var");
        this.type = type;
        this.isOut = isOut;
    }

    //metodi getter and setter
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isOut() {
        return isOut;
    }
    public void setOut(boolean out) {
        isOut = out;
    }

    @Override
    public String toString() {
        return "VarEntryCategory{" +
                "type='" + type + '\'' +
                ", isOut=" + isOut +
                '}';
    }
}
