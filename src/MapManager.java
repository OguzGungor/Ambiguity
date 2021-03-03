

public class MapManager implements Locatable {
  
  private Block [][] currMap;
  private int removedFigure;
  private int w = 0 ;
  private int h = 0 ;
  private boolean interacting;
  //private int interactionX;
 // private int interactionY;
  private int noOfBlocks;
  private int[][] disorder;
  //private int[][] eventsFaced;      
  /* 0 = x
                                     * 1 = y
                                     * 2 = type
                                     * 3 = ambushAxis(or any specification) 
                                     * 4 = ambushedPlayer
                                     * */
  private boolean additionalEvent;
  private int[][] order;
  //private boolean secondInteraction;
  private int defeatedPlayer;
  private boolean ambush;
  private boolean resistance;
  private boolean resistancePlaced = false;
  //private int noOfadditionalForce;
  private int [][]lastlyInserted;
  private int ambushX;
  private int ambushY;
  //private int ambushAxis;
  //private int ambushedPlayer;
  private int resistancePlayer;
  private int figureSizeY;
  private int figureSizeX;
  //private int ambushAllignY;
  //private int ambushAllignX;
  private int [][] capitals = new int[4][2];
  private int moveCounter;
  //private int eventCounter;
  private int conqueror;
  private int []noOfPaths;
  private int [] fillPoint; 
  private boolean fillDetected;
  
