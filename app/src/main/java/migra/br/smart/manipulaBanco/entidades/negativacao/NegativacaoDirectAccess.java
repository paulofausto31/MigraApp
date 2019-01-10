/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.negativacao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.utils.Utils;

/**
 *
 * @author ydxpaj
 */
public class NegativacaoDirectAccess {

    SQLiteStatement statement;
    Cursor cs;
    
    public ArrayList<Negativacao> pesquisar(SQLiteDatabase connection, Negativacao obj) throws SQLException{
        ArrayList<Negativacao> arrLresult = new ArrayList<Negativacao>();

        cs = connection.rawQuery(
                "select * "
                 +"from negativacao", null);

        while(cs.moveToNext()){
            Negativacao neg = new Negativacao();

            neg.setId(cs.getInt(cs.getColumnIndex("id")));
            neg.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            neg.setCodCli(cs.getDouble(cs.getColumnIndex("cliente")));
            neg.setSeqVisitId(cs.getInt(cs.getColumnIndex("seqVist_id")));
            neg.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            neg.setHora(cs.getString(cs.getColumnIndex("hora")));
            neg.setLatitude(cs.getDouble(cs.getColumnIndex("latitude")));
            neg.setLongitude(cs.getDouble(cs.getColumnIndex("longitude")));
            neg.setCodJustf(cs.getString(cs.getColumnIndex("justificativa")));
            neg.setIdEmpresa(cs.getInt(cs.getColumnIndex("idEmpresa")));

            arrLresult.add(neg);
        }

        return arrLresult;
    }

    public Negativacao getForId(SQLiteDatabase connection, int id) throws SQLException{
        Negativacao neg = new Negativacao();

        cs = connection.rawQuery(
                "select * "
                        +"from negativacao where id = ? ", new String[]{String.valueOf(id)});

        while(cs.moveToNext()){
            neg.setId(cs.getInt(cs.getColumnIndex("id")));
            neg.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            neg.setCodCli(cs.getDouble(cs.getColumnIndex("cliente")));
            neg.setSeqVisitId(cs.getInt(cs.getColumnIndex("seqVist_id")));
            neg.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            neg.setHora(cs.getString(cs.getColumnIndex("hora")));
            neg.setLatitude(cs.getDouble(cs.getColumnIndex("latitude")));
            neg.setLongitude(cs.getDouble(cs.getColumnIndex("longitude")));
            neg.setCodJustf(cs.getString(cs.getColumnIndex("justificativa")));
            neg.setStatus(cs.getString(cs.getColumnIndex("status")));
            neg.setIdEmpresa(cs.getInt(cs.getColumnIndex("idEmpresa")));
        }

        return neg;
    }

    public ArrayList<Negativacao> filtrar(SQLiteDatabase connection, Negativacao obj) throws SQLException {
        ArrayList<Negativacao> arrLresult = new ArrayList<Negativacao>();

        String logicCli = " = ";
        String logicRot = " = ";

        if (obj.getCodCli() == 0) {
            logicCli = " > ";
        }
        if (obj.getCodRota() == 0) {
            logicRot = " > ";
        }

        cs = connection.rawQuery(
                "select * "
                        + "from negativacao where status like ? and cliente " + logicCli + " ? " +
                        " and rota " + logicRot + " ? " + Utils.getExtraQuery(),
                new String[]{obj.getStatus() + "%", String.valueOf(obj.getCodCli()), String.valueOf(obj.getCodRota())});

        while (cs.moveToNext()) {
            Negativacao neg = new Negativacao();

            neg.setId(cs.getInt(cs.getColumnIndex("id")));
            neg.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            neg.setCodCli(cs.getDouble(cs.getColumnIndex("cliente")));
            neg.setSeqVisitId(cs.getInt(cs.getColumnIndex("seqVist_id")));
            neg.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            neg.setHora(cs.getString(cs.getColumnIndex("hora")));
            neg.setLatitude(cs.getDouble(cs.getColumnIndex("latitude")));
            neg.setLongitude(cs.getDouble(cs.getColumnIndex("longitude")));
            neg.setCodJustf(cs.getString(cs.getColumnIndex("justificativa")));
            neg.setIdEmpresa(cs.getInt(cs.getColumnIndex("idEmpresa")));

            arrLresult.add(neg);
        }

        Utils.setExtraQuery("");//limpa extra query

        return arrLresult;
    }

