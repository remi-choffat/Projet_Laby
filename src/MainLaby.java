import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principale permettant de jouer
 */
public class MainLaby {

   // TODO Faire en sorte de gérer les exceptions FichierIncorrectException
   public static void main(String[] args) throws IOException, FichierIncorrectException {

      Labyrinthe laby = Labyrinthe.chargerLabyrinthe(args[0]);
      System.out.println(laby);
      Scanner sc = new Scanner(System.in);
      while (!laby.etreFini()) {
         System.out.println("Entrez une direction (haut, bas, gauche, droite) : ");
         String direction = sc.nextLine();
         if (direction.equalsIgnoreCase("exit")) {
            System.out.println("Vous avez quitté le jeu");
            return;
         }
         try {
            laby.deplacerPerso(direction);
         } catch (ActionInconnueException e) {
            System.out.println(e.getMessage());
         }
         System.out.println(laby);
      }
      System.out.println("Le jeu est terminé, vous avez gagné");

   }

}
