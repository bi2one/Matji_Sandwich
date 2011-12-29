package com.matji.sandwich.data;

import java.io.Serializable;

import com.matji.sandwich.data.MatjiData;

public class RecentSearchToken extends MatjiData implements Serializable {

	private static final long serialVersionUID = 2341338377426147797L;
	private String seed;
	
	public RecentSearchToken(String seed) {
		setSeed(seed);
	}

	public void setSeed(String seed) {
		this.seed = seed.trim();
	}
	
	public String getSeed() {
		return seed;
	}
	
	public boolean equals(RecentSearchToken token) {
		return (token.getSeed().equals(getSeed()));
	}
}
