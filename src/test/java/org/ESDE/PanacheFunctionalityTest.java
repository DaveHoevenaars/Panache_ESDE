package org.ESDE;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ESDE.active_record.ARPerson;
import org.ESDE.repository.RPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


@QuarkusTest
public class PanacheFunctionalityTest {

    @Test
    public void testPanacheMocking() {
        // Mocked classes always return a default value
        PanacheMock.mock(ARPerson.class);
        Mockito.when(ARPerson.count()).thenCallRealMethod();
        Assertions.assertEquals(4, ARPerson.count());
    }
    @Test
    public void testCounting() {
        // Now let's specify the return value
        PanacheMock.mock(ARPerson.class);
        Mockito.when(ARPerson.count()).thenReturn(23l);
        Assertions.assertEquals(23, ARPerson.count());
    }
}
