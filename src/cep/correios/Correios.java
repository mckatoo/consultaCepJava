/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep.correios;

import cep.EnderecoERP;
import cep.SQLException_Exception;
import cep.SigepClienteException;

/**
 *
 * @author mckatoo
 */
public class Correios {

    public static EnderecoERP consultaCepCorreios(java.lang.String cep) throws SigepClienteException, SQLException_Exception {
        cep.AtendeClienteService service = new cep.AtendeClienteService();
        cep.AtendeCliente port = service.getAtendeClientePort();
        return port.consultaCEP(cep);
    }

}
