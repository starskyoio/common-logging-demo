package io.starskyoio.common.logging.factory;


import io.starskyoio.common.logging.dto.LogInfo;

public interface LogInfoFactory {
    LogInfo create(String code, String content, String jsonArgs, String errorMsg, String level, String action, String pushType);
}
