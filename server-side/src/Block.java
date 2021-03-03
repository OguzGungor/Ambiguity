/**
 * Auto Generated Java Class.
 */
public class Block {

  Block() {
    next = new int[2];
    next[0] = -1;
    next[1] = -1;

  }

  Block(Block copy) {
    this.playerNo = copy.playerNo;
    this.next = copy.next;
    this.figureID = copy.figureID;
    this.interacted = copy.interacted;
    this.pathNo = copy.pathNo;
    this.embeddedPlayerID = copy.embeddedPlayerID;
    this.eventID = copy.eventID;
  }

  private int playerNo;
  private int[] next;
  private int figureID;
  private int interacted;
  private int pathNo;
  private int embeddedPlayerID;
  private int eventID;/*
                       * 0 = no event; 1 = capital+ 2 = mines+ 3 = ambush+ 4 = supply 5 = hill+ 6 =
                       * resistance 7 = merchants 8 = river+
                       */

  public void setPlayer(int player) {
    playerNo = player;

  }

  public void setFigureID(int figure) {
    figureID = figure;
  }

  public int getPlayerNo() {
    return playerNo;
  }

  public int getFigureID() {
    return figureID;
  }

  public void setEvent(int event) {
    eventID = event;
  }

  public int getEvent() {
    return eventID;
  }

  public void setInteracted(int interacted) {
    this.interacted = interacted;
  }

  public int getInteracted() {

    return interacted;
  }

  public int getPathNo() {

    return pathNo;
  }

  public void setPathNo(int pathNo) {

    this.pathNo = pathNo;
  }

  public void setEmbeddedPlayerID(int playerID) {

    embeddedPlayerID = playerID;
  }

  public int getEmbeddedPlayerID() {
    ;
    return embeddedPlayerID;
  }

  public void setNext(int x, int y) {

    next[0] = x;
    next[1] = y;
  }

  public int[] getNext() {

    return next;
  }
}
