/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.vendedor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class VendedorTransaction implements VendedorDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }        
    
    @Override
    public void salvar(Vendedor sseProd) throws SQLException{
        getConnction().beginTransaction();
        try {
            new VendedorDirectAccess().salvar(getConnction(), sseProd);
            getConnction().setTransactionSuccessful();
        } catch (SQLException e) {
            throw new SQLException(e);
        }finally{
            getConnction().endTransaction();
            getConnction().close();
        }
    }

    @Override
    public ArrayList<Vendedor> pesquisar(Vendedor sgeClie) throws SQLException{
        ArrayList<Vendedor> list = new ArrayList<Vendedor>();
        try{
            getConnction().beginTransaction();
            
            list = new VendedorDirectAccess().pesquisar(getConnction(), sgeClie);
            
            getConnction().setTransactionSuccessful();
        }catch(SQLException ex){
            Log.e("erroSql", ex.getMessage());
            throw new SQLException(ex);
        }finally{
            getConnction().endTransaction();
            getConnction().close();
        }
        
        return list;
    }

    @Override
    public void atualizar(Vendedor sgeVend) throws SQLException {

    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
