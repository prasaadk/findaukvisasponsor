package org.findasponsor;

public class TierRatingAndSubTier {

	private String tierRating;
	private String subTier;

	public TierRatingAndSubTier(String tierRating, String subTier) {
		this.tierRating = tierRating;
		this.subTier = subTier;
	}

	public String getTierRating() {
		return tierRating;
	}

	public void setTierRating(String tierRating) {
		this.tierRating = tierRating;
	}

	public String getSubTier() {
		return subTier;
	}

	public void setSubTier(String subTier) {
		this.subTier = subTier;
	}

	@Override
	public String toString() {
		return "TierRatingAndSubTier [tierRating=" + tierRating + ", subTier="
				+ subTier + "]";
	}

}
