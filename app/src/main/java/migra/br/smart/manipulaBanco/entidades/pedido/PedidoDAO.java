/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.pedido;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;

/**
 *
 * @author ydxpaj
 */
public interface PedidoDAO {
    public void salvar(Pedido SFEProd) throws SQLException;
    public void update(Pedido p) throws SQLException;
    public ArrayList<Pedido> filtrar(Pedido pedido) throws SQLException;
    public ArrayList<Pedido> listForCliAndStstus(Pedido pf) throws SQLException;
    public ArrayList<Pedido> getOpenOrClose(Pedido pedido) throws SQLException;
    public ArrayList<Pedido> pesquisar(Pedido SFEProd) throws SQLException;
    public long getMaxId(Pedido p) throws SQLException;
    public String[] listForCodPedAndCli() throws SQLException;
    public Pedido getForId(long id) throws SQLException;
    public void duplicar(Pedido p, ListaPedido lp) throws SQLException;
    public void delPedidoForId(long id) throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
    public void close();
}
