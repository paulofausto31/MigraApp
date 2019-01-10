/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configLocal;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ConfigLocalRN {
    ConfigLocalDAO configLocalDAO;
    
    public ConfigLocalRN(Context ctx){
        configLocalDAO = new DAOFactory().criaConLocal(ctx);
    }
    
    public ArrayList<ConfigLocal> pesquisar(ConfigLocal configLocal) throws SQLException{
        return this.configLocalDAO.pesquisar(configLocal);
    }
    
    public void salvar(ConfigLocal sfePrco) throws SQLException{
        this.configLocalDAO.salvar(sfePrco);
    }

    public void atualizar(ConfigLocal configLocal) throws SQLException{
        this.configLocalDAO.atualizar(configLocal);
    }

    public void deletar(int id) throws SQLException{
        this.configLocalDAO.deletar(id);
    }
}
