import com.acv.cloud.frame.util.MD5Util;
import org.junit.Test;

public class EncodeTest {

    @Test
    public void test() {
        //64a62d49999fb141855f51ed3d58b5d0
        //a11111111

        System.out.println(MD5Util.md5("a11111111"));

    }
}
