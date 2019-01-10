/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.pedido;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaRN;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusRN;
import migra.br.smart.manipulaBanco.factory.DAOFactory;
import migra.br.smart.utils.data.Data;

/**
 *
 * @author ydxpaj
 */
public class PedidoRN {
    PedidoDAO pedidoDAO;
    Context ctx;
    private String statusPed = "";//status do pedido: Aberto, Fechado, Transmitido
    public PedidoRN(Context ctx){
        this.ctx = ctx;

        pedidoDAO = new DAOFactory().criaPedidoDAO(ctx);
    }

    public ArrayList<Pedido> filtrar(Pedido pedido) throws SQLException{
        return this.pedidoDAO.filtrar(pedido);
    }

    public ArrayList<Pedido> listForCliAndStstus(Pedido pf) throws SQLException{
        return this.pedidoDAO.listForCliAndStstus(pf);
    }

    public ArrayList<Pedido> getOpenOrClose(Pedido pedido) throws SQLException {
        return this.pedidoDAO.getOpenOrClose(pedido);
    }

    public ArrayList<Pedido> pesquisar(Pedido SFEProd) throws SQLException{
        return this.pedidoDAO.pesquisar(SFEProd);
    }

    public String[] listForCodPedAndCli() throws SQLException {
        return this.pedidoDAO.listForCodPedAndCli();
    }

    public long getMaxId(Pedido p) throws SQLException{
        return this.pedidoDAO.getMaxId(p);
    }

    public void salvar(Pedido p) throws SQLException {
        p.setId(geraId(p));
        this.pedidoDAO.salvar(p);
    }

    public void update(Pedido pedido) throws SQLException{
        this.pedidoDAO.update(pedido);
    }

    public void delPedidoForId(long id) throws SQLException {
        this.pedidoDAO.delPedidoForId(id);
    }

    public Pedido getForId(long id) throws SQLException {
        return this.pedidoDAO.getForId(id);
    }

    public void duplicar(Pedido p, ListaPedido lp) throws SQLException {
        this.pedidoDAO.duplicar(p, lp);
    }

    /**
     * duplica o pedido
     * @param status status do pedido: Aberto, Fechado, Transmitido
     * @throws SQLException
     */
    public void duplicar(String status) throws SQLException {
        this.statusPed = status;
        duplicar();
    }

    /**
     * Guarda o codigo do novo pedido em CurrentInfo.idPedido
     * @throws SQLException
     */
    public void duplicar() throws SQLException {
        ArrayList<ListaPedido> listaPedidos = new ListaPedidoRN(ctx).getForNomeProd("");
        Pedido p = this.pedidoDAO.getForId(CurrentInfo.idPedido);//
        Log.i("PRN", ""+CurrentInfo.idPedido+"--"+CurrentInfo.codCli);
        CurrentInfo.idPedido =  geraId(p);//codigo do pedido duplicado
        p.setId(CurrentInfo.idPedido);

        p.setDataInicio(new Data(Calendar.getInstance().getTimeInMillis()).getOnlyDataInMillis());//String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance()));
        p.setHoraInicio(String.format("%tT", Calendar.getInstance()));
        /*p.setDataFim(0);
        p.setHoraFim("");*/

        SeqVisit c = new SeqVisit();

        if(this.statusPed.equals("")){
            this.statusPed = "Aberto";
            p.setDataFim(0);
            p.setHoraFim("");
            c.setStatus("A");
        }else{
            p.setDataFim(p.getDataInicio());
            p.setHoraFim(p.getHoraInicio());
            c.setStatus("P");
        }
        p.setStatus(this.statusPed);
        new PedidoRN(ctx).salvar(p);
        //ItemListaRN itList = ;
        double total = 0;
        for(ListaPedido lp:listaPedidos){
            lp.setIdPedido(CurrentInfo.idPedido);
            lp.setIdItem(geraIdItem(lp.getItemLista().getCodProd()));//gerando id dos itens duplicados
            Log.i("InfoPedido", lp.getIdItem()+"");
            total += lp.getItemLista().getTotal();
            new ItemListaRN(ctx).salvar(lp.getItemLista());
            new ListaPedidoRN(ctx).salvar(lp);
        }

        p.setTotal(total);
        new PedidoRN(ctx).update(p);

        /*************DEFINE STATUS COMO "A=ABERTO"*****************************/
        //SeqVisit c = new SeqVisit();
        c.setId(p.getSeqVist_id());
       // c.setStatus("A");
        new SeqVisitRN(ctx).update(c);

        SeqVisitStatus seqVisitStatus = new SeqVisitStatus();
        seqVisitStatus.setSeq_id(String.valueOf(c.getId()));
        seqVisitStatus.setCodPed(String.valueOf(CurrentInfo.idPedido));
        seqVisitStatus.setStatus(c.getStatus());

        new SeqVisitStatusRN(ctx).salvar(seqVisitStatus);
        /*************DEFINE STATUS COMO "A=ABERTO"*****************************/
    }

    private String geraIdItem(String codProd){//retorno original long
        String codCli = String.valueOf(CurrentInfo.codCli).replace(".", "");
        StringBuilder sb = new StringBuilder(String.valueOf(CurrentInfo.idPedido));
        sb.delete(0, 4);//elimina do ano do codigo do pedido
        //codProd = (codProd).replace(".", "")+codCli+Integer.parseInt(sb.toString());
        long cod = Long.parseLong(sb.toString());

        codProd = (codProd).replace(".", "")+codCli+cod;

        //return Long.parseLong(codProd);//////MUDAR ISSO PARA long
        return codProd;//////MUDAR ISSO PARA STRING
    }

    public long geraId(Pedido p) throws SQLException{
        Calendar c = Calendar.getInstance();
        //int maxId = new PedidoRN(ctx).getMaxId();
        long maxId = new PedidoRN(ctx).getMaxId(p);

        /*if(maxId > 0){
            maxId++;
        }else{
            maxId = c.get(Calendar.YEAR)*100000;
        }*/
        if(maxId > 0){
            maxId = 1+maxId;
        }else{
            //maxId = Double.parseDouble(new Data(c.getTimeInMillis()).getDataWithoutSeparator()+""+CurrentInfo.codVendedor)*10000000+1;
            if(CurrentInfo.codVendedor.endsWith(".0")){
                CurrentInfo.codVendedor = CurrentInfo.codVendedor.replace(".0", "");
            }
            maxId = Long.parseLong(p.getRota()+""+CurrentInfo.codVendedor+""+c.get(Calendar.YEAR)+"0000001");//new Data(c.getTimeInMillis()).getDataWithoutSeparator();
        }

        Log.i("MAX_ID", ""+maxId);

        CurrentInfo.idPedido = maxId;

        return maxId;
    }
}