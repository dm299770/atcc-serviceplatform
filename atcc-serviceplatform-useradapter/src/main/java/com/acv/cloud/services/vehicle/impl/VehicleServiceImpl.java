package com.acv.cloud.services.vehicle.impl;


import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.constants.RedisConstants;
import com.acv.cloud.repository.redistemplate.RedisRepository;
import com.acv.cloud.services.user.TsUserService;
import com.acv.cloud.services.user.impl.TsUserServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.mapper.vehicle.VehicleMapper;
import com.acv.cloud.models.vehicle.TrUserVin;
import com.acv.cloud.services.vehicle.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 车辆管理 service
 *
 * @author leo
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 返回给APP端提示信息
     */
    public static final String FIND_VEHICLE_SUCCESS = "获取车辆信息成功";
    public static final String FIND_VEHICLE_FAIL = "获取车辆信息失败";
    public static final String SAVE_VEHICLE_SUCCESS = "绑定车辆信息成功";
    public static final String SAVE_VEHICLE_FAIL = "绑定车辆信息失败";
    public static final String VEHICLE_IS_EXIST = "车辆已被绑定";
    public static final String UNBIND_VEHICLE_SUCCESS = "解绑车辆信息成功";
    public static final String UNBIND_ISNOT_DEFAULT = "多辆绑定车辆不能解绑默认车辆";
    public static final String VEHICLE_ISNOT_EXIST = "车辆信息不存在";
    public static final String SET_DEFAULT_SUCCESS = "设置默认车辆成功";
    public static final String SET_DEFAULT_FAIL = "设置默认车辆失败";
    /**
     * 车辆绑定状态
     */
    public static final Integer binding = 1;
    public static final Integer unbinding = 0;


    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    RedisRepository redisRepository;

    @Override
    public JSONObject findBindVehicleByUser(String userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<TrUserVin> vehicles = vehicleMapper.findById(userId);
            jsonObject.put(AppResultConstants.MSG, FIND_VEHICLE_SUCCESS);
            jsonObject.put(AppResultConstants.DATA, vehicles);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
            logger.info("查询绑定车辆信息成功:" + vehicles.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询车辆信息失败" + e.getMessage());
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }

        return jsonObject;
    }

    @Override
    public JSONObject saveVehicle(TrUserVin trUserVin) {
        JSONObject jsonObject = new JSONObject();
        try {

            if (trUserVin != null) {//验证车辆不能为空
                //验证车牌唯一性
                TrUserVin vehicle = vehicleMapper.findVehicleByVin(trUserVin.getPlateNum());
                if (vehicle != null) {
                    jsonObject.put(AppResultConstants.MSG, VEHICLE_IS_EXIST);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                } else {
                    //查看默认车辆情况
                    List<TrUserVin> vehicles = vehicleMapper.findById(trUserVin.getUserId());
                    if (vehicles != null && vehicles.size() > 0) {
                        //该用户下有车辆存在
                        trUserVin.setDefaultVehicle(0);
                    } else {
                        //该用户无绑定车辆
                        trUserVin.setDefaultVehicle(1);
                    }
                    vehicleMapper.saveTrUserVin(trUserVin);
                    jsonObject.put(AppResultConstants.MSG, SAVE_VEHICLE_SUCCESS);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                    logger.info("车辆" + trUserVin.getPlateNum() + "添加成功");
                }

            } else {
                jsonObject.put(AppResultConstants.MSG, SAVE_VEHICLE_FAIL);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(trUserVin.getPlateNum() + "车辆信息绑定失败:" + e.getMessage());
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
        }

        return jsonObject;
    }

    @Override
    public JSONObject updateVehicle(UserInfo user, String vin , String code) {
        JSONObject jsonObject = new JSONObject();

        try {
            //验证车牌唯一性
            List<TrUserVin> vehicles = vehicleMapper.findById(user.getUserId());
            TrUserVin vehicle = vehicleMapper.findVehicleByVin(vin);

            if(!verifyCode(user.getPhoneNum(), RedisConstants.UNBINDCAR_HEAD,code)){
                //验证验证码是否正确
                jsonObject.put(AppResultConstants.MSG, TsUserServiceImpl.CODE_ERROR);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                return jsonObject;

            }else if (vehicle != null) {

                if (vehicle.getDefaultVehicle() == 1 && vehicles.size() > 1) {
                    //默认车辆,且绑定车辆数大于1
                    jsonObject.put(AppResultConstants.MSG, UNBIND_ISNOT_DEFAULT);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                    logger.info(vin + "解绑失败,多辆绑定车辆不能解绑默认车辆");
                    return jsonObject;

                } else {
                    //车牌存在,且不是默认车辆
                    vehicleMapper.unbindTrUserVin(user.getUserId(), vin);
                    jsonObject.put(AppResultConstants.MSG, UNBIND_VEHICLE_SUCCESS);
                    jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                    logger.info(vin + "解绑成功");
                    return jsonObject;
                }

            } else if(!vehicles.contains(vehicle)){
                //不能解绑非当前用户车辆
                jsonObject.put(AppResultConstants.MSG, UNBIND_VEHICLE_SUCCESS);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                return jsonObject;
            }else {
                //车辆不存在
                jsonObject.put(AppResultConstants.MSG, VEHICLE_ISNOT_EXIST);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                logger.info(vin + "信息不存在");
                return jsonObject;

            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            logger.error("解绑车辆异常" + e.getMessage());

        }


        return jsonObject;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public JSONObject setDefaultVehicle(String userId, String vin) {
        JSONObject jsonObject = new JSONObject();
        try {

            List<TrUserVin> vehicles = vehicleMapper.findById(userId);
            if (vehicles != null) {
                //用户车辆组存在
                vehicleMapper.clearDefaultVehicle(userId);//清除所有默认车辆
                vehicleMapper.setDefaultVehicle(userId, vin, binding);
                jsonObject.put(AppResultConstants.MSG, SET_DEFAULT_SUCCESS);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                logger.info(vin + "设置默认车辆成功");
            } else {
                //车辆不存在
                jsonObject.put(AppResultConstants.MSG, VEHICLE_ISNOT_EXIST);
                jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
                logger.info(vin + "信息不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            logger.error("设置默认车辆异常:" + e.getMessage());
        }

        return jsonObject;
    }

    private Boolean verifyCode(String phoneNum, String type , String code){
        Boolean flag = false;

        String reidsKey = String.format(type+":%s",phoneNum);
        Object codeCache = redisRepository.get(reidsKey);
        if(codeCache!=null){
            //找到验证码
            if(code.equals(codeCache.toString())){
                flag = true ;
            }
        }else{
            //未找到验证码
        }
        return flag;
    }
}
