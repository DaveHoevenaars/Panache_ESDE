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

 /*  @GetMapping("/persons")
   public List<RPerson> getPeople() {
       LOG.info("Getting persons");
       return personRepository.listAll(Sort.by("firstName").and("lastName").ascending());
   }*/

    @GetMapping(value = "/persons")
    public List<RPerson> getPeopleByKeyVal(@RequestParam("key") String key,
                                           @RequestParam("val") String val) {

        LOG.info("Getting persons by " + key + ", search param: " + val);
        return personRepository.findByKeyVal(key, val);
    }

    @GetMapping(value = "/persons/name/{name}/{lname}")
    public List<RPerson> getPeopleByFirstName(@PathVariable("name") String name, @PathVariable("lname") String lname) {

        LOG.info("Getting persons by " + name + " " + lname);
        return personRepository.getPersonByFirstNameAndLastName(name, lname);
    }
}
