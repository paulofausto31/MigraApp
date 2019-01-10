/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.cliente;


import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ClienteRN {
    ClienteDAO SGEClieDAO;
    
    public ClienteRN(Context ctx){
        SGEClieDAO = new DAOFactory().criaSGEClie(ctx);
    }
    
    public ArrayList<Cliente> pesquisar(Cliente fseProd) throws SQLException{
        return this.SGEClieDAO.pesquisar(fseProd);
    }
    
    public void salvar(Cliente sfePrco) throws SQLException{
        this.SGEClieDAO.salvar(sfePrco);
    }

    public void update(Cliente c) throws SQLException{
        this.SGEClieDAO.update(c);
    }
}
