package com.aak.xebia;


import javafx.geometry.Pos;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class XebiaApplication {

    static Logger LOG = LoggerFactory.getLogger(XebiaApplication.class);



    public static void main(String[] args) {
        try {

            String fileName =args[0];
            File input = new File(fileName);

            if(input.exists()){
                List<String> instructions = getFileData(input);
                Position position = getPosition(instructions);
                calculerPostionTendeuse(instructions,position);



            }
            else{

                LOG.error("File not found");
            }

        }catch (IOException ioe){

            LOG.error("IO Exception on reading file data" +
                    ioe.getMessage());

        }catch (Exception e){
            LOG.error("Exception...." +
                    e.getMessage());

        }





    }


   public static  List<String> getFileData(File file) throws IOException {

        List<String> data = new ArrayList<>();
        data = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        return data;

    }

    private static Position getPosition(List<String> instructions) throws Exception{

        Position position = null;

        if(!instructions.isEmpty()){

            String[] splited = instructions.get(0).split("\\s+");
            if(splited.length==2){

                int pelouseMaxX = Integer.parseInt(splited[0]);
                int pelouseMaxY = Integer.parseInt(splited[1]);

                 position = new Position(pelouseMaxX,pelouseMaxY);





            }
            else {

                System.out.print("No information provided about the plateau upper-right coordinates");


            }

        }

        return position;



    }

    private static void calculerPostionTendeuse(List<String> lines,Position maxPosition ){
        List<Tendeuse> tendeusesWithUpdatedMouvement = new ArrayList<Tendeuse>();

        for(int i=1;i<lines.size();i=i+2){

            String[] currentPositions = lines.get(i).split("\\s+");
            Tendeuse tendeuse = new Tendeuse();


            Position position = new Position(Integer.parseInt(currentPositions[0]),
                    Integer.parseInt(currentPositions[1]));
            tendeuse.setPosition(position);
            tendeuse.setDirection(currentPositions[2].charAt(0));
            String[] stepsToExecute = lines.get(i).split("");


            for(char step:lines.get(i+1).toCharArray()){

                switch (step){

                    case Mouvement.DROITE:


                        startRotation(tendeuse.getDirection(),tendeuse,true);

                        break;

                    case Mouvement.GAUCHE:

                        startRotation(tendeuse.getDirection(),tendeuse,false);

                        break;
                    case Mouvement.AVANCER:


                        startWalk(tendeuse.getDirection().charValue(),tendeuse,maxPosition);


                        break;

                    default:break;






                }

            }



            tendeusesWithUpdatedMouvement.add(tendeuse);



        }


        tendeusesWithUpdatedMouvement.stream().forEach(t-> System.out.println(t.getPosition().getX()
                +" "+t.getPosition().getY()+" "
                +t.getDirection()));




    }

    private static void startRotation (final char actualDirection,Tendeuse t ,boolean isRightRotation){



        switch (actualDirection){

            case(Direction.EST):
                if (isRightRotation) {
                    t.setDirection(Direction.SUD);
                } else {
                    t.setDirection(Direction.NORD);
                }
                break;

            case(Direction.SUD):
                if (isRightRotation) {

                    t.setDirection(Direction.OUEST);
                } else {
                    t.setDirection(Direction.EST);
                }
                break;

            case(Direction.OUEST):
                if (isRightRotation) {
                    t.setDirection(Direction.NORD);
                } else {
                    t.setDirection(Direction.SUD);
                }
                break;

            case(Direction.NORD):
                if (isRightRotation) {
                    t.setDirection(Direction.EST);
                } else {
                    t.setDirection(Direction.OUEST);
                }
                break;


            default:break;
        }

    }

    private static void startWalk(char currentHeadDirection,Tendeuse t,Position maxPosition){


        Position walkingPosition = new Position();
        Position currentPosition = t.getPosition();

        switch (currentHeadDirection){


            case Direction.EST:
                walkingPosition.setX((currentPosition.getX() + 1)<=maxPosition.getX()?((currentPosition.getX()) + 1):
                        currentPosition.getX());
                walkingPosition.setY(currentPosition.getY());

                t.setPosition(walkingPosition);
                break ;

            case Direction.OUEST:
                walkingPosition.setX((currentPosition.getX() - 1)<0?currentPosition.getX():
                        (currentPosition.getX() - 1));
                walkingPosition.setY(currentPosition.getY());

                t.setPosition(walkingPosition);

                break ;
            case Direction.SUD:
                walkingPosition.setX(currentPosition.getX());
                walkingPosition.setY((currentPosition.getY() - 1)<0?currentPosition.getY():
                        (currentPosition.getY() - 1));

                t.setPosition(walkingPosition);

                break ;

            case Direction.NORD:
                walkingPosition.setX(currentPosition.getX());
                walkingPosition.setY((currentPosition.getY() + 1)<=maxPosition.getY()?(
                        currentPosition.getY() + 1):currentPosition.getY());

                t.setPosition(walkingPosition);

                break ;

            default:break;
        }

    }




}
