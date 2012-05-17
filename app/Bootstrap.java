import models.Auteur;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
	
	@Override
	public void doJob() throws Exception {
		//TODO: essayer de trouver une méthode pour désactiver le chargement de la base en mode test
		if (Auteur.count() == 0)
			Fixtures.loadModels("dev-data.yml");
	}

}
