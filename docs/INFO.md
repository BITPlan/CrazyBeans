## Installation
```
mvn clean compile assembly:single
```
to create a standalone jar

## Original Documentation 
* http://crazybeans.sourceforge.net/

## Usage
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar --help
Help

cb.petal Version: 0.0.1

 github: https://github.com/BITPlan/CrazyBeans

usage: java cb.petaltools.PetalTool
  -d (--debug)              : debug
                             adds debugging output
 -f (--format) VALUE        : output format 
                             default: xmi, could also be rose,java or none
 -h (--help)               : help
                             show this usage
 -i (--input) VALUE         : input
                             the path to the rational rose input .mdl file -
                             will use stdin if omitted or '-' is specified as
                             input parameter
 -o (--output) VALUE        : output
                             the path to the output file - will use stdout if
                             omitted or '-' is specified as output parameter
 -src (--source-root) VALUE : path to source
                             the path to the folder where the generated (java)
                             code should be created
 -tv (--treeview)          : treeView
                             a Java Swing based GUI to show the petal tree is
                             started
 -v (--version)            : showVersion
                             show current version if this switch is used
```                      

### Example - xmiExport to stdout
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar -i examples/uni.mdl 
```

### Example - rose petal file dump to stdout
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar -i examples/uni.mdl --format rose
```

### Example - xmiExport to file (with pipe)
```
cat examples/uni.mdl | java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar  -i - -o examples/uni.xmi
```

### Example - java code generation
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar  -i examples/uni.mdl --format none -src /tmp/uni
```

### Example - treeView
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar -i examples/uni.mdl --format none -tv 
```

## AUTHORS
The original CrazyBeans framework is copyright (2001) Markus Dahm. 

This fork is Copyright(c) 2015 BITPlan GmbH (Owner: Wolfgang Fahl)

based on the githubytized version of https://github.com/matthewmeyer

## CREDITS
The AnsweringMachine Example file is from

https://github.com/adombroski/xslt-cookbook/blob/master/xsltckbk/XSLTCkBkCode_Edition_1/code-gen/AnswerinMachine.mdl

The Sequence Diagram Example file is from
https://www.cs.bgu.ac.il/~korenel/docs/Diagrams/sequanceDiagram.mdl

