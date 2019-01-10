/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.valTotPed;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;

/**
 *
 * @author ydxpaj
 */
public interface ValTotPedDAO {
    void salvar(ValTotPed r) throws SQLException;
    public ArrayList<ValTotPed> pesquisar(ValTotPed r) throws SQLException;
    public void update(ValTotPed seq)throws SQLException;
    void deletar(int id) throws SQLException;
}