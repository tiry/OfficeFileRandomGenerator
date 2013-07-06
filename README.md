
Simple command line utility to generate randomized office document.

## Why

When doing load tests, typically file import, it can be useful to have a lot of files.

Ideally, you don't want to inject the same file over and over :

 - because storage may de-duplicate the file and actually store it only once
 - because conversaion and processing associated to file injection may manage cache based on input file digest
 
You also want the files to be valid because having broken files may change the way the application behaves.

In addition, the text should be valid text so that full text indexing is correct.

This command line tool generate files :

 - uses MySpell dictionnaty to generate random text
 - uses [XDocReport](http://code.google.com/p/xdocreport/) for generating files based on a template

## Build :

    mvn clean install

NB : I used Nuxeo master pom because I am lazy, but there should be no dependencies on Nuxeo.

## Run

The build will generate a jar and a lib directory that contains all dependencies, MANIFEST adds the lib to the classpath so that you can directly be run

    java -jar target/ officeFileGenerator-5.6.jar  <inputFileName> <var1=xxx> <var2=yyy>

result will be output in the current directory will the same name as the input file but with _xxxxx_ where xxxxx is a random number

Arguments after the first one are interpreted :

 - key=value : as a key/value pair that will be part of the XDocReport rendering context (i.e. variable you can use inside the template)
 - output filename

## Batch generation

You can generate several files in one call.

For this, just use the loop arument :

    java -jar target/ officeFileGenerator-5.6.jar  <inputFileName> loop=100 

## Templating system

Input template should use XDocReport templating system : http://code.google.com/p/xdocreport/

Supported rendering engine is freemarker.
Supporter formats are : docx, odt

Built-in variables that can be used in the template file include :

 - idx : indexing when loop mode is used
 - random : a random number
 - randomText : some randomly generated text


