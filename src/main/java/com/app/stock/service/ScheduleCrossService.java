package com.app.stock.service;

/**
 * Created by lmx
 * Date 2019/1/23
 */
public interface ScheduleCrossService {

    public void pullingData() throws Exception;

    public void achieveTradingDate();

    public void calcCorss();

    public void calcYangLine();

    public void longUnderLine();

    public void hammerLine();

    public void KDJ();

    public void MACD();

    public void BOLL();

    public void WR();

    public void V();

    // 海底捞月
    public void SEA();

    // 均线多头
    public void MORE();

    // 三红兵
    public void THREEARMY();
}
