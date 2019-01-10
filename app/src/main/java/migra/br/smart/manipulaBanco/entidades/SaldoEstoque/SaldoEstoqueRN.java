/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.SaldoEstoque;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class SaldoEstoqueRN {
    SaldoEstoqueDAO SaldoEstoqueDAO;

    public SaldoEstoqueRN(Context ctx){

        SaldoEstoqueDAO = new DAOFactory().criaSaldoEstoqueDAO(ctx);
    }

    public ArrayList<SaldoEstoque> pesquisar(SaldoEstoque s) throws SQLException{
        return this.SaldoEstoqueDAO.pesquisar(s);
    }

    public ArrayList<SaldoEstoque> filter(SaldoEstoque s) throws SQLException{
        return this.SaldoEstoqueDAO.filter(s);
    }

    public void salvar(SaldoEstoque s) throws SQLException{
        this.SaldoEstoqueDAO.salvar(s);
    }

}
