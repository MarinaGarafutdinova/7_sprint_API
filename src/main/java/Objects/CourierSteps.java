package Objects;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    private static final String CREATE_COURIER = "api/v1/courier";
    private static final String LOGIN_COURIER = "/api/v1/courier/login";

    @Step("Создание курьера")
    public Response getPostRequestCreateCourier(Courier courier) {
        return given().log().all().filter(new AllureRestAssured()).header("Content-type", "application/json").body(courier).when().post(CREATE_COURIER);
    }

    @Step("Авторизация курьера")
    public Response getPostRequestCourierLogin(Courier courier) {
        return given().log().all().header("Content-type", "application/json").body(courier).when().post(LOGIN_COURIER);
    }
}