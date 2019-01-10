/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.contasReceber;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ContRecebDAO {
    public void salvar(ContReceb contReceb) throws SQLException;
    public ArrayList<ContReceb> pesquisar(ContReceb contReceb) throws SQLException;
    public ArrayList<ContReceb> searchForCli(double codCli) throws SQLException;
    public void deletar(int id) throws SQLException;
}
