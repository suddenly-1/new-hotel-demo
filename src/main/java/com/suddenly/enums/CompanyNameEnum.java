package com.suddenly.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CompanyNameEnum {

    COMPANY_NAME_1(1, "企业名1"),
    COMPANY_NAME_2(2, "企业名2"),
    COMPANY_NAME_3(3, "企业名3");

    private Integer key;

    private String value;

    public static Map<Integer, String> getCompanyNameMap() {
        Map<Integer, String> companyNameMap = new HashMap<>();
        CompanyNameEnum[] values = CompanyNameEnum.values();
        for (CompanyNameEnum value : values) {
            companyNameMap.put(value.key, value.value);
        }
        return companyNameMap;
    }

}
