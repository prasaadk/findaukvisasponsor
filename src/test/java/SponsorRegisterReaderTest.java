import java.io.FileInputStream;
import java.net.URL;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;

public class SponsorRegisterReaderTest {

	public static final URL FILE_PATH = ClassLoader
			.getSystemResource("Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf");
	public static final String DOC_URL = "https://www.gov.uk/government/uploads/system/uploads/attachment_data/file/305166/Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf";

	@Test
	public void justHacking() throws Exception {
		PDFTextStripper stripper = new PDFTextStripper();
		PDFParser pdfParser = new PDFParser(new FileInputStream(
				FILE_PATH.getFile()));
		pdfParser.parse();
		PDDocument load = pdfParser.getPDDocument();
		stripper.setSortByPosition(true);
		stripper.setLineSeparator("\n");
		stripper.setAddMoreFormatting(true);
		stripper.setWordSeparator("|");

		String text = stripper.getText(load);
		text = text
				.replaceAll(
						"Organisation Name\\|Town/City\\|County\\|Tier & Rating\\|Sub Tier",
						"");
		text = text.replaceAll("Page [0-9]+ of [0-9]+", "");
		System.out.println("text:" + text);
	}

}
