/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisitStatus;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class SeqVisitStatusTransaction implements SeqVisitStatusDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(SeqVisitStatus seqVisitStatus) throws SQLException{
        getConnection().beginTransaction();
        try {
            new SeqVisitStatusDirectAccess().salvar(getConnection(), seqVisitStatus);
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
    public ArrayList<SeqVisitStatus> pesquisar(SeqVisitStatus fseProd) throws SQLException{
        ArrayList<SeqVisitStatus> listFSEPrco = new ArrayList<SeqVisitStatus>();
        getConnection().beginTransaction();
        try{
            listFSEPrco = new SeqVisitStatusDirectAccess().pesquisar(getConnection(), fseProd);
            
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
    public ArrayList<SeqVisitStatus> getListSeqVisitStat() throws SQLException {
        ArrayList<SeqVisitStatus> list = null;
        try{
            list = new SeqVisitStatusDirectAccess().getListSeqVisitStat(getConnection());
        }catch(SQLException ex){
            Log.e("ERR_SELEC", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public long count(long codRota) throws SQLException {
        long count = 0;
        try{
            count = new SeqVisitStatusDirectAccess().count(getConnection(), codRota);
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return count;
    }

    @Override
    public void update(SeqVisitStatus seq) throws SQLException {
        try{
            new SeqVisitStatusDirectAccess().update(getConnection(), seq);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public void deletar(String sr_recno) throws SQLException{
        try{
            new SeqVisitStatusDirectAccess().deletar(getConnection(), sr_recno);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
    }
}
