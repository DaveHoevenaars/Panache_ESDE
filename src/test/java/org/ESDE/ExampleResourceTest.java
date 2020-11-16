package org.ESDE;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ESDE.active_record.ARPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Test
    public void testHelloEndpoint() {
        PanacheMock.mock(ARPerson.class);

        Mockito.when(ARPerson.count()).thenCallRealMethod();
        Assertions.assertEquals(4, ARPerson.count());

       /* given()
          .when().get("/persons/1")
          .then()
             .statusCode(200)
             .body(is("hello"));
*/
    }

}