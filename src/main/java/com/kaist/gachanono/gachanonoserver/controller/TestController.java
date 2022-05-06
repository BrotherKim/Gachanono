package com.kaist.gachanono.gachanonoserver.controller;

import java.util.ArrayList;
import java.util.List;

import com.kaist.gachanono.gachanonoserver.service.PeriodicCrawlingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PeriodicCrawlingService periodicCrawlingService;

    @GetMapping("/test")
    public String test() {
        List<String> URLs = new ArrayList<String>();
        URLs.add("'https://m.nexon.com/probability/705?language=ko'");
        String tableSpecStr = "table.MsoNormalTable";
        String lineSpecStr = "tr";
        String cellSpecStr = "td p.MsoNormal";
        return periodicCrawlingService.craw(URLs, tableSpecStr, lineSpecStr, cellSpecStr);
    }

}