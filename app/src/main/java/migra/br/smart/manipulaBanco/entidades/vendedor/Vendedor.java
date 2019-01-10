/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.vendedor;

import android.content.ContentValues;

/**
 *
 * @author ydxpaj
 */
public class Vendedor {

    private long id;
    public static String codigo;
    private String nome;
    private static ContentValues contentValues;

    public Vendedor(){
        contentValues = new ContentValues();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        contentValues.put("id", id);
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public static String getCodigo() {
        return Vendedor.codigo;
    }

    public static void setCodigo(String codigo) {
        Vendedor.codigo = codigo;
        contentValues.put("codigo", codigo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        contentValues.put("nome", nome);
    }
}
