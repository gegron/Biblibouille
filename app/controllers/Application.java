package controllers;

import models.Utilisateur;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Application extends Controller {

	@Before
	static void addDefaults() {
	}
	
	@Before
	static void setConnectedUtilisateur() {
		if(Security.isConnected()) {
			Utilisateur utilisateur = Utilisateur.find("byLogin", Security.connected()).first();
			renderArgs.put("utilisateur", utilisateur);
			renderArgs.put("security", Security.connected());
		}
	}

	public static void index() {
		render();
	}

}