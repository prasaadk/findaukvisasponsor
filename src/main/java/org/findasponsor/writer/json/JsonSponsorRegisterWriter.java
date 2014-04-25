package org.findasponsor.writer.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.findasponsor.Sponsor;
import org.findasponsor.TierRatingAndSubTier;

import com.google.gson.stream.JsonWriter;

public class JsonSponsorRegisterWriter {

	public void write(List<Sponsor> sponsors, StringWriter outputWriter)
			throws IOException {
		JsonWriter writer = new JsonWriter(outputWriter);
		writer.setIndent("");
		writeSponsorsArray(writer, sponsors);
		writer.close();
	}

	private void writeSponsorsArray(JsonWriter writer, List<Sponsor> sponsors)
			throws IOException {
		writer.beginArray();
		for (Sponsor message : sponsors) {
			writeSponsor(writer, message);
		}
		writer.endArray();
	}

	private void writeSponsor(JsonWriter writer, Sponsor sponsor)
			throws IOException {
		writer.beginObject();
		writer.name("company").value(sponsor.getName());
		writer.name("city").value(sponsor.getCity());
		if (!StringUtils.isEmpty(sponsor.getCounty())) {
			writer.name("county").value(sponsor.getCounty());
		}
		if (!sponsor.getTierAndRating().isEmpty()) {
			writer.name("ratings");
			writeTierRatingAndSubTierArray(writer, sponsor.getTierAndRating());
		}
		writer.endObject();
	}

	private void writeTierRatingAndSubTierArray(JsonWriter writer,
			List<TierRatingAndSubTier> tierRatingAndSubTiers)
			throws IOException {
		writer.beginArray();
		for (TierRatingAndSubTier message : tierRatingAndSubTiers) {
			writeTierRatingAndSubTier(writer, message);
		}
		writer.endArray();

	}

	private void writeTierRatingAndSubTier(JsonWriter writer,
			TierRatingAndSubTier tierRatingAndSubTier) throws IOException {
		writer.beginObject();
		writer.name("tierRating").value(tierRatingAndSubTier.getTierRating());
		if (!StringUtils.isEmpty(tierRatingAndSubTier.getSubTier())) {
			writer.name("subTier").value(tierRatingAndSubTier.getSubTier());
		}
		writer.endObject();

	}
}
