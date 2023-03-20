# Unauthorized Deliveries
![A1-logo](https://sun9-72.userapi.com/c851520/v851520353/196ae9/lVsG3Ikwf_w.jpg)
---
The application was created for implementing functionality for working with the posting deliveries system. 
You can parse from csv file or use crud http operations.

## Used technologies
* *Java 17*
* *Gradle*
* *Spring Boot 3*
* *Spring Data JPA*
* *Spring AOP*
* *Flyway*
* *PostgreSQL*
* *H2Database*
* *CSV*
* *JUnit 5*
* *Mockito*
* *Lombok*
* *Swagger*
* *Docker*

## How to run
* Install Java 17
* Install docker
* Change profile from env file(default: prod)
* Execute *docker_start.sh* in root project

## Endpoints
About endpoints and api, you can introduce by running the application and following to link
```
http://localhost:8080/swagger-ui.html
```
### DocumentHeader
A Java class representing the header of a document.
#### Fields:
* id: a unique identifier for the document header.
* postingNumber: a Long representing the posting number of the document.
* username: a User object representing the user who created the document.
* authorizeDelivery: a Boolean value indicating whether delivery of the document is authorized.
* contractDate: a LocalDate representing the date the contract was created.
* postingDate: a LocalDate representing the date the document was posted.

### Item
A Java class representing an item in a system.

#### Fields:
* id: a unique identifier for the item.
* description: a String representing the item's description.
* amount: an Integer representing the amount of the item.
* currency: a String representing the currency used for the item's price.
* status: a String representing the status of the item.

### Material
A Java class representing a material in a system.

#### Fields:
* id: a unique identifier for the material.
* item: an Item object representing the item that the material is associated with.
* posting: a Posting object representing the posting that the material is associated with.
* itemPosition: a Long representing the position of the item in the posting.
* quantity: an Integer representing the quantity of the material.
* measurementUnit: a String representing the unit of measurement used for the material.

### Posting
A Java class representing a posting in a system.

#### Fields:
* id: a unique identifier for the posting.
* header: a DocumentHeader object representing the header of the posting.

### User
A Java class representing a user in a system.

#### Fields:
* username: a String representing the user's username. It is a unique identifier for the user.
* application: a String representing the application that the user belongs to.
* job: a String representing the user's job title.
* department: a String representing the user's department.
* status: a String representing the status of the user.
* active: a Boolean value indicating whether the user is active in the system.