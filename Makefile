all: src

.PHONY: src
src:
	make -C src

clean:
	make -C src clean
