/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configLocal;

import android.content.ContentValues;

/**
 *
 * @author ydxpaj
 */
public class ConfigLocal {

    private String ip;
    private long porta;
    private long id;
    private String cnpjEmpresa;
    private String key;
    private String emailDestino;
    private int multBanco;//0 = false, 1 = true

    ContentValues contentValues;

    public ConfigLocal(){
        contentValues = new ContentValues();
        setIp("");
        setCnpjEmpresa("");
        setKey("");
        setMultBanco(0);
    }

    public int getMultBanco() {
        return multBanco;
    }

    public void setMultBanco(int multBanco) {
        this.contentValues.put("multBanco", multBanco);
        this.multBanco = multBanco;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.contentValues.put("emailDestino", emailDestino);
        this.emailDestino = emailDestino;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.contentValues.put("key", key);
        this.key = key;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
        contentValues.put("cnpjEmpresa", cnpjEmpresa);
    }

    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
        contentValues.put("id", id);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {

        this.ip = ip;
        contentValues.put("ip", ip);
    }

    public long getPorta() {
        return porta;
    }

    public void setPorta(long porta) {

        this.porta = porta;
        contentValues.put("porta", porta);
    }

    public ContentValues getContentValues(){
        return contentValues;
    }
}
