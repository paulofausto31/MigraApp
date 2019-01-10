/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Preco;

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
public class PrecoDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Preco> pesquisar(SQLiteDatabase connection, Preco obj) throws SQLException{
        ArrayList<Preco> arrLresult = new ArrayList<Preco>();

        String codigo = "";
        if(!obj.getCodigo().equals("")){
            codigo = obj.getCodigo()+"%";
        }

        cs = connection.rawQuery(
                "select * "+
                "from preco where codProd = ?;",
                new String[]{obj.getCodigo()+""});
        while(cs.moveToNext()){

            Preco preco = new Preco();
            preco.setCodProd(cs.getString(cs.getColumnIndex("codProd")));
            preco.setId(cs.getLong(cs.getColumnIndex("id")));
            preco.setVendeAVista(cs.getString(cs.getColumnIndex("vendeAVista")));
            preco.setValor(cs.getDouble(cs.getColumnIndex("valor")));

            arrLresult.add(preco);
        }
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Preco obj) throws SQLException{

        SQLiteStatement statement = connection.compileStatement(
                "insert into preco(codProd, valor, vendeAVista) " +
                        "values(?, ?, ?);"
        );
        statement.clearBindings();
        statement.bindString(1,obj.getCodigo());//OK
        statement.bindDouble(2,obj.getValor());//OK
        statement.bindString(3,obj.getVendeAVista());//OK

        statement.executeInsert();
    }
}
