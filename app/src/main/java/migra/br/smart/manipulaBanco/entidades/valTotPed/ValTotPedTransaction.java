/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.valTotPed;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;

/**
 *
 * @author ydxpaj
 */
public class ValTotPedTransaction implements ValTotPedDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(ValTotPed sseProd) throws SQLException{
        getConnection().beginTransaction();
        try {
            new ValTotPedDirectAccess().salvar(getConnection(), sseProd);
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
    public ArrayList<ValTotPed> pesquisar(ValTotPed fseProd) throws SQLException{
        ArrayList<ValTotPed> listFSEPrco = new ArrayList<ValTotPed>();
        getConnection().beginTransaction();
        try{
            listFSEPrco = new ValTotPedDirectAccess().pesquisar(getConnection(), fseProd);

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
    public void update(ValTotPed seq) throws SQLException {

    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
