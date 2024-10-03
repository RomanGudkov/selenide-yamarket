package ya.market.test.search.catalog.pages;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ya.market.pages.MainPage;
import ya.market.pages.ProductPage;
import ya.market.test.TestBaseSetup;

import static com.codeborne.selenide.Selenide.open;
import static ya.market.helpers.Properties.testsProperties;

/**
 *  Класс теста
 *  <p> автор: Роман Гудков</p>
 */
public class PagesTest extends TestBaseSetup {

    /**
     * метод задает шаги тестирования
     * <p> автор: Роман Гудков</p>
     */
    @Feature("Проверка результатов поиска")
    @DisplayName("Поиск товара с параметрами фильтра")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("ya.market.helpers.DataProvider#providerCheckingSmartphonesProduct")
    public void stepNexPageTest(String section, String category, String criteria, String brand, String model) {
        open(testsProperties.urlTest(), MainPage.class)
                .goToCatalogMenu()
                .hoverFromSectionCatalog(section)
                .goToCategoryFromCatalog(category, ProductPage.class)
                .checkingTransitionOnPage(category)
                .enumFilter(criteria, brand)
                .checkParameterInProductCard(model);
    }
}