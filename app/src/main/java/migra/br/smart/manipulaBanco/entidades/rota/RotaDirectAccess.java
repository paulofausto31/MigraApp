/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.rota;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class RotaDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Rota> pesquisar(SQLiteDatabase connection, Rota obj) throws SQLException{
        ArrayList<Rota> arrLresult = new ArrayList<Rota>();
        
        cs = connection.rawQuery(
                "select * "
                 +"from rota", null);

        while(cs.moveToNext()){
            Rota fo = new Rota();

            fo.setId(cs.getInt(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getLong(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            fo.setVendedor(cs.getDouble(cs.getColumnIndex("vendedor")));
            fo.setDiaVender(cs.getString(cs.getColumnIndex("diaVender")));
            
            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public ArrayList<Rota> getRouteForSalesMan(SQLiteDatabase connection, String codSalesMan) throws SQLException{
        ArrayList<Rota> lista = new ArrayList<>();

        cs = connection.rawQuery(
                "select * from rota where vendedor = ?",
                new String[]{codSalesMan}
        );
        while(cs.moveToNext()){
            Rota r = new Rota();
            r.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            r.setVendedor(cs.getDouble(cs.getColumnIndex("vendedor")));
            r.setCodigo(cs.getLong(cs.getColumnIndex("codigo")));
            r.setId(cs.getInt(cs.getColumnIndex("id")));
            r.setDiaVender(cs.getString(cs.getColumnIndex("diaVender")));

            lista.add(r);
        }

        return lista;
    }

    public void salvar(SQLiteDatabase connection, Rota motivo) throws SQLException{
        statement = connection.compileStatement(
                "insert into rota(codigo, descricao, vendedor, diaVender) " +
                        "values(?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindLong(1, motivo.getCodigo());
        statement.bindString(2, motivo.getDescricao());
        statement.bindDouble(3, motivo.getVendedor());
        statement.bindString(4, motivo.getDiaVender());
        statement.executeInsert();
    }
}