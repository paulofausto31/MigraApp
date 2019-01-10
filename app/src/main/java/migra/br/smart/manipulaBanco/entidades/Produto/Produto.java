/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.Produto;

import android.util.Log;

import java.util.ArrayList;

import migra.br.smart.manipulaBanco.entidades.Preco.Preco;

/**
 *
 * @author ydxpaj
 */
public class Produto {
    private int id;
    private String codigo;
    private String codBarras;
    private String nome;
    private String linha;
    private String subLinha;
    private String grupo;
    private String PVENDA1;
    private String PVENDA2;
    private String PVENDA3;
    private String PVENDA4;
    private String PVENDA5;
    private String PVENDA6;
    private String PVENDA7;
    private String PVENDA8;
    private String PVENDA9;
    private String pVendSelect;
    private double ALIQ_IPI;
    private String UNIDADE_ARMAZENAMENTO;
    private String unidadeVenda;
    private String APLICACAO;
    private String CEST;
    private String CFOP;
    private String COD_ANP;
    private String DESCONTO_MAXIMO;
    private String EX_IPI;
    private String VENDE_FRACIONADO;
    private String GENERO;
    private String ICMS_COMPRA;
    private String IRPJ;
    private String PIS;
    private String CONTROLA_LOTE;
    private String MARCA;
    private String MODELO;
    private String MVA;
    private String CONTROLE_NUMERO_DE_SERIE;
    private String ORIGEM;
    private String PESO_BRUTO;
    private String PESO_LIQUIDO;
    private String CST;
    private String TIPO;
    private String CODIGO_TRIBUTACAO;
    private String RAZAO_DE_VENDA_EM_MULTIPLO;
    private String PRECO_DE_COMPRA;

    private double saldo;//não tem
    private String fornecedor;//não tem
    private double armazenamento;

    private String promo1;
    private String promo2;
    private String promo3;
    private String promo4;
    private String promo5;
    private String promo6;
    private String promo7;
    private String promo8;
    private String promo9;

    private double qtdArmazenamento;//quantidade armazenada no pacote, caixa, etc
    private String unArmazena;//unidade de armazenamento

    private long valPromo;

    private Preco preco;
    private ArrayList<Preco> precos;
    private ArrayList<String> valores;
    ArrayList<String> vPromo;

    public Produto(){
        setCodigo("");
        setNome("");
        setUnidadeVenda("");
        setSaldo(0);
        setLinha("");
        setFornecedor("");
        precos =  new ArrayList<>();
        valores = new ArrayList<>();
        vPromo = new ArrayList<String>();
        setPVENDA1("0");
        setPVENDA2("0");
        setPVENDA3("0");
        setPVENDA4("0");
        setPVENDA5("0");
        setPVENDA6("0");
        setPVENDA7("0");
        setPVENDA8("0");
        setPVENDA9("0");
        setpVendSelect("0");

        setPromo1("0");
        setPromo2("0");
        setPromo3("0");
        setPromo4("0");
        setPromo5("0");
        setPromo6("0");
        setPromo7("0");
        setPromo8("0");
        setPromo9("0");

        setValPromo(0);
        setVENDE_FRACIONADO("N");
        setQtdArmazenamento(0);
        setUnArmazena("");//unidade de armazenamento
//        setPreco(new Preco());
    }

    /**
     * OBTÉM UNIDADE DE ARMAZENAMENTO
     * @return
     */
    public String getUnArmazena() {
        return unArmazena;
    }

    /**
     * DEFINE UNIDADE DE ARMAZENAMENTO
     * @param unArmazena
     */
    public void setUnArmazena(String unArmazena) {
        this.unArmazena = unArmazena;
    }

    public double getQtdArmazenamento() {
        return qtdArmazenamento;
    }

    public void setQtdArmazenamento(double qtdArmazenamento) {
        this.qtdArmazenamento = qtdArmazenamento;
    }

    public long getValPromo() {
        return valPromo;
    }

    public void setValPromo(long valPromo) {
        this.valPromo = valPromo;
    }

    public String getPromo1() {
        return promo1;
    }

    public void setPromo1(String promo1) {
        if(promo1.equals("")){
            this.promo1 = "0";
        }else{
            this.promo1 = promo1;
        }

    }

    public String getPromo2() {
        return promo2;
    }

    public void setPromo2(String promo2) {
        if(promo2.equals("")){
            this.promo2 = "0";
        }else{
            this.promo2 = promo2;
        }

    }

    public String getPromo3() {
        return promo3;
    }

