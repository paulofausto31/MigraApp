/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.comodato;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ComodatoDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Comodato> pesquisar(SQLiteDatabase connection, Comodato como) throws SQLException{
        ArrayList<Comodato> arrLresult = new ArrayList<Comodato>();

        cs = connection.rawQuery(
                "select comodato.id as comoId, cliente.codigo as cliCod, cliente.nome as cliNome, produto.nome as prodNome, " +
                        "comodato.produto as comoCodProd, comodato.saldo as comoSaldo from comodato " +
                        "    inner join produto on comodato.produto = produto.codigo " +
                        "    inner join cliente on comodato.cliente = cliente.codigo " +
                        " where cliCod = ?", new String[]{String.valueOf(como.getCodCli())});

        while(cs.moveToNext()){
            Comodato com = new Comodato();

            com.setId(cs.getInt(cs.getColumnIndex("comoId")));
            com.setCodProd(cs.getString(cs.getColumnIndex("comoCodProd")));
            com.setSaldo(cs.getDouble(cs.getColumnIndex("comoSaldo")));
            com.setCodCli(cs.getDouble(cs.getColumnIndex("cliCod")));
            com.getProduto().setNome(cs.getString(cs.getColumnIndex("prodNome")));
            com.getCliente().setFantasia(cs.getString(cs.getColumnIndex("cliNome")));

            arrLresult.add(com);
        }

        return arrLresult;
    }
/*
    public ArrayList<Comodato> getRouteForSalesMan(SQLiteDatabase connection, String codSalesMan) throws SQLException{
        ArrayList<Comodato> lista = new ArrayList<>();

        cs = connection.rawQuery(
                "select * from rota where vendedor = ?",
                new String[]{codSalesMan}
        );
        while(cs.moveToNext()){
            Comodato r = new Comodato();
            r.setSaldo(cs.getString(cs.getColumnIndex("descricao")));
            r.setCodCli(cs.getDouble(cs.getColumnIndex("vendedor")));
            r.setCodProd(cs.getLong(cs.getColumnIndex("codigo")));
            r.setSeq_id(cs.getInt(cs.getColumnIndex("id")));

            lista.add(r);
        }

        return lista;
    }
*/
    public void salvar(SQLiteDatabase connection, Comodato motivo) throws SQLException{
        statement = connection.compileStatement(
                "insert into comodato(produto, saldo, cliente) " +
                        "values(?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, motivo.getCodProd());
        statement.bindDouble(2, motivo.getSaldo());
        statement.bindDouble(3, motivo.getCodCli());
        statement.executeInsert();
    }
}