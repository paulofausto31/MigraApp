/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.rota;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class RotaTransaction implements RotaDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(Rota sseProd) throws SQLException{
        getConnection().beginTransaction();
        try {
            new RotaDirectAccess().salvar(getConnection(), sseProd);
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
    public ArrayList<Rota> pesquisar(Rota fseProd) throws SQLException{
        ArrayList<Rota> listFSEPrco = new ArrayList<Rota>();
        getConnection().beginTransaction();
        try{
            listFSEPrco = new RotaDirectAccess().pesquisar(getConnection(), fseProd);
            
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
    public ArrayList<Rota> getRouteForSalesMan(String codSalesMan) throws SQLException{
        ArrayList<Rota> lista = new ArrayList<Rota>();

        try{
            getConnection().beginTransaction();
            lista = new RotaDirectAccess().getRouteForSalesMan(getConnection(), codSalesMan);
            getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e("erroRota", ex.getMessage());
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }

        return lista;
    }

    @Override
    public void update(Rota r) {

    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
