/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.registro;

import android.content.ContentValues;

/**
 *
 * @author ydxpaj
 */

public class Registro {
    private int id;
    private String key;
    private String cnpjEmpresa;
    private long data_expira;
    private long ultima_data;
    ContentValues values;

    public Registro(){
        setValues(new ContentValues());
        setKey("");
        setCnpjEmpresa("");
        setId(1);
        setData_expira(0);
        setUltima_data(0);
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
        this.values.put("cnpjEmpresa", cnpjEmpresa);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.values.put("id", id);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        this.values.put("key", key);
    }

    public long getData_expira() {
        return data_expira;
    }

    public void setData_expira(long data_expira) {
        this.data_expira = data_expira;
        this.values.put("data_expira", data_expira);
    }

    public long getUltima_data() {
        return ultima_data;
    }

    public void setUltima_data(long ultima_data) {
        this.ultima_data = ultima_data;
        this.values.put("ultima_data", ultima_data);
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }
}