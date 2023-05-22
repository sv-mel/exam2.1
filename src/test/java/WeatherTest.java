import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class WeatherTest extends BaseTest {

    RequestSpecification requestSpec;
    ResponseSpecification responseWeather;

    @BeforeEach
    public void setUp() {
        requestSpec = RestAssured.given().param("appid", "5f1def4984d29e321d3383dca97a8169");
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200);
        responseWeather = specBuilder.build();
    }

    @Test
    public void shouldGetByCity() {
        requestSpec
                .get("/weather?q=London")
                .then()
                .assertThat()
                .body("base", equalTo("stations"));
    }
}