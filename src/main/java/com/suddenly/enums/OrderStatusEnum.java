package com.suddenly.enums;


import java.util.HashMap;
import java.util.Map;

public enum OrderStatusEnum {

    NOT_CARRIED_OUT(1, "未执行"),
    ALREADY_CARRIED_OUT(2, "已执行"),
    ALREADY_CARRY_OUT(3, "已完成"),
    ALREADY_REVOKE(4, "已撤销"),
    ABNORMAL(5, "异常");

    private Integer key;
    private String value;

    OrderStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Map<Integer, String> getOrderStatusMap() {
        Map<Integer, String> orderStatusMap = new HashMap<>();
        OrderStatusEnum[] values = OrderStatusEnum.values();
        for (OrderStatusEnum value : values) {
            orderStatusMap.put(value.key, value.value);
        }
        return orderStatusMap;
    }

}
