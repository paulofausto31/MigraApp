/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.valTotPed;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 *
 * @author ydxpaj
 */
public class ValTotPedRN {
    ValTotPedDAO ValTotPedDAO;

    public ValTotPedRN(Context ctx){
        ValTotPedDAO = new DAOFactory().criaValTotPedDAO(ctx);
    }

    public ArrayList<ValTotPed> pesquisar(ValTotPed l) throws SQLException{
        return this.ValTotPedDAO.pesquisar(l);
    }

    public void salvar(ValTotPed l) throws SQLException{
        this.ValTotPedDAO.salvar(l);
    }
}