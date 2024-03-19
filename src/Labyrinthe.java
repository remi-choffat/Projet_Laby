import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Squelette de classe labyrinthe
 */
class Labyrinthe {

   private static final char MUR = 'X';
   private static final char PJ = 'P';
   private static final char SORTIE = 'S';
   private static final char VIDE = '.';
   private static final String HAUT = "haut";
   private static final String BAS = "bas";
   private static final String GAUCHE = "gauche";
   private static final String DROITE = "droite";


   private boolean[][] murs;
   private Personnage personnage;
   private Sortie sortie;


   public Labyrinthe(boolean[][] m, Personnage p, Sortie s) {
      this.murs = m;
      this.personnage = p;
      this.sortie = s;
   }


   char getChar(int x, int y) {
      if (x < 0 || x >= murs.length || y < 0 || y >= murs[0].length) {
         return MUR;
      }
      if (murs[x][y]) {
         return MUR;
      }
      if (personnage.etrePresent(x, y)) {
         return PJ;
      }
      if (sortie.etrePresent(x, y)) {
         return SORTIE;
      }
      return VIDE;
   }


   static int[] getSuivant(int x, int y, String direction) {
      if (direction.equals(HAUT)) {
         return new int[]{x - 1, y};
      } else if (direction.equals(BAS)) {
         return new int[]{x + 1, y};
      } else if (direction.equals(GAUCHE)) {
         return new int[]{x, y - 1};
      } else {
         return new int[]{x, y + 1};
      }
   }


   void deplacerPerso(String direction) throws ActionInconnueException {
      if (!(direction.equals(HAUT) || direction.equals(BAS) || direction.equals(GAUCHE) || direction.equals(DROITE))) {
         throw new ActionInconnueException(direction + " n'est pas une direction valide");
      }
      int[] suivant = getSuivant(personnage.getX(), personnage.getY(), direction);
      while (this.getChar(suivant[0], suivant[1]) == VIDE) {
         personnage.setX(suivant[0]);
         personnage.setY(suivant[1]);
         suivant = getSuivant(personnage.getX(), personnage.getY(), direction);
      }
   }


   public String toString() {
      String laby = "";
      for (int x = 0; x < murs.length; x++) {
         for (int y = 0; y < murs[0].length; y++) {
            laby += getChar(x, y) + "  ";
         }
         laby += "\n";
      }
      return laby;
   }


   public boolean etreFini() {
      return personnage.equals(sortie);
   }

   public static Labyrinthe chargerLabyrinthe(String nom) throws IOException, FichierIncorrectException {
      Personnage perso = null;
      Sortie sortie = null;
      BufferedReader file = new BufferedReader(new FileReader(nom));
      int x = Integer.parseInt(file.readLine());
      int y = Integer.parseInt(file.readLine());
      String c = "";
      boolean[][] murs = new boolean[x][y];
      int ligne = 0;
      while ((c = file.readLine()) != null) {
         if (c.length() != y)
            throw new FichierIncorrectException("nbColonnes ne correspond pas");
         if (ligne >= x)
            throw new FichierIncorrectException("nbLignes ne correspond pas");
         for (int colonne = 0; colonne < c.length(); colonne++) {
            if (c.charAt(colonne) == MUR) murs[ligne][colonne] = true;
            else {
               murs[ligne][colonne] = false;
               if (c.charAt(colonne) == PJ && perso != null)
                  throw new FichierIncorrectException("Plusieurs personnages");
               if (c.charAt(colonne) == PJ) perso = new Personnage(ligne, colonne);
               if (c.charAt(colonne) == SORTIE && sortie != null)
                  throw new FichierIncorrectException("Plusieurs sorties");
               if (c.charAt(colonne) == SORTIE) sortie = new Sortie(ligne, colonne);
            }
         }
         ligne++;
      }
      if (ligne < x)
         throw new FichierIncorrectException("nbLignes ne correspond pas");
      if (perso == null) throw new FichierIncorrectException("Personnage inconnu");
      if (sortie == null) throw new FichierIncorrectException("Sortie inconnue");

      file.close();
      return new Labyrinthe(murs, perso, sortie);
   }


   public static void main(String[] args) throws IOException, ActionInconnueException, FichierIncorrectException {

      Labyrinthe laby = Labyrinthe.chargerLabyrinthe("./Projet_Laby/laby/laby_pasSortie.txt");
      System.out.println(laby);

   }
}
