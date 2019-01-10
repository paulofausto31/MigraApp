/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.formaPagamento;

/**
 *
 * @author ydxpaj
 */
public class FormPgment {
    private long id;
    private String codigo;
    private String descricao;
    private String tipo;
    private String prazoPadrao;
    private String consideraCredito;
    private String permiteDescont;
    private double jurus;
    private double multa;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrazoPadrao() {
        return prazoPadrao;
    }

    public void setPrazoPadrao(String prazoPadrao) {
        this.prazoPadrao = prazoPadrao;
    }

    public String getConsideraCredito() {
        return consideraCredito;
    }

    public void setConsideraCredito(String consideraCredito) {
        this.consideraCredito = consideraCredito;
    }

    public String getPermiteDescont() {
        return permiteDescont;
    }

    public void setPermiteDescont(String permiteDescont) {
        this.permiteDescont = permiteDescont;
    }

    public double getJurus() {
        return jurus;
    }

    public void setJuros(double jurus) {
        this.jurus = jurus;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public FormPgment(){
        setDescricao("");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
