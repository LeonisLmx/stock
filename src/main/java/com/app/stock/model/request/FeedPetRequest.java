package com.app.stock.model.request;

import javax.validation.constraints.NotNull;

/**
 * Created by lmx
 * Date 2019/1/9
 */
public class FeedPetRequest extends VerifyRequest {

    @NotNull(message = "股票价格不能为空")
    private String price;
    @NotNull(message = "股票代码不能为空")
    private Long StockId;
    @NotNull(message = "股票名称不能为空")
    private String stockName;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getStockId() {
        return StockId;
    }

    public void setStockId(Long stockId) {
        StockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
