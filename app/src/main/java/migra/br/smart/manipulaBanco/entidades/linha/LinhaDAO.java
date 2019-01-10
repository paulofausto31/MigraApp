/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.linha;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface LinhaDAO {
    void salvar(Linha l) throws SQLException;
    ArrayList<Linha> pesquisar(Linha l) throws SQLException;
    void deletar(int id) throws SQLException;
}
