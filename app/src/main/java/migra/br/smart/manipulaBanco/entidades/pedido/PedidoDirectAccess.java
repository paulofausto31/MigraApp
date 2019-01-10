/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.pedido;

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
public class PedidoDirectAccess {
    
    //Connection connection;
    SQLiteStatement statement;
    Cursor cs;

    public Pedido getForId(SQLiteDatabase connection, long id) throws SQLException{
        Pedido p = new Pedido();
        cs = connection.rawQuery(
                "select pedido.id as pId,codCli, datInici, horaInic, datFim, horaFim, qtParcela, prazo, total, codFormpg, status, del, obs, latitudeInicio, " +
                        " longitudeInicio, latitudeFim, longitudeFim, latitudeTransmit, longitudeTransmit, seqVist_id, rota, empresas.id as idEmpresa, empresas.cnpj as emprCnpj from pedido " +
                        "                         inner join empresas on pedido.idEmpresa = empresas.id " +
                        "                         where pId = ?",
                /*"select *, empresas.id as idEmpresa, empresas.cnpj as emprCnpj from pedido " +
                        " inner join empresas on pedido.idEmpresa = idEmpresa " +
                        " where pedido.id = ? ",*/
                new String[]{String.valueOf(id)}
        );
        /*
          p.setId(cs.getLong(cs.getColumnIndex("id")));
        p.setQtParcela(cs.getDouble(cs.getColumnIndex("qtParcela")));
        p.setDataInicio(cs.getLong(cs.getColumnIndex("datInici")));
        p.setHoraInicio(cs.getString(cs.getColumnIndex("horaInic")));
        p.setDataFim(cs.getLong(cs.getColumnIndex("datFim")));
        p.setHoraFim(cs.getString(cs.getColumnIndex("horaFim")));
        p.setPrazo(cs.getLong(cs.getColumnIndex("prazo")));
        p.setIdFormPg(cs.getString(cs.getColumnIndex("codFormpg")));
        p.setStatus(cs.getString(cs.getColumnIndex("status")));
        p.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
        p.setTotal(cs.getDouble(cs.getColumnIndex("total")));
        p.setRota(cs.getLong(cs.getColumnIndex("rota")));
        p.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
        p.setDel(cs.getString(cs.getColumnIndex("del")));
        p.setLatitudeInicio(cs.getDouble(cs.getColumnIndex("latitudeInicio")));
        p.setLongitudeInicio(cs.getDouble(cs.getColumnIndex("longitudeInicio")));
        p.setObs(cs.getString(cs.getColumnIndex("obs")));
        p.setIdEmpresa(cs.getInt(cs.getColumnIndex("idEmpresa")));
        p.getEmpresa().setCnpj(cs.getString(cs.getColumnIndex("cnpj")));//emprCnpj

         */
        while(cs.moveToNext()){
            //p = fillOnlyPedido(p);
            /*
            p.setId(cs.getLong(cs.getColumnIndex("id")));
            p.setQtParcela(cs.getDouble(cs.getColumnIndex("qtParcela")));
            p.setDataInicio(cs.getLong(cs.getColumnIndex("datInici")));
            p.setHoraInicio(cs.getString(cs.getColumnIndex("horaInic")));
            p.setDataFim(cs.getLong(cs.getColumnIndex("datFim")));
            p.setHoraFim(cs.getString(cs.getColumnIndex("horaFim")));
            p.setPrazo(cs.getLong(cs.getColumnIndex("prazo")));
            p.setIdFormPg(cs.getString(cs.getColumnIndex("codFormpg")));
            p.setStatus(cs.getString(cs.getColumnIndex("status")));
            p.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            p.setTotal(cs.getDouble(cs.getColumnIndex("total")));
            p.setRota(cs.getLong(cs.getColumnIndex("rota")));
            p.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
            p.setDel(cs.getString(cs.getColumnIndex("del")));
            p.setLatitudeInicio(cs.getDouble(cs.getColumnIndex("latitudeInicio")));
            p.setLongitudeInicio(cs.getDouble(cs.getColumnIndex("longitudeInicio")));
            p.setObs(cs.getString(cs.getColumnIndex("obs")));
            */
            p.setId(cs.getLong(cs.getColumnIndex("pId")));
            p.setQtParcela(cs.getDouble(cs.getColumnIndex("qtParcela")));
            p.setDataInicio(cs.getLong(cs.getColumnIndex("datInici")));
            p.setHoraInicio(cs.getString(cs.getColumnIndex("horaInic")));
            p.setDataFim(cs.getLong(cs.getColumnIndex("datFim")));
            p.setHoraFim(cs.getString(cs.getColumnIndex("horaFim")));
            p.setPrazo(cs.getLong(cs.getColumnIndex("prazo")));
            p.setIdFormPg(cs.getString(cs.getColumnIndex("codFormpg")));
            p.setStatus(cs.getString(cs.getColumnIndex("status")));
            p.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            p.setTotal(cs.getDouble(cs.getColumnIndex("total")));
            p.setRota(cs.getLong(cs.getColumnIndex("rota")));
            p.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
            p.setDel(cs.getString(cs.getColumnIndex("del")));
            p.setLatitudeInicio(cs.getDouble(cs.getColumnIndex("latitudeInicio")));
            p.setLongitudeInicio(cs.getDouble(cs.getColumnIndex("longitudeInicio")));
            p.setObs(cs.getString(cs.getColumnIndex("obs")));
            p.setIdEmpresa(cs.getInt(cs.getColumnIndex("idEmpresa")));
            //p.setIdEmpresa(1);
            p.getEmpresa().setCnpj(cs.getString(cs.getColumnIndex("emprCnpj")));//emprCnpj
        }

        return p;
    }

