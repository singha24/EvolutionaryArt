import Biomorph.Biomorph;
import Biomorph.BiomorphCreator;
import Biomorph.BiomorphWarehouse;
import Biomorph.GUI;
import Output.Renderer;

public class Controller {

	private BiomorphCreator bioCreate;
	private BiomorphWarehouse bioStorage;
	private Renderer render;
	private GUI gui;;

	public Controller() {
		bioCreate = new BiomorphCreator();
		bioStorage = new BiomorphWarehouse();
		render = new Renderer();
		gui = new GUI();

		// Create the two random parent biomorph's
		createStartUpBiomorphs();
	}

	public void createBiomorph() {
		// TODO
	}

	public boolean saveBiomorph(Biomorph b) {
		// TODO
	}

	private void displayBiomorph(Biomorph b) {
		// render.displayBiomorph(bioStorage.getParent());

	}

	public void saveTempBiomorph(Biomorph b) {
		bioStorage.addTempBiomorph(b);
	}
	
	public Biomorph getBioMorphValues(){
		return bioCreate.getChild();
	}

	/**
	 * Creates the two random parent bioMorphs and stores them in the parent
	 * array in the biomorph warehouse
	 */
	private void createStartUpBiomorphs() {
		for (int i = 0; i <= 2; i++) {
			bioStorage.addParentBiomorph(bioCreate.generateRandomBiomorph());
		}
	}
}
