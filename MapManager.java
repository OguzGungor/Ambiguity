//packages????

public class MapManager implements Locatable {
  
  private Block [][] currMap = new Block[22][10];
  private int removedFigure;
  private int [][] occupation = new int[2][2];
  private boolean interacting;
  private boolean secondInteraction;
  private int defeatedPlayer;
  private boolean ambush;
  private int ambushedPlayer;
  MapManager(){
    interacting = false;
    ambush = false;
    defeatedPlayer = -1;
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
      }
    }
    int w = 0;
    int h = 0;
    boolean found = true;
    for(int i = 0; i < this.locatable.getFigure().getShape().length; i++){
      for(int j = 0; j < this.locatable.getFigure().getShape()[0].length ; j++){
        if(this.locatable.getFigure().getShape()[i][j].getFigureID() != 0 && found ){
          h = j;
          w = i;
          found = false;
        }
      }
    }
    
    /////////////////////////////////////////////////////////////////////
    if(gameType == 2){     
    for(int i = 0; i < this.locatable.getFigure().getShape().length; i++){
      for(int j = 0; j < this.locatable.getFigure().getShape()[0].length ; j++){        
        if(figure[i][j].getFigureID() != 0){
          try{                     
              if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getFigureID() != 0 && map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){
                return false;
              }
              else if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent() == 2){
                currMap[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].setEvent(0);
                return true;              
              }
              else if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent() == 3 && map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo() != 0 ){
                ambush = true;
                ambushedPlayer = map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo(); 
                currMap[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].setEvent(0);
              }
              //System.out.println("event : " + map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent());             
//               if(!interacting){
//                if((this.locatable.getX() + i - w  + 1 )  < map.length ){
//                  if( map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo )
//                    interacting = true;
//                }
//                if((this.locatable.getX() + i - w  - 1 ) >= 0){
//                  if( map[this.locatable.getX() + i - w  - 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo )
//                    interacting = true;
//                }
//                if((this.locatable.getY() + j - h  + 1) < map[0].length  ){
//                  if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h  + 1].getPlayerNo() == playerNo )
//                    interacting = true;
//                }
//                if((this.locatable.getY() + j - h - 1 ) >= 0){
//                  if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1 ].getPlayerNo() == playerNo)
//                    interacting = true;
//                }
//                if(interacting){
//                  occupation[0][0] = this.locatable.getX() + i - w;
//                  occupation[0][1] = this.locatable.getY() + j - h;
//                }
//                }
//               else{
//                 if((this.locatable.getX() + i - w  + 1 )  < map.length ){
//                   if( map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){
//                     occupation[1][0] = this.locatable.getX() + i - w;
//                     occupation[1][1] = this.locatable.getY() + j - h;
//                     secondInteraction = true;
//                   }
//                   }
//                 if((this.locatable.getX() + i - w  - 1 ) >= 0){
//                   if( map[this.locatable.getX() + i - w  - 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){
//                    occupation[1][0] = this.locatable.getX() + i - w;
//                    occupation[1][1] = this.locatable.getY() + j - h;
//                    secondInteraction = true;
//                   }
//                 }
//                 if((this.locatable.getY() + j - h  + 1) < map[0].length  ){
//                   if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h  + 1].getPlayerNo() == playerNo ){
//                     occupation[1][0] = this.locatable.getX() + i - w;
//                    occupation[1][1] = this.locatable.getY() + j - h;
//                    secondInteraction = true;
//                   }
//                 }
//                 if((this.locatable.getY() + j - h - 1 ) >= 0){
//                   if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1 ].getPlayerNo() == playerNo){
//                    occupation[1][0] = this.locatable.getX() + i - w;
//                    occupation[1][1] = this.locatable.getY() + j - h;
//                    secondInteraction = true;
//                   }
//                 }
//               }
//            
          
        
          }catch(Exception ex){          
//            if(this.locatable.getFigure().getShape()[i][j].getFigureID() == 0 || 
//              (this.locatable.getX() + i - w  + 1 ) < 0 ||(this.locatable.getY() + j - h ) < 0 ||  
//               (this.locatable.getX() + i - w  - 1 ) < 0 || (this.locatable.getY() + j - h ) < 0  ||
//              (this.locatable.getX() + i - w ) < 0 || (this.locatable.getY() + j - h  + 1) <0  ||
//               (this.locatable.getX() + i - w) < 0 ||(this.locatable.getY() + j - h - 1 ) < 0){
//              
//            }
//            else{
//              if(!ambush){
//              this.locatable.setFigureID(-1);
//              System.out.println("no figure : " + this.locatable.getFigure().getShape()[i][j].getFigureID() + "   " + (this.locatable.getX() + i - w ) + "   " + (this.locatable.getY() + j - h ));
//              System.out.println(ex);
//              return false;
//              }
//              else;
//                
//            }
          }
        }
        
      }
    }
    }
    /////////////////////////////////////////////////////////////////////
    
    for(int i = 0; i < this.locatable.getFigure().getShape().length; i++){
      for(int j = 0; j < this.locatable.getFigure().getShape()[0].length ; j++){
        
        if(figure[i][j].getFigureID() != 0){
          try{
            if(gameType != 2){
              if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getFigureID() != 0 ){
//            for( int k = 0; k < 5 ; k++){
//              for(int l = 0; l < 11 ; l++)
//                System.out.print(currMap[l][k]);
//              System.out.println();
//            }               
                this.locatable.setFigureID(-1);
                return false;      
              }
              
              tempMap[this.locatable.getX() + i - w ][this.locatable.getY() + j - h ].setFigureID(this.locatable.getSelectedFigureID() * this.locatable.getFigure().getShape()[i][j].getFigureID());
            }
            else{
              if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getFigureID() != 0 && map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){
                return false;
              }
              if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent() == 1){
                defeatedPlayer = map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo();
                //System.out.println("yenilen : " + defeatedPlayer);
              }
//              else if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent() == 2){
//                interacting = false;
//                map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].setEvent(0);
//                return true;              
//              }
//              else if(map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent() == 3 && map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo() != 0 ){
//                interacting = false;
//                ambush = true;
//                ambushedPlayer =map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getPlayerNo(); 
//              }
              //System.out.println("event : " + map[this.locatable.getX() + i - w ][ this.locatable.getY() + j - h ].getEvent());
              if(ambush){
                tempMap[this.locatable.getX() + i - w ][this.locatable.getY() + j - h ].setPlayer(ambushedPlayer);              
                tempMap[this.locatable.getX() + i - w ][this.locatable.getY() + j - h ].setFigureID(ambushedPlayer * this.locatable.getFigure().getShape()[i][j].getFigureID());
                tempMap[this.locatable.getX() + i - w  ][this.locatable.getY() +(3- j) - h ].setPlayer(ambushedPlayer);              
                tempMap[this.locatable.getX() + i - w  ][this.locatable.getY() +(3- j) - h ].setFigureID(ambushedPlayer * this.locatable.getFigure().getShape()[i][j].getFigureID());
              }
              else{
                tempMap[this.locatable.getX() + i - w ][this.locatable.getY() + j - h ].setPlayer(playerNo);              
                tempMap[this.locatable.getX() + i - w ][this.locatable.getY() + j - h ].setFigureID(playerNo * this.locatable.getFigure().getShape()[i][j].getFigureID());
              }
               if(!interacting){
                if((this.locatable.getX() + i - w  + 1 )  < map.length ){
                  if( map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo )
                    interacting = true;
                }
                if((this.locatable.getX() + i - w  - 1 ) >= 0){
                  if( map[this.locatable.getX() + i - w  - 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo )
                    interacting = true;
                }
                if((this.locatable.getY() + j - h  + 1) < map[0].length  ){
                  if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h  + 1].getPlayerNo() == playerNo )
                    interacting = true;
                }
                if((this.locatable.getY() + j - h - 1 ) >= 0){
                  if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1 ].getPlayerNo() == playerNo)
                    interacting = true;
                }
                if(interacting){
                  occupation[0][0] = this.locatable.getX() + i - w;
                  occupation[0][1] = this.locatable.getY() + j - h;
                }
                }
               else{
                 if((this.locatable.getX() + i - w  + 1 )  < map.length ){
                   if( map[this.locatable.getX() + i - w  + 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){
                     occupation[1][0] = this.locatable.getX() + i - w;
                     occupation[1][1] = this.locatable.getY() + j - h;
                     secondInteraction = true;
                   }
                   }
                 if((this.locatable.getX() + i - w  - 1 ) >= 0){
                   if( map[this.locatable.getX() + i - w  - 1 ][this.locatable.getY() + j - h ].getPlayerNo() == playerNo ){
                    occupation[1][0] = this.locatable.getX() + i - w;
                    occupation[1][1] = this.locatable.getY() + j - h;
                    secondInteraction = true;
                   }
                 }
                 if((this.locatable.getY() + j - h  + 1) < map[0].length  ){
                   if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h  + 1].getPlayerNo() == playerNo ){
                     occupation[1][0] = this.locatable.getX() + i - w;
                    occupation[1][1] = this.locatable.getY() + j - h;
                    secondInteraction = true;
                   }
                 }
                 if((this.locatable.getY() + j - h - 1 ) >= 0){
                   if(map[this.locatable.getX() + i - w ][this.locatable.getY() + j - h - 1 ].getPlayerNo() == playerNo){
                    occupation[1][0] = this.locatable.getX() + i - w;
                    occupation[1][1] = this.locatable.getY() + j - h;
                    secondInteraction = true;
                   }
                 }
               }
            }
          
        
          }catch(Exception ex){          
            if(this.locatable.getFigure().getShape()[i][j].getFigureID() == 0 || 
              (this.locatable.getX() + i - w  + 1 ) < 0 ||(this.locatable.getY() + j - h ) < 0 ||  
               (this.locatable.getX() + i - w  - 1 ) < 0 || (this.locatable.getY() + j - h ) < 0  ||
              (this.locatable.getX() + i - w ) < 0 || (this.locatable.getY() + j - h  + 1) <0  ||
               (this.locatable.getX() + i - w) < 0 ||(this.locatable.getY() + j - h - 1 ) < 0){
              return false;
            }
            else{
              if(!ambush){
              this.locatable.setFigureID(-1);
              System.out.println("no figure : " + this.locatable.getFigure().getShape()[i][j].getFigureID() + "   " + (this.locatable.getX() + i - w ) + "   " + (this.locatable.getY() + j - h ));
              System.out.println(ex);
              return false;
              }
              else;
                
            }
          }
        }
        
      }
    }
    if(!interacting && gameType == 2){
//      System.out.println("no interaction ");
      defeatedPlayer = -1;
      ambushedPlayer = -1;
      ambush = false;
      interacting = false;
      return false;
    }
