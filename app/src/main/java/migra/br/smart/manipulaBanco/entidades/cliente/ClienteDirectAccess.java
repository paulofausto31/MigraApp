/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.cliente;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ClienteDirectAccess {

    SQLiteDatabase connection;
    SQLiteStatement statement;
    Cursor cs;

    public ArrayList<Cliente> pesquisar(SQLiteDatabase connection, Cliente obj) throws SQLException {
        ArrayList<Cliente> arrLresult = new ArrayList<Cliente>();

        String logiCod = " > ? ";//buscar todos os clientes

        if(obj.getCodigo() > 0){//buscar cliente especifico
            logiCod = " = ? ";
        }

        cs = connection.rawQuery(
                "select id, bairro , cidade, codigo, endereco, "+
                    " limitCred, nome ,razaoSocial, prazoPagamento "+
                "from cliente where codigo "+logiCod, new String[]{String.valueOf(obj.getCodigo())}
        );

        while (cs.moveToNext()) {
            Cliente cliente = new Cliente();

            cliente.setId(cs.getLong(cs.getColumnIndex("id")));
            cliente.setBairro(cs.getString(cs.getColumnIndex("bairro")));
            cliente.setEndereco(cs.getString(cs.getColumnIndex("endereco")));
            cliente.setFantasia(cs.getString(cs.getColumnIndex("nome")));
            cliente.setRzSocial(cs.getString(cs.getColumnIndex("razaoSocial")));
            //cliente.setTelefone(cs.getString(cs.getColumnIndex("telefone")));
            //cliente.setTotalAtraso(cs.getDouble(cs.getColumnIndex("totalAtraso")));
            cliente.setCidade(cs.getString(cs.getColumnIndex("cidade")));
            cliente.setCodigo(cs.getDouble(cs.getColumnIndex("codigo")));
            //cliente.setFormPagmento(cs.getString(cs.getColumnIndex("formPagmento")));
            cliente.setLimitCred(cs.getDouble(cs.getColumnIndex("limitCred")));
            cliente.setPrazoPagamento(cs.getLong(cs.getColumnIndex("prazoPagamento")));
            //cliente.setTotalDebito(cs.getDouble(cs.getColumnIndex("totalDebito")));
            //cliente.setCondicPgment(cs.getString(cs.getColumnIndex("condicPgment")));
            //cliente.setSeqVisit(cs.getString(cs.getColumnIndex("seqVisit")));
            //cliente.setPedeHoraVisit(cs.getString(cs.getColumnIndex("pedeHoraVisit")));
            //cliente.setUltimaCompra(cs.getString(cs.getColumnIndex("ultimaCompra")));//date
            //cliente.setCodCli(cs.getString(cs.getColumnIndex("cnpj")));
            arrLresult.add(cliente);
        }
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Cliente sgeClie) throws SQLException{

        statement = connection.compileStatement(
                "insert into cliente(bairro, codigo, endereco, nome, razaoSocial, " +
                        "cidade, formPagmento, limitCred, prazoPagamento" +
                        ") " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, sgeClie.getBairro());
        statement.bindDouble(2, sgeClie.getCodigo());
        statement.bindString(3, sgeClie.getEndereco());
        statement.bindString(4, sgeClie.getFantasia());
        statement.bindString(5, sgeClie.getRzSocial());
        //statement.bindString(6, sgeClie.getTelefone());
        //statement.bindDouble(7, sgeClie.getTotalAtraso());
        statement.bindString(6, sgeClie.getCidade());
        statement.bindString(7, sgeClie.getFormPagmento());
        statement.bindDouble(8, sgeClie.getLimitCred());
        statement.bindLong(9, sgeClie.getPrazoPagamento());
        //statement.bindString(10, sgeClie.getStatus());

        //statement.bindDouble(11, sgeClie.getTotalDebito());
        //statement.bindString(12, sgeClie.getCondicPgment());
        //statement.bindString(13, sgeClie.getSeqVisit());
        //statement.bindString(14, sgeClie.getPedeHoraVisit());
        //statement.bindString(15, sgeClie.getUltimaCompra());//date
        //statement.bindString(16, sgeClie.getCodCli());
        //statement.bindString();

        statement.executeInsert();
    }

    public void update(SQLiteDatabase connection, Cliente c) throws SQLException{
        //connection.update("cliente", c.getValues(), "id=", new String[]{String.valueOf(c.getSeq_id())});
    }
}