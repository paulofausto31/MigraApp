/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.comodato;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ComodatoTransaction implements ComodatoDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(Comodato sseProd) throws SQLException{
        getConnection().beginTransaction();
        try {
            new ComodatoDirectAccess().salvar(getConnection(), sseProd);
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
    public ArrayList<Comodato> pesquisar(Comodato fseProd) throws SQLException{
        ArrayList<Comodato> listFSEPrco = new ArrayList<Comodato>();
        getConnection().beginTransaction();
        try{
            listFSEPrco = new ComodatoDirectAccess().pesquisar(getConnection(), fseProd);

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
/*
    @Override
    public ArrayList<Comodato> getRouteForSalesMan(String codSalesMan) throws SQLException{
        ArrayList<Comodato> lista = new ArrayList<Comodato>();

        try{
            getConnection().beginTransaction();
            lista = new ComodatoDirectAccess().getRouteForSalesMan(getConnection(), codSalesMan);
            getConnection().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e("erroRota", ex.getMessage());
        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }

        return lista;
    }
*/
    @Override
    public void update(Comodato r) {

    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
