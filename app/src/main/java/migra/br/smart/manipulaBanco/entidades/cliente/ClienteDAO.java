/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.cliente;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ClienteDAO {
    public void salvar(Cliente sgeClie) throws SQLException;
    public void update(Cliente c) throws SQLException;
    public ArrayList<Cliente> pesquisar(Cliente sgeClie) throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
}
