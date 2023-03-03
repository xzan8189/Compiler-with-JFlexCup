package tree.tables;

public abstract class EntryCategory {
    private String name;
    private String entryCategory;

    //metodi costruttori
    public EntryCategory(String name, String entryCategory) {
        this.name = name;
        this.entryCategory = entryCategory;
    }

    //metodi getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEntryCategory() {
        return entryCategory;
    }
    public void setEntryCategory(String entryCategory) {
        this.entryCategory = entryCategory;
    }

    @Override
    public String toString() {
        return "EntryCategory{" +
                "name='" + name + '\'' +
                ", entryCategory='" + entryCategory + '\'' +
                '}';
    }
}
