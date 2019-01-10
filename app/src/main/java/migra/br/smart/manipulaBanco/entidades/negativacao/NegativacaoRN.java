/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.negativacao;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class NegativacaoRN {
    NegativacaoDAO negativacaoDAO;

    public NegativacaoRN(Context ctx){
        negativacaoDAO = new DAOFactory().criaNegativacaoDAO(ctx);
    }

    public ArrayList<Negativacao> pesquisar(Negativacao l) throws SQLException{
        return this.negativacaoDAO.pesquisar(l);
    }

    public ArrayList<Negativacao> filtrar(Negativacao obj) throws SQLException {
        return this.negativacaoDAO.filtrar(obj);
    }
/*
    public ArrayList<Cliente> getClientes(long codRota) throws SQLException {
        return this.negativacaoDAO.getClientes(codRota);
    }
*/
    public long count() throws SQLException {
        return this.negativacaoDAO.count();
    }

    public int getMaxId() throws SQLException {
        return this.negativacaoDAO.getMaxId();
    }

    public Negativacao getForId(int id) throws SQLException {
        return this.negativacaoDAO.getForId(id);
    }

    public ArrayList<Negativacao> getWithClients(Negativacao neg) throws SQLException{
        return this.negativacaoDAO.getWithClients(neg);
    }

    public ArrayList<Negativacao> getWithCodClients(Negativacao negFilter) throws SQLException {
        return this.negativacaoDAO.getWithCodClients(negFilter);
    }

    public void salvar(Negativacao n) throws SQLException{
        this.negativacaoDAO.salvar(n);
    }

    public void update(Negativacao seq) throws SQLException {
        this.negativacaoDAO.update(seq);
    }

    public void deletar(int id) throws SQLException{
        this.negativacaoDAO.deletar(id);
    }

    public void deletarForFilter(Negativacao neg) throws SQLException{
        this.negativacaoDAO.deletarForFilter(neg);
    }
}
