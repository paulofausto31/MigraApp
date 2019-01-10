package migra.br.smart.TaskNetWork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

//import migra.br.smart.Activityproduto.ControlFragment.ControlFragment;
import migra.br.smart.ActivityCliente.ClienteFragment;
import migra.br.smart.ActivityCliente.fragmentJustifcativa.NegativacaoFragment;
import migra.br.smart.R;
import migra.br.smart.TaskNetWork.httpTransfer.HttpTransfer;
import migra.br.smart.TaskNetWork.mailSender.MailSender;
import migra.br.smart.TaskNetWork.transferOperations.Operations;
import migra.br.smart.activityLogin.LoginActivity;
import migra.br.smart.atvConfig.Config;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.historiFg.HistoricoFragment;
import migra.br.smart.manipulaBanco.dbAccess.DBAccess;
import migra.br.smart.manipulaBanco.dropDatabase.DropCreateDatabase;
import migra.br.smart.manipulaBanco.dropDatabase.DropCreateDatabaseDirectAccess;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocal;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalRN;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaRN;
import migra.br.smart.manipulaBanco.entidades.registro.Registro;
import migra.br.smart.manipulaBanco.entidades.registro.RegistroRN;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusRN;
import migra.br.smart.rotaFg.RotaAdp;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.MontaEntidade.PreencheObjeto;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoRN;
import migra.br.smart.manipulaBanco.entidades.comodato.Comodato;
import migra.br.smart.manipulaBanco.entidades.comodato.ComodatoRN;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContReceb;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContRecebRN;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgment;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgmentRN;
import migra.br.smart.manipulaBanco.entidades.fornecedor.Fornecedor;
import migra.br.smart.manipulaBanco.entidades.fornecedor.FornecedorRN;
import migra.br.smart.manipulaBanco.entidades.justificativa.Justificativa;
import migra.br.smart.manipulaBanco.entidades.justificativa.JustificativaRN;
import migra.br.smart.manipulaBanco.entidades.linha.Linha;
import migra.br.smart.manipulaBanco.entidades.linha.LinhaRN;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteRN;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.rota.RotaRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.manipulaBanco.entidades.vendedor.Vendedor;
import migra.br.smart.manipulaBanco.entidades.vendedor.VendedorRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;
import migra.br.smart.utils.typeExtraTransfer.TypeTransfer;

/**
 * Created by ydxpaj con 06/07/2016.
 */
public class TaskConnectNetWork extends AsyncTask<String, String, Void> {

    private double currentCli;//o codigo do cliente será mudado para trnasmitir os dados, esta variável será usada para restaurar esse código após a transmissão
    private String ip;
    private int porta;
    private OutputStream output;
    private DataInputStream input;
    private String codVendedor;
    private String comando;//comando enviado para realizar a tarefa
    Socket socket;
    HttpURLConnection conn = null;//para requisições HTTP
    Transfer transfer;
    private String retorno;
    Context ctx;
    private String msgPostExec = "CHAVE OU CNPJ INVÁLIDO";
    boolean retornoTask = true;
    View view;//view passada para alguma interação
    ProgressDialog dialog;

    private String responseWebService;

    private ConfigLocal con;

    private Registro deviceRegister;

    private String saldProd;

    public boolean cancelTask;

    public FragmentManager fragManag;

    private boolean apagaStatus;

    private boolean statusOperacao;

    Handler handler;

    /*public TaskConnectNetWork(Context ctx, View view, String codVendedor){
        super();
        //this.ip = ip;
        //this.porta = (int)porta;
        this.codVendedor = codVendedor;
        this.ctx = ctx;
        this.view = view;
        handler = new Handler();
    }*/
    public TaskConnectNetWork(Context ctx, FragmentManager fragManag, String codVendedor){
        super();
        //this.ip = ip;
        //this.porta = (int)porta;
        this.codVendedor = codVendedor;
        this.ctx = ctx;
        this.fragManag = fragManag;
        handler = new Handler();
    }

    public TaskConnectNetWork(Context ctx, ConfigLocal con, View view, String codVendedor) {
        this.codVendedor = codVendedor;
        this.ctx = ctx;
        this.view = view;
        this.con = con;
        handler = new Handler();
    }

    public TaskConnectNetWork(Context ctx, Registro deviceRegister, View view, String codVendedor) {
        this.codVendedor = codVendedor;
        this.ctx = ctx;
        this.view = view;
        this.deviceRegister = deviceRegister;
        handler = new Handler();
    }

