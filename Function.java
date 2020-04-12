// Aashir Khan and Vatsal Baherwani
import java.util.ArrayList;

public class Function implements Expression{
    Variable parameter;
    Expression body;

    public Function(Variable parameter, Expression body) {
        this.parameter = parameter;
        this.body = body;
    }

    static void replaceInFunction(Function func, String name, Expression replace) {
        if (!name.equals(func.parameter.getName())) {
            Expression body = func.body;

            if (body instanceof Variable && ((Variable) body).getName().equals(name)){
                func.body = replace;
            }

            if (body instanceof Application){
                Application.replaceInApplication((Application) body, name, replace);
            }

            if (body instanceof Function){
                replaceInFunction((Function) body, name, replace);
            }
        }
    }

    public void reduce(String inputName) {
       if (inputName.equals(parameter.getName())) {
           //there is a conflict, reduce function
            String newName = inputName + 1;
            parameter = new Variable(newName);
            replaceInFunction(this, inputName, new Variable(newName));
       }
       else {
           body.reduce(inputName);
       }
    }

    public String toString() {
        return "(λ" + parameter + "." + body + ")";
    }

    public Expression run() {
        body = body.run();
        return this;
    }

    public Function clone() {
        return new Function(parameter.clone(), body.clone());
    }

    public String getCode() {
        return "/" + parameter.getCode() + "." + body.getCode();
    }

    public ArrayList<String> getVariableNames(ArrayList<String> list) {
        parameter.getVariableNames(list);
        body.getVariableNames(list);
        return list;
    }
}
