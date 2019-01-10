/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.itemLista;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ItemListaDAO {
    public void salvar(ItemLista venda) throws SQLException;
    public void update(ItemLista itemLista) throws SQLException;
    public ArrayList<ItemLista> pesquisar(ItemLista venda) throws SQLException;
    public ItemLista getForId(String id) throws SQLException;
    public long getId(String codProd) throws SQLException;
 //   public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException;
 public void delForIdItem(String id) throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
    public long getMaxId() throws SQLException;
    public void close();
}
