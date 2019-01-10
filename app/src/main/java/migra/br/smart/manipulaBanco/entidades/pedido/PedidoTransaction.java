/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.pedido;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaDirectAccess;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoDirectAccess;

/**
 *
 * @author ydxpaj
 */
public class PedidoTransaction implements PedidoDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }
        
    
    @Override
    public void salvar(Pedido SFEProd) throws SQLException {
        getConnection().beginTransaction();
        try{
            new PedidoDirectAccess().salvar(getConnection(), SFEProd);
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
    public ArrayList<Pedido> pesquisar(Pedido SFEProd) throws SQLException{
        ArrayList<Pedido> arrLFSEProd = new ArrayList<Pedido>();
        try{
            getConnection().beginTransaction();

            arrLFSEProd = new PedidoDirectAccess().filtrar(getConnection(), SFEProd);
            
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
    public long getMaxId(Pedido p) throws SQLException{
        long maxId = 0;
        try{
            getConnection().beginTransaction();
            maxId = new PedidoDirectAccess().getMaxId(getConnection(), p);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
        return maxId;
    }

    @Override
    public Pedido getForId(long id) throws SQLException {
        Pedido p = new Pedido();
        try{
            getConnection().beginTransaction();
            p = new PedidoDirectAccess().getForId(getConnection(), id);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
        return p;
    }

    @Override
    public String[] listForCodPedAndCli() throws SQLException {
        String[] codigos = null;
        try {
            codigos = new PedidoDirectAccess().listForCodPedAndCli(getConnection());
        }catch(SQLException ex){
            Log.e("ERRO_PEDIDO", ex.getMessage());
        }finally{
            getConnection().close();
        }
        return codigos;
    }

    @Override
    public void duplicar(Pedido p, ListaPedido lp) throws SQLException {
        try {
            ItemListaDirectAccess itDrect = new ItemListaDirectAccess();
            new PedidoDirectAccess().salvar(getConnection(), p);
            Log.i("PedidoTransaction", lp.getItemLista().getId()+"");
            new ItemListaDirectAccess().salvar(getConnection(), lp.getItemLista());
            new ListaPedidoDirectAccess().salvar(getConnection(), lp);

        }catch(SQLException ex){
            Log.e("erroDuplicarPedido", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public void delPedidoForId(long id) throws SQLException {
        try{
            getConnection().beginTransaction();
            new ListaPedidoDirectAccess().deleteForIdPedido(getConnection(), id);
            new PedidoDirectAccess().delPedidoForId(getConnection(), id);

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
    public void update(Pedido p) throws SQLException {
        try{
            getConnection().beginTransaction();

            new PedidoDirectAccess().update(getConnection(), p);

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
    public ArrayList<Pedido> filtrar(Pedido pedido) throws SQLException {
        ArrayList<Pedido> list = null;
        try{
            list = new PedidoDirectAccess().filtrar(getConnection(), pedido);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public ArrayList<Pedido> listForCliAndStstus(Pedido pf) throws SQLException {
        ArrayList<Pedido> list = null;
        try{
            list = new PedidoDirectAccess().listForCliAndStstus(getConnection(), pf);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public ArrayList<Pedido> getOpenOrClose(Pedido pedido) throws SQLException {
        ArrayList<Pedido> list = null;
        try{
            list = new PedidoDirectAccess().getOpenOrClose(getConnection(), pedido);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {

    }

}
