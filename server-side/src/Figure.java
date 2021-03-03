import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Figure {

  private Block[][] INITIAL_SHAPE = new Block[4][4];
  private Block[][] shape = new Block[4][4];
  private int figureID;
  private Image image = null;

  Figure(int[][] shape, int id) {
    figureID = id;
    int[][] temp = new int[4][4];
    for (int i = 0; i < 4; i++) {
      System.arraycopy(shape[i], 0, temp[i], 0, temp[i].length);
    }
    try {
      image = ImageIO.read(new File("./images/figures/figure_" + id + ".png"));
    } catch (IOException e) {
    }
    int count = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {

        this.shape[i][j] = new Block();
        if (temp[i][j] != 0) {
          try {

            if (temp[i][j + 1] != 0)
              count++;
          } catch (Exception ex) {
            // System.out.println("catched : " + id + " " + i + " " + j);
          }
          try {
            if (temp[i + 1][j] != 0)
              count++;
          } catch (Exception ex) {
            // System.out.println("catched1 : " + id+ " " + i + " " + j);
          }
          try {
            if (temp[i - 1][j] != 0)
              count++;
          } catch (Exception ex) {
            // System.out.println("catched2 : " + id+ " " + i + " " + j);
          }
          try {
            if (temp[i][j - 1] != 0)
              count++;
          } catch (Exception ex) {
            // System.out.println("catched3 : " + id+ " " + i + " " + j);
          }
          // System.out.println("catched5 : " + id + " " + i + " " + j + " " + count);
          this.shape[i][j].setFigureID(temp[i][j]);
          this.shape[i][j].setInteracted(count);
          count = 0;
        }
      }
    }
    this.INITIAL_SHAPE = new Block[this.shape.length][this.shape[0].length];
    for (int i = 0; i < this.INITIAL_SHAPE.length; i++) {
      for (int k = 0; k < this.INITIAL_SHAPE[0].length; k++) {
        this.INITIAL_SHAPE[i][k] = new Block(this.shape[i][k]);
      }
    }
  }

  public void rotate(boolean direction, boolean symetri) {

    Block[][] temp;
    temp = new Block[4][4];
    if (symetri) {

      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          temp[i][j] = new Block();
          temp[i][j].setFigureID(shape[i][j].getFigureID());
          temp[i][j].setInteracted(shape[i][j].getInteracted());
        }
      }

      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 4; j++) {
          shape[i][j].setFigureID(temp[3 - i][j].getFigureID());
          shape[i][j].setInteracted(temp[3 - i][j].getInteracted());
          shape[3 - i][j].setFigureID(temp[i][j].getFigureID());
          shape[3 - i][j].setInteracted(temp[i][j].getInteracted());
        }
      }
    }

    if (direction) {
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          temp[i][j] = new Block();
          temp[i][j].setFigureID(shape[3 - j][i].getFigureID());
          temp[i][j].setInteracted(shape[3 - j][i].getInteracted());
        }
      }
      // for(int i = 0;i<4;i++) {
      // System.arraycopy(temp[i], 0, shape[i], 0, temp[i].length);
      // }
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          shape[i][j].setFigureID(temp[i][j].getFigureID());
          shape[i][j].setInteracted(temp[i][j].getInteracted());
        }

      }

    }

  }

  public Block[][] getShape() {
    return shape;
  }

  public Image getImage() {

    return image;
  }

  public void resetShape() {
    for (int i = 0; i < this.INITIAL_SHAPE.length; i++) {
      for (int k = 0; k < this.INITIAL_SHAPE[0].length; k++) {
        this.shape[i][k] = new Block(this.INITIAL_SHAPE[k][i]);
      }
    }
    System.out.println("[Figure] Reset Shape START");
    for (Block[] arr : this.INITIAL_SHAPE) {
      for (Block element : arr) {
        System.out.print(element.getFigureID() + " ");
      }
      System.out.println();
    }
    System.out.println("[Figure] Reset Shape END");
  }

}
