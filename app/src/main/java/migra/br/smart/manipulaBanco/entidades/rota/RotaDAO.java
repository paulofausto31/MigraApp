/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.rota;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface RotaDAO {
    public void salvar(Rota r) throws SQLException;
    public ArrayList<Rota> pesquisar(Rota r) throws SQLException;
    public ArrayList<Rota> getRouteForSalesMan(String codigo)throws SQLException;//salesman = vendedor
    public void update(Rota r);
    public void deletar(int id) throws SQLException;
}
