/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.justificativa;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface JustificativaDAO {
    void salvar(Justificativa l) throws SQLException;
    ArrayList<Justificativa> pesquisar(Justificativa l) throws SQLException;
    ArrayList<Justificativa> filterDescri(Justificativa obj) throws SQLException;
    void deletar(int id) throws SQLException;
}