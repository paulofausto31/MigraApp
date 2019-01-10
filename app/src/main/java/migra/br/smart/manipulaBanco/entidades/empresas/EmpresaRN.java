package migra.br.smart.manipulaBanco.entidades.empresas;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.manipulaBanco.factory.DAOFactory;

/**
 * Created by r2d2 on 4/12/18.
 */

public class EmpresaRN {

    private EmpresaDAO empresaDAO;
    private Context ctx;

    public EmpresaRN(Context ctx){
        this.ctx = ctx;
        empresaDAO = new DAOFactory().criaEmpresaDAO(ctx);
    }

    public ArrayList<Empresa> list(Empresa emp) throws SQLException {
        return empresaDAO.list(emp);
    }
    public Empresa getForId(int id) throws SQLException {
        return empresaDAO.getForId(id);
    }

    public Empresa getForCnpj(String cnpj) throws SQLException {
        return empresaDAO.getForCnpj(cnpj);
    }

    public void save(Empresa empresa) throws SQLException {
        empresaDAO.save(empresa);
    }
}
