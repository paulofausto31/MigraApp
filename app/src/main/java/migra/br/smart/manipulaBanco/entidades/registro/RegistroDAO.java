/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.registro;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ydxpaj
 */
public interface RegistroDAO {
    public void salvar(Registro reg) throws SQLException;
    public Registro getRegistro() throws SQLException;
    public ArrayList<Registro> pesquisar(Registro reg) throws SQLException;
    public void update(Registro reg) throws SQLException;
 //   public ArrayList<ItemLista> pesquisarNome(ItemLista ItemLista) throws SQLException;
    public void close();
}
