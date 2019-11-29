/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep.cepaberto;

import cep.CEPBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mckatoo
 */
public class CEPAberto {

    private static String sendGet(String url) {
        try {
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "CepAberto");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Token token=d8a5c0d4a4b41dd62441953bf34c6fda");
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }

            in.close();
            return buffer.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject getCep(String cep) {
        String json = sendGet("http://www.cepaberto.com/api/v3/cep?cep=" + cep);
        return new JSONObject(json);
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

    public static Future<CEPBean> consultaCepAbertoAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            return consultaCepAberto(cep);
        });
    }
}
