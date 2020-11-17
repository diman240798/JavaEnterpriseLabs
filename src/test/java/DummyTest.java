import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import ru.sfedu.nanicky.shop.Main;


public class DummyTest {

    private static Logger LOG = LogManager.getLogger(Main.class);

    @Test
    public void test() {
        LOG.debug("Check tests can run");

        Object o = new Object();
        Assert.assertEquals(o, o);
        Assert.assertNotEquals(o, null);
        Assert.assertNotNull(o);
    }
}