    /*
    public ArrayList<Negativacao> filtrar(SQLiteDatabase connection, Negativacao obj) throws SQLException{
        ArrayList<Negativacao> arrLresult = new ArrayList<Negativacao>();

        String logicCli = " = ";
        String logicRot = " = ";

        if(obj.getCodCli() == 0){
            logicCli = " > ";
        }
        if(obj.getCodRota() == 0){
            logicRot = " > ";
        }

        cs = connection.rawQuery(
                "select * "
                        +"from negativacao where status like ? and cliente "+logicCli+" ? " +
                        " and rota "+logicRot+" ? "+Utils.getExtraQuery(),
                new String[]{obj.getStatus()+"%", String.valueOf(obj.getCodCli()), String.valueOf(obj.getCodRota())});

        while(cs.moveToNext()){
            Negativacao neg = new Negativacao();

            neg.setSeq_id(cs.getInt(cs.getColumnIndex("id")));
            neg.setCodRota(cs.getLong(cs.getColumnIndex("rota")));
            neg.setCodCli(cs.getDouble(cs.getColumnIndex("cliente")));
            neg.setSeqVisitId(cs.getInt(cs.getColumnIndex("seqVist_id")));
            neg.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            neg.setHora(cs.getString(cs.getColumnIndex("hora")));
            neg.setLatitude(cs.getDouble(cs.getColumnIndex("latitude")));
            neg.setLongitude(cs.getDouble(cs.getColumnIndex("longitude")));
            neg.setCodJustf(cs.getString(cs.getColumnIndex("justificativa")));

            arrLresult.add(neg);
        }

        Utils.setExtraQuery("");//limpa extra query

        return arrLresult;
    }
    */

    public void salvar(SQLiteDatabase connection, Negativacao neg) throws SQLException{
        statement = connection.compileStatement(
                "insert into negativacao(rota, cliente, seqVist_id, data, hora, latitude, longitude, justificativa, idEmpresa) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindLong(1, neg.getCodRota());
        statement.bindDouble(2, neg.getCodCli());
        statement.bindLong(3, neg.getSeqVisitId());
        statement.bindLong(4, neg.getDataInicio());
        statement.bindString(5, neg.getHora());
        statement.bindDouble(6, neg.getLatitude());
        statement.bindDouble(7, neg.getLongitude());
        statement.bindString(8, neg.getCodJustf());
        statement.bindLong(9, neg.getIdEmpresa());

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
            cli.setSeq_id(cs.getLong(cs.getColumnIndex("idCli")));
            cli.setHora(cs.getDouble(cs.getColumnIndex("codCli")));
            cli.setFantasia(cs.getString(cs.getColumnIndex("nomeCli")));
            cli.setRzSocial(cs.getString(cs.getColumnIndex("rzSociCli")));
            cli.setEndereco(cs.getString(cs.getColumnIndex("endereCli")));
            listaCli.add(cli);
        }

        return listaCli;
    }
*/

    /**
     * @param connection
     * @return o total de tuplas
     * @throws SQLException
     */
    public long count(SQLiteDatabase connection) throws SQLException {
        long total = 0;
        cs = connection.rawQuery("select count(*) as totReg from negativacao where status", null);
        while(cs.moveToNext()){
            total = cs.getLong(cs.getColumnIndex("totReg"));
        }

        return total;
    }

