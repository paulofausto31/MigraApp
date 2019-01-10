/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.listaPedido;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;

public class ListaPedidoDirectAccess {

    SQLiteStatement statement;
    Cursor cs;

/*************************DELETA TUPLA SELECIONADA COM 's' CAMPO DELETAR***************************/
    public void delSelectDel(SQLiteDatabase connction, ListaPedido listaPedido) throws SQLException{
        connction.delete("listaPedido", "id=? and deletar=?",
                new String[]{String.valueOf(listaPedido.getId()), listaPedido.getDeletar()});
    }
/*************************DELETA TUPLA SELECIONADA COM 's' CAMPO DELETAR***************************/


    /**
     * TEMPORARIAMENTE NO LUGAR DE getProdsVendidos
     * @param connection
     * @param lp
     * @return
     * @throws SQLException
     */
    public ArrayList<ListaPedido> listProdsVendidos(SQLiteDatabase connection, ListaPedido lp) throws SQLException{
    ArrayList<ListaPedido> list = new ArrayList<>();
    double qtdCaixa = 0, qtdUnidade = 0;

    cs = connection.rawQuery(
            "select produto.nome as prodNome, sum(qtd) as iQtdCaixa, sum(qtdUn) as iQtdUnidade, " +
                    " codProd, produto.vendeFracionado as pVendFraciona, produto.unidadeVenda as pUndVenda, produto.unArmazena as pUnArmz, " +
                    " produto.qtdArmazena as pQtdArmaz, " +
                         /*itemLista.unVenda as iUnVend,*/" pedido.datInici as pDatIni, pedido.status as pStat " +
                    " from listaPedido inner join itemLista on listaPedido.idItem = itemLista.id " +
                    " inner join pedido on listaPedido.idPedido = pedido.id " +
                    " inner join produto on itemLista.codProd = produto.codigo " +
                    " where pDatIni >= ? and pDatIni <= ? and (pStat = 'Transmitido' or pStat = 'Fechado') " +
            //" where pDatIni >= ? and pDatIni <= ? and (pStat = 'Fechado') "+
                    " group by codProd",
            new String[]{String.valueOf(lp.getPedido().getDataInicio()), String.valueOf(lp.getPedido().getDataFim())}
    );

    while(cs.moveToNext()){
        ListaPedido lisPed = new ListaPedido();
        lisPed.getProduto().setNome(cs.getString(cs.getColumnIndex("prodNome")));
        lisPed.getProduto().setVENDE_FRACIONADO(cs.getString(cs.getColumnIndex("pVendFraciona")));
        lisPed.getProduto().setUnidadeVenda(cs.getString(cs.getColumnIndex("pUndVenda")));
        lisPed.getProduto().setQtdArmazenamento(cs.getDouble(cs.getColumnIndex("pQtdArmaz")));
        lisPed.getProduto().setUnArmazena(cs.getString(cs.getColumnIndex("pUnArmz")));
        lisPed.getItemLista().setQtd(cs.getString(cs.getColumnIndex("iQtdCaixa")));
        lisPed.getItemLista().setQtdUn(cs.getLong(cs.getColumnIndex("iQtdUnidade")));

        Log.e("QTDUN", lisPed.getItemLista().getQtdUn()+"");

        //lisPed.getItemLista().setQtd(cs.getString(cs.getColumnIndex("prodQtd")));
        //lisPed.getItemLista().setUnVenda(cs.getString(cs.getColumnIndex("iUnVend")));

        //Double.parseDouble(lisPed.getItemLista().getQtd())*lisPed.getItemLista().getProduto().getQtdArmazenamento())
            /*
            if(cs.getString(cs.getColumnIndex("qtd")).startsWith("0/")){
                lisPed.getItemLista().setQtd(
                        String.valueOf(Double.parseDouble(lisPed.getItemLista().getQtd())*lisPed.getItemLista().getProduto().getQtdArmazenamento())
                );
            }
            */


       // lisPed.getItemLista().setTotal(cs.getDouble(cs.getColumnIndex("prodTotal")));

        lisPed.getItemLista().setCodProd(cs.getString(cs.getColumnIndex("codProd")));
        lisPed.getPedido().setDataInicio(cs.getLong(cs.getColumnIndex("pDatIni")));
        lisPed.getPedido().setStatus(cs.getString(cs.getColumnIndex("pStat")));

        list.add(lisPed);
    }

    return list;
}







