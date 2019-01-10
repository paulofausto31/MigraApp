/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.erro;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ErroDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Erro> pesquisar(SQLiteDatabase connection, Erro obj) throws SQLException{
        ArrayList<Erro> arrLresult = new ArrayList<Erro>();

        cs = connection.rawQuery(
                "select * "
                 +"from erro", null);

        while(cs.moveToNext()){
            Erro fo = new Erro();

            fo.setId(cs.getInt(cs.getColumnIndex("id")));
            fo.setHora(cs.getString(cs.getColumnIndex("hora")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            fo.setData(cs.getLong(cs.getColumnIndex("data")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }

    /**
     * Filtra por descrição
     * @param connection
     * @param obj
     * @return
     * @throws SQLException
     */
    public ArrayList<Erro> filterDescri(SQLiteDatabase connection, Erro obj) throws SQLException{
        ArrayList<Erro> arrLresult = new ArrayList<Erro>();

        cs = connection.rawQuery(
                "select * "
                        +"from erro where descricao = ?",
                    new String[]{obj.getDescricao()}
                );

        while(cs.moveToNext()){
            Erro fo = new Erro();

            fo.setId(cs.getInt(cs.getColumnIndex("id")));
            fo.setHora(cs.getString(cs.getColumnIndex("hora")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            fo.setData(cs.getLong(cs.getColumnIndex("data")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Erro linha) throws SQLException{
        statement = connection.compileStatement(
                "insert into erro(hora, descricao, data) " +
                        "values(?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, linha.getHora());
        statement.bindString(2, linha.getDescricao());
        statement.bindLong(3, linha.getData());

        statement.executeInsert();
    }
}