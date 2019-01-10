/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.listaPedido;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ListaPedidoRN {
    ListaPedidoDAO listaPedidoDAO;
    
    public ListaPedidoRN(Context ctx){
        listaPedidoDAO = new DAOFactory().criaListaPedidoDAO(ctx);
    }

    public ArrayList<ListaPedido> getProdsVendidos(ListaPedido lp) throws SQLException{
        return listaPedidoDAO.getProdsVendidos(lp);
    }

    public ArrayList<ListaPedido> getForNomeProd(String nomeProd) throws SQLException{
        return this.listaPedidoDAO.getForNomeProd(nomeProd);
    }

    public ArrayList<ItemLista> getItemLista(long idPedido) throws SQLException {
        return this.listaPedidoDAO.getItemLista(idPedido);
    }

    public ListaPedido getListaPedido(long idPedido) throws SQLException {
        return this.listaPedidoDAO.getListaPedido(idPedido);
    }

    public ArrayList<ListaPedido> listProdsVendidos(ListaPedido lp) throws SQLException{
        return this.listaPedidoDAO.listProdsVendidos(lp);
    }

    public String getIdItem(String codProd, double codCli, long codPedido) throws SQLException {
        return this.listaPedidoDAO.getIdItem(codProd, codCli, codPedido);
    }

    public String[] getQtdItemListaPedido(String codProd, double codCli, long idPedido) throws SQLException {
        return this.listaPedidoDAO.getQtdItemListaPedido(codProd, codCli, idPedido);
    }

    public String[] listForCodPedAndCli(double codigo) throws SQLException{
        return this.listaPedidoDAO.listForCodPedAndCli(codigo);
    }

    public void delForIdItem(long idItem) throws SQLException {
        this.listaPedidoDAO.delForIdItem(idItem);
    }

    public void deleteForIdPedido(long idPedido) throws SQLException {
        this.listaPedidoDAO.deleteForIdPedido(idPedido);
    }

    public void delForStatus(long idPedido) throws SQLException {
        this.listaPedidoDAO.delForStatus(idPedido);
    }

    public void updateForIdItem(ListaPedido lp) throws SQLException {
        this.listaPedidoDAO.updateForIdItem(lp);
    }

    public void salvar(ListaPedido lp) throws SQLException {
        this.listaPedidoDAO.salvar(lp);
    }
}
