# This a Makefile, an input file for the 'make' program.  For you 
# command-line and Emacs enthusiasts, this makes it possible to build
# this program with a single command:
#     gmake 
# (or just 'make' if you are on a system that uses GNU make by default,
# such as Linux.) You can also clean up junk files and .class files with
#     gmake clean
# Finally, you can run tests with
#     gmake check

# This is not an especially efficient Makefile, because it's not easy to
# figure out the minimal set of Java files that need to be recompiled.  
# So if any .class file does not exist or is older than its .java file,
# we just remove all the .class files, compile the main class, and 
# then compile everything in the plugin directory.  

# All source files
SRCS = $(wildcard *.java) $(wildcard tracker/*.java) $(wildcard util/*.java)

# Sources for the public entry points to the system.
MAIN_SRCS = track.java tracker/Testing.java \
	    util/Set2D.java util/QuadTree.java util/Testing.java \
	    util/Debugging.java

# All other Java sources (see also Project 1 Makefile).
OTHER_SRCS := $(filter-out $(MAIN_SRCS), $(SRCS))

# Flags to pass to Java compilations (include debugging info and report
# "unsafe" operations.
JFLAGS = -g -Xlint:unchecked

MAIN_CLASSES = $(MAIN_SRCS:.java=.class)
OTHER_CLASSES = $(OTHER_SRCS:.java=.class)

# Tell make that these are not really files.
.PHONY: clean default check check-util check-tracker

# By default, make sure all classes are present and check if any sources have
# changed since the last build.
default: $(MAIN_CLASSES) 

# If any class is missing, recompile.
$(MAIN_CLASSES): $(MAIN_SRCS) $(OTHER_SRCS)
	rm -f $(OTHER_CLASSES)
	javac $(JFLAGS) $(MAIN_SRCS)


# Run Tests.
check: check-util check-tracker

check-util: default
	java util.Testing

check-tracker: default
	java tracker.Testing

clean :
	rm -f *.class tracker/*.class util/*.class
	rm -f *~ tracker/*~ util/*~ 
