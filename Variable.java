// Aashir Khan and Vatsal Baherwani
import java.util.ArrayList;

public class Variable implements Expression{
    private String name;
    private static ArrayList<String> VariableList = new ArrayList<>();

    public Variable(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public Expression run() {
        return this;
    }

    public String getName() {
        return name;
    }

    public Variable clone() {
        return new Variable(name);
    }

    static void clearNStack() {
        VariableList.clear();
    }

    public String getCode() {
        if (VariableList.contains(name)){
            return "" + VariableList.indexOf(name);
        }
        VariableList.add(name);
        return "" + (VariableList.size()-1);
    }

    public ArrayList<String> getVariableNames(ArrayList<String> list) {
        if (!list.contains(name)){
            list.add(name);
        }
        return list;
    }

    public void reduce(String inputName) {
    }
}
