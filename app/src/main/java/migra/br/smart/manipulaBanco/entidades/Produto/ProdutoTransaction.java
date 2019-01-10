/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Produto;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.Preco.Preco;

/**
 *
 * @author ydxpaj
 */
public class ProdutoTransaction implements ProdutoDAO {

    SQLiteDatabase connction;
    
    public void setConnection(SQLiteDatabase connction) {
        this.connction = connction;
    }

    public SQLiteDatabase getConnction() {
        return connction;
    }
    
    @Override
    public void salvar(Produto SFEProd) throws SQLException {
        getConnction().beginTransaction();
        try{
            new ProdutoDirectAccess().salvar(getConnction(), SFEProd);
            getConnction().setTransactionSuccessful();
        }catch (SQLException ex){
            Log.e(ex.getMessage(), "banco");
            throw new SQLException(ex);
        }finally{
            getConnction().endTransaction();
            getConnction().close();
        }
    }

    @Override
    public ArrayList<Produto> pesquisar(Produto SFEProd) throws SQLException{
        ArrayList<Produto> produtos = new ArrayList<Produto>();
        ArrayList<Preco> precos = new ArrayList<>();
        try{
            getConnction().beginTransaction();
            
            produtos = new ProdutoDirectAccess().pesquisar(getConnction(), SFEProd);
/*            for(int i = 0; i < produtos.size(); i++){
                precos = new PrecoDirectAccess().getForNameProd(getConnection(), produtos.get(i).getPreco());
                produtos.get(i).setPrecos(precos);
            }*/
            
            getConnction().setTransactionSuccessful();
        }catch(SQLException ex){

                Log.e("banco", ex.getMessage());

                throw new SQLException(ex);

        }finally{
                getConnction().endTransaction();
                getConnction().close();
        }
        
        return produtos;
    }

    /*@Override
    public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException {
        ArrayList<ItemLista> arrLFSEProd = new ArrayList<ItemLista>();
        try{
            getConnection().beginTransaction();

            arrLFSEProd = new ItemListaDirectAccess().pesquisarNome(getConnection(), ItemLista);

            getConnection().setTransactionSuccessful();
        }catch(SQLException ex){

            Log.e("banco", ex.getMessage());

            throw new SQLException(ex);

        }finally{
            getConnection().endTransaction();
            getConnection().close();
        }

        return arrLFSEProd;
    }
*/

    @Override
    public void close() {

    }

}
