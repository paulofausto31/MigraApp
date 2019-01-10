/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.rota;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class RotaRN {
    RotaDAO rotaDAO;

    public RotaRN(Context ctx){
        rotaDAO = new DAOFactory().criaRotaDAO(ctx);
    }

    public ArrayList<Rota> pesquisar(Rota l) throws SQLException{
        return this.rotaDAO.pesquisar(l);
    }

    public ArrayList<Rota> getRouteForSalesMan(String codSalesMan) throws SQLException{
        return this.rotaDAO.getRouteForSalesMan(codSalesMan);
    }
    
    public void salvar(Rota l) throws SQLException{
        this.rotaDAO.salvar(l);
    }
}
