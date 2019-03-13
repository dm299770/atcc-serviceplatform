package com.acv.cloud.controller.mpcv;

import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.models.mongdb.MpCv;
import com.acv.cloud.services.mpcv.MpCvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author:@leo.
 */
@RestController
@RequestMapping({"/mpCv"})
public class MpCvController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MpCvService mpCvService;
//    @Autowired
//    private MongoTemplate mongoTemplate;

    @LoginRequired
    @RequestMapping(value = "save")
    @ResponseBody
    public String save() {
        String cvId = "cvId";
        String mpId = "mpId";
        Double value = 12.1230;
        Date dataTime = new Date();
        Date samTime = new Date();
        // MpCv mpCv = new MpCv(UUID.randomUUID() + "", cvId, mpId, value, dataTime, samTime);
        //mongoTemplate.save(mpCv);
        return "";
    }
}
