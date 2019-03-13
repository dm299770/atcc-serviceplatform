package com.acv.cloud.services.mpcv;

import com.acv.cloud.models.mongdb.MpCv;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @description:查询历史抄表记录
 * @author:@leo.
 */
@Repository
public interface MpCvService {
    /**
     * 保存历史抄表示数
     * @param mpCv 历史抄表示数
     */
    void save(MpCv mpCv);

    /**
     * 更新抄表示数
     * @param cvId 表计cvId
     * @param dataTime 抄表时间
     * @param value 抄表示数
     */
    void updateValueByCvIdAndDataTime(String cvId, Date dataTime, String value);
}
