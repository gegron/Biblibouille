package models;

import java.util.List;

import org.junit.Test;

import config.BasicTest;

/**
 * Classe de test du modèle <code>Livre</code>
 * 
 * @author Legunda
 *
 */
public class TestLivre extends BasicTest {

	/**
	 * Test de la sauvegarde d'un livre
	 */
	@Test
	public void save() {
		String titreTest = "L'avenir de l'humanité";
		String collectionTest = "Gallimard";
		Auteur auteurTest = new Auteur("Dupont", "Hervé", null, null, null).save();
		
		Livre livre = new Livre();
		livre.titre = titreTest;
		livre.collection = collectionTest;
		livre.auteur = auteurTest;
		
		assertEquals("Le livre ne doit pas être présent en base", 0, Livre.find("byTitre", titreTest).fetch().size());
		
		// Appel de la sauvegarde
		livre.save();
		
		List<Livre> resultats = Livre.find("byTitre", titreTest).fetch();
		assertEquals("Le livre doit être présent en base", 1, resultats.size());
		assertEquals(titreTest, resultats.get(0).titre);
		assertEquals(collectionTest, resultats.get(0).collection);
		assertEquals(auteurTest.nom, resultats.get(0).auteur.nom);
		assertEquals(auteurTest.prenom, resultats.get(0).auteur.prenom);
	}
	
	
}
