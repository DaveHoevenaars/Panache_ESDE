package org.ESDE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.*;
import javax.transaction.Transactional;
import java.util.List;


@RestController

public class ExampleResource {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/person")
    @Transactional
    public void addPerson(Person person)
    {
        Person.persist(person);
    }

    @GetMapping("/person")
    public List<Person> getPeople()
    {
        return Person.listAll();
    }
}