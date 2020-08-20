package org.autumn.webdemo.controller;

import org.autumn.autoconfiguration.aspectlog.AspectLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    public static Integer COUNT = 0;

    @RequestMapping("testA")
    @AspectLog
    public int testA() {
        return COUNT--;
    }


}
