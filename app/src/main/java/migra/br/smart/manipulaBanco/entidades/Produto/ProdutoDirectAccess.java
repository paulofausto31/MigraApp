/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Produto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.utils.Utils;

/**
 *
 * @author ydxpaj
 */
public class ProdutoDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;

    public ArrayList<Produto> pesquisar(SQLiteDatabase connection, Produto obj) throws SQLException{
        ArrayList<Produto> arrLresult = new ArrayList<Produto>();

        String fgQueryProCod = " and proCod like ?";
        String codigo = obj.getCodigo()+"%";

        if(!obj.getCodigo().equals("")){
            fgQueryProCod = "and proCod = ?";
            codigo = obj.getCodigo();
        }
/*
        "fracionar text not null default 'N', "+//upgrade versao 6 permitir fracionar S/N
                "unArmazena text not null default '', "+//upgrade versao 6 unidade de armazaenamento
                "qtdArmazena double not null default '1', "+//upgrade versao 6 quantidade armazenada no pacote, caixa, etc
        */
        cs = connection.rawQuery(
                "select " +
                "produto.promo1 as prom1, produto.promo2 as prom2, produto.promo3 as prom3, " +
                        " produto.promo4 as prom4, produto.promo5 as prom5, produto.promo6 as prom6, " +
                        " produto.promo7 as prom7, produto.promo8 as prom8, produto.promo9 as prom9, " +
                        " produto.id as id, produto.codigo as proCod, produto.nome as proNome," +
                 " produto.unidadeVenda as proUnidVend, " +
                 " produto.linha as proLinha, produto.tipo as pTipo, " +
                 " produto.unidadeArmazena as proArmazenam, " +
                 " pVenda1, pVenda2, pVenda3, pVenda4, pVenda5, pVenda6, pVenda7, pVenda8, pVenda9, " +
                 " saldo, valPromo, unArmazena, qtdArmazena, vendeFracionado "+
                 " from produto "+
                   //"inner join preco on proCod = preco.codProd "+
                " where proNome like ? "+fgQueryProCod+
                //" and proLinha like ? and (pTipo ='' or  pTipo = ?) " +
                " and proLinha like ? and pTipo = ? " +
                " AND (   pVenda1 <> 0 OR " +
                        "       pVenda2 <> 0 OR " +
                        "       pVenda3 <> 0 OR " +
                        "       pVenda4 <> 0 OR " +
                        "       pVenda5 <> 0 or " +
                        "       pVenda6 <> 0 OR " +
                        "       pVenda7 <> 0 OR " +
                        "       pVenda8 <> 0 OR " +
                        "       pVenda9 <> 0) " +
                     Utils.getExtraQuery() +" order by proNome;",// limit 200;",
                new String[]{"%"+obj.getNome()+"%", codigo, obj.getLinha()+"%", obj.getTIPO()});
        Log.i("QUER", Utils.getExtraQuery());
        //statement.execute();
        while(cs.moveToNext()){

            Produto prod = new Produto();
            prod.setCodigo(cs.getString(cs.getColumnIndex("proCod")));
            prod.setId(cs.getInt(cs.getColumnIndex("id")));
            prod.setNome(cs.getString(cs.getColumnIndex("proNome")));
            prod.setUnidadeVenda(cs.getString(cs.getColumnIndex("proUnidVend")));
            //prod.setSaldo(cs.getLong(cs.getColumnIndex("proSaldo")));
            prod.setLinha(cs.getString(cs.getColumnIndex("proLinha")));
            //prod.setFornecedor(cs.getString(cs.getColumnIndex("proForneced")));
            prod.setUNIDADE_ARMAZENAMENTO(cs.getString(cs.getColumnIndex("proArmazenam")));
            prod.setPVENDA1(cs.getString(cs.getColumnIndex("pVenda1")));
            prod.setPVENDA2(cs.getString(cs.getColumnIndex("pVenda2")));
            prod.setPVENDA3(cs.getString(cs.getColumnIndex("pVenda3")));
            prod.setPVENDA4(cs.getString(cs.getColumnIndex("pVenda4")));
            prod.setPVENDA5(cs.getString(cs.getColumnIndex("pVenda5")));
            prod.setPVENDA6(cs.getString(cs.getColumnIndex("pVenda6")));
            prod.setPVENDA7(cs.getString(cs.getColumnIndex("pVenda7")));
            prod.setPVENDA8(cs.getString(cs.getColumnIndex("pVenda8")));
            prod.setPVENDA9(cs.getString(cs.getColumnIndex("pVenda9")));

            prod.setPromo1(cs.getString(cs.getColumnIndex("prom1")));
            prod.setPromo2(cs.getString(cs.getColumnIndex("prom2")));
            prod.setPromo3(cs.getString(cs.getColumnIndex("prom3")));
            prod.setPromo4(cs.getString(cs.getColumnIndex("prom4")));
            prod.setPromo5(cs.getString(cs.getColumnIndex("prom5")));
            prod.setPromo6(cs.getString(cs.getColumnIndex("prom6")));
            prod.setPromo7(cs.getString(cs.getColumnIndex("prom7")));
            prod.setPromo8(cs.getString(cs.getColumnIndex("prom8")));
            prod.setPromo9(cs.getString(cs.getColumnIndex("prom9")));

            prod.setTIPO(cs.getString(cs.getColumnIndex("pTipo")));
            prod.setSaldo(cs.getDouble(cs.getColumnIndex("saldo")));

            prod.setValPromo(cs.getLong(cs.getColumnIndex("valPromo")));

            prod.setUnArmazena(cs.getString(cs.getColumnIndex("unArmazena")));
            prod.setQtdArmazenamento(cs.getDouble(cs.getColumnIndex("qtdArmazena")));
            prod.setVENDE_FRACIONADO(cs.getString(cs.getColumnIndex("vendeFracionado")));
           // prod.getPreco().setQtParcela(cs.getDouble(cs.getColumnIndex("preValor")));
           // prod.getPreco().setIdPedido(cs.getString(cs.getColumnIndex("preVendVist")));
           //prod.getPreco().setSeq_id(cs.getLong(cs.getColumnIndex("preId")));
            arrLresult.add(prod);
        }

        Utils.setExtraQuery("");
        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, Produto obj) throws SQLException{
        int i = 0;
/*tipoUnidade=unidade de venda, armazenamento=unidade de armazenamento*/
        SQLiteStatement statement = connection.compileStatement(
                "insert into produto(codigo, codigoBarras, nome, unidadeVenda, " +
                        " linha, subLinha, grupo, pVenda1, pVenda2, pVenda3, pVenda4, " +
                        " descontoMaximo, vendeFracionado, controlaLote, marca, modelo, " +
                        " pesoBruto, pesoLiq, unidadeArmazena, " +
                        " pVenda5, pVenda6, pVenda7, pVenda8, pVenda9, tipo, saldo, " +
                        " promo1, promo2, promo3, promo4, promo5, promo6, promo7, promo8, promo9, " +
                        " valPromo, unArmazena, qtdArmazena" +
                        ") " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        statement.clearBindings();
        statement.bindString(1,obj.getCodigo());//OK
        statement.bindString(2,obj.getCodBarras());//OK
        statement.bindString(3,obj.getNome());//OK
        statement.bindString(4,obj.getUnidadeVenda());//OK
        statement.bindString(5,obj.getLinha());//OK
        statement.bindString(6,obj.getSubLinha());//OK
        statement.bindString(7,obj.getGrupo());//OK
        statement.bindString(8,obj.getPVENDA1());//OK
        statement.bindString(9,obj.getPVENDA2());//OK
        statement.bindString(10,obj.getPVENDA3());//OK
        statement.bindString(11,obj.getPVENDA4());//OK
        statement.bindString(12,obj.getDESCONTO_MAXIMO());//OK
        statement.bindString(13,obj.getVENDE_FRACIONADO());//OK
        statement.bindString(14,obj.getCONTROLA_LOTE());//OK
        statement.bindString(15,obj.getMARCA());//OK
        statement.bindString(16,obj.getMODELO());//OK
        statement.bindString(17,obj.getPESO_BRUTO());//OK
        statement.bindString(18,obj.getPESO_LIQUIDO());//OK
//        statement.bindString(19,obj.getRAZAO_DE_VENDA_EM_MULTIPLO());//OK
        statement.bindString(19, obj.getUNIDADE_ARMAZENAMENTO());//ok
        statement.bindString(20, obj.getPVENDA5());//ok
        statement.bindString(21, obj.getPVENDA6());//ok
        statement.bindString(22, obj.getPVENDA7());//ok
        statement.bindString(23, obj.getPVENDA8());//ok
        statement.bindString(24, obj.getPVENDA9());//ok
        statement.bindString(25, obj.getTIPO());//ok
        statement.bindDouble(26, obj.getSaldo());

        statement.bindString(27, obj.getPromo1());
        statement.bindString(28, obj.getPromo2());
        statement.bindString(29, obj.getPromo3());
        statement.bindString(30, obj.getPromo4());
        statement.bindString(31, obj.getPromo5());
        statement.bindString(32, obj.getPromo6());
        statement.bindString(33, obj.getPromo7());
        statement.bindString(34, obj.getPromo8());
        statement.bindString(35, obj.getPromo9());

        statement.bindLong(36, obj.getValPromo());
        statement.bindString(37, obj.getUnArmazena());
        statement.bindDouble(38, obj.getQtdArmazenamento());

        statement.executeInsert();
    }
}