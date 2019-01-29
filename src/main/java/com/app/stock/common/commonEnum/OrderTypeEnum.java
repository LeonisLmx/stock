package com.app.stock.common.commonEnum;

/**
 * Created by lmx
 * Date 2019/1/17
 */
public enum OrderTypeEnum {

    SUBJECT("SUBJECT",1),
    VIP("VIP",2);

    private String name;
    private int type;

    private OrderTypeEnum(String name,int type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static String getName(int index){
        for(OrderTypeEnum c:OrderTypeEnum.values()){
            if(c.getType() == index){
                return c.getName();
            }
        }
        return null;
    }
}
