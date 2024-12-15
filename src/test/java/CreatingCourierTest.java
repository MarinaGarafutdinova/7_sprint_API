import Objects.Courier;
import Objects.CourierSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


public class CreatingCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Статус-код и значений для полей /api/v1/courier")
    public void createCourierTest() {
        CourierSteps courierSteps = new CourierSteps();
        String login = "Igor"+ RandomStringUtils.randomAlphabetic(3);
        String password = "Pas"+ RandomStringUtils.randomAlphabetic(3);
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierSteps.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(201).and().body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание курьера без имени курьера")
    @Description("Статус-код и сообщение при создании курьера без имени курьера")
    public void creatingCourierWithoutFirstName() {
        CourierSteps courierClient = new CourierSteps();
        String login = "Igor"+ RandomStringUtils.randomAlphabetic(3);
        String password = "Pas"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(201).and().body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Статус-код и сообщение при создании двух курьеров с одинаковыми логинами")
    public void creatingTwoIdenticalLoginCouriers() {
        CourierSteps courierClient = new CourierSteps();
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier("Pechkin123", "Pas1233", "IIPechkin"));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(409).and().body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Статус-код  и сообщение при создании курьера без логина")
    public void creatingCourierWithoutLogin() {
        CourierSteps courierClient = new CourierSteps();
        String login = "";
        String password = "Pas"+ RandomStringUtils.randomAlphabetic(3);
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Статус-код и сообщение при создании курьера без пароля")
    public void creatingCourierWithoutPassword() {
        CourierSteps courierClient = new CourierSteps();
        String login = "Igor"+ RandomStringUtils.randomAlphabetic(3);
        String password = "";
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Статус-код и сообщение при создании курьера без логина и пароля")
    public void creatingCourierWithoutLoginAndPassword() {
        CourierSteps courierClient = new CourierSteps();
        String login = "";
        String password = "";
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }
}

