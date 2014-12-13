package Biomorph;

import java.util.ArrayList;


public class BiomorphWarehouse {
	
	ArrayList<Biomorph> tempBiomorphs; 
	ArrayList<Biomorph> parentBiomorphs;
	
	public void BiomorphWareouse(){
		
		tempBiomorphs = new ArrayList<Biomorph>();
		parentBiomorphs = new ArrayList<Biomorph>();
		
	}
	
	/**
	 * @param b
	 * @return a true or false value depending if the 
	 */
	private Boolean storeTempBiomorph(Biomorph b){
		Boolean stored = false;
		while(!stored){
			for(int i = 0; i < 7; i++){
				if(tempBiomorphs.get(i) == null){
					tempBiomorphs.add(b);
					stored = true;
				}else if(i == 7){
					System.err.println("Only 8 Biomorph's can be temp stored at a time");
					return false;
				}
			}
			
		}
		return true;
							//If you want to use an array
							/*Boolean stored = false;
							while(!stored){
								for(int i = 0; i < tempBiomorphs.length; i++){
									if(tempBiomorphs[i] == null){
										tempBiomorphs[i] = b;
										stored = true;
									}else if(i == tempBiomorphs.length){
										System.out.println("Only 8 Biomorph's can be temp stored at a time");
									}
								}
							}*/
	}
	

	private Boolean storeParentBiomorph(Biomorph b){
		Boolean stored = false;
		while(!stored){
			for(int i = 0; i < 1; i++){
				if(parentBiomorphs.get(i) == null){
					parentBiomorphs.add(b);
					stored = true;
				}else if(i == 1){
					System.err.println("Only 8 Biomorph's can be temp stored at a time");
					return false;
				}
			}
		}
		return true;
								//if you wanted to use an array
								/*Boolean stored = false;
								while(!stored){
									for(int i = 0; i < parentBiomorphs.length; i++){
										if(tempBiomorphs[i] == null){
											tempBiomorphs[i] = b;
											stored = true;
										}else if(i == parentBiomorphs.length){
											System.out.println("Only 2 parent Biomorph's can be used in the evolution processs");
											return false;
										}
									}
								}*/
	}
	
	
	public void addTempBiomorph(Biomorph b){
		storeTempBiomorph(b);
	}
	
	public void addParentBiomorph(Biomorph b){
		storeTempBiomorph(b);
	}
	
	public void removeTempBiomorph(int i){
		tempBiomorphs.remove(i);
	}
	
	public void removeParentBiomorph(int i){
		parentBiomorphs.remove(i);
	}
	
	public void addTempToParent(int tempNum){
		if(storeParentBiomorph(tempBiomorphs.get(tempNum)) == true){
			tempBiomorphs.remove(tempNum);
		}
		
	}
	
	public void addParentToTemp(int tempNum, int parentNum){
		if(storeTempBiomorph(parentBiomorphs.get(parentNum)) == true){
			parentBiomorphs.remove(parentNum);
		}
	}

}
