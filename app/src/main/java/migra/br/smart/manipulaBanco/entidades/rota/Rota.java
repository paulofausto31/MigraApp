/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.rota;

/**
 *
 * @author ydxpaj
 */
public class Rota {
    /*
    codigo rota
descrção
vendedor rota
     */
    private int id;
    private long codigo;
    private String descricao;
    private double vendedor;
    private String diaVender;//dias da semana em que se pode vender, ex: dom=1000000. seg=0100000, etc

    public Rota(){
        setDescricao("");
    }

    public double getVendedor() {
        return vendedor;
    }

    public void setVendedor(double vendedor) {
        this.vendedor = vendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDiaVender() {
        return diaVender;
    }

    public void setDiaVender(String diaVender) {
        this.diaVender = diaVender;
    }
}
