# Geocoding Search App
Simple application which allows user to import list 
of cities and to get their coordinates from Google maps service 
(Google API key required) and also find if given point is in the city 
or to find 10 nearest cities.

## Getting Started

Project is located in github (), download it using Git or manually download zip file.

### Prerequisites

To run this application there are several things needed:

Java 8 (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Google API key (its free: https://developers.google.com/maps/documentation/geocoding/start#get-a-key)

Maven (https://maven.apache.org/)

### Installing&Running

Open command line or terminal, 
navigate to project folder, where project was Git-cloned (unpacked from zip file)

There should be _pom.xml_ file
```
/geocoding-search-app/
```

Run maven clean and install command

```
mvn clean install
```

Run Main class

```
java Main
```

Or do all these steps in IDE.

## Tests

Only two TDD tests were developed to help develop importer and parser.

## Built With

* [Hibernate](http://hibernate.org/) - ORM
* [Maven](https://maven.apache.org/) - Dependency Management
