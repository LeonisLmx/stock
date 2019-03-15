package com.app.stock.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by lmx
 * Date 2019/3/12
 */
public class StockDetailRequest extends VerifyRequest{

    @NotNull(message = "股票ID不能为空")
    public List<String> stocks;

    public List<String> getStocks() {
        return stocks;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }
}
