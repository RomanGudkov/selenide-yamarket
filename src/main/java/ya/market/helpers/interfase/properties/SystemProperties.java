package ya.market.helpers.interfase.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для доступа к глобальным переменным проперти
 * <p> автор: Роман Гудков</p>
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:src/main/resources/system.properties"})
public interface SystemProperties extends Config{

    /**
     * <p> метод возвращает параметр ширины экрана</p>
     * <p> автор: Роман Гудков</p>
     *
     * @return число
     */
    @Key("window.width")
    int windowWidth();

    /**
     * <p> метод возвращает параметр высоты экрана</p>
     * <p> автор: Роман Гудков</p>
     *
     * @return число
     */
    @Key("window.height")
    int windowHeight();
}