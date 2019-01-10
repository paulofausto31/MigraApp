package migra.br.smart.manipulaBanco.entidades.empresas;

import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by r2d2 on 4/12/18.
 */

public class EmpresaTransaction implements EmpresaDAO{
    private SQLiteDatabase connection;

    @Override
    public void save(Empresa empresa) throws SQLException {
        try{
            new EmpresaDirectAccess().save(getConnection(), empresa);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            getConnection().close();
        }
    }

    @Override
    public ArrayList<Empresa> list(Empresa emp) throws SQLException {
        ArrayList<Empresa> list = null;

        try{
            list = new EmpresaDirectAccess().list(getConnection(), emp);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
        return list;
    }

    @Override
    public Empresa getForId(int id) throws SQLException {
        Empresa emp = null;

        try{
            emp = new EmpresaDirectAccess().getForId(getConnection(), id);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }

        return emp;
    }

    @Override
    public Empresa getForCnpj(String cnpj) throws SQLException {
        Empresa emp = null;

        try{
            emp = new EmpresaDirectAccess().getForCnpj(getConnection(), cnpj);
        }catch(SQLException ex){
            throw new SQLException(ex);
        }

        return emp;
    }

    @Override
    public void update(Empresa empresa) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public SQLiteDatabase getConnection() {
        return connection;
    }

    public void setConnection(SQLiteDatabase connection) {
        this.connection = connection;
    }
}
