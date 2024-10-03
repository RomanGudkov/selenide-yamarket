package ya.market.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ya.market.assertions.Assertion;
import ya.market.helpers.LetterTranslate;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ya.market.helpers.Properties.pagesProperties;

/**
 * page object  класс страницы карточек товаров
 * <p> автор: Роман Гудков</p>
 */
public class ProductPage extends BasePage {

    /**
     * метод проверяет переход на страницу
     * <p> автор: Роман Гудков</p>
     *
     * @param head параметр названия страницы на которую перешли
     * @return ссылка на объект
     */
    @Step("Проверяем заголовок страницы, ожидаем --{head}--")
    public ProductPage checkingTransitionOnPage(String head) {
        boolean contains = $x("//h1[@data-auto='title']").shouldBe(visible).getText().equals(head);
        Assertion.assertTrue(contains, "переход в --" + head + "-- не выполнен");
        return this;
    }

    /**
     * метод работает с фильтром по критерию
     * <p> автор: Роман Гудков</p>
     *
     * @param head     параметр названия фильтра
     * @param criteria параметр критерия
     * @return ссылка на объект
     */
    @Step("Отмечаем --{criteria}-- в фильтре --{head}--")
    public ProductPage enumFilter(String head, String criteria) {
        $x("//div[@data-grabber='SearchFilters']").shouldBe(clickable, Duration.ofSeconds(5));
        openMoreParameters(head);
        boolean criteriaExist = searchAndCheckboxCriteria(criteria);
        waitStaticLoader();
        Assertion.assertTrue(criteriaExist, "критерий --" + criteria + "-- не найден");
        return this;
    }

    /**
     * метод проверки отображение товаров согласно установкам фильтра
     * <p> автор: Роман Гудков</p>
     *
     * @param criteria список критериев
     * @return ссылка на объект
     */
    @Step("Проверяем --{criteria}-- в нзвании товара}")
    public ProductPage checkParameterInProductCard(String criteria) {
        $x("//*[@data-auto='SerpList']/../..").shouldBe(visible);
        int numberBlock = 1;
        while (true) {
            $$x("//article[@data-auto='searchOrganic']/../..").last().scrollTo();
            if ($$x("//*[@data-auto='pagination-page']").find(text(String.valueOf(numberBlock + 1)))
                    .exists()) {
                $x("(//*[@id='/marketfrontSerpLayout'])[" + (numberBlock) + "]")
                        .should(visible, Duration.ofSeconds(5));
                numberBlock++;
                continue;
            }
            break;
        }
        checkingCriteriaInCard(criteria);
        return this;
    }

    /**
     * метод разворачивает скрытый список критериев
     * <p> автор: Роман Гудков</p>
     *
     * @param head параметр название фильта
     */
    @Step("Открываем весь список")
    private void openMoreParameters(String head) {
        SelenideElement selenideElement = $x("//h4[text()='" + head + "']" +
                "/ancestor::legend/following-sibling::div//div[@data-baobab-name='showMoreFilters']/button");
        boolean assertion = true;
        while (true) {
            if (selenideElement.getAttribute("aria-expanded").equals("false")) {
                selenideElement.click();
                continue;
            }
            if(!($x("//*[@data-test-id='virtuoso-scroller']").exists())){
               assertion = false;
            }
            break;
        }
        Assertion.assertTrue(assertion, "список критериев --" + head + "-- не развернут");
    }

    /**
     * метод отмечает критерий фильта
     * <p> автор: Роман Гудков</p>
     *
     * @param criteria название критерия
     * @return boolean
     */
    @Step("Выбираем параметр --{string}--")
    private boolean searchAndCheckboxCriteria(String criteria) {
        boolean criteriaExist = false;
        int letterNumber = 0;
        int counter = 0;
        while (counter <= pagesProperties.loopCount()) {
            $x("//div[@data-item-index='" + letterNumber + "']").hover();
            criteriaExist = $x("//div[@data-item-index='" + letterNumber + "']//span[text()='"
                    + criteria + "']").exists();
            SelenideElement selenideElement = $x("//div[@data-item-index='" + letterNumber
                    + "']//span[text()='" + criteria + "']/ancestor::label");
            if (criteriaExist && selenideElement.getAttribute("aria-checked").equals("true")) {
                return criteriaExist;
            }
            if (criteriaExist && selenideElement.getAttribute("aria-checked").equals("false")) {
                $x("//div[@data-item-index='" + letterNumber + "']//span[text()='" + criteria + "']").click();
                if (selenideElement.shouldBe(visible).getAttribute("aria-checked").equals("false")) {
                    counter++;
                    continue;
                } else {
                    return criteriaExist;
                }
            }
            letterNumber++;
        }
        Assertion.assertTrue(false, "клик по --" + criteria + "-- не выполнен");
        return criteriaExist;
    }

    /**
     * метоод ждет загрузку элемента страницы
     * <p> автор: Роман Гудков</p>
     */
    private void waitStaticLoader() {
        $x("//div[@data-auto='SerpStatic-loader']").shouldBe(visible);
        $x("//div[@data-auto='SerpStatic-loader']").shouldNotBe(visible);
    }

    /**
     * метод проверяет заголовок товара по выбранным в фильтре критериям
     * <p> автор: Роман Гудков</p>
     *
     * @param criteria параметр список критериев фильтра
     */
    private void checkingCriteriaInCard(String criteria) {
        ElementsCollection elements = $$x("//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']");
        List<String> collect = elements.stream()
                .map(SelenideElement::getText)
                .filter(e -> {
                    String webText = e.toLowerCase();
                    if (Pattern.compile("([a-z]+[а-я])|\\s([а-я][a-z]+)|([a-z]+[а-я][a-z]+)").matcher(webText).find()
                    ) {
                        webText = LetterTranslate.convertCyrillicToLatin(webText);
                    }
                    return !webText.contains(criteria.toLowerCase());
                })
                .collect(Collectors.toList());
        Assertion.assertTrue(collect.size() == 0, "из " + elements.size() + " заголовков "
                + collect.size() + " не соответствуют фильтру " + criteria + " это \n \t" + collect);
    }
}