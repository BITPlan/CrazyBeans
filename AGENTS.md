# CrazyBeans AGENTS.md

## Project Overview
- **CrazyBeans** is a Java library to read, modify, or create Rational Rose petal files.
- The project is built with **Maven**.

## Dev environment tips
- Ensure JDK 8, 17, or 21 is installed as per the CI matrix.
- Use `mvn clean compile` to build the project.
- Executables or scripts like `cb` exist for command-line usage (`args4j` is used for CLI args).

## Testing instructions
- The CI plan is located in `.github/workflows/maven.yml`.
- Run `mvn test` to run every check defined for the project.
- To focus on a specific test step, use the surefire pattern: `mvn -Dtest=TestName test`.
- Fix any test or compilation errors until the whole suite is green.
- Add or update JUnit tests for the code you change, even if nobody asked.

## PR instructions
- Always run `mvn test` before committing to ensure the build stays green.
- Check GitHub workflows matrix to ensure changes are compatible with Java 8, 17, and 21.
