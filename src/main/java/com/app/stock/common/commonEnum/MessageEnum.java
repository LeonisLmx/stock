package com.app.stock.common.commonEnum;

/**
 * Created by lmx
 * Date 2018/12/24
 */
public enum MessageEnum {
    REGISTER("注册",0),
    LOGIN("登录",1),
    FIND_PASSWORD("找回密码",2);

    private String name;
    private int type;

    private MessageEnum(String name,int type){
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
        for(MessageEnum c:MessageEnum.values()){
            if(c.getType() == index){
                return c.getName();
            }
        }
        return null;
    }
}
