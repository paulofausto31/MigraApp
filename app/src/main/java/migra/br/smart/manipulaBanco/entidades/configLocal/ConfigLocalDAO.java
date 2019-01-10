/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configLocal;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ConfigLocalDAO {
    public void salvar(ConfigLocal configLocal) throws SQLException;
    public ArrayList<ConfigLocal> pesquisar(ConfigLocal configLocal) throws SQLException;
    public void atualizar(ConfigLocal configLocal) throws SQLException;
    public void deletar(int id) throws SQLException;
}