    public ArrayList<Negativacao> getWithClients(SQLiteDatabase connection, Negativacao negFilter) throws SQLException{
        ArrayList<Negativacao> listNega = new ArrayList<>();

        String logicRot = " > ";
        String logicVendedor = " = ";
        String logicCodCli = " = ";
        String logicSeqVist = " = ";
        String logicIdEmpre = " = ";

        if(negFilter.getRota().getVendedor() == 0){
            logicVendedor = " > ";
        }

        if(negFilter.getCodCli() == 0){
            logicCodCli = " > ";
        }

        if(negFilter.getSeqVisitId() == 0){
            logicSeqVist = " > ";
        }

        if(negFilter.getCodRota() > 0){
            logicRot = " = ";
        }
        if(negFilter.getIdEmpresa() == 0){
            logicIdEmpre = " > ";
        }

        cs = connection.rawQuery(
            "select negativacao.idEmpresa as nIdEmpresa, justificativa.descricao as justDesci, negativacao.id as idNeg, cliente.id as idCli, cliente.codigo as codCli, cliente.nome as nomeCli, " +
                " cliente.razaoSocial as rzSociCli, cliente.endereco as endereCli, data, seqVist_id, " +
                " rota.codigo as rotCod, rota.descricao as rotDesc,  vendedor.codigo as vendCod, " +
                " negativacao.status as negStat, justificativa, data, hora, latitude, longitude, " +
                " empresas.cnpj as empCnpj " +
                " from negativacao " +
                " inner join rota on negativacao.rota = rota.codigo "+
                " inner join cliente on negativacao.cliente = cliente.codigo "+
                " inner join vendedor on rota.vendedor = vendedor.codigo " +
                " inner join justificativa on negativacao.justificativa = justificativa.codigo " +
                " inner join empresas on negativacao.idEmpresa = empresas.id "+
                " where rota.vendedor "+logicVendedor+" ? and rota.codigo "+logicRot+" ? and (nomeCli like ? or rzSociCli like ?) " +
                    " and negStat like ? and codCli "+logicCodCli+" ? and seqVist_id "+logicSeqVist+" ? " +
                    " and nIdEmpresa "+logicIdEmpre+" ? "+Utils.getExtraQuery()+" order by seqVist_id",
                //funciona new String[]{CurrentInfo.codVendedor, String.valueOf(negFilter.getCodRota()),
                new String[]{
                        String.valueOf(negFilter.getRota().getVendedor()), String.valueOf(negFilter.getCodRota()),
                        "%"+negFilter.getCliente().getFantasia()+"%", "%"+negFilter.getCliente().getRzSocial()+"%", "%"+negFilter.getStatus()+"%",
                        String.valueOf(negFilter.getCodCli()), String.valueOf(negFilter.getSeqVisitId()),
                        String.valueOf(negFilter.getIdEmpresa())
                }
        );

        while(cs.moveToNext()){
            Negativacao ne = new Negativacao();
            ne.setId(cs.getInt(cs.getColumnIndex("idNeg")));
            ne.getCliente().setId(cs.getLong(cs.getColumnIndex("idCli")));
            ne.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            ne.getCliente().setFantasia(cs.getString(cs.getColumnIndex("nomeCli")));
            ne.getCliente().setRzSocial(cs.getString(cs.getColumnIndex("rzSociCli")));
            ne.getCliente().setEndereco(cs.getString(cs.getColumnIndex("endereCli")));
            ne.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            ne.setSeqVisitId(cs.getInt(cs.getColumnIndex("seqVist_id")));
            ne.setCodRota(cs.getLong(cs.getColumnIndex("rotCod")));
            ne.getRota().setDescricao(cs.getString(cs.getColumnIndex("rotDesc")));
            ne.getVendedor().setCodigo(cs.getString(cs.getColumnIndex("vendCod")));
            ne.setStatus(cs.getString(cs.getColumnIndex("negStat")));

            //ne.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            ne.setHora(cs.getString(cs.getColumnIndex("hora")));
            ne.setLatitude(cs.getDouble(cs.getColumnIndex("latitude")));
            ne.setLongitude(cs.getDouble(cs.getColumnIndex("longitude")));

            ne.setCodJustf(cs.getString(cs.getColumnIndex("justificativa")));
            ne.getJustificativa().setDescricao(cs.getString(cs.getColumnIndex("justDesci")));
            ne.setIdEmpresa(cs.getInt(cs.getColumnIndex("nIdEmpresa")));

            ne.getEmpresa().setCnpj(cs.getString(cs.getColumnIndex("empCnpj")));

            listNega.add(ne);
        }

        Utils.setExtraQuery("");

        return listNega;
    }

