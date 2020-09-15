package org.ESDE;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Person extends PanacheEntity {
    // No id needed, Panache creates own default ID for this entity
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static List<Person> getPersonByFirstName(String firstName){

        return Person.find("firstName",firstName).list();
    }
    // Note how you can map variables in your query to be able to do queries with multiple parameters
    public static List<Person> getPersonByFirstNameAndLastName(String firstName, String lastName){

        return Person.find("firstName = :fn and lastname = :ln", Parameters.with("fn",firstName)
                .and("ln",lastName).map()).list();
    }

}
//stopped at 6:41
