package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import models.Auteur;
import models.Livre;

import org.apache.commons.lang.StringUtils;

import play.data.validation.Valid;
import play.db.jpa.JPA;
import play.db.jpa.GenericModel.JPAQuery;

/**
 * Contient les actions disponibles pour le modèle <code>Livre</code>
 * 
 * @author Legunda
 * 
 */
public class LivreAction extends Application {

	public static int nbParPage = 10;

	/**
	 * Initialise l'écran permettant d'ajouter un <code>Livre</code>
	 */
	public static void ajouter() {
		List auteurs = Auteur.find("order by nom").fetch();
		Livre livre = new Livre();

		render(livre, auteurs);
	}

	/**
	 * Vérifie que le livre est valide (selon les règles du modèle) et si c'est
	 * le cas l'enregistre en base.
	 * 
	 * @param livre
	 */
	public static void enregistrer(@Valid Livre livre) {
		// on regarde si c'est un ajout ou une modification de livre pour savoir
		// quel écran afficher à la suite
		boolean ajout = livre.id == null;

		if (livre.proprietaire == null) {
			livre.proprietaire = Security.getCurrentUser();
		}

		if (validation.hasErrors()) {
			List auteurs = Auteur.find("order by nom").fetch();
			flash.error("Votre livre n'a pû être ajouté");
			render("@ajouter", livre, auteurs);
		} else {
			if (livre.save() != null) {
				flash.success("Le livre a bien été enregistré");
			} else {
				flash.error("Problème rencontré lors de l'enregistrement du livre");
			}
			if (ajout) {
				// on permet l'ajout d'un nouveau livre
				LivreAction.ajouter();
			} else {
				// on affiche la fiche du livre
				LivreAction.afficher(livre.id);
			}
		}
	}

	/**
	 * Affiche la fiche du livre sélectionné par son id
	 * 
	 * @param id
	 */
	public static void afficher(Long id) {
		Livre livre = Livre.findById(id);

		render(livre);
	}

	/**
	 * Permet de modifier un livre existant.
	 * 
	 * @param id
	 */
	public static void modifier(Long id) {
		Livre livre = Livre.findById(id);
		List<Auteur> auteurs = Auteur.findAll();

		render("@ajouter", auteurs, livre);
	}

	/**
	 * Donne accès à l'écran de recherche d'un livre
	 */
	public static void rechercher(Livre livre) {
		flash.clear();

		if (livre.estVide()) {
			render();
		}

		StringBuffer requete = new StringBuffer("select l from Livre l where");
		StringBuffer requeteCount = new StringBuffer("select count(*) from Livre l where");
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(livre.titre)) {
			requete.append(" LOWER(l.titre) like LOWER(:titre) and");
			requeteCount.append(" LOWER(l.titre) like LOWER(:titre) and");
			params.put("titre", "%" + livre.titre + "%");
		}
		// Obligatoire pour trier selon utilisateur connecté
		// TODO: en attendant d'avoir une meilleure solution pour passer la
		// requete pour gestion pagination,
		// on utilise l'id en paramètre plutôt que l'objet propriétaire en lui
		// même
		requete.append(" l.proprietaire.id = :proprio ");
		requeteCount.append(" l.proprietaire.id = :proprio ");
		params.put("proprio", Security.getCurrentUser().id);
		// TODO: rajouter les autres critères

		String requeteFinale = requete.toString();

		JPAQuery jpaQuery = Livre.find(requete.toString());
		Query queryCount = JPA.em().createQuery(requeteCount.toString());

		for (String key : params.keySet()) {
			Object value = params.get(key);
			jpaQuery.bind(key, value);
			queryCount.setParameter(key, value);
			requeteFinale = StringUtils.replace(requeteFinale, ":" + key, value.toString());
		}

		List livres = jpaQuery.fetch(nbParPage);
		Long nbTotalResultat = (Long) queryCount.getSingleResult();

		if (livres.size() != 0) {
			int numPage = 1;
			int nbParPage = LivreAction.nbParPage;
			String query = requeteFinale;

			render("@afficher", livres, query, nbTotalResultat, numPage, nbParPage);
		} else {
			flash.error("Il n'y a aucun livre correspondant à vos critères");
			render();
			// LivreAction.rechercher(livre);
		}
	}

	public static void pagination(int numPage, String query, int nbTotalResultat) {
		int nbParPage = LivreAction.nbParPage;

		List<Livre> livres = Livre.find(query).fetch(numPage, LivreAction.nbParPage);

		render("@afficher", livres, query, nbTotalResultat, numPage, nbParPage);
	}
}
