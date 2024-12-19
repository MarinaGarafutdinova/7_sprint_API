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

import static Objects.CourierGenerator.getRandomCourier;


public class CreatingCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test    //в этом тесте я использовала генератор данных для курьера,
    // а в остальных надо не все данные использовать, опэтому я не знаю, как там использовать генератор
    @DisplayName("Создание учетной записи курьера")
    @Description("Статус-код и значений для полей /api/v1/courier")
    public void createCourierTest() {
        CourierSteps courierStep = new CourierSteps();
        Courier courier = getRandomCourier();
        Response postRequestCreateCourier = courierStep.getPostRequestCreateCourier(courier);
        postRequestCreateCourier.then().log().all().assertThat().statusCode(201)
                .and().body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание курьера без имени курьера")
    @Description("Статус-код и сообщение при создании курьера без имени курьера")
    public void creatingCourierWithoutFirstName() {
        CourierSteps courierStep = new CourierSteps();
        String login = "Igor"+ RandomStringUtils.randomAlphabetic(3);
        String password = "Pas"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierStep.getPostRequestCreateCourier(new Courier(login, password, null));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(201)
                .and().body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Статус-код и сообщение при создании двух курьеров с одинаковыми логинами")
    public void creatingTwoIdenticalLoginCouriers() {
        CourierSteps courierStep = new CourierSteps();
        Response postRequestCreateCourier = courierStep.getPostRequestCreateCourier(new Courier("Pechkin123", "Pas1233", "IIPechkin"));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(409)
                .and().body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Статус-код  и сообщение при создании курьера без логина")
    public void creatingCourierWithoutLogin() {
        CourierSteps courierStep = new CourierSteps();
        String password = "Pas"+ RandomStringUtils.randomAlphabetic(3);
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierStep.getPostRequestCreateCourier(new Courier(null, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400)
                .and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Статус-код и сообщение при создании курьера без пароля")
    public void creatingCourierWithoutPassword() {
        CourierSteps courierStep = new CourierSteps();
        String login = "Igor"+ RandomStringUtils.randomAlphabetic(3);
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierStep.getPostRequestCreateCourier(new Courier(login, null, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400)
                .and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Статус-код и сообщение при создании курьера без логина и пароля")
    public void creatingCourierWithoutLoginAndPassword() {
        CourierSteps courierStep = new CourierSteps();
        String firstName = "IgorIv"+ RandomStringUtils.randomAlphabetic(3);
        Response postRequestCreateCourier = courierStep.getPostRequestCreateCourier(new Courier(null, null, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400)
                .and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }
}

