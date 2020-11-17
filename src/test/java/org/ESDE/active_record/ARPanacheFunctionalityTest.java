package org.ESDE.active_record;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ESDE.active_record.ARPerson;
import org.ESDE.repository.RPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;


@QuarkusTest
public class ARPanacheFunctionalityTest {

    @Test
    public void testPanacheMocking() {
        // Mocked classes always return a default value
        PanacheMock.mock(ARPerson.class);
        Mockito.when(ARPerson.count()).thenCallRealMethod();
        Assertions.assertEquals(6, ARPerson.count());
    }
    @Test
    public void testCounting() {
        // Now let's specify the return value
        PanacheMock.mock(ARPerson.class);
        Mockito.when(ARPerson.count()).thenReturn(23l);
        Assertions.assertEquals(23, ARPerson.count());
    }
    //ToDo: test for filtering localhost:8080/ar/persons?key=lastname&val=Funke

    @Test
    public void testGetByLname() {
        String lname = "lastname";
        String name = "Funke";
        PanacheMock.mock(ARPerson.class);
        Mockito.when(ARPerson.findByKeyVal(lname, name)).thenCallRealMethod();
        Mockito.when(ARPerson.find(lname, name)).thenCallRealMethod();
        Assertions.assertEquals(2, ARPerson.findByKeyVal(lname, name).size());
    }
}
