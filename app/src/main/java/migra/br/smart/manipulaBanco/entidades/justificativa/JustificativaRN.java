/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.justificativa;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class JustificativaRN {
    JustificativaDAO justificativaDAO;

    public JustificativaRN(Context ctx){
        justificativaDAO = new DAOFactory().criaJustificaDAO(ctx);
    }

    public ArrayList<Justificativa> pesquisar(Justificativa l) throws SQLException{
        return this.justificativaDAO.pesquisar(l);
    }

    public ArrayList<Justificativa> filterDescri(Justificativa obj) throws SQLException{
        return this.justificativaDAO.filterDescri(obj);
    }
    
    public void salvar(Justificativa l) throws SQLException{
        this.justificativaDAO.salvar(l);
    }
}
