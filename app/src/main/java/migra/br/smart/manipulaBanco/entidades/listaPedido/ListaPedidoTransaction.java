/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.listaPedido;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoDirectAccess;

/**
 *
 * @author ydxpaj
 */
public class ListaPedidoTransaction implements ListaPedidoDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }


    @Override
    public ArrayList<ItemLista> getItemLista(long idPedido) throws SQLException {
        ArrayList<ItemLista> list = null;

        try{
            list = new ListaPedidoDirectAccess().getItemLista(getConnection(), idPedido);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public void salvar(ListaPedido SFEProd) throws SQLException {
        getConnection().beginTransaction();
        try{
            new ListaPedidoDirectAccess().salvar(getConnection(), SFEProd);
            getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e(ex.getMessage(), "banco");
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public ArrayList<ListaPedido> getProdsVendidos(ListaPedido lp) throws SQLException {
        ArrayList<ListaPedido> list = new ArrayList<>();

        try{
            list = new ListaPedidoDirectAccess().getProdsVendidos(getConnection(), lp);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }

        return list;
    }

    @Override
    public ArrayList<ListaPedido> listProdsVendidos(ListaPedido lp) throws SQLException {
        ArrayList<ListaPedido> list = new ArrayList<>();

        try{
            list = new ListaPedidoDirectAccess().listProdsVendidos(getConnection(), lp);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
        return list;
    }

    @Override
    public ListaPedido getListaPedido(long idPedido) throws SQLException {
        return new ListaPedidoDirectAccess().getListaPedido(getConnection(), idPedido);
    }


    @Override
    public ArrayList<ListaPedido> getForNomeProd(String nomeProd) throws SQLException{
        ArrayList<ListaPedido> arrLFSEProd = new ArrayList<ListaPedido>();
        try{
            getConnection().beginTransaction();
            arrLFSEProd = new ListaPedidoDirectAccess().getForNomeProd(getConnection(), nomeProd);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
                Log.e("banco", ex.getMessage());
                throw new SQLException(ex);
        }finally{
                getConnection().endTransaction();
                getConnection().close();
        }
        
        return arrLFSEProd;
    }

    @Override
    public String getIdItem(String codProd, double codCli, long codPedido) throws SQLException {
        String idItem = "0";

        try{
            getConnection().beginTransaction();
            idItem = new ListaPedidoDirectAccess().getIdItem(getConnection(),codProd, codCli, codPedido);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }

        return idItem;
    }

    @Override
    public String[] getQtdItemListaPedido(String codProd, double codCli, long idPedido) throws SQLException {
        String lPedItemQtd[] = new String[]{"0", "0"};
        try{
            getConnection().beginTransaction();
            lPedItemQtd = new ListaPedidoDirectAccess().getQtdItemListaPedido(getConnection(), codProd, codCli, idPedido);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("ERRO SQL", ex.getMessage());
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }

        return lPedItemQtd;
    }

    @Override
    public String[] listForCodPedAndCli(double codigo) throws SQLException{
        String[] cods = null;
        try{
            //getConnection().beginTransaction();
            cods = new ListaPedidoDirectAccess().listForCodPedAndCli(getConnection(), codigo);//busca por codigo do cliente e do pedido
            //getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            //getConnection().endTransaction();
            getConnection().close();
        }
        return cods;
    }

    @Override
    public void updateForIdItem(ListaPedido lp) throws SQLException {
        try {
            getConnection().beginTransaction();
            new ListaPedidoDirectAccess().updateForIdItem(getConnection(), lp);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("ErroListaPedidoUpdate", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public void delForStatus(long idPedido) throws SQLException {
        try{
            getConnection().beginTransaction();
            new ListaPedidoDirectAccess().delForStatus(getConnection(), idPedido);//deleta por coluna 'deletar' s ou n
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public void delForIdItem(long idItem) throws SQLException {
        try {
            getConnection().beginTransaction();
            new ListaPedidoDirectAccess().delForIdItem(getConnection(), idItem);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public void deleteForIdPedido(long idPedido) throws SQLException {
        try {
            getConnection().beginTransaction();
            new ListaPedidoDirectAccess().deleteForIdPedido(getConnection(), idPedido);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {

    }

}
