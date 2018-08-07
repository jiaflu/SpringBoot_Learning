package com.ljf.web;

import org.springframework.web.bind.annotation.*;

/**
 * Created by lujiafeng on 2018/8/5.
 */

@RestController
public class HelloController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name) {
        return "hello " + name;
    }
}
