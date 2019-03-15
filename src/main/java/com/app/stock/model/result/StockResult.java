package com.app.stock.model.result;

import java.util.List;

/**
 * Created by lmx
 * Date 2019/3/12
 */
public class StockResult {
    private String showapi_res_error;
    private String showapi_res_id;
    private Integer showapi_res_code;
    private StockBody showapi_res_body;

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public String getShowapi_res_id() {
        return showapi_res_id;
    }

    public void setShowapi_res_id(String showapi_res_id) {
        this.showapi_res_id = showapi_res_id;
    }

    public Integer getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(Integer showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public StockBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(StockBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public class StockBody{
        private Integer ret_code;
        private List<Object> list;

        public Integer getRet_code() {
            return ret_code;
        }

        public void setRet_code(Integer ret_code) {
            this.ret_code = ret_code;
        }

        public List<Object> getList() {
            return list;
        }

        public void setList(List<Object> list) {
            this.list = list;
        }
    }
}
