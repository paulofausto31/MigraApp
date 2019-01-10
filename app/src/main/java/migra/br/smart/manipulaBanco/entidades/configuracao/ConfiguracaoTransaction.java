/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configuracao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ConfiguracaoTransaction implements ConfiguracaoDAO {

    SQLiteDatabase connection;

    public void setConnection(SQLiteDatabase connction) {
        this.connection = connction;
    }

    public SQLiteDatabase getConnection() {
        return connection;
    }

    @Override
    public void salvar(Configuracao conLocal) throws SQLException{
        getConnection().beginTransaction();
        try {
            new ConFiguracaoDirectAccess().salvar(getConnection(), conLocal);
            getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e("erroSql", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public ArrayList<Configuracao> pesquisar(Configuracao config) throws SQLException{
        ArrayList<Configuracao> listFSEPrco = new ArrayList<Configuracao>();

        try{
            getConnection().beginTransaction();
            listFSEPrco = new ConFiguracaoDirectAccess().pesquisar(getConnection(), config);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
                getConnection().endTransaction();
                getConnection().close();
        }
        
        return listFSEPrco;
    }

    @Override
    public void atualizar(Configuracao conLocal) throws SQLException {
        getConnection().beginTransaction();
        try {
            new ConFiguracaoDirectAccess().atualizar(getConnection(), conLocal);
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
