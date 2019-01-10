/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.cliente;

import android.content.ContentValues;

public class Cliente {
/*********************************usado no webservice**********************************************/
    private long id;
    public double codigo;
    private String fantasia;//fantasia fantasia
    private String rzSocial;//razao social
    private String endereco;
    private String cidade;
    private String bairro;
    private double limitCred;
    private double codVendedor;
/*********************************usado no webservice**********************************************/
    private String ultimaCompra;
    private double totalAtraso;
    private double totalDebito;
    private String telefone;
    private String formPagmento;
    private String condicPgment;
    private String cnpj;
    private String seqVisit;
    private String pedeHoraVisit;
    private long prazoPagamento;//prazo pagamento individual pode ser maior que o prazo maximo generico na classe Configuracao

    private String status;

    private ContentValues values;

    public Cliente(){
        setUltimaCompra("");
        setEndereco("");
        setFantasia("");
        setRzSocial("");
        setTelefone("");
        setFormPagmento("");
        setCondicPgment("");
        setCnpj("");
        setSeqVisit("");
        setCidade("");
        setBairro("");
        setPedeHoraVisit("");
        setPrazoPagamento(0);

        setValues(new ContentValues());
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    /**
     * obtem o prazo de pagamento individual especial do cliente, desconsidera o prazo maximo generico da classe Configuracao
     * @return
     */
    public long getPrazoPagamento() {
        return prazoPagamento;
    }

    /**
     * define o prazo de pagamento individual especial do cliente, desconsidera o prazo maximo generico da classe Configuracao
     * @param prazoPagamento
     */
    public void setPrazoPagamento(long prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public double getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(double codVendedor) {
        this.codVendedor = codVendedor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPedeHoraVisit(){
        return pedeHoraVisit;
    }

    public void setPedeHoraVisit(String pedeHoraVisit) {
        this.pedeHoraVisit = pedeHoraVisit;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSeqVisit() {
        return seqVisit;
    }

    public void setSeqVisit(String seqVisit) {
        this.seqVisit = seqVisit;
    }

    public double getLimitCred() {
        return limitCred;
    }

    public void setLimitCred(double limitCred) {
        this.limitCred = limitCred;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCondicPgment() {
        return condicPgment;
    }

    public void setCondicPgment(String condicPgment) {
        this.condicPgment = condicPgment;
    }

    public String getFormPagmento() {
        return formPagmento;
    }

    public void setFormPagmento(String formPagmento) {
        this.formPagmento = formPagmento;
    }

    public double getTotalAtraso() {
        return totalAtraso;
    }

    public double getTotalDebito() {
        return totalDebito;
    }

    public void setTotalDebito(double totalDebito) {
        this.totalDebito = totalDebito;
    }

    public String getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(String ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public void setTotalAtraso(double totalAtraso) {
        this.totalAtraso = totalAtraso;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public double getCodigo() {
        return this.codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getRzSocial() {
        return rzSocial;
    }

    public void setRzSocial(String razaoSocial) {
        this.rzSocial = razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
