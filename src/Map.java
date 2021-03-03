/**
 * Auto Generated Java Class.
 */
public class Map {
  
  private int[][] twoDMap;
  private int[][] versus;
  
  
  Map(){
    twoDMap = new int[11][5];
    versus = new int[45][20];
    
  }  
  
  public int[][] getMap(int id){  
    
    if(id == 1)
      return versus;
    else
      return twoDMap;
  }
  
}
