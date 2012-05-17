package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;

import enums.TypeUtilisateur;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * L'utilisateur représente un utilisateur du site.
 * Va être utilisé pour identifier ceux qui ont accès à la bibliothèque.
 * 
 * Un utilisateur peut être du type suivant:
 * - Admin
 * - Propriétaire de bibliothèque
 * 
 * Un propriétaire de bibliothèque peut accéder à sa bibliothèque et modifier les ouvrages qui s'y trouvent.
 * 
 * @author Legunda
 *
 */
@Entity
public class Utilisateur extends Model {

	@Required(message="Le login est obligatoire!")
	@Column(unique=true)
	public String login;
	
	@Required(message="Le mot de passe est obligatoire!")
	@Password
	public String password;
	
	@Required(message="L'adresse mail est obligatoire!")
	@Email
	public String email;

	@Required(message="Le type d'utilisateur est obligatoire!")
	public TypeUtilisateur typeUtilisateur;
	
	@Override
	public String toString() {
		return email;
	}
}
