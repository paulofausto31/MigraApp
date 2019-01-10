package migra.br.smart.manipulaBanco.entidades.empresas;

import android.content.ContentValues;

/**
 * Created by r2d2 on 4/12/18.
 */

public class Empresa {
    private int id;
    private String cnpj;
    private String fantasia;
    private String rzSocial;
    private String db;//banco de dados da cada empresa
    private String tipo;//f = filial, m = matriz

    private ContentValues values;

    public Empresa(){
        setValues(new ContentValues());
        setCnpj("");
        setFantasia("");
        setRzSocial("");
        setDb("");
        setTipo("f");//tipo filial
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public String getTipo() {
        return tipo;
    }

    /**
     * f = filial, m = matriz
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.values.put("tipo", tipo);
        this.tipo = tipo;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.values.put("db", db);
        this.db = db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        getValues().put("id", id);
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        getValues().put("cnpj", cnpj);
        this.cnpj = cnpj;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        getValues().put("fantasia", fantasia);
        this.fantasia = fantasia;
    }

    public String getRzSocial() {
        return rzSocial;
    }

    public void setRzSocial(String rzSocial) {
        getValues().put("rzSocial", rzSocial);
        this.rzSocial = rzSocial;
    }
}
