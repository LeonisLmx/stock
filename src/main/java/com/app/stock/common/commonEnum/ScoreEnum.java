package com.app.stock.common.commonEnum;

/**
 * Created by lmx
 * Date 2018/12/28
 * 会员积分修改枚举类型
 */
public enum ScoreEnum {

    COMMENT(0,"COMMENT"),
    FEED_PARENT(1,"FEED"),
    RESET_PET(2,"RESET"),
    EXPEND(3,"EXPEND"),
    INCOME(4,"INCOME"),
    SUBJECT(5,"SUBJECT"),
    VIP(6,"VIP");

    private String name;
    private int type;

    private ScoreEnum(int type,String name){
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
        for(ScoreEnum c:ScoreEnum.values()){
            if(c.getType() == index){
                return c.getName();
            }
        }
        return null;
    }
}
