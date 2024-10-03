package ya.market.helpers.interfase.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для доступа к глобальным переменным проперти
 * <p> автор: Роман Гудков</p>
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:src/main/resources/test.properties"})
public interface TestsProperties extends Config {

    /**
     * <p> метод возвращает тестовый url</p>
     * <p> автор: Роман Гудков</p>
     *
     * @return строка
     */
    @Key("ya.market")
    String urlTest();
}