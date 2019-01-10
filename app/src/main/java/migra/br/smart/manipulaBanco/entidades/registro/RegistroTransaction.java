/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.registro;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.Preco.Preco;

/**
 *
 * @author ydxpaj
 */
public class RegistroTransaction implements RegistroDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }
        
    
    @Override
    public void salvar(Registro reg) throws SQLException {
        //getConnection().beginTransaction();
        try{
            new RegistroDirectAccess().salvar(getConnection(), reg);
            //getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e("registroTransaction", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            //getConnection().endTransaction();
            getConnection().close();
        }
    }

    @Override
    public Registro getRegistro() throws SQLException {
        Registro r = null;
        try {
            r = new RegistroDirectAccess().getRegistro(getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return r;
    }

    @Override
    public ArrayList<Registro> pesquisar(Registro reg) throws SQLException{
        ArrayList<Registro> list = new ArrayList<Registro>();
        try{
            getConnection().beginTransaction();
            list = new RegistroDirectAccess().pesquisar(getConnection(), reg);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
                Log.e(ex.getMessage(),  "registroTransaction");
                throw new SQLException(ex);
        }finally{
                getConnection().endTransaction();
                getConnection().close();
        }
        return list;
    }

    @Override
    public void update(Registro reg) throws SQLException {
        try{
            new RegistroDirectAccess().update(getConnection(), reg);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public void close() {

    }
}
