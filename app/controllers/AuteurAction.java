package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import models.Auteur;
import play.data.validation.Valid;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

/**
 * 
 * @author Legunda
 *
 */
public class AuteurAction extends Application {

	public static void ajouter() {
		render();
	}
	
	/**
	 * 
	 * @param id
	 */
	public static void modifier(Long id) {
		Auteur auteur = Auteur.findById(id);
		
		render("@ajouter", auteur);
	}
	
	/**
	 * 
	 * @param auteur
	 */
	public static void enregistrer(@Valid Auteur auteur){
		if(validation.hasErrors()){
			render("@ajouter", auteur);
			//render("AjouterAuteurAction/index.html", auteur);
		}
		Auteur result = auteur.save();
		
		if(result != null) {
			flash.success("L'auteur " + StringUtils.upperCase(auteur.nom) + " " + auteur.prenom + " a bien été ajouté.");
		} else {
			flash.error("L'auteur n'a pu être enregistré.");
			render("@ajouter", auteur);
		}
		AuteurAction.ajouter();
	}
	
	/**
	 * 
	 * @param id
	 */
	public static void afficher(Long id) {
		Auteur auteur = Auteur.findById(id);
		
		render(auteur);
	}
	
	/**
	 * Recherche un auteur à partir de son nom ou de son prénom
	 */
	public static void rechercher(String nom, String prenom) {
		if (nom == null && prenom == null) {
			// initialisation du formulaire de recherche
			render();
		}

		StringBuffer requete = new StringBuffer("select a from Auteur a ");
		
		String prochainMotClef = "where";

		if (StringUtils.isNotEmpty(nom)) {
			requete.append(prochainMotClef);
			requete.append(" LOWER(a.nom) like LOWER(:nom) ");
			prochainMotClef = "and";
		}
		if (StringUtils.isNotEmpty(prenom)) {
			requete.append(prochainMotClef);
			requete.append(" LOWER(a.prenom) like LOWER(:prenom) ");
			prochainMotClef = "and";
		}
		requete.append(" order by a.nom, a.prenom ");

		JPAQuery jpaQuery = Auteur.find(requete.toString());

		if (StringUtils.isNotEmpty(nom)) {
			jpaQuery.bind("nom", "%" + nom + "%");
		}
		if (StringUtils.isNotEmpty(prenom)) {
			jpaQuery.bind("prenom", "'%" + prenom + "%'");
		}
		
		List<Auteur> auteurs = jpaQuery.fetch();
		
		// si qu'un seul résultat, afficher l'auteur
		if(auteurs.isEmpty()) {
			flash.error("Il n'y a pas d'auteur correspondant au(x) mot(s) clef(s).");
			render();
		} else if (auteurs.size() == 1) {
			AuteurAction.afficher((auteurs.get(0)).id);
		} else {
			render("@afficher", auteurs);
		}
	}
}
