package aac_tech.automotiveui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Client_Address_Info_Test {
    private Client_Address_Info mclient_address_info;

    @Before
    public void setUp(){
        mclient_address_info = new Client_Address_Info();
    }

    @Test
    public void isValidCity(){
        assertTrue(mclient_address_info.isValidCity("Brampton"));
    }

    @Test
    public void isValidZip(){
        assertTrue(mclient_address_info.isValidZip("L4B 2R4"));
    }

    @Test
    public void isValidStreet(){
        assertTrue(mclient_address_info.isValidStreet("Humber Blv"));
    }

    @Test
    public void isValidPhone(){
        assertTrue(mclient_address_info.isValidPhone("5558974896"));
    }

    @Test
    public void isValidProvince(){
        assertTrue(mclient_address_info.isValidProvince("ON"));
    }
}
