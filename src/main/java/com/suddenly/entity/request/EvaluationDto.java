package com.suddenly.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvaluationDto implements Serializable {

    private String orderNumber;

    private Double score;

    private String comment;

}
