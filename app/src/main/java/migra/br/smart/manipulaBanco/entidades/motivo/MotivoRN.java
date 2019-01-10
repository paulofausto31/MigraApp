/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.motivo;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class MotivoRN {
    MotivoDAO linhaDAO;

    public MotivoRN(Context ctx){
        linhaDAO = new DAOFactory().criaMotivoDAO(ctx);
    }

    public ArrayList<Motivo> pesquisar(Motivo l) throws SQLException{
        return this.linhaDAO.pesquisar(l);
    }
    
    public void salvar(Motivo l) throws SQLException{
        this.linhaDAO.salvar(l);
    }
}
