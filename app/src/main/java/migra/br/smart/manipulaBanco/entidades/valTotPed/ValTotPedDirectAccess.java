/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.valTotPed;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;

/**
 *
 * @author ydxpaj
 */
public class ValTotPedDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<ValTotPed> pesquisar(SQLiteDatabase connection, ValTotPed obj) throws SQLException{
        ArrayList<ValTotPed> arrLresult = new ArrayList<ValTotPed>();

        cs = connection.rawQuery(
                "select * "
                 +"from valTotPed", null);

        while(cs.moveToNext()){
            ValTotPed fo = new ValTotPed();

            fo.setId(cs.getInt(cs.getColumnIndex("id")));
            fo.setIdPed(cs.getInt(cs.getColumnIndex("idPed")));
            fo.setTotal(cs.getDouble(cs.getColumnIndex("total")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, ValTotPed motivo) throws SQLException{
        statement = connection.compileStatement(
                "insert into valTotPed(idPed, total) " +
                        "values(?, ?)"
        );
        statement.clearBindings();
        statement.bindLong(1, motivo.getIdPed());
        statement.bindDouble(2, motivo.getTotal());

        statement.executeInsert();
    }

    public void update(SQLiteDatabase connection, ValTotPed valTotPed)throws SQLException{
        connection.update("valTotPed", valTotPed.getValues(), "id=?", new String[]{String.valueOf(valTotPed.getId())});
    }
}