/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.negativacao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class NegativacaoTransaction implements NegativacaoDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(Negativacao sseProd) throws SQLException{
        getConnection().beginTransaction();
        try {
            new NegativacaoDirectAccess().salvar(getConnection(), sseProd);
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
    public ArrayList<Negativacao> pesquisar(Negativacao fseProd) throws SQLException{
        ArrayList<Negativacao> listFSEPrco = new ArrayList<Negativacao>();
        getConnection().beginTransaction();
        try{
            listFSEPrco = new NegativacaoDirectAccess().pesquisar(getConnection(), fseProd);
            
            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("negativacao", ex.getMessage());
            throw new SQLException(ex);
        }finally{
                getConnection().endTransaction();
                getConnection().close();
        }
        
        return listFSEPrco;
    }

    @Override
    public ArrayList<Negativacao> filtrar(Negativacao obj) throws SQLException {
        ArrayList<Negativacao> lNeg = new ArrayList<Negativacao>();
        try {
            lNeg = new NegativacaoDirectAccess().filtrar(getConnection(), obj);
        }catch(SQLException ex){
            Log.e("negativacao", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return lNeg;
    }

    @Override
    public Negativacao getForId(int id) throws SQLException {
        Negativacao neg = null;

        try{
            neg = new NegativacaoDirectAccess().getForId(getConnection(), id);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return neg;
    }

    @Override
    public long count() throws SQLException {
        long total = 0;
        try{
             total = new NegativacaoDirectAccess().count(getConnection());
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return total;
    }

    @Override
    public int getMaxId() throws SQLException {
        int maxId = 0;
        try{
            maxId = new NegativacaoDirectAccess().getMaxId(getConnection());
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return maxId;
    }

/*
    @Override
    public ArrayList<Negativacao> getForSalesMan(double codSalesMan) throws SQLException {
        ArrayList<Negativacao> lista = new ArrayList<>();

        try{
            getConnection().beginTransaction();
            lista = new NegativacaoDirectAccess().getForSalesMan(getConnection(), codSalesMan);
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
*/
/*
    @Override
    public ArrayList<Cliente> getClientes(long codRota) throws SQLException {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            getConnection().beginTransaction();
            lista = new NegativacaoDirectAccess().getClientes(getConnection(), codRota);
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
*/

    @Override
    public ArrayList<Negativacao> getWithClients(Negativacao neg) throws SQLException {
        ArrayList<Negativacao> list = new ArrayList<>();
        try{
            list = new NegativacaoDirectAccess().getWithClients(getConnection(), neg);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public ArrayList<Negativacao> getWithCodClients(Negativacao negFilter) throws SQLException {
        ArrayList<Negativacao> list = new ArrayList<>();
        try{
            list = new NegativacaoDirectAccess().getWithCodClients(getConnection(), negFilter);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public void update(Negativacao seq) throws SQLException {
        try{
            new NegativacaoDirectAccess().update(getConnection(), seq);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public void deletar(int id) throws SQLException{
        try{
            new NegativacaoDirectAccess().deletar(getConnection(), id);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public void deletarForFilter(Negativacao neg) throws SQLException {
        try{
            new NegativacaoDirectAccess().deletarForFilter(getConnection(), neg);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

}
