package io.starskyoio.common.logging.dto;

import lombok.Data;

@Data
public class LogInfo {
    private String service;
    private String serviceCn;
    private String name;
    private String appId;
    private String content;
    private String jsonArgs;
    private String errorMsg;
    private String code;
    private String action;
    private String level;
    private String pushType;
    private String timestamp;
}
