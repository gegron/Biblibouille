package controllers;

import enums.TypeUtilisateur;
import models.Utilisateur;

/**
 * On surcharge la classe fournit par défaut pour configurer notre gestionnaire de connexion
 * 
 * @author Legunda
 *
 */
public class Security extends Secure.Security {

	/**
	 * Retoure l'Utilisateur courant
	 * 
	 * @return <code>Utilisateur</code>
	 */
	static Utilisateur getCurrentUser() {
		return Utilisateur.find("byLogin", connected()).first();
	}
	
	static boolean authenticate(String login, String password) {
		Utilisateur utilisateur = Utilisateur.find("byLogin", login).first();
		return utilisateur != null && utilisateur.password.equals(password);
	}
	
	/**
	 * Méthode permettant de protéger des écrans selon le type d'utilisateur
	 * Ou autre que le type, il suffit de l'implémenter.
	 * 
	 * @param profile: Sur quel critère se fait la vérification
	 * @return
	 */
	static boolean check(String profile) {
		Utilisateur utilisateur = Utilisateur.find("byLogin", connected()).first();
		
		if("isAdmin".equals(profile)) {
			return TypeUtilisateur.ADMIN.equals(utilisateur.typeUtilisateur);
		} else {
			return false;
		}
	}
	
}
