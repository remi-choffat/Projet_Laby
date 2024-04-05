/**
 * Représente une position dans le labyrinthe
 */
public class Position {

   /**
    * Coordonnées X et Y.
    */
   private int x, y;

   /**
    * Constructeur pour la classe Position.
    *
    * @param x Coordonnée X.
    * @param y Coordonnée Y.
    */
   public Position(int x, int y) {
      this.x = x;
      this.y = y;
   }

   /**
    * Renvoie la coordonnée X.
    *
    * @return Coordonnée X.
    */
   public int getX() {
      return x;
   }

   /**
    * Renvoie la coordonnée Y.
    *
    * @return Coordonnée Y.
    */
   public int getY() {
      return y;
   }

   /**
    * Modifie la coordonnée X.
    *
    * @param x Nouvelle coordonnée X.
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    * Modifie la coordonnée Y.
    *
    * @param y Nouvelle coordonnée Y.
    */
   public void setY(int y) {
      this.y = y;
   }

   /**
    * Indique une équivalence entre deux positions.
    *
    * @param p Position à comparer.
    * @return Vrai si les positions sont équivalentes, faux sinon.
    */
   public boolean equals(Position p) {
      return (this.x == p.getX() && this.y == p.getY());
   }

   /**
    * Indique si la position est présente aux coordonnées données.
    *
    * @param x Coordonnée X.
    * @param y Coordonnée Y.
    * @return Vrai si la position est présente, faux sinon.
    */
   public boolean etrePresent(int x, int y) {
      return (this.x == x && this.y == y);
   }

}
