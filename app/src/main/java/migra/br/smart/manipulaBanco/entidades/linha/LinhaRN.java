/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.linha;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class LinhaRN {
    LinhaDAO linhaDAO;

    public LinhaRN(Context ctx){
        linhaDAO = new DAOFactory().criaLinhaDAO(ctx);
    }

    public ArrayList<Linha> pesquisar(Linha l) throws SQLException{
        return this.linhaDAO.pesquisar(l);
    }
    
    public void salvar(Linha l) throws SQLException{
        this.linhaDAO.salvar(l);
    }
}
