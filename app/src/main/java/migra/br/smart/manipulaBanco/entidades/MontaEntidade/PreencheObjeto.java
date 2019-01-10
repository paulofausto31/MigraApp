package migra.br.smart.manipulaBanco.entidades.MontaEntidade;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import migra.br.smart.manipulaBanco.entidades.Preco.Preco;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.SaldoEstoque.SaldoEstoque;
import migra.br.smart.manipulaBanco.entidades.comodato.Comodato;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContReceb;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgment;
import migra.br.smart.manipulaBanco.entidades.fornecedor.Fornecedor;
import migra.br.smart.manipulaBanco.entidades.justificativa.Justificativa;
import migra.br.smart.manipulaBanco.entidades.linha.Linha;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.motivo.Motivo;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.vendedor.Vendedor;
import migra.br.smart.utils.data.Data;

/**
 * Created by ydxpaj on 20/07/2016.
 */
public class PreencheObjeto {

    DataInputStream input;
    DataOutputStream output;

    Produto prod;

    String atributo;
    String tipoObjeto;
    FieldsVerify fV;
    public PreencheObjeto(){
        fV = new FieldsVerify();
    }

    public DataInputStream getInput() {
        return input;
    }

    public void setInput(DataInputStream input) {
        this.input = input;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(String tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public Produto getProd() {
        return prod;
    }

    public void execute(){
        if(getTipoObjeto().equals("prod")){

        }
    }

    public Produto extraiProduto(String[] colunas) {
        Produto p = new Produto();
        //BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
        Log.i("LENG_PROD", colunas.length+"-barr"+colunas[1]+"--cod"+colunas[0]+"--Nome "+colunas[2]);
        int i = 0;
//        while((carga = bfr.readLine()) != null){
            p.setCodigo((colunas[i]));
            p.setCodBarras(colunas[++i]);
            p.setNome(colunas[++i]);
            p.setUnidadeVenda(colunas[++i]);
            p.setLinha(colunas[++i]);
            p.setSubLinha(colunas[++i]);
            p.setGrupo(colunas[++i]);
            p.setPVENDA1(colunas[++i]);
            p.setPVENDA2(colunas[++i]);
            p.setPVENDA3(colunas[++i]);
            p.setPVENDA4(colunas[++i]);
            p.setPVENDA5(colunas[++i]);
            p.setPVENDA6(colunas[++i]);
            p.setPVENDA7(colunas[++i]);
            p.setPVENDA8(colunas[++i]);
            p.setPVENDA9(colunas[++i]);
            /*p.setALIQ_IPI(Double.parseDouble(colunas[++i]));*/
            p.setUNIDADE_ARMAZENAMENTO(colunas[17]);
            /*p.setAPLICACAO(colunas[++i]);
            p.setCEST(colunas[++i]);
            p.setCFOP(colunas[++i]);
            p.setCOD_ANP(colunas[++i]);*/
            p.setICMS_COMPRA(colunas[21]);
            p.setDESCONTO_MAXIMO(colunas[22]);
            //p.setEX_IPI(colunas[++i]);
            p.setVENDE_FRACIONADO(colunas[23]);
            /*p.setGENERO(colunas[++i]);
            p.setICMS_COMPRA(colunas[++i]);
            p.setIRPJ(colunas[++i]);
            p.setPIS(colunas[++i]);*/
            p.setCONTROLA_LOTE(colunas[29]);
            p.setMARCA(colunas[30]);
            p.setMODELO(colunas[31]);
            /*p.setMVA(colunas[++i]);
            p.setCONTROLE_NUMERO_DE_SERIE(colunas[++i]);
            p.setORIGEM(colunas[++i]);*/
            p.setPESO_BRUTO(colunas[35]);
            p.setPESO_LIQUIDO(colunas[36]);
            Log.i("TIPO_PROD", colunas[38]);
            //p.setCST(colunas[++i]);
            p.setTIPO(colunas[38]);

            fV.verify("d", colunas[43]);
            p.setSaldo(fV.vDouble);

        //fV.verify("s", colunas[44]);
        p.setPromo1(colunas[44]);

        //fV.verify("s", colunas[45]);
        p.setPromo2(colunas[45]);

        //fV.verify("s", colunas[46]);
        p.setPromo3(colunas[46]);

        //fV.verify("s", colunas[47]);
        p.setPromo4(colunas[47]);

        //fV.verify("s", colunas[48]);
        p.setPromo5(colunas[48]);

        //fV.verify("s", colunas[49]);
        p.setPromo6(colunas[49]);

        //fV.verify("s", colunas[50]);
        p.setPromo7(colunas[50]);

        //fV.verify("s", colunas[51]);
        p.setPromo8(colunas[51]);

        //fV.verify("s", colunas[52]);
        p.setPromo9(colunas[52]);

        String valPromo = colunas[53].replaceAll("\\s", "");//validade da promocao

        if(valPromo.split("/").length == 3){
            fV.verify("l", String.valueOf(new Data(colunas[53]).getOnlyDataInMillis()));
            p.setValPromo(fV.vLong);
        }else{
            p.setValPromo(0);
        }

        p.setVENDE_FRACIONADO(colunas[24]);
        p.setUnArmazena(colunas[55]);

        fV.verify("d", colunas[54]);
        p.setQtdArmazenamento(fV.vDouble);

        //fV.verify("l", new Data(colunas[53]).getOnlyDataInMillis();
           // p.setCODIGO_TRIBUTACAO(colunas[++i]);*/
        //    p.setRAZAO_DE_VENDA_EM_MULTIPLO(colunas[40]);
            //p.setPRECO_DE_COMPRA(colunas[++i]);
  //      }
    //    bfr.close();
        return p;
    }

    private double converteDouble(String valor){
        double v = 0;

        if(valor.endsWith(".")){
            valor = valor.replace(".", "");
        }

        v = Double.parseDouble(valor);

        return v;
    }

    public Produto preencheFSEProd(BufferedReader input) throws IOException {
        prod = new Produto();
        prod.setCodigo((input.readLine()));

        prod.setNome(input.readLine());
        prod.setUnidadeVenda(input.readLine());
        prod.setSaldo(Long.parseLong(input.readLine()));
        prod.setLinha(input.readLine());
        prod.setFornecedor(input.readLine());
        prod.setArmazenamento(Double.parseDouble(input.readLine()));

        return prod;
    }

    public Cliente extraiClie(BufferedReader input) throws IOException {
        Cliente sgeClie = new Cliente();

        sgeClie.setCodigo(Double.parseDouble(input.readLine()));
        sgeClie.setFantasia(input.readLine());
        sgeClie.setEndereco(input.readLine());
        sgeClie.setTelefone(input.readLine());
        sgeClie.setUltimaCompra(input.readLine());//date
        sgeClie.setTotalAtraso(Double.parseDouble(input.readLine()));
        sgeClie.setTotalDebito(Double.parseDouble(input.readLine()));
        sgeClie.setFormPagmento(input.readLine());
        sgeClie.setCondicPgment(input.readLine());
        sgeClie.setCnpj(input.readLine());
        sgeClie.setLimitCred(Double.parseDouble(input.readLine()));
        sgeClie.setSeqVisit(input.readLine());
        sgeClie.setRzSocial(input.readLine());
        sgeClie.setCidade(input.readLine());
        sgeClie.setBairro(input.readLine());
        sgeClie.setPedeHoraVisit(input.readLine());

        return sgeClie;
    }

    public Cliente extraiClie(String[] cols){
        Cliente sgeClie = new Cliente();

        fV.verify("d", cols[0]);
        //sgeClie.setHora(Double.parseDouble(cols[0]));
        sgeClie.setCodigo(fV.vDouble);

        sgeClie.setFantasia(cols[1]);
        Log.i("FANTA", sgeClie.getFantasia());
        sgeClie.setEndereco(cols[3]);
        //sgeClie.setTelefone(input.readLine());
        //sgeClie.setUltimaCompra(input.readLine());//date
        //sgeClie.setTotalAtraso(Double.parseDouble(input.readLine()));
        //sgeClie.setTotalDebito(Double.parseDouble(input.readLine()));
        //sgeClie.setFormPagmento(input.readLine());
        //sgeClie.setCondicPgment(input.readLine());
        //sgeClie.setCodCli(input.readLine());

        fV.verify("d", cols[6]);
        //sgeClie.setLimitCred(Double.parseDouble(cols[6]));
        sgeClie.setCodVendedor(fV.vDouble);

        sgeClie.setRzSocial(cols[2]);
        sgeClie.setCidade(cols[4]);
        sgeClie.setBairro(cols[5]);

        fV.verify("d", cols[6]);
        //sgeClie.setLimitCred(Double.parseDouble(cols[6]));
        sgeClie.setLimitCred(fV.vDouble);
        fV.verify("i", cols[8]);
        sgeClie.setPrazoPagamento(fV.vInteger);
        //sgeClie.setCodVendedor(Double.parseDouble(cols[7]));
        //sgeClie.setPedeHoraVisit(input.readLine());

        return sgeClie;
    }

    public Vendedor extraiVendedor(BufferedReader input) throws IOException{
        Vendedor vendedor = new Vendedor();

        vendedor.setCodigo(input.readLine());
        vendedor.setNome(input.readLine());

        return vendedor;
    }

    public Vendedor extraiVendedor(String[] cols){
        Vendedor vendedor = new Vendedor();

        vendedor.setCodigo(cols[0]);
        vendedor.setNome(cols[1]);

        return vendedor;
    }

    public Preco prePreco(BufferedReader input) throws IOException{
        Preco preco = new Preco();

        preco.setCodProd((input.readLine()));
        preco.setValor(Double.parseDouble(input.readLine().replace(",", ".")));
        preco.setVendeAVista(input.readLine());

        return preco;
    }

    public Fornecedor extraiFornecedor(BufferedReader input) throws IOException{

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setCodigo(Double.parseDouble(input.readLine()));
        fornecedor.setNome(input.readLine());

        return fornecedor;
    }

    public Fornecedor extraiFornecedor(String[] cols){
        Fornecedor forne = new Fornecedor();

        fV.verify("d", cols[0]);
        //forne.setHora(Double.parseDouble(cols[0]));
        forne.setCodigo(fV.vDouble);

        forne.setNome(cols[1]);
        forne.setRazaoSocial(cols[2]);

        return forne;
    }

    public Linha extraiLinha(String[] cols) {
        Linha l = new Linha();
        int i = 0;
        l.setCodigo(cols[i]);
        l.setDescricao(cols[++i]);
        l.setComissao1(cols[++i]);
        l.setComissao2(cols[++i]);
        l.setFoto(cols[++i]);
        l.setControlaLote(cols[++i]);

        return l;
    }

    public Linha extraiLinha(BufferedReader input) throws IOException{

        Linha l = new Linha();

        l.setCodigo(input.readLine());
        l.setDescricao(input.readLine());

        return l;
    }

    public FormPgment extraiFormPgment(BufferedReader input) throws IOException{

        FormPgment formPgment = new FormPgment();

        formPgment.setCodigo(input.readLine());
        formPgment.setDescricao(input.readLine());
        formPgment.setJuros(Double.parseDouble(input.readLine()));
        formPgment.setMulta(Double.parseDouble(input.readLine().replace(",", ".")));

        return formPgment;
    }

    public FormPgment extraiFormPgment(String[] cols){
        FormPgment formPgment = new FormPgment();

        formPgment.setCodigo(cols[0]);
        formPgment.setDescricao(cols[1]);
        formPgment.setTipo(cols[2]);
        formPgment.setPrazoPadrao(cols[3]);
        formPgment.setConsideraCredito(cols[4]);
        formPgment.setPermiteDescont(cols[5]);

        fV.verify("d", cols[10]);
        //formPgment.setJuros(Double.parseDouble(cols[10]));
        formPgment.setJuros(fV.vDouble);

        fV.verify("d", cols[11].replace(",", "."));
        //formPgment.setMulta(Double.parseDouble(cols[11].replace(",", ".")));
        formPgment.setMulta(fV.vDouble);

        return formPgment;
    }

    public ContReceb extraiContReceb(BufferedReader input) throws IOException{
        /*"id integer primary key autoincrement, " +
                "codCliente double," +
                " numTitulo text," +
                " datEmissao text," +//date
                " dataVenci text," +//date
                " valor double, " +
                "codFormPgment integer," +//id do tipo de pagamento*/
        ContReceb contRe = new ContReceb();

        contRe.setCodCliente(Double.parseDouble(input.readLine()));
        contRe.setNumTitulo(input.readLine());
        contRe.setDataEmissao(input.readLine());
        contRe.setDataVenci(input.readLine());
        contRe.setValor(Double.parseDouble(input.readLine().replace(",", ".")));
        contRe.setFormPg(input.readLine());

        return contRe;
    }

    public ContReceb extraiContReceb(String[] cols){

        ContReceb contRe = new ContReceb();
        contRe.setCodVenda(cols[0]);

        fV.verify("d", cols[4]);
        Log.i("problema", fV.vDouble+"");
        //contRe.setCodCliente(Double.parseDouble(cols[4]));
        contRe.setCodCliente(fV.vDouble);

        contRe.setNumTitulo(cols[2]);
        contRe.setDataEmissao(cols[5]);
        contRe.setDataVenci(cols[6]);

        fV.verify("d", cols[3].replace(",", "."));
        //contRe.setValor(Double.parseDouble(cols[3].replace(",", ".")));
        contRe.setValor(fV.vDouble);

        contRe.setFormPg(cols[1]);

        fV.verify("d", cols[8]);
        contRe.setvOriginal(fV.vDouble);

        return contRe;
    }

    public Empresa extractEmpresa(String[] cols) {
        Empresa emp = new Empresa();

        emp.setCnpj(cols[0]);
        emp.setFantasia(cols[1]);
        //emp.setRzSocial(cols[2]);

        return emp;
    }

    private class FieldsVerify{
        public int vInteger;
        public double vDouble;
        public long vLong;
        public String vString;
        public String tipo;

        /**
         * VERIFICA CARACTERES INVÁLIDOS
         * @param tipo tipo desejado para saída
         * @param d String de entrada
         * @return nada
         */
        public void verify(String tipo, String d){
            if(!tipo.equals("s") && (d.isEmpty() || d.equals("\n"))){
                d = "0";
            }

            switch(tipo){
                case "i":
                    vInteger = Integer.parseInt(d);
                    break;
                case "d":
                    vDouble = Double.parseDouble(d);
                    break;
                case "l":
                    vLong = Long.parseLong(d);
                    break;
                case "s":
                    if(d.equals("\n")){
                        vString = "";
                    }else{
                    vString = d;
                    }
                    break;
            }
        }
    }

    public Motivo preMotivo(BufferedReader input) throws IOException{
        Motivo motivo = new Motivo();

        motivo.setCodigo(Long.parseLong(input.readLine()));
        motivo.setDescricao(input.readLine());

        return motivo;
    }

    public Configuracao preConfiguracao(BufferedReader input) throws IOException{
        Configuracao config = new Configuracao();

        config.setDescontMax(Double.parseDouble(input.readLine()));
        /*config.setKey(input.readLine());
        config.setNumeroUsuario(Long.parseLong(input.readLine()));
        config.setControlaEstoque(input.readLine());*/
        config.setMaxParcelas(Long.parseLong(input.readLine()));
        config.setDataCarga(input.readLine());
        config.setHoraCarga(input.readLine());
        config.setMensagem(input.readLine());

        return config;
    }

    public SaldoEstoque extraiSaldoEstoque(String[] cols) {
        SaldoEstoque saldoEstoque = new SaldoEstoque();

        fV.verify("l", cols[0]);
        //saldoEstoque.setDeposito(Long.parseLong(cols[0]));
        saldoEstoque.setDeposito(fV.vLong);
        saldoEstoque.setProduto(cols[1]);

        //saldoEstoque.setSaldo(Double.parseDouble(cols[2]));
        fV.verify("d", cols[2]);
        saldoEstoque.setSaldo(fV.vDouble);

        return saldoEstoque;
    }

    public Rota extraiRota(String[] cols) {
        Rota rota = new Rota();

        fV.verify("l", cols[0]);
        //rota.setHora(Long.parseLong(cols[0]));
        rota.setCodigo(fV.vLong);

        rota.setDescricao(cols[1]);

        fV.verify("d", cols[2]);
        //rota.setVendedor(Double.parseDouble(cols[2]));
        rota.setVendedor(fV.vDouble);

        rota.setDiaVender(cols[3]);
        return rota;
    }

    public SeqVisit extraiSeqVisit(String[] cols) {
        SeqVisit seqVisit = new SeqVisit();

        fV.verify("l", cols[0]);
        //seqVisit.setCodRota(Long.parseLong(cols[0]));
        seqVisit.setCodRota(fV.vLong);

        fV.verify("d", cols[1]);
        //seqVisit.setCodCliente(Double.parseDouble(cols[1]));
        seqVisit.setCodCliente(fV.vDouble);

        fV.verify("l", cols[2]);
        //seqVisit.setSeqVisit(Long.parseLong(cols[2]));
        seqVisit.setSeqVisit(fV.vLong);
        return seqVisit;
    }

    public Configuracao extraiConFiguracao(String[] cols) {
        Configuracao config = new Configuracao();

        fV.verify("d", cols[18]);
        //config.setDescontMax(Double.parseDouble(cols[18]));
        config.setDescontMax(fV.vDouble);

        fV.verify("i", cols[30]);
        config.setFiltraEstoque(fV.vInteger);

        fV.verify("l", cols[84]);
        /*if(Long.parseLong(cols[84]) == 0){
            cols[84] = "1";
        }*/
        if(fV.vLong == 0){
            fV.vLong = 1;
        }
        config.setMaxParcelas(fV.vLong);
        Log.i("MAX_PARC",""+config.getMaxParcelas());

        config.setMensagem(cols[69]);
        config.setDataCarga(String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance()));
        config.setHoraCarga(String.format("%tT", Calendar.getInstance()));
        fV.verify("i", cols[123]);
        config.setPrazoMaxGeral(fV.vInteger);//prazo maximo geral de pagamento, em dias apos a compra
        config.setVendePorDiaSemana(cols[201]);

        return config;
    }

    public Justificativa extraiJustificativa(String[] cols) {
        Justificativa just = new Justificativa();
        just.setCodigo(cols[0]);
        just.setDescricao(cols[1]);
        return just;
    }

    public Comodato extractComodato(String[] cols) {
        Comodato como = new Comodato();

        fV.verify("d", cols[0]);
        //como.setCodCli(Double.parseDouble(cols[0]));
        como.setCodCli(fV.vDouble);

        como.setCodProd(cols[1]);

        fV.verify("d", cols[2]);
        //como.setSaldo(Double.parseDouble(cols[2]));
        como.setSaldo(fV.vDouble);
        return como;
    }
}
