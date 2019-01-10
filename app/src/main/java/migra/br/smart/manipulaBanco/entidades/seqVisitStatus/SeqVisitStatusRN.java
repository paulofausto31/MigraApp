/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisitStatus;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class SeqVisitStatusRN {
    SeqVisitStatusDAO seqVisitStatusDAO;

    public SeqVisitStatusRN(Context ctx){
        seqVisitStatusDAO = new DAOFactory().criaSeqVisitStatusDAO(ctx);
    }

    public ArrayList<SeqVisitStatus> pesquisar(SeqVisitStatus l) throws SQLException{
        return this.seqVisitStatusDAO.pesquisar(l);
    }
    public ArrayList<SeqVisitStatus> getListSeqVisitStat() throws SQLException {
        return this.seqVisitStatusDAO.getListSeqVisitStat();
    }
    public long count(long codRota) throws SQLException {
        return this.seqVisitStatusDAO.count(codRota);
    }
    /*
    public ArrayList<Cliente> getClientes(long codRota) throws SQLException {
        return this.seqVisitStatusDAO.getClientes(codRota);
    }

    public ArrayList<SeqVisitStatus> getWithClients(long codRota, Cliente clien) throws SQLException{
        return this.seqVisitStatusDAO.getWithClients(codRota, clien);
    }
*/
    public void salvar(SeqVisitStatus l) throws SQLException{
        this.seqVisitStatusDAO.salvar(l);
    }

    public void update(SeqVisitStatus seq) throws SQLException{
        this.seqVisitStatusDAO.update(seq);
    }

    public void deletar(String sr_recno) throws SQLException{
        this.seqVisitStatusDAO.deletar(sr_recno);
    }
/*
    public SeqVisitStatus getSeqVisit(double codCli) throws SQLException{
        return seqVisitStatusDAO.getSeqVisit(codCli);
    }
    */
}
