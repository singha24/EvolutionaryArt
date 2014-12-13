import Biomorph.Biomorph;
import Biomorph.BiomorphCreator;
import Biomorph.BiomorphWarehouse;
import Biomorph.GUI;
import Output.Renderer;


public class Controller {

	private BiomorphCreator bioCreate = new BiomorphCreator();
	private BiomorphWarehouse bioStorage = new BiomorphWarehouse();
	private Renderer render = new Renderer();
	private GUI gui = new GUI();

	public Controller(){

	}

	public void createBiomorph(){

	}

	public boolean saveBiomorph(Biomorph b){
		return false;
	}
	
	public Biomorph displayBiomorph(){
		Biomorph b = bioStorage.getBiomorph();
		return b;
	}

}
