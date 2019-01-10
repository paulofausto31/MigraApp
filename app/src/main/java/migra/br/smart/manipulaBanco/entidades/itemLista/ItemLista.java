/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.itemLista;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.Produto.Produto;

/**
 *
 * @author ydxpaj
 */
public class ItemLista {

    private String id;
    private double descAcrePercent;
    private double descAcreMone;
    private String codProd;
    private String deletar;
    private String qtd;
    private double total;
    private Produto produto;
    private ContentValues values;
    public long pVendSelect;
    private long qtdUn;
    //private String unVenda;//unidade de venda

    //private String unFrac;//unidade ou fracionado

    public ItemLista(){
        setProduto(new Produto());

        values = new ContentValues();
    }
/*
    public String getUnVenda() {
        return unVenda;
    }

    public void setUnVenda(String unVenda) {
        this.values.put("unVenda", unVenda);
        this.unVenda = unVenda;
    }
*/

    public long getQtdUn() {
        return qtdUn;
    }

    public void setQtdUn(long qtdUn) {
        this.values.put("qtdUn", qtdUn);
        this.qtdUn = qtdUn;
    }

    /**
     * unidade ou fracionado na quantidade do item
     * @return
     *
    public String getUnFrac() {
        return unFrac;
    }

    **
     * unidade ou fracionado na quantidade do item
     *
     *
    public void setUnFrac(String unFrac) {
        this.unFrac = unFrac;
        this.values.put("unFrac", unFrac);
    }
*/

    public long getpVendSelect() {
        return this.pVendSelect;
    }

    public void setpVendSelect(long pVendSelect) {
        this.pVendSelect = pVendSelect;
        this.values.put("pVendSelect", pVendSelect);
    }

    public String getDeletar() {
        return deletar;
    }

    public void setDeletar(String deletar) {
        this.deletar = deletar;
    }

    public double getDescAcreMone() {
        return descAcreMone;
    }

    public void setDescAcreMone(double descAcreMone) {
        this.descAcreMone = descAcreMone;
        values.put("descAcreMone", descAcreMone);
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        //values.put("id", id);
        this.id = id;
    }

    public double getDescAcrePercent() {
        return descAcrePercent;
    }

    public void setDescAcrePercent(double descAcrePercent) {
        this.descAcrePercent = descAcrePercent;
        values.put("descAcrePercent", descAcrePercent);
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
        getProduto().setCodigo(codProd);
        values.put("codProd", codProd);
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
        values.put("qtd", qtd);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        values.put("total", total);
    }
}
