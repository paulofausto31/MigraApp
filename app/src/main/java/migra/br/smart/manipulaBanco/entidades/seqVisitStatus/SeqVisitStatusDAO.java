/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisitStatus;

import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;

/**
 *
 * @author ydxpaj
 */
public interface SeqVisitStatusDAO {
    void salvar(SeqVisitStatus r) throws SQLException;
    public ArrayList<SeqVisitStatus> pesquisar(SeqVisitStatus r) throws SQLException;
    public ArrayList<SeqVisitStatus> getListSeqVisitStat() throws SQLException;
    public long count(long codRota) throws SQLException;
    //public ArrayList<SeqVisitStatus> getForSalesMan(double codSalesMan)throws SQLException;
    //public ArrayList<Cliente> getClientes(long codRota) throws SQLException;
    //public ArrayList<SeqVisitStatus> getWithClients(long codRota, Cliente cli) throws SQLException;
    public void update(SeqVisitStatus seq)throws SQLException;
    void deletar(String id) throws SQLException;
    //public SeqVisitStatus getSeqVisit(double codCli) throws SQLException;
}
