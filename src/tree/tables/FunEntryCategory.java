package tree.tables;

import java.util.ArrayList;

public class FunEntryCategory extends EntryCategory{
    private ArrayList<String> parametersName;
    private ArrayList<String> parametersMode;
    private ArrayList<String> parametersType;
    private String returnedType;

    //metodi costruttori
    public FunEntryCategory(String name, ArrayList<String> parametersName, ArrayList<String> parametersMode, ArrayList<String> parametersType, String returnedType) {
        super(name, "Fun");
        this.parametersName = parametersName;
        this.parametersMode = parametersMode;
        this.parametersType = parametersType;
        this.returnedType = returnedType;
    }

    //metodi getter and setter
    public ArrayList<String> getParametersName() {
        return parametersName;
    }
    public void setParametersName(ArrayList<String> parametersName) {
        this.parametersName = parametersName;
    }
    public ArrayList<String> getParametersMode() {
        return parametersMode;
    }
    public void setParametersMode(ArrayList<String> parametersMode) {
        this.parametersMode = parametersMode;
    }
    public ArrayList<String> getParametersType() {
        return parametersType;
    }
    public void setParametersType(ArrayList<String> parametersType) {
        this.parametersType = parametersType;
    }
    public String getReturnedType() {
        return returnedType;
    }
    public void setReturnedType(String returnedType) {
        this.returnedType = returnedType;
    }

    @Override
    public String toString() {
        return "FunEntryCategory{" +
                "parametersName=" + parametersName +
                ", parametersMode=" + parametersMode +
                ", parametersType=" + parametersType +
                ", returnedType='" + returnedType + '\'' +
                '}';
    }
}
