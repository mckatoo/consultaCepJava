/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mckatoo
 */
public class CepTest {
    
    private static CEPBean expResult = null;
    
    public CepTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        expResult = new CEPBean();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of consultaCEP method, of class Cep.
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     */
    @Test
    public void testConsultaCEP() throws InterruptedException, ExecutionException, TimeoutException {
        String cep = "13976110";
        
        expResult.setEndereco("Rua Doutor Miguel Couto");
        expResult.setBairro("Vila Esperança");
        expResult.setCidade("Itapira");
        expResult.setUf("SP");
        
        CEPBean result = Cep.consultaCEP(cep);
        System.out.println("API DE CEP MAIS RÁPIDA NA CONSULTA SÍNCRONA = " + result.getApi());
        assertEquals(expResult.getEndereco(), result.getEndereco());
        assertEquals(expResult.getBairro(), result.getBairro());
        assertEquals(expResult.getCidade(), result.getCidade());
        assertEquals(expResult.getUf(), result.getUf());
    }
    
    @Test
    public void testConsultaCEPasync() throws InterruptedException, ExecutionException, TimeoutException {
        String cep = "13973481";
        
        expResult.setEndereco("Rua Santa Terezinha");
        expResult.setBairro("Jardim Guarujá");
        expResult.setCidade("Itapira");
        expResult.setUf("SP");
        
        CEPBean result = Cep.consultaCEPasync(cep).get(10, TimeUnit.SECONDS);
        System.out.println("API DE CEP MAIS RÁPIDA NA CONSULTA ASSÍNCRONA = " + result.getApi());
        assertEquals(expResult.getEndereco(), result.getEndereco());
        assertEquals(expResult.getBairro(), result.getBairro());
        assertEquals(expResult.getCidade(), result.getCidade());
        assertEquals(expResult.getUf(), result.getUf());
    }
    
}
