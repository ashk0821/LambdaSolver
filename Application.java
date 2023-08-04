// Aashir Khan and Vatsal Baherwani
import java.util.ArrayList;

public class Application implements Expression {
    private Expression left;
    private Expression right;

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return "(" + left + " " + right + ")";
    }

    static void replaceInApplication(Application app, String name, Expression replace) {
        Expression left = app.left;
        Expression right = app.right;

        if (left instanceof Application) {
            replaceInApplication((Application) left, name, replace);
        }

        if (right instanceof Application) {
            replaceInApplication((Application) right, name, replace);
        }

        if (left instanceof Variable && ((Variable) left).getName().equals(name)) {
            app.left = replace;
        }

        if (right instanceof Variable && ((Variable) right).getName().equals(name)) {
            app.right = replace;
        }

        if (left instanceof Function) {
            Function.replaceInFunction((Function) left, name, replace);
        }

        if (right instanceof Function) {
            Function.replaceInFunction((Function) right, name, replace);
        }
    }


    public void reduce(String inputName) {
        left.reduce(inputName);
        right.reduce(inputName);
    }

    public Expression run() {
        if (left instanceof Function) {
            Variable parameter = ((Function) left).parameter;
            Expression input = right;
            Expression body = ((Function) left).body.clone();
            Expression returnBody = body;

            for (String inputName : right.getVariableNames(new ArrayList<>())) {
                body.reduce(inputName);
            }

            if (body instanceof Variable && ((Variable) body).getName().equals(parameter.getName())) {
                returnBody = input;
            }

            if (body instanceof Application) {
                returnBody = body;
                replaceInApplication((Application) returnBody, parameter.getName(), input);
            }

            if (body instanceof Function) {
                Function.replaceInFunction((Function) body, parameter.getName(), input);
            }
            //run ((\f.\x.(f x)) (\x.x) woohooyougotit)
            //System.out.println("Result: " + ret);
            return returnBody;
        }
        else {
            left = left.run();
            right = right.run();
            return this;
        }
    }

    public Application clone() {
        return new Application(left.clone(), right.clone());
    }

    public String getCode() {
        return left.getCode() + right.getCode();
    }

    public ArrayList<String> getVariableNames(ArrayList<String> list) {
        left.getVariableNames(list);
        right.getVariableNames(list);
        return list;
    }
}
