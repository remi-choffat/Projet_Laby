import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test
 */
public class TestLaby {

   /**
    * test de la methode etreFini
    */
   @Test
   public void test_etreFini() throws Exception {
      // utilise laby0.txt
      Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");

      // test si le jeu n'est pas fini (n'a pas commencé)
      assertEquals(l.etreFini(), false);

      // déplace le personnage jusqu'à la sortie
      l.deplacerPerso(Labyrinthe.HAUT);
      l.deplacerPerso(Labyrinthe.GAUCHE);

      // test si le jeu est fini
      assertEquals(l.etreFini(), true);
   }


   /**
    * test de la methode getSuivant
    */
   @Test
   public void test_getSuivant() throws Exception {
      int[] res;
      res = Labyrinthe.getSuivant(1, 1, Labyrinthe.HAUT);
      assertEquals(res[0], 0);
      assertEquals(res[1], 1);
      res = Labyrinthe.getSuivant(1, 1, Labyrinthe.BAS);
      assertEquals(res[0], 2);
      assertEquals(res[1], 1);
      res = Labyrinthe.getSuivant(1, 1, Labyrinthe.DROITE);
      assertEquals(res[0], 1);
      assertEquals(res[1], 2);
      res = Labyrinthe.getSuivant(1, 1, Labyrinthe.GAUCHE);
      assertEquals(res[0], 1);
      assertEquals(res[1], 0);
   }


   /**
    * test de la methode deplacerPerso - Déplacement possible
    */
   @Test
   public void test_deplacerPerso_OK() throws Exception {
      // utilise laby0.txt
      Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");

      // test de deplacerPerso
      l.deplacerPerso(Labyrinthe.HAUT);
      assertEquals(l.getChar(2, 3), Labyrinthe.VIDE);
      assertEquals(l.getChar(1, 3), Labyrinthe.PJ);
   }


   /**
    * test de la methode deplacerPerso - Déplacement impossible
    */
   @Test
   public void test_deplacerPerso_nein() throws Exception {
      // utilise laby0.txt
      Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");

      // test de deplacerPerso
      l.deplacerPerso(Labyrinthe.HAUT);
      // déplacement contre un mur
      l.deplacerPerso(Labyrinthe.HAUT);
      assertEquals(l.getChar(2, 3), Labyrinthe.VIDE);
      assertEquals(l.getChar(1, 3), Labyrinthe.PJ);
   }

}
