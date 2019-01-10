/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.vendedor;


import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class VendedorRN {
    VendedorDAO SGEVendDAO;

    public VendedorRN(Context ctx){
        SGEVendDAO = new DAOFactory().criaVendedor(ctx);
    }

    public ArrayList<Vendedor> pesquisar(Vendedor fseProd) throws SQLException{
        return this.SGEVendDAO.pesquisar(fseProd);
    }

    public void salvar(Vendedor sfePrco) throws SQLException{
        this.SGEVendDAO.salvar(sfePrco);
    }

    public void atualizar(Vendedor sgeVend) throws SQLException{
        this.SGEVendDAO.atualizar(sgeVend);
    }
}