//    System.out.println(occupation[0][0] + "  " +
//                       occupation[0][1] + "  \n" + 
//                       occupation[1][0] + "  " +
//                       occupation[1][1] + "  \n sonu� : " + secondInteraction + "\n defeatedPlayer : " + defeatedPlayer);
    for( int i = 0 ; i < map.length ; i++){
      for(int j = 0 ; j< map[0].length ; j++){
        if(tempMap[i][j].getPlayerNo() != defeatedPlayer){
          currMap[i][j].setFigureID(tempMap[i][j].getFigureID()); 
          currMap[i][j].setPlayer(tempMap[i][j].getPlayerNo());
          currMap[i][j].setEvent(tempMap[i][j].getEvent());
          //System.out.print(currMap[i][j].getPlayerNo() + "  ");
        }
        else{
          currMap[i][j].setFigureID(0);
           currMap[i][j].setPlayer(0);
           currMap[i][j].setEvent(0);
        }
      }
      // System.out.println();
    }
    this.locatable.setFigureID(-1);
    interacting = false;
    defeatedPlayer = -1;
    ambushedPlayer = -1;
    ambush = false;
    return true;
    
    
  }
  
  public boolean remove(int x,int y, int gameType){
    if(gameType == 2)
      return false;
    else{
    if(currMap[x][y].getFigureID() == 0)
      return false;
    
    else{
      int toBeRemoved = currMap[x][y].getFigureID();
      //System.out.println("here2");
//      for(int i = 0 ; i < 5 ; i++){        
//        //System.out.println("here1");
//        for(int j = 0; j < 11 ; j++){
//          if(currMap[j][i] == toBeRemoved)
//            currMap[j][i] = 0;        
//        }        
//        //System.out.println("here3");
//      }
      removedFigure = toBeRemoved;      
      removeSteps(x,y);
      return true;
    }
    
    }
  }
  private void removeSteps(int x, int y){
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
  
  public void reset(Block [][] blankMap){
    defeatedPlayer = -1;
    for( int i = 0 ; i < blankMap.length ; i++){
      for(int j = 0 ; j< blankMap[0].length ; j++){
          currMap[i][j] = new Block();
          currMap[i][j].setFigureID(blankMap[i][j].getFigureID()); 
          currMap[i][j].setPlayer(blankMap[i][j].getPlayerNo());
          currMap[i][j].setEvent(blankMap[i][j].getEvent());
        }
    }
    
  }
  public int getRemovedFigure(){
    return removedFigure;
  }
  public Block[][] getCurrMap(){
    return currMap;
  }  
  public int getDefeatedPlayer(){
    return defeatedPlayer;
  }
  }
  
//  public void setLocation( int x1 , int y1){
//    this.locatable.setLocation( x1, y1);
//    
//  }
//  public int getX(){
//    return this.locatable.getX();
//    
//  }
//  public int getY(){
//    return this.locatable.getY();
//    
//  }
//  public void setFigureID( int a){}
//  public void setFigure(Figure a){}
//  
//}