    public ArrayList<Pedido> listForCliAndStstus(SQLiteDatabase connection, Pedido pf) throws SQLException{
        ArrayList<Pedido> listPed = new ArrayList<Pedido>();
        Pedido p = new Pedido();

        cs = connection.rawQuery("select distinct pedido.id as pId,codCli, datInici, horaInic, datFim, horaFim, qtParcela, prazo, total, codFormpg, status, del, obs, latitudeInicio, " +
                        " longitudeInicio, latitudeFim, longitudeFim, latitudeTransmit, longitudeTransmit, seqVist_id, rota, " +
                        " empresas.id as idEmpresa, empresas.cnpj as emprCnpj from pedido " +
                "                         inner join empresas on pedido.idEmpresa = empresas.id  where codCli = ? and status = ?",
                new String[]{String.valueOf(CurrentInfo.codCli), pf.getStatus()});
        while(cs.moveToNext()){
            p = fillOnlyPedido(p);
            listPed.add(p);
        }

        return listPed;
    }

    public String[] listForCodPedAndCli(SQLiteDatabase connection) throws SQLException{
        ArrayList<String> cods = new ArrayList<String>();

        cs = connection.rawQuery("select distinct id from pedido where codCli = ? and status <> 'Transmitido'",
                new String[]{String.valueOf(CurrentInfo.codCli)});
        while(cs.moveToNext()){
            cods.add(cs.getString(cs.getColumnIndex("id")));
        }

        String[] codigos = new String[cods.size()];
        codigos = cods.toArray(codigos);
        return codigos;
    }
    /**
    *@param pedido com dataInici, codCli e status
    **/
    public ArrayList<Pedido> filtrar(SQLiteDatabase connection, Pedido pedido) throws SQLException{
        ArrayList<Pedido> arrLresult = new ArrayList<>();
        String logicRot = "=";
        String logicCli = "=";
        String logicFormPg = " = ";
        String logicData = " = ";
        String logiEmpre = " = ";

        if(pedido.getRota() == 0){//pesquisar todos os pedidos de todas as rotas
            logicRot = ">";
        }
        if(pedido.getCodCli()  == 0){//pesquisar todos os pedidos de todos os clientes clientes
            logicCli = ">";
        }
        if(pedido.getIdFormPg().equals("")){
            logicFormPg = "<>";
        }
        if(pedido.getIdEmpresa() == 0){
            logiEmpre = " > ";
        }

        cs = connection.rawQuery(
                //"select * from pedido inner join cliente on pedido.codCli = cliente.codigo" +
                "select empresas.cnpj as emprCnpj, pedido.idEmpresa as pIdEmpresa, prazo, codCli, datInici, pedido.status as pStatus, datFim, horaFim, codFormpg, " +
                        " horaInic, total, pedido.rota as pRot, " +
                        " seqVist_id, pedido.id as idPed, del, qtParcela, latitudeInicio, longitudeInicio, " +
                        " cliente.nome as cliNome, cliente.razaoSocial as cliRzSoci, obs, " +
                        " seqVisit.seqVisit as sqVisit from pedido " +
                        " inner join cliente on pedido.codCli = cliente.codigo " +
                        " inner join seqVisit on pedido.seqVist_id = seqVisit.id " +
                        " inner join empresas on pedido.idEmpresa = empresas.id "+
                        " where codCli "+logicCli+" ? and (datInici >= ? and datInici <= ?) and pStatus like ? and pRot "+logicRot+" ? " +
                        " and codFormpg "+logicFormPg+" ? and del like ? and (cliRzSoci like ? or cliNome like ?) and pIdEmpresa "+logiEmpre+" ? "+
                        Utils.getExtraQuery(),
                    new String[]{
                            pedido.getCodCli()+"", pedido.getDataInicio()+"", pedido.getDataFim()+"",
                            pedido.getStatus()+"%", pedido.getRota()+"", pedido.getIdFormPg(), pedido.getDel()+"%",
                            "%"+pedido.getCliente().getRzSocial()+"%", "%"+pedido.getCliente().getFantasia()+"%",
                            String.valueOf(pedido.getIdEmpresa())
                    }
                );

        Log.i("diPH", pedido.getCodCli()+"--"+pedido.getDataInicio()+"--"+pedido.getDataFim()+"--"+
                pedido.getStatus()+"%--"+pedido.getRota()+"--"+pedido.getIdFormPg()+"--"+pedido.getDel()+"%--"+
                "%"+pedido.getCliente().getRzSocial()+"%--"+"%"+pedido.getCliente().getFantasia()+"%");
        int i = 0;
        while(cs.moveToNext()){
            Pedido ped = new Pedido();

            arrLresult.add(ped);
            ped.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            ped.setDataInicio(cs.getLong(cs.getColumnIndex("datInici")));
            ped.setStatus(cs.getString(cs.getColumnIndex("pStatus")));
            ped.setDataFim(cs.getLong(cs.getColumnIndex("datFim")));
            ped.setHoraFim(cs.getString(cs.getColumnIndex("horaFim")));
            ped.setIdFormPg(cs.getString(cs.getColumnIndex("codFormpg")));
            ped.setHoraInicio(cs.getString(cs.getColumnIndex("horaInic")));
            ped.setTotal(cs.getDouble(cs.getColumnIndex("total")));
            ped.setRota(cs.getLong(cs.getColumnIndex("pRot")));
            ped.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
            ped.setId(cs.getLong(cs.getColumnIndex("idPed")));
            ped.setDel(cs.getString(cs.getColumnIndex("del")));
            ped.setQtParcela(cs.getDouble(cs.getColumnIndex("qtParcela")));
            ped.setLatitudeInicio(cs.getDouble(cs.getColumnIndex("latitudeInicio")));
            ped.setLongitudeInicio(cs.getDouble(cs.getColumnIndex("longitudeInicio")));

            ped.getSeqVisit().setSeqVisit(cs.getLong(cs.getColumnIndex("sqVisit")));

            ped.getCliente().setRzSocial(cs.getString(cs.getColumnIndex("cliRzSoci")));
            ped.getCliente().setFantasia(cs.getString(cs.getColumnIndex("cliNome")));

            ped.setObs(cs.getString(cs.getColumnIndex("obs")));

            ped.setPrazo(cs.getLong(cs.getColumnIndex("prazo")));
            ped.setIdEmpresa(cs.getInt(cs.getColumnIndex("pIdEmpresa")));
            ped.getEmpresa().setCnpj(cs.getString(cs.getColumnIndex("emprCnpj")));

            Log.i("HCLINOM", cs.getString(cs.getColumnIndex("cliNome")));//razão social
            Log.i("HCLIRZ", cs.getString(cs.getColumnIndex("cliRzSoci")));//nome fantasia
        }
        return arrLresult;
    }

