/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;

/**
 *
 * @author ydxpaj
 */
public class SeqVisitDirectAccess {

    SQLiteStatement statement;
    Cursor cs;

    /**
     * pesquisar id
     * @param connection
     * @param obj
     * @return
     * @throws SQLException
     */
    public ArrayList<SeqVisit> pesquisar(SQLiteDatabase connection, SeqVisit obj) throws SQLException{
        ArrayList<SeqVisit> arrLresult = new ArrayList<SeqVisit>();

        String logicId = " = ";
        String logicSeq = " = ";
        String logicCli = " = ";

        if(obj.getId() == 0){
            logicId = " > ";
        }

        cs = connection.rawQuery(
                "select * "
                 +"from seqVisit where id "+logicId+" ? ",
                new String[]{String.valueOf(obj.getId())}
        );

        while(cs.moveToNext()){
            SeqVisit fo = new SeqVisit();

            fo.setId(cs.getInt(cs.getColumnIndex("id")));
            fo.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            fo.setCodCliente(cs.getDouble(cs.getColumnIndex("cliente")));
            fo.setSeqVisit(cs.getLong(cs.getColumnIndex("seqVisit")));
            fo.setStatus(cs.getString(cs.getColumnIndex("status")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }

    public ArrayList<SeqVisit> getForSalesMan(SQLiteDatabase connection, double codSalesMan)throws SQLException{
        ArrayList<SeqVisit> lista = new ArrayList<>();

        cs = connection.rawQuery(
                "select status, s.id as sId, s.rota as sRota, s.cliente as sCliente, s.seqVisit as sSeqVisit, " +
                        " r.id as rId, r.codigo as rCodigo, r.descricao as rDescricao, r.vendedor as rVendedor " +
                " from seqVisit as s inner join rota as r on s.rota = r.codigo " +
                        " where rVendedor = ?", new String[]{String.valueOf(codSalesMan)});
        while(cs.moveToNext()){
            SeqVisit seqVisit = new SeqVisit();
            seqVisit.setSeqVisit(cs.getLong(cs.getColumnIndex("sSeqVisit")));
            seqVisit.setCodCliente(cs.getDouble(cs.getColumnIndex("sCliente")));
            seqVisit.setId(cs.getInt(cs.getColumnIndex("sId")));
            seqVisit.setCodRota(cs.getLong(cs.getColumnIndex("sRota")));
            seqVisit.getRota().setDescricao(cs.getString(cs.getColumnIndex("rDescricao")));
            seqVisit.getRota().setVendedor(cs.getDouble(cs.getColumnIndex("rVendedor")));
            seqVisit.getRota().setId(cs.getInt(cs.getColumnIndex("rId")));
            seqVisit.setStatus(cs.getString(cs.getColumnIndex("status")));

            lista.add(seqVisit);
        }

        return lista;
    }

    public void salvar(SQLiteDatabase connection, SeqVisit motivo) throws SQLException{
        statement = connection.compileStatement(
                "insert into seqVisit(rota, cliente, seqVisit) " +
                        "values(?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindLong(1, motivo.getCodRota());
        statement.bindDouble(2, motivo.getCodCliente());
        statement.bindLong(3, motivo.getSeqVisit());
        statement.executeInsert();
    }

    public ArrayList<Cliente> getClientes(SQLiteDatabase connection, long codRota) throws SQLException{
        ArrayList<Cliente> listaCli = new ArrayList<Cliente>();
        cs = connection.rawQuery(
            "select cliente.id as idCli, cliente.codigo as codCli, cliente.nome as nomeCli, " +
                " cliente.razaoSocial as rzSociCli, cliente.endereco as endereCli " +
            " from seqVisit " +
                " inner join rota on seqVisit.rota = rota.codigo " +
                " inner join cliente on seqVisit.cliente = cliente.codigo " +
                " where rota.vendedor = ? and seqVisit.rota = ? order by seqVisit.seqVisit", new String[]{CurrentInfo.codVendedor, String.valueOf(codRota)}
        );

        while(cs.moveToNext()){
            Cliente cli = new Cliente();
            cli.setId(cs.getLong(cs.getColumnIndex("idCli")));
            cli.setCodigo(cs.getDouble(cs.getColumnIndex("codCli")));
            cli.setFantasia(cs.getString(cs.getColumnIndex("nomeCli")));
            cli.setRzSocial(cs.getString(cs.getColumnIndex("rzSociCli")));
            cli.setEndereco(cs.getString(cs.getColumnIndex("endereCli")));
            listaCli.add(cli);
        }

        return listaCli;
    }

    public ArrayList<SeqVisit> getWithClients(SQLiteDatabase connection, long codRota, Cliente clien) throws SQLException{
        ArrayList<Cliente> listaCli = new ArrayList<Cliente>();
        ArrayList<SeqVisit> listSeqVist = new ArrayList<>();

        String logicRot = "=";
        if(codRota == 0){
            logicRot = ">";
        }

        cs = connection.rawQuery(
                "select status, seqVisit.id as seqId, seqVisit.rota as seqRot, seqVisit.cliente as seqCli, seqVisit.seqVisit as seq, " +
                        " cliente.id as idCli, cliente.codigo as codCli, cliente.nome as nomeCli, cliente.prazoPagamento as cliPrazo, " +
                        " cliente.razaoSocial as rzSociCli, cliente.endereco as endereCli, cliente.limitCred as cliLimiCred, rota.descricao as descRot " +
                        " from seqVisit " +
                        " inner join rota on seqVisit.rota = rota.codigo " +
                        " inner join cliente on seqVisit.cliente = cliente.codigo " +
                        " where rota.vendedor = ? and seqVisit.rota "+logicRot+" ? and (nomeCli like ? or rzSociCli like ?) " +
                        " order by rzSociCli", new String[]{CurrentInfo.codVendedor, String.valueOf(codRota),
                        "%"+clien.getFantasia()+"%", "%"+clien.getRzSocial()+"%"}
        );

        while(cs.moveToNext()){
            Cliente cli = new Cliente();
            SeqVisit seq = new SeqVisit();

            seq.setId(cs.getInt(cs.getColumnIndex("seqId")));
            seq.setCodRota(cs.getLong(cs.getColumnIndex("seqRot")));
            seq.setCodCliente(cs.getDouble(cs.getColumnIndex("seqCli")));
            seq.setSeqVisit(cs.getLong(cs.getColumnIndex("seq")));
            seq.getCliente().setId(cs.getLong(cs.getColumnIndex("idCli")));
            seq.getCliente().setCodigo(cs.getDouble(cs.getColumnIndex("codCli")));
            seq.getCliente().setFantasia(cs.getString(cs.getColumnIndex("nomeCli")));
            seq.getCliente().setRzSocial(cs.getString(cs.getColumnIndex("rzSociCli")));
            seq.getCliente().setEndereco(cs.getString(cs.getColumnIndex("endereCli")));
            seq.getCliente().setPrazoPagamento(cs.getLong(cs.getColumnIndex("cliPrazo")));
            seq.getCliente().setLimitCred(cs.getDouble(cs.getColumnIndex("cliLimiCred")));
            seq.getRota().setDescricao(cs.getString(cs.getColumnIndex("descRot")));
            seq.setStatus(cs.getString(cs.getColumnIndex("status")));

            listSeqVist.add(seq);

            Log.i("LCRED", seq.getCliente().getLimitCred()+"--"+seq.getCliente().getCodigo());
        }

        return listSeqVist;
    }

    public SeqVisit getSeqVisit(SQLiteDatabase connection, double codCli) throws SQLException{
        SeqVisit seq = new SeqVisit();
        cs = connection.rawQuery("select * from seqVisit where cliente = ?", new String[]{String.valueOf(codCli)});
        while(cs.moveToNext()){
            seq.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            seq.setSeqVisit(cs.getLong(cs.getColumnIndex("seqVisit")));
            seq.setCodCliente(cs.getDouble(cs.getColumnIndex("cliente")));
            seq.setId(cs.getInt(cs.getColumnIndex("id")));
            seq.setStatus(cs.getString(cs.getColumnIndex("status")));
        }
        return seq;
    }

    public long count(SQLiteDatabase connection, long codRota) throws SQLException{
        long count = 0;
        cs = connection.rawQuery("select count(*) from seqVisit where rota = ?", new String[]{String.valueOf(codRota)});
        while(cs.moveToNext()) {
            count = cs.getLong(cs.getColumnIndex("count(*)"));
        }

        return count;
    }

    public void update(SQLiteDatabase connection, SeqVisit seq) throws SQLException{
        connection.update("seqVisit", seq.getValues(), "id=?", new String[]{String.valueOf(seq.getId())});
    }
}