package com.example.feignmockserver2.bo;

import lombok.Data;

@Data
public class RcsApplyRequest {
    private String applyNo;
    private String gateId;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }
}
