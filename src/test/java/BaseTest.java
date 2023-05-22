import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setBaseUrl () {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
    }
}
