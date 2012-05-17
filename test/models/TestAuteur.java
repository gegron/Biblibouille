package models;

import java.util.List;

import org.junit.Test;

import config.BasicTest;

import play.test.UnitTest;

/**
 * Test du modèle Auteur
 * 
 * @author Legunda
 *
 */
public class TestAuteur extends BasicTest {

	@Test
	public void save() {
		String nomTest = "Skywalker";
		String prenomTest = "Luke";
		
		List<Auteur> auteurs = Auteur.find("byNomAndPrenom", nomTest, prenomTest).fetch();
		
		assertEquals("L'auteur ne doit pas exister",0 , auteurs.size());
		
		new Auteur(nomTest, prenomTest, null, null, null).save();

		auteurs.clear();
		auteurs = Auteur.find("byNomAndPrenom", nomTest, prenomTest).fetch();
		
		assertEquals("Il devrait y avoir un auteur", 1, auteurs.size());
	
		assertEquals("Ce n'est pas le nom attendu", nomTest, auteurs.get(0).nom);
		assertEquals("Ce n'est pas le prénom attendu", prenomTest, auteurs.get(0).prenom);
	}

	/**
	 * Test de la méthode miseAJourLibelle
	 * On vérifie que le libellé se met bien à jour dès que l'on change le nom ou le prénom de l'auteur 
	 */
	@Test
	public void miseAJourLibelle_OK() {
		Auteur auteur = new Auteur("Skywalker", null, null, null, null);
		assertEquals("Ce n'est pas le libellé attendu", "Skywalker", auteur.libelle);
		
		auteur.setPrenom("Luke");
		assertEquals("Ce n'est pas le libellé attendu", "Luke Skywalker", auteur.libelle);
		
		auteur.setPrenom("Anakin");
		assertEquals("Ce n'est pas le libellé attendu", "Anakin Skywalker", auteur.libelle);
	}
	
}
