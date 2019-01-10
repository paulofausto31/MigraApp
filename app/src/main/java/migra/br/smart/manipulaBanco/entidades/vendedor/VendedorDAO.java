/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.vendedor;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface VendedorDAO {
    public void salvar(Vendedor sgeVend) throws SQLException;
    public ArrayList<Vendedor> pesquisar(Vendedor sgeVend) throws SQLException;
    public void atualizar(Vendedor sgeVend) throws SQLException;
    public void deletar(int id);
}
