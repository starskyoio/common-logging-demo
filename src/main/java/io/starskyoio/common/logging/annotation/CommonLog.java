package io.starskyoio.common.logging.annotation;

import io.starskyoio.common.logging.enums.LogAction;
import io.starskyoio.common.logging.enums.LogLevel;

import java.lang.annotation.*;

/**
 * 统一日志注解
 *
 * @author linus lee
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Documented
public @interface CommonLog {
    /**
     * 日志编码
     *
     * @return
     */
    String code();

    /**
     * 日志内容
     *
     * @return
     */
    String content();

    /**
     * 日志动作
     *
     * @return
     */
    LogAction action();


    /**
     * 日志级别
     *
     * @return
     */
    LogLevel level() default LogLevel.INFO;

    String pushType() default "inner";
}
