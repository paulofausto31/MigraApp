/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.motivo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class MotivoDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Motivo> pesquisar(SQLiteDatabase connection, Motivo obj) throws SQLException{
        ArrayList<Motivo> arrLresult = new ArrayList<Motivo>();
        
        cs = connection.rawQuery(
                "select * "
                 +"from motivo", null);

        while(cs.moveToNext()){
            Motivo fo = new Motivo();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getLong(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            
            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Motivo motivo) throws SQLException{
        statement = connection.compileStatement(
                "insert into motivo(codigo, descricao) " +
                        "values(?, ?)"
        );
        statement.clearBindings();
        statement.bindLong(1, motivo.getCodigo());
        statement.bindString(2, motivo.getDescricao());
        statement.executeInsert();
    }
}
