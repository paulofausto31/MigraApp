/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.negativacao;

import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;

/**
 *
 * @author ydxpaj
 */
public interface NegativacaoDAO {
    void salvar(Negativacao n) throws SQLException;
    public ArrayList<Negativacao> pesquisar(Negativacao r) throws SQLException;
    public ArrayList<Negativacao> filtrar(Negativacao obj) throws SQLException;
    public Negativacao getForId(int id) throws SQLException;
    public long count() throws SQLException;
    int getMaxId() throws SQLException;
    //public ArrayList<SeqVisitStatus> getForSalesMan(double codSalesMan)throws SQLException;
    //public ArrayList<Cliente> getClientes(long codRota) throws SQLException;
    public ArrayList<Negativacao> getWithClients(Negativacao neg) throws SQLException;
    public ArrayList<Negativacao> getWithCodClients(Negativacao negFilter) throws SQLException;
    public void update(Negativacao seq)throws SQLException;
    void deletar(int id) throws SQLException;
    void deletarForFilter(Negativacao neg) throws SQLException;
}
