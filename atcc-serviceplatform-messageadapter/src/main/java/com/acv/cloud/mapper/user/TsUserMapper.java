package com.acv.cloud.mapper.user;

import com.acv.cloud.models.sys.TsUser;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liyang on 2019/5/9 14:46.
 */
public interface TsUserMapper {
    /**
     * 根据用户手机号查询用户userId
     *
     * @param phoneNums
     * @return
     */
    TsUser findUserId(@Param("phoneNum") String phoneNums);
}
