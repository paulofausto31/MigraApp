/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.fornecedor;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class FornecedorRN {
    FornecedorDAO fornecedorDAO;

    public FornecedorRN(Context ctx){
        fornecedorDAO = new DAOFactory().criaFornecedor(ctx);
    }

    public ArrayList<Fornecedor> pesquisar(Fornecedor fornecedor) throws SQLException{
        return this.fornecedorDAO.pesquisar(fornecedor);
    }
    
    public void salvar(Fornecedor fornecedor) throws SQLException{
        this.fornecedorDAO.salvar(fornecedor);
    }
}
