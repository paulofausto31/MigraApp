/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.vendedor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class VendedorDirectAccess {

    SQLiteStatement statement;
    Cursor cs;

    public ArrayList<Vendedor> pesquisar(SQLiteDatabase connection, Vendedor obj) throws SQLException {
        ArrayList<Vendedor> list = new ArrayList<Vendedor>();

        cs = connection.rawQuery(
                "select * from vendedor", null
        );

        while (cs.moveToNext()) {
            Vendedor sgeVend = new Vendedor();
            sgeVend.setId(cs.getLong(cs.getColumnIndex("id")));
            sgeVend.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
            sgeVend.setNome(cs.getString(cs.getColumnIndex("nome")));

            list.add(sgeVend);
        }

        return list;
    }

    public void salvar(SQLiteDatabase connection, Vendedor sgeVend) throws SQLException{
        statement = connection.compileStatement(
                "insert into vendedor(codigo, nome) " +
                        "values(?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, sgeVend.getCodigo());
        statement.bindString(2, sgeVend.getNome());
        statement.executeInsert();
    }

    public void atualizar(SQLiteDatabase connection, Vendedor sgeVend){
        connection.update("vendedor", sgeVend.getContentValues(), "codigo = ?", new String[]{String.valueOf(sgeVend.getCodigo())});
    }

}
