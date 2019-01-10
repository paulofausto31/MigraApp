/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisitStatus;

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
public class SeqVisitStatusDirectAccess {

    SQLiteStatement statement;
    Cursor cs;

    public ArrayList<SeqVisitStatus> getListSeqVisitStat(SQLiteDatabase conn) throws SQLException{
        ArrayList<SeqVisitStatus> list = new ArrayList<SeqVisitStatus>();

        cs = conn.rawQuery(
                "select * from seqVisitStatus",
                null
        );

        while(cs.moveToNext()){
            SeqVisitStatus seqStat = new SeqVisitStatus();
            seqStat.setSeq_id(cs.getString(cs.getColumnIndex("seq_id")));
            seqStat.setStatus(cs.getString(cs.getColumnIndex("status")));
            seqStat.setCodPed(cs.getString(cs.getColumnIndex("codPed")));
            seqStat.setIdNegativa(cs.getInt(cs.getColumnIndex("idNegativa")));

            list.add(seqStat);
        }

        return list;
    }

    /**
     * pesquisar id
     * @param connection
     * @param obj
     * @return
     * @throws SQLException
     */
    public ArrayList<SeqVisitStatus> pesquisar(SQLiteDatabase connection, SeqVisitStatus obj) throws SQLException{
        ArrayList<SeqVisitStatus> arrLresult = new ArrayList<SeqVisitStatus>();

        cs = connection.rawQuery(
                "select * "
                 +"from seqVisitStatus where seq_id = ? ",
                new String[]{String.valueOf(obj.getSeq_id())}
        );

        while(cs.moveToNext()){
            SeqVisitStatus fo = new SeqVisitStatus();
            fo.setSeq_id(cs.getString(cs.getColumnIndex("seq_id")));
            fo.setStatus(cs.getString(cs.getColumnIndex("status")));
            fo.setCodPed(cs.getString(cs.getColumnIndex("codPed")));
            fo.setIdNegativa(cs.getInt(cs.getColumnIndex("idNegativa")));

            arrLresult.add(fo);
        }

        return arrLresult;
    }
/*
    public ArrayList<SeqVisitStatus> getForSalesMan(SQLiteDatabase connection, double codSalesMan)throws SQLException{
        ArrayList<SeqVisitStatus> lista = new ArrayList<>();

        cs = connection.rawQuery(
                "select status, s.id as sId, s.rota as sRota, s.cliente as sCliente, s.seqVisit as sSeqVisit, " +
                        " r.id as rId, r.codigo as rCodigo, r.descricao as rDescricao, r.vendedor as rVendedor " +
                " from seqVisit as s inner join rota as r on s.rota = r.codigo " +
                        " where rVendedor = ?", new String[]{String.valueOf(codSalesMan)});
        while(cs.moveToNext()){
            SeqVisitStatus seqVisit = new SeqVisitStatus();
            seqVisit.setSeqVisit(cs.getLong(cs.getColumnIndex("sSeqVisit")));
            seqVisit.setCodCliente(cs.getDouble(cs.getColumnIndex("sCliente")));
            seqVisit.setSeq_id(cs.getInt(cs.getColumnIndex("sId")));
            seqVisit.setCodRota(cs.getLong(cs.getColumnIndex("sRota")));
            seqVisit.getRota().setDescricao(cs.getString(cs.getColumnIndex("rDescricao")));
            seqVisit.getRota().setVendedor(cs.getDouble(cs.getColumnIndex("rVendedor")));
            seqVisit.getRota().setId(cs.getInt(cs.getColumnIndex("rId")));
            seqVisit.setStatus(cs.getString(cs.getColumnIndex("status")));

            lista.add(seqVisit);
        }

        return lista;
    }
*/
    public void salvar(SQLiteDatabase connection, SeqVisitStatus seqStat) throws SQLException{
        statement = connection.compileStatement(
                "insert into seqVisitStatus(seq_id, status, codPed, idNegativa) " +
                        "values(?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1, seqStat.getSeq_id());
        statement.bindString(2, seqStat.getStatus());
        statement.bindString(3, seqStat.getCodPed());
        statement.bindLong(4, seqStat.getIdNegativa());
        statement.executeInsert();
    }
/*
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

    public ArrayList<SeqVisitStatus> getWithClients(SQLiteDatabase connection, long codRota, Cliente clien) throws SQLException{
        ArrayList<Cliente> listaCli = new ArrayList<Cliente>();
        ArrayList<SeqVisitStatus> listSeqVist = new ArrayList<>();

        String logicRot = "=";
        if(codRota == 0){
            logicRot = ">";
        }

        cs = connection.rawQuery(
                "select status, seqVisit.id as seqId, seqVisit.rota as seqRot, seqVisit.cliente as seqCli, seqVisit.seqVisit as seq, " +
                        "cliente.id as idCli, cliente.codigo as codCli, cliente.nome as nomeCli, cliente.prazoPagamento as cliPrazo, " +
                        " cliente.razaoSocial as rzSociCli, cliente.endereco as endereCli, cliente.limitCred as cliLimiCred, rota.descricao as descRot " +
                        " from seqVisit " +
                        " inner join rota on seqVisit.rota = rota.codigo " +
                        " inner join cliente on seqVisit.cliente = cliente.codigo " +
                        " where rota.vendedor = ? and seqVisit.rota "+logicRot+" ? and nomeCli like ? and rzSociCli like ? " +
                        " order by rzSociCli", new String[]{CurrentInfo.codVendedor, String.valueOf(codRota),
                        "%"+clien.getFantasia()+"%", "%"+clien.getRzSocial()+"%"}
        );

        while(cs.moveToNext()){
            Cliente cli = new Cliente();
            SeqVisitStatus seq = new SeqVisitStatus();

            seq.setSeq_id(cs.getInt(cs.getColumnIndex("seqId")));
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

    public SeqVisitStatus getSeqVisit(SQLiteDatabase connection, double codCli) throws SQLException{
        SeqVisitStatus seq = new SeqVisitStatus();
        cs = connection.rawQuery("select * from seqVisit where cliente = ?", new String[]{String.valueOf(codCli)});
        while(cs.moveToNext()){
            seq.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            seq.setSeqVisit(cs.getLong(cs.getColumnIndex("seqVisit")));
            seq.setCodCliente(cs.getDouble(cs.getColumnIndex("cliente")));
            seq.setSeq_id(cs.getInt(cs.getColumnIndex("id")));
            seq.setStatus(cs.getString(cs.getColumnIndex("status")));
        }
        return seq;
    }
*/
    public long count(SQLiteDatabase connection, long codRota) throws SQLException{
        long count = 0;
        cs = connection.rawQuery("select count(*) from seqVisitStatus where rota = ?", new String[]{String.valueOf(codRota)});
        while(cs.moveToNext()) {
            count = cs.getLong(cs.getColumnIndex("count(*)"));
        }

        return count;
    }

    public void update(SQLiteDatabase connection, SeqVisitStatus seq) throws SQLException{
        connection.update("seqVisitStatus", seq.getValues(), "codPed = ?", new String[]{String.valueOf(seq.getCodPed())});
    }

    public void deletar(SQLiteDatabase connection, String seq_id) throws SQLException{
        connection.delete("seqVisitStatus", "seq_id = ?", new String[]{seq_id});
    }
}