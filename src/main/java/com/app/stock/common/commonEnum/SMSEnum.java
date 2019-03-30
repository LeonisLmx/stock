package com.app.stock.common.commonEnum;

/**
 * @Author mingxin Liu
 * @Date 2019-03-30 23:12
 * @Description //SMS 服务调用枚举类
 */
public enum SMSEnum {

    REGISTER(1,"SMS_162465758"),
    RESET_PASSWORD(2,"SMS_162522032"),
    CODE_LOGIN(3,"SMS_162199739");

    private int order;
    private String code;

    private SMSEnum(int order,String code){
        this.order = order;
        this.code = code;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getName(int index){
        for(SMSEnum c:SMSEnum.values()){
            if(c.getOrder() == index){
                return c.getCode();
            }
        }
        return null;
    }
}
