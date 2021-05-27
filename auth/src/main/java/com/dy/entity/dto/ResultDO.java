package com.dy.entity.dto;

import lombok.Data;

/**
 * @author shuaidong.li
 * @date 2019/7/16
 */
@Data
public class ResultDO {

    private boolean success;

    private String resultCode;

    private String message;
}
