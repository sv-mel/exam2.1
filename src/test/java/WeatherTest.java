import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
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
    @DisplayName("Get forecast by City (correct name)")
    public void shouldGetByCityPositive() {
        step("Get forecast for London", () -> {
              requestSpec
                .get("/weather?q=London")
                .then()
                .assertThat()
                .body("base", equalTo("stations"));
        });
    }
    @Test
    @DisplayName("Get forecast by City (random name)")
    public void shouldGetByRandomCity() {

        String city = faker.address().city();
                //"Abu Dhabi";
        System.out.println(city);
        step("Get forecast by City", () -> {
            requestSpec
                    .get("/weather?q={city}", city)
                    .then()
                    .assertThat()
                    .log().body()
                    .body("name", equalTo(city));
        });
    }

    @Test
    @DisplayName("Get forecast by correct ID")
    public void shouldGetByIdPositive() {
        requestSpec
                .get("/weather?id=499068")
                .then()
                .assertThat()
                .body("name", equalTo("Samara Oblast"));
    }
    @Test
    @DisplayName("Get forecast by incorrect ID")
    public void shouldGetByIdNegative() {
        requestSpec
                .get("/weather?id=499061")
                .then()
                .assertThat()
                .statusCode(404)
                .log().body()
                .body("message", equalTo("city not found"));
    }
    @Test
    @DisplayName("Get forecast by longitude and latitude")
    //longitude and latitude
    public void shouldGetByCoordinatePositive() {
        requestSpec
                .get("/weather?lat=48.8534&lon=2.3488")
                .then()
                .assertThat()
                .body("name", equalTo("Paris"));
    }
    @Test
    @DisplayName("Get forecast only by latitude")
    public void shouldGetByCoordinateNegative1() {
        requestSpec
                .get("/weather?lat=43")
                .then()
                .assertThat()
                .statusCode(400)
                .log().body()
                .body("message", equalTo("Nothing to geocode"));
    }

    @Test
    @DisplayName("Get forecast by incorrect coordinate")
    public void shouldGetByCoordinateNegative2() {
        requestSpec
                .get("/weather?lat=111111&lon=2222222")
                .then()
                .assertThat()
                .statusCode(400)
                .log().body()
                .body("message", equalTo("wrong latitude"));
    }
    @Test
    @DisplayName("Get humidity")
    public void shouldGetHumidityByCity() {

        int airHumidity = requestSpec
                .get("/weather?q=Rome")
                .then()
                .log().body()
                .spec(responseWeather)
                .body("name", equalTo("Rome"))
                .extract()
                .path("main.humidity");
        }

}