import java.util.Timer;
import java.util.TimerTask;
import java.awt.Image;
// packages???

public class GameManager {
  
  public static final int TIME = 0 ,
                          CASUAL = 1 ,
                          VERSUS = 2,
                          LIMIT0 = -1, 
                          LIMIT1 = 0 , 
                          LIMIT2 = 1,
                          LIMIT3 = 2;
  private MapManager mapManager;
  private SoundManager soundManager;
  ////
  private DataManager dataManager;
  ////
  private int turn;
  private int round;
  private int [] players = new int[4];
  private int [] figureContainer;
  private int [] [] figureContainers;
  private int [][] eventContainer;
  private int[] specifications;
  private int score;
  private int time;
  private int moveTime;
  private int moveCount;
  private int selectedFigureID;
  private int selectedEventID;
  private Timer timer;
  private TimerTask timerStart;
  private boolean hurry = false;
  
  
  
  public GameManager() {
      round = 0;
    selectedEventID = -1;
    mapManager = new MapManager();
    soundManager = new SoundManager();
    ////
    dataManager = new DataManager();
    ////
    soundManager.setSounds(dataManager.getSounds());
    figureContainer = new int[12];
    eventContainer = new int[4][8];
    for(int i = 0 ; i < 4 ; i++){
        for(int j = 1; j < 4 ; j++)
            eventContainer[i][j] = j  ;        
    }
        
    
    for( int i = 0; i < 12; i++)
      figureContainer[i] = i + 1;
     turn = 0;
     figureContainers = new int[4][12];
    for(int i = 0; i < 4 ; i++){
//      System.out.println("players[" + i + "] = " + players[i]); 
      for( int j = 0; j < 12; j++)
      figureContainers [i][j] = figureContainer[j];
    }
     
    score = 0;
    time = 0;
    moveTime = 0;
    moveCount = 0;
    selectedFigureID = -1;// NULL?
    timer = new Timer();
    timerStart = new TimerTask(){
      @Override
      public void run(){        
        if(specifications[0] == 0){
          if(time == 10){
            soundManager.stopBackgroundSound();
            soundManager.playTickTockSound();
          }
          
          time--;
        }
          moveTime++;           
            
      }
    };
  }
  public void startGame(){
    timer = new Timer();
    timerStart = new TimerTask(){
      @Override
      public void run(){
        if(specifications[0] == 0){
          if(time == 10){
            soundManager.stopBackgroundSound();
            soundManager.playTickTockSound();
          }
          
          time--;
        }
          moveTime++; 
          
            
      }
    };
    timer.schedule(timerStart, 0, 1000);      
    
    if(specifications[0] == 0){
      soundManager.playBackgroundSound();
      hurry = false;
    }
    else
      soundManager.playBackgroundSound2();
  }
  public int isGameOver(boolean moved){
    //-1 for lose
    //0 for maintain
    //1nfor win
    if(specifications[0] == 0){
      if(time <= 0){
        soundManager.stopBackgroundSound();
        timerStart.cancel ();
        timer.purge ();
        soundManager.playSound(1);
        return -1;
        
      }
    }
    if (moved) {
      if(specifications[0] != 2){
        for(int i = 0; i < dataManager.getCurrMap().length ; i++){
          for( int j = 0 ; j < dataManager.getCurrMap()[0].length ; j++){
            if (getCurrentMap()[i][j].getFigureID() == 0)
              return 0;
          }
        }
      }
      else {
        int numberOfPlayers = 0;
        for(int i = 0; i < 4 ; i++){
          if(players[i] != 0)
            numberOfPlayers++;       
        }
        if(numberOfPlayers == 1){
            soundManager.stopBackgroundSound();
            soundManager.playSound(2);
            return 1;
        }
        else
          return 0;
      }
    }
    soundManager.stopBackgroundSound();
    soundManager.playSound(2);
    return 1;  
  }
  public void initialize(int[]specifications ){//number of specifications    
    this.score = 0;
    this.moveCount = 0;    
    turn = 0;
    for(int i = 0 ; i < 12 ; i++)
      figureContainer[i] = i + 1;
    for(int i = 0; i < 4 ; i++){
      players[i] = i + 1;
      for( int j = 0; j < 12; j++)
      figureContainers [i][j] = figureContainer[j];
    }
    
    
    
    this.specifications = specifications; //1- gameMode //0 -time 1- casual
    if(this.specifications[0] == 0){
      if(this.specifications[1] == 0)      
        time = 15;
      else if(this.specifications[1] == 1)        
        time = 60;                                             //2- timeLimit// -1 - no limit 0 - 45secs 1 - 60secs 2- 90 secs
      else if (this.specifications[1] == 2)        
        time = 90;
      dataManager.setMapType(0);
    }
    else if (this.specifications[0] == 1){
      time = -1;
      dataManager.setMapType(0);
    } 
    else if(this.specifications[0] == 2){
      time = -1;
      dataManager.setMapType(1);
    }
    //3- map// 0- 2D, 1 -3D , 2 , dualPentagon
    
    //4- stage // xth stage
    
    if(this.specifications[4] == 0){
      if(!soundManager.checkMuted());
      //soundManager.setMute();
    }
    else if(this.specifications[4] == 1){
      if(soundManager.checkMuted());
      // soundManager.setUnMute();
    }
    round = 0;
    dataManager.reset();
    mapManager.reset(dataManager.getCurrMap() , dataManager.getCapitals() , specifications[0]);
    
  }
  public void setFigure ( int id){
    dataManager.setFigure();
    selectedFigureID = id;
  } 
  public void setEventID(int id){
     /*switch(id){
         case 1 : selectedEventID = 2; break;
         case 2 : selectedEventID = 3; break;
         case 3 : selectedEventID = 4; break;
     
     }*/
     selectedEventID = id;
  
  }
  public int getEventID(){
      return selectedEventID;
  
  }
  public boolean setEvent(){
      if(specifications[0] == 2){
        if(mapManager.setEvent(dataManager.getCurrMap(),players[turn], selectedEventID)){
            eventContainer[turn][selectedEventID -1] --;
            selectedEventID = -1;
            setCurrentMap(mapManager.getCurrMap());
            return true;
            }
        else{
            selectedEventID = -1;
            return false;            
        }
      }     
      else {
          selectedEventID = -1;
          return false;
      
      }
  }
  public boolean move (){
    if(!dataManager.setFigure())
      return false;
    
    //DataManager.getDataManagerInstance
    //GameObjectManager.GetGameObjectManagerInstance.getFigures();
    
    int figureID = this.selectedFigureID;
 
      
     //System.out.println("turn of : " + players[turn] + " order : " + turn);
    if(mapManager.checkMove(dataManager.getCurrMap(),specifications[0],players[turn])){
      setCurrentMap(mapManager.getCurrMap()); 
      if(specifications[0] == 2)
      //figureContainers[turn][selectedFigureID - 1 ] = 0;
      //else
        figureContainer[selectedFigureID -1] = 0;
      if(mapManager.getDefeatedPlayer() != -1)
        players[mapManager.getDefeatedPlayer() - 1] = 0;
      moveCount++;
      updateScore();
      moveTime = 0;       
      selectedFigureID = -1;
      do{
        turn++;
        turn = turn%4;
        if(turn == 3){
            round++;
           update();
        }
        System.out.println("sira : " + turn);
      }while(players[turn] == 0);
      //turn = 0;
      soundManager.playSound(4);// correct sound
      this.dataManager.getFigure(figureID).resetShape();
      return true;
    }
    else{
     // selectedFigureID = -1;
      soundManager.playSound(3);// incorrect sound
      this.dataManager.getFigure(figureID).resetShape();
      return false;
    }
  }
  public boolean remove(int x, int y){
    boolean isRemoved = mapManager.remove(x, y , specifications[0]);
    if(isRemoved){
      figureContainer[mapManager.getRemovedFigure() - 1] =mapManager.getRemovedFigure();
      this.moveCount--;
      this.score -= 1000;
      score = (score < 0) ? 0 : score;
      return true;
    }
    else
      return false;
  }
  public Block[][] getCurrentMap(){
    
    return dataManager.getCurrMap();
  }
  public int getTime(){
    
    return time;
  }
  public Block[][] getFigure(int figureID){
    return dataManager.getFigure(figureID).getShape();//?????
  }
  public int getScore(){
    return score;
  }
  public int getMoveCount(){
    return moveCount;
  }
  public int[] getFigureContainer(){
    if(specifications[0] == 2){
        return figureContainers[turn];
    }
    return figureContainer;
  }
  
