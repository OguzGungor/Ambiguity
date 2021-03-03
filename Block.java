/**
 * Auto Generated Java Class.
 */
public class Block {
  
  private int playerNo;
  private int figureID;
  private int eventID;/* 1 = capital+
                       * 2 = mines+
                       * 3 = ambush+
                       * 4 = supply
                       * 5 = hill+
                       * 6 = resistance
                       * 7 = merchants
                       * 8 = river+
                       */ 
  
  public void setPlayer( int player){
    playerNo = player;
  
  }
  
  public void setFigureID( int figure){
   figureID = figure;  
  }
  public int getPlayerNo(){
    return playerNo;
  }
  public int getFigureID(){
    return figureID;
  }
  public void setEvent(int event){  
    eventID = event;
  }
  public int getEvent(){
    return eventID;
  }

  public int getEmbeddedPlayerID() {
    return 0;
  }
}
