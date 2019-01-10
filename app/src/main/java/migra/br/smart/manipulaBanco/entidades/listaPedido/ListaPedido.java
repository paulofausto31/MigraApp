/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.listaPedido;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.vendedor.Vendedor;

public class ListaPedido {

    private long id;
    //public int idPedido;
    public long idPedido;
    private double codVendedor;
    private double codCli;
    private long rota;
    private String idItem;
    private String deletar;
    private Pedido pedido;
    private Vendedor vendedor;
    private Cliente cliente;
    private ItemLista itemLista;
    private Produto produto;
    private int seqVist_id;

    private ContentValues values;

    public ListaPedido(){
        setValues(new ContentValues());
        setPedido(new Pedido());
        setVendedor(new Vendedor());
        setCliente(new Cliente());
        setItemLista(new ItemLista());
        setDeletar("n");
        setProduto(new Produto());
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public long getRota() {
        return rota;
    }

    public void setRota(long rota) {
        this.values.put("rota", rota);
        this.rota = rota;
    }

    public int getSeqVist_id() {
        return seqVist_id;
    }

    public void setSeqVist_id(int seqVist_id) {
        this.values.put("seqVist_id", seqVist_id);
        this.seqVist_id = seqVist_id;
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public String getDeletar() {
        return deletar;
    }

    public void setDeletar(String deletar) {
        this.deletar = deletar;
        this.getValues().put("deletar", deletar);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public ItemLista getItemLista() {
        return itemLista;
    }

    public void setItemLista(ItemLista itemLista) {
        this.itemLista = itemLista;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        this.values.put("id", id);
    }

    public long getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
        this.values.put("idPedido", idPedido);
    }

    public double getCodVendedor() {
        return this.codVendedor;
    }

    public void setCodVendedor(double codVendedor) {
        this.codVendedor = codVendedor;
        this.values.put("codVendedor", codVendedor);
    }

    public double getCodCli() {
        return this.codCli;
    }

    public void setCodCli(double codCli) {
        this.codCli = codCli;
        this.values.put("codCli", codCli);
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
        getItemLista().setId(idItem);
        this.values.put("idItem", idItem);
    }
}
