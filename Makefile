# Makefile based on what we were given in Berkeley's CS61B course.

JFLAGS = -g -Xlint:unchecked -Xlint:deprecation

CLASSDIR = ../classes

# A CLASSPATH value that (seems) to work on both Windows and Unix systems.
# To Unix, it looks like ..:$(CLASSPATH):JUNK and to Windows like
# JUNK;..;$(CLASSPATH).
CPATH = "..:$(CLASSPATH):;..;$(CLASSPATH)"

# All .java files in this directory.
SRCS := $(wildcard *.java)

.PHONY: default check clean style

# As a convenience, you can compile a single Java file X.java in this directory
# with 'make X.class'
%.class: %.java
	javac $(JFLAGS) -cp $(CPATH) $<

# First, and therefore default, target.
default: sentinel

# 'make clean' will clean up stuff you can reconstruct.
clean:
	$(RM) *~ *.class sentinel

### DEPENDENCIES ###

sentinel: $(SRCS)
	javac $(JFLAGS) -cp $(CPATH) $(SRCS)
	touch sentinel
