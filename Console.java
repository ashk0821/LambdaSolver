// Aashir Khan and Vatsal Baherwani

import java.util.Scanner;

public class Console {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            String trimmed = input.split(";")[0].trim();

            if (trimmed.equals("exit")) {
                break;
            }

            if (!trimmed.equals("")) {
                String[] splitWhenEqual = trimmed.split("=");

                if (splitWhenEqual.length > 1) {
                    //there is = in the expression
                    String variableName = splitWhenEqual[0].trim();
                    String toParse = splitWhenEqual[1].trim();

                    Expression value = Expression.parseString(toParse);

                    if (toParse.split(" ")[0].trim().equals("run")) {
                        value = Expression.parseString(toParse.split(" ",2)[1]).run();

                        while (!value.getCode().equals(value.run().getCode())) {
                            value = value.run();
                        }
                    }
                    Expression.addVariable(variableName, value);
                    System.out.println("Added " + value + " as " + variableName);
                    Variable.clearNStack();
                }

                else {
                    Expression result = Expression.parseString(trimmed);

                    if (trimmed.split(" ")[0].trim().equals("populate")) {
                        int low = Integer.parseInt(trimmed.split(" ")[1]);
                        int high = Integer.parseInt(trimmed.split(" ")[2]);

                        Expression.populate(low, high);
                    }

                    else {
                        if (trimmed.split(" ")[0].trim().equals("run")) {
                            result = Expression.parseString(trimmed.split(" ", 2)[1].trim()).run();

                            while (!result.getCode().equals(result.run().getCode())) {
                                result = result.run();
                            }
                        }
                        System.out.println(Expression.match(result));
                        Variable.clearNStack();
                    }
                }
            }

            System.out.print(">");
            input = scanner.nextLine();
        }
        System.out.println("Goodbye!");
    }
}
