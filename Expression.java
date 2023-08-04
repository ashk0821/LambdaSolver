// Aashir Khan and Vatsal Baherwani
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Expression {

    Map<String, Expression> variableMap = new HashMap<>();
    Map<String, String> patternMap = new HashMap<>();

    Expression run();
    Expression clone();

    String getCode();

    ArrayList<String> getVariableNames(ArrayList<String> list);

    void reduce(String inputName);

    static char[] getParenthesesChunk(char[] array, int startIndex) {
        int openCount = 1;
        int index = startIndex + 1;
        StringBuilder builder = new StringBuilder();

        while (openCount > 0) {
            if (array[index] == '(') {
                openCount++;
            }
            else if (array[index] == ')') {
                openCount--;
            }

            if (openCount > 0) {
                builder.append(array[index]);
            }
            index++;
        }
        return builder.toString().toCharArray();
    }

    static int getParenthesesEnd(char[] array, int startIndex) {
        int openCount = 1;
        int index = startIndex;

        while (openCount > 0) {
            index++;

            if (array[index] == '(') {
                openCount++;
            }

            else if (array[index] == ')') {
                openCount--;
            }
        }
        return index;
    }

    static void addVariable(String name, Expression value) {
        Variable.clearNStack();
        variableMap.put(name, value);
        patternMap.put(value.getCode(), name);
    }

    static Expression parseString(String input) {
        input = input.replaceAll("\uFEFF", "");

        //Alphanumeric check method obtained from https://www.techiedelight.com/check-string-contains-only-alphabets-java/
        if (input.matches("^[a-zA-Z0-9+*\\-/?!]*$")) {
            Expression variable = variableMap.get(input);

            if (variable != null){
                return variable;
            }

            return new Variable(input);
        }

        char[] chars = input.toCharArray();
        int readIndex = 0;

        ArrayList<Expression> expressions = new ArrayList<>();

        while (readIndex < chars.length) {
            String chunk = null;

            if (chars[readIndex] == '\\' || chars[readIndex] == 'λ') {

                String afterLambda = input.substring(readIndex + 1);
                String[] split = afterLambda.split("\\.",2);
                String body = split[1];

                expressions.add(new Function(new Variable(split[0].trim()), parseString(body.trim())));
                //System.out.println(readIndex);

                readIndex += split[0].length() + body.length() + 1;
                //System.out.println(readIndex);
            }

            else if (chars[readIndex] == '(') {
                chunk = new String(getParenthesesChunk(chars, readIndex));
                readIndex = getParenthesesEnd(chars, readIndex);
            }

            else if (chars[readIndex] != ' ') {
                chunk = input.substring(readIndex).split(" ")[0];

                if (chunk.indexOf('(') != -1) {
                    chunk = input.split("\\(")[0];
                }
                readIndex += chunk.length() - 1;
            }
            //System.out.println(chunk);
            if (chunk != null) {
                expressions.add(parseString(chunk));
            }

            readIndex++;
        }

        while (expressions.size() > 2) {
            Expression leftExpression = expressions.remove(0);
            Expression rightExpression = expressions.remove(0);
            expressions.add(0, new Application(leftExpression, rightExpression));
        }

        if (expressions.size() == 2) {
            return new Application(expressions.get(0), expressions.get(1));
        }

        else {
            return expressions.get(0);
        }
    }

    static Expression match(Expression expression) {
        Variable.clearNStack();
        String pattern = expression.getCode();

        if (patternMap.containsKey(pattern)) {
            return new Variable(patternMap.get(pattern));
        }
        return expression;
    }

    static void populate(int low, int high) {
        addVariable("0", parseString("\\f.\\x.x"));
        addVariable("succ", parseString("\\n.\\f.\\x.f (n f x)"));

        int num = low;

        while (num < high) {
            Expression value = Expression.parseString("succ " + num).run();

            while(!value.getCode().equals(value.run().getCode())) {
                value = value.run();
            }

            addVariable("" + (num + 1), value);
            num++;
        }

        System.out.println("Populated numbers " + low + " to " + high);
    }
}