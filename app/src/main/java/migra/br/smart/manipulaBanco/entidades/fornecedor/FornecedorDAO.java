/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.fornecedor;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface FornecedorDAO {
    public void salvar(Fornecedor fornecedor) throws SQLException;
    public ArrayList<Fornecedor> pesquisar(Fornecedor fornecedor) throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
}
