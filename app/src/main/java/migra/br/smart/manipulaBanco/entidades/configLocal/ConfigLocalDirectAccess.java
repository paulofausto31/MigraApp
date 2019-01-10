/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configLocal;

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
public class ConfigLocalDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<ConfigLocal> pesquisar(SQLiteDatabase connection, ConfigLocal obj) throws SQLException{
        ArrayList<ConfigLocal> arrLresult = new ArrayList<ConfigLocal>();

        cs = connection.rawQuery(
                "select * "
                 +"from con_local", null);

        while(cs.moveToNext()){
            ConfigLocal con = new ConfigLocal();
            con.setId(cs.getLong(cs.getColumnIndex("id")));
            con.setIp(cs.getString(cs.getColumnIndex("ip")));
            con.setPorta(cs.getLong(cs.getColumnIndex("porta")));
            con.setCnpjEmpresa(cs.getString(cs.getColumnIndex("cnpjEmpresa")));
            con.setKey(cs.getString(cs.getColumnIndex("key")));
            con.setEmailDestino(cs.getString(cs.getColumnIndex("emailDestino")));
            con.setMultBanco(cs.getInt(cs.getColumnIndex("multBanco")));
            arrLresult.add(con);
        }


        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, ConfigLocal configLocal) throws SQLException{
        statement = connection.compileStatement(
                "insert into con_local(ip, porta, cnpjEmpresa, key, emailDestino, multBanco) " +
                        "values(?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, configLocal.getIp());
        statement.bindLong(2, configLocal.getPorta());
        statement.bindString(3, configLocal.getCnpjEmpresa());
        statement.bindString(4, configLocal.getKey());
        statement.bindString(5, configLocal.getEmailDestino());
        statement.bindLong(6, configLocal.getMultBanco());
        statement.execute();
    }

    public void atualizar(SQLiteDatabase connection, ConfigLocal configLocal) throws SQLException{

        connection.update("con_local", configLocal.getContentValues(), "id=?", new String[]{"1"});
    }

    public void deletar(SQLiteDatabase connection, long id) throws SQLException{

        statement = connection.compileStatement(
                "deletar from con_local where id >= ?"
        );
        statement.bindLong(1, id);
        statement.execute();
    }
}
