package org.findasponsor.reader.test;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.findasponsor.Sponsor;
import org.junit.Test;

/**
 * Created on 4/26/14.
 * @author akshay 
 */
public class SponsorJacksonTest {

    @Test
    public void testJsonWrite() throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Sponsor sponsor     = new Sponsor("FindASponsor","London","London");
        sponsor.add("A","Tier2");
        String expectedJson = "{\"name\":\"FindASponsor\",\"city\":\"London\",\"county\":\"London\",\"tierAndRating\":[{\"tierRating\":\"A\",\"subTier\":\"Tier2\"}]}";
        assertEquals("Json serialization should produce", expectedJson, mapper.writeValueAsString(sponsor));
    }
}
