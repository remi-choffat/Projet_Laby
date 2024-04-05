import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principale permettant de jouer
 */
public class MainLaby {

   /**
    * Charge un labyrinthe à partir d'un nom de fichier
    * Si le nom de fichier n'est pas valide, on demande un autre nom de fichier
    *
    * @param nom Nom du fichier
    * @param sc  Scanner pour lire l'entrée utilisateur
    * @return Labyrinthe chargé
    */
   public static Labyrinthe labyOK(String nom, Scanner sc) {
      Labyrinthe laby = null;
      // Essaie de charger le labyrinthe
      try {
         laby = Labyrinthe.chargerLabyrinthe(nom);
      } catch (IOException e) {
         // Erreur d'entrée/sortie
         System.out.println("\u001B[91mLe fichier " + nom + " est incorrect\u001B[0m\n");
         System.out.print("Veuillez entrer un nom de fichier correct : ");
         laby = labyOK(sc.nextLine(), sc);
      } catch (FichierIncorrectException e) {
         // Fichier incorrect
         System.out.println("\u001B[91m" + e.getMessage() + "\u001B[0m\n");
         System.out.print("Veuillez entrer un nom de fichier correct : ");
         laby = labyOK(sc.nextLine(), sc);
      } catch (Exception e) {
         // Autre erreur
         System.out.println("\u001B[91m" + e.getMessage() + "\u001B[0m\n");
         System.out.print("Veuillez entrer un nom de fichier correct : ");
         laby = labyOK(sc.nextLine(), sc);
      }
      // Renvoie le labyrinthe (correct) chargé
      return laby;
   }

   /**
    * Main permettant de lancer le jeu
    */
   public static void main(String[] args) {

      try {
         // On vérifie que le fichier est bien passé en argument (et qu'il y en a bien un seul)
         if (args.length != 1) {
            throw new IllegalArgumentException("Veuillez entrer 1 nom de fichier en argument");
         }
      } catch (IllegalArgumentException e) {
         System.out.println("\u001B[91m" + e.getMessage() + "\u001B[0m\n");
         return;
      }
      Scanner sc = new Scanner(System.in);
      // On charge le labyrinthe
      Labyrinthe laby = labyOK(args[0], sc);
      // On efface la console et on affiche le labyrinthe
      System.out.println("\033[H\033[2J");
      System.out.println(laby);

      // Tant que le jeu n'est pas terminé
      while (!laby.etreFini()) {
         // On demande une direction
         System.out.print("Entrez une direction (haut, bas, gauche, droite) : ");
         String direction = sc.nextLine();
         System.out.println("\033[H\033[2J");
         // Si la direction est "exit", on quitte le jeu
         if (direction.equalsIgnoreCase("exit")) {
            System.out.println("Vous avez quitté le jeu");
            return;
         }
         try {
            // On essaye de déplacer le personnage
            laby.deplacerPerso(direction);
         } catch (ActionInconnueException e) {
            // Si la direction n'est pas valide, on affiche un message d'erreur
            System.out.println("\u001B[91m" + e.getMessage() + "\u001B[0m\n");
         }
         System.out.println(laby);
      }
      // Si le jeu est terminé, on affiche un message de victoire
      System.out.println("\u001B[33mLe jeu est terminé, vous avez gagné\u001B[0m\n");

   }

}
