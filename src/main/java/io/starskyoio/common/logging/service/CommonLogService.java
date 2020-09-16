package io.starskyoio.common.logging.service;

import io.starskyoio.common.logging.dto.LogInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonLogService {

    /**
     * 添加日志
     *
     * @param logInfo
     */
    public void addLog(LogInfo logInfo) {
        System.out.println(logInfo);
    }

}
