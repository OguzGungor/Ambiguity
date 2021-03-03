import java.awt.*;
import java.util.Arrays;

public class GameObjectManager implements Locatable {
    private Map[] maps;
    private Figure[] figures;

    private static volatile GameObjectManager GameObjectManagerInstance = null;

    private GameObjectManager(){

        maps = new Map[1];
        int[][] shapeTemp = new int[4][4];
        figures= new Figure[12];
        shapeTemp[1][0] = 1;
        shapeTemp[2][0] = 1;
        shapeTemp[2][1] = 1;
        shapeTemp[2][2] = 1;

        //int t1[][]
        Figure figure1 = new Figure(shapeTemp,1);
        figures[0] = figure1;

        shapeTemp[1][1] = 1;

        Figure figure2 = new Figure(shapeTemp,2);
        figures[1] = figure2;


        shapeTemp[1][1] = 0;
        shapeTemp[2][3] = 1;

        Figure figure3 = new Figure(shapeTemp,3);
        figures[2] = figure3;


        shapeTemp[1][0] = 0;
        shapeTemp[1][1] = 1;

        Figure figure4 = new Figure(shapeTemp,4);
        figures[3] = figure4;

        shapeTemp[1][0] = 1;
        shapeTemp[2][0] = 0;

        Figure figure5 = new Figure(shapeTemp,5);
        figures[4] = figure5;

        shapeTemp[2][2] = 0;
        shapeTemp[2][3] = 0;

        Figure figure6 = new Figure(shapeTemp,6);
        figures[5] = figure6;

        shapeTemp[1][1] = 0;
        shapeTemp[2][1] = 0;
        shapeTemp[2][0] = 1;
        shapeTemp[3][1] = 1;
        shapeTemp[3][0] = 1;
        shapeTemp[3][2] = 1;

        Figure figure7 = new Figure(shapeTemp,7);
        figures[6] = figure7;

        shapeTemp[3][0] = 0;
        shapeTemp[2][1] = 1;

        Figure figure8 = new Figure(shapeTemp,8);
        figures[7] = figure8;

        shapeTemp[1][1] = 1;
        shapeTemp[2][1] = 0;
        shapeTemp[3][0] = 1;
        shapeTemp[3][2] = 0;

        Figure figure9 = new Figure(shapeTemp,9);
        figures[8] = figure9;

        shapeTemp[1][1] = 0;
        shapeTemp[2][1] = 1;
        shapeTemp[3][1] = 0;

        Figure figure10 = new Figure(shapeTemp,10);
        figures[9] = figure10;

        shapeTemp[1][0] = 0;
        shapeTemp[1][1] = 1;

        Figure figure11 = new Figure(shapeTemp,11);
        figures[10] = figure11;



        shapeTemp[2][2] = 1;

        Figure figure12 = new Figure(shapeTemp,12);
        figures[11] = figure12;


        //System.out.print("in gameobjectmanager after creation constructor \n");
        /*for(int i = 0; i<12; i++){
            System.out.println("figure"+i);
            figures[i].printImage();
        }*/
        figure1.rotate(false,true);
        figure1.rotate(true,false);



        maps[0] = new Map();


    }

    public static GameObjectManager GetGameObjectManagerInstance(){
        if(GameObjectManagerInstance == null){
            synchronized (GameObjectManager.class){
                if (GameObjectManagerInstance==null){
                    GameObjectManagerInstance = new GameObjectManager();
                }
            }
        }
        return GameObjectManagerInstance;
    }
    public boolean setFigure(){
      if(this.locatable.getSelectedFigureID() == -1)
        return false;
      this.locatable.setFigure(figures[this.locatable.getSelectedFigureID() -1 ]);
      return true;
    }
    public Figure[] getFigures(){
        return figures;
    }
    public Map[] getMaps(){
        return maps;
    }
    public Figure getFigure(int figureID){
    
      return figures[figureID -1];
    }
}