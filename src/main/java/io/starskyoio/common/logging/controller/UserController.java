package io.starskyoio.common.logging.controller;

import io.starskyoio.common.logging.annotation.CommonLog;
import io.starskyoio.common.logging.consts.Logs;
import io.starskyoio.common.logging.dto.UserDTO;
import io.starskyoio.common.logging.enums.LogAction;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @CommonLog(
            code = Logs.Codes.USER_LOG_USER_INFO,
            content = "添加用户",
            action = LogAction.ADD
    )
    @PostMapping
    public String add(@RequestBody UserDTO user) {
        return "ok";
    }

    @CommonLog(
            code = Logs.Codes.USER_LOG_USER_INFO,
            content = "更新用户",
            action = LogAction.UPDATE
    )
    @GetMapping
    public String update(String value) {
        if (value == null) {
            throw new RuntimeException("参数错误");
        }
        return "ok";
    }

    @CommonLog(
            code = Logs.Codes.USER_LOG_USER_INFO,
            content = "删除用户",
            action = LogAction.DELETE
    )
    @DeleteMapping
    public String delete(String value) {
        if (value == null) {
            throw new RuntimeException("参数错误");
        }
        return "ok";
    }


}
