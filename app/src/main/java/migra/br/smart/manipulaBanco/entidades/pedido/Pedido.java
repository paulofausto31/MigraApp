/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.pedido;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;

/**
 *
 * @author ydxpaj
 */
public class Pedido {

    //private int id;
    private long id;
    private double codCli;
    private String idFormPg;
    private long dataInicio;
    private String horaInicio;
    private long dataFim;
    private String horaFim;
    String status;
    private String obs;
    private long prazo;
    private double qtParcela;
    private double total;

    private int idEmpresa;
    private Empresa empresa;

    private int seqVist_id;
    private long rota;

    private String del;

    private String unFrac;//fracionado ou em unidade

    private Cliente cliente;

    private SeqVisit seqVisit;

    private ContentValues values;

    private double latitudeInicio, longitudeInicio, latitudeFim, longitudeFim, latitudeTransmit, longitudeTransmit;

    public Pedido(){
        this.values = new ContentValues();
        //setDataInicio("");
        setDataFim(900000000000000000l);
        setHoraFim("");
        setDataInicio(0l);
        setHoraInicio("");
        setIdFormPg("");
        setStatus("Aberto");
        setObs("");
        setDel("");
        setCliente(new Cliente());
        setSeqVisit(new SeqVisit());
        setUnFrac("");//unidade ou fracionado
        setEmpresa(new Empresa());
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.values.put("idEmpresa", idEmpresa);
        this.idEmpresa = idEmpresa;
    }

    /**
     * unidade ou fracionado
     *
     * @return
     */
    public String getUnFrac() {
        return unFrac;
    }

    /**
     * unidade ou fracionado
     * @param unFrac
     */
    public void setUnFrac(String unFrac) {
        this.unFrac = unFrac;
    }

    public SeqVisit getSeqVisit() {
        return seqVisit;
    }

    public void setSeqVisit(SeqVisit seqVisit) {
        this.seqVisit = seqVisit;
        setSeqVist_id(seqVisit.getId());
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.values.put("obs", obs);
        this.obs = obs;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.values.put("del", del);
        this.del = del;
    }

    public double getLatitudeInicio() {
        return latitudeInicio;
    }

    public void setLatitudeInicio(double latitudeInicio) {
        this.values.put("latitudeInicio", latitudeInicio);
        this.latitudeInicio = latitudeInicio;
    }

    public double getLongitudeInicio() {
        return longitudeInicio;
    }

    public void setLongitudeInicio(double longitudeInicio) {
        this.values.put("longitudeInicio", longitudeInicio);
        this.longitudeInicio = longitudeInicio;
    }

    public double getLatitudeFim() {
        return latitudeFim;
    }

    public void setLatitudeFim(double latitudeFim) {
        this.values.put("latitudeFim", latitudeFim);
        this.latitudeFim = latitudeFim;
    }

    public double getLongitudeFim() {
        return longitudeFim;
    }

    public void setLongitudeFim(double longitudeFim) {
        this.values.put("longitudeFim", longitudeFim);
        this.longitudeFim = longitudeFim;
    }

    public double getLatitudeTransmit() {
        return latitudeTransmit;
    }

    public void setLatitudeTransmit(double latitudeTransmit) {
        this.values.put("latitudeTransmit", latitudeTransmit);
        this.latitudeTransmit = latitudeTransmit;
    }

    public double getLongitudeTransmit() {
        return longitudeTransmit;
    }

    public void setLongitudeTransmit(double longitudeTransmit) {
        this.values.put("longitudeTransmit", longitudeTransmit);
        this.longitudeTransmit = longitudeTransmit;
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public int getSeqVist_id() {
        return seqVist_id;
    }

    public void setSeqVist_id(int seqVist_id) {
        this.values.put("seqVist_id", seqVist_id);
        this.seqVist_id = seqVist_id;
    }

    public long getRota() {
        return rota;
    }

    public void setRota(long rota) {
        this.values.put("rota", rota);
        this.rota = rota;
    }

    public double getCodCli() {
        return codCli;
    }

    public void setCodCli(double codCli) {
        this.codCli = codCli;
        this.values.put("codCli", codCli);
    }

    /**
     * VERIFICA SE O PEDIDO FOI TRANSMITIDO PARA O SERVIDOR
     * @return true se o pedido tiver sido transmitido
     * @return false caso contrario
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.values.put("status", status);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        values.put("total", total);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
        this.values.put("id", id);
    }

    public String getIdFormPg() {
        return idFormPg;
    }

    public void setIdFormPg(String idFormPg) {
        this.idFormPg = idFormPg;
        this.values.put("codFormpg", idFormPg);
    }

    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
        this.values.put("datInici", dataInicio);
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
        this.values.put("horaInic", horaInicio);
    }

    public long getDataFim() {
        return dataFim;
    }

    public void setDataFim(long dataFim) {
        this.dataFim = dataFim;
        this.values.put("datFim", dataFim);
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
        this.values.put("horaFim", horaFim);
    }

    public long getPrazo() {
        return prazo;
    }

    public void setPrazo(long prazo) {
        this.prazo = prazo;
        this.values.put("prazo", prazo);
    }

    public double getQtParcela() {
        return qtParcela;
    }

    public void setQtParcela(double qtParcela) {
        this.qtParcela = qtParcela;
        this.values.put("qtParcela", qtParcela);
    }
}