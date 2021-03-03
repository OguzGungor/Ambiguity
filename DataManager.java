import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class DataManager {
   
  private GameObjectManager gameObjectManager = GameObjectManager.GetGameObjectManagerInstance();
  private Block[][] currMap;
  private AudioInputStream[] sounds  = new AudioInputStream[9];
  private int mapID;
   
   DataManager(){
      
      
      
      try{
         AudioInputStream voice1;
         voice1 = AudioSystem.getAudioInputStream(new File("../musics/1.wav"));
         AudioInputStream voice2;
         voice2 = AudioSystem.getAudioInputStream(new File("../musics/2.wav"));
         AudioInputStream voice3;
         voice3 = AudioSystem.getAudioInputStream(new File("../musics/3.wav"));
         AudioInputStream voice4;
         voice4 = AudioSystem.getAudioInputStream(new File("../musics/4.wav"));//no
         AudioInputStream voice5;
         voice5 = AudioSystem.getAudioInputStream(new File("../musics/5.wav"));//yes
         AudioInputStream voice6;
         voice6 = AudioSystem.getAudioInputStream(new File("../musics/6.wav"));//tickTock
         AudioInputStream voice7;
         voice7 = AudioSystem.getAudioInputStream(new File("../musics/7.wav"));//casual
         AudioInputStream voice8;
         voice8 = AudioSystem.getAudioInputStream(new File("../musics/8.wav"));//menuMusic
         AudioInputStream voice9;
         voice9 = AudioSystem.getAudioInputStream(new File("../musics/9.wav"));
         sounds[0] = voice1;
         sounds[1] = voice2;
         sounds[2] = voice3;
         sounds[3] = voice4;
         sounds[4] = voice5;
         sounds[5] = voice6;
         sounds[6] = voice7;
         sounds[7] = voice8;
         sounds[8] = voice9;
      }catch(Exception ex)
      { }
      
   }
   
   public Block[][] getCurrMap(){
      
      return currMap;  
   }
   
   public AudioInputStream[] getSounds(){
      
      return sounds;
   }
   public void reset(){
//     currMap = new Block[gameObjectManager.getMaps()[0].getMap(mapID).length][gameObjectManager.getMaps()[0].getMap(mapID)[0].length];
//     for(int i = 0 ; i < gameObjectManager.getMaps()[0].getMap(mapID).length ; i++){
//       for(int j = 0 ; j < gameObjectManager.getMaps()[0].getMap(mapID)[0].length ; j++){ 
//         currMap[i][j] = new Block();
//         currMap[i][j].setFigureID(gameObjectManager.getMaps()[0].getMap(mapID)[i][j]);
//     }
//     }
     setMapType(mapID);
     
     
   }
//   public int[][] getFigure(int figureID){
//      return figureA;         
//   }
   public Figure getFigure(int figureID){
     return gameObjectManager.getFigure( figureID);
   }
   public Figure[] getFigures(){   
     return gameObjectManager.getFigures();
   }
   public void setCurrMap(Block[][] currentMap){
      currMap = currentMap;
   }
   
   public void setMapType(int mapID){
     this.mapID = mapID;
     currMap = new Block[gameObjectManager.getMaps()[0].getMap(mapID).length][gameObjectManager.getMaps()[0].getMap(mapID)[0].length];
     for(int i = 0 ; i < gameObjectManager.getMaps()[0].getMap(mapID).length ; i++){
       for(int j = 0 ; j < gameObjectManager.getMaps()[0].getMap(mapID)[0].length ; j++){ 
         currMap[i][j] = new Block();
         currMap[i][j].setFigureID(gameObjectManager.getMaps()[0].getMap(mapID)[i][j]);
//         if(i = 1 && j = 2)
//           currMap[i][j]
       }
     }
     if(mapID == 1){
       currMap[0][0].setPlayer(1);
       currMap[0][0].setFigureID(1);
       currMap[0][0].setEvent(1);
      // currMap[1][2].setEvent(2);
       currMap[1][4].setEvent(3);
       currMap[0][gameObjectManager.getMaps()[0].getMap(mapID)[0].length - 1].setPlayer(2);
       currMap[0][gameObjectManager.getMaps()[0].getMap(mapID)[0].length - 1].setFigureID(2);
       currMap[0][gameObjectManager.getMaps()[0].getMap(mapID)[0].length - 1].setEvent(1);
         currMap[gameObjectManager.getMaps()[0].getMap(mapID).length - 1 ][0].setPlayer(3);
         currMap[gameObjectManager.getMaps()[0].getMap(mapID).length - 1 ][0].setFigureID(3);
         currMap[gameObjectManager.getMaps()[0].getMap(mapID).length - 1 ][0].setEvent(1);
         currMap[gameObjectManager.getMaps()[0].getMap(mapID).length - 1][gameObjectManager.getMaps()[0].getMap(mapID)[0].length - 1].setPlayer(4);
         currMap[gameObjectManager.getMaps()[0].getMap(mapID).length - 1][gameObjectManager.getMaps()[0].getMap(mapID)[0].length - 1].setFigureID(4);
         currMap[gameObjectManager.getMaps()[0].getMap(mapID).length - 1][gameObjectManager.getMaps()[0].getMap(mapID)[0].length - 1].setEvent(1);
     }
   }
   public boolean setFigure(){
     return gameObjectManager.setFigure();
   
   }
   
}