/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.registro;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class RegistroRN {
    RegistroDAO RegistroDAO;
    
    public RegistroRN(Context ctx){

        RegistroDAO = new DAOFactory().criaRegistro(ctx);
    }
    
    public ArrayList<Registro> pesquisar(Registro prod) throws SQLException{
        return this.RegistroDAO.pesquisar(prod);
    }

    public Registro getRegistro() throws SQLException{
        return this.RegistroDAO.getRegistro();
    }

    public void update(Registro r) throws SQLException{
        this.RegistroDAO.update(r);
    }

    public void salvar(Registro prod) throws SQLException {
        this.RegistroDAO.salvar(prod);
    }
}