    public ArrayList<Negativacao> getWithCodClients(SQLiteDatabase connection, Negativacao negFilter) throws SQLException{
        ArrayList<Negativacao> listNega = new ArrayList<>();

        String logicRot = ">";
        String logicVendedor = " = ";
        String logicCodCli = " = ";
        String logicSeqVist = " = ";
        String logicIdEmpre = " = ";

        if(negFilter.getRota().getVendedor() == 0){
            logicVendedor = " > ";
        }
        if(negFilter.getCodCli() == 0){
            logicCodCli = " > ";
        }
        if(negFilter.getSeqVisitId() == 0){
            logicSeqVist = " > ";
        }
        if(negFilter.getCodRota() > 0){
            logicRot = " = ";
        }
        if(negFilter.getIdEmpresa() == 0){
            logicIdEmpre = " > ";
        }

        cs = connection.rawQuery(
                "select negativacao.idEmpresa as nIdEmpresa, justificativa.descricao as justDesci, negativacao.id as idNeg, cliente.id as idCli, cliente.codigo as codCli, cliente.nome as nomeCli, " +
                        " cliente.razaoSocial as rzSociCli, cliente.endereco as endereCli, data, seqVist_id, " +
                        " rota.codigo as rotCod, rota.descricao as rotDesc,  vendedor.codigo as vendCod, " +
                        " negativacao.status as negStat, justificativa, data, hora, latitude, longitude " +
                        " from negativacao " +
                        " inner join rota on negativacao.rota = rota.codigo "+
                        " inner join cliente on negativacao.cliente = cliente.codigo "+
                        " inner join vendedor on rota.vendedor = vendedor.codigo " +
                        " inner join justificativa on negativacao.justificativa = justificativa.codigo "+
                        " where rota.vendedor "+logicVendedor+" ? and rota.codigo "+logicRot+" ? and (nomeCli like ? or rzSociCli like ?) " +
                        " and negStat like ? and codCli "+logicCodCli+" ? and seqVist_id "+logicSeqVist+" ? " +
                        " and "+logicIdEmpre+" ? " +Utils.getExtraQuery()+" order by seqVist_id",
                //funciona new String[]{CurrentInfo.codVendedor, String.valueOf(negFilter.getCodRota()),
                new String[]{
                        String.valueOf(negFilter.getRota().getVendedor()), String.valueOf(negFilter.getCodRota()),
                        "%"+negFilter.getCliente().getFantasia()+"%", "%"+negFilter.getCliente().getRzSocial()+"%", "%"+negFilter.getStatus()+"%",
                        String.valueOf(negFilter.getCodCli()), String.valueOf(negFilter.getSeqVisitId()),
                        String.valueOf(negFilter.getIdEmpresa())
                }
        );

        Log.i("NEG",  "select negativacao.id as idNeg, codigo as codCli,  data, seqVist_id, " +
                " rota.codigo as rotCod, rota.descricao as rotDesc,  vendedor.codigo as vendCod, " +
                " negativacao.status as negStat, justificativa, data, hora, latitude, longitude " +
                " from negativacao " +
                " inner join rota on negativacao.rota = rota.codigo "+
                " inner join cliente on negativacao.cliente = cliente.codigo "+
                " inner join vendedor on rota.vendedor = vendedor.codigo "+
                " where rota.vendedor "+logicVendedor+" ? and rota.codigo "+logicRot+" ? and nomeCli like ? " +
                " and negStat like ? and codCli "+logicCodCli+" ? and seqVist_id "+logicSeqVist+" ? "+Utils.getExtraQuery()+" order by seqVist_id");

        while(cs.moveToNext()){
            Negativacao ne = new Negativacao();
            ne.setId(cs.getInt(cs.getColumnIndex("idNeg")));
            //ne.getCliente().setSeq_id(cs.getLong(cs.getColumnIndex("idCli")));
            ne.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            //ne.getCliente().setFantasia(cs.getString(cs.getColumnIndex("nomeCli")));
            //ne.getCliente().setRzSocial(cs.getString(cs.getColumnIndex("rzSociCli")));
            //ne.getCliente().setEndereco(cs.getString(cs.getColumnIndex("endereCli")));
            ne.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            ne.setSeqVisitId(cs.getInt(cs.getColumnIndex("seqVist_id")));
            ne.setCodRota(cs.getLong(cs.getColumnIndex("rotCod")));
            //ne.getRota().setDescricao(cs.getString(cs.getColumnIndex("rotDesc")));
            ne.getVendedor().setCodigo(cs.getString(cs.getColumnIndex("vendCod")));
            ne.setStatus(cs.getString(cs.getColumnIndex("negStat")));

            //ne.setDataInicio(cs.getLong(cs.getColumnIndex("data")));
            ne.setHora(cs.getString(cs.getColumnIndex("hora")));
            ne.setLatitude(cs.getDouble(cs.getColumnIndex("latitude")));
            ne.setLongitude(cs.getDouble(cs.getColumnIndex("longitude")));

            ne.setCodJustf(cs.getString(cs.getColumnIndex("justificativa")));
            ne.getJustificativa().setDescricao(cs.getString(cs.getColumnIndex("justDesci")));

            ne.setIdEmpresa(cs.getInt(cs.getColumnIndex("nIdEmpresa")));

            listNega.add(ne);
        }

        Utils.setExtraQuery("");

        return listNega;
    }

    public void update(SQLiteDatabase connection, Negativacao neg) throws SQLException{
        connection.update("negativacao", neg.getValues(), "id = ?", new String[]{String.valueOf(neg.getId())});
    }

    /**
     * deletar apor id
     * @param con
     * @param id
     * @throws SQLException
     */
    void deletar(SQLiteDatabase con, int id) throws SQLException{
        con.delete("negativacao", "id=?", new String[]{String.valueOf(id)});
    }

    /**
     * usa filtro com data, sequencia de visita, cliente e rota
     * @param con
     * @param neg
     * @throws SQLException
     */
    void deletarForFilter(SQLiteDatabase con, Negativacao neg) throws SQLException{
        con.delete("negativacao", "data >= ? and data <= ? and seqVist_id = ? and cliente = ? and rota = ?",
                new String[]{String.valueOf(neg.getDataInicio()), String.valueOf(neg.getDataFim()),
                        String.valueOf(neg.getSeqVisitId()), String.valueOf(neg.getCodCli()),
                        String.valueOf(neg.getCodRota())}
        );
    }

    int getMaxId(SQLiteDatabase con) throws SQLException{
        int maxId = 0;

        cs = con.rawQuery(
                "select max(id) from negativacao",
                null
        );

        while(cs.moveToNext()){
            maxId = cs.getInt(cs.getColumnIndex("max(id)"));
        }

        return maxId;
    }
}