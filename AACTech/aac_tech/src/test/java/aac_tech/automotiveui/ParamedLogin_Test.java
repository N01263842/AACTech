package aac_tech.automotiveui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ParamedLogin_Test {

    private paramedLogin mparamdLogin;

    @Before
    public void setUp(){
        mparamdLogin = new paramedLogin();
    }

    @Test
    public void test_valid_UName(){
        assertTrue(mparamdLogin.isValidUName("name2345"));
    }

    @Test
    public void test_valid_password(){
        assertTrue(mparamdLogin.isValidPassword("Pass2345"));
    }

    @Test
    public void test_NotValid_password(){
        assertFalse(mparamdLogin.isValidPassword("pass8997"));
    }
}
