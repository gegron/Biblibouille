package models;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import config.BasicTest;
import enums.TypeUtilisateur;

/**
 * Classe de test du modèle <code>Utilisateur</code>
 * 
 * @author Legunda
 *
 */
public class TestUtilisateur extends BasicTest {

	/**
	 * Test de la sauvegarde d'un utilisateur
	 */
	@Test
	public void save() {
		String loginTest = "luke";
		String passwordTest = "forceIsStrong";
		String emailTest = "luke@jedi.com";
		TypeUtilisateur typeUtilisateurTest = TypeUtilisateur.ADMIN;

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.login = loginTest;
		utilisateur.password = passwordTest;
		utilisateur.email = emailTest;
		utilisateur.typeUtilisateur = typeUtilisateurTest;
		
		assertEquals(0, Utilisateur.find("byLogin", loginTest).fetch().size());
		// Lancement de la méthode testée
		utilisateur.save();
		
		// Asserts
		List<Utilisateur> resultats = Utilisateur.find("byLogin", loginTest).fetch();
		assertEquals(1, resultats.size());
		
		assertEquals(loginTest, resultats.get(0).login);
		assertEquals(passwordTest, resultats.get(0).password);
		assertEquals(emailTest, resultats.get(0).email);
		assertEquals(typeUtilisateurTest, resultats.get(0).typeUtilisateur);
	}
	
	/**
	 * On vérifie que l'on ne peut pas sauvegarder deux utilisateurs ayant le même id
	 * 
	 * Pour notre jeu de test, seul les logins sont identiques
	 */
	@Test
	public void verifContrainteUnicite() {
		String loginTest = "luke";

		Utilisateur u1 = new Utilisateur();
		u1.login = loginTest;
		u1.password = "p1";
		u1.email = "u1@jedi.com";
		u1.typeUtilisateur = TypeUtilisateur.ADMIN;
		
		Utilisateur u2 = new Utilisateur();
		u2.login = loginTest;
		u2.password = "p2";
		u2.email = "u2@jedi.com";
		u2.typeUtilisateur = TypeUtilisateur.ADMIN;
		
		assertEquals(0, Utilisateur.find("byLogin", loginTest).fetch().size());
		u1.save();
		assertEquals(1, Utilisateur.find("byLogin", loginTest).fetch().size());
		
		try {
			u2.save();
			fail("Une exception doit être levée dû à la violation de la contrainte d'unicité sur le login");
		} catch (PersistenceException e){
			// OK
		}
	}
}
