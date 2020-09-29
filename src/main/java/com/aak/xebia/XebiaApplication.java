package com.aak.xebia;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class XebiaApplication  {

    static Logger LOG = LoggerFactory.getLogger(XebiaApplication.class);



    public static void main(String[] args) {
        TendeuseManager tendeuseManager = new TendeuseManager();
        tendeuseManager.startTendeuse(args);





    }








}
