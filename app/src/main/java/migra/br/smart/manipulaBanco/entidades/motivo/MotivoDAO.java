/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.motivo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface MotivoDAO {
    void salvar(Motivo l) throws SQLException;
    ArrayList<Motivo> pesquisar(Motivo l) throws SQLException;
    void deletar(int id) throws SQLException;
}
