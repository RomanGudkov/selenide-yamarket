package ya.market.helpers.interfase.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для доступа к глобальным переменным проперти
 * <p> автор: Роман Гудков</p>
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:src/main/resources/page.properties"})
public interface PagesProperties extends Config {

    /**
     * <p> метод возвращает значение количества повторений цикла</p>
     * <p> автор: Роман Гудков</p>
     * @return число
     */
    @Key("loop.count")
    int loopCount();
}