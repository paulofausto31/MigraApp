/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.configuracao;

import android.content.ContentValues;

/**
 *
 * @author ydxpaj
 */
public class Configuracao {

    private double descontMax;
    private String key;
    private long id;
    private String vendePorDiaSemana;//permissao para vender por dia da semana
    private int filtraEstoque;
    private long maxParcelas;
    private String data;
    private long dataExpira;
    private long ultimaData;//ultima data menor ou igual a data de vencimento
    private String hora;
    private String mensagem;
    private long prazoMaxGeral;//prazo maximo do número de dias para pagamento
    private ContentValues values;

    public Configuracao(){
        setValues(new ContentValues());
        setKey("");
  /*      setControlaEstoque("");*/
        setDataCarga("");
        setHoraCarga("");
        setMensagem("");
        setMaxParcelas(0);
        setUltimaData(0);
        setDataExpira(0);
        setDescontMax(0);
        setVendePorDiaSemana("n");
        setPrazoMaxGeral(0);
    }

    /**
     * SE 1 EXIBE ESTOQUE, (SE 2 MOSTRA ALERTA, MAS EXIBE), SE 3 NAO EXIBE ESTOQUE)
     * @return
     */
    public int getFiltraEstoque() {
        return filtraEstoque;
    }

    public void setFiltraEstoque(int filtraEstoque) {
        values.put("filtrEstoq", filtraEstoque);
        this.filtraEstoque = filtraEstoque;
    }

    /**
     * OBTEM O PRAZO MAXIMO DE PAGAMENTO EM DIAS APOS A REALIZAÇÃO DA COMPRA
     * @return
     */
    public long getPrazoMaxGeral() {
        return prazoMaxGeral;
    }

    /**
     * DEFINE O PRAZO MAXIMO GERAL EM DIAS APOS A REALIZACAO DA COMPRA
     * @param prazoMaxGeral
     */
    public void setPrazoMaxGeral(long prazoMaxGeral) {
        this.values.put("prazoMaxGeral", prazoMaxGeral);
        this.prazoMaxGeral = prazoMaxGeral;
    }

    public String getVendePorDiaSemana() {
        return vendePorDiaSemana;
    }

    public void setVendePorDiaSemana(String vendePorDiaSemana) {

        this.vendePorDiaSemana = vendePorDiaSemana;
    }

    public ContentValues getValues() {
        return values;
    }

    public void setValues(ContentValues values) {
        this.values = values;
    }

    public long getDataExpira() {
        return dataExpira;
    }

    public void setDataExpira(long dataExpira) {
        this.dataExpira = dataExpira;
        this.values.put("data_expira", dataExpira);
    }

    public long getUltimaData() {
        return ultimaData;
    }

    public void setUltimaData(long ultimaData) {
        this.ultimaData = ultimaData;
        this.values.put("ultima_data", ultimaData);
    }

    public double getDescontMax() {
        return descontMax;
    }

    public void setDescontMax(double descontMax) {
        this.descontMax = descontMax;
        this.values.put("descont_max", descontMax);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        this.values.put("key", key);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        this.values.put("id", id);
    }
/*
    public long getNumeroUsuario() {
        return numeroUsuario;
    }

    public void setNumeroUsuario(long numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public String getControlaEstoque() {
        return controlaEstoque;
    }

    public void setControlaEstoque(String controlaEstoque) {
        this.controlaEstoque = controlaEstoque;
    }
*/
    public long getMaxParcelas() {
        return maxParcelas;
    }

    public void setMaxParcelas(long maxParcelas) {
        this.maxParcelas = maxParcelas;
        this.values.put("quant_max_parc", maxParcelas);
    }

    public String getData() {
        return data;
    }

    public void setDataCarga(String dataCarga) {
        this.data = dataCarga;
        this.values.put("data_carga", dataCarga);
    }

    public String getHora() {
        return hora;
    }

    public void setHoraCarga(String hora_carga) {
        this.hora = hora_carga;
        this.values.put("hora_carga", hora_carga);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
        this.values.put("mensagem", mensagem);
    }
}
