/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Preco;

/**
 *
 * @author ydxpaj
 */
public class Preco {

    private long id;
    private String codProd;
    private String vendeAVista;
    private double valor;

    public Preco(){
        setVendeAVista("");
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getVendeAVista() {
        return vendeAVista;
    }

    public void setVendeAVista(String vendeAVista) {
        this.vendeAVista = vendeAVista;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
