/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.registro;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class RegistroDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Registro> pesquisar(SQLiteDatabase connection, Registro reg) throws SQLException{
        ArrayList<Registro> arrLresult = new ArrayList<Registro>();
/*
        cs = connection.rawQuery(
                "select * "+
                 "from produto "+
                "where proNome like ? and proCod like ? "+
                "and proLinha like ? limit 180;",
                new String[]{reg.getFantasia()+"%", codigo, reg.getLinha()+"%"});
        while(cs.moveToNext()){

            Registro r = new Registro();
            r.setSeq_id(cs.getInt(cs.getColumnIndex("id")));
            r.setKey(cs.getString(cs.getColumnIndex("key")));
            r.setUltima_data(cs.getLong(cs.getColumnIndex("ultima_data")));
            r.setData_expira(cs.getLong(cs.getColumnIndex("data_expira")));

            arrLresult.add(r);
        }*/
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Registro reg) throws SQLException{

        SQLiteStatement statement = connection.compileStatement(
                "insert into registro(key, data_expira, ultima_data, cnpjEmpresa) " +
                        "values(?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1,reg.getKey());//OK
        statement.bindLong(2,reg.getData_expira());//OK
        statement.bindLong(3,reg.getUltima_data());//OK
        statement.bindString(4, reg.getCnpjEmpresa());

        statement.executeInsert();
    }

    /**
     * @param connection
     * @return tipo Regristro se bem sucedido
     * @return null, caso não exista algum registro
     * @throws SQLException
     */
    public Registro getRegistro(SQLiteDatabase connection) throws SQLException{
        Registro r = new Registro();

        cs = connection.rawQuery(
                "select * from registro"
                ,null
        );

        while(cs.moveToNext()){
            r = new Registro();
            r.setId(cs.getInt(cs.getColumnIndex("id")));
            r.setKey(cs.getString(cs.getColumnIndex("key")));
            r.setUltima_data(cs.getLong(cs.getColumnIndex("ultima_data")));
            r.setData_expira(cs.getLong(cs.getColumnIndex("data_expira")));
            r.setCnpjEmpresa(cs.getString(cs.getColumnIndex("cnpjEmpresa")));
        }

        return r;
    }

    public void update(SQLiteDatabase connection, Registro reg) throws SQLException{
        connection.update("registro", reg.getValues(), "id=?",
                new String[]{String.valueOf(reg.getId())});
    }
}