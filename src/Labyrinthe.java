import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Représente un labyrinthe (murs, personnage, sortie)
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

   /**
    * Constructeur pour la classe Labyrinthe.
    *
    * @param m tableau 2D de booléens représentant le labyrinthe.
    * @param p Personnage dans le labyrinthe.
    * @param s Sortie du labyrinthe.
    */
   public Labyrinthe(boolean[][] m, Personnage p, Sortie s) {
      this.murs = m;
      this.personnage = p;
      this.sortie = s;
   }

   /**
    * Renvoie le caractère aux coordonnées données dans le labyrinthe.
    *
    * @param x Coordonnée X.
    * @param y Coordonnée Y.
    * @return Caractère aux coordonnées données.
    */
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

   /**
    * Renvoie les prochaines coordonnées dans la direction donnée.
    *
    * @param x         Coordonnée X.
    * @param y         Coordonnée Y.
    * @param direction Direction à prendre.
    * @return Tableau de deux entiers représentant les prochaines coordonnées.
    */
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

   /**
    * Déplace le personnage dans la direction donnée.
    *
    * @param direction Direction à prendre.
    * @throws ActionInconnueException Si la direction donnée n'est pas valide.
    */
   void deplacerPerso(String direction) throws ActionInconnueException {
      if (!(direction.equals(HAUT) || direction.equals(BAS) || direction.equals(GAUCHE) || direction.equals(DROITE))) {
         throw new ActionInconnueException(direction + " n'est pas une direction valide");
      }
      int[] suivant = getSuivant(personnage.getX(), personnage.getY(), direction);
      while (this.getChar(suivant[0], suivant[1]) != MUR) {
         personnage.setX(suivant[0]);
         personnage.setY(suivant[1]);
         suivant = getSuivant(personnage.getX(), personnage.getY(), direction);
      }
   }

   /**
    * Renvoie une représentation en chaîne de caractères du labyrinthe.
    *
    * @return Représentation en chaîne de caractères du labyrinthe.
    */
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

   /**
    * Vérifie si le jeu est terminé, c'est-à-dire si le personnage a atteint la sortie.
    *
    * @return True si le jeu est terminé, false sinon.
    */
   public boolean etreFini() {
      return personnage.equals(sortie);
   }

   /**
    * Charge un labyrinthe à partir d'un fichier.
    *
    * @param nom Nom du fichier.
    * @return Objet Labyrinthe.
    * @throws IOException               Si une erreur d'Entrée/Sortie se produit.
    * @throws FichierIncorrectException Si le fichier n'est pas correctement formaté.
    */
   public static Labyrinthe chargerLabyrinthe(String nom) throws IOException, FichierIncorrectException {
      Personnage perso = null;
      Sortie sortie = null;
      BufferedReader file = new BufferedReader(new FileReader(nom));
      int x = 0;
      int y = 0;
      try {
         x = Integer.parseInt(file.readLine());
         y = Integer.parseInt(file.readLine());
      } catch (NumberFormatException e) {
         throw new FichierIncorrectException("nbLignes ou nbColonnes non entier");
      }
      String c = "";
      boolean[][] murs = new boolean[x][y];
      int ligne = 0;
      while ((c = file.readLine()) != null) {
         if (c.length() != y)
            throw new FichierIncorrectException("nbColonnes ne correspond pas");
         if (ligne >= x)
            throw new FichierIncorrectException("nbLignes ne correspond pas");
         for (int colonne = 0; colonne < c.length(); colonne++) {
            char car = c.charAt(colonne);
            if (car == MUR) murs[ligne][colonne] = true;
            else {
               murs[ligne][colonne] = false;
               if (car == PJ && perso != null)
                  throw new FichierIncorrectException("Plusieurs personnages");
               if (car == PJ) perso = new Personnage(ligne, colonne);
               if (car == SORTIE && sortie != null)
                  throw new FichierIncorrectException("Plusieurs sorties");
               if (car == SORTIE) sortie = new Sortie(ligne, colonne);
               if (car != PJ && car != SORTIE && car != VIDE)
                  throw new FichierIncorrectException("Caractère inconnu <" + car + ">");
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

}
