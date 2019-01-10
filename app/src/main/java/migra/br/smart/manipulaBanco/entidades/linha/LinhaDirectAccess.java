/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.linha;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class LinhaDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Linha> pesquisar(SQLiteDatabase connection, Linha obj) throws SQLException{
        ArrayList<Linha> arrLresult = new ArrayList<Linha>();
        
        cs = connection.rawQuery(
                "select * "
                 +"from linha", null);

        while(cs.moveToNext()){
            Linha fo = new Linha();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            
            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Linha linha) throws SQLException{
        statement = connection.compileStatement(
                "insert into linha(codigo, descricao, comissao1, comissao2, foto, controlaLote) " +
                        "values(?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, linha.getCodigo());
        statement.bindString(2, linha.getDescricao());
        statement.bindString(3, linha.getComissao1());
        statement.bindString(4, linha.getComissao2());
        statement.bindString(5, linha.getFoto());
        statement.bindString(6, linha.getControlaLote());

        statement.executeInsert();
    }
}