  public int[] getEventContainer(){
  
      return eventContainer[turn];
  }
  private void updateScore(){
    score = score + ((900/moveTime) - (900/moveTime)%10);
    
  }
  private void setCurrentMap(Block [][] currMap){// size of the array?
    dataManager.setCurrMap(currMap);
  }
  public Image[] getFigureImages (){
    Image [] temp = new Image[12];
    for(int i = 1 ; i < 13 ; i++)
      temp[i -1] = dataManager.getFigure(i).getImage();
    return temp;
    
  }
  public Image [] getEventImages(){
    return  dataManager.getEventImages();
    
  }
  
  public void rotate (boolean dir, boolean sym) {
     dataManager.getFigure(selectedFigureID).rotate(dir,sym);
  }
  
  public void muteBackground (boolean mute) {
     if (mute) {
        //soundManager.setMute ();
        soundManager.stopBackgroundSound();
     } else {
        //soundManager.setUnMute ();
        if(this.specifications[0] == 0){
           soundManager.playBackgroundSound();
        } else {
           soundManager.playBackgroundSound2();
        }
     }
  }
  
  public void playMenuMusic (boolean mute) {
     if (mute) {
        //soundManager.setMute ();
        soundManager.stopMenuMusic ();
     } else {
        //soundManager.setUnMute ();
        soundManager.playMenuMusic ();
     }
  }
  public int getTurnPlayer(){
      if(specifications[0] == 2)
        return (turn+1);
      else
          return 1;
  }
  
  public void stopTimer () {
     timerStart.cancel ();
     timer.purge ();
  }
  public void playRocky( boolean mute){
     if(!mute)
        soundManager.playRocky();
     else
        soundManager.stopRocky();
  }
  private void update(){
      for(int i = 0; i < 4 ; i++){
          eventContainer[i][3]++;
          if(round+1 % 3 == 0 )
              eventContainer[i][1]++;
          if(round+1 % 5 == 0 )
              eventContainer[i][2]++;
              }     
  }

  public int getTurn() {
      return this.turn;
  }

  public void passTurn() {
      this.turn++;
      this.turn %= 4;
  }
}
