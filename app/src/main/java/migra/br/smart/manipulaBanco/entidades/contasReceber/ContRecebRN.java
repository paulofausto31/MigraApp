/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.contasReceber;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ContRecebRN {
    ContRecebDAO contRecebDAO;

    public ContRecebRN(Context ctx){
        contRecebDAO = new DAOFactory().criacontRecebDAO(ctx);
    }

    public ArrayList<ContReceb> pesquisar(ContReceb contReceb) throws SQLException{
        return this.contRecebDAO.pesquisar(contReceb);
    }

    public ArrayList<ContReceb> searchForCli(double codCli) throws SQLException {
        return this.contRecebDAO.searchForCli(codCli);
    }
    
    public void salvar(ContReceb contReceb) throws SQLException{
        this.contRecebDAO.salvar(contReceb);
    }
}
