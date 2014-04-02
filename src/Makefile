RM=rm -fv
JAVAC=javac
JFLAGS=-Xlint:unchecked
# Any supporting java files that don't implement main()
CLASSES=Song.class Transformer.class Visualizer.class SequentialDiscreteFT.class SequentialFFT.class
DEP_CLASSES=WavFile/WavFileException.class WavFile/WavFile.class

all: $(DEP_CLASSES) $(CLASSES)
#singleFFT singleDFT concurrentFFT concurrentDFT
	

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

clean:
	-$(RM) $(CLASSES) $(DEP_CLASSES)