    public ArrayList<ListaPedido> getProdsVendidos(SQLiteDatabase connection, ListaPedido lp) throws SQLException{
        ArrayList<ListaPedido> list = new ArrayList<>();
        double qtdCaixa = 0, qtdUnidade = 0;

        cs = connection.rawQuery(
                "select produto.nome as prodNome, sum(itemLista.total) as prodTotal, qtd, " +
                        "sum(replace(replace(qtd, \"/0\", \"\"), \"0/\", \"\")) as prodQtd, " +
                        " codProd, produto.vendeFracionado as pVendFraciona, produto.unidadeVenda as pUndVenda, produto.qtdArmazena pQtdArmaz, " +
                         /*itemLista.unVenda as iUnVend,*/" pedido.datInici as pDatIni, pedido.status as pStat " +
                        " from listaPedido inner join itemLista on listaPedido.idItem = itemLista.id " +
                        " inner join pedido on listaPedido.idPedido = pedido.id " +
                        " inner join produto on itemLista.codProd = produto.codigo " +
                        " where pDatIni >= ? and pDatIni <= ? and (pStat = 'Transmitido' or pStat = 'Fechado') " +
                        //" where pDatIni >= ? and pDatIni <= ? and (pStat = 'Fechado') " +
                        " group by codProd",
                new String[]{String.valueOf(lp.getPedido().getDataInicio()), String.valueOf(lp.getPedido().getDataFim())}
        );

        while(cs.moveToNext()){
            ListaPedido lisPed = new ListaPedido();
            lisPed.getProduto().setNome(cs.getString(cs.getColumnIndex("prodNome")));
            lisPed.getProduto().setVENDE_FRACIONADO(cs.getString(cs.getColumnIndex("pVendFraciona")));
            lisPed.getProduto().setUnidadeVenda(cs.getString(cs.getColumnIndex("pUndVenda")));
            lisPed.getProduto().setQtdArmazenamento(cs.getDouble(cs.getColumnIndex("pQtdArmaz")));
            lisPed.getItemLista().setQtd(cs.getString(cs.getColumnIndex("prodQtd")));
            //lisPed.getItemLista().setUnVenda(cs.getString(cs.getColumnIndex("iUnVend")));

                    //Double.parseDouble(lisPed.getItemLista().getQtd())*lisPed.getItemLista().getProduto().getQtdArmazenamento())
            /*
            if(cs.getString(cs.getColumnIndex("qtd")).startsWith("0/")){
                lisPed.getItemLista().setQtd(
                        String.valueOf(Double.parseDouble(lisPed.getItemLista().getQtd())*lisPed.getItemLista().getProduto().getQtdArmazenamento())
                );
            }
            */

            //cs.getString(cs.getColumnIndex("qtd"));
            lisPed.getItemLista().setTotal(cs.getDouble(cs.getColumnIndex("prodTotal")));

            lisPed.getItemLista().setCodProd(cs.getString(cs.getColumnIndex("codProd")));
            lisPed.getPedido().setDataInicio(cs.getLong(cs.getColumnIndex("pDatIni")));
            lisPed.getPedido().setStatus(cs.getString(cs.getColumnIndex("pStat")));

            list.add(lisPed);
        }

        return list;
    }

