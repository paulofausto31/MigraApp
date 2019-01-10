/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.itemLista;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ItemListaRN {
    ItemListaDAO itemListaDAO;
    Context ctx;
    public ItemListaRN(Context ctx){
        this.ctx = ctx;
        itemListaDAO = new DAOFactory().criaItemListaDAO(ctx);
    }

    public ArrayList<ItemLista> pesquisar(ItemLista prodPed) throws SQLException{
        return this.itemListaDAO.pesquisar(prodPed);
    }

    public void salvar(ItemLista item) throws SQLException {
        String id = new ListaPedidoRN(ctx).getIdItem(item.getCodProd(), CurrentInfo.codCli, CurrentInfo.idPedido);
       // Pedido p = new PedidoRN(ctx).getForId(CurrentInfo.idPedido);
        if(Double.parseDouble(id) > 0){
            //ItemLista itL = new ItemListaRN(ctx).getForId(id);
            item.setId(id);
            this.itemListaDAO.update(item);
        }else{
            item.setId(geraIdItem(item.getCodProd()));
            this.itemListaDAO.salvar(item);
            ListaPedido listaPedido = new ListaPedido();
            listaPedido.setIdItem(item.getId());//ok
            Log.i("ID_LIST_PED", listaPedido.getIdItem()+"");
            Log.i("ID_ITEM", item.getId()+"");

            listaPedido.setCodCli(CurrentInfo.codCli);
            listaPedido.setIdPedido(CurrentInfo.idPedido);//ok
            Pedido p = new PedidoRN(ctx).getForId(CurrentInfo.idPedido);
            listaPedido.setCodVendedor(Double.parseDouble(CurrentInfo.codVendedor));
            listaPedido.setRota(p.getRota());
            listaPedido.setSeqVist_id(p.getSeqVist_id());
            new ListaPedidoRN(ctx).salvar(listaPedido);
/*
            p.setTotal(p.getTotal()+item.getTotal());
            new PedidoRN(ctx).update(p);*/
        }
    }

    public ItemLista getForId(String id) throws SQLException {
        return this.itemListaDAO.getForId(id);
    }

    public void delForIdItem(String id) throws SQLException{
        this.itemListaDAO.delForIdItem(id);
    }

    public long getMaxId() throws SQLException{
        return this.itemListaDAO.getMaxId();
    }

    public String geraIdItem(String codProd) throws SQLException {//original long
        //long idItem = 0;
        //String codCli = String.valueOf(CurrentInfo.codCli).replace(".", "");
        /*String codCli = String.valueOf(CurrentInfo.codCli);
        if(codCli.endsWith(".0")){
            codCli = String.valueOf(CurrentInfo.codCli).replace(".0", "");
        }
        StringBuilder sb = new StringBuilder(String.valueOf(CurrentInfo.idPedido));*/
        Log.i("ID_GERAR", CurrentInfo.idPedido+"");
       // sb.deletar(0, 4);//elimina do ano do codigo do pedido
        //codProd = codProd.replace(".", "")+Double.parseDouble(String.valueOf(CurrentInfo.idPedido));
        long maxId = new ItemListaRN(ctx).getMaxId();
        Log.i("ID_ITEM_pesq", maxId+"");
        if(maxId > 0){
            maxId += 1;
        }else{
            maxId = Long.parseLong(codProd)*10000000000000l;
        }

        Log.i("ID_ITEM", maxId+"");
        return String.valueOf(maxId);
    }

    public void update(ItemLista itemLista) throws SQLException {
        this.itemListaDAO.update(itemLista);
    }

    public long getId(String codProd) throws SQLException {
        return this.itemListaDAO.getId(codProd);
    }
}