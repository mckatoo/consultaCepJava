/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import cep.CEPAberto.Api;
import cep.correios.Correios;
import cep.viacep.ViaCEP;
import cep.viacep.ViaCEPException;
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
     */
    public static CEPBean consultaCEP(String cep) {
        CEPBean cepBean = new CEPBean();
        try {
            ViaCEP viaCep = new ViaCEP();
            viaCep.buscar(cep);
            cepBean.setEndereco(viaCep.getLogradouro());
            cepBean.setBairro(viaCep.getBairro());
            cepBean.setCidade(viaCep.getLocalidade());
            cepBean.setUf(viaCep.getUf());
        } catch (ViaCEPException ex1) {
            System.out.println("ERRO AO PESQUISAR NO ViaCEP: " + ex1.getMessage() + "\nPESQUISANDO NO CEPABERTO...");
            try {
                JSONObject jsonCep = Api.getCep(cep);
                cepBean.setEndereco(jsonCep.getString("logradouro") + ", ");
                cepBean.setBairro(jsonCep.getString("bairro"));
                JSONObject cidade = jsonCep.getJSONObject("cidade");
                cepBean.setCidade(cidade.getString("nome"));
                JSONObject estado = jsonCep.getJSONObject("estado");
                cepBean.setUf(estado.getString("sigla"));
            } catch (JSONException e) {
                System.out.println("ERRO AO PESQUISAR NO CEPABERTO: " + e.getMessage() + ".\nPESQUISANDO NO CORREIOS");
                try {
                    EnderecoERP result = Correios.consultaCepCorreios(cep);
                    cepBean.setEndereco(result.getEnd());
                    cepBean.setBairro(result.getBairro());
                    cepBean.setCidade(result.getCidade());
                    cepBean.setUf(result.getUf());
                } catch (SigepClienteException | SQLException_Exception ex) {
                    System.out.println("ERRO AO PESQUISAR NO CORREIOS: " + ex.getMessage());
                }
            }

        }
        return cepBean;
    }

}
