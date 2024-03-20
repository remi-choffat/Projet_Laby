import java.util.Scanner;

/**
 * Classe principale permettant de jouer
 */
public class MainLaby {

   public static Labyrinthe labyOK(String nom, Scanner sc) {
      Labyrinthe laby = null;
      try {
         laby = Labyrinthe.chargerLabyrinthe(nom);

      } catch (Exception e) {
         System.out.println("\u001B[91m" + e.getMessage() + "\u001B[0m\n");
         System.out.print("Veuillez entrer un nom de fichier correct : ");
         laby = labyOK(sc.nextLine(), sc);
      }
      return laby;
   }

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      Labyrinthe laby = labyOK(args[0], sc);
      System.out.println("\033[H\033[2J");
      System.out.println(laby);

      while (!laby.etreFini()) {

         System.out.print("Entrez une direction (haut, bas, gauche, droite) : ");
         String direction = sc.nextLine();
         System.out.println("\033[H\033[2J");
         if (direction.equalsIgnoreCase("exit")) {
            System.out.println("Vous avez quitté le jeu");
            return;
         }
         try {
            laby.deplacerPerso(direction);
         } catch (ActionInconnueException e) {
            System.out.println("\u001B[91m" + e.getMessage() + "\u001B[0m\n");
         }
         System.out.println(laby);
      }
      System.out.println("\u001B[33mLe jeu est terminé, vous avez gagné\u001B[0m\n");

   }

}
