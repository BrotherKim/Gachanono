package com.kaist.gachanono.gachanonoserver.config.schedule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    @Autowired private PeriodicCrawlingService periodicCrawlingService;

    /**
     * Cron 표현식을 사용한 작업 예약
     **/
    // 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
    @Scheduled(cron = "0 4 15 * * *") // 매일 14:15:00에 실행
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        logger.info("schedule tasks using cron jobs - {}", now);
    }

    @Scheduled(cron = "1 0 0 * * *") // 매일 00:00:01에 실행
    public String kartriderCraw()throws IOException {
        List<String> URLs = new ArrayList<String>();
        URLs.add("'https://m.nexon.com/probability/705?language=ko'");
        String tableSpecStr = "table.MsoNormalTable";
        String lineSpecStr = "tr";
        String cellSpecStr = "td p.MsoNormal";
        String t = periodicCrawlingService.craw(
            1,
            URLs,
            tableSpecStr,
            lineSpecStr,
            cellSpecStr
        );

        String fileName = "src\\main\\resources\\static\\assets\\crawled\\1.csv";
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream output=new FileOutputStream(fileName,false);
        OutputStreamWriter writer=new OutputStreamWriter(output,"UTF-8");
        writer.write(t);
        writer.close();

        return t;
    }

    @Scheduled(cron = "1 0 0 * * *") // 매일 00:00:01에 실행
    public String mapleCraw()throws IOException {
        List<String> URLs = new ArrayList<String>();
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/RoyalStyle'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/MasterpieceRed'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/MasterpieceBlack'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/RoyalHairCoupon'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/RoyalPlasticSurgeryCo" +
            "upon'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/NormalDyeCoupon'"
        );
        URLs.add("'https://maplestory.nexon.com/Guide/CashShop/Probability/GoldApple'");
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/gameAddOption'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/gameSoulExtractor'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/WispsWonderBerry'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/LunaCrystalSweet'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/LunaCrystalDream'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/petGiantSkill'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/VeryGoodBox'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/MonsterIncubator'"
        );
        URLs.add(
            "'https://maplestory.nexon.com/Guide/CashShop/Probability/PremiumMonsterIncubat" +
            "or'"
        );
        String tableSpecStr = "table";
        String lineSpecStr = "tr";
        String cellSpecStr = "td";
        String t = periodicCrawlingService.craw(
            2,
            URLs,
            tableSpecStr,
            lineSpecStr,
            cellSpecStr
        );

        String fileName = "src\\main\\resources\\static\\assets\\crawled\\2.csv";
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream output=new FileOutputStream(fileName,false);
        OutputStreamWriter writer=new OutputStreamWriter(output,"UTF-8");
        writer.write(t);
        writer.close();

        return t;
    }
}
