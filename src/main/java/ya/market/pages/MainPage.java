package ya.market.pages;

import io.qameta.allure.Step;
import ya.market.assertions.Assertion;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ya.market.helpers.Properties.pagesProperties;

/**
 * page object класс главной страницы
 * <p> автор: Роман Гудков</p>
 */
public class MainPage extends BasePage {

    /**
     * метод выполняет переход в каталог
     * <p> автор: Роман Гудков</p>
     *
     * @return ссылка на объект
     */
    @Step("Кликаем по кнопке --Каталог--")
    public MainPage goToCatalogMenu() {
        int counter = 0;
        boolean exists = false;
        while (!exists && counter < pagesProperties.loopCount()) {
            $x("//div[@data-zone-name='catalog']//span[text()='Каталог']").shouldBe(clickable).click();
            exists = $x("//*[@data-apiary-widget-id='/content/header/header/catalogEntrypoint/catalog']/../..").exists();
        }
        Assertion.assertTrue(exists, "переход в каталог не выполнен");
        return this;
    }

    /**
     * метод выбирает раздел каталога
     * <p> автор: Роман Гудков</p>
     *
     * @param section параметр названия раздела
     * @return ссылка на объект
     */
    @Step("Наводим курсор на раздел --{section}--")
    public MainPage hoverFromSectionCatalog(String section) {
        $x("//div[@data-zone-name='catalog-content']//ul[@role='tablist']").shouldBe(visible);
        $x("//*[@role='tabpanel']").shouldBe(visible);
        String sectionXpath = "//div[@data-zone-name='catalog-content'] //ul[@role='tablist']//span[text()]";
        checkSectionCatalogInDOM(section, sectionXpath);
        hoverRepeat(section, sectionXpath);
        return this;
    }

    /**
     * метод выполняет переход в подкатегорию каталога
     * <p> автор: Роман Гудков</p>
     *
     * @param category параметр названия подкатегории
     * @return ссылка на объект
     */
    @Step("Переходим в подкатегорию --{subcategory}--")
    public <T extends BasePage> T goToCategoryFromCatalog(String category, Class<T> typeNextPage) {
        openList();
        String sectionXpath = "//*[@role='heading']//li//a[text()]";
        checkSectionCatalogInDOM(category, sectionXpath);
        $x(sectionXpath).click();
        return typeNextPage.cast(page(typeNextPage));
    }

    /**
     * метод наводит курсор на раздел
     * <p> автор: Роман Гудков</p>
     *
     * @param section      параметр названия раздела
     * @param sectionXpath селектор xpath раздела
     */
    private void hoverRepeat(String section, String sectionXpath) {
        String nameSectionXpath = "//div[@role='heading']/a";
        int loop = pagesProperties.loopCount();
        boolean assertions = false;
        while (loop >= 0) {
            loop--;
            $$x(sectionXpath).find(text(section)).hover();
            if ($x(nameSectionXpath).getText().contains(section)) {
                assertions = true;
                break;
            }
            int size = $$x("//div[@data-apiary-widget-id='/content/header/header/catalogEntrypoint/catalog']" +
                    "//ul[@role='tablist']/li").size();
            $x("(//div[@data-apiary-widget-id='/content/header/header/catalogEntrypoint/catalog']" +
                    "//ul[@role='tablist']/li)[" + size + "]").hover();
        }
        Assertion.assertTrue(assertions, "курсор не попал на раздел --" + section + "--");
    }

    /**
     * метод разворачивает список подкатегорий
     * <p> автор: Роман Гудков</p>
     */
    private void openList() {
        int position = 1;
        while ($x("//*[@role='heading']//li/span").exists()) {
            if ($x("(//*[@role='heading']//li/span)[" + (position) + "]").exists()) {
                $x("(//*[@role='heading']//li/span)[" + (position) + "]").click();
                position++;
                continue;
            }
            break;
        }
        Assertion.assertTrue(!($$x("//*[@role='heading']//li/span/span").find(text("Ещё")).exists()),
                "не все списки с категориями развернуты");
    }

    /**
     * метод проверяет существование раздела
     * <p> автор: Роман Гудков</p>
     *
     * @param nameElement название раздела
     * @param xPath       селектор
     */
    @Step("Проверяем существование раздела")
    private void checkSectionCatalogInDOM(String nameElement, String xPath) {
        boolean checkPresence = $$x(xPath).find(text(nameElement)).exists();
        Assertion.assertTrue(checkPresence, "раздел --" + nameElement + "-- не найден");
    }
}