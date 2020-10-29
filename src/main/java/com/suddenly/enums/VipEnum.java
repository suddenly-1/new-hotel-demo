package com.suddenly.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  VipEnum {

    ONE("1", "VIP1"),
    TWO("2", "VIP2"),
    THREE("3", "VIP3"),
    FOUR("4", "VIP4"),
    FIVES("5", "VIP5");

    private String key;
    private String value;

}
