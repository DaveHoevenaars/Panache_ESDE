package org.ESDE;
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