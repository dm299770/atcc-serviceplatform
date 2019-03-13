import com.acv.cloud.SmartHomeApplication;
import com.acv.cloud.models.vehicle.TrUserVin;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class RedisTest {
    @Autowired
    RedisRepository redisRepository;

    @Test
    public void test() {
        TrUserVin vin = new TrUserVin();
        vin.setIsEffctive(0);
        vin.setCreateDate(new Date());
        vin.setUserId("1234567890");
        //redisRepository.set("testIntent1111", vin);
        Object o = redisRepository.get("testIntent1111");
        System.out.println("111111"+o);
    }
}
