import java.util.List;

import com.google.common.collect.Lists;

/**
 * Sponsor object
 * 
 * @author prasad
 */
public class Sponsor {

	private String name;
	private String city;
	private String county;
	private List<TierRatingAndSubTier> tierAndRating = Lists.newArrayList();

	public Sponsor(String name, String city, String county) {
		this.name = name;
		this.city = city;
		this.county = county;

	}

	public void add(String tierRating, String subTier) {
		tierAndRating.add(new TierRatingAndSubTier(tierRating, subTier));
	}

	@Override
	public String toString() {
		return "Sponsor [name=" + name + ", city=" + city + ", county="
				+ county + ", tierAndRating=" + tierAndRating + "]\n";
	}

}
