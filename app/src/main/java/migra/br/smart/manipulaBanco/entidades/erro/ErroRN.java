/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.erro;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ErroRN {
    ErroDAO erroDAO;

    public ErroRN(Context ctx){
        erroDAO = new DAOFactory().erroDAO(ctx);
    }

    public ArrayList<Erro> pesquisar(Erro l) throws SQLException{
        return this.erroDAO.pesquisar(l);
    }

    public ArrayList<Erro> filterDescri(Erro obj) throws SQLException{
        return this.erroDAO.filterDescri(obj);
    }
    
    public void salvar(Erro l) throws SQLException{
        this.erroDAO.salvar(l);
    }
}
