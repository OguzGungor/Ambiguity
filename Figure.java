import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Figure {
   
  
   private Block[][] shape = new Block[4][4];
   private int figureID;
   private Image image = null;
   
   Figure( int[][] shape, int id ){   
      figureID = id;
      int[][] temp = new int[4][4];
      for(int i = 0;i<4;i++) {
            System.arraycopy(shape[i], 0, temp[i], 0, temp[i].length);
      }   
      try {
         image = ImageIO.read(new File("./images/figures/figure_" + id + ".png"));
      } catch (IOException e) {
      }
      for(int i = 0 ; i < 4 ; i++){
        for(int j = 0; j < 4 ; j++){
          this.shape[i][j] = new Block();
          this.shape[i][j].setFigureID(temp[i][j]);        
        }
      }
      
   }
   public void rotate(boolean direction, boolean symetri){

        int[][] temp;
        temp = new int[4][4];
        if(symetri){

            for(int i = 0;i<4;i++) {
              for( int j = 0; j < 4; j++){
                temp[i][j] = shape[i][j].getFigureID();
              }
            }

            for(int i = 0; i<2;i++) {
              for(int j = 0 ; j < 4 ; j++){
                shape[i][j].setFigureID(temp[3-i][j]);
                shape[3-i][j].setFigureID(temp[i][j]);
              }
              }
        }

        if(direction){
            for (int i = 0; i < 4; i++){
                for (int j =0; j<4; j++){
                    temp[i][j] = shape [3-j][i].getFigureID();
                }
            }
//            for(int i = 0;i<4;i++) {
//                System.arraycopy(temp[i], 0, shape[i], 0, temp[i].length);
//            }
            for( int i = 0; i < 4 ; i++){
              for(int j = 0; j < 4 ; j++){
                shape[i][j].setFigureID(temp[i][j]);              
              }
            
            }

        }

    }
   public Block[][] getShape(){
        return shape;
    }
   
   public Image getImage(){
     
     return image;
   }
   
   
   
}
