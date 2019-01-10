/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.comodato;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface ComodatoDAO {
    public void salvar(Comodato r) throws SQLException;
    public ArrayList<Comodato> pesquisar(Comodato r) throws SQLException;
    //public ArrayList<Comodato> getRouteForSalesMan(String codigo)throws SQLException;
    public void update(Comodato r);
    public void deletar(int id) throws SQLException;
}
