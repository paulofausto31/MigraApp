/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.seqVisitStatus;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;

/**
 *
 * @author ydxpaj
 */
public class SeqVisitStatus {
    /*
    codRota codRota
    codCliente
    seqVisita
     */
    private String seq_id;
    private String codPed;
    private String status;
    private int idNegativa;
    private ContentValues values;

    public SeqVisitStatus(){
        setValues(new ContentValues());
        setStatus("");
        setCodPed("");
    }

    public int getIdNegativa() {
        return idNegativa;
    }

    public void setIdNegativa(int idNegativa) {
        this.idNegativa = idNegativa;
        values.put("idNegativa", idNegativa);
    }

    public String getCodPed() {
        return codPed;
    }

    public void setCodPed(String codPed) {
        values.put("codPed", codPed);
        this.codPed = codPed;
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

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public String getSeq_id() {
        return seq_id;
    }

    public void setSeq_id(String seq_id) {
        this.seq_id = seq_id;
        this.values.put("seq_id", seq_id);
    }
}
