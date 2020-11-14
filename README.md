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

## Exercises
Go to https://code.quarkus.io/
Select the following dependencies:
- Quarkus extension for Spring Web API
- Hibernate ORM with Panache
- JDBC driver - H2

Specify the Group and Artifact ID of your project.

Download your project

# Active Record Pattern

In the project create a new Package called "active_record"

Create a class called "ARPerson" with the following specs:
```
@Entity
public class ARPerson {
  public String fname;
  public String lname; 
}
```
make it extend PanacheEntity

Specify the following methods: 
```
public static List<ARPerson> getPersonByFirstName(String firstName){
  return ARPerson.find("firstName",firstName).list();
}

public static List<ARPerson> getPersonByLastName(String lastName){
  return ARPerson.find("firstName",firstName).list();
}

public static List<ARPerson> getPersonByFirstNameAndLastName(String firstName, String lastName){
    return ARPerson.find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName).and("ln",lastName).map()).list();
}
```

Modify the Example resource class to look like this:
```
@RestController
public class ExampleResource {
    private static final Logger LOG = Logger.getLogger(ExampleResource.class);
    
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
        return ARPerson.listAll(Sort.by("firstName").and("lastName").ascending());
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
        return Person.getPersonByFirstNameAndLastName(name,lname);
    }
}
```

Play around with your program with postman!
Send post requests by setting the body to :
```
{
    "firstName": "YourFname",
    "lastName": "YourName"
}
```

# Repository pattern
Create another package called repository

create A class called 
 
