package org.ESDE.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class RPersonRepository implements PanacheRepository<RPerson> {

    public List<RPerson> findByKeyVal(String key, String val) {
        return find(key, val).list();
    }

    public List<RPerson> getPersonByFirstNameAndLastName(String firstName, String lastName){
        return find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName)
                .and("ln",lastName).map()).list();
    }

    public List<RPerson> getPersonWSalarayabove(double salary) {
        return find("salary > :s", Parameters.with(":s", salary)).list();
    }
}
