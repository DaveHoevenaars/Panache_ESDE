package org.ESDE.active_record;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

import java.util.List;

public class ARPerson extends PanacheEntity {
    public String fname;
    public String lname;

    public static List<ARPerson> getPersonByFirstName(String firstName){

        return ARPerson.find("firstName",firstName).list();
    }
    // Note how you can map variables in your query to be able to do queries with multiple parameters
    public static List<ARPerson> getPersonByFirstNameAndLastName(String firstName, String lastName){

        return ARPerson.find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName)
                .and("ln",lastName).map()).list();
    }
}
