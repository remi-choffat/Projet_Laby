import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test
 */
public class TestLaby {

   /**
    * Test de la methode etreFini
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
    * Test de la methode getSuivant
    */
   @Test
   public void test_getSuivant() {
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
    * Test de la methode getSuivant - direction inconnue
    */
   @Test
   public void test_getSuivant_dir_inconnue() {
      int[] res;
      res = Labyrinthe.getSuivant(1, 1, "au-dessus");
      assertEquals(res[0], 1);
      assertEquals(res[1], 1);
   }


   /**
    * Test de la methode deplacerPerso - Déplacement possible
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
    * Test de la methode deplacerPerso - Déplacement impossible
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


   /**
    * Test de la methode deplacerPerso - Case en dehors des limites du labyrinthe
    */
   @Test
   public void test_deplacerPerso_out() throws Exception {
      Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby_ou_sont_les_murs.txt");
      // déplacement hors du labyrinthe
      l.deplacerPerso(Labyrinthe.HAUT);
      assertEquals(l.getChar(0, 3), Labyrinthe.PJ);
   }


   /**
    * Test de la methode chargerLabyrinthe
    */
   @Test
   public void test_chargerLabyrinthe() throws Exception {
      // utilise laby0.txt
      Labyrinthe l = Labyrinthe.chargerLabyrinthe("laby/laby0.txt");
      assertEquals(l.getChar(0, 0), Labyrinthe.MUR);
      assertEquals(l.getChar(1, 1), Labyrinthe.SORTIE);
      assertEquals(l.getChar(2, 3), Labyrinthe.PJ);
      assertEquals(l.getChar(2, 1), Labyrinthe.VIDE);
   }


   /**
    * Test de la méthode chargerLabyrinthe avec un fichier non rectangulaire
    */
   @Test
   public void test_chargerLabyrinthe_nonrect() {
      try {
         Labyrinthe.chargerLabyrinthe("laby/laby_nonrect.txt");
      } catch (Exception e) {
         assertEquals(e.getMessage(), "nbColonnes ne correspond pas");
      }
   }

}
