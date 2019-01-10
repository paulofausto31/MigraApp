/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.formaPagamento;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface FormPgmentDAO {
    public void salvar(FormPgment form) throws SQLException;
    public ArrayList<FormPgment> pesquisar(FormPgment form) throws SQLException;
    public ArrayList<FormPgment> filtrarForCod(FormPgment obj) throws SQLException;
    public ArrayList<String> listaFormPagamento() throws SQLException;
    public void deletar(int sr_recno) throws SQLException;
}