    public void setPromo3(String promo3) {
        if(promo3.equals("")){
            this.promo3 = "0";
        }else{
            this.promo3 = promo3;
        }

    }

    public String getPromo4() {
        return promo4;
    }

    public void setPromo4(String promo4) {
        if(promo4.equals("")){
            this.promo4 = "0";
        }else{
            this.promo4 = promo4;
        }

    }

    public String getPromo5() {
        return promo5;
    }

    public void setPromo5(String promo5) {
        if(promo5.equals("")){
            this.promo5 = "0";
        }else{
            this.promo5 = promo5;
        }

    }

    public String getPromo6() {
        return promo6;
    }

    public void setPromo6(String promo6) {
        if(promo6.equals("")){
            this.promo6 = "0";
        }else{
            this.promo6 = promo6;
        }

    }

    public String getPromo7() {
        return promo7;
    }

    public void setPromo7(String promo7) {
        if(promo7.equals("")){
            this.promo7 = "0";
        }else{
            this.promo7 = promo7;
        }

    }

    public String getPromo8() {
        return promo8;
    }

    public void setPromo8(String promo8) {
        if(promo8.equals("")){
            this.promo8 = "0";
        }else{
            this.promo8 = promo8;
        }

    }

    public String getPromo9() {
        return promo9;
    }

