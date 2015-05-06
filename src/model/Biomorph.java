package model;

import java.io.Serializable;

/**
 * A Biomorph has genes which are used to render an image.
 * 
 * Genes can be increased based on the genelimit specified in BiomorphCreator.
 * @author Manjit Bansal
 * @version 06 May 2015
 */

public class Biomorph implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	// Genes 
	private int[] genes;

	/**
	 * Constructor - construct the Biomorph object
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

	/**
	 * @return int as The Gene length of genes
	 */
	public int getGenesLength() {

		return genes.length;
	}

}