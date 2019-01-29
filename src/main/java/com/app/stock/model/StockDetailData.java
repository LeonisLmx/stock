package com.app.stock.model;

import java.math.BigDecimal;
import java.util.Date;

public class StockDetailData {
    private Long id;

    private String stockCode;

    private String stockName;

    private Double currentPrice;

    private String chg;

    private BigDecimal change;

    private BigDecimal open;

    private BigDecimal highest;

    private BigDecimal lowest;

    private BigDecimal curClose;

    private String volumn;

    private String totalValue;

    private String peRatioTtm;

    private String peRatioLyr;

    private Long totalCapital;

    private Long turnover;

    private String swing;

    private String turnoverRate;

    private String marketValue;

    private String mainBuy;

    private String plate;

    private String buyFive;

    private String buyTen;

    private String bugTwenty;

    private Integer isDelete;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getChg() {
        return chg;
    }

    public void setChg(String chg) {
        this.chg = chg;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHighest() {
        return highest;
    }

    public void setHighest(BigDecimal highest) {
        this.highest = highest;
    }

    public BigDecimal getLowest() {
        return lowest;
    }

    public void setLowest(BigDecimal lowest) {
        this.lowest = lowest;
    }

    public BigDecimal getCurClose() {
        return curClose;
    }

    public void setCurClose(BigDecimal curClose) {
        this.curClose = curClose;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public String getPeRatioTtm() {
        return peRatioTtm;
    }

    public void setPeRatioTtm(String peRatioTtm) {
        this.peRatioTtm = peRatioTtm;
    }

    public String getPeRatioLyr() {
        return peRatioLyr;
    }

    public void setPeRatioLyr(String peRatioLyr) {
        this.peRatioLyr = peRatioLyr;
    }

    public Long getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(Long totalCapital) {
        this.totalCapital = totalCapital;
    }

    public Long getTurnover() {
        return turnover;
    }

    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    public String getSwing() {
        return swing;
    }

    public void setSwing(String swing) {
        this.swing = swing;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getMainBuy() {
        return mainBuy;
    }

    public void setMainBuy(String mainBuy) {
        this.mainBuy = mainBuy;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBuyFive() {
        return buyFive;
    }

    public void setBuyFive(String buyFive) {
        this.buyFive = buyFive;
    }

    public String getBuyTen() {
        return buyTen;
    }

    public void setBuyTen(String buyTen) {
        this.buyTen = buyTen;
    }

    public String getBugTwenty() {
        return bugTwenty;
    }

    public void setBugTwenty(String bugTwenty) {
        this.bugTwenty = bugTwenty;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}