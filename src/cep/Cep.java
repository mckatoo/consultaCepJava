/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import static cep.cepaberto.CEPAberto.consultaCepAbertoAsync;
import cep.cepla.CepLa;
import cep.viacep.ViaCEP;
import cep.webmaniabr.WebManiaBr;
import static cep.widenet.Widenet.consultaWidenetAsync;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;

/**
 *
 * @author mckatoo
 */
public class Cep {

    /**
     * @param cep
     * @return cep.CEPBean
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     */
    public static CEPBean consultaCEP(String cep) throws InterruptedException, ExecutionException, TimeoutException, JSONException {
        CEPBean cepBean = null;
        
        Future<CEPBean> futureViaCep = ViaCEP.consultaViaCepAsync(cep);
        Future<CEPBean> futureCepAberto = consultaCepAbertoAsync(cep);
        Future<CEPBean> futureWidenet = consultaWidenetAsync(cep);
        Future<CEPBean> futureWebManiaBr = WebManiaBr.consultaWebManiaBrAsync(cep);
        Future<CEPBean> futureCepLa = CepLa.consultaCepLaAsync(cep);

        while (!futureViaCep.isDone() && !futureCepAberto.isDone() && !futureWidenet.isDone() && !futureWebManiaBr.isDone() && !futureCepLa.isDone()) {
            System.out.println("BUSCANDO CEP...");
        }

        if (futureViaCep.isDone()) cepBean = futureViaCep.get(10, TimeUnit.SECONDS);
        if (futureCepAberto.isDone()) cepBean = futureCepAberto.get(10, TimeUnit.SECONDS);
        if (futureWidenet.isDone()) cepBean = futureWidenet.get(10, TimeUnit.SECONDS);
        if (futureWebManiaBr.isDone()) cepBean = futureWebManiaBr.get(10, TimeUnit.SECONDS);
        if (futureCepLa.isDone()) cepBean = futureCepLa.get(10, TimeUnit.SECONDS);
        
        return cepBean;
    }
}
