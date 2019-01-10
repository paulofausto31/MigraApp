package migra.br.smart.manipulaBanco.dbAccess;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocal;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalRN;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoRN;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteRN;

/**
 * Created by ARMIGRA on 22/08/2016.
 */
public class DBAccess {

    Context ctx;

    public DBAccess(Context ctx){
        this.ctx = ctx;
    }
    /********************************CONEXÃO LOCAL*************************************/
    public ArrayList<ConfigLocal> pesquisar(ConfigLocal configLocal) throws SQLException {
        return new ConfigLocalRN(ctx).pesquisar(configLocal);
    }
    public void salvar(ConfigLocal configLocal) throws SQLException{
        new ConfigLocalRN(ctx).salvar(configLocal);
    }
    public void atualizar(ConfigLocal configLocal) throws SQLException{
        new ConfigLocalRN(ctx).atualizar(configLocal);
    }
    public void deletar(int id) throws SQLException{
        new ConfigLocalRN(ctx).deletar(id);
    }
    /********************************CONEXÃO LOCAL*************************************/

    /********************************PRODUTOS******************************************/
    public ArrayList<Produto> pesquisar(Produto prod) throws SQLException {//ItemLista
        return new ProdutoRN(ctx).pesquisar(prod);
    }
    /********************************PRODUTOS******************************************/

    /********************************CLIENTES******************************************/
    public ArrayList<Cliente> pesquisar(Cliente sgeClie) throws SQLException {//Vendedor
        return new ClienteRN(ctx).pesquisar(sgeClie);
    }
    /********************************CLIENTES******************************************/

    /********************************SFEPRCO*******************************************
    public ArrayList<SeqVisitStatus> getForNameProd(SeqVisitStatus sfePrco) throws SQLException {//SeqVisitStatus
        return new ValTotPedRN(ctx).getForNameProd(sfePrco);
    }
    /********************************SFEPRCO*******************************************/
}
