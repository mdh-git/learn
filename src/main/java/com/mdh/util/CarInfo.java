package com.mdh.util;

/**
 *
 * @author madonghao
 * @date 2018/10/16
 */
public class CarInfo {

    /**
     * 商业险保单号
     */
    private String bussniessNo;

    /**
     * 交强险单号
     */
    private String forceNo;

    /**
     * 单号类型（1保单号，2投保单号）
     */
    private Byte type;

    /**
     * 报价单号(太平洋使用)
     */
    private String quotationNo;

    /**
     * 车牌号
     */
    private String carNumber;

    private Integer deff;

    public String getBussniessNo() {
        return bussniessNo;
    }

    public void setBussniessNo(String bussniessNo) {
        this.bussniessNo = bussniessNo;
    }

    public String getForceNo() {
        return forceNo;
    }

    public void setForceNo(String forceNo) {
        this.forceNo = forceNo;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }


    public Integer getDeff() {
        return deff;
    }

    public void setDeff(Integer deff) {
        this.deff = deff;
    }

    public CarInfo(String bussniessNo, String forceNo, Byte type, String quotationNo, String carNumber, Integer deff) {
        this.bussniessNo = bussniessNo;
        this.forceNo = forceNo;
        this.type = type;
        this.quotationNo = quotationNo;
        this.carNumber = carNumber;
        this.deff = deff;
    }
}
