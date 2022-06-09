package com.kaist.gachanono.gachanonoserver.service;

import java.util.List;

import com.kaist.gachanono.gachanonoserver.util.ExecUtil;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PeriodicCrawlingService {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value("${crawler.basepath}") 
    private String basepath;

    public String craw(int game_id, List<String> URLs, String tableSpecStr, String lineSpecStr, String cellSpecStr){
        String retval = "";

        try {
            String cmd = 
            String.format(
                "python3 -c \"from src.main.python.template import *; Craw(%s, '%s', '%s', '%s');\""
                , URLs.toString()
                , tableSpecStr
                , lineSpecStr
                , cellSpecStr
            );
            logger.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            logger.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }
}
