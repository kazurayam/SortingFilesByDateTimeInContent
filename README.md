# [Java] Sorting text files in a directory by the date-time found in the content

- @date 2022/10/31
- @author kazurayam

This is my exercise in Java Programming.

## Problem to solve

One day in the "Katalon Community" forum I was asked [a question](https://forum.katalon.com/t/how-do-i-decode-a-base64-encoded-url-from-a-test-email-using-katalon/79979/18). The original poster asked as follows:

- He has several hundreds of text files in a directory. Every file was named with a postfix ".eml", which stands for "Email message", like `79edddc6-ce98-4eff-b8ea-414e392bce1f.eml`.
- The files have similar content like this:
```
X-Sender: "Do Not Reply" donotreply@anywhere.com
X-Receiver: xyz.com
MIME-Version: 1.0
From: "Do Not Reply" donotreply@anywhere.com
To: xyz.com
Date: 26 Oct 2022 14:43:15 -0700
Subject: Hello world, what is my URL?
Content-Type: text/html; charset=utf-8
Content-Transfer-Encoding: base64

aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbQ==
```
- He wants to get a list of file names, which are sorted by descending order of the "Date" header value in the file content, for example: `Date: 26 Oct 2022 14:43:15 -0700`.

## Solution

I developed a set of code in Java to solve the problem. I developed a class [`com.kazurayam.study20221030.PathComparableByContentEmailHeaderValue`](./src/main/java/com/kazurayam/study20221030/PathComparableByContentEmailHeaderValue.java) which implements the solution for the problem.

I developed an application [`com.kazurayam.study20221030.sample.Main2`](./src/main/java/com/kazurayam/study20221030/sample/Main2.java) that calls the core class. Also this project includes a JUnit5 test [`com.kazurayam.study20221030.sample.Main2Test`](src/test/java/com/kazurayam/study20221030/sample/Main2Test.java) that executes the `Main2` application.

## Description


You can execute the test by 
```
$ cd <projectDir>
$ ./gradlew test
```

The test will emit a report of test result at `<projectDir>/build/reports/tests/test/index.html`.

In there, you would find a StandardOutput from `Main2Test`, which looks like:

```
------------------------
com.kazurayam.study20221030.Main2Test
1	20221026_144315	79edddc6-ce98-4eff-b8ea-414e392bce1f.eml
2	20221026_120000	f503182a-d0ef-444d-a52b-658b5d10de81.eml
3	20221026_110000	test.eml
4	20221026_110000	ccc.eml
5	20221026_110000	bb.eml
6	20221026_110000	a.eml
```

In the 1st line, the string `20221026_144415` is picked up out of the file `79edddc6-ce98-4eff-b8ea-414e392bce1f.eml`. The string is the value of file content:
```
Date: 26 Oct 2022 14:43:15 -0700
```
The program converted the zoned time stamp value to a slightly different format. But the value is exactly what it is.

Please find the above output from `Main2Test` program demonstrates that it could solve the problem I was asked.

### Code design

Please read the source of class [`com.kazurayam.study20221030.PathComparableByContentEmailHeaderValue`](./src/main/java/com/kazurayam/study20221030/PathComparableByContentEmailHeaderValue.java) and surroundings.

You may find this code is too complicated. Well, the original problem "sort text files by the content" is a complicated one, which deserves this level of complexity in solution. I employed many Java coding techniques which I learned from many books for years. Enjoy.

## How to try

Download the project's zip file from

- https://github.com/kazurayam/SortingFilesByDateTimeInContent/releases/

Unzip it, then do:

```
$ cd SortingFilesByDateTimeInContent
$ ./gradlew test
```

This requires Java8 or newer installed in your environment.

## Javadoc

- https://kazurayam.github.io/SortingFilesByDateTimeInContent/api/

## Special note

The ver0.3.0 supported doing the following 2 things at a single run.

1. filter text files by the value of Email header contained in the file content. For example, select only files with a line of "To: xyz.com" while discarding files with "To: abc.com".
2. sort the files by the descending order of the lastModified timestamp of java.io.File

An example is here

- [Main3](src/main/java/com/kazurayam/study20221030/Main3.java)

Just for easier reference, I will quote a part of `Main3`:

```
...
List<IPathComparable> files =
    Files.list(this.dir)
        filter(p -> p.getFileName().toString().endsWith(".eml"))
        // filter files with "To: xyz.com" header
        .map(p -> new PathComparableByContentEmailHeaderValue(p, "To"))
        .filter(p -> p.getValue().matches("xyz.com"))
        // to sort by the lastModified timestamp
        .map(p -> new PathComparableByFileLastModified(p.get()))
        .sorted(reverseOrder())
        .collect(Collectors.toList());
...
```