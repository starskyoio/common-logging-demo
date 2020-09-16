package io.starskyoio.common.logging.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.starskyoio.common.logging.annotation.CommonLog;
import io.starskyoio.common.logging.dto.LogInfo;
import io.starskyoio.common.logging.enums.LogLevel;
import io.starskyoio.common.logging.factory.LogInfoFactory;
import io.starskyoio.common.logging.service.CommonLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final CommonLogService commonLogService;
    private final LogInfoFactory logInfoFactory;

    @PreDestroy
    public void preDestory() {
        executorService.shutdown();
    }

    @Around(value = "@annotation(io.starskyoio.common.logging.annotation.CommonLog)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String jsonArgs = getJsonArgs(joinPoint);
        CommonLog commonLogData = getDeclaredAnnotation(joinPoint);
        LogInfo logInfo = null;
        Object proceed;
        try {
            proceed = joinPoint.proceed();
            logInfo = logInfoFactory.create(commonLogData.code(), commonLogData.content(), jsonArgs, "", commonLogData.level().name(), commonLogData.action().name(), commonLogData.pushType());
        } catch (Throwable throwable) {
            logInfo = logInfoFactory.create(commonLogData.code(), commonLogData.content(), jsonArgs, throwable.getMessage(), LogLevel.ERROR.name(), commonLogData.action().name(), commonLogData.pushType());
            throw throwable;
        } finally {
            if (Objects.nonNull(logInfo)) {
                executorService.submit(new LogProccessTask(logInfo));
            }
        }
        return proceed;
    }

    /**
     * 日志处理任务
     */
    private class LogProccessTask implements Runnable {

        private LogInfo logInfo;

        private LogProccessTask(LogInfo logInfo) {
            this.logInfo = logInfo;
        }

        @Override
        public void run() {
            try {
                commonLogService.addLog(logInfo);
                log.info("添加统一日志成功：{}", logInfo);
            } catch (Exception e) {
                log.info("添加统一日志失败：{}", e.getMessage());
            }
        }
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    private CommonLog getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = targetClass.getMethod(joinPoint.getSignature().getName(), parameterTypes);
        return method.getDeclaredAnnotation(CommonLog.class);
    }

    /**
     * 参数转json字符串
     *
     * @param joinPoint
     * @return
     * @throws JsonProcessingException
     */
    private String getJsonArgs(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> paramMap = new LinkedHashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return objectMapper.writeValueAsString(paramMap);
    }


}
