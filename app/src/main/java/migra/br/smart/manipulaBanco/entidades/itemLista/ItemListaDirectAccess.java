/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.itemLista;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public class ItemListaDirectAccess {
    
    Connection connection;
    SQLiteStatement statement;
    Cursor cs;

    public ItemLista getForId(SQLiteDatabase connection, String id) throws SQLException{
        ItemLista item = new ItemLista();

        cs = connection.rawQuery(
                "select * from ItemLista where id = ?",
                new String[]{String.valueOf(id)}
        );

        while(cs.moveToNext()){
            item.setDescAcreMone(cs.getDouble(cs.getColumnIndex("descAcreMone")));
            item.setTotal(cs.getDouble(cs.getColumnIndex("total")));
            item.setQtd(cs.getString(cs.getColumnIndex("qtd")));
            item.setCodProd(cs.getString(cs.getColumnIndex("codProd")));
            item.setDescAcrePercent(cs.getDouble(cs.getColumnIndex("descAcrePercent")));
            item.setId(cs.getString(cs.getColumnIndex("id")));
            item.setDeletar(cs.getString(cs.getColumnIndex("deletar")));
            item.setQtdUn(cs.getLong(cs.getColumnIndex("qtdUn")));//adicionado na versao 2 do banco
            //item.setUnVenda(cs.getString(cs.getColumnIndex("unVenda")));
            //item.setUnFrac(cs.getString(cs.getColumnIndex("unFrac")));
        }

        return item;
    }

    public ArrayList<ItemLista> pesquisar(SQLiteDatabase connection, ItemLista obj) throws SQLException{
        ArrayList<ItemLista> arrLresult = new ArrayList<ItemLista>();
        ItemLista itemLista = null;
/*
        String codProd = "%";
        if(obj.getCodProd().equals("")){
            codProd = obj.getCodProd()+"%";
        }
*/
        cs = connection.rawQuery(
                "select i.qtdUn as iQtdUn, i.codProd as iCodProd, i.id as iId, p.codigo as pCodigo, i.qtd as iQtd, i.total as iTotal, " +
                        "i.descAcrePercent as iDescAcrePercent, i.descAcreMone as iDescAcreMone, i.deletar as iDeletar, " +
                        //" unFrac, p.nome as pNome, p.codigo as pCodigo from ItemLista as i "+
                        /*" unFrac,*/" p.nome as pNome, p.codigo as pCodigo from ItemLista as i "+
                        "inner join produto as p on iCodProd = pCodigo " +
                "where codProd like ? ",
                new String[]{obj.getCodProd()+"%"});
        while(cs.moveToNext()){
            itemLista = new ItemLista();
            itemLista.setId(cs.getString(cs.getColumnIndex("iId")));
            itemLista.setCodProd(cs.getString(cs.getColumnIndex("iCodProd")));
            itemLista.setQtd(cs.getString(cs.getColumnIndex("iQtd")));
            itemLista.setTotal(cs.getDouble(cs.getColumnIndex("iTotal")));
            itemLista.setDescAcrePercent(cs.getDouble(cs.getColumnIndex("iDescAcrePercent")));
            itemLista.setDescAcreMone(cs.getDouble(cs.getColumnIndex("iDescAcreMone")));
            itemLista.getProduto().setNome(cs.getString(cs.getColumnIndex("pNome")));
            itemLista.getProduto().setCodigo(cs.getString(cs.getColumnIndex("pCodigo")));
            itemLista.setDeletar(cs.getString(cs.getColumnIndex("iDeletar")));
            itemLista.setQtdUn(cs.getLong(cs.getColumnIndex("iQtdUn")));//adicionado na versão 2 do banco
            //itemLista.setUnVenda(cs.getString(cs.getColumnIndex("iUnVend")));
            //itemLista.setUnFrac(cs.getString(cs.getColumnIndex("unFrac")));

            arrLresult.add(itemLista);
        }

        return arrLresult;
    }

    public void salvar(SQLiteDatabase connection, ItemLista itemLista) throws SQLException{

        SQLiteStatement statement = connection.compileStatement(
                "insert into ItemLista(codProd, qtd, total, descAcrePercent, id, descAcreMone, pVendSelect, qtdUn)"+//, unVenda)"+//unFrac) " +
                        //"values(?, ?, ?, ?, ?, ?, ?, ?);"
                        "values(?, ?, ?, ?, ?, ?, ?, ?);"
        );
        statement.clearBindings();
        statement.bindString(1,itemLista.getCodProd());//OK
        statement.bindString(2, itemLista.getQtd());
        statement.bindDouble(3, itemLista.getTotal());
        statement.bindDouble(4, itemLista.getDescAcrePercent());
        statement.bindString(5, itemLista.getId());
        statement.bindDouble(6, itemLista.getDescAcreMone());
        statement.bindLong(7, itemLista.getpVendSelect());//o preço que foi escolhi para vender
        statement.bindLong(8, itemLista.getQtdUn());
        //statement.bindString(8, itemLista.getUnVenda());//

        //statement.bindString(8, itemLista.getUnFrac());

        statement.executeInsert();
    }

    public long getId(SQLiteDatabase connection, String codProd) throws SQLException{
        long id = 0;
        cs = connection.rawQuery("select i.id as idItem from itemLista as i " +
                "inner join produto as p on i.codProd = p.codigo " +
                "where i.codProd = ?", new String[]{codProd});
        while(cs.moveToNext()){
            id = cs.getLong(cs.getColumnIndex("idItem"));
        }
        return id;
    }

    public void delForIdItem(SQLiteDatabase connection, String id) throws SQLException{
        connection.delete("ItemLista", "id=?", new String[]{String.valueOf(id)});
    }

    public void update(SQLiteDatabase connection, ItemLista itemLista) throws SQLException{
        connection.update("ItemLista", itemLista.getValues(), "id=?",new String[]{String.valueOf(itemLista.getId())});
    }

    public long getMaxId(SQLiteDatabase connection) throws SQLException{
        long maxId = 0;

        cs = connection.rawQuery("select max(id) from itemLista", null);
        while(cs.moveToNext()){
            maxId = cs.getLong(cs.getColumnIndex("max(id)"));
        }
        return maxId;
    }
}