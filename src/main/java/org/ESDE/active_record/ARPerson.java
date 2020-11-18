package org.ESDE.active_record;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import java.util.List;

@Entity(name = "arperson")
@NamedQuery(name = "ARPerson.getByLastname", query = "from arperson where lastname = :lastname")
public class ARPerson extends PanacheEntity {
    public String firstname;
    public String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fname) {
        this.firstname = fname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lname) {
        this.lastname = lname;
    }

    public static List<ARPerson> getPersonByFirstName(String firstName){
        return ARPerson.find("firstName",firstName).list();
    }
    // Note how you can map variables in your query to be able to do queries with multiple parameters
    public static List<ARPerson> getPersonByFirstNameAndLastName(String firstName, String lastName){

        return ARPerson.find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName)
                .and("ln",lastName).map()).list();
    }

    public static List<ARPerson> findByKeyVal(String key, String val) {
        return ARPerson.find(key, val).list();
    }

    public static List<ARPerson> getPersonByLname(String lastname) {
        System.out.println("Getting person by lname: " + lastname);
        return find("#ARPerson.getByLastname", Sort.by("firstname").ascending().and("lastname").ascending(), Parameters.with("lastname", lastname)).list();
    }
}
