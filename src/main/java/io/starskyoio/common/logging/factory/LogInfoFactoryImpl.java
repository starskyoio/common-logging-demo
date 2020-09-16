package io.starskyoio.common.logging.factory;

import io.starskyoio.common.logging.consts.Logs;
import io.starskyoio.common.logging.dto.LogInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogInfoFactoryImpl implements LogInfoFactory {
    @Override
    public LogInfo create(String code, String content, String jsonArgs, String errorMsg, String level, String action, String pushType) {
        LogInfo logInfo = new LogInfo();
        logInfo.setService("USER-SERVICE");
        logInfo.setServiceCn("用户服务");
        logInfo.setName(Logs.Names.USER_LOG);
        logInfo.setAppId("USER");
        logInfo.setContent(content);
        logInfo.setJsonArgs(jsonArgs);
        logInfo.setErrorMsg(errorMsg);
        logInfo.setCode(code);
        logInfo.setLevel(level);
        logInfo.setAction(action);
        logInfo.setPushType(pushType);
        logInfo.setTimestamp(LocalDateTime.now().toString().concat("+0800"));
        return logInfo;
    }
}
