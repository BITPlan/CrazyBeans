#
# $Id: Makefile,v 1.1 2001/07/27 11:45:28 dahm Exp $
#

SOURCES := $(wildcard *.java)
CLASSES := $(patsubst %.java,%.class,$(SOURCES))
JC      = jikes
JFLAGS  = +E

%.class: %.java
	@rm -f $@
	$(JC) $(JFLAGS) $<

all: $(CLASSES)

clean:
	rm -f core *~ *.class
