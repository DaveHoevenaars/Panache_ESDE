package org.ESDE.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "rperson")
public class RPerson {
    public String firstname;
    public String lastname;
    public String id;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
