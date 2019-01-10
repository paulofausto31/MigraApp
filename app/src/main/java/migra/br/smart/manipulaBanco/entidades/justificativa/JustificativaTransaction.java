/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.justificativa;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class JustificativaTransaction implements JustificativaDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }

    @Override
    public void salvar(Justificativa sseProd) throws SQLException{
        getConnction().beginTransaction();
        try {
            new JustificativaDirectAccess().salvar(getConnction(), sseProd);
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
    public ArrayList<Justificativa> pesquisar(Justificativa fseProd) throws SQLException{
        ArrayList<Justificativa> listFSEPrco = new ArrayList<Justificativa>();
        getConnction().beginTransaction();
        try{
            listFSEPrco = new JustificativaDirectAccess().pesquisar(getConnction(), fseProd);
            
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
    public ArrayList<Justificativa> filterDescri(Justificativa obj) throws SQLException {
        ArrayList<Justificativa> list = new ArrayList<Justificativa>();
        try{
            list = new JustificativaDirectAccess().filterDescri(getConnction(), obj);
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
