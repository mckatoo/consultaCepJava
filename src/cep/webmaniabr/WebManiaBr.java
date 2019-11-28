/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep.webmaniabr;

import cep.CEPBean;
import cep.cepaberto.CEPAberto;
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
public class WebManiaBr {
    private static String sendGet(String url) {
        try {
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "WebManiaBr");
            conn.setRequestProperty("Accept", "application/json");
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
        String urlBase = "https://webmaniabr.com/api/";
        String app_key = "D7BxN8spEecIACVKQkuCmrIkhPryHYv8";
        String app_secret = "X69rQC5CPBie3EYIvgLLgY9aUWeCUOG2OUNOs327b8VKqnjO";
        String apiVersion = "1";
        String json = sendGet(urlBase + apiVersion + "/cep/" + cep + "/?app_key=" + app_key + "&app_secret=" + app_secret);
        return new JSONObject(json);
    }
    
    
    private static CEPBean consultaWebManiaBr(String cep) throws JSONException {
        CEPBean cepBean = new CEPBean();
        JSONObject jsonCep = getCep(cep);
        cepBean.setEndereco(jsonCep.getString("endereco"));
        cepBean.setBairro(jsonCep.getString("bairro"));
        cepBean.setCidade(jsonCep.getString("cidade"));
        cepBean.setUf(jsonCep.getString("uf"));
        cepBean.setApi("WebManiaBr");
        return cepBean;
    }

    public static Future<CEPBean> consultaWebManiaBrAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            return consultaWebManiaBr(cep);
        });
    }
}
