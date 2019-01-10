/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Produto;

import android.content.Context;

import migra.br.smart.manipulaBanco.factory.DAOFactory;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ProdutoRN {
    ProdutoDAO ProdutoDAO;
    
    public ProdutoRN(Context ctx){

        ProdutoDAO = new DAOFactory().criaProduto(ctx);
    }
    
    public ArrayList<Produto> pesquisar(Produto prod) throws SQLException{
        return this.ProdutoDAO.pesquisar(prod);
    }
    /*public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException{
        return this.fsEProdDAO.pesquisarNome(ItemLista);
    }*/

    public void salvar(Produto prod) throws SQLException {
        this.ProdutoDAO.salvar(prod);
    }
}
