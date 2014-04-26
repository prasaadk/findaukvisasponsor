package org.findasponsor.reader.test;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Collection;

import org.findasponsor.Sponsor;
import org.findasponsor.reader.SponsorRegisterReader;
import org.junit.Test;

/**
 * 
 * @author prasad
 */
public class SponsorRegisterReaderTest {

    public static final URL FILE_PATH = ClassLoader
            .getSystemResource("Tiers_2___5_Register_of_Sponsors_2014-04-22.pdf");

	@Test
	public void justHacking() throws Exception {
		SponsorRegisterReader reader = new SponsorRegisterReader(FILE_PATH.toString());
		Collection<Sponsor> sponsors = reader.getSponsors();
		System.out.println(sponsors);
//		assertEquals("Size of the sponsors list should be", 27812, sponsors.size());
        assertEquals("Size of the sponsors list should be", 27816, sponsors.size());
	}

}
