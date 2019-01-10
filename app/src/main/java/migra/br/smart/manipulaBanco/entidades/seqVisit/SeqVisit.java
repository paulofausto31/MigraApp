/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisit;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;

/**
 *
 * @author ydxpaj
 */
public class SeqVisit {
    /*
    codRota codRota
    codCliente
    seqVisita
     */
    private int id;
    private long codRota;
    private double codCliente;
    private long seqVisit;
    private String status;
    private ContentValues values;

    private Rota rota;
    private Cliente cliente;
    private SeqVisitStatus seqVisitStatus;

    public SeqVisit(){

        setValues(new ContentValues());
        setCliente(new Cliente());
        setRota(new Rota());
        setSeqVisitStatus(new SeqVisitStatus());
        setStatus("");
    }

    public SeqVisitStatus getSeqVisitStatus() {
        return seqVisitStatus;
    }

    public void setSeqVisitStatus(SeqVisitStatus seqVisitStatus) {
        this.seqVisitStatus = seqVisitStatus;
    }

    public ContentValues getValues() {
        return values;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        values.put("status", status);
        this.status = status;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public long getSeqVisit() {
        return seqVisit;
    }

    public void setSeqVisit(long seqVisit) {
        this.seqVisit = seqVisit;
        this.values.put("seqVisit", seqVisit);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.values.put("id", id);
    }

    public long getCodRota() {
        return codRota;
    }

    public void setCodRota(long codRota) {
        this.codRota = codRota;
        this.values.put("rota", codRota);
        getRota().setCodigo(codRota);
    }

    public double getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(double codCliente) {
        this.codCliente = codCliente;
        this.values.put("cliente", codCliente);
        getCliente().setCodigo(codCliente);
    }
}
