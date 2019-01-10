/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.contasReceber;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgment;

/**
 *
 * @author ydxpaj
 */

public class ContReceb {
    private long id;
    private String codVenda;
    private String formPg;//forma de pagamento
    private String numTitulo;

    private double valor;
    private double vOriginal;
    private double codCliente;
    private String dataEmissao;
    private String dataVenci;
    private long vendedor;

    private Cliente cliente;
    private FormPgment formPgment;

    public ContReceb(){
        setCliente(new Cliente());
        setFormPgment(new FormPgment());
        setvOriginal(0);
        setNumTitulo("");
        setDataEmissao("");
        setDataVenci("");
        setFormPg("");
    }

    /**
     * adicionado na versão 5
     * @return o valor original anted de qualquer baixa no contas a receber
     */
    public double getvOriginal() {
        return vOriginal;
    }

    public void setvOriginal(double vOriginal) {
        this.vOriginal = vOriginal;
    }

    public long getVendedor() {
        return vendedor;
    }

    public void setVendedor(long vendedor) {
        this.vendedor = vendedor;
    }

    public String getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(String codVenda) {
        this.codVenda = codVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public FormPgment getFormPgment() {
        return formPgment;
    }

    public void setFormPgment(FormPgment formPgment) {
        this.formPgment = formPgment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(double codCliente) {
        this.codCliente = codCliente;
    }

    public String getNumTitulo() {
        return numTitulo;
    }

    public void setNumTitulo(String numTitulo) {
        this.numTitulo = numTitulo;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataVenci() {
        return dataVenci;
    }

    public void setDataVenci(String dataVenci) {
        this.dataVenci = dataVenci;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormPg() {
        return formPg;
    }

    public void setFormPg(String formPg) {
        this.formPg = formPg;
    }
}
