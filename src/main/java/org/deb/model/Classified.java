/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.deb.model;

import com.jakewharton.fliptables.FlipTable;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;

/**
 *
 * @author debmalyajash
 */
public class Classified implements Comparable<Classified>{
    private String sentiment;
	private CoreMap sentence;
	private List<EmbeddedToken> tokenList;
	private int rating;
	private float neutrality;

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public CoreMap getSentence() {
		return sentence;
	}

	public void setSentence(CoreMap sentence) {
		this.sentence = sentence;
	}

	/**
	 * @param sentiment
	 * @param sentence2
	 * @param tokens
	 * @param rating2 
	 */
	public Classified(String sentiment, CoreMap sentence2, List<EmbeddedToken> tokens, int rating2) {
		this.sentiment = sentiment;
		this.sentence = sentence2;
		this.tokenList = tokens;
		this.rating = rating2;
	}

	public List<EmbeddedToken> getTokenList() {
		return tokenList;
	}
	
	public String toString() {
		String[] headers = { "Name", "Value" };
		StringBuilder sb = new StringBuilder();
		String[][] data = new String[tokenList.size()][2];
		for (int i = 0; i < tokenList.size(); i++){
			EmbeddedToken endorseMent = tokenList.get(i);
			data[i] = new String[]{endorseMent.getName(),endorseMent.getValue()};
		}
		return System.getProperty("line.separator")+ FlipTable.of(headers, data);
	}

	public void setTokenList(List<EmbeddedToken> tokenList) {
		this.tokenList = tokenList;
	}

	public float getNeutrality() {
		return neutrality;
	}

	public void setNeutrality(float neutrality) {
		this.neutrality = neutrality;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Classified o) {
		if (rating > o.rating) {
			return 1;
		} else if (rating < o.rating) {
			return -1;
		}
		return 0;
	}
}
