all: test

test: test-input test-output

# Run tests on original 101implementation:jdom
test-input:
	cd input; make test-xsltproc

# Run same tests on transformed implementation
test-output:
	cd output; make test-xsltproc

# git push convenience
push:
	git commit -a
	git push
