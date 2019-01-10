/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Preco;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class PrecoRN {
    PrecoDAO precoDAO;
    
    public PrecoRN(Context ctx){

        precoDAO = new DAOFactory().criaPreco(ctx);
    }
    
    public ArrayList<Preco> pesquisar(Preco SFEProd) throws SQLException{
        return this.precoDAO.pesquisar(SFEProd);
    }

    public void salvar(Preco SFEProd) throws SQLException {
        this.precoDAO.salvar(SFEProd);
    }
}
