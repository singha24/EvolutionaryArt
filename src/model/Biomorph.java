package model;

import java.io.Serializable;

/**
 * A Biomorph has genes which are used to render an image.
 * 
 * Genes can be increased based on the genelimit specified in BiomorphCreator.
 * 
 * @author Manjit Bansal
 * @version 14 Dec 2014
 */
public class Biomorph implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] genes;

	/**
	 * Constructor
	 * 
	 * @param array
	 *            of int values
	 */
	public Biomorph(int[] genes) {
		this.genes = genes;
	}

	/**
	 * Method that returns the gene value.
	 * 
	 * @return an array of int which are the values used to generate image.
	 */
	public int[] getGenes() {
		return genes;
	}

	public int getGenesLenth() {

		return genes.length;
	}

}