    public ArrayList<ListaPedido> getForNomeProd(SQLiteDatabase connection, String nomeProd) throws SQLException{
        ArrayList<ListaPedido> arrLresult = new ArrayList<ListaPedido>();

        cs = connection.rawQuery(
                "select distinct idPedido, rota, seqVist_id, idItem,p.codigo as pCodProd,  p.nome as pNome, qtd, i.qtdUn as iQtdUn, total, " +
                    "codVendedor, codCli, lp.deletar as lpDeletar, i.descAcrePercent as iDescAcrePercent, " +
                        //"i.descAcreMone as iDescAcreMone, i.deletar as iDeletar, i.unFrac as iunFrac, " +
                        /*"i.unVenda as iUnVend, */"i.descAcreMone as iDescAcreMone, i.deletar as iDeletar,  " +
                        "p.unidadeVenda as pUnVend, p.unArmazena as pUnArm, " +
                        "p.pVenda1 as pPVenda1, p.pVenda2 as pPVenda2, p.pVenda3 as pPVenda3, " +
                        "p.pVenda4 as pPVenda4, p.pVenda5 as pPVenda5, p.pVenda6 as pPVenda6, " +
                        "p.pVenda7 as pPVenda7, p.pVenda8 as pPVenda8, p.pVenda9 as pPVenda9, " +
                        "p.saldo as pSald, i.pVendSelect as iPVendSelect, " +
                        "p.promo1 as prom1, p.promo2 as prom2, p.promo3 as prom3, p.promo4 as prom4, " +
                        "p.promo5 as prom5, p.promo6 as prom6, p.promo7 as prom7, p.promo8 as prom8, " +
                        "p.promo9 as prom9, p.valPromo as vProm, p.vendeFracionado as pVendFrac, p.qtdArmazena as pQtdArmazena " +
                "from listaPedido as lp " +
                        "inner join ItemLista as i on i.id = lp.idItem " +
                        "inner join produto as p on i.codProd = pCodProd " +
                "where idPedido = ? and codCli = ? and pNome like ?",
                new String[]{String.valueOf(CurrentInfo.idPedido), String.valueOf(CurrentInfo.codCli), "%"+nomeProd+"%"});
        while(cs.moveToNext()){
            ListaPedido lPedido = new ListaPedido();

            lPedido.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
            lPedido.setIdPedido(cs.getLong(cs.getColumnIndex("idPedido")));
            lPedido.setCodVendedor(cs.getDouble(cs.getColumnIndex("codVendedor")));
            lPedido.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            lPedido.setIdItem(cs.getString(cs.getColumnIndex("idItem")));
            lPedido.setDeletar(cs.getString(cs.getColumnIndex("lpDeletar")));
            lPedido.getItemLista().setDeletar(cs.getString(cs.getColumnIndex("iDeletar")));
            lPedido.getItemLista().getProduto().setNome(cs.getString(cs.getColumnIndex("pNome")));
            lPedido.getItemLista().setQtd(cs.getString(cs.getColumnIndex("qtd")));
            lPedido.getItemLista().setQtdUn(cs.getLong(cs.getColumnIndex("iQtdUn")));
            lPedido.getItemLista().setTotal(cs.getDouble(cs.getColumnIndex("total")));
            lPedido.getItemLista().setCodProd(cs.getString(cs.getColumnIndex("pCodProd")));
            lPedido.getItemLista().setDescAcrePercent(cs.getDouble(cs.getColumnIndex("iDescAcrePercent")));
            lPedido.getItemLista().setDescAcreMone(cs.getDouble(cs.getColumnIndex("iDescAcreMone")));
            //lPedido.getItemLista().setUnVenda(cs.getString(cs.getColumnIndex("iUnVend")));

            lPedido.getItemLista().getProduto().setPVENDA1(cs.getString(cs.getColumnIndex("pPVenda1")));
            lPedido.getItemLista().getProduto().setPVENDA2(cs.getString(cs.getColumnIndex("pPVenda2")));
            lPedido.getItemLista().getProduto().setPVENDA3(cs.getString(cs.getColumnIndex("pPVenda3")));
            lPedido.getItemLista().getProduto().setPVENDA4(cs.getString(cs.getColumnIndex("pPVenda4")));
            lPedido.getItemLista().getProduto().setPVENDA5(cs.getString(cs.getColumnIndex("pPVenda5")));
            lPedido.getItemLista().getProduto().setPVENDA6(cs.getString(cs.getColumnIndex("pPVenda6")));
            lPedido.getItemLista().getProduto().setPVENDA7(cs.getString(cs.getColumnIndex("pPVenda7")));
            lPedido.getItemLista().getProduto().setPVENDA8(cs.getString(cs.getColumnIndex("pPVenda8")));
            lPedido.getItemLista().getProduto().setPVENDA9(cs.getString(cs.getColumnIndex("pPVenda9")));
            lPedido.getItemLista().getProduto().setPromo1(cs.getString(cs.getColumnIndex("prom1")));
            lPedido.getItemLista().getProduto().setPromo2(cs.getString(cs.getColumnIndex("prom2")));
            lPedido.getItemLista().getProduto().setPromo3(cs.getString(cs.getColumnIndex("prom3")));
            lPedido.getItemLista().getProduto().setPromo4(cs.getString(cs.getColumnIndex("prom4")));
            lPedido.getItemLista().getProduto().setPromo5(cs.getString(cs.getColumnIndex("prom5")));
            lPedido.getItemLista().getProduto().setPromo6(cs.getString(cs.getColumnIndex("prom6")));
            lPedido.getItemLista().getProduto().setPromo7(cs.getString(cs.getColumnIndex("prom7")));
            lPedido.getItemLista().getProduto().setPromo8(cs.getString(cs.getColumnIndex("prom8")));
            lPedido.getItemLista().getProduto().setPromo9(cs.getString(cs.getColumnIndex("prom9")));
            lPedido.getItemLista().getProduto().setValPromo(cs.getLong(cs.getColumnIndex("vProm")));//validade da promoção
            lPedido.getItemLista().getProduto().setVENDE_FRACIONADO(cs.getString(cs.getColumnIndex("pVendFrac")));//verder faracionado
            lPedido.getItemLista().getProduto().setQtdArmazenamento(cs.getDouble(cs.getColumnIndex("pQtdArmazena")));
            lPedido.getItemLista().getProduto().setUnidadeVenda(cs.getString(cs.getColumnIndex("pUnVend")));
            lPedido.getItemLista().getProduto().setUnArmazena(cs.getString(cs.getColumnIndex("pUnArm")));

            lPedido.getItemLista().setpVendSelect(cs.getLong(cs.getColumnIndex("iPVendSelect")));
            lPedido.getItemLista().getProduto().setSaldo(cs.getDouble(cs.getColumnIndex("pSald")));
            lPedido.setRota(cs.getLong(cs.getColumnIndex("rota")));

            //lPedido.getItemLista().setUnFrac(cs.getString(cs.getColumnIndex("iunFrac")));
            Log.i("ITEM", lPedido.getItemLista().getpVendSelect()+"");

            arrLresult.add(lPedido);
        }

        return arrLresult;
    }

