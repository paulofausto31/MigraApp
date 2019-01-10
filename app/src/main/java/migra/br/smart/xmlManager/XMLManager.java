/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.xmlManager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;


public class XMLManager {
    private Calendar cal = Calendar.getInstance();

    private File f;
    
    BufferedWriter bfw;
    private BufferedReader bfr;
    RandomAccessFile randAf;
    private String tag;
    private String subTag;

    /******************************NEGATIVAÇÃO*********************************/
    private String codJustfica;
    private String datNegativ;
    private String horaNegativ;
    private String rota;
    private String latitude;
    private String longitude;
    /******************************NEGATIVAÇÃO*********************************/


    /*****************************cliente**************************************/    
    private String codCli;
    /*****************************cliente**************************************/    
    
    /****************************pedido****************************************/
    private String codPed;
    private long dataInicio;
    private String horaInicio;
    private long dataFim;
    private String horaFim;
    private String qtdParcela;
    private long prazo;
    private String codFormPg;
    private String obs;
    /****************************pedido****************************************/

    /****************************vendedor**************************************/
    private String codVendedor;
    /****************************vendedor**************************************/

    /**************************item********************************************/
    private String codProd;
    private String qtd;
    private String total;
    private String descAcress;
    /**************************item********************************************/

    Context ctx;
    public XMLManager(Context ctx) throws IOException {
        this.ctx = ctx;
        f = new java.io.File((ctx.getApplicationContext().getFileStreamPath("pedido.xml").getPath()));
//        bfr = new BufferedReader(new FileReader(f));//talvez eliminar isso e reativar no readXml()
        Log.i("asffaasfafasfs", f.getAbsoluteFile().toString());
        //f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        /*if(!f.exists()){
            f.mkdirs();
        }*/
        //f = new File(f.getPath()+"/pedido.xml");
        AssetManager assetManager = ctx.getResources().getAssets();
        //InputStream inputStream = assetManager.open((f.getPath()+"/pedido.xml"));
        //InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getCodJustfica() {
        return codJustfica;
    }

    public void setCodJustfica(String codJustfica) {
        this.codJustfica = removePoint(codJustfica);
    }

    public String getDatNegativ() {
        return datNegativ;
    }

    public void setDatNegativ(String datNegativ) {
        this.datNegativ = datNegativ;
    }

    public String getHoraNegativ() {
        return horaNegativ;
    }

    public void setHoraNegativ(String horaNegativ) {
        this.horaNegativ = horaNegativ;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = removePoint(rota);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = removePoint(latitude);
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = removePoint(longitude);
    }

    public String getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(String codVendedor) {

        this.codVendedor = removePoint(codVendedor);
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {

        this.codCli = removePoint(codCli);
    }

    private String removePoint(String codCli) {
        StringBuilder sb = new StringBuilder(codCli);
        if(codCli.endsWith(".0")){
            sb.delete(sb.lastIndexOf("."), sb.lastIndexOf("0")+1);
            codCli = sb.toString();
        }
        return codCli;
    }

    public String getCodPed() {
        return codPed;
    }

    public void setCodPed(String codPed) {
        this.codPed = removePoint(codPed);
    }

    public String getDataInicio() {
        this.cal.setTimeInMillis(this.dataInicio);
        return this.cal.get(Calendar.DAY_OF_MONTH)+"/"+(this.cal.get(Calendar.MONTH)+1)+"/"+this.cal.get(Calendar.YEAR);
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDataFim() {
        this.cal.setTimeInMillis(this.dataFim);
        return this.cal.get(Calendar.DAY_OF_MONTH)+"/"+(this.cal.get(Calendar.MONTH)+1)+"/"+this.cal.get(Calendar.YEAR);
    }

    public void setDataFim(long dataFim) {
        this.dataFim = dataFim;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getQtdParcela() {
        return qtdParcela;
    }

    public void setQtdParcela(String qtdParcela) {
        this.qtdParcela = removePoint(qtdParcela);
    }

    public long getPrazo() {
        return prazo;
    }

    public void setPrazo(long prazo) {
        this.prazo = prazo;
    }

    public String getCodFormPg() {
        return codFormPg;
    }

    public void setCodFormPg(String codFormPg) {
        this.codFormPg = codFormPg;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = removePoint(codProd);
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = removePoint(qtd);
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = removePoint(total);
    }

    public String getDescAcress() {
        return descAcress;
    }

    public void setDescAcress(String descAcress) {
        this.descAcress = removePoint(descAcress);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSubTag() {
        return subTag;
    }

    public void setSubTag(String subTag) {
        this.subTag = subTag;
    }

    public String insertClie() throws IOException{
        String clie =
                    "<cli><cod>\\\""+getCodCli()+"\\\"</cod></cli>";
        randAf.seek(randAf.length());
        randAf.writeBytes(clie);

        return clie;
    }

    public String insertVendedor() throws IOException{
        String vendedor =
                "<vend cod=\\\""+getCodVendedor()+"\\\"/>";

        randAf.writeBytes(vendedor);

        return vendedor;
    }

    public String insertPedido(String quebraLinha) throws IOException{
        String hora = null;
        StringBuilder sb = new StringBuilder(getHoraInicio());
        sb.delete(sb.lastIndexOf(":"), sb.length());
        hora = sb.toString();

        String pedido =
                "p"+
                "|"+getCodCli()+//1
                        "|"+getDataInicio()+//1
                        "|"+getHoraInicio()+//2
                        "|"+getDataFim()+//3
                        "|"+getHoraFim()+//4
                        "|"+getQtdParcela()+//5
                        "|"+getPrazo()+//6
                        "|"+getCodFormPg()+//7
                        "|"+getCodVendedor()+//8
                        "|"+getLatitude()+//9
                        "|"+getLongitude()+
                        "|"+getCodPed()+
                        "|"+getObs()+
                        quebraLinha;//10
        randAf.seek(randAf.length());
        randAf.writeBytes(pedido);
        return pedido;
    }

    public String insertItem(String quebraLinha) throws IOException{
        /*String itens =
                "<item cod=\\\""+getCodProd()+"\\\">" +
                    "<qtd>"+getQtd()+"</qtd>" +
                    "<total>"+getTotal()+"</total>" +
                    "<descAcre>"+getDescAcress()+"</descAcre>" +
                "</item>";*/
        String itens =
                "i"+
                "|"+getCodProd()+//1
                        "|"+getQtd()+//2
                        "|"+getTotal()+//3
                        "|"+getDescAcress()+
                        quebraLinha;//4
        randAf.seek(randAf.length());
        randAf.writeBytes(itens);
        return itens;
    }
    public void makeStruct() throws IOException{
        //String xmlStruct = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><carg>";
        bfw = new BufferedWriter(new FileWriter(f));
       // bfw.write(xmlStruct);
       bfw.close();
    }

    public void closePedido() throws IOException{
       /* randAf.seek(randAf.length());
        randAf.writeBytes("</ped>");*/
    }

    public void openXml() throws FileNotFoundException{
        randAf = new RandomAccessFile(f, "rw");
    }

    public void closeXml() throws IOException{
        //randAf.seek(randAf.length());
       // randAf.writeBytes("</carg>");
        randAf.close();
    }

    public BufferedReader getBfr() {
        return bfr;
    }

    public void makeLocalBfr() throws FileNotFoundException {
        bfr = new BufferedReader(new FileReader(f));//talvez eliminar isso e reativar no readXml()
    }

    public void setBfr(BufferedReader bfr) {
        this.bfr = bfr;
    }

    public String readXml() throws IOException{
        String xml = "";
        String linha = "";

        bfr = new BufferedReader(new FileReader(f));

        while((linha = bfr.readLine()) != null){
            xml += linha;
        }

        bfr.close();

        xml = xml.replace("<", "%3C");
        xml = xml.replace(">", "%3E");
        xml = xml.replace(" ", "%20");
        xml = xml.replace("\"", "%22");
        xml = "%22" + xml + "%22";

        return xml;
    }

    public String insereJustificativa() throws IOException{
        String just =
                "<just><cod>\\\""+getCodJustfica()+"\\\"</cod></just>";
        randAf.writeBytes(just);
        return just;
    }

    /**
     * Insere data da negativação
     * @param seek é o deslocamento do ponteiro do arquivo xml
     * @return
     * @throws IOException
     */
    public String insertDatNeg(int seek) throws IOException{
        String dataIni =
                "<dNeg>\\\""+getDatNegativ()+"\\\"</dNeg>";
        randAf.seek(randAf.length()+seek);
        randAf.writeBytes(dataIni);
        return dataIni;
    }
    /**
     * Insere hora da negativação
     * @param seek é o deslocamento do ponteiro do arquivo xml
     * @return
     * @throws IOException
     */
    public String insertHoraNeg(int seek) throws IOException{
        String horaIni =
                "<hNeg>\\\""+getHoraNegativ()+"\\\"</hNeg>";
        randAf.seek(randAf.length()+seek);
        randAf.writeBytes(horaIni);
        return horaIni;
    }
    /**
     * Insere latitude
     * @param seek é o deslocamento do ponteiro do arquivo xml
     * @return
     * @throws IOException
     */
    public String insertLatitude(int seek) throws IOException{
        String horaIni =
                "<lati>\\\""+getLatitude()+"\\\"</lati>";
        randAf.seek(randAf.length()+seek);
        randAf.writeBytes(horaIni);
        return horaIni;
    }
    /**
     * Insere longtude
     * @param seek é o deslocamento do ponteiro do arquivo xml
     * @return
     * @throws IOException
     */
    public String insertLongitude(int seek) throws IOException{
        String horaIni =
                "<longi>\\\""+getLongitude()+"\\\"</longi>";
        randAf.seek(randAf.length()+seek);
        randAf.writeBytes(horaIni);
        return horaIni;
    }
    public String insereRota() throws IOException{
        String rota =
                "<rota>\\\""+getRota()+"\\\"</rota>";
        randAf.writeBytes(rota);
        return rota;
    }

    public String insertNegativ(String quebraLinha) throws IOException{
        /*String negativa =
                "<negativa>"+
                        "<vendcod>"+getCodVendedor()+"</vendcod>"+
                        "<rota>"+getRota()+"</rota>"+
                        "<cli>"+getCodCli()+"</cli>"+
                        "<just>"+getCodJustfica()+"</just>"+
                        "<dNeg>"+getDatNegativ()+"</dNeg>"+
                        "<hNeg>"+getHoraNegativ()+"</hNeg>"+
                        "<lati>"+getLatitude()+"</lati>"+
                        "<longi>"+getLongitude()+"</longi>"+
                "</negativa>";*/
        String negativa =
                        "n"+//1
                        "|"+getCodVendedor()+//2
                        "|"+getRota()+//3
                        "|"+getCodCli()+//4
                        "|"+getCodJustfica()+//5
                        "|"+getDatNegativ()+//6
                        "|"+getHoraNegativ()+//7
                        "|"+getLatitude()+//8
                        "|"+getLongitude()+//9
                        quebraLinha;
        randAf.seek(randAf.length());
        randAf.writeBytes(negativa);
        return negativa;
    }
}