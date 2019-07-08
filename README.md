### CrazyBeans
[Java library to read, modify or create Rational Rose petal files](http://www.bitplan.com/CrazyBeans)

[![Travis (.org)](https://img.shields.io/travis/BITPlan/CrazyBeans.svg)](https://travis-ci.org/BITPlan/CrazyBeans)
[![Maven Central](https://img.shields.io/maven-central/v/com.bitplan/org.crazybeans.svg)](https://search.maven.org/artifact/com.bitplan/org.crazybeans/1.6.0/jar)
[![codecov](https://codecov.io/gh/BITPlan/CrazyBeans/branch/master/graph/badge.svg)](https://codecov.io/gh/BITPlan/CrazyBeans)
[![GitHub issues](https://img.shields.io/github/issues/BITPlan/CrazyBeans.svg)](https://github.com/BITPlan/CrazyBeans/issues)
[![GitHub issues](https://img.shields.io/github/issues-closed/BITPlan/CrazyBeans.svg)](https://github.com/BITPlan/CrazyBeans/issues/?q=is%3Aissue+is%3Aclosed)
[![GitHub](https://img.shields.io/github/license/BITPlan/CrazyBeans.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![BITPlan](http://wiki.bitplan.com/images/wiki/thumb/3/38/BITPlanLogoFontLessTransparent.png/198px-BITPlanLogoFontLessTransparent.png)](http://www.bitplan.com)

### Documentation
* [Wiki](http://www.bitplan.com/CrazyBeans)
* [CrazyBeans Project pages](https://BITPlan.github.io/CrazyBeans)
* [Javadoc](https://BITPlan.github.io/CrazyBeans/apidocs/index.html)
* [Test-Report](https://BITPlan.github.io/CrazyBeans/surefire-report.html)
### Maven dependency

Maven dependency
```xml
<!-- Java library to read, modify or create Rational Rose petal files http://www.bitplan.com/CrazyBeans -->
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>org.crazybeans</artifactId>
  <version>1.6.0</version>
</dependency>
```

[Current release at repo1.maven.org](http://repo1.maven.org/maven2/com/bitplan/org.crazybeans/1.6.0/)

### How to build
```
git clone https://github.com/BITPlan/CrazyBeans
cd CrazyBeans
mvn install
```
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

## Version history
| Version | Date      | changes
| -------:| ------:   | ----------
| 1.5.1   | 2001-2013 | Original by Markus Dahm on SourceForge https://sourceforge.net/projects/crazybeans/
| 1.5.2   | 2015-01-13| first version of this fork
| 1.5.3   | 2016-11-29| improves Petal File reading by allowing non strict reading to ignore errors recreating .ser templates by running cb.util.Dump
| 1.5.5   | 2018-09-06| fixes #7 adds Visibility convenience enumeration
| 1.5.6   | 2018-09-16| fixes #8, #9 #10 
| 1.5.7   | 2018-09-26| fixes #11 
| 1.5.8   | 2018-10-17| fixes #12 - Tag lookup for Views, clientView, supplierView, fixes #13 compartment.getCompartmentItems()
| 1.5.9   | 2018-10-27| fixes #13 - Compartment Items, fixes #14 PathMap from ini / registry 
| 1.5.10  | 2018-10-28| fixes #15 - SelfTransView support, fixes #16 Detect endless loop, fixes #17 show context for path map errors, fixes #18 allow non strict handling of path map errors 
| 1.5.11  | 2018-10-31| fixes #19 - DestructionMarker support, fixes #20 showOperationsSignature,fixes #6 - docu, license, readme
| 1.5.12  | 2018-11-02| fixes #21 - AssocConstraintView support
| 1.6.0   | 2018-11-03| fixes #22 - PathMap handling
