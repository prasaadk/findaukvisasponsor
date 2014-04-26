package org.findasponsor.writer.json;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.List;

import org.findasponsor.Sponsor;
import org.junit.Test;

import com.google.common.collect.Lists;

public class JsonSponsorRegisterWriterTest {

	@Test
	public void testNoSponsors() throws Exception {
		JsonSponsorRegisterWriter writer = new JsonSponsorRegisterWriter();
		List<Sponsor> sponsors = Lists.newArrayList();
		StringWriter outputWriter = new StringWriter();
		writer.write(sponsors, outputWriter);
		assertEquals("should be empty", "[]", outputWriter.toString());
	}
	
	@Test
	public void testSingleSponsor() throws Exception {
		JsonSponsorRegisterWriter writer = new JsonSponsorRegisterWriter();
		List<Sponsor> sponsors = Lists.newArrayList();
		sponsors.add(new Sponsor("Foo", "London", "Greater London"));
		StringWriter outputWriter = new StringWriter();
		writer.write(sponsors, outputWriter);
		assertEquals(
				"should equal to the json object",
				"[{\"company\":\"Foo\",\"city\":\"London\",\"county\":\"Greater London\"}]",
				outputWriter.toString());
	}
	
	@Test
	public void testSingleSponsorWithoutCounty() throws Exception {
		JsonSponsorRegisterWriter writer = new JsonSponsorRegisterWriter();
		List<Sponsor> sponsors = Lists.newArrayList();
		sponsors.add(new Sponsor("Foo", "London", null));
		StringWriter outputWriter = new StringWriter();
		writer.write(sponsors, outputWriter);
		assertEquals(
				"should equal to the json object",
				"[{\"company\":\"Foo\",\"city\":\"London\"}]",
				outputWriter.toString());
	}

	@Test
	public void testSingleSponsorWithSingleTierRating() throws Exception {
		JsonSponsorRegisterWriter writer = new JsonSponsorRegisterWriter();
		List<Sponsor> sponsors = Lists.newArrayList();
		Sponsor e = new Sponsor("Foo", "London", "Greater London");
		e.add("Foo", "Bar");
		sponsors.add(e);
		StringWriter outputWriter = new StringWriter();
		writer.write(sponsors, outputWriter);
		assertEquals("should equal to the json object",
				"[{\"company\":\"Foo\"," 
						+ "\"city\":\"London\","
						+ "\"county\":\"Greater London\","
						+ "\"ratings\":[{\"tierRating\":\"Foo\"," +
						"\"subTier\":\"Bar\"}]}]", outputWriter.toString());
	}

	@Test
	public void testSingleSponsorWithMultipleTier2Ratings() throws Exception {
		JsonSponsorRegisterWriter writer = new JsonSponsorRegisterWriter();
		List<Sponsor> sponsors = Lists.newArrayList();
		Sponsor e = new Sponsor("Foo", "London", "Greater London");
		e.add("Foo1", "Bar1");
		e.add("Foo2", "Bar2");
		sponsors.add(e);
		StringWriter outputWriter = new StringWriter();
		writer.write(sponsors, outputWriter);
		assertEquals("should equal to the json object",
				"[{\"company\":\"Foo\"," 
						+ "\"city\":\"London\","
						+ "\"county\":\"Greater London\","
						+ "\"ratings\":[{\"tierRating\":\"Foo1\"," 
						+ "\"subTier\":\"Bar1\"}," 
						+ "{\"tierRating\":\"Foo2\"," 
						+ "\"subTier\":\"Bar2\"}]}]", outputWriter.toString());		
	}

	@Test
	public void testMultipleSponsors() throws Exception {
		JsonSponsorRegisterWriter writer = new JsonSponsorRegisterWriter();
		List<Sponsor> sponsors = Lists.newArrayList();
		Sponsor s1 = new Sponsor("Foo1", "London", "Greater London");
		s1.add("Foo", "Bar");
		sponsors.add(s1);
		Sponsor s2 = new Sponsor("Foo2", "Lancaster", "Lancashire");
		s2.add("Foo", "Bar");
		sponsors.add(s2);
		StringWriter outputWriter = new StringWriter();
		writer.write(sponsors, outputWriter);
		assertEquals("should equal to the json object",
				"[" +
				"{\"company\":\"Foo1\"," 
						+ "\"city\":\"London\","
						+ "\"county\":\"Greater London\","
						+ "\"ratings\":[{\"tierRating\":\"Foo\"," 
						+ "\"subTier\":\"Bar\"}]}," +
						"{\"company\":\"Foo2\"," 
						+ "\"city\":\"Lancaster\","
						+ "\"county\":\"Lancashire\","
						+ "\"ratings\":[{\"tierRating\":\"Foo\"," 
						+ "\"subTier\":\"Bar\"}]}" +						
						"]", outputWriter.toString());		
	}

}
