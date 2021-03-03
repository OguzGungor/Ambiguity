
public interface Locatable{
  
  public LocatableFigure locatable = new LocatableFigure();  

  
  public class LocatableFigure{
    private int x;
    private int y;
    private int figureID;
    private Figure selected; 
    
    public void setLocation(int x, int y){
      this.x = x;
      this.y = y;
    }
    public void setFigureID(int figureID){
      this.figureID = figureID;  
    }
    
    public void rotate(boolean dir, boolean sym){
      selected.rotate(dir,sym);
    } 
    
    public void setFigure( Figure selected){
      
      this.selected = selected;
    }
    
    public int getX(){
      return x;
    }
    public int getY(){
      return y;
      
    }
    public int getSelectedFigureID(){
    
      return figureID;
    }
    public Figure getFigure(){
    
      return selected;
    }
    
    
  }
}