    public String[] listForCodPedAndCli(SQLiteDatabase connection, double codCli) throws SQLException{
        ArrayList<String> cods = new ArrayList<String>();

        cs = connection.rawQuery("select distinct idPedido from listaPedido where codCli = ?",
                new String[]{String.valueOf(codCli)});
        while(cs.moveToNext()){
            cods.add(cs.getString(cs.getColumnIndex("idPedido")));
        }

        String[] codigos = new String[cods.size()];
        codigos = cods.toArray(codigos);
        return codigos;
    }

    public void salvar(SQLiteDatabase connection, ListaPedido obj) throws SQLException{
        SQLiteStatement statement = connection.compileStatement(
                "insert into listaPedido(idPedido, codVendedor, idItem, codCli, seqVist_id, rota)" +
                        "values(?, ?, ?, ?, ?, ?);"
        );
        statement.clearBindings();
        statement.bindLong(1,obj.getIdPedido());//OK
        statement.bindDouble(2,obj.getCodVendedor());//OK
        statement.bindString(3,obj.getIdItem());//OK
        statement.bindDouble(4,obj.getCodCli());//OK
        statement.bindLong(5, obj.getSeqVist_id());//ok
        statement.bindLong(6, obj.getRota());
        statement.executeInsert();
    }

    public String getIdItem(SQLiteDatabase connection, String codProd, double codCli, long codPedido) throws SQLException{
        String idItem = "0";
        cs = connection.rawQuery("select lp.idItem as lpIdItem from listaPedido as lp" +
                "       inner join ItemLista as il on lp.idItem = il.id " +
                "where il.codProd = ? and lp.codCli = ? and lp.idPedido = ?",
                new String[]{codProd, String.valueOf(codCli), String.valueOf(codPedido)});
        while(cs.moveToNext()){
            idItem = cs.getString(cs.getColumnIndex("lpIdItem"));
        }
        return idItem;
    }

