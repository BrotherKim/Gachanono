package com.kaist.gachanono.gachanonoserver.service;

import java.util.ArrayList;
import java.util.List;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync 
public class PeriodicCrawlingService {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static PythonInterpreter intp;

    @Value("${crawler.basepath}") 
    private String basepath;

    /** 
     * Cron 표현식을 사용한 작업 예약
     **/
    // 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
    @Scheduled(cron = "1 0 0 * * *")  // 매일 00:00:01에 실행
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        logger.info( "schedule tasks using cron jobs - {}", now);
    }

    @Scheduled(cron = "0 4 15 * * *") // 매일 14:15:00에 실행
    public void test() {
        List<String> URLs = new ArrayList<String>();
        URLs.add("'https://m.nexon.com/probability/705?language=ko'");
        String tableSpecStr = "table.MsoNormalTable";
        String lineSpecStr = "tr";
        String cellSpecStr = "td p.MsoNormal";
        this.craw(URLs, tableSpecStr, lineSpecStr, cellSpecStr);
    }

    private String craw(List<String> URLs, String tableSpecStr, String lineSpecStr, String cellSpecStr){
        intp = new PythonInterpreter();
        intp.execfile("src/main/python/template.py");
        
        intp.exec(String.format("Craw(%s, '%s', '%s', '%s'"
                , URLs.toString()
                , tableSpecStr
                , lineSpecStr
                , cellSpecStr
                ));

        // PyFunction func = intp.get("crawler", PyFunction.class);
        // int a = 10, b = 20;
        // PyObject result = func.__call__(new PyInteger(a), new PyInteger(b));
        // System.out.println(result.toString());

        // return result.toString();

        return "";

    }
}
