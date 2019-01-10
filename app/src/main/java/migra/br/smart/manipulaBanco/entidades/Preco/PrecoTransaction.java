/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Preco;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class PrecoTransaction implements PrecoDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }
        
    
    @Override
    public void salvar(Preco SFEProd) throws SQLException {
        getConnction().beginTransaction();
        try{
            new PrecoDirectAccess().salvar(getConnction(), SFEProd);
            getConnction().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e(ex.getMessage(), "banco");
            throw new SQLException(ex);
        }finally{
            getConnction().endTransaction();
            getConnction().close();
        }
    }


    @Override
    public ArrayList<Preco> pesquisar(Preco SFEProd) throws SQLException{
        ArrayList<Preco> arrLFSEProd = new ArrayList<Preco>();
        try{
            getConnction().beginTransaction();
            
            arrLFSEProd = new PrecoDirectAccess().pesquisar(getConnction(), SFEProd);
            
            getConnction().setTransactionSuccessful();
        }catch(SQLException ex){

                Log.e("banco", ex.getMessage());

                throw new SQLException(ex);

        }finally{
                getConnction().endTransaction();
                getConnction().close();
        }
        
        return arrLFSEProd;
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
    public void close() {

    }

}
