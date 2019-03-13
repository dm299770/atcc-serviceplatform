import com.acv.cloud.UserApplication;
import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.mapper.account.AccountMapper;
import com.acv.cloud.mapper.user.TsUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class lTest {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TsUserMapper tsUserMapper;

    @Test
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)
    public void deduct() {
        Date date = new Date();    //下单时间及修改时间
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = ft.format(date);
        String user_id = "e5792c05-fa1c-4ffa-9adb-aa66f2cd8863";
        String balance = "1";
        String id = UUID.randomUUID().toString();
        String moneyD = "100";
        Integer direction = -1;
        UserInfo userInfo = tsUserMapper.findUserPhoneNum(user_id);
        String comment = "662101959121503129";

        try {
            accountMapper.upadteBalance(user_id, balance, updateTime);
            throw new Exception("异常了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        accountMapper.saveChargeFlow(id, user_id, Double.valueOf(moneyD), direction, updateTime, userInfo.getPhoneNum(), "PowreSare", comment);
    }
}
