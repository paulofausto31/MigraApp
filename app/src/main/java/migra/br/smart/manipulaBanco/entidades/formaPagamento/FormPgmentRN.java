/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.formaPagamento;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class FormPgmentRN {
    FormPgmentDAO formPgmentDAO;

    public FormPgmentRN(Context ctx){
        formPgmentDAO = new DAOFactory().criaformPgmentDAO(ctx);
    }

    public ArrayList<FormPgment> pesquisar(FormPgment form) throws SQLException{
        return this.formPgmentDAO.pesquisar(form);
    }

    /**
     * FILTRAR A FORMA DE PAGAMENTO PELO CODIGO
     * @param obj
     * @return
     * @throws SQLException
     */
    public ArrayList<FormPgment> filtrarForCod(FormPgment obj) throws SQLException {
        return this.formPgmentDAO.filtrarForCod(obj);
    }

    public ArrayList<String> listaFormPagamento() throws SQLException {
        return this.formPgmentDAO.listaFormPagamento();
    }
    public void salvar(FormPgment form) throws SQLException{
        this.formPgmentDAO.salvar(form);
    }
}
