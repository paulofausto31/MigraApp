/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.formaPagamento;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class FormPgmentTransaction implements FormPgmentDAO {

    SQLiteDatabase connction;

    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }

    @Override
    public void salvar(FormPgment sseProd) throws SQLException{
        getConnction().beginTransaction();
        try {
            new FormPgmenDirecAccess().salvar(getConnction(), sseProd);
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
    public ArrayList<FormPgment> pesquisar(FormPgment fseProd) throws SQLException{
        ArrayList<FormPgment> listFSEPrco = new ArrayList<FormPgment>();
        getConnction().beginTransaction();
        try{

            
            listFSEPrco = new FormPgmenDirecAccess().pesquisar(getConnction(), fseProd);
            
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

    /**
     * FILTRA A FORMA DE PAGAMENTO PELO CODIGO
     * @param obj
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<FormPgment> filtrarForCod(FormPgment obj) throws SQLException {
        ArrayList<FormPgment> list = new ArrayList<FormPgment>();
        try{
            list = new FormPgmenDirecAccess().filtrarForCod(getConnction(), obj);
        }catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex);
        }finally{
            getConnction().close();
        }
        return list;
    }

    @Override
    public ArrayList<String> listaFormPagamento() throws SQLException {
        ArrayList<String> listFSEPrco = null;
        getConnction().beginTransaction();
        try{
            listFSEPrco = new FormPgmenDirecAccess().listaFormPagamento(getConnction());
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
