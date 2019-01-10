/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.SaldoEstoque;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface SaldoEstoqueDAO {
    void salvar(SaldoEstoque s) throws SQLException;
    ArrayList<SaldoEstoque> pesquisar(SaldoEstoque s) throws SQLException;
    public ArrayList<SaldoEstoque> filter(SaldoEstoque sald) throws SQLException;
    void deletar(int id) throws SQLException;
}
