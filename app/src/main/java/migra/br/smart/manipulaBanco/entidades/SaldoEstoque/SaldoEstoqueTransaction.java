/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.SaldoEstoque;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class SaldoEstoqueTransaction implements SaldoEstoqueDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }

    @Override
    public void salvar(SaldoEstoque sald) throws SQLException{
        getConnction().beginTransaction();
        try {
            new SaldoEstoqueDirectAccess().salvar(getConnction(), sald);
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
    public ArrayList<SaldoEstoque> pesquisar(SaldoEstoque fseProd) throws SQLException{
        ArrayList<SaldoEstoque> listFSEPrco = new ArrayList<SaldoEstoque>();
        getConnction().beginTransaction();
        try{
            listFSEPrco = new SaldoEstoqueDirectAccess().pesquisar(getConnction(), fseProd);
            
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
    public ArrayList<SaldoEstoque> filter(SaldoEstoque sald) throws SQLException {
        ArrayList<SaldoEstoque> listFSEPrco = new ArrayList<SaldoEstoque>();
        getConnction().beginTransaction();
        try{
            listFSEPrco = new SaldoEstoqueDirectAccess().filter(getConnction(), sald);
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
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
