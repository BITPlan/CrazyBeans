CrazyBeans
==========

## Project 
... allows to read modify or create model files form Rational Rose. 

### Documentation
* [Javadoc](http://wolfgangfahl.github.io/CrazyBeans/apidocs/index.html)


## INSTALLATION
```
mvn install
```
to use as a library 

```
mvn clean compile assembly:single
```
to create a standalone jar

## Usage
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar --help
Help

cb.petal Version: 0.0.1

 github: https://github.com/BITPlan/CrazyBeans

usage: java cb.petaltools.XmiExport
 -d (--debug)       : debug
                      adds debugging output
 -h (--help)        : help
                      show this usage
 -i (--input) WERT  : input
                      the path to the rational rose input .mdl file - will use
                      stdin if '-' is specified as input parameter
 -ne (--noexport)   : no export
                      the xmi export is supressed
 -o (--output) WERT : output
                      the path to the xmi output .xmi file - will use stdout if
                      omitted or - is specified as output parameter
 -tv (--treeview)   : treeView
                      a Java Swing based GUI to show the petal tree is started
 -v (--version)     : showVersion
                      show current version if this switch is used
```                      

### Example - xmiExport to stdout
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar -i examples/uni.mdl 
```

### Example - xmiExport to file
```
cat examples/uni.mdl | java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar  -i - -o examples/uni.xmi
```

### Example - treeView
```
java -jar target/crazybeans-0.0.1-jar-with-dependencies.jar -i examples/uni.mdl -ne -tv 
```

## AUTHORS
The original CrazyBeans framework is copyright (2001) M. Dahm. 
This fork is Copyright(c) 2015 BITPlan GmbH based on the githubytized version of https://github.com/matthewmeyer

## Version history
* 2001-2013: Original by Markus Dahm on SourceForge https://sourceforge.net/projects/crazybeans/
* 0.0.1 - 2015-01-11: first version of this fork