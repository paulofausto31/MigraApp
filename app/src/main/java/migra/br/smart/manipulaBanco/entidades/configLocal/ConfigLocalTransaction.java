/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configLocal;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ConfigLocalTransaction implements ConfigLocalDAO {

    SQLiteDatabase connection;

    public void setConnection(SQLiteDatabase connction) {
        this.connection = connction;
    }

    public SQLiteDatabase getConnection() {
        return connection;
    }

    @Override
    public void salvar(ConfigLocal configLocal) throws SQLException{
        getConnection().beginTransaction();
        try {
            new ConfigLocalDirectAccess().salvar(getConnection(), configLocal);
            getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e("erroConLocal", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public ArrayList<ConfigLocal> pesquisar(ConfigLocal configLocal) throws SQLException{
        ArrayList<ConfigLocal> listFSEPrco = new ArrayList<ConfigLocal>();
        getConnection().beginTransaction();
        try{

            
            listFSEPrco = new ConfigLocalDirectAccess().pesquisar(getConnection(), configLocal);
            
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
                getConnection().endTransaction();
                getConnection().close();
        }
        
        return listFSEPrco;
    }

    @Override
    public void atualizar(ConfigLocal configLocal) throws SQLException {
        getConnection().beginTransaction();
        try {
            new ConfigLocalDirectAccess().atualizar(getConnection(), configLocal);
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
    public void deletar(int id) {

    }
    
}
