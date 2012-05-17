package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * En héritant de la classe Model, on pourrait s'éviter de gérer un Id par
 * exemple. Mais c'est plus intéressant de le faire
 * 
 * @author Legunda
 * 
 */
@Entity
public class Auteur extends Model {
	// Avec Play! tous les paramètres sont public et les getter/setter générés
	// automatiquement

	@Required(message = "Le nom est requis!")
	public String nom;
	@Required(message = "Le prénom est requis!")
	public String prenom;
	public String dateNaissance;
	public String dateMort;
	public String image;
	public String libelle;

	// private List<LivreReference> livreReference;
	// TODO: rechercher à quoi correspondre les différentes stratégies de
	// Cascade
	@OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
	public List<Livre> livreEcrits = new ArrayList<Livre>();

	public Auteur() {
	}

	public Auteur(String nom, String prenom, String dateNaissance, String dateMort, String image) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.dateMort = dateMort;
		this.image = image;
	}

	public void setNom(String nom) {
		this.nom = nom;
		miseAJourLibelle();
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
		miseAJourLibelle();
	}

	/**
	 * Génère le libellé de l'auteur en fonction de son nom et prénom
	 */
	private void miseAJourLibelle() {
		StringBuffer libelle = new StringBuffer();

		libelle.append(this.prenom != null ? this.prenom : "");		
		if (this.nom != null && this.prenom != null) {
			libelle.append(" ");
		}
		libelle.append(" " + this.nom != null ? this.nom : "");

		this.libelle = libelle.toString();
	}
}