  MapManager(){
    fillPoint = new int[3];
    noOfPaths = new int[4];
    lastlyInserted = new int[4][2];
    additionalEvent = false;
    for( int i = 0 ; i < 4 ; i++)
      noOfPaths[i] = 1;
    
  
    interacting = false;
    ambush = false;
    fillDetected = false;
    defeatedPlayer = -1;
    //ambushAllignY = 0;
    //ambushAllignX = 0;
    noOfBlocks = 0;
  }
  public boolean setEvent(Block[][] map , int playerNo, int eventID){ 
    if(map[this.locatable.getX()][this.locatable.getY()].getEvent() == 0 && map[this.locatable.getX()][this.locatable.getY()].getPlayerNo() == playerNo){
      currMap[this.locatable.getX()][this.locatable.getY()].setEvent(eventID);
      return true;
    }
    else
      return false;  
  }
  private Block[][] moveSteps( Block [][] tempMap){
    if(noOfBlocks == moveCounter)
      return tempMap;
    else{      
      if(tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 1 && tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo() != conqueror)//capital
      {
        // to do remove player        
        defeatedPlayer = tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo();
        tempMap[order[moveCounter][0]][order[moveCounter][1]].setEvent(0);
        this.removeVersus(order[moveCounter][0],order[moveCounter][1],tempMap);                    
      }/*
       * 
       if(tempMap[x][y] == 1 && tempMap[x][y].getPlayerNo() != playerNo){
        defeatedPlayer = tempMap[x][y].getPlayerNo();
        removeVersus(x,y,tempMap);}
       */
      /* 0 = no event; 
                       * 1 = capital+
                       * 2 = mines+
                       * 3 = ambush+
                       * 4 = supply
                       * 5 = hill+
                       * 6 = resistance
                       * 7 = merchants
                       * 8 = river+
                       */ 
      else if(tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 2 && tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo() != conqueror && tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo() != 0)//mines
      {
        tempMap[order[moveCounter][0]][order[moveCounter][1]].setEvent(0);
        return tempMap;
      }      
      else if(tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 3 && tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo() != 0 && tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo() != conqueror)// ambush
      {       
        resistancePlayer = conqueror;
        conqueror = tempMap[order[moveCounter][0]][order[moveCounter][1]].getPlayerNo();
        ambush = true;
        ambushX = order[moveCounter][0];
        ambushY = order[moveCounter][1];  
        additionalEvent = true;
      }
      /*
      else if (tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 4)//supply
      {}
      */
      else if (tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 6 && tempMap[order[moveCounter][0]][order[moveCounter][1]].getEmbeddedPlayerID() == conqueror )//resistance
      {
        tempMap[order[moveCounter][0]][order[moveCounter][1]].setEvent(0);
        resistance = true;
      }      
      else if (tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 7)//merchants
      {
        tempMap[order[moveCounter][0]][order[moveCounter][1]].setEvent(0);
        resistance = true;
      }
      System.out.print(tempMap[order[moveCounter][0]][order[moveCounter][1]].getNext()[0] + "   ");
      System.out.println( tempMap[order[moveCounter][0]][order[moveCounter][1]].getNext()[1]);  
      tempMap = executeOccupation(tempMap,conqueror,order[moveCounter][0],order[moveCounter][1],-1,-1,order[moveCounter][2],ambush,resistance);                                   
      if(ambush && additionalEvent){
        tempMap[order[moveCounter][0]][order[moveCounter][1]].setEvent(0);
        try{
        tempMap = executeOccupation(tempMap,conqueror, 2*ambushX-order[moveCounter][0],2*ambushY -order[moveCounter][1] , -1, -1, order[moveCounter][2],ambush,resistance); 
        }catch(Exception ex){}     
        try{
        tempMap = executeOccupation(tempMap, conqueror, 2*ambushX-order[moveCounter][0],order[moveCounter][1] , -1, -1,order[moveCounter][2],ambush,resistance);
         }catch(Exception ex){}
        try{
        tempMap = executeOccupation(tempMap,conqueror,order[moveCounter][0],2*ambushY -order[moveCounter][1] , -1,-1, order[moveCounter][2],ambush,resistance);    
        }catch(Exception ex){}    
        try {
        if(Math.floor(Math.random()*6) > 3 && !resistancePlaced){
          tempMap[2*ambushX-order[moveCounter][0]][2*ambushY -order[moveCounter][1]].setEvent(6);
          tempMap[2*ambushX-order[moveCounter][0]][2*ambushY -order[moveCounter][1]].setEmbeddedPlayerID(resistancePlayer);
          resistancePlaced = true;
        }
        }catch(Exception ex){}
      }
      if(resistance){
         
        try{          
        tempMap = executeOccupation(tempMap,conqueror, order[moveCounter][0]+1,order[moveCounter][1]+1, -1,-1, order[moveCounter][2],ambush,resistance);
        }catch(Exception ex){}
          try{
        tempMap = executeOccupation(tempMap,conqueror, order[moveCounter][0]-1,order[moveCounter][1]-1 , -1,-1, order[moveCounter][2],ambush,resistance);
        }catch(Exception ex){}
        try{
        tempMap = executeOccupation(tempMap,conqueror, order[moveCounter][0]-1,order[moveCounter][1]+1 , -1,-1, order[moveCounter][2],ambush,resistance);
        }catch(Exception ex){}
        try{
        tempMap = executeOccupation(tempMap,conqueror,order[moveCounter][0]+1,order[moveCounter][1]-1 , -1,-1, order[moveCounter][2],ambush,resistance);
        }catch(Exception ex){}
        resistance = false;
      }
      if(checkBoundary(order[moveCounter][0], order[moveCounter][1] ,tempMap) && !fillDetected){
        fillPoint[0] = order[moveCounter][0];
        fillPoint[1] = order[moveCounter][1];
        fillPoint[2] = conqueror;
        fillDetected = true;        
      }
      
      moveCounter++;
      return moveSteps(tempMap);
    }
  
  }
  
