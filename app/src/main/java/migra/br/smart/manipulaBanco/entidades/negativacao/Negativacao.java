package migra.br.smart.manipulaBanco.entidades.negativacao;

import android.content.ContentValues;

import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.justificativa.Justificativa;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.vendedor.Vendedor;

/**
 * Created by r2d2 on 24/04/17.
 */

public class Negativacao {
    private int id;
    private long dataInicio;
    private long dataFim;//somente para consulta, não tem coluna para esta variavel no banco de dados
    private String hora;
    private double latitude;
    private double longitude;
    private int seqVisitId;
    private long codRota;
    private double codCli;
    private String codJustf;
    private String status;

    private int idEmpresa;
    private Empresa empresa;

    private Cliente cliente;
    private Rota rota;
    private Vendedor vendedor;
    private Justificativa justificativa;

    private ContentValues values;

    public Negativacao(){
        setCliente(new Cliente());
        setRota(new Rota());
        setVendedor(new Vendedor());
        setValues(new ContentValues());
        setDataInicio(0);
        setDataFim(900000000000000000l);
        setStatus("");
        setJustificativa(new Justificativa());
        setEmpresa(new Empresa());
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.values.put("idEmpresa", idEmpresa);
        this.idEmpresa = idEmpresa;
    }

    public Justificativa getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(Justificativa justificativa) {
        this.justificativa = justificativa;
    }

    public long getDataFim() {
        return dataFim;
    }

    public void setDataFim(long dataFim) {
        this.dataFim = dataFim;
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.values.put("status", status);
        this.status = status;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.values.put("id", id);
        this.id = id;
    }

    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.values.put("data", dataInicio);
        this.dataInicio = dataInicio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.values.put("hora", hora);
        this.hora = hora;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.values.put("latitude", latitude);
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.values.put("longitude", longitude);
        this.longitude = longitude;
    }

    public int getSeqVisitId() {
        return seqVisitId;
    }

    public void setSeqVisitId(int seqVisitId) {
        this.values.put("seqVist_id", seqVisitId);
        this.seqVisitId = seqVisitId;
    }

    public long getCodRota() {
        return codRota;
    }

    public void setCodRota(long codRota) {
        getRota().setCodigo(codRota);
        this.values.put("rota", codRota);
        this.codRota = codRota;
    }

    public double getCodCli() {
        return codCli;
    }

    public void setCodCli(double codCli) {
        getCliente().setCodigo(codCli);
        this.values.put("cliente", codCli);
        this.codCli = codCli;
    }

    public String getCodJustf() {
        return codJustf;
    }

    public void setCodJustf(String codJustf) {
        this.values.put("justificativa", codJustf);
        this.codJustf = codJustf;
        getJustificativa().setCodigo(codJustf);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}