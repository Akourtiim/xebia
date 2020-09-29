package com.aak.xebia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class XebiaApplicationTests {

    @Autowired
    ApplicationContext ctx;

    @Test
    public void testStartTendeuseDefaultCase() {
        List<String> lines = new ArrayList<String>();
        lines.add("5 5");
        lines.add("1 2 N" ) ;
        lines.add("GAGAGAGAA" );
        lines.add("3 3 E");
        lines.add("AADAADADDA");

        Position position =new Position(5,5);
        TendeuseManager tendeuseManager = new TendeuseManager();


        List<String> finalPositions =tendeuseManager.calculerPostionTendeuse(lines,position);


        Assertions.assertEquals("1 3 N",finalPositions.get(0));
        Assertions.assertEquals("5 1 E",finalPositions.get(1));

    }

    @Test
    void contextLoads() {
    }

    /**
     * test if instruction file exits*/

     void loadInputFile(){

    }

     void moveRight(){

     }

     void moveLeft(){

     }



}
