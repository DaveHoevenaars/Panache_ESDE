package org.ESDE.repository;

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
