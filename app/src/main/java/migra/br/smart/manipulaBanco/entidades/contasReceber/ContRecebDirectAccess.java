/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.contasReceber;

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
public class ContRecebDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<ContReceb> pesquisar(SQLiteDatabase connection, ContReceb obj) throws SQLException{
        ArrayList<ContReceb> arrLresult = new ArrayList<ContReceb>();
        cs = connection.rawQuery(
                "select cliente.nome as nomeClie, contaReceb.codCliente as codCli, " +
                        "contaReceb.numTitulo as titulo, contaReceb.datEmissao as emissao ," +
                        "contaReceb.dataVenci as vence, contaReceb.valor as valor, " +
                        "contaReceb.codFormPgment as formaPaga, contaReceb.vOriginal as vOrig " +
                "from cliente " +
                    "inner join contaReceb on contaReceb.codCliente = cliente.codigo limit 180", null);

        while(cs.moveToNext()){
            ContReceb receb = new ContReceb();

            receb.setDataEmissao(cs.getString(cs.getColumnIndex("emissao")));
            receb.setDataVenci(cs.getString(cs.getColumnIndex("vence")));
            receb.setCodCliente(cs.getDouble(cs.getColumnIndex("codCli")));
            receb.setFormPg(cs.getString(cs.getColumnIndex("formaPaga")));
            receb.setNumTitulo(cs.getString(cs.getColumnIndex("titulo")));
            receb.setValor(cs.getDouble(cs.getColumnIndex("valor")));
            receb.setvOriginal(cs.getDouble(cs.getColumnIndex("vOrig")));

            receb.getCliente().setFantasia(cs.getString(cs.getColumnIndex("nomeClie")));
            
            arrLresult.add(receb);
        }
        return arrLresult;
    }

    public ArrayList<ContReceb> searchForCli(SQLiteDatabase connection, double codCli) throws SQLException{
        ArrayList<ContReceb> arrLresult = new ArrayList<ContReceb>();
        cs = connection.rawQuery(
                " select cliente.nome as nomeClie, contaReceb.id as contRecebId, contaReceb.codVenda as codVedaContaReceb, " +
                        " contaReceb.vendedor as vendedorContaReceb, contaReceb.codCliente as codCli , contaReceb.numTitulo as tituContReceb, " +
                        " contaReceb.datEmissao as emissaoContaReceb, contaReceb.dataVenci as vence, contaReceb.valor as valorContReceb, " +
                        " contaReceb.codFormPgment as formaPaga, contaReceb.vOriginal as vOrig " +
                " from contaReceb inner join cliente on contaReceb.codCliente = cliente.codigo " +
                        " where contaReceb.codCliente = ? limit 180",
                new String[]{String.valueOf(codCli)});

        while(cs.moveToNext()){
            ContReceb receb = new ContReceb();

            receb.setDataEmissao(cs.getString(cs.getColumnIndex("emissaoContaReceb")));
            receb.setDataVenci(cs.getString(cs.getColumnIndex("vence")));
            receb.setCodCliente(cs.getDouble(cs.getColumnIndex("codCli")));
            receb.setFormPg(cs.getString(cs.getColumnIndex("formaPaga")));
            receb.setNumTitulo(cs.getString(cs.getColumnIndex("tituContReceb")));
            receb.setValor(cs.getDouble(cs.getColumnIndex("valorContReceb")));
            receb.getCliente().setFantasia(cs.getString(cs.getColumnIndex("nomeClie")));
            receb.setId(cs.getLong(cs.getColumnIndex("contRecebId")));
            receb.setvOriginal(cs.getDouble(cs.getColumnIndex("vOrig")));

            arrLresult.add(receb);
        }
        return arrLresult;
    }
    /*"id integer primary key autoincrement, " +
                   "idCodCli long," +//id do cliente
                   " numTitulo text," +
                   " datEmissao text," +//date
                   " dataVenci text," +//date
                   " valor double, " +
                   "idFormPgment long," +//id do tipo de pagamento*/
    public void salvar(SQLiteDatabase connection, ContReceb formPgment) throws SQLException{
        statement = connection.compileStatement(
                "insert into contaReceb(codCliente, numTitulo, datEmissao, dataVenci," +
                        "valor, codFormPgment, codVenda, vOriginal) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindDouble(1, formPgment.getCodCliente());
        statement.bindString(2, formPgment.getNumTitulo());
        statement.bindString(3, formPgment.getDataEmissao());
        statement.bindString(4, formPgment.getDataVenci());
        statement.bindDouble(5, formPgment.getValor());
        statement.bindString(6, formPgment.getFormPg());
        statement.bindString(7, formPgment.getCodVenda());
        statement.bindDouble(8, formPgment.getvOriginal());
        statement.executeInsert();
    }
}
