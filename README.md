CrazyBeans
==========

## Project 
... allows to read modify or create model files form Rational Rose. 
[![Build Status](https://travis-ci.org/BITPlan/CrazyBeans.svg?branch=master)](https://travis-ci.org/BITPlan/CrazyBeans)

### Documentation
* [Javadoc](http://bitplan.github.io/CrazyBeans/apidocs/index.html)

### Distribution
Available at Maven Central see 

http://search.maven.org/#artifactdetails|com.bitplan|org.crazybeans|1.5.3|jar

Maven dependency:

```xml
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>org.crazybeans</artifactId>
  <version>1.5.3</version>
</dependency>
```

## Installation
```
git clone https://github.com/BITPlan/CrazyBeans
mvn install
```
to use as a library 

```
mvn clean compile assembly:single
```
to create a standalone jar

## Test
```
mvn test
...
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

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

## Version history
* 1.5.1: 2001-2013 : Original by Markus Dahm on SourceForge https://sourceforge.net/projects/crazybeans/
* 1.5.2: 2015-01-13: first version of this fork
* 1.5.3: 2016-11-29: improves Petal File reading by allowing non strict reading to ignore errors
recreating .ser templates by running cb.util.Dump