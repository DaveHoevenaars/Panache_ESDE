package org.ESDE;

import io.quarkus.panache.common.Sort;
import org.ESDE.repository.RPerson;
import org.ESDE.repository.RPersonRepository;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.PathParam;
import java.util.List;

@RestController("/r")
public class RResource {

    private static final Logger LOG = Logger.getLogger(RResource.class);

    @Inject
    RPersonRepository personRepository;

    @PostMapping("/person")
    @Transactional
    public void addPerson(RPerson person) {
        LOG.info("Added person");
        Person.persist(person);
    }

    @GetMapping("/persons")
    public List<RPerson> getPeople() {
        LOG.info("Getting persons");
        return personRepository.listAll(Sort.by("firstName").and("lastName").ascending());
    }

    @GetMapping("/persons")
    public List<RPerson> getPeopleByKeyVal(@PathVariable("key") String key,
                                           @PathVariable("val") String val) {
        LOG.info("Getting persons by " + key + ", search param: " + val);
        return personRepository.findByKeyVal(key, val);
    }
}