    public void setPromo9(String promo9) {
        if(promo9.equals("")){
            this.promo9 = "0";
        }else{
            this.promo9 = promo9;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getValorePromocao(){

        vPromo.clear();


        if(Double.parseDouble(getPromo1()) > 0){
            vPromo.add(getPromo1());
        }

        if(Double.parseDouble(getPromo2()) > 0){
            vPromo.add(getPromo2());
        }

        if(Double.parseDouble(getPromo3()) > 0){
            vPromo.add(getPromo3());
        }

        if(Double.parseDouble(getPromo4()) > 0){
            vPromo.add(getPromo4());
        }
        if(Double.parseDouble(getPromo5()) > 0){
            vPromo.add(getPromo5());
        }
        if(Double.parseDouble(getPromo6()) > 0){
            vPromo.add(getPromo6());
        }
        if(Double.parseDouble(getPromo7()) > 0){
            vPromo.add(getPromo7());
        }
        if(Double.parseDouble(getPromo8()) > 0){
            vPromo.add(getPromo8());
        }
        if(Double.parseDouble(getPromo9()) > 0){
            vPromo.add(getPromo9());
        }

        return vPromo;
    }

    public ArrayList<String> getValores(){
        valores.clear();

        if(Double.parseDouble(getPVENDA1()) > 0){
            valores.add(getPVENDA1());
        }
        if(Double.parseDouble(getPVENDA2()) > 0){
            valores.add(getPVENDA2());
        }
        if(Double.parseDouble(getPVENDA3()) > 0){
            valores.add(getPVENDA3());
        }
        if(Double.parseDouble(getPVENDA4()) > 0){
            valores.add(getPVENDA4());
        }
        if(Double.parseDouble(getPVENDA5()) > 0){
            valores.add(getPVENDA5());
        }
        if(Double.parseDouble(getPVENDA6()) > 0){
            valores.add(getPVENDA6());
        }
        if(Double.parseDouble(getPVENDA7()) > 0){
            valores.add(getPVENDA7());
        }
        if(Double.parseDouble(getPVENDA8()) > 0){
            valores.add(getPVENDA8());
        }
        if(Double.parseDouble(getPVENDA9()) > 0){
            valores.add(getPVENDA9());
        }
        if(Double.parseDouble(getPromo1()) > 0){
            valores.add("p"+getPromo1());
        }

        if(Double.parseDouble(getPromo2()) > 0){
            valores.add("p"+getPromo2());
        }

        if(Double.parseDouble(getPromo3()) > 0){
            valores.add("p"+getPromo3());
        }

        if(Double.parseDouble(getPromo4()) > 0){
            valores.add("p"+getPromo4());
        }
        if(Double.parseDouble(getPromo5()) > 0){
            valores.add("p"+getPromo5());
        }
        if(Double.parseDouble(getPromo6()) > 0){
            valores.add("p"+getPromo6());
        }
        if(Double.parseDouble(getPromo7()) > 0){
            valores.add("p"+getPromo7());
        }
        if(Double.parseDouble(getPromo8()) > 0){
            valores.add("p"+getPromo8());
        }
        if(Double.parseDouble(getPromo9()) > 0){
            valores.add("p"+getPromo9());
        }

        /*
        valores.add(getPVENDA1());
        valores.add(getPVENDA2());
        valores.add(getPVENDA3());
        valores.add(getPVENDA4());
        valores.add(getPVENDA5());
        valores.add(getPVENDA6());
        valores.add(getPVENDA7());
        valores.add(getPVENDA8());
        valores.add(getPVENDA9());
        */
        return valores;
    }

    public ArrayList<Preco> getListaPreco() {
        return precos;
    }

    public void setPrecos(ArrayList<Preco> listaPreco) {
        this.precos = listaPreco;
        for(Preco p:precos){
            valores.add(String.valueOf(p.getValor()));
        }
    }

    public String getpVendSelect() {
        return pVendSelect;
    }

    public void setpVendSelect(String pVendSelect) {
        this.pVendSelect = pVendSelect;
    }

    public Preco getPreco() {
        return preco;
    }

    public void setPreco(Preco preco) {
        this.preco = preco;
    }

    public double getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(double armazenamento) {
        this.armazenamento = armazenamento;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getCodigo() {

        return codigo;
    }

    public void setCodigo(String codigo) {
//        getPreco().setCodProd(codigo);
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidadeVenda() {
        return unidadeVenda;
    }

    public void setUnidadeVenda(String unidadeVenda) {
        this.unidadeVenda = unidadeVenda;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }
/*
    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
*/
    public String getSubLinha() {
        return subLinha;
    }

    public void setSubLinha(String subLinha) {
        this.subLinha = subLinha;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getPVENDA1() {
        return PVENDA1;
    }

    public void setPVENDA1(String PVENDA1) {
        if(PVENDA1.equals("")) {
            this.PVENDA1 = "0";
        }else{
            this.PVENDA1 = PVENDA1;
        }
    }

    public String getPVENDA2() {
        return PVENDA2;
    }

    public void setPVENDA2(String PVENDA2) {
        if(PVENDA2.equals("")) {
            this.PVENDA2 = "0";
        }else{
            this.PVENDA2 = PVENDA2;
        }
    }

    public String getPVENDA3() {
        return PVENDA3;
    }

    public void setPVENDA3(String PVENDA3) {
        if(PVENDA3.equals("")) {
            this.PVENDA3 = "0";
        }else{
            this.PVENDA3 = PVENDA3;
        }
    }

    public String getPVENDA4() {
        return PVENDA4;
    }

    public void setPVENDA4(String PVENDA4) {
        if(PVENDA4.equals("")) {
            this.PVENDA4 = "0";
        }else{
            this.PVENDA4 = PVENDA4;
        }
    }

    public String getPVENDA5() {
        return PVENDA5;
    }

    public void setPVENDA5(String PVENDA5) {
        if(PVENDA5.equals("")) {
            this.PVENDA5 = "0";
        }else{
            this.PVENDA5 = PVENDA5;
        }
    }

    public String getPVENDA6() {
        return PVENDA6;
    }

    public void setPVENDA6(String PVENDA6) {
        if(PVENDA6.equals("")) {
            this.PVENDA6 = "0";
        }else{
            this.PVENDA6 = PVENDA6;
        }
    }

    public String getPVENDA7() {
        return PVENDA7;
    }

    public void setPVENDA7(String PVENDA7) {
        if(PVENDA7.equals("")) {
            this.PVENDA7 = "0";
        }else{
            this.PVENDA7 = PVENDA7;
        }
    }

    public String getPVENDA8() {
        return PVENDA8;
    }

    public void setPVENDA8(String PVENDA8) {
        if(PVENDA8.equals("")) {
            this.PVENDA8 = "0";
        }else{
            this.PVENDA8 = PVENDA8;
        }
    }

    public String getPVENDA9() {
        return PVENDA9;
    }

    public void setPVENDA9(String PVENDA9) {
        if(PVENDA9.equals("")) {
            this.PVENDA9 = "0";
        }else{
            this.PVENDA9 = PVENDA9;
        }
    }

    public double getALIQ_IPI() {
        return ALIQ_IPI;
    }

    public void setALIQ_IPI(double ALIQ_IPI) {
        this.ALIQ_IPI = ALIQ_IPI;
    }

    public String getAPLICACAO() {
        return APLICACAO;
    }

    public void setAPLICACAO(String APLICACAO) {
        this.APLICACAO = APLICACAO;
    }

    public String getCEST() {
        return CEST;
    }

    public void setCEST(String CEST) {
        this.CEST = CEST;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    public String getCOD_ANP() {
        return COD_ANP;
    }

    public void setCOD_ANP(String COD_ANP) {
        this.COD_ANP = COD_ANP;
    }

    public String getDESCONTO_MAXIMO() {
        return DESCONTO_MAXIMO;
    }

    public void setDESCONTO_MAXIMO(String DESCONTO_MAXIMO) {
        this.DESCONTO_MAXIMO = DESCONTO_MAXIMO;
    }

    public String getEX_IPI() {
        return EX_IPI;
    }

    public void setEX_IPI(String EX_IPI) {
        this.EX_IPI = EX_IPI;
    }

    public String getVENDE_FRACIONADO() {
        return VENDE_FRACIONADO;
    }

    public void setVENDE_FRACIONADO(String VENDE_FRACIONADO) {
        this.VENDE_FRACIONADO = VENDE_FRACIONADO;
    }

    public String getGENERO() {
        return GENERO;
    }

    public void setGENERO(String GENERO) {
        this.GENERO = GENERO;
    }

    public String getICMS_COMPRA() {
        return ICMS_COMPRA;
    }

    public void setICMS_COMPRA(String ICMS_COMPRA) {
        this.ICMS_COMPRA = ICMS_COMPRA;
    }

    public String getIRPJ() {
        return IRPJ;
    }

    public void setIRPJ(String IRPJ) {
        this.IRPJ = IRPJ;
    }

    public String getPIS() {
        return PIS;
    }

    public void setPIS(String PIS) {
        this.PIS = PIS;
    }

    public String getCONTROLA_LOTE() {
        return CONTROLA_LOTE;
    }

    public void setCONTROLA_LOTE(String CONTROLA_LOTE) {
        this.CONTROLA_LOTE = CONTROLA_LOTE;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public String getMVA() {
        return MVA;
    }

    public void setMVA(String MVA) {
        this.MVA = MVA;
    }

    public String getCONTROLE_NUMERO_DE_SERIE() {
        return CONTROLE_NUMERO_DE_SERIE;
    }

    public void setCONTROLE_NUMERO_DE_SERIE(String CONTROLE_NUMERO_DE_SERIE) {
        this.CONTROLE_NUMERO_DE_SERIE = CONTROLE_NUMERO_DE_SERIE;
    }

    public String getORIGEM() {
        return ORIGEM;
    }

    public void setORIGEM(String ORIGEM) {
        this.ORIGEM = ORIGEM;
    }

    public String getPESO_BRUTO() {
        return PESO_BRUTO;
    }

    public void setPESO_BRUTO(String PESO_BRUTO) {
        this.PESO_BRUTO = PESO_BRUTO;
    }

    public String getPESO_LIQUIDO() {
        return PESO_LIQUIDO;
    }

    public void setPESO_LIQUIDO(String PESO_LIQUIDO) {
        this.PESO_LIQUIDO = PESO_LIQUIDO;
    }

    public String getCST() {
        return CST;
    }

    public void setCST(String CST) {
        this.CST = CST;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getCODIGO_TRIBUTACAO() {
        return CODIGO_TRIBUTACAO;
    }

    public void setCODIGO_TRIBUTACAO(String CODIGO_TRIBUTACAO) {
        this.CODIGO_TRIBUTACAO = CODIGO_TRIBUTACAO;
    }

    public String getRAZAO_DE_VENDA_EM_MULTIPLO() {
        return RAZAO_DE_VENDA_EM_MULTIPLO;
    }

    public void setRAZAO_DE_VENDA_EM_MULTIPLO(String RAZAO_DE_VENDA_EM_MULTIPLO) {
        this.RAZAO_DE_VENDA_EM_MULTIPLO = RAZAO_DE_VENDA_EM_MULTIPLO;
    }

    public String getPRECO_DE_COMPRA() {
        return PRECO_DE_COMPRA;
    }

    public void setPRECO_DE_COMPRA(String PRECO_DE_COMPRA) {
        this.PRECO_DE_COMPRA = PRECO_DE_COMPRA;
    }

    public ArrayList<Preco> getPrecos() {
        return precos;
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }

    public String getUNIDADE_ARMAZENAMENTO() {
        return UNIDADE_ARMAZENAMENTO;
    }

    public void setUNIDADE_ARMAZENAMENTO(String UNIDADE_ARMAZENAMENTO) {
        this.UNIDADE_ARMAZENAMENTO = UNIDADE_ARMAZENAMENTO;
    }

}
