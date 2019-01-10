/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.justificativa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class JustificativaDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Justificativa> pesquisar(SQLiteDatabase connection, Justificativa obj) throws SQLException{
        ArrayList<Justificativa> arrLresult = new ArrayList<Justificativa>();

        cs = connection.rawQuery(
                "select * "
                 +"from justificativa", null);

        while(cs.moveToNext()){
            Justificativa fo = new Justificativa();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));

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
    public ArrayList<Justificativa> filterDescri(SQLiteDatabase connection, Justificativa obj) throws SQLException{
        ArrayList<Justificativa> arrLresult = new ArrayList<Justificativa>();

        cs = connection.rawQuery(
                "select * "
                        +"from justificativa where descricao = ?",
                    new String[]{obj.getDescricao()}
                );

        while(cs.moveToNext()){
            Justificativa fo = new Justificativa();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Justificativa linha) throws SQLException{
        statement = connection.compileStatement(
                "insert into justificativa(codigo, descricao) " +
                        "values(?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, linha.getCodigo());
        statement.bindString(2, linha.getDescricao());

        statement.executeInsert();
    }
}