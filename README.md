# panache_final project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `panache_final-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/panache_final-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/panache_final-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Setting up (pgAdmin4) db management

For easy Database creation download pgAdmin4 at: https://www.pgadmin.org/download/
If you havent already create a server group and a database named postgres with the fields: (int id, varchar firstname, varchar lastname)
You can do so by executing this statement: 

create table person
(
    id        integer,
    firstname varchar(20),
    lastname  varchar(20)
);

alter table person
    owner to postgres;
    
## Setup Postgress db 

Go to https://www.postgresql.org/download/ 
And install postgres

1. In the Database tool window (View | Tool Windows | Database), click the Data Source Properties icon The Data Source Properties icon.

2. In the Data Sources and Drivers dialog, click the Add icon (+) and select PostgreSQL.
3. At the bottom of the data source settings area, click the Download missing driver files link. Alternatively, you can specify user drivers for the data source. For more information about user drivers, see Add a user driver to an existing connection.

4. Specify database connection details. 
Name: PostgreSQL - postgres@localhost
Host: localhost
Port: 5432
User: postgres
Password: N/A
database: postgres
URL: jdbc:postgresql://localhost:5432/postgres

## Setting up the project:
Go to https://code.quarkus.io/
Select the following dependencies:
- Quarkus extension for Spring Web API
- Hibernate ORM with Panache
- JDBC driver - H2

Specify the Group and Artifact ID of your project.

Download your project

In the resources set the following values in the application.properties file:
```
quarkus.datasource.url=jdbc:postgresql://localhost:5432/postgres
quarkus.datasource.driver=org.postgresql.Driver
quarkus.datasource.username=postgres
quarkus.datasource.password=<<your password (can be empty)>>
quarkus.datasource.max-size=8
quarkus.datasource.min-size=2
quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.log.sql=true
```

Create a file called "import.sql" in your resources.

### Active Record Pattern

In the project create a new Package called "active_record"

Create a class called "ARPerson" with the following specs:
```
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "arperson")
public class ARPerson extends PanacheEntity {
    public String firstname;
    public String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fname) {
        this.firstname = fname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lname) {
        this.lastname = lname;
    }

    public static List<ARPerson> getPersonByFirstName(String firstName){

        return ARPerson.find("firstName",firstName).list();
    }
    // Note how you can map variables in your query to be able to do queries with multiple parameters
    public static List<ARPerson> getPersonByFirstNameAndLastName(String firstName, String lastName){

        return ARPerson.find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName)
                .and("ln",lastName).map()).list();
    }
}

```
Note that the entity class extends PanacheEntity!

Create a class called ARResource and specify it like this:

```
import org.ESDE.active_record.ARPerson;
import org.jboss.logging.Logger;
import io.quarkus.panache.common.Sort;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/ar")
public class ARResource {
    private static final Logger LOG = Logger.getLogger(ARResource.class);

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/person")
    @Transactional
    public void addPerson(ARPerson person)
    {
        LOG.info("Added person");
        ARPerson.persist(person);
    }

    @GetMapping("/persons")
    public List<ARPerson> getPeople()
    {
        LOG.info("Getting persons");
        LOG.info(ARPerson.listAll());
        return ARPerson.listAll();
    }

    @GetMapping("/persons/{id}")
    public ARPerson getPerson(@PathVariable("id") long id)
    {
        return ARPerson.findById(id);
    }
    @DeleteMapping("/person/{id}")
    @Transactional
    public void deletePerson(@PathVariable("id") long id)
    {
        ARPerson.delete("id",id);
    }

    @GetMapping("/persons/name/{name}")
    public List<ARPerson> getPersonWithFirstname(@PathVariable("name") String name) {
        return ARPerson.getPersonByFirstName(name);
    }

    @GetMapping("/persons/name/{name}/{lname}")
    public List<ARPerson> getPersonWithFirstNameAndLastName(@PathVariable("name") String name, @PathVariable("lname") String lname) {
        return ARPerson.getPersonByFirstNameAndLastName(name,lname);
    }
}
```
This class will function as our rest controller. The @RequestMapping("/ar") annotation specifies that the urls will all begin with "/ar/"
The other methods will function as post and get mappings for the person entity.

add the following insert statements to your "import.sql" file, so you have some test data:

```
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'George', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Michael', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Gob', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Buster', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Funke', 'Tobias');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Funke', 'Lindsay ');
```

Start the program by running "mvn quarkus:dev" from your console

Note that in your database schema a table called "arperson" has been created, with an id, firstname and lastname attribute.

Play around with your program with postman!

You can also filter your persons by specifying a key and a val
```
localhost:8080/ar/persons?key=firstname&val=Buster
```

Send post requests by setting the body of the request "localhost:8080/person" to:
```
{
    "firstName": "YourFname",
    "lastName": "YourName"
}
```

### Repository pattern
Create another package called repository

in this package create A class called RPerson:

```
package org.ESDE.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "rperson")
public class RPerson {
    public String firstname;
    public String lastname;
    public String id;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
```

Note: This time the Entity class does not extend the PanacheEntity
Instead, now a seperate class will be created. We will  name this one RPersonRepository:

```package org.ESDE.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import org.ESDE.active_record.ARPerson;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RPersonRepository implements PanacheRepository<RPerson> {

    public List<RPerson> findByKeyVal(String key, String val) {
        return find(key, val).list();
    }

    public List<RPerson> getPersonByFirstNameAndLastName(String firstName, String lastName){
        return find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName)
                .and("ln",lastName).map()).list();
    }
}
```
This class extends the PanacheRepository class and passes the RPerson class as a Generic. The class will be used to specify the Database calls.

Finally, create a class called RResource. This class will be used as the rest interface to the RPerson entity:

```
package org.ESDE;

import io.quarkus.panache.common.Sort;
import org.ESDE.repository.RPerson;
import org.ESDE.repository.RPersonRepository;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/r")
public class RResource {

    private static final Logger LOG = Logger.getLogger(RResource.class);

    @Inject
    RPersonRepository personRepository;

    @PostMapping("/person")
    @Transactional
    public void addPerson(RPerson person) {
        LOG.info("Added person");
        personRepository.persist(person);
    }

    @GetMapping(value = "/persons")
    public List<RPerson> getPeopleByKeyVal(@RequestParam("key") String key,
                                           @RequestParam("val") String val) {

        LOG.info("Getting persons by " + key + ", search param: " + val);
        if (key == null || val == null)
            return personRepository.listAll(Sort.by("firstname").and("lastname").ascending());
        else
            return personRepository.findByKeyVal(key, val);
    }

    @GetMapping(value = "/persons/name/{name}/{lname}")
    public List<RPerson> getPeopleByFirstName(@PathVariable("name") String name, @PathVariable("lname") String lname) {

        LOG.info("Getting persons by " + name + " " + lname);
        return personRepository.getPersonByFirstNameAndLastName(name, lname);
    }
}
```

alter the import.sql again and add the following insert statement to the existing ones:

```
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'George', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Michael', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Gob', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Buster', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Funke', 'Tobias');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Funke', 'lindsay ');

```
Quarkus should automatically adapt the changes from the code, so a reboot of the application should not be necessary. You can now make calls to 
"localhost:8080/r/* "

Congratulations! You just created your first functional Panache application!
 
