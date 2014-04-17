#
# $Id: Makefile,v 1.4 2001/06/22 09:10:36 dahm Exp $
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
