package com.kaist.gachanono.gachanonoserver.service;

import java.util.List;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PeriodicCrawlingService {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static PythonInterpreter intp;

    @Value("${crawler.basepath}") 
    private String basepath;

    public String craw(List<String> URLs, String tableSpecStr, String lineSpecStr, String cellSpecStr){
        String retval = "";

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


        // try {
        //     String cmd = String.format("python src/main/python/template.py %s %s %s %s");
        //     ExecUtil.getInstance().exec(cmd);
        //     retval = ExecUtil.getInstance().getStdOutString();
        //     logger.info("retval: {}", retval);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return retval;
    }
}
