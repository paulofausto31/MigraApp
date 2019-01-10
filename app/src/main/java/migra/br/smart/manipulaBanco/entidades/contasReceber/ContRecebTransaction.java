/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.contasReceber;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ContRecebTransaction implements ContRecebDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnection() {
        return connction;
    }

    @Override
    public void salvar(ContReceb sseProd) throws SQLException{
        getConnection().beginTransaction();
        try {
            new ContRecebDirectAccess().salvar(getConnection(), sseProd);
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
    public ArrayList<ContReceb> pesquisar(ContReceb fseProd) throws SQLException{
        ArrayList<ContReceb> listFSEPrco = new ArrayList<ContReceb>();
        getConnection().beginTransaction();
        try{

            
            listFSEPrco = new ContRecebDirectAccess().pesquisar(getConnection(), fseProd);
            
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
    public ArrayList<ContReceb> searchForCli(double codCli) throws SQLException {
        ArrayList<ContReceb> list = new ArrayList<>();
        try{
            list = new ContRecebDirectAccess().searchForCli(getConnection(), codCli);
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            getConnection().close();
        }
        return list;
    }

    @Override
    public void deletar(int sr_recno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
