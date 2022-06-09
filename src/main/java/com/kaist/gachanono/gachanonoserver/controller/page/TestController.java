package com.kaist.gachanono.gachanonoserver.controller.page;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        return periodicCrawlingService.craw(1, URLs, tableSpecStr, lineSpecStr, cellSpecStr);
    }

    @GetMapping("/kartriderCraw")
    public String kartriderCraw() throws IOException {
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

    @GetMapping("/mapleCraw")
    public String mapleCraw() throws IOException {
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