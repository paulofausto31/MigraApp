/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisit;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;

/**
 *
 * @author ydxpaj
 */
public interface SeqVisitDAO {
    void salvar(SeqVisit r) throws SQLException;
    public ArrayList<SeqVisit> pesquisar(SeqVisit r) throws SQLException;
    public long count(long codRota) throws SQLException;
    public ArrayList<SeqVisit> getForSalesMan(double codSalesMan)throws SQLException;
    public ArrayList<Cliente> getClientes(long codRota) throws SQLException;
    public ArrayList<SeqVisit> getWithClients(long codRota, Cliente cli) throws SQLException;
    public void update(SeqVisit seq)throws SQLException;
    void deletar(int id) throws SQLException;
    public SeqVisit getSeqVisit(double codCli) throws SQLException;
}
