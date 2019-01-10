/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.erro;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ErroTransaction implements ErroDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }

    @Override
    public void salvar(Erro sseProd) throws SQLException{
        getConnction().beginTransaction();
        try {
            new ErroDirectAccess().salvar(getConnction(), sseProd);
            getConnction().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e("erroSql", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnction().endTransaction();
            getConnction().close();
        }
    }

    @Override
    public ArrayList<Erro> pesquisar(Erro fseProd) throws SQLException{
        ArrayList<Erro> listFSEPrco = new ArrayList<Erro>();
        getConnction().beginTransaction();
        try{
            listFSEPrco = new ErroDirectAccess().pesquisar(getConnction(), fseProd);
            
            getConnction().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
                getConnction().endTransaction();
                getConnction().close();
        }
        
        return listFSEPrco;
    }

    @Override
    public ArrayList<Erro> filterDescri(Erro obj) throws SQLException {
        ArrayList<Erro> list = new ArrayList<Erro>();
        try{
            list = new ErroDirectAccess().filterDescri(getConnction(), obj);
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            getConnction().close();
        }
        return list;
    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
