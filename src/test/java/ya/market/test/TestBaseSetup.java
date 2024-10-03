package ya.market.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.BeforeAll;
import ya.market.allure.selenide.CustomAllureSelenide;

import static ya.market.helpers.Properties.systemProperties;

/**
 * Базовый тестовый класс
 * <p> автор: Роман Гудков</p>
 */
public class TestBaseSetup {

    /**
     * метод задает стратегию загрузки драйвера
     * <p> автор: Роман Гудков</p>
     */
    @BeforeAll
    public static void setUp() {
        Configuration.pageLoadStrategy = "none";
        String windowSize = systemProperties.windowWidth() + "x" + systemProperties.windowHeight();
        Configuration.browserSize = windowSize;
        SelenideLogger.addListener("AllureSelenide", new CustomAllureSelenide()
                .screenshots(true)
                .savePageSource(false)
                .includeSelenideSteps(true));
    }
}