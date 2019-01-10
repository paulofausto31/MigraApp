/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.comodato;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ComodatoRN {
    ComodatoDAO comodatoDAO;

    public ComodatoRN(Context ctx){
        comodatoDAO = new DAOFactory().criaComodatoDAO(ctx);
    }

    public ArrayList<Comodato> pesquisar(Comodato l) throws SQLException{
        return this.comodatoDAO.pesquisar(l);
    }
/*
    public ArrayList<Comodato> getRouteForSalesMan(String codSalesMan) throws SQLException{
        return this.comodatoDAO.getRouteForSalesMan(codSalesMan);
    }
  */
    public void salvar(Comodato l) throws SQLException{
        this.comodatoDAO.salvar(l);
    }
}
