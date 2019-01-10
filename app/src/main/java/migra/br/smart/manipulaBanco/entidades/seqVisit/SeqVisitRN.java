/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisit;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class SeqVisitRN {
    SeqVisitDAO seqVisitDAO;

    public SeqVisitRN(Context ctx){
        seqVisitDAO = new DAOFactory().criaSeqVisitDAO(ctx);
    }

    public ArrayList<SeqVisit> pesquisar(SeqVisit l) throws SQLException{
        return this.seqVisitDAO.pesquisar(l);
    }

    public long count(long codRota) throws SQLException {
        return this.seqVisitDAO.count(codRota);
    }

    public ArrayList<Cliente> getClientes(long codRota) throws SQLException {
        return this.seqVisitDAO.getClientes(codRota);
    }

    public ArrayList<SeqVisit> getWithClients(long codRota, Cliente clien) throws SQLException{
        return this.seqVisitDAO.getWithClients(codRota, clien);
    }

    public void salvar(SeqVisit l) throws SQLException{
        this.seqVisitDAO.salvar(l);
    }

    public void update(SeqVisit seq) throws SQLException{
        this.seqVisitDAO.update(seq);
    }

    public SeqVisit getSeqVisit(double codCli) throws SQLException{
        return seqVisitDAO.getSeqVisit(codCli);
    }
}
