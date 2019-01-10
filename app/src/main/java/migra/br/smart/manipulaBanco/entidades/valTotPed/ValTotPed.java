package migra.br.smart.manipulaBanco.entidades.valTotPed;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;

/**
 * Created by droidr2d2 on 07/03/2017.
 */
public class ValTotPed {
    private int id;
    private int idPed;
    private double total;

    private ContentValues values;

    private Pedido pedido;

    public ValTotPed(){
        setPedido(new Pedido());
        setValues(new ContentValues());
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.values.put("id", id);
        this.id = id;
    }

    public int getIdPed() {
        return idPed;
    }

    public void setIdPed(int idPed) {
        this.values.put("idPed", idPed);
        this.idPed = idPed;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.values.put("total", total);
        this.total = total;
    }
}