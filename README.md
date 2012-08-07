# Headline 

An illustrative coupled transformation of a 101implementation

# Motivation

This 101contribution demonstrates a coupled software transformation for the 101implementation:xslt which exercises XML programming with XSLT. The coupled transformation is concerned with renaming element names and it operates at three levels: the instance level (XML documents), the type level (XML schemas, incidentally also XML documents), and the program level (XSLT programs, incidentally also XML documents). The transformation captures the element names under renaming in a level-specific manner. The transformation is implemented in Java with the help of the JDOM API.

# Features

* [Software transformation demo](http://101companies.org/index.php/101feature:Software_transformation_demo)

# Architecture

The 101implementation:xslt was snapshotted on 2 Aug 2012 in subdirectory "input". The transformed implementation is located in subdirectory "output". The implementation of the coupled transformation is located in subdirectory "transformation"; this includes the actual implementation and JUnit-base test cases. The JDOM API is distributed with this contribution in subdirectory "jdom".

# Usage

This contribution is best explored within Eclipse (tested with Juno). The transformed 101implementation:xslt can be recomputed by running the JUnit tests from subdirectory "transformation". The integrity of the original and the transformed implementation can be tested by running "make" at the top of the project.

# References

See the [YouTube video](http://www.youtube.com/watch?v=FtRuYVkENZU) for this effort.
