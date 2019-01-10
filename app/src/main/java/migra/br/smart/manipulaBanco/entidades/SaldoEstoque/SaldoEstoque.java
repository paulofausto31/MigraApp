package migra.br.smart.manipulaBanco.entidades.SaldoEstoque;

import android.content.ContentValues;

/**
 * Created by droidr2d2 on 29/11/2016.
 */
public class SaldoEstoque {
    /*
    "id longeger primary key autoincrement, longeger deposito, " +
                    "longeger produto, longeger saldo"+
     */
    long id;
    long deposito;
    String produto;
    double saldo;
    ContentValues values;

    public SaldoEstoque(){
        values = new ContentValues();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.values.put("id", id);
        this.id = id;
    }

    public long getDeposito() {
        return deposito;
    }

    public void setDeposito(long deposito) {
        this.values.put("deposito", deposito);
        this.deposito = deposito;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.values.put("produto", produto);
        this.produto = produto;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.values.put("saldo", saldo);
        this.saldo = saldo;
    }
}