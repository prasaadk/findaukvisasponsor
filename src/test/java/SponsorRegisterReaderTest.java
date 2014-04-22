import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Before;
import org.junit.Test;

public class SponsorRegisterReaderTest {

	public static final String FILE_PATH = "data/Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf";
	public static final String DOC_URL = "https://www.gov.uk/government/uploads/system/uploads/attachment_data/file/305166/Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf";

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testName() throws Exception {
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument load = PDDocument.load(new File(FILE_PATH));		
		String text = stripper.getText(load);
		//String text = stripper.getText(PDDocument.load(new URL(DOC_URL)));
		stripper.setSpacingTolerance(0);
		System.out.println("text:" + text);
	}

}
