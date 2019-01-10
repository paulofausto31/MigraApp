/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.cliente;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ClienteTransaction implements ClienteDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }        
    
    @Override
    public void salvar(Cliente sseProd) throws SQLException{
        getConnction().beginTransaction();
        try {
            new ClienteDirectAccess().salvar(getConnction(), sseProd);
            getConnction().setTransactionSuccessful();
        } catch (SQLException e) {
            throw new SQLException(e);
        }finally{
            getConnction().endTransaction();
            getConnction().close();
        }
    }

    @Override
    public void update(Cliente c) throws SQLException {

    }

    @Override
    public ArrayList<Cliente> pesquisar(Cliente sgeClie) throws SQLException{
        ArrayList<Cliente> list = new ArrayList<Cliente>();
        try{
            getConnction().beginTransaction();
            
            list = new ClienteDirectAccess().pesquisar(getConnction(), sgeClie);
            
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
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
