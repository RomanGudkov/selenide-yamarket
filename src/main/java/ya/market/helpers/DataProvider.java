package ya.market.helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Класс хранит входные параметры для теста
 * <p> автор: Роман Гудков</p>
 */
public class DataProvider {

    /**
     * метод возврвщает тестовые параметры
     * <p> автор: Роман Гудков</p>
     *
     * @return поток аргументов
     */
    public static Stream<Arguments> providerCheckingSmartphonesProduct() {
        return Stream.of(
                Arguments.of("Электроника", "Смартфоны", "Производитель", "Apple", "iPhone")
        );
    }
}
