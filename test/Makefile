#
# $Id: Makefile,v 1.7 2001/07/30 15:51:42 dahm Exp $
#

SOURCES := $(wildcard *.java)
CLASSES := $(patsubst %.java,%.class,$(SOURCES))
JC     = jikes
JFLAGS = +E

%.class: %.java
	@rm -f $@
	$(JC) $(JFLAGS) $<

all: $(CLASSES)

test: all
	java test.Test1 > Test1.mdl
	java test.Test2
	java test.Test4 ../examples/uni.mdl uni.xmi
	java test.Test3 ../examples/uni.mdl

clean:
	rm -f core *~ *.class *.mdl
