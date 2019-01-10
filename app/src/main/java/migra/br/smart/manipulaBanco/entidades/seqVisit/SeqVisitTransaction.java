/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisit;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;

/**
 *
 * @author ydxpaj
 */
public class SeqVisitTransaction implements SeqVisitDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(SeqVisit sseProd) throws SQLException{
        getConnection().beginTransaction();
        try {
            new SeqVisitDirectAccess().salvar(getConnection(), sseProd);
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
    public ArrayList<SeqVisit> pesquisar(SeqVisit fseProd) throws SQLException{
        ArrayList<SeqVisit> listFSEPrco = new ArrayList<SeqVisit>();
        getConnection().beginTransaction();
        try{
            listFSEPrco = new SeqVisitDirectAccess().pesquisar(getConnection(), fseProd);
            
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
    public long count(long codRota) throws SQLException {
        long count = 0;
        try{
            count = new SeqVisitDirectAccess().count(getConnection(), codRota);
        }catch(SQLException ex){
            Log.e("banco", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return count;
    }

    @Override
    public ArrayList<SeqVisit> getForSalesMan(double codSalesMan) throws SQLException {
        ArrayList<SeqVisit> lista = new ArrayList<>();

        try{
            getConnection().beginTransaction();
            lista = new SeqVisitDirectAccess().getForSalesMan(getConnection(), codSalesMan);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("ErroSeqVisit", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }
        return lista;
    }

    @Override
    public ArrayList<Cliente> getClientes(long codRota) throws SQLException {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            getConnection().beginTransaction();
            lista = new SeqVisitDirectAccess().getClientes(getConnection(), codRota);
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }

        return lista;
    }

    @Override
    public ArrayList<SeqVisit> getWithClients(long codRota, Cliente clien) throws SQLException {
        ArrayList<SeqVisit> list = new ArrayList<SeqVisit>();
        try{
            list = new SeqVisitDirectAccess().getWithClients(getConnection(), codRota, clien);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public void update(SeqVisit seq) throws SQLException {
        try{
            new SeqVisitDirectAccess().update(getConnection(), seq);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SeqVisit getSeqVisit(double codCli) throws SQLException {
        SeqVisit seq = null;
        try{
            seq = new SeqVisitDirectAccess().getSeqVisit(getConnection(), codCli);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            connction.close();
        }
        return seq;
    }

}
