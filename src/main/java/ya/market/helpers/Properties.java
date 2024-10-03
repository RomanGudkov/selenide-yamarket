package ya.market.helpers;

import org.aeonbits.owner.ConfigFactory;
import ya.market.helpers.interfase.properties.PagesProperties;
import ya.market.helpers.interfase.properties.SystemProperties;
import ya.market.helpers.interfase.properties.TestsProperties;

/**
 * класс глобальных переменных проперти
 * <p> автор: Роман Гудков</p>
 */
public class Properties {

    /**
     * переменная интерфейса к проперти значения
     * <p> автор: Роман Гудков</p>
     */
    public static TestsProperties testsProperties = ConfigFactory.create(TestsProperties.class);

    /**
     * переменная интерфейса к проперти значения
     * <p> автор: Роман Гудков</p>
     */
    public static PagesProperties pagesProperties = ConfigFactory.create(PagesProperties.class);

    /**
     * переменная интерфейса к проперти значения
     * <p> автор: Роман Гудков</p>
     */
    public static SystemProperties systemProperties = ConfigFactory.create(SystemProperties.class);
}