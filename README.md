# Lambda Calculus Interpreter

A Java implementation of a lambda calculus interpreter with support for Church numerals, variable definitions, and function applications through an interactive console.

## Features

- Interactive console interface for lambda expression evaluation
- Support for Church numerals and basic arithmetic operations
- Variable definitions and substitution
- Lambda function abstraction and application
- Automatic population of Church numerals within a specified range

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Running the Program

1. Compile all Java files:
```bash
javac *.java
```

2. Run the program:
```bash
java Console
```

## Usage

The interpreter accepts the following commands:

### Basic Expressions
- Define variables using `=`:
```
> x = \f.\x.x
Added (λf.λx.x) as x
```

### Function Applications
- Use `run` to evaluate expressions:
```
> run (\x.x) y
y
```

### Church Numerals
- Populate Church numerals in a range:
```
> populate 0 5
Populated numbers 0 to 5
```

### Lambda Syntax
- Use `\` or `λ` for lambda abstractions
- Variables can be any alphanumeric string
- Use parentheses for grouping expressions

### Special Commands
- `exit`: Exit the interpreter
- `populate <low> <high>`: Generate Church numerals from low to high

## Examples

```
> zero = \f.\x.x
Added (λf.λx.x) as zero

> succ = \n.\f.\x.f (n f x)
Added (λn.λf.λx.f (n f x)) as succ

> run succ zero
(λf.λx.f x)

> one = run succ zero
Added (λf.λx.f x) as one
```

## Implementation Details

The interpreter consists of several key components:

- `Console.java`: Main interface and command processing
- `Expression.java`: Core interface for lambda expressions
- `Application.java`: Handles function applications
- `Function.java`: Represents lambda abstractions
- `Variable.java`: Manages variable bindings

## Contributing

Feel free to submit issues and enhancement requests!

## Authors

- Aashir Khan
- Vatsal Baherwani
