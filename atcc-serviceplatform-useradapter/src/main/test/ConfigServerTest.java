import com.acv.cloud.UserApplication;
import com.acv.cloud.constants.ApplicationPropertiesConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: leo
 * @Date: 2019/5/9 17:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@WebAppConfiguration
public class ConfigServerTest {

    @Autowired
    ApplicationPropertiesConstants applicationPropertiesConstants;

    @Test
    public void test(){
        System.out.println("[--------------------------------------------------]"+applicationPropertiesConstants.getPhotoPath());
    }
}
