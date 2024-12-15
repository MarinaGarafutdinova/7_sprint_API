import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import Objects.Courier;
import Objects.CourierSteps;

public class CreatingLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Курьер авторизирован")
    @Description("Авторизации курьера с корректным логином и паролем")
    public void checkCreatingLoginTest() {
        CourierSteps courierClient = new CourierSteps();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("Pechkin123", "Pas1233", "IIPechkin"));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(200).and().body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Курьер авторизирован без логина")
    @Description("Авторизации курьера без логина")
    public void checkWithoutLoginAuthorization() {
        CourierSteps courierClient = new CourierSteps();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("", "Pas1233"));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер авторизирован без пароля")
    @Description("Авторизации курьера без пароля")
    public void checkVerificationWithoutPasswordAuthorization() {
        CourierSteps courierClient = new CourierSteps();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("Pechkin123", ""));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер авторизирован под некорректным логином")
    @Description("Авторизации курьера в системе под несуществующим пользователем")
    public void checkAuthorizationUnderIncorrectLogin() {
        CourierSteps courierClient = new CourierSteps();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("Iv", "Pas1233"));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(404).and().body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер авторизирован под некорректным паролем")
    @Description("Авторизации курьера с неверным паролем")
    public void checkEnteringInvalidPassword() {
        CourierSteps courierClient = new CourierSteps();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("Pechkin123", "Pas321"));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(404).and().body("message", Matchers.is("Учетная запись не найдена"));
    }
}
