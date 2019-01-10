/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configuracao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ConfiguracaoDAO {
    public void salvar(Configuracao conLocal) throws SQLException;
    public ArrayList<Configuracao> pesquisar(Configuracao config) throws SQLException;
    public void atualizar(Configuracao conLocal) throws SQLException;
    public void deletar(int id) throws SQLException;
}