    public ArrayList<ItemLista> getItemLista(SQLiteDatabase connection, long idPed) throws SQLException{
        ArrayList<ItemLista> list = new ArrayList<>();
        cs = connection.rawQuery(
                "select distinct ItemLista.id as idItem, ItemLista.total as valTotalItem, " +
                        /*" ItemLista.unVenda as iUnVend, */" produto.id as prodId, " +
                        " produto.codigo as prodCod, produto.saldo as prodSald " +
                        " from ListaPedido " +
                    " inner join ItemLista on listaPedido.idItem = ItemLista.id " +
                    " inner join produto on ItemLista.codProd = produto.codigo" +
                " where ListaPedido.idPedido = ?", new String[]{String.valueOf(idPed)}
        );

        while(cs.moveToNext()){
            ItemLista item = new ItemLista();
            item.setId(cs.getString(cs.getColumnIndex("idItem")));
            item.setTotal(cs.getDouble(cs.getColumnIndex("valTotalItem")));
            item.getProduto().setSaldo(cs.getDouble(cs.getColumnIndex("prodSald")));
            item.getProduto().setCodigo(cs.getString(cs.getColumnIndex("prodCod")));
            item.getProduto().setId(cs.getInt(cs.getColumnIndex("prodId")));
            //item.setUnVenda(cs.getString(cs.getColumnIndex("iUnVend")));
            Log.i("TOTAL-----", item.getTotal()+"");
            list.add(item);
        }

        return list;
    }

    public ListaPedido getListaPedido(SQLiteDatabase connection, long idPedido) throws SQLException{
        cs = connection.rawQuery("select * from listaPedido where idPedido = ?", new String[]{String.valueOf(idPedido)});

        ListaPedido lp = new ListaPedido();

        while(cs.moveToNext()){
            lp.setCodCli(cs.getDouble(cs.getColumnIndex("codCli")));
            lp.setCodVendedor(cs.getDouble(cs.getColumnIndex("codVendedor")));
            lp.setIdItem(cs.getString(cs.getColumnIndex("idItem")));
            lp.setIdPedido(cs.getLong(cs.getColumnIndex("idPedido")));
            lp.setId(cs.getLong(cs.getColumnIndex("id")));
            lp.setDeletar(cs.getString(cs.getColumnIndex("deletar")));
            lp.setSeqVist_id(cs.getInt(cs.getColumnIndex("seqVist_id")));
        }

        return lp;
    }

    public String[] getQtdItemListaPedido(SQLiteDatabase connection, String codProd, double codCli, long idPedido) throws SQLException{
        String[] lPedItemQtd = new String[]{"0", "0"};
        cs = connection.rawQuery("select il.qtd as lPedItemQtd, il.qtdUn as ilQtdUn from listaPedido as lp" +
                        "       inner join ItemLista as il on lp.idItem = il.id " +
                        "where il.codProd = ? and lp.codCli = ? and idPedido = ?",
                new String[]{codProd, String.valueOf(codCli), String.valueOf(idPedido)});
        while(cs.moveToNext()){
            //lPedItemQtd = cs.getLong(cs.getColumnIndex("lPedItemQtd"));
            //lPedItemQtd = cs.getString(cs.getColumnIndex("lPedItemQtd")).replace("/0", "").replace("0/", "");
            lPedItemQtd[0] = cs.getString(cs.getColumnIndex("lPedItemQtd"));
            lPedItemQtd[1] = cs.getString(cs.getColumnIndex("ilQtdUn"));//quantidade em unidade
        }
        return lPedItemQtd;
    }

    public void delForIdItem(SQLiteDatabase connction, long idItem) throws SQLException{
        connction.delete("listaPedido", "idItem=? ",
                new String[]{String.valueOf(idItem)});
    }

    public void delForStatus(SQLiteDatabase connction, long idPedido) throws SQLException{
        connction.delete("listaPedido", "idPedido=? and deletar = 's'",
                new String[]{String.valueOf(idPedido)});
    }

    public void updateForIdItem(SQLiteDatabase connection, ListaPedido listaPedido) throws SQLException{
        connection.update("listaPedido" ,listaPedido.getValues(), "idItem=?",
                new String[]{String.valueOf(listaPedido.getIdItem())}
        );
    }

    public void deleteForIdPedido(SQLiteDatabase connction, long idPedido) throws SQLException{
        connction.delete("listaPedido", "idPedido=?", new String[]{String.valueOf(idPedido)});
    }
}