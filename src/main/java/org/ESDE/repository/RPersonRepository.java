package org.ESDE.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class RPersonRepository implements PanacheRepository<RPerson> {

    public List<RPerson> findByKeyVal(String key, String val) {
        return find(key, val).list();
    }

}
