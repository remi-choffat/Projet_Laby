/**
 * Représente une position dans le labyrinthe
 */
public class Position {

   private int x, y;

   public Position(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public boolean equals(Position p) {
      return (this.x == p.getX() && this.y == p.getY());
   }

   public boolean etrePresent(int x, int y) {
      return (this.x == x && this.y == y);
   }

}
