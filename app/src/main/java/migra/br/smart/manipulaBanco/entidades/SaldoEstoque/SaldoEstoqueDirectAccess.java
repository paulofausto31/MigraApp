/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.SaldoEstoque;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class SaldoEstoqueDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<SaldoEstoque> pesquisar(SQLiteDatabase connection, SaldoEstoque obj) throws SQLException{
        ArrayList<SaldoEstoque> arrLresult = new ArrayList<SaldoEstoque>();
        
        cs = connection.rawQuery(
                "select * "
                 +"from saldEstoq", null);

        while(cs.moveToNext()){
            SaldoEstoque fo = new SaldoEstoque();
            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setDeposito(cs.getLong(cs.getColumnIndex("deposito")));
            fo.setProduto(cs.getString(cs.getColumnIndex("produto")));
            fo.setSaldo(cs.getDouble(cs.getColumnIndex("saldo")));
            
            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public ArrayList<SaldoEstoque> filter(SQLiteDatabase connection, SaldoEstoque obj) throws SQLException{
        ArrayList<SaldoEstoque> arrLresult = new ArrayList<SaldoEstoque>();

        cs = connection.rawQuery(
                "select * from saldEstoq where produto = ?", new String[]{obj.getProduto()});

        while(cs.moveToNext()){
            SaldoEstoque fo = new SaldoEstoque();
            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setDeposito(cs.getLong(cs.getColumnIndex("deposito")));
            fo.setProduto(cs.getString(cs.getColumnIndex("produto")));
            fo.setSaldo(cs.getDouble(cs.getColumnIndex("saldo")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, SaldoEstoque sald) throws SQLException{
        statement = connection.compileStatement(
                "insert into saldEstoq(deposito, produto, saldo) " +
                        "values(?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindLong(1, sald.getDeposito());
        statement.bindString(2, sald.getProduto());
        statement.bindDouble(3, sald.getSaldo());

        statement.executeInsert();
    }
}