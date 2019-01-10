/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.itemLista;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoDirectAccess;

/**
 *
 * @author ydxpaj
 */
public class ItemListaTransaction implements ItemListaDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }
        
    
    @Override
    public void salvar(ItemLista SFEProd) throws SQLException {
        getConnection().beginTransaction();
        try{
            new ItemListaDirectAccess().salvar(getConnection(), SFEProd);
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
    public void update(ItemLista itemLista) throws SQLException {
//        getConnection().beginTransaction();
        try{

            new ItemListaDirectAccess().update(getConnection(), itemLista);
  //          getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e(ex.getMessage(), "banco");
            throw new SQLException(ex);
        }finally {
    //        getConnection().endTransaction();
            getConnection().close();
        }

    }

    @Override
    public ArrayList<ItemLista> pesquisar(ItemLista SFEProd) throws SQLException{
        ArrayList<ItemLista> arrLFSEProd = new ArrayList<ItemLista>();
        try{
            getConnection().beginTransaction();
            
            arrLFSEProd = new ItemListaDirectAccess().pesquisar(getConnection(), SFEProd);
            
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){

                Log.e("banco", ex.getMessage());

                throw new SQLException(ex);

        }finally{
                getConnection().endTransaction();
                getConnection().close();
            Log.i("itemLista", "CLOSED");
        }
        
        return arrLFSEProd;
    }

    @Override
    public ItemLista getForId(String id) throws SQLException {
        ItemLista i = null;

        try{
            i = new ItemListaDirectAccess().getForId(getConnection(), id);
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            getConnection().close();
            Log.i("itemLista", "id"+id+"----CLOSED");
        }
        return i;
    }

    @Override
    public long getId(String codProd) throws SQLException {
        long id = 0;
        try{
            getConnection().beginTransaction();

            id = new ItemListaDirectAccess().getId(getConnection(), codProd);

            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
        return id;
    }

    @Override
    public void delForIdItem(String id) throws SQLException {
        try{
            getConnection().beginTransaction();
            new ItemListaDirectAccess().delForIdItem(getConnection(), id);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    /*@Override
    public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException {
        ArrayList<ItemLista> arrLFSEProd = new ArrayList<ItemLista>();
        try{
            getConnection().beginTransaction();

            arrLFSEProd = new ItemListaDirectAccess().pesquisarNome(getConnection(), ItemLista);

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
*/
    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getMaxId() throws SQLException {
        long maxId = 0;

        try{
            maxId = new ItemListaDirectAccess().getMaxId(getConnection());
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }

        return maxId;
    }

    @Override
    public void close() {

    }
}