  public boolean checkMove ( Block [][] map , int gameType , int playerNo ){
    
    currMap = map;
    Block [][] figure = this.locatable.getFigure().getShape();
    Block [][] tempMap;
    tempMap = new Block[map.length][map[0].length];
    for( int i = 0 ; i < map.length ; i++){
      for(int j = 0 ; j< map[0].length ; j++){
        tempMap[i][j] = new Block();
        tempMap[i][j].setFigureID(map[i][j].getFigureID()); 
        tempMap[i][j].setPlayer(map[i][j].getPlayerNo());
        tempMap[i][j].setEvent(map[i][j].getEvent());
        tempMap[i][j].setPathNo(map[i][j].getPathNo());
        tempMap[i][j].setEmbeddedPlayerID(map[i][j].getEmbeddedPlayerID());
        tempMap[i][j].setNext(map[i][j].getNext()[0],map[i][j].getNext()[1]);
      }
    }    
    allignFigure(gameType);      
    if(gameType == 2){ 
      if(!getInteraction(map,figure, playerNo)){
        noOfBlocks = 0;
        moveCounter = 0;
        //eventCounter = 0;        
        return false;
      }
      else if(!checkEvents(map, figure,tempMap , playerNo)){
        moveCounter = 0;
        //eventCounter = 0;
        noOfBlocks = 0;
        return false;        
      }
       moveCounter = 0;
       //eventCounter = 0;
       conqueror = playerNo;
       currMap = moveSteps(tempMap);
       ambush = false;
       fillDetected = false;
       resistance = false;
       boolean resistancePlaced = false;
    }
    else{
    for(int i = 0; i < this.locatable.getFigure().getShape().length; i++){
      for(int j = 0; j < this.locatable.getFigure().getShape()[0].length ; j++){
        
        if(figure[i][j].getFigureID() != 0){
          try{
            if(gameType != 2){
              if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getFigureID() != 0 ){
                
                this.locatable.setFigureID(-1);
                return false;      
              }
              
              tempMap[this.locatable.getX() + i - w ][this.locatable.getY() + j - h ].setFigureID(this.locatable.getSelectedFigureID() );
            }
           
            
          }catch(Exception ex){          

          }
        }
        
      }
    }
    if(!interacting && gameType == 2){
      defeatedPlayer = -1;
      //ambushedPlayer = -1;
      ambush = false;
      interacting = false;
      //ambushAllignY = 0;
      //ambushAllignX = 0;
      return false;
    }
    for( int i = 0 ; i < map.length ; i++){
      for(int j = 0 ; j< map[0].length ; j++){
        if(tempMap[i][j].getPlayerNo() != defeatedPlayer){
          currMap[i][j].setFigureID(tempMap[i][j].getFigureID()); 
          currMap[i][j].setPlayer(tempMap[i][j].getPlayerNo());
          currMap[i][j].setEvent(tempMap[i][j].getEvent());
        }
        else{
          currMap[i][j].setFigureID(0);
          currMap[i][j].setPlayer(0);
          currMap[i][j].setEvent(0);
        }
      }
    }
    }
   // ambushAllignY = 0;
   // ambushAllignX = 0;
    this.locatable.setFigureID(-1);
    interacting = false;
   // ambushedPlayer = -1;
    ambush = false;
    noOfBlocks = 0;
    return true;
  }
  private Block[][]  removeVersus(int x, int y, Block[][] tempMap){
    int toBeRemoved = tempMap[x][y].getPlayerNo();
    removedFigure = toBeRemoved;      
    return removeStepsVersus(x,y,tempMap);
    
  }  
  private Block[][] removeStepsVersus(int x, int y, Block[][] tempMap){     
    
    if(tempMap[x][y].getPlayerNo() == removedFigure){
          tempMap[x][y].setFigureID(0);
          tempMap[x][y].setPlayer(0);
          try{
           if(tempMap[x+1][y].getPlayerNo() == removedFigure)
                tempMap = removeStepsVersus(x+1,y,tempMap);    
          }catch(Exception ex){ }
          try{
              if(tempMap[x-1][y].getPlayerNo() == removedFigure)
                tempMap =  removeStepsVersus(x-1,y,tempMap);
          }catch(Exception ex){}
          try{
              if(tempMap[x][y+1].getPlayerNo() == removedFigure)
                tempMap =  removeStepsVersus(x,y+1,tempMap);
          }catch(Exception ex){ }
          try{
              if(tempMap[x][y-1].getPlayerNo() == removedFigure)
                tempMap =  removeStepsVersus(x,y-1,tempMap);
          }catch(Exception ex){}
          return tempMap;
      }
      else
        return tempMap;  
    
    }
  public boolean remove(int x,int y, int gameType){
    if(gameType != 2){
      if(currMap[x][y].getFigureID() == 0)
        return false;
      
      else{
        int toBeRemoved = currMap[x][y].getFigureID();
        removedFigure = toBeRemoved;      
        removeSteps(x,y);
        return true;
      }
      
    }
    else
      return false;
  }
  private void removeSteps(int x, int y ){
    try{
      if(currMap[x][y].getFigureID() == removedFigure){
        currMap[x][y].setFigureID(0);
        removeSteps(x+1,y);
        removeSteps(x-1,y);
        removeSteps(x,y+1);
        removeSteps(x,y-1);
      }
      else
        return;      
          
    }catch(Exception ex){}
  }
  
  public void reset(Block [][] blankMap, int [][]capitals , int gameType){
    defeatedPlayer = -1;
    interacting = false;
    ambush = false;
    currMap = new Block[blankMap.length][blankMap[0].length];
    
    for( int i = 0 ; i < blankMap.length ; i++){
      for(int j = 0 ; j< blankMap[0].length ; j++){
        currMap[i][j] = new Block();
        currMap[i][j].setFigureID(blankMap[i][j].getFigureID()); 
        currMap[i][j].setPlayer(blankMap[i][j].getPlayerNo());
        currMap[i][j].setEvent(blankMap[i][j].getEvent());
      }
    }
    
    if(gameType == 2){
      for(int i = 0; i < 4 ; i++){
        for( int j = 0; j < 2 ; j++){
          this.capitals[i][j] = capitals[i][j]; 
          lastlyInserted[i][j] = capitals[i][j];
        }
      }    
      
    }
    noOfPaths = new int[4];
    for( int i = 0 ; i < 4 ; i++)
      noOfPaths[i] = 1;
  }
  public int getRemovedFigure(){
    return removedFigure;
  }
  public Block[][] getCurrMap(){
    
    //ambushAllignY = 0;
   // ambushAllignX = 0;
   // ambushAxis = 0;
    return currMap;
  }  
  public int getDefeatedPlayer(){
    return defeatedPlayer;
  }
  ///////////////////////////////////////////
  
  private void allignFigure( int gameType){      
    boolean found = true;
    for(int i = 0; i < this.locatable.getFigure().getShape().length; i++){
      for(int j = 0; j < this.locatable.getFigure().getShape()[0].length ; j++){
        if(this.locatable.getFigure().getShape()[i][j].getFigureID() != 0 && found ){
          h = j;
          w = i;
          found = false;
        }
        if(this.locatable.getFigure().getShape()[i][j].getFigureID() != 0 &&!found && gameType == 2){
          if(figureSizeX < i)
            figureSizeX = i;
          if(figureSizeY < j)
            figureSizeY = j;
        }
        if(this.locatable.getFigure().getShape()[i][j].getFigureID() != 0)
          noOfBlocks++;
      }
    }
    if(gameType == 2){
      figureSizeX ++;
      figureSizeY ++;
      figureSizeX -= w;
      figureSizeY -= h; 
    }
 
  }
  private boolean checkEvents(Block [][] map, Block [][] figure, Block [][]tempMap, int playerNo){
    
    /* 1 = capital+
     * 2 = mines+
     * 3 = ambush+
     * 4 = supply+
     * 5 = hill+
     * 6 = resistance/
     * 7 = merchants+
     * 8 = river+
     * eventsFaced[][]
     */ 
    
//    int[][] temp = new int [noOfBlocks][5];
//    int tempCount = 0;
    for(int i = 0; i < noOfBlocks ; i++){
            try{                     
              if(map[order[i][0]][order[i][1]].getFigureID() != 0 && map[order[i][0]][order[i][1]].getPlayerNo() == playerNo ){
                return false;              
              }                          
              else if(map[order[i][0]][order[i][1]].getEvent() == 5 || map[order[i][0]][order[i][1]].getEvent() == 8)
                return false;
//              else if(map[order[i][0]][order[i][1]].getEvent() == 1){
//                temp[tempCount][0] = order[i][0];
//                temp[tempCount][1] = order[i][1];
//                temp[tempCount][2] = 1;
//                tempCount++;
//              }
//              else if(map[order[i][0]][order[i][1]].getEvent() == 2 && map[order[i][0]][order[i][1]].getPlayerNo() != 0 ){
//                temp[tempCount][0] = order[i][0];
//                temp[tempCount][1] = order[i][1];
//                temp[tempCount][2] = 2; 
//                tempCount++;
//              }
//              else if(map[order[i][0]][order[i][1]].getEvent() == 3 && map[order[i][0]][order[i][1]].getPlayerNo() != 0 ){
//                temp[tempCount][0] = order[i][0];
//                temp[tempCount][1] = order[i][1];
//                temp[tempCount][2] = 3;
//                if( capitals[map[order[i][0]][order[i][1]].getPlayerNo() - 1][0] ==  capitals[conqueror -1][0] &&  capitals[map[order[i][0]][order[i][1]].getPlayerNo() - 1][1] !=  capitals[conqueror -1][1] ) 
//                  temp[tempCount][3] = 1;
//                else if( capitals[map[order[i][0]][order[i][1]].getPlayerNo() - 1][0] !=  capitals[conqueror -1][0] &&  capitals[map[order[i][0]][order[i][1]].getPlayerNo() - 1][1] ==  capitals[conqueror -1][1] )
//                  temp[tempCount][3] = 2;
//                else
//                  temp[tempCount][3] = 3;
//                temp[tempCount][4] = map[order[i][0]][order[i][1]].getPlayerNo();
//                tempCount++;
//              }
//              else if(map[order[i][0]  - 1 ][order[i][1]].getEvent() == 4 && map[order[i][0] - 1][order[i][1]].getPlayerNo() == playerNo||
//                      map[order[i][0] + 1][order[i][1]].getEvent() == 4 && map[order[i][0] + 1][order[i][1]].getPlayerNo() == playerNo ||
//                      map[order[i][0]][order[i][1] - 1].getEvent() == 4 && map[order[i][0]][order[i][1] - 1].getPlayerNo() == playerNo||
//                      map[order[i][0]][order[i][1] + 1].getEvent() == 4 && map[order[i][0]][order[i][1] + 1].getPlayerNo() == playerNo){
//                temp[tempCount][0] = order[i][0];
//                temp[tempCount][1] = order[i][1];
//                temp[tempCount][2] = 4; 
//                tempCount++;
//              }
//              else if(map[order[i][0]][order[i][1]].getEvent() == 7 && map[order[i][0]][order[i][1]].getPlayerNo() != 0){
//                temp[tempCount][0] = order[i][0];
//                temp[tempCount][1] = order[i][1];
//                temp[tempCount][2] = 7;
//                tempCount++;
//              }
              

              
              
            }catch(Exception ex){}
          
    }
          
//    eventsFaced = new int[tempCount][5];
//    for(int i = 0 ; i < tempCount ; i++ ){
//      for(int j = 0 ; j < 5 ; j++)
//        eventsFaced[i][j] = temp[i][j];
//              
//    }

      return true;
  }
  private boolean getInteraction(Block[][] map,Block[][] figure , int playerNo){
    
    disorder = new int [noOfBlocks][4];
    int count = 0;
    int path = 0;
    boolean pathFound = false;
    int interactionCount = 0;
    int[] interactionDirection = new int[4];
    
    for(int i = 0; i < this.locatable.getFigure().getShape().length; i++){
      for(int j = 0; j < this.locatable.getFigure().getShape()[0].length ; j++){        
        if(figure[i][j].getFigureID() != 0){
         try{
          if((this.locatable.getX() + i - w  + 1 )  < map.length ){
              
            if( map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo){//sa??na
              
              interacting = true;
              if(pathFound){
                disorder[count][2] = path;
              }
              else {
                if(map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPathNo() == 1){
                disorder[count][2] = noOfPaths[playerNo -1] + 1;
                noOfPaths[playerNo -1]++;
                path = noOfPaths[playerNo -1];
                System.out.println("here: " + path);
                pathFound = true;
              }
                else if(map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPathNo() != 1){
                  disorder[count][2] = map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPathNo();
                }
              }
            }
          }
      
          if((this.locatable.getX() + i - w  - 1 ) >= 0){
            if( map[this.locatable.getX() + i - w  - 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){//soluna
              interacting = true;
              if(pathFound){
                disorder[count][2] = path;
              }
              else{
                if(map[this.locatable.getX() + i - w  -1  ][this.locatable.getY() + j - h ].getPathNo() == 1){
                disorder[count][2] = noOfPaths[playerNo -1] + 1;
                noOfPaths[playerNo -1]++;
                path = noOfPaths[playerNo -1];
                System.out.println("here: " + path);
                pathFound = true;
                }
                else if(map[this.locatable.getX() + i - w  -1  ][this.locatable.getY() + j - h ].getPathNo() != 1){
                  disorder[count][2] = map[this.locatable.getX() + i - w  -1  ][this.locatable.getY() + j - h ].getPathNo();
                }
              }
            }
          }
          if((this.locatable.getY() + j - h  + 1) < map[0].length  ){
            if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h  + 1].getPlayerNo() == playerNo  ){//alt?na
              interacting = true;
              if(pathFound) {
                disorder[count][2] = path;
              }
              else {
                if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h + 1 ].getPathNo() == 1){
                disorder[count][2] = noOfPaths[playerNo -1] + 1;
                noOfPaths[playerNo -1]++;
                path = noOfPaths[playerNo -1];
                System.out.println("here: " + path);
                pathFound = true;
              }
                else if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h + 1].getPathNo() != 1){
                  disorder[count][2] = map[this.locatable.getX() + i - w][this.locatable.getY() + j - h + 1 ].getPathNo();
                }
              }
            }
          }
          if((this.locatable.getY() + j - h - 1 ) >= 0){
            if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1 ].getPlayerNo() == playerNo){//�st�ne
              interacting = true;
              if(pathFound){
                disorder[count][2] = path;
              }
                else {
                if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1 ].getPathNo() == 1){
                  disorder[count][2] = noOfPaths[playerNo -1] + 1;
                  noOfPaths[playerNo -1]++;
                  path = noOfPaths[playerNo -1];
                  System.out.println("here: " + path);
                  pathFound = true;
                }
                else if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1].getPathNo() != 1){
                  disorder[count][2] = map[this.locatable.getX() + i - w][this.locatable.getY() + j - h - 1 ].getPathNo();
                }
                }
            }
          }
          disorder[count][0] = this.locatable.getX() + i - w;
          disorder[count][1] = this.locatable.getY() + j - h;
          disorder[count][3] = figure[i][j].getInteracted();
          count++;
           }catch(Exception ex){
         System.out.println("aha la burda la :" + this.locatable.getX()  + "  " + this.locatable.getX() );
         }
        }
         
      }
    }
    if(interacting)
      order(playerNo);
    count = 0; 
    pathFound = false;
    return interacting;
  }
  private void order(int playerNo){
    
    int[] temp = new int[4];
    temp[3] = 0;
    int count =0;
    int deleted = 0;
    order = new int[disorder.length][disorder[0].length];
    boolean pathFound = false;
    boolean onlyInteracting = true;
    int path = 0;
    
    
    for(int i = 0 ; i < noOfBlocks ; i++){
      for(int j =0 ; j < noOfBlocks ; j++)
      {
        if(disorder[j][2] != 0){                                
          if(!pathFound){
            pathFound = true;
            path = disorder[j][2];
          }
          for(int k = 0; k < 4 ; k++)
            temp[k]= disorder[j][k];
          deleted = j;                  
        }  
         
     }
      if(temp[3] != 0 ){
        count++;
        temp[2] = path;
      }
      for(int k = 0; k < 4 ; k++){
        order[i][k]= temp[k];
        temp[k] = 0;   
        disorder[deleted][k] = 0;
      }
     
      disorder[deleted][0] = -2;
      disorder[deleted][1] = -2;
     
    }
    int tempCounter = 1;
    
    if(count < noOfBlocks){
    for(int i = count; i < noOfBlocks ; i++){
      do{
      for(int j =0 ; j < noOfBlocks ; j++)
      {
        try{
          if(disorder[j][3] != 0){            
            if(((order[i-tempCounter][0] == disorder[j][0] - 1) && (order[i-tempCounter][1] == disorder[j][1]) )||
               ((order[i-tempCounter][0] == disorder[j][0] + 1) && (order[i-tempCounter][1] == disorder[j][1])) ||
               ((order[i-tempCounter][1] == disorder[j][1] - 1) && (order[i-tempCounter][0] == disorder[j][0] )) ||
               ((order[i-tempCounter][1] == disorder[j][1] + 1) && (order[i-tempCounter][0] == disorder[j][0] )) ){
              for(int k = 0; k < 4 ; k++)
                temp[k]= disorder[j][k];
              if(pathFound ){
                temp[2] = path;
                onlyInteracting = false;
              }
              else{
                if(onlyInteracting){
                noOfPaths[playerNo -1 ]++;
                path = noOfPaths[playerNo -1 ];
                onlyInteracting = false;
                }
                temp[2] = path;
                pathFound = true;
              }
                
              deleted = j;                  
            }
                        
            }         
        }
        catch(Exception ex){
          
          if(tempCounter > 10)
            disorder[deleted][3] = 1;
        }
      }
      if(disorder[deleted][3] == 0) {
          tempCounter++;
          pathFound = false;
          onlyInteracting = true;
      }
      }while(disorder[deleted][3] ==0);
        tempCounter = 1;
      for(int k = 0; k < 4 ; k++){
        order[i][k]= temp[k];
        temp[k] = 0;
        disorder[deleted][k] = 0;
      }
      disorder[deleted][0] = -2;
      disorder[deleted][1] = -2;
    }
    }
    count = 0;
  }
  private boolean checkBoundary(int x , int y ,Block[][] tempMap){
    try{
    if((tempMap[x + 1][y].getPathNo() != tempMap[x][y].getPathNo() && tempMap[x + 1][y].getPathNo() != 0)||
       (tempMap[x][y + 1 ].getPathNo() != tempMap[x][y].getPathNo() && tempMap[x][y + 1 ].getPathNo() != 0)||
       (tempMap[x - 1][y].getPathNo() != tempMap[x][y].getPathNo() && tempMap[x - 1][y].getPathNo() != 0) ||
       (tempMap[x][y - 1].getPathNo() != tempMap[x][y].getPathNo() && tempMap[x][y - 1].getPathNo() != 0 ))
      return true;
    else
      return false;
    }catch(Exception ex){return false;}
  }
  
