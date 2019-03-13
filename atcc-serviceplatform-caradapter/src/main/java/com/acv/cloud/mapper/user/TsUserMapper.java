package com.acv.cloud.mapper.user;

import com.acv.cloud.models.sys.TsUser;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息
 *
 * @author leo.
 */
public interface TsUserMapper {
    /**
     * 根据id查找用户
     *
     * @param userId 用户主键
     * @return
     */
    TsUser findById(@Param("userId") String userId);

    /**findByPhoneNum
     * 根据用户名查找用户
     *
     * @param phoneNum 用户电话号
     * @return
     */
    TsUser findEffctiveByPhoneNum(@Param("phoneNum") String phoneNum);

    /**
     * 保存用户
     *
     * @param bean 用户bean
     */
    void save(TsUser bean);


    /**
     * 删除用户
     *
     * @param userIds 用户主键数组
     */
    void delete(@Param("userIds") String[] userIds);


//    /**
//     * 更新用户（密码不用更新）
//     *
//     * @param bean 用户bean
//     */
//    void update(SysUser bean);


    /**
     * 修改用户密码
     *
     * @param phoneNum      用户手机号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(@Param("phoneNum") String phoneNum, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    /**
     * 重置用户密码
     *
     * @param phoneNum
     * @param newPassword
     */
    void reSetPassword(@Param("phoneNum") String phoneNum, @Param("newPassword") String newPassword);

}
