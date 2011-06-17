all: clean build

build:
	mkdir -p ../../../build/doc/user-guide
	latex --output-directory=../../../build/doc/user-guide --output-format=pdf user-guide.tex
	latex --output-directory=../../../build/doc/user-guide --output-format=pdf user-guide.tex
	
clean:
	rm -rf ../../../build/doc/user-guide

