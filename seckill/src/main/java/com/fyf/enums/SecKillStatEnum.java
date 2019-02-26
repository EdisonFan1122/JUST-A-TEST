package com.fyf.enums;

/**
 * 使用枚举表表示常量
 * Created by Edison on 2018/10/10.
 */
public enum SecKillStatEnum {

    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    private int state;

    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    SecKillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SecKillStatEnum stateOf(int index){
        for (SecKillStatEnum state : values()){
            if (state.getState() == index){
                return state;
            }
        }
        return null;
    }

}
