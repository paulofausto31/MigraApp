/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.erro;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ErroDAO {
    void salvar(Erro l) throws SQLException;
    ArrayList<Erro> pesquisar(Erro l) throws SQLException;
    ArrayList<Erro> filterDescri(Erro obj) throws SQLException;
    void deletar(int id) throws SQLException;
}