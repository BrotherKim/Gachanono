package com.kaist.gachanono.gachanonoserver.config;

import java.util.ArrayList;
import java.util.List;

import com.kaist.gachanono.gachanonoserver.service.PeriodicCrawlingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class CrawlingScheduler {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private PeriodicCrawlingService periodicCrawlingService;

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
    public String test() {
        List<String> URLs = new ArrayList<String>();
        URLs.add("'https://m.nexon.com/probability/705?language=ko'");
        String tableSpecStr = "table.MsoNormalTable";
        String lineSpecStr = "tr";
        String cellSpecStr = "td p.MsoNormal";
        return periodicCrawlingService.craw(URLs, tableSpecStr, lineSpecStr, cellSpecStr);
    }
}
