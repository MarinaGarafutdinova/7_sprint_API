package Objects;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static Courier getRandomCourier() {
        String login = "Igor" + RandomStringUtils.randomAlphabetic(3);
        String password = "Pas" + RandomStringUtils.randomAlphabetic(3);
        String firstName = "IgorIv" + RandomStringUtils.randomAlphabetic(3);

        return new Courier(login, password, firstName);
    }
}