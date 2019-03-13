package com.acv.cloud.models.jsonBean.smartSpeaker.ali;


import com.acv.cloud.Enum.ExecuteCode;
import com.acv.cloud.models.jsonBean.smartSpeaker.TaskResponse;

import java.io.Serializable;

public class AliTaskResponse extends TaskResponse implements Serializable {

    public AliTaskResponse(String returnCode, String reply, ExecuteCode executeCode) {
        super(returnCode, reply, executeCode);
    }
}