    public Transfer getTransfer() {
        return transfer;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        dialog = new ProgressDialog(ctx);
        dialog.setTitle("IMPORTANDO");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "CANCELAR", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dg, int wich){
                cancelTask = true;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
        CurrentInfo.finisAssincTask = true;//indica que a tarefa assincrona acabou
        if(view != null){
            if(view instanceof FloatingActionButton) {
                view.setEnabled(true);
            }else if(view instanceof ListView){
                try {
                    updateLV();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if(view instanceof TextView){
                TextView tv = (TextView) view;
                tv.setText(saldProd);
            }
        }
        dialog.dismiss();
        if(!cancelTask) {//se a operação foi cancelada, o app não finaliza
            if (this.codVendedor != null && !this.codVendedor.equals("")) {
                try {
                    ArrayList<Rota> alRota = new RotaRN(this.ctx).getRouteForSalesMan(this.codVendedor);
                    if (alRota.size() == 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(this.ctx);
                        alert.setTitle("ESTE VENDEDOR NÃO EXISTE NA BASE DE DADOS");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelTask = true;
                            }
                        });
                        alert.show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            Toast.makeText(ctx, msgPostExec, Toast.LENGTH_SHORT).show();
            Utils.showMsg(ctx, "OPERAÇÃO CONCLUÍDA", msgPostExec, null);
            if (this.msgPostExec.equals("CONFIGURAÇÃO SALVA")) {

            } else if (this.msgPostExec.equals("SEM RESPOSTA DA REDE")) {
                Utils.showMsg(ctx, "ERRO", this.msgPostExec, R.drawable.dialog_error);
            } else if (this.msgPostExec.equals("CHAVE OU CNPJ INVÁLIDO")) {
                //if(!cancelTask) {//se a operação foi cancelada, o app não finaliza
                if (ctx instanceof Config) {
                    Config l = (Config) ctx;
                    l.finish();
                } else if (ctx instanceof LoginActivity) {
                    LoginActivity l = (LoginActivity) ctx;
                    l.finish();
                }
                //}
            } else if (this.msgPostExec.equals("ERRO PRCESSO WEB")) {
                Utils.showMsg(ctx, "ERRO", this.msgPostExec, R.drawable.dialog_error);
            } else if (this.msgPostExec.equals("CARGA RECEBIDA")) {//se não houver msgPostExec
                //CurrentInfo.codVendedor = edtLoginCodVend.getText().toString();
                if (ctx instanceof LoginActivity) {
                    Intent it = new Intent(Operations.ACTIVITY_CONTAINER_FRAGMENTS);
                    it.putExtra("openFrag", "rotaFg");//identifica o fragment a exibir
                    ctx.startActivity(it);
                }
            }
        }
        if(fragManag != null) {
            if (ControlFragment.isActiveJustifica()) {
                //ProdListFragment prodListFrag = (ProdListFragment)fragmentManager.findFragmentByTag("prodListFrag");
                NegativacaoFragment negFrag = (NegativacaoFragment) fragManag.findFragmentByTag("fragJust");
                negFrag.fillList(null);
            }
            if (ControlFragment.isActiveHistoriFg()) {
                HistoricoFragment histFg = (HistoricoFragment) fragManag.findFragmentByTag("historiFg");
                try {
                    histFg.updateListView(null);
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
            if (ControlFragment.isActiveClientFrag()) {
                ClienteFragment cliFg = (ClienteFragment) fragManag.findFragmentByTag("cliFrag");
                cliFg.fillListView(new Pedido());
            }
        }

        if(isApagaStatus()) {
            new Utils().clearStatClientes(ctx);//limpar os status dos cliente P, N, A
        }

        CurrentInfo.codCli = currentCli;

        if(TypeTransfer.isINTERNO()){//pedidos foram enviados para a memória interna
            AlertDialog.Builder alMail = new AlertDialog.Builder(ctx);
            alMail.setMessage("ENVIAR POR EMAIL?");
            alMail.setPositiveButton("SIM", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dg, int wich){
                    MailSender mSend = new MailSender(ctx);
                    ArrayList<ConfigLocal> list = null;
                    try {
                        list = new DBAccess(ctx).pesquisar(new ConfigLocal());
                        if(list.size() > 0){
                            mSend.setDestino(new String[]{list.get(0).getEmailDestino()});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //mSend.setDestino(new String[]{"jdanielobj@gmail.com"});
                    mSend.startMail();
                    mSend.removePedidos();
                }
            });
            alMail.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dg, int wich){
                }
            });
            alMail.show();
        }

        if(statusOperacao){
            Utils.showMsg(ctx, "OPERAÇÃO", "CONCLUÍDA", null);
        }
    }

    @Override
    protected Void doInBackground(String... comands) {
         currentCli = CurrentInfo.codCli;

        String retLocalServer = "";
        ArrayList<ConfigLocal> confLocal = null;
        String[] data = null;
        int totalConections = 0;

        try {
            confLocal = new ConfigLocalRN(ctx).pesquisar(new ConfigLocal());
            ip = confLocal.get(0).getIp();
            porta = (int)confLocal.get(0).getPorta();
            comando = comands[0];

            if(Integer.parseInt(comando) == Operations.DOWNLOAD_INTERNET) {

            }else if(Integer.parseInt(comando) == Operations.EXPORT_PEDIDOS_INTERNET){
                //dialog.setTitle("ENVIANDO");
            }

            switch(Integer.parseInt(comands[0])){
                case Operations.CORRIGE_ITENS:
                    publishProgress("CORRIGINDO ITENS DA VERSÃO ANTERIOR", "");
                    ItemLista iList = new ItemLista();
                    iList.setCodProd("");
                    ArrayList<ItemLista> arrIl = new ItemListaRN(ctx).pesquisar(iList);
                    Log.i("TSoK", ""+arrIl.size());

                    for(ItemLista il: arrIl){
                        String[] cxUn = il.getQtd().split("/");
                        if(cxUn.length > 1){
                            Log.i("TSK", il.getId());// arrIl.get(i).getQtd()+"--"+arrIl.get(i).getId());
                            il.setQtd(cxUn[0]);//caixa
                            il.setQtdUn(Long.parseLong(cxUn[1]));//unidade
                            new ItemListaRN(ctx).update(il);
                        }
                    }
                    /*for(int i = 0; i < arrIl.size(); i++){
                        String[] cxUn = arrIl.get(i).getQtd().split("/");
                        if(cxUn.length > 1){
                            Log.i("TSK", arrIl.get(i).getId());// arrIl.get(i).getQtd()+"--"+arrIl.get(i).getId());
                            arrIl.get(i).setQtd(cxUn[0]);//caixa
                            arrIl.get(i).setQtdUn(Long.parseLong(cxUn[1]));//unidade
                            new ItemListaRN(ctx).update(arrIl.get(i));
                        }
                    }*/
                    statusOperacao = true;
                    break;
                case Operations.downloadLocal:
                    String[] tabelas = {"L", "F", "P", "C", "V", "R", "O", "B", "D", "E", "Z", "J", "K"};
                    if(this.ip != null) {
                        for(String t :tabelas) {
                            Transfer transfer = null;
                            while((transfer = connect(this.ip, this.porta, Operations.downloadLocal)) == null) {
                                publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
                            }

                            String rLocalServ = "";

                            //lerchave do servidor local, se não houver chave, não recebe, nem transmite

                            transfer.send(Operations.VERSAO_APP+"\n");//envia a versão do app
                            rLocalServ = transfer.getBufferedReader().readLine();//resposta da recepção da versão do app
                            if(Operations.OK == Integer.parseInt(rLocalServ)) {

                                ++totalConections;
                                if (totalConections == 1) {//faz o drop só na primeira conexao
                                    new DropCreateDatabaseDirectAccess().dropTables(ctx, new DropCreateDatabase());//apaga algumas tabelas
                                }

                                PreencheObjeto preencheObj = new PreencheObjeto();

                                transfer.send("TABELA_" + t + "\n");
                                retLocalServer = transfer.getBufferedReader().readLine();//resposta do servidor
                                Log.i("RESP", retLocalServer);
                                switch (Integer.parseInt(retLocalServer)) {
                                    case Operations.ERRO_IO:
                                        publishProgress(transfer.getBufferedReader().readLine(), "ERRO");
                                        transfer.getSocket().close();
                                        break;
                                    case Operations.ERRO_FILE_NOT_FOUND:
                                        publishProgress(transfer.getBufferedReader().readLine(), "ERRO");
                                        transfer.getSocket().close();
                                        break;/****CONTINUAR AQUI VERICAÇÃO DE ERROS NOTIFICAR O USUÁRIO****/
                                    case Operations.OK:
                                        receiverDataLocal(transfer, "TABELA_" + t, preencheObj);
                                        break;
                                }
                            }
                        }
                        updateStatus();//mantem os status dos pedidos e clientes após receber carga
                    }
                    totalConections = 0;//para fazer drop quando conectar 1 vez
                    break;
                case Operations.UPLOAD_INTERNO:
                    TypeTransfer.setINTERNO(true);//será verificado no postExecute()

                    ArrayList<Pedido> arrLPed = null;
                    Transfer transfer = new Transfer();
                    try {
                        this.msgPostExec = "OK";//não há erros
                        Pedido filterPed = new Pedido();
                        if(comands[1].equals("reenviar")){/////////////PERMITE REENVIAR ////////////////////////
                            filterPed.setStatus("Transmitido");
                            filterPed.setDel("S");//neste caso a opção para deletar(s) é usada para retransmitir
                        }else {
                            filterPed.setStatus("Fechado");
                        }
                        filterPed.setIdEmpresa(Integer.parseInt(comands[2]));
                        //Pedido filterPed = new Pedido();
                        //filterPed.setStatus("Fechado");
                        arrLPed = new PedidoRN(ctx).filtrar(filterPed);
                        if (arrLPed.size() > 0) {
                            for (Pedido ped : arrLPed) {
                                publishProgress("PEDIDO " + ped.getId(), "TRANSMITINDO");
                                Log.i("TRANSMITINDO", ped.getStatus());

                                CurrentInfo.idPedido = ped.getId();
                                CurrentInfo.codCli = ped.getCodCli();
                                CurrentInfo.dataInicio = ped.getDataInicio();
                                CurrentInfo.horaInicio = ped.getHoraInicio();
                                CurrentInfo.dataFim = ped.getDataFim();
                                CurrentInfo.horaFim = ped.getHoraFim();
                                CurrentInfo.codFormPagamento = ped.getIdFormPg();
                                CurrentInfo.prazo = ped.getPrazo();//prazo de pagamento

                                ArrayList<ListaPedido> listPed = new ListaPedidoRN(ctx).getForNomeProd("");
                                String pedido = "p" +
                                        "|" + String.valueOf(CurrentInfo.codCli) +//1
                                        "|" + CurrentInfo.dataInicio +//2
                                        "|" + CurrentInfo.horaInicio +//3
                                        "|" + CurrentInfo.dataFim +//4
                                        "|" + CurrentInfo.horaFim +//5
                                        "|" + String.valueOf(ped.getQtParcela()) +//6
                                        "|" + CurrentInfo.prazo +//7
                                        "|" + CurrentInfo.codFormPagamento +//8
                                        "|" + listPed.get(0).getCodVendedor() + "" +//9
                                        "|" + String.valueOf(ped.getLatitudeInicio()) +//10
                                        "|" + String.valueOf(ped.getLongitudeInicio()) +//11
                                        "|" + CurrentInfo.idPedido +//12
                                        "|" + ped.getObs() +//13
                                        // "|"+new Data(ped.getDataPagamento()).getStringData()+
                                        "\n";//quebra de linha

                                //File pedFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "appMped");//pasta raiz dos pedidos
                                File migra = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getParent() + "/migra");//pasta do pedid
                                File pedMigra = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getParent() + "/migra/pedido");//pasta do pedid
                                if (!migra.exists()) {
                                    //if(pedFolder.mkdir()){
                                    migra.mkdir();
                                        pedMigra = new File(migra.getAbsolutePath()+"/pedido");
                                        pedMigra.mkdir();
                                    //}
                                }
                                transfer.setBufferedWriter(
                                        new BufferedWriter(
                                                new FileWriter(pedMigra.getAbsolutePath() + "/" + CurrentInfo.cnpjEmpresa + CurrentInfo.idPedido + ".txt")));//nome do arquivo do pedid
                                                            /*new File(
                                                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"pedFolder+"/"+CurrentInfo.cnpjEmpresa+CurrentInfo.idPedido+".txt"))));//nome do arquivo do pedid
                                                                    */
                                transfer.send(pedido);//DADOS DO PEDIDO
                                for (ListaPedido lp : listPed) {
                                    Log.i("COD_PROD", lp.getItemLista().getCodProd());
                                    Log.i("TOTAL", lp.getItemLista().getTotal() + "");
                                    String iten =
                                            "i" +
                                                    "|" + lp.getItemLista().getCodProd() +//1
                                                    //"|" + String.valueOf(lp.getItemLista().getQtd()) +//2
                                                    "|" + String.valueOf(lp.getItemLista().getQtdUn()) + "/" + String.valueOf(lp.getItemLista().getQtd()) +//2
                                                    "|" + String.valueOf(lp.getItemLista().getTotal()) +//3
                                                    "|" + String.valueOf(lp.getItemLista().getDescAcreMone()) +//4
                                                    "\n";
                                    transfer.send(iten);//DADOS DO PEDIDO
                                }

                                if (comands[1].equals("reenviar")) {//verifica retransmissão
                                    ped.setDel("n");
                                    new PedidoRN(ctx).update(ped);
                                }

                                transfer.send("***FIM***\n");//FINALIZA TRANSMISSÃO
                                transfer.getBufferedWriter().close();

                                confirmTransmit(ped);
                                //++totPedTransmitido; IMPLEMENTAR ISSO/////////////////////////////
                                if (!comands[1].equals("reenviar")) {//não é retransmissão
                                    if(isApagaStatus()) {
                                        Pedido pd = new Pedido();
                                        pd.setSeqVist_id(ped.getSeqVist_id());

                                        ArrayList<Pedido> arrLPd = new PedidoRN(ctx).getOpenOrClose(pd);
                                        Log.i("SEQSTAT", arrLPd.size() + "");
                                        if (arrLPd.size() == 0) {//LIMPA O STATUS SOMENTE SE O PEDIDO SETIVER ABERTO OU FECHADO
                                            SeqVisit seq = new SeqVisit();
                                            seq.setId(ped.getSeqVist_id());
                                            ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);

                                            Log.i("SQiNT", arrLSq.get(0).getStatus() + "STATUS SEQUENCIA VISITA");

                                            if (arrLSq != null && arrLSq.size() > 0) {
                                                arrLSq.get(0).setStatus("");
                                                new SeqVisitRN(ctx).update(arrLSq.get(0));
                                            }
                                        }

                                        new SeqVisitStatusRN(ctx).deletar(String.valueOf(ped.getSeqVist_id()));
                                    }
                                }

                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        msgPostExec = "FALHA NA TRANSMISSÃO";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    /*************************NEGATIVAÇÃO**********************************/
                    try {
                        Negativacao ne = new Negativacao();
                        if(comands[1].equals("reenviar")){////////REENVIAR OS DADOS////////
                            ne.setStatus("Transmitido/select");
                        }else {
                            ne.setStatus("Espera");
                        }
                        ne.setIdEmpresa(Integer.parseInt(comands[2]));//EXPERIMENTAL 24/04/2018
                        ArrayList<Negativacao> lNegativ = new NegativacaoRN(ctx).getWithClients(ne);

                        transfer = new Transfer();

                        if (lNegativ.size() > 0) {
                            Log.i("NEGATIVADOS", "existe negativacao");
                            publishProgress("", "TRANSMITINDO NEGATIVAÇÕES");
                                        transfer.setBufferedWriter(
                                                new BufferedWriter(
                                                        new FileWriter(
                                                                new File(
                                                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/NEGA"+CurrentInfo.codVendedor+".txt")
                                                        )
                                                )
                                        );

                                        for (Negativacao neg : lNegativ) {
                                            publishProgress(neg.getCliente().getFantasia(), "PREPARANDO PARA TRANSMITIR");
                                            String negativa =
                                                    "n" +//1
                                                            "|" + neg.getVendedor().getCodigo() +//2
                                                            "|" + String.valueOf(neg.getCodRota()) +//3
                                                            "|" + String.valueOf(neg.getCodCli()) +//4
                                                            "|" + neg.getCodJustf() +//5
                                                            "|" + new Data(neg.getDataInicio()).getStringData() +//6
                                                            "|" + neg.getHora() +//7
                                                            "|" + String.valueOf(neg.getLatitude()) +//8
                                                            "|" + String.valueOf(neg.getLongitude()) +//9
                                                            "|" + ne.getEmpresa().getCnpj()+//10-----24/04/2018
                                                            "\n";
                                            transfer.send(negativa);//envia negativação;
                                        }

                                        transfer.send("***FIM***\n");//FINALIZA TRANSMISSÃO
                                        transfer.getBufferedWriter().close();
                            if (!comands[1].equals("reenviar")) {//NÃO É RETRANSMISSÃO
                                for (Negativacao neg : lNegativ) {
                                    neg.setStatus("Transmitido");

                                    new NegativacaoRN(ctx).update(neg);

                                    /*
                                    SeqVisit seq = new SeqVisit();
                                    seq.setId(neg.getSeqVisitId());
                                    ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                                    if (arrLSq != null && arrLSq.size() > 0) {
                                        arrLSq.get(0).setStatus("");
                                        new SeqVisitRN(ctx).update(arrLSq.get(0));
                                    }*/
                                    if(isApagaStatus()) {//se true apaga o status do pedido
                                        SeqVisit seq = new SeqVisit();
                                        seq.setId(neg.getSeqVisitId());
                                        ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                                        if (arrLSq != null && arrLSq.size() > 0) {
                                            arrLSq.get(0).setStatus("");
                                            new SeqVisitRN(ctx).update(arrLSq.get(0));
                                        }
                                        new SeqVisitStatusRN(ctx).deletar(String.valueOf(seq.getId()));
                                    }
                                }
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        this.msgPostExec = "FALHA NA TRANSMISSÃO";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                /*************************NEGATIVAÇÃO**********************************/

                case Operations.UPLOAD_TO_SERV_OFF:
                            try {
                                deviceRegister = new RegistroRN(ctx).getRegistro();
                                this.msgPostExec = "";//não há erros
                                Pedido filterPed = new Pedido();
                                if(comands[1].equals("reenviar")){
                                    filterPed.setStatus("Transmitido");
                                    filterPed.setDel("S");//neste caso a opção para deletar(s) é usada para retransmitir
                                }else {
                                    filterPed.setStatus("Fechado");
                                }
                                filterPed.setIdEmpresa(Integer.parseInt(comands[2]));
                                arrLPed = new PedidoRN(ctx).filtrar(filterPed);
                                long totPedTransmitido = 0;//total transmitido
                                long totPedResta = arrLPed.size();//total de pedidos antes de transmitir
                                Log.i("FOI", arrLPed.size()+"--"+comands[1]);
                                if (arrLPed.size() > 0) {
                                    for (Pedido ped : arrLPed) {

                                        publishProgress(totPedTransmitido+"-" + ped.getId(), "TRANSMITINDO");
                                        Log.i("TRANSMITINDO", ped.getStatus());

                                        CurrentInfo.idPedido = ped.getId();
                                        CurrentInfo.codCli = ped.getCodCli();
                                        CurrentInfo.dataInicio = ped.getDataInicio();
                                        CurrentInfo.horaInicio = ped.getHoraInicio();
                                        CurrentInfo.dataFim = ped.getDataFim();
                                        CurrentInfo.horaFim = ped.getHoraFim();
                                        CurrentInfo.codFormPagamento = ped.getIdFormPg();
                                        CurrentInfo.prazo = ped.getPrazo();//prazo de pagamento

                                        ArrayList<ListaPedido> listPed = new ListaPedidoRN(ctx).getForNomeProd("");
                                        if(listPed.size() > 0) {//transmitte se existir algum intem no pedido
                                            String pedido = "p" +
                                                    "|" + String.valueOf(CurrentInfo.codCli) +//1
                                                    "|" + new Data(CurrentInfo.dataInicio).getStringData() +//2
                                                    "|" + CurrentInfo.horaInicio +//3
                                                    "|" + new Data(CurrentInfo.dataFim).getStringData() +//4
                                                    "|" + CurrentInfo.horaFim +//5
                                                    "|" + String.valueOf(ped.getQtParcela()) +//6 COMERCIAL PEDREIRA 1 PRODUTO
                                                    "|" + CurrentInfo.prazo +//7
                                                    "|" + CurrentInfo.codFormPagamento +//8
                                                    "|" + listPed.get(0).getCodVendedor() + "" +//codigo do vendedor
                                                    "|" + String.valueOf(ped.getLatitudeInicio()) +//10
                                                    "|" + String.valueOf(ped.getLongitudeInicio()) +//11
                                                    "|" + CurrentInfo.idPedido +//12
                                                    "|" + ped.getObs() +//13
                                                    "|"+deviceRegister.getKey()+//chave de registro do palm
                                                    // "|"+new Data(ped.getDataPagamento()).getStringData()+
                                                    "\n";//quebra de linha

                                            while ((transfer = connect(this.ip, this.porta, Operations.UPLOAD_TO_SERV_OFF)) == null) {
                                                publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
                                            }

                                            String rLocalServ = "";
                                            transfer.send(Operations.VERSAO_APP+"\n");//envia a versão do app, chave de resgitro e codigo do vendedor
                                            rLocalServ = transfer.getBufferedReader().readLine();//resposta da recepção da versão do app
                                            if (Operations.OK == Integer.parseInt(rLocalServ)) {
                                                //transfer.send(CurrentInfo.cnpjEmpresa + CurrentInfo.idPedido + ".txt\n");//nome do arquivo do pedid
                                                transfer.send(ped.getEmpresa().getCnpj() + CurrentInfo.idPedido + ".txt\n");//nome do arquivo do pedid

                                                rLocalServ = transfer.getBufferedReader().readLine();//lê resposta do servidor
                                                switch (Integer.parseInt(rLocalServ)) {
                                                    case Operations.IGNORA_PEDIDO:
                                                        transfer.send(Operations.CLOSE_CONNECTION + "\n");
                                                        transfer.getSocket().close();
                                                        break;
                                                    case Operations.OK:
                                                        transfer.send(pedido);//DADOS DO PEDIDO
                                                        for (ListaPedido lp : listPed) {
                                                            Log.i("COD_PROD", lp.getItemLista().getCodProd());
                                                            Log.i("TOTAL", lp.getItemLista().getTotal() + "");
                                                            String iten =
                                                                    "i" +
                                                                            "|" + lp.getItemLista().getCodProd() +//1
                                                                            //"|" + String.valueOf(lp.getItemLista().getQtd()) +//2
                                                                            "|" + String.valueOf(lp.getItemLista().getQtdUn()) + "/" + String.valueOf(lp.getItemLista().getQtd()) +//2
                                                                            "|" + String.valueOf(lp.getItemLista().getTotal()) +//3
                                                                            "|" + String.valueOf(lp.getItemLista().getDescAcreMone()) +//4
                                                                            //"|" + lp.getItemLista().getUnFrac() +//5
                                                                            "\n";
                                                            transfer.send(iten);//DADOS DO PEDIDO
                                                        }

                                                        if (comands[1].equals("reenviar")) {//verifica retransmissão
                                                            ped.setDel("n");
                                                            new PedidoRN(ctx).update(ped);
                                                        }

                                                        transfer.send("***FIM***\n");//FINALIZA TRANSMISSÃO
                                                        rLocalServ = transfer.getBufferedReader().readLine();//confirmação de upload completo para o servidor

                                                        if (rLocalServ.equals("***OK***")) {// || responseWebService.startsWith("Invalid query: Duplicate entry")) {
                                                            confirmTransmit(ped);//MUDA O STATUS DO PEDIDO PARA "TRANSMITIDO"
                                                            ++totPedTransmitido;
                                                            transfer.send(Operations.CLOSE_CONNECTION + "\n");
                                                            transfer.getSocket().close();

                                                            if (!comands[1].equals("reenviar")) {//não é retransmissão
                                                                //////////////////////////////////////////////////////////testando condição de apagar status do pedido//////////////////////////////////////////////////
                                                                if(isApagaStatus()) {//se true apaga o status do pedido

                                                                    Pedido pd = new Pedido();
                                                                    pd.setSeqVist_id(ped.getSeqVist_id());
                                                                    ArrayList<Pedido> arrLPd = new PedidoRN(ctx).getOpenOrClose(pd);
                                                                    Log.i("SEQSTAT", arrLPd.size() + "");
                                                                    if (arrLPd.size() == 0) {//nao tem pedidos abertos ou fechados
                                                                        SeqVisit seq = new SeqVisit();
                                                                        seq.setId(ped.getSeqVist_id());
                                                                        ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                                                                        if (arrLSq != null && arrLSq.size() > 0) {
                                                                            arrLSq.get(0).setStatus("");//apaga o status (P, N, A) que aparece na lista de clientes
                                                                            new SeqVisitRN(ctx).update(arrLSq.get(0));
                                                                        }
                                                                    }

                                                                    new SeqVisitStatusRN(ctx).deletar(String.valueOf(ped.getSeqVist_id()));
                                                                }
                                                            }
                                                  //          }

                                                        } else {
                                                            Log.i("ERRO_TRANSMIT", responseWebService);
                                                            this.msgPostExec = "ERRO PRCESSO LOCAL";
                                                        }
                                                        break;
                                                }
                                            }//resposta da verificação da versão do app
                                        }else{//DELETA OS PEDIDOS VAZIOS
                                            new PedidoRN(ctx).delPedidoForId(CurrentInfo.idPedido);
                                            SeqVisit seq = new SeqVisit();
                                            seq.setId(ped.getSeqVist_id());
                                            ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                                            if (arrLSq != null && arrLSq.size() > 0) {
                                                arrLSq.get(0).setStatus("");
                                                new SeqVisitRN(ctx).update(arrLSq.get(0));
                                            }
                                            //msgPostExec="EXISTE PEDIDO VAZIO";
                                        }
                                    }

                                filterPed = new Pedido();
                                filterPed.setStatus("Aberto");
                                arrLPed = new PedidoRN(ctx).filtrar(filterPed);
                                    msgPostExec =
                                            "Fechados: "+totPedResta+"\n" +
                                            "Enviados: "+totPedTransmitido+"\n"+
                                            "Abertos: "+arrLPed.size();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                msgPostExec = "FALHA NA TRANSMISSÃO";
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            /*************************NEGATIVAÇÃO**********************************/
                            try {
                                Negativacao ne = new Negativacao();
                                if(comands[1].equals("reenviar")){//FLAG QUE PERMITE A RETRANSMISSAO DA NEGATIVAÇÃO
                                    ne.setStatus("Transmitido/select");
                                }else {
                                    ne.setStatus("Espera");
                                }

                                ne.setIdEmpresa(Integer.parseInt(comands[2]));//EXPERIMENTAL 24/04/2018

                                ArrayList<Negativacao> lNegativ = new NegativacaoRN(ctx).getWithClients(ne);
                                long totNeg = lNegativ.size();//total de negativações para transmitir
                                long totNegTransmitido = 0;//total de negativações transmitidas
                                if (lNegativ.size() > 0) {
                                    Log.i("NEGATIVADOS", "existe negativacao");
                                    publishProgress("", "TRANSMITINDO NEGATIVAÇÕES");

                                    while ((transfer = connect(this.ip, this.porta, Operations.UPLOAD_TO_SERV_OFF)) == null) {
                                        publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
                                    }

                                    String rLocalServ = "";
                                    transfer.send(Operations.VERSAO_APP + "\n");//envia a versão do app
                                    rLocalServ = transfer.getBufferedReader().readLine();//resposta da recepção da versão do app
                                    if (Operations.OK == Integer.parseInt(rLocalServ)) {
                                        transfer.send("NEGA" + CurrentInfo.codVendedor + ".txt\n");//nome do arquivo de negativação

                                        rLocalServ = transfer.getBufferedReader().readLine();//lê resposta do servidor
                                        switch (Integer.parseInt(rLocalServ)) {
                                            case Operations.IGNORA_PEDIDO:
                                                transfer.send(Operations.CLOSE_CONNECTION + "\n");
                                                transfer.getSocket().close();
                                                break;
                                            case Operations.OK:
                                                String linha = "";
                                                for (Negativacao neg : lNegativ) {
                                                    publishProgress(neg.getCliente().getFantasia(), "PREPARANDO PARA TRANSMITIR");
                                                    String negativa =
                                                            "n" +//1
                                                                    "|" + neg.getVendedor().getCodigo() +//2
                                                                    "|" + String.valueOf(neg.getCodRota()) +//3
                                                                    "|" + String.valueOf(neg.getCodCli()) +//4
                                                                    "|" + neg.getCodJustf() +//5
                                                                    "|" + new Data(neg.getDataInicio()).getStringData() +//6
                                                                    "|" + neg.getHora() +//7
                                                                    "|" + String.valueOf(neg.getLatitude()) +//8
                                                                    "|" + String.valueOf(neg.getLongitude()) +//9
                                                                    "|" + neg.getEmpresa().getCnpj()+//10-----24/04/2018
                                                                    "\n";
                                                    transfer.send(negativa);
                                                    ++totNegTransmitido;
                                                }
                                                transfer.send("k|"+deviceRegister.getKey()+"\n");//chave de registro do palm
                                                transfer.send("***FIM***\n");//FINALIZA TRANSMISSÃO
                                                rLocalServ = transfer.getBufferedReader().readLine();//confirmação de upload completo para o servidor

                                                if (rLocalServ.equals("***OK***")) {// || responseWebService.startsWith("Invalid query: Duplicate entry")) {
                                                    //if (!comands[1].equals("reenviar")) {//não é retransmissão
                                                        SeqVisit seq = new SeqVisit();
                                                        for (Negativacao neg : lNegativ) {
                                                            neg.setStatus("Transmitido");

                                                            new NegativacaoRN(ctx).update(neg);

                                                            //SeqVisit seq = new SeqVisit();
                                                            //////////////////////////////////////////////////////////testando condição de apagar status do pedido//////////////////////////////////////////////////
                                                            if(isApagaStatus()) {//se true apaga o status do pedido

                                                                seq.setId(neg.getSeqVisitId());
                                                                ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                                                                if (arrLSq != null && arrLSq.size() > 0) {
                                                                    arrLSq.get(0).setStatus("");
                                                                    new SeqVisitRN(ctx).update(arrLSq.get(0));
                                                                }
                                                                new SeqVisitStatusRN(ctx).deletar(String.valueOf(seq.getId()));
                                                            }
                                                        }
                                                    //}
                                                    transfer.send(Operations.CLOSE_CONNECTION + "\n");
                                                    transfer.getSocket().close();
                                                } else {
                                                    this.msgPostExec = "ERRO NO SERVIDOR";
                                                }
                                                break;
                                        }
                                    }
                                    msgPostExec += "\nTOTAL NEGATVADO: "+totNeg+"\n"+
                                    "NEGATIVADO ENVIADO: "+totNegTransmitido;
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                this.msgPostExec = "FALHA NA TRANSMISSÃO";
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                    break;
                case Operations.EXPORT_PEDIDOS_INTERNET:
                    Registro r = new RegistroRN(ctx).getRegistro();

                    HttpTransfer httpTransfer = connect("http://sitsys.com.br/webservice/w6.php?chave=" + r.getKey());
                    if(cancelTask){//operação cancelada
                        break;
                    }
                    /**
                     * CONTROLE DE LICENÇA
                     */
                    data = consulKey(httpTransfer);//verifica licença online
                    String[] licenca = consulLicence();
                    publishProgress("", "VERIVAÇÃO DE LICENÇA");
                    if(licenca != null) {//conectou com o servidor
                        if(licenca[5].equals("A")) {//licença ativa
                            Calendar cLicence = Calendar.getInstance();
                            cLicence.set(Integer.parseInt(licenca[3].split("-")[0]), Integer.parseInt(licenca[3].split("-")[1]), Integer.parseInt(licenca[3].split("-")[2]), 0, 0, 0);//data de vencimento da licença
                            cLicence.add(Calendar.DAY_OF_MONTH, 10);//o app para de transmitir após 10 diaz de atraso
                            long dHoje = new Data(Calendar.getInstance().getTimeInMillis()).getOnlyDataInMillis();
                            //Data dVence = new Data(licenca[3].split("-")[2] + "/" + licenca[3].split("-")[1] + "/" + licenca[3].split("-")[0]);//data vencimento
                            Data dVence = new Data(cLicence.getTimeInMillis());//data vencimento
                            if (dHoje <= dVence.getOnlyDataInMillis()) {
                                if (data == null || !r.getCnpjEmpresa().equals(data[0])) {
                                    if (!msgPostExec.equals("SEM RESPOSTA DA REDE")) {
                                        msgPostExec = "CHAVE INVÁLIDA";
                                    }
                                } else if (data != null || r.getCnpjEmpresa().equals(data[0])) {
                                    try {
                                        this.msgPostExec = "OK";//não há erros
                                        Pedido filterPed = new Pedido();
                                        if (comands[1].equals("reenviar")) {//ESTA FLAG PERMITE REENVIAR DADOS/////////////////
                                            filterPed.setStatus("Transmitido");
                                            filterPed.setDel("S");//neste caso a opção para deletar(s) é usada para retransmitir
                                        } else {
                                            filterPed.setStatus("Fechado");
                                        }

                                        filterPed.setIdEmpresa(Integer.parseInt(comands[2]));//EXPERIMENTAL 24/04/2018

                                        arrLPed = new PedidoRN(ctx).filtrar(filterPed);
                                        long totPedTransmitido = 0;//total transmitido
                                        long totPedResta = arrLPed.size();//total de pedidos antes de transmitir
                                        if (arrLPed.size() > 0) {
                                            for (Pedido ped : arrLPed) {
                                                publishProgress(totPedTransmitido + "-" + ped.getId(), "TRANSMITINDO");
                                                Log.i("TRANSMITINDO", ped.getStatus());

                                                CurrentInfo.idPedido = ped.getId();
                                                CurrentInfo.codCli = ped.getCodCli();
                                                CurrentInfo.dataInicio = ped.getDataInicio();
                                                CurrentInfo.horaInicio = ped.getHoraInicio();
                                                CurrentInfo.dataFim = ped.getDataFim();
                                                CurrentInfo.horaFim = ped.getHoraFim();
                                                CurrentInfo.codFormPagamento = ped.getIdFormPg();
                                                CurrentInfo.prazo = ped.getPrazo();//prazo de pagamento

                                                ArrayList<ListaPedido> listPed = new ListaPedidoRN(ctx).getForNomeProd("");

                                                String pedido = "p" +//1
                                                        "|" + String.valueOf(CurrentInfo.codCli) +//2
                                                        "|" + new Data(CurrentInfo.dataInicio).getStringData() +//3
                                                        "|" + CurrentInfo.horaInicio +//4
                                                        "|" + new Data(CurrentInfo.dataFim).getStringData() +//5
                                                        "|" + CurrentInfo.horaFim +//6
                                                        "|" + String.valueOf(ped.getQtParcela()) +//7
                                                        "|" + CurrentInfo.prazo +//8
                                                        "|" + CurrentInfo.codFormPagamento +//9
                                                        "|" + listPed.get(0).getCodVendedor() + "" +//10
                                                        "|" + String.valueOf(ped.getLatitudeInicio()) +//11
                                                        "|" + String.valueOf(ped.getLongitudeInicio()) +//12
                                                        "|" + CurrentInfo.idPedido +//13
                                                        "|" + ped.getObs() +//14
                                                        "|"+r.getKey()+//chave de registro do palm
                                                        "%0A";//quebra de linha

                                                String itens = "";
                                                for (ListaPedido lp : listPed) {
                                                    String qtdUn = String.valueOf(lp.getItemLista().getQtdUn());
                                                    itens += "i" +
                                                            "|" + lp.getItemLista().getCodProd() +//1
                                                            "|" + String.valueOf(lp.getItemLista().getQtdUn()) + "/" + String.valueOf(lp.getItemLista().getQtd()) +//2
                                                            "|" + String.valueOf(lp.getItemLista().getTotal()) +//3
                                                            "|" + String.valueOf(lp.getItemLista().getDescAcreMone()) +//4
                                                            "%0A";//quebra de linha
                                                }

                                                pedido += itens;
                                                pedido = "%22" + pedido + "%22";

                                                Log.i("XML", pedido);
                                                if (comands[1].equals("reenviar")) {/////////ESTE PROCESSO APAGA OS DADOOS DO SERVIDOR ONLINE ANTES DE TRANSMITIR
                                                    this.msgPostExec = "OK";//não há erros
                                                    publishProgress("PEDIDO: " + pedido, "PREPARANDO RETRANSMISSÃO");
                                                    httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=delete%20from%20sitvirp%20where%20vichave='" + CurrentInfo.cnpjEmpresa + CurrentInfo.idPedido + "'%20and%20vicnpj='" + CurrentInfo.cnpjEmpresa + "'");
                                                    responseWebService = httpTransfer.getBufReader().readLine();
                                                    if (responseWebService.equals("***OK***")) {//confimação da operação
                                                        if (enviarPedido(pedido, ped, comands)) {
                                                            ++totPedTransmitido;
                                                        }

                                                        Log.i("PPP", CurrentInfo.idPedido + "");
                                                    } else if (responseWebService.contains("Connection timed")) {
                                                        this.msgPostExec = "SERVIDOR INDISPONÍVEL";
                                                        Log.i("TIMED_OUT", responseWebService);
                                                        break;
                                                    } else {
                                                        Log.i("ERRO_TRANSMIT", responseWebService);
                                                        this.msgPostExec = "ERRO PRCESSO WEB";
                                                        break;
                                                    }
                                                } else {//ENVIO NORMAL
                                                    if (enviarPedido(pedido, ped, comands)) {
                                                        ++totPedTransmitido;
                                                    }
                                                }
                                            }

                                            filterPed = new Pedido();
                                            filterPed.setStatus("Aberto");
                                            arrLPed = new PedidoRN(ctx).filtrar(filterPed);
                                            msgPostExec =
                                                    "Fechados: " + totPedResta + "\n" +
                                                            "Enviados: " + totPedTransmitido + "\n" +
                                                            "Abertos: " + arrLPed.size();
                                        }
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                        msgPostExec = "FALHA NA TRANSMISSÃO";
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
/****************************************************NEGATIVAÇÃO ONLINE***************************************************************************/
                                    try {
                                        Negativacao ne = new Negativacao();
                                        if(comands[1].equals("reenviar")){//FLAG QUE PERMITE A RETRANSMISSAO DA NEGATIVAÇÃO
                                            ne.setStatus("Transmitido/select");
                                        }else {
                                            ne.setStatus("Espera");
                                        }

                                        ne.setIdEmpresa(Integer.parseInt(comands[2]));//EXPERIMENTAL 24/04/2018
                                        //ne.setStatus("Espera");
                                        ArrayList<Negativacao> lNegativ = new NegativacaoRN(ctx).getWithClients(ne);
                                        long totNeg = lNegativ.size();//total de negativações para transmitir
                                        long totNegTransmitido = 0;//total de negativações transmitidas
                                        if (lNegativ.size() > 0) {
                                            Log.i("NEGATIVADOS", "existe negativacao");

                                            String negativa = "";
                                            for (Negativacao neg : lNegativ) {
                                                publishProgress(neg.getCliente().getFantasia(), "PREPARANDO PARA TRANSMITIR");
                                                negativa +=
                                                        "n" +//1
                                                                "|" + neg.getVendedor().getCodigo() +//2
                                                                "|" + String.valueOf(neg.getCodRota()) +//3
                                                                "|" + String.valueOf(neg.getCodCli()) +//4
                                                                "|" + neg.getCodJustf() +//5
                                                                "|" + new Data(neg.getDataInicio()).getStringData() +//6
                                                                "|" + neg.getHora() +//7
                                                                "|" + String.valueOf(neg.getLatitude()) +//8
                                                                "|" + String.valueOf(neg.getLongitude()) +//9
                                                                "|" + ne.getEmpresa().getCnpj()+//10-----24/04/2018
                                                                "%0A";
                                                ++totNegTransmitido;

                                            }
                                            negativa = "%22" + negativa+"k|"+r.getKey()+"%0A"+"%22";//ultima linha, campo com chave de registro do palm
                                                publishProgress("", "TRANSMITINDO NEGATIVAÇÕES");
                                                /*negativa =
                                                        "n" +//1
                                                                "|" + neg.getVendedor().getCodigo() +//2
                                                                "|" + String.valueOf(neg.getCodRota()) +//3
                                                                "|" + String.valueOf(neg.getCodCli()) +//4
                                                                "|" + neg.getCodJustf() +//5
                                                                "|" + new Data(neg.getDataInicio()).getStringData() +//6
                                                                "|" + neg.getHora() +//7
                                                                "|" + String.valueOf(neg.getLatitude()) +//8
                                                                "|" + String.valueOf(neg.getLongitude()) +//9
                                                                "|"+deviceRegister.getKey()+//chave de registro do palm
                                                                "%0A";
                                                negativa = "%22" + negativa + "%22";
                                                */
                                                Log.i("XML", negativa);

                                                if (comands[1].equals("reenviar")) {/////////ESTE PROCESSO APAGA OS DADOOS DO SERVIDOR ONLINE ANTES DE TRANSMITIR
                                                    this.msgPostExec = "OK";//não há erros
                                                    publishProgress("NEGATIVAÇÕES: " + negativa, "PREPARANDO RETRANSMISSÃO");

                                                    httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=delete%20from%20sitvirp%20where%20vichave='" + CurrentInfo.cnpjEmpresa + CurrentInfo.codVendedor + lNegativ.get(lNegativ.size() - 1).getId() + "'%20and%20vicnpj='" + CurrentInfo.cnpjEmpresa + "'");
                                                    //httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=delete%20from%20sitvirp%20where%20vichave='" + CurrentInfo.cnpjEmpresa + CurrentInfo.codVendedor + neg.getId() + "'%20and%20vicnpj='" + CurrentInfo.cnpjEmpresa + "'");
                                                    responseWebService = httpTransfer.getBufReader().readLine();
                                                    if (responseWebService.equals("***OK***")) {//confimação da operação
                                                        //enviarNegativacao(lNegativ, negativa, comands);
                                                        if (enviarNegativacao(lNegativ, negativa, comands)) {
                                                            msgPostExec += "\nTOTAL NEGATVADO: " + totNeg + "\n" +
                                                                    "NEGATIVADO ENVIADO: " + totNegTransmitido;
                                                        }

                                                        Log.i("PPP", CurrentInfo.idPedido + "");
                                                    } else if (responseWebService.contains("Connection timed")) {
                                                        this.msgPostExec = "SERVIDOR INDISPONÍVEL";
                                                        Log.i("TIMED_OUT", responseWebService);
                                                        break;
                                                    } else {
                                                        Log.i("ERRO_TRANSMIT", responseWebService);
                                                        this.msgPostExec = "ERRO PRCESSO WEB";
                                                        break;
                                                    }
                                                } else {//ENVIO NORMAL
                                                    if (enviarNegativacao(lNegativ, negativa, comands)) {
                                                        msgPostExec += "\nTOTAL NEGATVADO: " + totNeg + "\n" +
                                                                "NEGATIVADO ENVIADO: " + totNegTransmitido;
                                                    }
                                                }
                                        }
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                        this.msgPostExec = "FALHA NA TRANSMISSÃO";
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                msgPostExec = "A LICENÇA EXPIROU";
                            }
                        }else{
                            msgPostExec = "LICENÇA INATIVA";
                        }
                    }else{
                        msgPostExec = "FALHA DE CONEXÃO COM O SERVIDOR WEB";
                    }
                    break;
                case Operations.DOWNLOAD_INTERNET:
                    //dialog.setTitle("IMPORTANDO");
                    tabelas = new String[]{"L", "F", "P", "C", "V", "R", "O", "B", "D", "E", "Z", "J", "K"};

                    for(String t :tabelas) {
                        HttpTransfer httpTrans = null;
                        while((httpTrans = connect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa)) == null) {
                            if(cancelTask){
                                break;
                            }
                            publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
                        }

                        if(!cancelTask) {
                            ++totalConections;
                            if (totalConections == 1) {//faz o drop só na primeira conexao
                                new DropCreateDatabaseDirectAccess().dropTables(ctx, new DropCreateDatabase());//apaga algumas tabelas
                            }

                            PreencheObjeto preencheObj = new PreencheObjeto();
                            receiverDataOnLine(httpTrans, t, preencheObj);
                        }else{
                            break;
                        }
                    }

                    updateStatus();//mantem os status dos pedidos e clientes após receber carga

                    totalConections = 0;//para fazer drop na primeira conexao
                    break;
                case Operations.CONSUL_SAL_PROD:
                    publishProgress("", "CONSULTANDO");
                    try {
                        httpTransfer = new HttpTransfer("http://www.sitsys.com.br/webservice/w5.php?query=VICNPJ="+CurrentInfo.cnpjEmpresa+"%20and%20VIPROD="+comands[1]);
                        String carga = "";

                        while (!(carga = httpTransfer.getBufReader().readLine()).equals("***FIM***")) {
                            if (carga.endsWith("|")) {
                                carga += "***FIM***";
                            }
                            String[] cols = carga.split("\\|");

                            saldProd = cols[1];
                            Log.i("CNPJ_WEB", cols[0]);
                            Log.i("SALDO_WEB", cols[1]);
                            carga = null;
                            msgPostExec = "OK";
                        }
                    }catch(IOException ex){
                        msgPostExec = "FALHA DE CONEXÃO";
                    }
                    break;
                case Operations.CONSUL_LICENCA_CLI:
                    /*StringBuilder sb = new StringBuilder(CurrentInfo.cnpjEmpresa);
                    sb.insert(2, ".");
                    sb.insert(6, ".");
                    sb.insert(10, "/");
                    sb.insert(15, "-");
                    httpTransfer = connect("http://sitsys.com.br/webservice/licenca.php?cnpj="+sb.toString());//+CurrentInfo.cnpjEmpresa);
                    responseWebService = httpTransfer.getBufReader().readLine();
                    Log.i("LICLI", responseWebService);
                    String linha[] = responseWebService.split("\\|");
                    if(linha[5].equals("A")){//A=licença ativa; I=licença inativa

                    }else if(responseWebService.contains("Connection timed")){
                        this.msgPostExec = "SERVIDOR INDISPONÍVEL";
                        Log.i("TIMED_OUT", responseWebService);
                    }else{
                        Log.i("ERRO_TRANSMIT", responseWebService);
                        this.msgPostExec = "ERRO PRCESSO WEB";
                    }*/
                    break;
                case Operations.CONSUL_KEY:
                    //funciona if(!con.getKey().equals("--") && con.getKey().split("-").length == 3) {
                    if(!deviceRegister.getKey().equals("--") && deviceRegister.getKey().split("-").length == 3) {
                        publishProgress("", "AUTENTICANDO");
                        try {
                            validKey();
                        } catch (IOException e) {
                            this.msgPostExec = "FALHA NA AUTENTICAÇÃO";
                            e.printStackTrace();
                        }
                    }
                    break;
                case Operations.DELELAR_PEDIDO:
                    r = new RegistroRN(ctx).getRegistro();

                    httpTransfer = connect("http://sitsys.com.br/webservice/w6.php?chave=" + r.getKey());
                    if(cancelTask){//operação cancelada
                        break;
                    }
                    data = consulKey(httpTransfer);

                    if(data == null || !r.getCnpjEmpresa().equals(data[0])) {
                        if(!msgPostExec.equals("SEM RESPOSTA DA REDE")){
                            msgPostExec = "CHAVE INVÁLIDA";
                        }
                    }else  if(data != null || r.getCnpjEmpresa().equals(data[0])){//CNPJ CONFIRMADO
                        try {
                            this.msgPostExec = "OK";//não há erros
                            httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=delete%20from%20sitvirp%20where%20vichave=%22"+CurrentInfo.cnpjEmpresa+CurrentInfo.idPedido+"%22%20and%20vicnpj=%22"+CurrentInfo.cnpjEmpresa+"%22");
                            responseWebService = httpTransfer.getBufReader().readLine();
                            if (responseWebService.equals("***OK***")){//confimação da operação
                                msgPostExec = "RE";
                            }else if(responseWebService.contains("Connection timed")){
                                this.msgPostExec = "SERVIDOR INDISPONÍVEL";
                                Log.i("TIMED_OUT", responseWebService);
                            }else{
                                Log.i("ERRO_TRANSMIT", responseWebService);
                                this.msgPostExec = "ERRO PRCESSO WEB";
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            msgPostExec = "FALHA NA TRANSMISSÃO";
                        }
                    }
                    break;
            }
       /* } catch (IOException e) {
            e.printStackTrace();*/
        } catch (SQLException e) {
            e.printStackTrace();
       /* }finally {
            try {
                if(transfer != null) {
                    transfer.closeConnect();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isApagaStatus() {
        return apagaStatus;
    }

    public void setApagaStatus(boolean apagaStatus) {
        this.apagaStatus = apagaStatus;
    }

    private boolean enviarNegativacao(ArrayList<Negativacao> lNegativ, String negativa, String[] comands) throws IOException, SQLException {
        boolean enviado = false;
        HttpTransfer httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=insert%20into%20sitvirp(vichave,vicnpj,%20vixml,%20sr_deleted,%20viimp)%20values(" + CurrentInfo.cnpjEmpresa + CurrentInfo.codVendedor + lNegativ.get(lNegativ.size() - 1).getId() + "," + CurrentInfo.cnpjEmpresa + "," + negativa + ",%20false,%20false)");
        //HttpTransfer httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=insert%20into%20sitvirp(vichave,vicnpj,%20vixml,%20sr_deleted)%20values(" + CurrentInfo.cnpjEmpresa + CurrentInfo.codVendedor + neg.getId() + "," + CurrentInfo.cnpjEmpresa + "," + negativa + ",%20false)");
        responseWebService = httpTransfer.getBufReader().readLine();
        publishProgress("", "TRANSMITINDO NEGATIVAÇÕES");
        if (responseWebService.equals("***OK***")) {
            enviado = true;
            SeqVisit seq = new SeqVisit();
            for (Negativacao neg : lNegativ) {
                neg.setStatus("Transmitido");
                new NegativacaoRN(ctx).update(neg);

                if (!comands[1].equals("reenviar")) {//não é retransmissão

//////////////////////////////////////////////////////////testando condição de apagar status do pedido//////////////////////////////////////////////////
                    if(isApagaStatus()) {//se true apaga o status do pedido
                    //    if (comands[1].equals("reenviar")) {
                            //SeqVisit seq = new SeqVisit();
                            seq.setId(neg.getSeqVisitId());
                            ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                            if (arrLSq != null && arrLSq.size() > 0) {
                                arrLSq.get(0).setStatus("");
                                new SeqVisitRN(ctx).update(arrLSq.get(0));
                            }
                            new SeqVisitStatusRN(ctx).deletar(String.valueOf(seq.getId()));
                        }
                    }
            }
            /*
                neg.setStatus("Transmitido");
                new NegativacaoRN(ctx).update(neg);

                //SeqVisit seq = new SeqVisit();
                seq.setId(neg.getSeqVisitId());
                ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
                if (arrLSq != null && arrLSq.size() > 0) {
                    arrLSq.get(0).setStatus("");
                    new SeqVisitRN(ctx).update(arrLSq.get(0));
                }
                new SeqVisitStatusRN(ctx).deletar(String.valueOf(seq.getId()));
                */
            Log.i("RETORNO WEBSERVICE", responseWebService);
        } else {
            Log.i("ERRO_TRANSMIT", responseWebService);
            //Utils.showMsg(ctx, "ERRO", "ERRO AO TRANSMITIR", R.drawable.dialog_error);
            msgPostExec = "ERRO AO TRANSMITIR NEGATIVAÇOÃ";
        }

        return enviado;
    }

    private boolean enviarPedido(String pedido, Pedido ped, String[] comands) throws IOException, SQLException {
        boolean enviado = false;
        HttpTransfer httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=insert%20into%20sitvirp(vichave,vicnpj,%20vixml,%20sr_deleted,%20viimp)%20values(%22"+CurrentInfo.cnpjEmpresa+CurrentInfo.idPedido+"%22,%22"+CurrentInfo.cnpjEmpresa+"%22,"+pedido+",%20false,%20false)");//CurrentInfo.idPedido+"%22,%22"+CurrentInfo.cnpjEmpresa+"%22,"+pedido+",%20false)");
        responseWebService = httpTransfer.getBufReader().readLine();
        if (responseWebService.equals("***OK***")){// || responseWebService.startsWith("Invalid query: Duplicate entry")) {
            enviado = true;
            try {
                confirmTransmit(ped);
                if (!comands[1].equals("reenviar")) {//não é retransmissão
                    Pedido pd = new Pedido();
                    pd.setSeqVist_id(ped.getSeqVist_id());
                    //ArrayList<Pedido> arrLPd = new PedidoRN(ctx).getOpenOrClose(pd);
//////////////////////////////////////////////////////////testando condição de apagar status do pedido//////////////////////////////////////////////////
//                    if(isApagaStatus()) {//se true apaga o status do pedido
//                        ArrayList<Pedido> arrLPd = new PedidoRN(ctx).getOpenOrClose(pd);
//                        Log.i("SEQSTAT", arrLPd.size() + "");
//                        if (arrLPd.size() == 0) {//nao tem pedidos abertos ou fechados
//                            SeqVisit seq = new SeqVisit();
//                            seq.setId(ped.getSeqVist_id());
//                            ArrayList<SeqVisit> arrLSq = new SeqVisitRN(ctx).pesquisar(seq);
//                            if (arrLSq != null && arrLSq.size() > 0) {
//                                arrLSq.get(0).setStatus("");
//                                new SeqVisitRN(ctx).update(arrLSq.get(0));
//                            }
//                        }
//                        new SeqVisitStatusRN(ctx).deletar(String.valueOf(pd.getSeqVist_id()));
//                    }//se true apaga o status do pedido
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(responseWebService.startsWith("Invalid query: Duplicate entry")) {
            Log.i("ERRO_TRANSMIT", responseWebService);
            publishProgress("PEDIDO: " + ped.getId(), "EVITANDO DUPLICIDADE");
            publishProgress("PEDIDO: " + pedido, "PREPARANDO RETRANSMISSÃO");
            httpTransfer = new HttpTransfer("http://sitsys.com.br/webservice/w4.php?query=delete%20from%20sitvirp%20where%20vichave='" + CurrentInfo.cnpjEmpresa + CurrentInfo.idPedido + "'%20and%20vicnpj='" + CurrentInfo.cnpjEmpresa + "'");
            responseWebService = httpTransfer.getBufReader().readLine();
            if (responseWebService.equals("***OK***")) {//confimação da operação

                 if(enviarPedido(pedido, ped, comands)){
                     enviado = true;
                 }


                Log.i("PPP", CurrentInfo.idPedido + "");
            } else if (responseWebService.contains("Connection timed")) {
                this.msgPostExec = "SERVIDOR INDISPONÍVEL";
                Log.i("TIMED_OUT", responseWebService);

            } else {
                Log.i("ERRO_TRANSMIT", responseWebService);
                this.msgPostExec = "ERRO PRCESSO WEB";

            }
           //11 confirmTransmit(ped);
        }else if(responseWebService.contains("Connection timed")){
            this.msgPostExec = "SERVIDOR INDISPONÍVEL";
            Log.i("TIMED_OUT", responseWebService);
        }else{
            Log.i("ERRO_TRANSMIT", responseWebService);
            this.msgPostExec = "ERRO PRCESSO WEB";
        }

        return enviado;
    }

    private void updateStatus() throws SQLException {
        ArrayList<SeqVisit> arrLSeqVisit = new SeqVisitRN(ctx).pesquisar(new SeqVisit());
        SeqVisitStatus seqVisitStatus = new SeqVisitStatus();
        SeqVisit seqVisit = new SeqVisit();
        for(SeqVisit seq: arrLSeqVisit){
            seqVisitStatus.setSeq_id(String.valueOf(seq.getId()));
            ArrayList<SeqVisitStatus> lSeqVStat = new SeqVisitStatusRN(ctx).pesquisar(seqVisitStatus);
            if(lSeqVStat.size() > 0){
                boolean fechado = false;
                for(SeqVisitStatus sqVStat: lSeqVStat){
                    if(sqVStat.getStatus().equals("P")){
                        fechado = true;
                        break;
                    }
                }

                seqVisit.setId(seq.getId());
                publishProgress(seqVisit.getId()+"", "ATUALIZANDO STATUS");
                if(fechado){
                    seqVisit.setStatus("P");
                }else{
                    seqVisit.setStatus(lSeqVStat.get(0).getStatus());
                }
                new SeqVisitRN(ctx).update(seqVisit);
            }
        }
    }

    /**
     * altera o status do pedido para transmitido
     * @param ped
     * @throws SQLException
     */
    private void confirmTransmit(Pedido ped) throws SQLException{
        //for (Pedido p : arrLPed) {
        ped.setStatus("Transmitido");
        new PedidoRN(ctx).update(ped);
        publishProgress("PEDIDO " + ped.getId(), "TRANSMITIDO");
    }

    private HttpTransfer connect(String URL){
        while(!Utils.verifiNetConnection(ctx)){
            if(cancelTask){//se true, cancela
                break;
            }
            publishProgress("TENTANDO RECONECTAR", "FALHA NA CONEXÃO");
        }
        HttpTransfer httpTrans = null;
        if(!cancelTask) {//se true, cancela
            try {
                httpTrans = new HttpTransfer(URL);

       /* }catch(NullPointerException ex){
            ex.printStackTrace();
            publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
            httpTrans = null;
            httpTrans = connect(URL);*/
            } catch (ConnectException ex) {
                ex.printStackTrace();
                publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");

                try {
                    if (httpTrans != null) {
                        httpTrans.disconnect();
                        httpTrans.openConnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                //publishProgress("FALHA NA CONEXÃO", "ERRO");
                httpTrans = null;
            /*try {
                if(httpTrans != null) {
                    httpTrans = null;
                    httpTrans.disconnect();
                    httpTrans.openConnect();
                    //httpTrans = openConnect(URL);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            }
        }
        return httpTrans;
    }

    private Transfer connect(String ip, int porta, int operacao){
        try{
            socket = new Socket(this.ip, this.porta);
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(socket.getInputStream());

            transfer = new Transfer();
            transfer.setSocket(socket);
            transfer.setOutput(this.output);
            transfer.setInput(this.input);

            Log.i("SOCKET", "SOCKET");

            transfer.send(operacao + "\n");
           // transfer.send(codVendedor + "\n");
       /* }catch(NullPointerException ex){
            ex.printStackTrace();
            publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
            httpTrans = null;
            httpTrans = connect(URL);*/
        }catch(ConnectException ex){
            ex.printStackTrace();
            publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
/*
            try {
                if(httpTrans != null) {
                    httpTrans.disconnect();
                    httpTrans.openConnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }catch(IOException ex){
            //publishProgress("FALHA NA CONEXÃO", "ERRO");
          //  httpTrans = null;
            /*try {
                if(httpTrans != null) {
                    httpTrans = null;
                    httpTrans.disconnect();
                    httpTrans.openConnect();
                    //httpTrans = openConnect(URL);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        return transfer;
    }
    String carga = "";
    boolean statusLeitura = false;

    private void receiverDataOnLine(HttpTransfer httpTrans, String t, PreencheObjeto preencheObj) throws SQLException {
        int totalProd = 0;

        if(httpTrans != null) {
                try{
                while (true){
                 /*   handler.postDelayed(new Runnable(){
                        public void run(){
                            if(!statusLeitura) {
                                System.out.println(statusLeitura);
                                throw new NullPointerException();
                            }else{
                                System.out.println(statusLeitura);
                            }
                            Thread.currentThread().interrupt();
                        }
                    }, 120000);*/
                    Log.i("ANTES", "ANTES"+httpTrans.getConnection().getResponseCode());
                    carga = httpTrans.getBufReader().readLine();
                    System.out.println(carga);
                    statusLeitura = true;//leitura feita com sucesso
                    Log.i("DEPOIS", "DEPOIS");
                    if(carga == null){
                        publishProgress("TENTANDO RECONECTAR", "SERVIDOR OFFLINE, FALHA DE CONEXÃO");
                        continue;
                    }else if(!carga.equals("***FIM***")){
                        Log.i("TABELA", t);
                        this.msgPostExec = "CARGA RECEBIDA";//o cnpj não é inválido caso contrario a msg default se mantem

                        if (carga.endsWith("|")) {
                            carga += "***FIM***";
                        }else if(carga.contains("Connection timed outConnection timed out")){
                            this.msgPostExec = "SERVIDOR WEB INDISPOSÍVEL";
                            publishProgress("", carga);
                            continue;
                        }
                        String[] cols = carga.split("\\|");

                        if (t.equals("P")) {
                            Produto p = preencheObj.extraiProduto(cols);//extrai cada produto
                            new ProdutoRN(ctx).salvar(p);
                            publishProgress(p.getNome(), "DOWNLOAD PRODUTOS");
                            totalProd++;
                        } else if (t.equals("L")) {
                            Linha l = preencheObj.extraiLinha(cols);
                            new LinhaRN(ctx).salvar(l);
                            publishProgress(l.getDescricao(), "DOWNLOAD LINHAS");
                        } else if (t.equals("F")) {
                            FormPgment formPg = preencheObj.extraiFormPgment(cols);
                            new FormPgmentRN(ctx).salvar(formPg);
                            publishProgress(formPg.getDescricao(), "DOWNLOAD FORMA PAGAMENTO");
                        } else if (t.equals("C")) {
                            Cliente cli = preencheObj.extraiClie(cols);
                            new ClienteRN(ctx).salvar(cli);
                            publishProgress(cli.getFantasia(), "DOWNLOAD CLIENTES");
                        } else if (t.equals("V")) {
                            Vendedor v = preencheObj.extraiVendedor(cols);
                            new VendedorRN(ctx).salvar(v);
                            publishProgress(v.getNome(), "DOWNLOAD VENDEDOR");
                        } else if (t.equals("R")) {
                            ContReceb contReceb = preencheObj.extraiContReceb(cols);
                            new ContRecebRN(ctx).salvar(contReceb);
                            publishProgress(contReceb.getNumTitulo(), "DOWNLOAD CONTAS A RECEBER");
                        } else if (t.equals("O")) {
                            Fornecedor f = preencheObj.extraiFornecedor(cols);
                            new FornecedorRN(ctx).salvar(f);
                            publishProgress(f.getNome(), "DOWNLOAD FORNECEDORES");
                        } else if (t.equals("B")) {
                            Rota rota = preencheObj.extraiRota(cols);
                            new RotaRN(ctx).salvar(rota);
                            publishProgress(rota.getDescricao(), "DOWNLOAD ROTAS");
                        } else if (t.equals("D")) {
                            SeqVisit seqVisit = preencheObj.extraiSeqVisit(cols);
                            new SeqVisitRN(ctx).salvar(seqVisit);
                            publishProgress(String.valueOf(seqVisit.getCodRota()), "DOWNLOAD SEQUENCIA DE VISITAS");
                        } else if (t.equals("Z")) {
                            Configuracao configuracao = preencheObj.extraiConFiguracao(cols);
                            new ConfiguracaoRN(ctx).salvar(configuracao);
                            publishProgress(String.valueOf(configuracao.getId()), "DOWNLOAD CONFIGURAÇÕES");
                        } else if (t.equals("J")) {
                            Justificativa justfi = preencheObj.extraiJustificativa(cols);
                            new JustificativaRN(ctx).salvar(justfi);
                            publishProgress(justfi.getDescricao(), "DOWNLOAD JUSTIFICATIVAS");
                        } else if (t.equals("E")) {//comodato
                            Comodato como = preencheObj.extractComodato(cols);
                            new ComodatoRN(ctx).salvar(como);
                            publishProgress(como.getCodProd(), "DOWNLOAD COMODATO");
                        }else if(t.equals("K")){//DOWNLOAD EMPRESAS
                            Empresa emp = preencheObj.extractEmpresa(cols);
                            new EmpresaRN(ctx).save(emp);
                            publishProgress(emp.getFantasia(), "DOWNLOAD EMPRESAS");
                        }
                    }else if(carga.equals("***FIM***")){
                        httpTrans.getBufReader().close();
                        Log.i("TUPL", "CLOSED CONNECTION");
                        break;
                    }
                    statusLeitura = false;//aguardando nova leitura
                  //carga = "";
                   // carga = "NEW";//pronto para nova leitura
                }
//                Log.i("INVÁLIDO", carga);
  //              Log.i("TOT_PROD", totalProd + "");
//                httpTrans.getBufReader().close();
            }catch(NullPointerException ex) {
                    try {
                        httpTrans.getBufReader().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //ex.printStackTrace();
               // publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
//Connection timed outConnection timed out***FIM***
                //httpTrans = null;
                //httpTrans = openConnect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);

                //preencheObj = null;
                //receiverDataOnLine(httpTrans, t, preencheObj);
            }catch(ConnectException ex){
                try {
                    if(httpTrans != null) {
                        httpTrans.disconnect();
                        httpTrans = null;
                        //httpTrans = openConnect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                        httpTrans.openConnect();
                        receiverDataOnLine(httpTrans, t, preencheObj);
                    }else{
                        httpTrans = connect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                receiverDataOnLine(httpTrans, t, preencheObj);
            }catch(IOException ex){
                ex.printStackTrace();
                publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");

                try {
                    if(httpTrans != null) {
                        httpTrans.disconnect();
                        httpTrans = null;
                        //httpTrans = openConnect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                        httpTrans.openConnect();
                        //receiverDataOnLine(httpTrans, t);
                    }else{
                        httpTrans = connect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                    }
                    receiverDataOnLine(httpTrans, t, preencheObj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            this.msgPostExec = "TENTE NOVAMENTE";
        }
    }

    private void receiverDataLocal(Transfer transfer, String t, PreencheObjeto preencheObj) throws SQLException {
        int totalProd = 0;
        String carga = "";

        if(transfer != null) {
            try{
                while (!(carga = transfer.getBufferedReader().readLine()).equals("***FIM***")) {
                    Log.i("TABELA", t);

                    this.msgPostExec = "CARGA RECEBIDA";//o cnpj não é inválido caso contrario a msg default se mantem

                    if (carga.endsWith("|")) {
                        carga += "***FIM***";
                    }
                    String[] cols = carga.split("\\|");

                    if (t.equals("TABELA_P")) {
                        Produto p = preencheObj.extraiProduto(cols);//extrai cada produto
                        new ProdutoRN(ctx).salvar(p);
                        publishProgress(p.getNome(), "DOWNLOAD PRODUTOS");
                        totalProd++;
                    } else if (t.equals("TABELA_L")) {
                        Linha l = preencheObj.extraiLinha(cols);
                        new LinhaRN(ctx).salvar(l);
                        publishProgress(l.getDescricao(), "DOWNLOAD LINHAS");
                    } else if (t.equals("TABELA_F")) {
                        FormPgment formPg = preencheObj.extraiFormPgment(cols);
                        new FormPgmentRN(ctx).salvar(formPg);
                        publishProgress(formPg.getDescricao(), "DOWNLOAD FORMA PAGAMENTO");
                    } else if (t.equals("TABELA_C")) {
                        Cliente cli = preencheObj.extraiClie(cols);
                        new ClienteRN(ctx).salvar(cli);
                        publishProgress(cli.getFantasia(), "DOWNLOAD CLIENTES");
                    } else if (t.equals("TABELA_V")) {
                        Vendedor v = preencheObj.extraiVendedor(cols);
                        new VendedorRN(ctx).salvar(v);
                        publishProgress(v.getNome(), "DOWNLOAD VENDEDOR");
                    } else if (t.equals("TABELA_R")) {
                        ContReceb contReceb = preencheObj.extraiContReceb(cols);
                        new ContRecebRN(ctx).salvar(contReceb);
                        publishProgress(contReceb.getNumTitulo(), "DOWNLOAD CONTAS A RECEBER");
                    } else if (t.equals("TABELA_O")) {
                        Fornecedor f = preencheObj.extraiFornecedor(cols);
                        new FornecedorRN(ctx).salvar(f);
                        publishProgress(f.getNome(), "DOWNLOAD FORNECEDORES");
                    } else if (t.equals("TABELA_B")) {
                        Rota rota = preencheObj.extraiRota(cols);
                        new RotaRN(ctx).salvar(rota);
                        publishProgress(rota.getDescricao(), "DOWNLOAD ROTAS");
                    } else if (t.equals("TABELA_D")) {
                        SeqVisit seqVisit = preencheObj.extraiSeqVisit(cols);
                        new SeqVisitRN(ctx).salvar(seqVisit);
                        publishProgress(String.valueOf(seqVisit.getCodRota()), "DOWNLOAD SEQUENCIA DE VISITAS");
                    } else if (t.equals("TABELA_Z")) {
                        Configuracao configuracao = preencheObj.extraiConFiguracao(cols);
                        System.out.println("CONFIG: "+carga);
                        new ConfiguracaoRN(ctx).salvar(configuracao);
                        publishProgress(String.valueOf(configuracao.getId()), "DOWNLOAD CONFIGURAÇÕES");
                    } else if (t.equals("TABELA_J")) {
                        Justificativa justfi = preencheObj.extraiJustificativa(cols);
                        new JustificativaRN(ctx).salvar(justfi);
                        publishProgress(justfi.getDescricao(), "DOWNLOAD JUSTIFICATIVAS");
                    } else if (t.equals("TABELA_E")) {
                        Comodato como = preencheObj.extractComodato(cols);
                        new ComodatoRN(ctx).salvar(como);
                        publishProgress(como.getCodProd(), "DOWNLOAD COMODATO");
                    }else if(t.equals("TABELA_K")){//DOWNLOAD EMPRESAS
                        Empresa emp = preencheObj.extractEmpresa(cols);
                        new EmpresaRN(ctx).save(emp);
                        publishProgress(emp.getFantasia(), "DOWNLOAD EMPRESAS");
                    }
                }
                if(carga.equals(Operations.CLOSE_CONNECTION)) {
                    transfer.send(Operations.CLOSE_CONNECTION +"\n");
                }
                Log.i("INVÁLIDO", carga);
                Log.i("TOT_PROD", totalProd + "");
                transfer.getSocket().close();
            }catch(NullPointerException ex) {
                ex.printStackTrace();
                publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");

                //httpTrans = null;
                //httpTrans = openConnect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);

                //preencheObj = null;
                receiverDataLocal(transfer, t, preencheObj);
            }catch(ConnectException ex){
                try {
                    if(transfer != null) {
                        transfer.getSocket().close();
                        transfer = null;
                        //httpTrans = openConnect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                        transfer = connect(this.ip, this.porta, Operations.downloadLocal);
                        receiverDataLocal(transfer, t, preencheObj);
                    }else{
                        //transfer = connect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                receiverDataLocal(transfer, t, preencheObj);
            }catch(IOException ex){
                ex.printStackTrace();
                publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");

                try {
                    if(transfer != null) {
                        transfer.getSocket().close();
                        transfer = null;
                        //httpTrans = openConnect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                        transfer = connect(this.ip, this.porta, Operations.downloadLocal);
                        //receiverDataOnLine(httpTrans, t);
                    }else{
                        //transfer = connect("http://sitsys.com.br/webservice/w1.php?tab=" + t + "&cnpj=" + CurrentInfo.cnpjEmpresa);
                    }
                    receiverDataLocal(transfer, t, preencheObj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            this.msgPostExec = "TENTE NOVAMENTE";
        }
    }

    private void receiverStatUpdate() throws IOException {
        HttpTransfer httpTransferUpdate = connect("http://sitsys.com.br/webservice/w4.php?query=update%20SITFORV%20set%20SASTATUS=1%20where%20SACHAVE=%27" + deviceRegister.getKey() + "%27");
        String statUpdate = "";
            while (!(statUpdate = httpTransferUpdate.getBufReader().readLine()).equals("***OK***"));
            Log.i("STAT_UPDATE", statUpdate);
    }

    /**
     * VERIFICA LICENÇA online
     * @return um array com os dados da licença
     * @throws IOException
     */
    public String[] consulLicence() throws IOException {
        String licenca[] = null;//licença ativa
        StringBuilder sb = new StringBuilder(CurrentInfo.cnpjEmpresa);
        sb.insert(2, ".");
        sb.insert(6, ".");
        sb.insert(10, "/");
        sb.insert(15, "-");
        HttpTransfer httpTransfer = null;//ceramica mojuence//11.225.269/0001-92;34.915.728/0001-65;08.662.720/0001-24
        httpTransfer = connect("http://sitsys.com.br/webservice/licenca.php?cnpj="+sb.toString());//+CurrentInfo.cnpjEmpresa))

        responseWebService = httpTransfer.getBufReader().readLine();
        Log.i("LICLI", responseWebService);
        licenca = responseWebService.split("\\|");
        /*if(linha[5].equals("A")) {//A=licença ativa; I=licença inativa
            Log.i("LICLI", linha[5]);
            licenca = true;
        }else if(linha[5].equals("I")){
            licenca = false;
        }else
        if(responseWebService.contains("Connection timed")){
            this.msgPostExec = "SERVIDOR INDISPONÍVEL";
            Log.i("TIMED_OUT", responseWebService);
        }else{
            Log.i("ERRO_TRANSMIT", responseWebService);
            this.msgPostExec = "ERRO PRCESSO WEB";
        }*/

        return licenca;
    }

    private String[] consulKey(HttpTransfer httpTransfer) throws IOException {
        String retorno = "";
        String[] colsStat = null;
        try {
            while (!(retorno = httpTransfer.getBufReader().readLine()).equals("***FIM***")) {
                if (retorno.endsWith("|")) {
                    retorno = "***FIM***";
                }
                colsStat = retorno.split("\\|");
                if(colsStat.length == 2){
                    Log.i("CNPJ_WEB", colsStat[0]);
                    Log.i("STAUS_WEB", colsStat[1]);
                }else if(colsStat[0].contains("Connection timed")){
                    this.msgPostExec = "SERVIDOR NÃO DISPOSÍVEL";
                    Log.i("CNPJ_WEBerr", colsStat[0]);
                }else{
                    Log.i("CNPJ_WEBerr", colsStat[0]);
                }

                msgPostExec = "OK";
            }
        }catch(NullPointerException ex){
            this.msgPostExec = "SEM RESPOSTA DA RECE";
            ex.printStackTrace();
            //publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
         //   httpTransfer.disconnect();
           // httpTransfer.openConnect();// = openConnect("http://sitsys.com.br/webservice/w6.php?chave=" + con.getKey());
            //colsStat = consulKey(httpTransfer);

        }catch(IOException ex){
            publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
            httpTransfer = null;
            if(httpTransfer != null) {
                httpTransfer.disconnect();
                httpTransfer = null;
                httpTransfer.openConnect();
            }else{
                httpTransfer = connect("http://sitsys.com.br/webservice/w6.php?chave=" + deviceRegister.getKey());
            }
            colsStat = consulKey(httpTransfer);
        }

        return colsStat;
    }

    /**
     * realiza atualização da chave para validá-la
     * @param colsStat é um array de Strings com o resultado da leitura da chave no servidor web
     * @throws IOException
     */
    private void updateKey(String[] colsStat) throws IOException {
        if (colsStat != null && Double.parseDouble(colsStat[1]) == 0 && colsStat[0].equals(deviceRegister.getCnpjEmpresa())) {//cnpj ok
            try {
                /*funciona ArrayList<ConfigLocal> confLocal = new ConfigLocalRN(ctx).pesquisar(new ConfigLocal());
                if (confLocal.size() == 0) {
                    //new ConfigLocalRN(ctx).deletar(1);
                    new ConfigLocalRN(ctx).salvar(con);
                }else{
                    new ConfigLocalRN(ctx).atualizar(con);
                }*/

                //Calendar c = Calendar.getInstance();
                //deviceRegister.setUltima_data(c.getTimeInMillis());//data atual
                //c.add(Calendar.DAY_OF_MONTH, 30);
                //deviceRegister.setData_expira(c.getTimeInMillis());

                Registro r = new RegistroRN(ctx).getRegistro();
                if(r.getCnpjEmpresa().equals("") ){
                    new RegistroRN(ctx).salvar(deviceRegister);
                }else{
                    r.setKey(deviceRegister.getKey());
                    r.setCnpjEmpresa(deviceRegister.getCnpjEmpresa());

                    r.setUltima_data(deviceRegister.getUltima_data());//data atual
                    //c.add(Calendar.DAY_OF_MONTH, 30);
                    r.setData_expira(deviceRegister.getData_expira());
                    new RegistroRN(ctx).update(r);
                }


                //new ConfigLocalRN(ctx).salvar(con);
                receiverStatUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            msgPostExec = "CHAVE OU CNPJ INVÁLIDO";
        }
    }

    /**
     * Valida chave de licença
     * @return
     * @throws IOException
     */
    private void validKey() throws IOException {
        HttpTransfer httpTransfer = null;

        while((httpTransfer = connect("http://sitsys.com.br/webservice/w6.php?chave=" + deviceRegister.getKey())) == null){
            publishProgress("TENTANDO RECONECTAR", "FALHA DE CONEXÃO");
        }
        String[] data = consulKey(httpTransfer);

        if(data == null || data[1].equals("1") && deviceRegister.getCnpjEmpresa().equals(data[0])) {//chave inválida
            msgPostExec = "CHAVE INVALIDA OU DUPLICADA";
            /*funciona ArrayList<ConfigLocal> confLocal = null;
            try {
                confLocal = new ConfigLocalRN(ctx).pesquisar(new ConfigLocal());
                if (confLocal.size() > 0) {
                    con.setCnpjEmpresa(confLocal.get(0).getCnpjEmpresa());
                    con.setKey(confLocal.get(0).getKey());
                    new ConfigLocalRN(ctx).atualizar(con);
***************************CONTROLE DE DATA********************************************************
                    Registro r = new Registro();
                    r.setKey(con.getKey());
                    Calendar c = Calendar.getInstance();
                    r.setUltima_data(c.getTimeInMillis());//data atual
                    c.add(Calendar.DAY_OF_MONTH, 30);
                    r.setData_expira(c.getTimeInMillis());
                    new RegistroRN(ctx).salvar(r);
***************************CONTROLE DE DATA********************************************************
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        }else if(data[1].equals("0") && !deviceRegister.getCnpjEmpresa().equals(data[0])) {
            msgPostExec = "CNPJ INVÁLIDO";
        }else if(data[1].equals("1") && !deviceRegister.getCnpjEmpresa().equals(data[0])){
            msgPostExec = "CHAVE E CNPJ INVÁLIDOS";
        }else {
            updateKey(data);
            httpTransfer.disconnect();
            httpTransfer.openConnect();
            data = consulKey(httpTransfer);
            if (data != null && data[1].equals("1")) {
                msgPostExec = "VERIFICAÇÃO OK";
            } else {
                msgPostExec = "FALHA DE VERIFICAÇÃO";
            }
        }
    }

    private void updateLV()throws SQLException{
        ArrayList<Rota> list = new RotaRN(ctx).getRouteForSalesMan(CurrentInfo.codVendedor);

        if(list.size() > 0) {
            ListView lvAtvInicio = (ListView) view;
            lvAtvInicio.setAdapter(new RotaAdp(ctx, list));
        }
    }

    protected void onProgressUpdate(String... s){
        dialog.setMessage(s[0]);
        dialog.setTitle(s[1]);
    }
}