//  private Block[][] fill( Block[][] tempMap, int xDirection, int yDirection, int playerNo,int pathNo){
//    if(tempMap[fillPoint[0] + xDirection][fillPoint[1] + yDirection].getPlayerNo() != 0)
//      tempMap = fillSteps(Block[][] tempMap,playerNo,fillPoint[0],fillPoint[1]);
//    else if(tempMap[fillPoint[0] + xDirection][fillPoint[1] + yDirection].getPathNo()!= pathNo)
//      return tempMap;
//    else if(tempMap[fillPoint[0] + xDirection][fillPoint[1] + yDirection].getPathNo()== pathNo){
//      fillPoint[0] +=xDirection;
//      fillPoint[1] +=ydirection;
//      tempMap =  fill(tempMap,xDirection,yDirection,playerNo,pathNo);
//    
//    }
//    
//    //todo
//    //System.out.println("HERE : " + fillPoint[0] + "  " + fillPoint[1] + "  " + fillPoint[2] + "  "); 
//    return tempMap;
//  }
// // private Block[][] fillSteps(Block[][] tempMap,int playerNo, int x, int y){
//    // todo
//  //}
//    
 
  
  
  private Block[][] executeOccupation(Block [][] tempMap, int playerNo, int x,int y , int nextX, int nextY, int pathNo,boolean ambush,boolean resistance){
    
    if (tempMap[x][y].getEvent() == 5 || tempMap[x][y].getEvent() == 8)
        return tempMap;
    else if(tempMap[x][y].getEvent() == 1 && tempMap[x][y].getPlayerNo() != conqueror && tempMap[x][y].getPlayerNo() != 0)//capital
      {
        // to do remove player        
        defeatedPlayer = tempMap[x][y].getPlayerNo(); 
        tempMap[x][y].setEvent(0);
        this.removeVersus(x,y,tempMap);                    
      }
      else if(tempMap[x][y].getEvent() == 2 && tempMap[x][y].getPlayerNo() != conqueror && tempMap[x][y].getPlayerNo() != 0)//mines
      {
        tempMap[x][y].setEvent(0);
        additionalEvent = false;
        return tempMap;
      }      
      else if(tempMap[x][y].getEvent() == 3 && tempMap[x][y].getPlayerNo() != conqueror && tempMap[x][y].getPlayerNo() != 0)// ambush
      {              
        conqueror = tempMap[x][y].getPlayerNo();
        ambush = true;
        ambushX = x;
        ambushY = y;   
      }
      /*
      else if (tempMap[order[moveCounter][0]][order[moveCounter][1]].getEvent() == 4)//supply
      {}
      */
      else if (tempMap[x][y].getEvent() == 6 && tempMap[x][y].getEmbeddedPlayerID() == conqueror && tempMap[x][y].getPlayerNo() != 0)//resistance
      {
        tempMap[x][y].setEvent(0);
        resistance = true;
      }      
      else if (tempMap[x][y].getEvent() == 7)//merchants
      {
        tempMap[x][y].setEvent(0);
        resistance = true;
      }
      
      ///////////////////
    if(tempMap[x][y].getNext()[0] != -1 && tempMap[x][y].getNext()[1] != -1){
      Block temp;
      int[] temp2 = new int[2];
      temp = tempMap[x][y];
      temp2[0] = x;
      temp2[1] = y;
//      while(temp2[0] != -1 && temp2[1] != -1){ 
//        tempMap[temp2[0]][temp2[1]] = null;
//        tempMap[temp2[0]][temp2[1]] = new Block();
//        temp2 = temp.getNext();
//        try{
//          temp =  tempMap[temp2[0]][temp2[1]];
//        }catch(Exception ex){}
//      }
    }
    if(tempMap[x][y].getEvent() == 1 && tempMap[x][y].getPlayerNo() != playerNo){
        defeatedPlayer = tempMap[x][y].getPlayerNo();
        tempMap[x][y].setEvent(0);
        removeVersus(x,y,tempMap);}
    if(ambush || resistance){
      if(tempMap[x][y].getPlayerNo() != playerNo)
        tempMap[x][y].setEvent(0);
    }
    
    tempMap[x][y].setFigureID(this.locatable.getSelectedFigureID());
    tempMap[x][y].setPlayer(playerNo);
    tempMap[x][y].setPathNo(pathNo);
    tempMap[x][y].setNext(nextX, nextY);
    if( pathNo == tempMap[lastlyInserted[conqueror -1][0]][lastlyInserted[conqueror-1][1]].getPathNo()|| tempMap[lastlyInserted[conqueror -1][0]][lastlyInserted[conqueror-1][1]].getPathNo() == 1){
      tempMap[lastlyInserted[conqueror -1][0]][lastlyInserted[conqueror-1][1]].setNext(x,y);
      lastlyInserted[conqueror- 1][0] = x;
      lastlyInserted[conqueror- 1][1] = y;
      
      
    }
    return tempMap;
    
  }
  // tempMap = executeOccupation(tempMap,conqueror, int x,int y , -1,-1, order[moveCounter][2]);
       //if(.getPlayerNo() != conqueror)
}
//
