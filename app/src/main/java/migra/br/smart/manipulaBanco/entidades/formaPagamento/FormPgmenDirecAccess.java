/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.formaPagamento;

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
public class FormPgmenDirecAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    /*
    codigo, descricao, juros, multa, tipo, prazoPadrao, consideraCredito, permiteDesconto
     */
    public ArrayList<FormPgment> pesquisar(SQLiteDatabase connection, FormPgment obj) throws SQLException{
        ArrayList<FormPgment> arrLresult = new ArrayList<FormPgment>();
        
        cs = connection.rawQuery(
                "select * "
                 +"from formPgment", null);

        while(cs.moveToNext()){
            FormPgment fo = new FormPgment();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            fo.setJuros(cs.getDouble(cs.getColumnIndex("juros")));
            fo.setMulta(cs.getDouble(cs.getColumnIndex("multa")));
            fo.setTipo(cs.getString(cs.getColumnIndex("tipo")));
            fo.setPrazoPadrao(cs.getString(cs.getColumnIndex("prazoPadrao")));
            fo.setConsideraCredito(cs.getString(cs.getColumnIndex("consideraCredito")));
            fo.setPermiteDescont(cs.getString(cs.getColumnIndex("permiteDesconto")));
            
            arrLresult.add(fo);
        }
        return arrLresult;
    }

    public ArrayList<String> listaFormPagamento(SQLiteDatabase connection) throws SQLException{
        ArrayList<String> arrLresult = new ArrayList<String>();

        cs = connection.rawQuery(
                "select * from formPgment", null);

        while(cs.moveToNext()){
            FormPgment fo = new FormPgment();

            fo.setCodigo(cs.getString(cs.getColumnIndex("codigo")));

            arrLresult.add(fo.getCodigo());
        }
/*
        String[] codigos = new String[arrLresult.size()];
        for(int i = 0; i < arrLresult.size(); i++){
            codigos[i] = arrLresult.get(i).getHora();
        }

        return codigos;*/
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, FormPgment formPgment) throws SQLException{
        statement = connection.compileStatement(
                "insert into formPgment(codigo, descricao, juros, multa, tipo, prazoPadrao, " +
                        "consideraCredito, permiteDesconto) " +
                        "values(?, ?, ?, ?, ?, ?,  ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, formPgment.getCodigo());
        statement.bindString(2, formPgment.getDescricao());
        statement.bindDouble(3, formPgment.getJurus());
        statement.bindDouble(4, formPgment.getMulta());
        statement.bindString(5, formPgment.getTipo());
        statement.bindString(6, formPgment.getPrazoPadrao());
        statement.bindString(7, formPgment.getConsideraCredito());
        statement.bindString(8, formPgment.getPermiteDescont());
        statement.executeInsert();
    }

    /**
     * FILTRA POR CODIGO DA FORMA DE PAGAMENTO
     * @param connection
     * @param obj
     * @return
     * @throws SQLException
     */
    public ArrayList<FormPgment> filtrarForCod(SQLiteDatabase connection, FormPgment obj) throws SQLException{
        ArrayList<FormPgment> arrLresult = new ArrayList<FormPgment>();

        cs = connection.rawQuery(
                "select * "
                        +"from formPgment where codigo = ?", new String[]{obj.getCodigo()});

        while(cs.moveToNext()){
            FormPgment fo = new FormPgment();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getString(cs.getColumnIndex("codigo")));
            fo.setDescricao(cs.getString(cs.getColumnIndex("descricao")));
            fo.setJuros(cs.getDouble(cs.getColumnIndex("juros")));
            fo.setMulta(cs.getDouble(cs.getColumnIndex("multa")));
            fo.setTipo(cs.getString(cs.getColumnIndex("tipo")));
            fo.setPrazoPadrao(cs.getString(cs.getColumnIndex("prazoPadrao")));
            fo.setConsideraCredito(cs.getString(cs.getColumnIndex("consideraCredito")));
            fo.setPermiteDescont(cs.getString(cs.getColumnIndex("permiteDesconto")));

            arrLresult.add(fo);
        }
        return arrLresult;
    }
}
