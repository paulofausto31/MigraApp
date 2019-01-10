package migra.br.smart.manipulaBanco.entidades.empresas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by r2d2 on 4/12/18.
 */

public class EmpresaDirectAccess {

    private SQLiteStatement stmt;
    private Cursor cs;
    public void save(SQLiteDatabase con, Empresa empresa) throws SQLException {
        stmt = con.compileStatement(
            "insert into empresas(cnpj, rzSocial, fantasia, db, tipo) values(?, ?, ?, ?, ?)"
        );
        stmt.bindString(1, empresa.getCnpj());
        stmt.bindString(2, empresa.getRzSocial());
        stmt.bindString(3, empresa.getFantasia());
        stmt.bindString(4, empresa.getDb());
        stmt.bindString(5, empresa.getTipo());
        stmt.execute();
    }

    public Empresa getForCnpj(SQLiteDatabase con, String cnpj) throws SQLException {
        Empresa empresa = new Empresa();

        cs = con.rawQuery(
                //"select * from empresas where fantasia "+logiFantasia+"  or rzSocial "+logiRzSoci+" or cnpj "+logiCnpj,
                "select * from empresas where cnpj = ? ",
                new String[]{String.valueOf(empresa.getCnpj())}
        );

        while(cs.moveToNext()){
            empresa.setCnpj(cs.getString(cs.getColumnIndex("cnpj")));
            empresa.setRzSocial(cs.getString(cs.getColumnIndex("rzSocial")));
            empresa.setFantasia(cs.getString(cs.getColumnIndex("fantasia")));
            empresa.setId(cs.getInt(cs.getColumnIndex("id")));
            empresa.setDb(cs.getString(cs.getColumnIndex("db")));
            empresa.setTipo(cs.getString(cs.getColumnIndex("tipo")));
        }

        return empresa;
    }

    public Empresa getForId(SQLiteDatabase con, int id) throws SQLException {
        Empresa empresa = new Empresa();

        cs = con.rawQuery(
                //"select * from empresas where fantasia "+logiFantasia+"  or rzSocial "+logiRzSoci+" or cnpj "+logiCnpj,
                "select * from empresas where id = ? ",
                new String[]{String.valueOf(empresa.getId())}
        );

        while(cs.moveToNext()){
            empresa.setCnpj(cs.getString(cs.getColumnIndex("cnpj")));
            empresa.setRzSocial(cs.getString(cs.getColumnIndex("rzSocial")));
            empresa.setFantasia(cs.getString(cs.getColumnIndex("fantasia")));
            empresa.setId(cs.getInt(cs.getColumnIndex("id")));
            empresa.setDb(cs.getString(cs.getColumnIndex("db")));
            empresa.setTipo(cs.getString(cs.getColumnIndex("tipo")));
        }

        return empresa;
    }

    public ArrayList<Empresa> list(SQLiteDatabase con, Empresa emp) throws SQLException {
        ArrayList<Empresa> list = new ArrayList<Empresa>();

        String logiRzSoci = " = ? ";
        String logiCnpj = " = ? ";
        String logiFantasia = " = ? ";

        if(emp.getCnpj().equals("")){
            logiCnpj = " like '%?%'";
        }
        if(emp.getRzSocial().equals("")){
            logiRzSoci = " like '%?%'";
        }
        if(emp.getFantasia().equals("")){
            logiFantasia = " like '%?%'";
        }
        Log.e("que", "select * from empresas where fantasia "+logiFantasia+"  or rzSocial "+logiRzSoci+" or cnpj "+logiCnpj);
        cs = con.rawQuery(
            //"select * from empresas where fantasia "+logiFantasia+"  or rzSocial "+logiRzSoci+" or cnpj "+logiCnpj,
                "select * from empresas where fantasia like ?  or rzSocial like ? or cnpj like ? ",
                new String[]{"%"+emp.getFantasia()+"%", "%"+emp.getRzSocial()+"%", "%"+emp.getCnpj()+"%"}
        );

        while(cs.moveToNext()){
            Empresa empresa = new Empresa();
            empresa.setCnpj(cs.getString(cs.getColumnIndex("cnpj")));
            empresa.setRzSocial(cs.getString(cs.getColumnIndex("rzSocial")));
            empresa.setFantasia(cs.getString(cs.getColumnIndex("fantasia")));
            empresa.setId(cs.getInt(cs.getColumnIndex("id")));
            empresa.setDb(cs.getString(cs.getColumnIndex("db")));
            empresa.setTipo(cs.getString(cs.getColumnIndex("tipo")));

            Log.e("empre", empresa.getFantasia());

            list.add(empresa);
        }

        return list;
    }

    public void update(SQLiteDatabase con, Empresa empresa) throws SQLException {
        con.update("empresas", empresa.getValues(), "id = ?", new String[]{String.valueOf(empresa.getId())});
    }

    public void delete(SQLiteDatabase con, int id) throws SQLException {
        con.delete("empresas", "id = ?", new String[]{String.valueOf(id)});
    }
}