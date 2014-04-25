package org.findasponsor.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.findasponsor.Sponsor;

import com.google.common.collect.Lists;

/**
 * Returns list of Sponsors
 * 
 * @author prasad
 * 
 */
public class SponsorRegisterReader {

	public static final URL FILE_PATH = ClassLoader
			.getSystemResource("Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf");
	public static final String DOC_URL = "https://www.gov.uk/government/uploads/system/uploads/attachment_data/file/305166/Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf";
	public static final long HEADER_COUNT = 7;

	public Collection<Sponsor> getSponsors() throws IOException {
		PDFTextStripper stripper = new PDFTextStripper();
		PDFParser pdfParser = new PDFParser(new FileInputStream(
				FILE_PATH.getFile()));
		pdfParser.parse();
		PDDocument load = pdfParser.getPDDocument();
		stripper.setSortByPosition(true);
		stripper.setLineSeparator("\n");
		stripper.setWordSeparator("|");
		stripper.setAddMoreFormatting(true);

		String text = stripper.getText(load);
		text = text
				.replaceAll(
						"Organisation Name\\|Town/City\\|County\\|Tier & Rating\\|Sub Tier",
						"");
		text = text.replaceAll("Page [0-9]+ of [0-9]+", "");

		return extractSponsors(text);
	}

	private Collection<Sponsor> extractSponsors(String text) throws IOException {
		List<Sponsor> list = Lists.newArrayList();

		BufferedReader reader = new BufferedReader(new StringReader(text));
		String line = null;

		Sponsor sponsor = null;
		boolean newSponsor = true;
		long count = 0;
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				continue;
			}

			if (count >= HEADER_COUNT) {
				if (!line.startsWith("Tier")) {
					newSponsor = true;
				} else {					
					String[] split = line.split("\\|");
					String tierRating = split[0];
					String subTier = null;
					if (split.length > 1) {
						subTier = split[1];
					}
					sponsor.add(tierRating, subTier);
				}

				if (newSponsor) {
					String[] split = line.split("\\|");
					String name = split[0];
					String city = null;
					if (split.length > 1) {
						city = split[1];
					}
					String county = null;
					if (split.length > 2) {
						county = split[2];
					}
					sponsor = new Sponsor(name, city, county);
					list.add(sponsor);
					newSponsor = false;
				}
			}
			count++;
		}
		return list;
	}
}
