/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Preco;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface PrecoDAO {
    public void salvar(Preco SFEProd) throws SQLException;

    public ArrayList<Preco> pesquisar(Preco SFEProd) throws SQLException;
 //   public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
    public void close();
}
