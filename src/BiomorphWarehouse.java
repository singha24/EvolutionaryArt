import java.util.ArrayList;


public class BiomorphWarehouse {
	
	ArrayList<BioMorph> tempBiomorphs; 
	ArrayList<BioMorph> parentBiomorphs;
	
	public void BiomorphWareouse(){
		
		tempBiomorphs = new ArrayList<BioMorph>();
		parentBiomorphs = new ArrayList<BioMorph>();
		
	}
	
	public void storeTempBiomorph(BioMorph b){
		Boolean stored = false;
		while(!stored){
			for(int i = 0; i < 7; i++){
				if(tempBiomorphs.get(i) == null){
					tempBiomorphs.add(b);
					stored = true;
				}else if(i == 7){
					System.out.println("Only 8 biomorph's can be temp stored at a time");
				}
			}
		}
		//If you want to use an array
		/*Boolean stored = false;
		while(!stored){
			for(int i = 0; i < tempBiomorphs.length; i++){
				if(tempBiomorphs[i] == null){
					tempBiomorphs[i] = b;
					stored = true;
				}else if(i == tempBiomorphs.length){
					System.out.println("Only 8 biomorph's can be temp stored at a time");
				}
			}
		}*/
	}
	
	public Boolean storeParentBiomorph(BioMorph b){
		/*Boolean stored = false;
		while(!stored){
			for(int i = 0; i < parentBiomorphs.length; i++){
				if(tempBiomorphs[i] == null){
					tempBiomorphs[i] = b;
					stored = true;
				}else if(i == parentBiomorphs.length){
					System.out.println("Only 2 parent biomorph's can be used in the evolution processs");
					return false;
				}
			}
		}*/
		return true;
	}
	
	public void removeTempBiomorph(int i){
		tempBiomorphs[i] = null;
	}
	
	public void removeParentBiomorph(int i){
		parentBiomorphs[i] = null;
	}
	
	public void addTempToParent(int tempNum){
		if(storeParentBiomorph(tempBiomorphs[tempNum]) == true){
			tempBiomorphs[tempNum] = null;
		}
		
	}
	
	public void addParentToTemp(int tempNum, int parentNum){

	}

}
