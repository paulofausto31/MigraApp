package migra.br.smart.manipulaBanco.entidades.empresas;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by r2d2 on 4/12/18.
 */

public interface EmpresaDAO {
    public void save(Empresa empresa) throws SQLException;
    public ArrayList<Empresa> list(Empresa emp) throws SQLException;
    public Empresa getForId(int id) throws SQLException;
    public Empresa getForCnpj(String cnpj) throws SQLException;
    public void update(Empresa empresa) throws SQLException;
    public void delete(int id) throws SQLException;
}
