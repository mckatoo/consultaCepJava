/*
 * PARG Desenvolvimento de Sistemas
 * Pablo Alexander - pablo@parg.com.br
 * 
 * Obtem um CEP no ViaCEP
 */
package cep.viacep;

import cep.CEPBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe java para obter um CEP no ViaCEP
 *
 * @author Pablo Alexander da Rocha Gonçalves
 */
public class ViaCEP {

    private static String sendGet(String url) {
        try {
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
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
        String json = sendGet("https://viacep.com.br/ws/" + cep + "/json/ ");
        return new JSONObject(json);
    }

    private static CEPBean consultaViaCep(String cep) throws JSONException {
        CEPBean cepBean = new CEPBean();
        JSONObject jsonCep = getCep(cep);
        cepBean.setEndereco(jsonCep.getString("logradouro"));
        cepBean.setBairro(jsonCep.getString("bairro"));
        cepBean.setCidade(jsonCep.getString("localidade"));
        cepBean.setUf(jsonCep.getString("uf"));
        cepBean.setApi("ViaCep");
        return cepBean;
    }

    public static Future<CEPBean> consultaViaCepAsync(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            return consultaViaCep(cep);
        });
    }
}
