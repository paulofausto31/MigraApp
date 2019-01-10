/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ProdutoDAO {
    public void salvar(Produto prod) throws SQLException;

    public ArrayList<Produto> pesquisar(Produto prod) throws SQLException;
 //   public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException;
    public void close();
}
