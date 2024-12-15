import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import Objects.Order;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

@RunWith(Parameterized.class)

public class CreatingOrderTest {

    private final Order order;
    public CreatingOrderTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{{new Order("Иван", "Иванов", "ул.Зеленая 5", "Универститет", "89119075050", 1, "2024-12-20", "Позвоните заранее", new String[]{"BLACK"})},
                              {new Order("Петр", "Петров", "ул.Красная 6", "Университет", "89119075051", 3, "2023-12-20", "Вход со двора", new String[]{"GREY"})},
                              {new Order("Анна", "Каренина", "ул.Пионерская 7", "Спортивная", "89119075052", 7, "2023-12-21", "", new String[]{"BLACK", "GREY"})},
                              {new Order("Елена", "Прекрасная", "ул.Ленина 2", "Спортивная", "89119075053", 8, "2023-12-22", "", new String[]{})},};
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказов с различными наборами данных")
    public void checkCreateOrder() {
        Response response = given().log().all().header("Content-type", "application/json").body(order).when().post("/api/v1/orders");
        response.then().log().all().assertThat().and().statusCode(201).body("track", Matchers.notNullValue());
    }
}
