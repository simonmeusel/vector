# vector

![Screenshot](https://user-images.githubusercontent.com/16321843/40603919-aa0c5af4-625d-11e8-9617-3b03dc3459bd.png)

This is a simple [JavaFX](https://en.wikipedia.org/wiki/JavaFX) vector graphics application. It was created in the context of a school task, but was expanded a little further. This project is using [Apache Maven](https://maven.apache.org/) for project management, and [Travis CI](https://travis-ci.org/) for CI and CD. You may use this project as a boilerplate for your own [JavaFX](https://en.wikipedia.org/wiki/JavaFX) projects under the terms of the [MIT license](https://choosealicense.com/licenses/mit/).

## Features
* Drawing of shapes like rectangles, arrows and circles with custom colors
* Moving and editing of shapes
* Traversing the drawboard and zooming
* Export of SVG files

**Attention!** This program shouldn't be used in production. There currently is no way of opening exported files. If you want to edit [SVG](https://en.wikipedia.org/wiki/Scalable_Vector_Graphics)s, you may use the free and also open source [Inkscape](https://inkscape.org/en/).

## Usage

**If you just want to try out this program, you can download it from the [releases page](https://github.com/simonmeusel/vector/releases).**

Prerequisites:
* [Apache Maven](https://maven.apache.org/)
* [Source code of this repository](https://help.github.com/articles/cloning-a-repository/)
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) (not newer, see [javafx-maven-plugin](https://github.com/javafx-maven-plugin/javafx-maven-plugin/issues/287))

To start this program from source, run:

```bash
$ mvn jfx:run
```

To build this project into a jar file and gerate native files, run:

```bash
$ mvn jfx:build-jar
$ mvn jfx:build-native
```

## License

MIT License

Copyright (c) 2018 Simon Meusel

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
