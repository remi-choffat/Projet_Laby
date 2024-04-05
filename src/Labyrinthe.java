import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Représente un labyrinthe (murs, personnage, sortie)
 */
class Labyrinthe {

   // Constantes qui représentent les caractères du labyrinthe
   public static final char MUR = 'X';
   public static final char PJ = 'P';
   public static final char SORTIE = 'S';
   public static final char VIDE = '.';

   // Constantes qui représentent les directions possibles
   public static final String HAUT = "haut";
   public static final String BAS = "bas";
   public static final String GAUCHE = "gauche";
   public static final String DROITE = "droite";
   public static final String ESP = " ";

   /**
    * Tableau 2D de booléens représentant le labyrinthe (murs)
    */
   private final boolean[][] murs;
   /**
    * Personnage qui se déplace dans le labyrinthe
    */
   private final Personnage personnage;
   /**
    * Sortie du labyrinthe
    */
   private final Sortie sortie;

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
      switch (direction) {
         case HAUT:
            return new int[]{x - 1, y};
         case BAS:
            return new int[]{x + 1, y};
         case GAUCHE:
            return new int[]{x, y - 1};
         case DROITE:
            return new int[]{x, y + 1};
         default:
            return new int[]{x, y};
      }
   }

   /**
    * Déplace le personnage dans la direction donnée.
    *
    * @param direction Direction à prendre.
    * @throws ActionInconnueException Si la direction donnée n'est pas valide.
    */
   void deplacerPerso(String direction) throws ActionInconnueException {
      // Vérifie que la direction est valide
      if (!(direction.equals(HAUT) || direction.equals(BAS) || direction.equals(GAUCHE) || direction.equals(DROITE))) {
         throw new ActionInconnueException(direction + " n'est pas une direction valide");
      }
      // Récupère les coordonnées du prochain déplacement
      int[] suivant = getSuivant(personnage.getX(), personnage.getY(), direction);
      // Déplace le personnage jusqu'à rencontrer un mur
      while (this.getChar(suivant[0], suivant[1]) != MUR) {
         personnage.setX(suivant[0]);
         personnage.setY(suivant[1]);
         suivant = getSuivant(personnage.getX(), personnage.getY(), direction);
      }
   }

   /**
    * Renvoie une représentation du labyrinthe.
    *
    * @return Représentation en chaîne de caractères du labyrinthe.
    */
   public String toString() {
      String laby = "";
      for (int x = 0; x < murs.length; x++) {
         for (int y = 0; y < murs[0].length; y++) {
            // Affiche tous les caractères colorés
            laby += colorer(ESP + getChar(x, y) + ESP);
         }
         laby += "\n";
      }
      return laby;
   }

   /**
    * Renvoie le caractère avec une couleur qui dépend de son type.
    *
    * @param c Caractère à colorer.
    * @return Caractère coloré.
    */
   public String colorer(String c) {
      switch (c) {
         case ESP + MUR + ESP:
            // Mur (sur fond violet)
            return "\u001B[45m" + c + "\u001B[0m";
         case ESP + PJ + ESP:
            // Personnage sur la sortie (bleu sur fond jaune)
            if (personnage.equals(sortie)) return "\u001B[36;43m" + c + "\u001B[0m";
               // Personnage (bleu)
            else return "\u001B[36m" + c + "\u001B[0m";
         case ESP + SORTIE + ESP:
            // Sortie (sur fond jaune)
            return "\u001B[43m" + c + "\u001B[0m";
         default:
            // Vide (blanc / couleur par défaut)
            return c;
      }
   }

   /**
    * Vérifie si le jeu est terminé, c'est-à-dire si le personnage a atteint la sortie.
    *
    * @return True si le jeu est terminé, false sinon.
    */
   public boolean etreFini() {
      // Vrai si le personnage est sur la sortie
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
      // Initialisation des variables
      Personnage perso = null;
      Sortie sortie = null;
      BufferedReader file = new BufferedReader(new FileReader(nom));
      int x = 0;
      int y = 0;
      // Teste si les deux premières lignes sont des entiers
      try {
         x = Integer.parseInt(file.readLine());
         y = Integer.parseInt(file.readLine());
      } catch (NumberFormatException e) {
         throw new FichierIncorrectException("nbLignes ou nbColonnes non entier");
      }
      String c = "";
      boolean[][] murs = new boolean[x][y];
      int ligne = 0;
      // Parcourt le fichier ligne par ligne
      while ((c = file.readLine()) != null) {
         // Teste si la ligne a le bon nombre de colonnes
         if (c.length() != y)
            throw new FichierIncorrectException("nbColonnes ne correspond pas");
         // Teste si le fichier a le bon nombre de lignes
         if (ligne >= x)
            throw new FichierIncorrectException("nbLignes ne correspond pas");
         // Parcourt la ligne caractère par caractère
         for (int colonne = 0; colonne < c.length(); colonne++) {
            char car = c.charAt(colonne);
            // Si le caractère correspond à un mur, le labyrinthe a un mur (true) à cette position
            if (car == MUR) murs[ligne][colonne] = true;
            else {
               murs[ligne][colonne] = false;
               // Si un personnage est déjà présent, le fichier est incorrect
               if (car == PJ && perso != null)
                  throw new FichierIncorrectException("Plusieurs personnages");
               // Crée un personnage à la position donnée
               if (car == PJ) perso = new Personnage(ligne, colonne);
               // Si une sortie est déjà présente, le fichier est incorrect
               if (car == SORTIE && sortie != null)
                  throw new FichierIncorrectException("Plusieurs sorties");
               // Crée une sortie à la position donnée
               if (car == SORTIE) sortie = new Sortie(ligne, colonne);
               // Si le caractère n'est pas un mur, un personnage, une sortie ou un espace, le fichier est incorrect
               if (car != PJ && car != SORTIE && car != VIDE)
                  throw new FichierIncorrectException("Caractère inconnu <" + car + ">");
            }
         }
         ligne++;
      }
      // Teste si le fichier a le bon nombre de lignes
      if (ligne < x)
         throw new FichierIncorrectException("nbLignes ne correspond pas");
      // Teste si le personnage et la sortie sont bien présents
      if (perso == null) throw new FichierIncorrectException("Personnage inconnu");
      if (sortie == null) throw new FichierIncorrectException("Sortie inconnue");
      // Ferme le fichier
      file.close();
      // Retourne un nouveau labyrinthe
      return new Labyrinthe(murs, perso, sortie);
   }

}
