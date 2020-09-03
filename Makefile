
SEASRC	= sea/Coord.java sea/SeaSpot.java sea/Sea.java 
NAVSRC	= naval/Ship.java naval/Iowa.java naval/Navy.java naval/AutoNavy.java \
	  naval/OnePlayer.java naval/Naval.java naval/SoundLib.java

SRC	= $(SEASRC) $(NAVSRC)
PACKAGES = sea naval

APIDOC	= /mums/java/docs/api
HTMLDIR = /misc/projects/media/media.it/docs/spot/nw
DOCPATH	= $(HTMLDIR)/ProjectInfo/doc
INSTDIR	= $(HTMLDIR)/code
CLASSPATH = .:/afs/cell/home/guest/kaj/src/classes:/misc/projects/media/inst/lang/JDK/1.0/java/lib/classes.zip

MKDOC   = javadoc -version -author
JC	= javac
SHELL 	= /bin/tcsh -f

all: $(SRC:.java=.class)
	cp sea/*.class $(INSTDIR)/sea
	cp naval/*.class $(INSTDIR)/naval

.SUFFIXES: .java .class

.java.class:
	$(JC) $<

clean:
	find . -name "*.class" -exec rm {} \;

doc:
	env CLASSPATH=$(CLASSPATH) \
          $(MKDOC) -internal -imageurl $(APIDOC) -package java:$(APIDOC) \
		-d $(DOCPATH) $(PACKAGES)

cleandoc:
