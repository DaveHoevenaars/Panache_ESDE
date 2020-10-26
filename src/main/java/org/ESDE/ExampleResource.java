package org.ESDE;
import org.jboss.logging.Logger;
import io.quarkus.panache.common.Sort;
import org.springframework.web.bind.annotation.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.*;
import javax.transaction.Transactional;
import java.util.List;


@RestController

public class ExampleResource {
    private static final Logger LOG = Logger.getLogger(ExampleResource.class);

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/person")
    @Transactional
    public void addPerson(Person person)
    {
        LOG.info("Added person");
        Person.persist(person);
    }

    @GetMapping("/persons")
    public List<Person> getPeople()
    {
        LOG.info("Getting persons");
        return Person.listAll(Sort.by("firstName").and("lastName").ascending());
    }

    @GetMapping("/persons/{id}")
    public Person getPerson(@PathVariable("id") long id)
    {
        return Person.findById(id);
    }
    @DeleteMapping("/person/{id}")
    @Transactional
    public void deletePerson(@PathVariable("id") long id)
    {
        Person.delete("id",id);
    }

    @GetMapping("/persons/name/{name}")
    public List<Person> getPersonWithFirstname(@PathVariable("name") String name) {
        return Person.getPersonByFirstName(name);
    }

    @GetMapping("/persons/name/{name}/{lname}")
    public List<Person> getPersonWithFirstNameAndLastName(@PathVariable("name") String name, @PathVariable("lname") String lname) {
        return Person.getPersonByFirstNameAndLastName(name,lname);
    }
}