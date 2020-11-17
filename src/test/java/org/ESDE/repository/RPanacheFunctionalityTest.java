package org.ESDE.repository;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.ESDE.repository.RPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


@QuarkusTest
public class RPanacheFunctionalityTest {
    @InjectMock
    RPersonRepository personRepository;

    @Test
    public void testPanacheMocking() {
        // Mocked classes always return a default value
        Mockito.when(personRepository.count()).thenCallRealMethod();
        Assertions.assertEquals(6, personRepository.count());
    }
    @Test
    public void testCounting() {
        // Now let's specify the return value
        Mockito.when(personRepository.count()).thenReturn(23l);
        Assertions.assertEquals(23, personRepository.count());
    }
    //ToDo: test for filtering localhost:8080/ar/persons?key=lastname&val=Funke
}
