package com.suddenly.responseResult;

public enum ResultEnum implements IResultEnum {

    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    AUTH_ACCOUNT_LOGIN_ERROR(11, "账号登录失败"),
    AUTH_ACCOUNT_LOGOUT_ERROR(12, "账号登出失败"),
    QUERY_LOGIN_ACCOUNT_ERROR(13, "查询登录账号失败"),
    QUERY_ACCOUNT_INFO_FAILURE(14, "获取账号信息失败"),
    INTERCEPTOR_JUDGE_AT_PRESENT_USER_FAILURE(15, "拦截器判断当前用户失败"),
    ACCOUNT_NOT_LOGIN(16, "账号未登录"),
    AUTH_ACCOUNT_NOT_LOGIN(17, "账号未登录"),
    TOKEN_PARSE_FAILURE(18, "token解析失败"),
    SYSTEM_EXCEPTION(101,"系统异常"),
    BIND_EXCEPTION(102, "绑定异常"),
    QUERY_USER_FAILURE(1001, "查询用户失败"),
    ADD_USER_FAILURE(1002, "新增用户失败"),
    UPDATE_USER_FAILURE(1003, "修改用户失败"),
    DELETE_USER_FAILURE(1004, "删除用户失败"),
    QUERY_ORDER_FAILURE(1005, "查询订单失败"),
    CREATE_ORDER_EXIST(1006, "生成订单失败"),
    REVOKE_ORDER_FAILURE(1007, "撤销订单失败"),
    ONLY_CAN_REVOKE_NOT_CARRIED_OUT_ORDER(1008, "只可以撤销未执行订单"),
    ORDER_STATUS_DOES_NOT_EXIST(1009, "订单状态不存在"),
    QUERY_CREDIT_FAILURE(1010, "查询信用失败"),
    CREDIT_LOW(1011, "信用过低"),
    CARRIED_OUT_ORDER_FAILURE(1012, "执行订单失败"),
    ONLY_CAN_CARRY_OUT_NOT_CARRIED_OUT_ORDER(1013, "只可以执行未执行订单"),
    CARRY_OUT_EXCEPTION_ORDER_FAILURE(1014, "执行异常订单失败"),
    ONLY_CAN_CARRY_OUT_EXCEPTION_ORDER(1015, "只可以执行异常订单"),
    QUERY_HOTEL_FAILURE(1016, "查询酒店失败"),
    QUERY_ORDER_QUANTITY_FAILURE(1017, "查询订单数量失败"),
    QUERY_USER_CURRENT_HOTEL_ORDER_FAILURE(1018, "查询用户在当前酒店的订单失败"),
    QUERY_BOOKED_HOTEL_FAILURE(1019, "查询预定过的酒店失败"),
    EVALUATION_FAILURE(1020, "评价失败"),
    CAN_ONLY_EVALUATION_ALREADY_CARRY_OUT_ORDER(1021, "只能评价已完成订单"),
    UPDATE_HOTEL_INFO_FAILURE(1022, "更新酒店信息失败"),
    QUERY_HOTEL_TYPE_FAILURE(1023, "查询酒店房型失败"),
    UPDATE_HOTEL_TYPE_FAILURE(1023, "修改酒店房型失败"),
    DELETE_HOTEL_ROOM_FAILURE(1024, "删除酒店房型失败"),
    ADD_ROOM_TYPE_FAILURE(1025, "新增房型失败"),
    CARRY_OUT_ORDER_FAILURE(1026, "完成订单失败"),
    ONLY_CAN_CARRY_OUT_CARRIED_OUT_ORDER(1027, "只可以完成已执行订单"),
    QUERY_THAT_DAY_ORDER_FAILURE(1028, "查询当天订单失败"),
    RECHARGE_CREDIT_FAILURE(1029, "充值信用失败"),
    HOTEL_STAFF_EXISTED(1030, "已存在酒店工作人员"),
    QUERY_HOTEL_IS_EXISTED_STAFF_FAILURE(1031, "查询酒店是否存在工作人员失败"),



    ;

    private Integer code;
    private String message;

    private ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
