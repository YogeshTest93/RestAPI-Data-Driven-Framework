package Test;

import Base.base;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class OpenWeatherMapAPITest extends base {

    @Test(priority = 2, dataProvider = "cityData")
    public void testGet5DayForecastForCity(String cityName) {
        RestAssured.baseURI = getBaseUrl();
        Response response = given().param("q", cityName).param("appid", getApiKey()).when().get("/data/2.5/forecast");
        response.then().statusCode(200).body("city.name", equalTo(cityName)).body("list.size()", equalTo(40));
    }

    @Test(priority = 1, dataProvider = "cityData")
    public void testGetCurrentWeatherForCity(String cityName) {
        RestAssured.baseURI = getBaseUrl();
        Response response = given().param("q", cityName).param("appid", getApiKey()).when().get("/data/2.5/weather");
        response.then().statusCode(200).body("name", equalTo(cityName)).body("main.temp", notNullValue());
    }

    @Test(priority = 4)
    public void testInvalidAPIKey() {
        RestAssured.baseURI = getBaseUrl();
        Response response = given().param("q", "London").param("appid", "INVALID_API_KEY").when().get("/data/2.5/weather");
        response.then().statusCode(401).body("message",
                anyOf(containsString("Invalid API key. Please see http://openweathermap.org/faq#error401"),
                        containsString("Invalid API key. Please see https://openweathermap.org/faq#error401")));
    }

    @Test(priority = 3, dataProvider = "cityData")
    public void testInvalidCityName(String cityName) {
        RestAssured.baseURI = getBaseUrl();
        Response response = given().param("q", cityName).param("appid", getApiKey()).when().get("/data/2.5/weather");
        response.then().statusCode(404).body("message", equalTo("city not found"));
    }
}