/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import cep.cepaberto.CEPAberto;
import cep.cepla.CepLa;
import cep.correios.Correios;
import cep.viacep.ViaCEP;
import cep.viacep.ViaCEPException;
import cep.webmaniabr.WebManiaBr;
import cep.widenet.Widenet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

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
        
        Future<CEPBean> futureCorreios = consultaCepCorreiosAsync(cep);
        Future<CEPBean> futureViaCep = consultaViaCepAsync(cep);
        Future<CEPBean> futureCepAberto = consultaCepAbertoAsync(cep);
        Future<CEPBean> futureWidenet = consultaWidenetAsync(cep);
        Future<CEPBean> futureWebManiaBr = WebManiaBr.consultaWebManiaBrAsync(cep);
        Future<CEPBean> futureCepLa = CepLa.consultaCepLaAsync(cep);

        while (!futureCorreios.isDone() && !futureViaCep.isDone() && !futureCepAberto.isDone() && !futureWidenet.isDone() && !futureWebManiaBr.isDone() && !futureCepLa.isDone()) {
            System.out.println("BUSCANDO CEP...");
        }

        if (futureViaCep.isDone()) cepBean = futureViaCep.get(10, TimeUnit.SECONDS);
        if (futureCorreios.isDone()) cepBean = futureCorreios.get(10, TimeUnit.SECONDS);
        if (futureCepAberto.isDone()) cepBean = futureCepAberto.get(10, TimeUnit.SECONDS);
        if (futureWidenet.isDone()) cepBean = futureWidenet.get(10, TimeUnit.SECONDS);
        if (futureWebManiaBr.isDone()) cepBean = futureWebManiaBr.get(10, TimeUnit.SECONDS);
        if (futureCepLa.isDone()) cepBean = futureCepLa.get(10, TimeUnit.SECONDS);
        
        return cepBean;
    }

    private static CEPBean consultaViaCep(String cep) throws ViaCEPException {
        CEPBean cepBean = new CEPBean();
        ViaCEP viaCep = new ViaCEP();
        viaCep.buscar(cep);
        cepBean.setEndereco(viaCep.getLogradouro());
        cepBean.setBairro(viaCep.getBairro());
        cepBean.setCidade(viaCep.getLocalidade());
        cepBean.setUf(viaCep.getUf());
        cepBean.setApi("ViaCep");
        return cepBean;
    }

    private static Future<CEPBean> consultaViaCepAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return consultaViaCep(cep);
            } catch (ViaCEPException ex) {
                Logger.getLogger(Cep.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });
    }

    private static CEPBean consultaCepAberto(String cep) throws JSONException {
        CEPBean cepBean = new CEPBean();
        JSONObject jsonCep = CEPAberto.getCep(cep);
        cepBean.setEndereco(jsonCep.getString("logradouro"));
        cepBean.setBairro(jsonCep.getString("bairro"));
        JSONObject cidade = jsonCep.getJSONObject("cidade");
        cepBean.setCidade(cidade.getString("nome"));
        JSONObject estado = jsonCep.getJSONObject("estado");
        cepBean.setUf(estado.getString("sigla"));
        cepBean.setApi("CepAberto");
        return cepBean;
    }

    private static Future<CEPBean> consultaCepAbertoAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            return consultaCepAberto(cep);
        });
    }

    private static CEPBean consultaWidenet(String cep) throws JSONException {
        CEPBean cepBean = new CEPBean();
        JSONObject jsonCep = Widenet.getCep(cep);
        cepBean.setEndereco(jsonCep.getString("address"));
        cepBean.setBairro(jsonCep.getString("district"));
        cepBean.setCidade(jsonCep.getString("city"));
        cepBean.setUf(jsonCep.getString("state"));
        cepBean.setApi("Widenet");
        return cepBean;
    }

    private static Future<CEPBean> consultaWidenetAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            return consultaWidenet(cep);
        });
    }

    private static CEPBean consultaCepCorreios(String cep) throws SigepClienteException, SQLException_Exception {
        CEPBean cepBean = new CEPBean();
        EnderecoERP result = Correios.consultaCepCorreios(cep);
        cepBean.setEndereco(result.getEnd());
        cepBean.setBairro(result.getBairro());
        cepBean.setCidade(result.getCidade());
        cepBean.setUf(result.getUf());
        cepBean.setApi("Correios");
        return cepBean;
    }

    private static Future<CEPBean> consultaCepCorreiosAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return consultaCepCorreios(cep);
            } catch (SigepClienteException | SQLException_Exception ex) {
                Logger.getLogger(Cep.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });
    }

}
