/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.listaPedido;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;

/**
 *
 * @author ydxpaj
 */
public interface ListaPedidoDAO {
    public ArrayList<ItemLista> getItemLista(long idPedido) throws SQLException;

    public void salvar(ListaPedido SFEProd) throws SQLException;
    public ArrayList<ListaPedido> getProdsVendidos(ListaPedido lp) throws SQLException;
    public ArrayList<ListaPedido> listProdsVendidos(ListaPedido lp) throws SQLException;
    public ListaPedido getListaPedido(long idPedido) throws SQLException;
    public ArrayList<ListaPedido> getForNomeProd(String nomeProd) throws SQLException;
    public String getIdItem(String codProd, double codCli, long codPedido)throws SQLException;
    public String[] getQtdItemListaPedido(String codProd, double codCli, long idPedido) throws SQLException;
    public String[] listForCodPedAndCli(double codigo) throws SQLException;
    public void updateForIdItem(ListaPedido lp) throws SQLException;
    public void delForStatus(long idPedido) throws SQLException;
 public void delForIdItem(long idItem) throws SQLException;
    public void deleteForIdPedido(long idPedido) throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
    public void close();
}
