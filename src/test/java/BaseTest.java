import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    static Faker faker;

    @BeforeAll
    public static void setBaseUrl () {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
        faker = new Faker();

    }
}
