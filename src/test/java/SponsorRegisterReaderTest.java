import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;

import java.net.URL;

public class SponsorRegisterReaderTest {

	public static final URL FILE_PATH = ClassLoader.getSystemResource("Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf");
	public static final String DOC_URL = "https://www.gov.uk/government/uploads/system/uploads/attachment_data/file/305166/Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf";

    @Test
	public void testName() throws Exception {
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument load = PDDocument.load(FILE_PATH);
		String text = stripper.getText(load);
		//String text = stripper.getText(PDDocument.load(new URL(DOC_URL)));
		stripper.setSpacingTolerance(0);
		System.out.println("text:" + text);
	}

}
