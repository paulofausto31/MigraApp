/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.fornecedor;

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
public class FornecedorDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Fornecedor> pesquisar(SQLiteDatabase connection, Fornecedor obj) throws SQLException{
        ArrayList<Fornecedor> arrLresult = new ArrayList<Fornecedor>();
        
        cs = connection.rawQuery(
                "select * "
                 +"from fornecedor", null);

        while(cs.moveToNext()){
            Fornecedor fo = new Fornecedor();

            fo.setId(cs.getLong(cs.getColumnIndex("id")));
            fo.setCodigo(cs.getDouble(cs.getColumnIndex("codigo")));
            fo.setNome(cs.getString(cs.getColumnIndex("nome")));
            
            arrLresult.add(fo);
        }
        
        
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Fornecedor fornecedor) throws SQLException{
        statement = connection.compileStatement(
                "insert into fornecedor(codigo, nome, razaoSocial) " +
                        "values(?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindDouble(1, fornecedor.getCodigo());
        statement.bindString(2, fornecedor.getNome());
        statement.bindString(3, fornecedor.getRazaoSocial());
        statement.executeInsert();
    }
}
