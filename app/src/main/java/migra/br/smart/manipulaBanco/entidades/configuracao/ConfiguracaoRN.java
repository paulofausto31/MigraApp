/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configuracao;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ConfiguracaoRN {
    ConfiguracaoDAO conLocalDAO;
    
    public ConfiguracaoRN(Context ctx){
        conLocalDAO = new DAOFactory().criaConfiguracao(ctx);
    }
    
    public ArrayList<Configuracao> pesquisar(Configuracao conLocal) throws SQLException{
        return this.conLocalDAO.pesquisar(conLocal);
    }
    
    public void salvar(Configuracao sfePrco) throws SQLException{
        this.conLocalDAO.salvar(sfePrco);
    }

    public void atualizar(Configuracao conLocal) throws SQLException{
        this.conLocalDAO.atualizar(conLocal);
    }

    public void deletar(int id) throws SQLException{
        this.conLocalDAO.deletar(id);
    }
}
