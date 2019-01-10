/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configuracao;

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
public class ConFiguracaoDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Configuracao> pesquisar(SQLiteDatabase connection, Configuracao obj) throws SQLException{
        ArrayList<Configuracao> arrLresult = new ArrayList<Configuracao>();
        cs = connection.rawQuery(
                "select * "
                 +"from configuracao", null);

        while(cs.moveToNext()){
            Configuracao con = new Configuracao();
            con.setId(cs.getLong(cs.getColumnIndex("id")));
            con.setDescontMax(cs.getDouble(cs.getColumnIndex("descont_max")));
            /*con.setKey(cs.getString(cs.getColumnIndex("da_descont")));
            con.setControlaEstoque(cs.getString(cs.getColumnIndex("controla_estoq_nao")));*/
            con.setMaxParcelas(cs.getLong(cs.getColumnIndex("quant_max_parc")));
            //con.setNumeroUsuario(cs.getLong(cs.getColumnIndex("num_do_user")));
            con.setDataCarga(cs.getString(cs.getColumnIndex("data_carga")));
            con.setHoraCarga(cs.getString(cs.getColumnIndex("hora_carga")));
            con.setMensagem(cs.getString(cs.getColumnIndex("mensagem")));
            con.setDataExpira(cs.getLong(cs.getColumnIndex("data_expira")));
            con.setUltimaData(cs.getLong(cs.getColumnIndex("ultima_data")));
            con.setKey(cs.getString(cs.getColumnIndex("key")));
            con.setVendePorDiaSemana(cs.getString(cs.getColumnIndex("venderDiaSemana")));
            con.setPrazoMaxGeral(cs.getLong(cs.getColumnIndex("prazoMaxGeral")));
            con.setFiltraEstoque(cs.getInt((cs.getColumnIndex("filtrEstoq"))));

            arrLresult.add(con);
        }
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Configuracao config) throws SQLException{

        statement = connection.compileStatement(
                "insert into configuracao(descont_max, " +
                        " quant_max_parc, data_carga, hora_carga, mensagem, key, venderDiaSemana, " +
                        " prazoMaxGeral, filtrEstoq) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindDouble(1, config.getDescontMax());
/*        statement.bindLong(3, config.getNumeroUsuario());
        statement.bindString(4, config.getControlaEstoque());*/
        statement.bindLong(2, config.getMaxParcelas());
        statement.bindString(3, config.getData());
        statement.bindString(4, config.getHora());
        statement.bindString(5, config.getMensagem());
        statement.bindString(6, config.getKey());
        statement.bindString(7, config.getVendePorDiaSemana());
        statement.bindLong(8, config.getPrazoMaxGeral());
        statement.bindLong(9, config.getFiltraEstoque());;

        statement.execute();
    }

    public void atualizar(SQLiteDatabase connection, Configuracao conLocal) throws SQLException{
        connection.update("configuracao", conLocal.getValues(), "id=?", new String[]{String.valueOf(conLocal.getId())});
    }

    public void deletar(SQLiteDatabase connection, long id) throws SQLException{

        statement = connection.compileStatement(
                "deletar from configuracao where id = ?"
        );
        statement.bindLong(1, id);
        statement.execute();
    }
}