    public ArrayList<Pedido> getOpenOrClose(SQLiteDatabase connection, Pedido pedido) throws SQLException{
        ArrayList<Pedido> arrLresult = new ArrayList<>();
        String logicRot = "=";
        String logicCli = "=";
        String logicFormPg = "=";
        String logicSeqVisi = "=";

        if(pedido.getRota() == 0){//pesquisar todos os pedidos de todas as rotas
            logicRot = ">";
        }
        if(pedido.getCodCli()  == 0){//pesquisar todos os pedidos de todos os clientes clientes
            logicCli = ">";
        }
        if(pedido.getIdFormPg().equals("")){
            logicFormPg = "<>";
        }

        if(pedido.getSeqVist_id() == 0){
            logicSeqVisi = ">";
        }

        cs = connection.rawQuery(
                //"select * from pedido inner join cliente on pedido.codCli = cliente.codigo" +
                "select pedido.idEmpresa as pIdEmpresa, prazo, codCli, dataInicio, pedido.status as pStatus, dataFim, horaFim, idFormpg, " +
                        " horaInicio, total, pedido.rota as pRot, seqVist_id, pedido.id as idPed, del, " +
                        "qtParcela, latitudeInicio, longitudeInicio, " +
                        "obs " +
                        " from pedido " +
                        "where codCli "+logicCli+" ? and seqVist_id "+logicSeqVisi+"? "+ Utils.getExtraQuery(),
                new String[]{
                        pedido.getCodCli()+"", String.valueOf(pedido.getSeqVist_id())
                }
        );
//        cs = connection.rawQuery(
//                //"select * from pedido inner join cliente on pedido.codCli = cliente.codigo" +
//                "select emrpesas.cnpj as emprCnpj pedido.idEmpresa as pIdEmpresa, prazo, codCli, datInici, pedido.status as pStatus, datFim, horaFim, codFormpg, " +
//                        " horaInic, total, pedido.rota as pRot, seqVist_id, pedido.id as idPed, del, " +
//                        "qtParcela, latitudeInicio, longitudeInicio, cliente.nome as cliNome, " +
//                        "cliente.razaoSocial as cliRzSoci, obs, seqVisit.seqVisit as sqVisit " +
//                        " from pedido " +
//                        "   inner join cliente on pedido.codCli = cliente.codigo "+
//                        "   inner join seqVisit on pedido.seqVist_id = seqVisit.id " +
//                        "   inner join empresas on pedido.idEmpresa = empresas.id"+
//                        "where codCli "+logicCli+" ? and datInici >= ? and datInici <= ?  " +
//                        "and (pStatus = 'Aberto' or pStatus = 'Fechado') and pRot "+logicRot+" ? " +
//                        "and codFormpg "+logicFormPg+" ? and del like ? and cliRzSoci like ? " +
//                        " and cliNome like ? and seqVist_id "+logicSeqVisi+"? "+ Utils.getExtraQuery(),
//                new String[]{
//                        pedido.getCodCli()+"", pedido.getDataInicio()+"", pedido.getDataFim()+"",
//                        pedido.getRota()+"", pedido.getIdFormPg(), pedido.getDel()+"%",
//                        "%"+pedido.getCliente().getRzSocial()+"%", "%"+pedido.getCliente().getFantasia()+"%",
//                        String.valueOf(pedido.getSeqVist_id())
//                }
//        );
        int i = 0;
        while(cs.moveToNext()){
            Pedido ped = new Pedido();

            arrLresult.add(ped);
            ped.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            ped.setDataInicio(cs.getLong(cs.getColumnIndex("datInici")));
            ped.setStatus(cs.getString(cs.getColumnIndex("pStatus")));
            ped.setDataFim(cs.getLong(cs.getColumnIndex("datFim")));
            ped.setHoraFim(cs.getString(cs.getColumnIndex("horaFim")));
            ped.setIdFormPg(cs.getString(cs.getColumnIndex("codFormpg")));
            ped.setHoraInicio(cs.getString(cs.getColumnIndex("horaInic")));
            ped.setTotal(cs.getDouble(cs.getColumnIndex("total")));
            ped.setRota(cs.getLong(cs.getColumnIndex("pRot")));
            ped.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
            ped.setId(cs.getLong(cs.getColumnIndex("idPed")));
            ped.setDel(cs.getString(cs.getColumnIndex("del")));
            ped.setQtParcela(cs.getDouble(cs.getColumnIndex("qtParcela")));
            ped.setLatitudeInicio(cs.getDouble(cs.getColumnIndex("latitudeInicio")));
            ped.setLongitudeInicio(cs.getDouble(cs.getColumnIndex("longitudeInicio")));

            ped.getCliente().setRzSocial(cs.getString(cs.getColumnIndex("cliRzSoci")));
            ped.getCliente().setFantasia(cs.getString(cs.getColumnIndex("cliNome")));

            ped.setObs(cs.getString(cs.getColumnIndex("obs")));

            ped.setPrazo(cs.getLong(cs.getColumnIndex("prazo")));
            ped.setIdEmpresa(cs.getInt(cs.getColumnIndex("pIdEmpresa")));//id empresa
            ped.getEmpresa().setCnpj(cs.getString(cs.getColumnIndex("emprCnpj")));

            ped.getSeqVisit().setSeqVisit(cs.getLong(cs.getColumnIndex("sqVisit")));

            Log.i("HCLINOM", cs.getString(cs.getColumnIndex("cliNome")));//razão social
            Log.i("HCLIRZ", cs.getString(cs.getColumnIndex("cliRzSoci")));//nome fantasia
        }
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Pedido p) throws SQLException{

        SQLiteStatement statement = connection.compileStatement(
                "insert into pedido(datInici, horaInic, datFim, horaFim, " +
                        "qtParcela, prazo, codFormpg, id, codCli, rota, seqVist_id, " +
                        "latitudeInicio, longitudeInicio, obs, idEmpresa) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );
        statement.clearBindings();
        statement.bindLong(1,p.getDataInicio());//OK
        statement.bindString(2,p.getHoraInicio());//OK
        statement.bindLong(3,p.getDataFim());//OK
        statement.bindString(4,p.getHoraFim());//OK
        statement.bindDouble(5,p.getQtParcela());//OK
        statement.bindLong(6,p.getPrazo());//OK
        statement.bindString(7,p.getIdFormPg());//OK
        statement.bindLong(8, p.getId());
        statement.bindDouble(9, p.getCodCli());
        statement.bindLong(10, p.getRota());
        statement.bindLong(11, p.getSeqVist_id());
        statement.bindDouble(12, p.getLatitudeInicio());
        statement.bindDouble(13, p.getLongitudeInicio());
        statement.bindString(14, p.getObs());
        statement.bindLong(15, p.getIdEmpresa());

        statement.executeInsert();
    }

    public void update(SQLiteDatabase connection, Pedido p) throws SQLException{
        connection.update("pedido", p.getValues(), "id=?",new String[]{String.valueOf(p.getId())});
    }

    public void delPedidoForId(SQLiteDatabase connection, double idPedido) throws SQLException{
        connection.delete("pedido", "id = ?", new String[]{String.valueOf(idPedido)});
    }

    /**
     * DELETA PELO STATUS da coluna del da tabela pedido
     * @param connection
     * @throws SQLException
     */
    public void delPedidoForStatusDel(SQLiteDatabase connection) throws SQLException{
        connection.delete("pedido", "del = 's'", null);
    }

    public long getMaxId(SQLiteDatabase connection, Pedido p) throws SQLException{
        long maxId = 0;
        cs = connection.rawQuery("select max(id) from pedido where rota = ?", new String[]{String.valueOf(p.getRota())});
        while(cs.moveToNext()){
            maxId = cs.getLong(cs.getColumnIndex("max(id)"));
        }
        return maxId;
    }

    /**
     * OBTEM O ID ULTIMO PEDIDO BASEADO NO VENDEDOR
     * @param connection
     * @param p
     * @return
     * @throws SQLException
     */
    public long getMaxIdForRotAndVend(SQLiteDatabase connection, Pedido p) throws SQLException{
        long maxId = 0;
        cs = connection.rawQuery("select max(id) from pedido where rota = ?", new String[]{String.valueOf(p.getRota())});
        while(cs.moveToNext()){
            maxId = cs.getLong(cs.getColumnIndex("max(id)"));
        }
        return maxId;
    }

    private Pedido fillOnlyPedido(Pedido p){
        p.setId(cs.getLong(cs.getColumnIndex("pId")));
        p.setQtParcela(cs.getDouble(cs.getColumnIndex("qtParcela")));
        p.setDataInicio(cs.getLong(cs.getColumnIndex("datInici")));
        p.setHoraInicio(cs.getString(cs.getColumnIndex("horaInic")));
        p.setDataFim(cs.getLong(cs.getColumnIndex("datFim")));
        p.setHoraFim(cs.getString(cs.getColumnIndex("horaFim")));
        p.setPrazo(cs.getLong(cs.getColumnIndex("prazo")));
        p.setIdFormPg(cs.getString(cs.getColumnIndex("codFormpg")));
        p.setStatus(cs.getString(cs.getColumnIndex("status")));
        p.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
        p.setTotal(cs.getDouble(cs.getColumnIndex("total")));
        p.setRota(cs.getLong(cs.getColumnIndex("rota")));
        p.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
        p.setDel(cs.getString(cs.getColumnIndex("del")));
        p.setLatitudeInicio(cs.getDouble(cs.getColumnIndex("latitudeInicio")));
        p.setLongitudeInicio(cs.getDouble(cs.getColumnIndex("longitudeInicio")));
        p.setObs(cs.getString(cs.getColumnIndex("obs")));
        p.setIdEmpresa(cs.getInt(cs.getColumnIndex("idEmpresa")));
        p.getEmpresa().setCnpj(cs.getString(cs.getColumnIndex("emprCnpj")));//emprCnpj

        return p;
    }
}