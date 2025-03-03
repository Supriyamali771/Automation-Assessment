package day1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class APITest {

    private static final String BASE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @BeforeClass
    public void setup() {
        // Set the base URI for all API requests
        RestAssured.baseURI = BASE_URL;
    }

    @Test(priority = 1, description = "Verify the API response status is 200 OK")
    public void verifyResponseStatus() {
        Response response = given().get();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP status code 200 but found " + response.getStatusCode());
    }

   @Test(priority = 2, description = "Verify the API contains expected BPI currencies: USD, GBP, EUR")
    public void verifyBpiCurrencies() {
        Response response = given().get();

        // Extract BPI keys as a Set of Strings safely
        Map<String, Object> bpiMap = response.jsonPath().getMap("bpi");
        Set<String> currencyKeys = bpiMap.keySet();

        Assert.assertTrue(currencyKeys.contains("USD"), "USD currency is missing");
        Assert.assertTrue(currencyKeys.contains("GBP"), "GBP currency is missing");
        Assert.assertTrue(currencyKeys.contains("EUR"), "EUR currency is missing");
    }
}
