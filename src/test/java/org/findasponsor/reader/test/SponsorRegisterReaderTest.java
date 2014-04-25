package org.findasponsor.reader.test;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.findasponsor.Sponsor;
import org.findasponsor.reader.SponsorRegisterReader;
import org.junit.Test;

/**
 * 
 * @author prasad
 */
public class SponsorRegisterReaderTest {

	@Test
	public void justHacking() throws Exception {
		SponsorRegisterReader reader = new SponsorRegisterReader();
		Collection<Sponsor> sponsors = reader.getSponsors();
		System.out.println(sponsors);
		assertEquals("Size of the sponsors list should be", 27812,
				sponsors.size());
	}

}
