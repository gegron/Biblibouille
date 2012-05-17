package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.StringUtils;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Livre extends Model {

	@Required(message = "Le titre est requis.")
	public String titre;

	// TODO: données référentiel
	@Required(message = "La collection est requise.")
	public String collection;

	public String etage;

	@Required(message = "L'auteur est requis.")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public Auteur auteur;
	
	@ManyToOne(optional=false)
	public Utilisateur proprietaire;

	// public String image;
	// // TODO: utiliser type Date
	// public String dateEdition;
	// @Lob
	// public String description;
	// @Lob
	// public String avis;
	// @Lob
	// public String localisation;
	// public String contenu;
	// public String liens;
	// public String lieuEdition;
	// // TODO: données référentiel
	// public String edition;
	// // TODO: données référentiel
	// public String langue;
	// @Lob
	// public String sujet;
	// @Lob
	// public String notes;

	// TODO: remplacer l'enum par une table stockant ces données référentiels
	// dossier, document numérique, livre.
	// dossier("dossier"),docNum("document numerique"),livre("livre");
	// public TypeLivre typeLivre;

	// @ManyToMany
	// @JoinTable(name = "LIVRE_AUTEUR", joinColumns = { @JoinColumn(name =
	// "LIVRE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTEUR_ID") })
	// @LazyCollection(LazyCollectionOption.TRUE)
	// public List<Auteur> auteurs = new ArrayList<Auteur>();

	/**
	 * Permet de savoir s'il n'y a aucun champs d'initialisé dans l'objet
	 */
	public Boolean estVide() {
		boolean estVide = StringUtils.isEmpty(this.titre) && StringUtils.isEmpty(this.collection)
				&& StringUtils.isEmpty(this.etage) && this.auteur == null;
		return Boolean.valueOf(estVide);
	}

}
