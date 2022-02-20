package com.wuyu.controller;

import com.wuyu.entity.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Author:      wy
 * Create Date: 2022/2/16
 * Create Time: 22:21
 * Description:
 */
@Controller
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/toIndex")
    public String index(Model model){
        model.addAttribute("msg","hello,spring!");
        return "index";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(@ApiParam("用户名") String username){
        return username;
    }

    @ApiOperation("Hello控制类")
    @PostMapping("/hello2")
    @ResponseBody
    public User hello2(User user){
        return user;
    }
}
