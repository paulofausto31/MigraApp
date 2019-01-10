package migra.br.smart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.ActivityCliente.ClienteFragment;
import migra.br.smart.Activityproduto.ProdListFragment;
import migra.br.smart.R;
import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.historiFg.HistoricoFragment;
import migra.br.smart.listaPedidoFg.ListaPedidoFragment;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoRN;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocal;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalRN;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.manipulaBanco.entidades.registro.Registro;
import migra.br.smart.manipulaBanco.entidades.registro.RegistroRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.rota.RotaRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;

/**
 * Created by droidr2d2 on 19/01/2017.
 */
public class Utils {
    private static String key = "jpr0gr@mfringe1974";


    public static ConfigLocal configLocal;

    private static long totalData = 0;
    private static long dataAtual = 0;
    private static boolean clickOk = false;

    private static String extraQuery = "";

    private Context ctx;

    /**
     * Adiciona um filtro na query com o banco de dados
     * @return
     */
    public static String getExtraQuery() {
        return extraQuery;
    }

    public static void setExtraQuery(String extraQuery) {
        Utils.extraQuery = extraQuery;
    }

    /**
     * Adicionar algum filtro na query, no banco de dados
     * @return
     */


    public static ConfigLocal getConfigLocal() {
        return configLocal;
    }

    public static void setConfigLocal(ConfigLocal configLocal) {
        Utils.configLocal = configLocal;
    }

    /**
     * VERIFICA SE EXISTE CNPJ  E CHAVE NA BASE
     * @param ctx
     * @return TRUE QUANDO HÁ CNPJ + CHAVE, FALSE CASO CONTRÁRIO
     * @throws SQLException
     */
    public static boolean verifiCnpjAndKey(Context ctx) throws SQLException {
        boolean status = false;//true quando houver cnpj

        Registro r = new RegistroRN(ctx).getRegistro();
        if(r != null && !r.getCnpjEmpresa().equals("") && (!r.getKey().equals("") && r.getKey().split("-").length == 3)){
            status = true;
            CurrentInfo.cnpjEmpresa = r.getCnpjEmpresa();
        }
        return status;
    }

    /**
     *
     * @return true quando tem ip e porta validos
     * @throws SQLException
     */
    public boolean verifiIpAndPort(Context ctx) throws SQLException {
        boolean status = false;
        ArrayList<ConfigLocal> listConfigLocal = new ConfigLocalRN(ctx).pesquisar(new ConfigLocal());
        if(listConfigLocal.size() >  0 && !listConfigLocal.get(0).getIp().equals("")
                && listConfigLocal.get(0).getPorta() > 1024){
                status = true;
                //CurrentInfo.cnpjEmpresa = configLocal.getCnpjEmpresa();
        }

        return status;
    }

    /*********************************VERIFICA SE EXISTE CNPJ*************************************/

    /**
     * OPERAÇÃO: EXIBE MENSAGEM DE ERRO,
     * RETORNO: true se clicar em ok
     * se icon for null, não exibe icone
     * */
    public static void showMsg(Context ctx, String titulo,String msg, Integer icon){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        if(titulo != null){
            dialog.setTitle(titulo);
        }

        if(icon != null){
            dialog.setIcon(icon.intValue());
        }

        dialog.setMessage(msg);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.clickOk = true;
            }
        });
        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Utils.clickOk = false;
            }
        });
        dialog.show();
    }

    /**
     * @param ctx
     * @return true se o vendedor estiver em alguma rota e false, caso contrario
     * @throws SQLException
     */
    public static boolean codVendedorExists(Context ctx, String codVend) throws SQLException{
        boolean status = false;
        ArrayList<Rota> alRota = new RotaRN(ctx).getRouteForSalesMan(codVend);
        if(alRota.size() > 0){
            status = true;
        }
        return status;
    }
    /*********************************EXIBE MENSAGEM DE ERRO**************************************/

    public static void fullScreen(Window window){
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= 19){
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );

        }
    }

    /* Função para verificar existência de conexão com a internet
 */

    /**
     * VERIFICA SE EXISTE CONEXÃO COM A INTERNET
     * @param ctx, contexto
     * @return true se houver conexão com a internet, false caso contrário
     */
    public static boolean verifiNetConnection(Context ctx){
        boolean statConect = false;
        ConnectivityManager cm  = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(cm != null && netInfo.isAvailable() && netInfo.isConnected()){
            statConect = true;
        }

        return statConect;
    }

    /**
     * @operação verifica se a data expirou
     * @return true se a data expirou e false, caso contrário
     */
    public static boolean isDateExpira(){
        boolean status = false;
        Calendar c = null;
        if(Utils.dataAtual == 0){
            c = Calendar.getInstance();
            dataAtual = c.getTimeInMillis();
            totalData = c.getTimeInMillis()+1209600000;
        }else{
            c = Calendar.getInstance();
        }

        if(c.getTimeInMillis() >=  totalData){
            status = true;
        }

        return status;
    }

    /**
     *
     * @param ctx
     * @return true se a licença não espirar, false caso contrário
     * @throws SQLException
     */
    public static boolean verifiLicence(final Activity ctx) throws SQLException{
        boolean status = true;//false;

        //ArrayList<Configuracao> lConf = new ConfiguracaoRN(this).filtrar(new Configuracao());
        Registro r = new RegistroRN(ctx).getRegistro();
        if(r == null || r.getKey().length() != 14 || r.getKey().split("-").length != 3){
            Utils.showMsg(ctx, "ERRO", "VÁ EM CONFIGURAÇÕES E INTRODUZA A LICENÇA", R.drawable.dialog_error);
            status = false;
        }
        /*
        if(r != null){
            if(r.getUltima_data() > r.getData_expira()){
                status = false;

                AlertDialog.Builder ald = new AlertDialog.Builder(ctx);
                ald.setTitle("ERRO");
                ald.setCancelable(false);
                ald.setMessage("A LICENÇA EXPIROU, POR FAVOR SOLICITE UMA NOVA");
                ald.setIcon(R.drawable.dialog_error);
                ald.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int wich){
                        //funciona ctx.finish();
                    }
                });
                ald.show();
            }else if(r.getUltima_data() > Calendar.getInstance().getTimeInMillis()) {//ultima data menor que data atual
                status = false;
                AlertDialog.Builder ald = new AlertDialog.Builder(ctx);
                ald.setTitle("ERRO");
                ald.setCancelable(false);
                ald.setMessage("DATA INCORRETA");
                ald.setIcon(R.drawable.dialog_error);
                ald.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int wich){
                        ctx.finish();
                    }
                });
                ald.show();
            }else if(Calendar.getInstance().getTimeInMillis() > r.getData_expira()){
                status = false;
                AlertDialog.Builder ald = new AlertDialog.Builder(ctx);
                ald.setTitle("ERRO");
                ald.setCancelable(false);
                ald.setMessage("A LICENÇA EXPIROU, POR FAVOR SOLICITE UMA NOVA");
                ald.setIcon(R.drawable.dialog_error);
                ald.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int wich){
                        ////funciona ctx.finish();
                    }
                });
                ald.show();
            }else {
                status = true;
            }

        }
        */
        return status;
    }

    public void dialogFilter(final Activity ctx, final FragmentManager fragmentManager) throws SQLException {//, final Intent generalIntent) throws SQLException {
        LayoutInflater inflate = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.dg_generic_pesq, null);

        final EditText edtNome = (EditText)v.findViewById(R.id.edtNome);
        final EditText edtCodProd = (EditText)v.findViewById(R.id.edtCodProd);
        final EditText edtDgCliPesq = (EditText)v.findViewById(R.id.edtDgCliRzSoci);
        final EditText edtDgCliFantasiaPesq = (EditText)v.findViewById(R.id.edtDgCliFantasiaPesq);//nome fantasia
        final CheckBox chDgPesqTodRot = (CheckBox)v.findViewById(R.id.chDgPesqTodRot);
       // final TableLayout tbLayDgProd = (TableLayout)v.findViewById(R.id.tbLayDgProd);//para pesquisar produtos
        final EditText edtDgCliRzSoci = (EditText) v.findViewById(R.id.edtDgCliRzSoci);
       // final TableLayout tbLayDgCliFrag = (TableLayout)v.findViewById(R.id.tbLayDgCliFrag);//para pesqusar clientes
       final CheckBox chDgPesqSaldProd = (CheckBox) v.findViewById(R.id.chDgPesqSaldProd);

        final AlertDialog dialog;

        AlertDialog.Builder alerta = new AlertDialog.Builder(ctx);
        alerta.setTitle("COMO VOCÊ QUER PROCURAR?");
        alerta.setIcon(R.drawable.pesq16x16);

        if(!ControlFragment.isActiveHistoriFg() && !ControlFragment.isActiveProd() && !ControlFragment.isActiveListaPedidos() && !ControlFragment.isActiveClientFrag()) {
            Utils.showMsg(ctx, "ERRO", "OPERAÇÃO NÃO PERMITIDA NESTA TELA", R.drawable.dialog_error);
        }else {
            if(ControlFragment.isActiveProd()){
                edtNome.setVisibility(View.VISIBLE);
                edtCodProd.setVisibility(View.VISIBLE);
                edtDgCliRzSoci.setVisibility(View.GONE);
              //  tbLayDgCliFrag.setVisibility(View.GONE);
                edtDgCliFantasiaPesq.setVisibility(View.GONE);
                chDgPesqSaldProd.setVisibility(View.VISIBLE);
            }else if(ControlFragment.isActiveClientFrag()){
                chDgPesqSaldProd.setVisibility(View.GONE);
                edtNome.setVisibility(View.GONE);
                edtCodProd.setVisibility(View.GONE);
                edtDgCliRzSoci.setVisibility(View.VISIBLE);
                edtDgCliFantasiaPesq.setVisibility(View.GONE);
              //  tbLayDgCliFrag.setVisibility(View.VISIBLE);
            }else if(ControlFragment.isActiveListaPedidos()){
                chDgPesqSaldProd.setVisibility(View.GONE);
                edtCodProd.setVisibility(View.GONE);
                edtDgCliRzSoci.setVisibility(View.GONE);
              //  tbLayDgCliFrag.setVisibility(View.GONE);
                edtDgCliFantasiaPesq.setVisibility(View.GONE);
            }else if(ControlFragment.isActiveHistoriFg()){
                chDgPesqSaldProd.setVisibility(View.GONE);
                edtNome.setVisibility(View.GONE);
                edtCodProd.setVisibility(View.GONE);
                edtDgCliRzSoci.setVisibility(View.VISIBLE);
                //tbLayDgCliFrag.setVisibility(View.GONE);
                edtDgCliFantasiaPesq.setVisibility(View.GONE);
            }

            alerta.setView(v);
            dialog = alerta.show();

            Button btPesquisar = (Button)v.findViewById(R.id.btPesquiProd);
            btPesquisar.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    Produto fseProd = new Produto();

                    fseProd.setNome(edtNome.getText().toString());
                    fseProd.setLinha("");
                    fseProd.setFornecedor("");

                    fseProd.setCodigo(edtCodProd.getText().toString());
                    fseProd.setTIPO("00");

                    /*long rot = generalIntent.getLongExtra("codRota", 0);
                    Log.i("ROT_UTI", rot+"");
                    if(chDgPesqTodRot.isChecked()){
                        rot = 0;
                    }*/

                    try {
                        if(ControlFragment.isActiveProd()){
                            if(chDgPesqSaldProd.isChecked()){//pesquisar apenas produtos com saldo positivo
                                Utils.setExtraQuery(" and saldo > 0 ");
                            }else{//pesquisar apenas produtos com saldo negativo ou zerado
                                Utils.setExtraQuery(" and saldo > 0 or saldo <= 0");
                            }

                            ArrayList<Produto> list = new ProdutoRN(ctx).pesquisar(fseProd);
                            ProdListFragment prodListFrag = (ProdListFragment)fragmentManager.findFragmentByTag("prodListFrag");
                            if(prodListFrag == null){
                                Log.i("PROD_LIST_FRAGMENT", "NULL");
                            }
                            prodListFrag.updateLista(list);
                        }else if(ControlFragment.isActiveListaPedidos()){
                            //ArrayList<ListaPedido> list = new ListaPedidoRN(ActivityContainerFragments.this).getForNomeProd(edtNome.getText().toString());
                            ListaPedidoFragment listaPedidoFragment = (ListaPedidoFragment)fragmentManager.findFragmentByTag("listPedFrag");
                            listaPedidoFragment.updateLista(edtNome.getText().toString());
                        }else if(ControlFragment.isActiveClientFrag()){
                            ClienteFragment clifrag = (ClienteFragment) fragmentManager.findFragmentByTag("cliFrag");
                            Cliente c = new Cliente();
                            /* funciona c.setFantasia(edtDgCliPesq.getText().toString());//razao social
                            c.setRzSocial(edtDgCliFantasiaPesq.getText().toString());//nome fantasia*/

                            c.setFantasia(edtDgCliPesq.getText().toString());//razao social
                            c.setRzSocial(edtDgCliPesq.getText().toString());//nome fantasia
                            clifrag.setCliFilter(c);//para continuar a pesquisa no fragment com o cliente como parte do filtro
                            long rot = clifrag.getCurrentRota();//generalIntent.getLongExtra("codRota", 0);
                            Log.i("ROT_BUSC", rot+"");

                            ArrayList<Configuracao> conf = new ConfiguracaoRN(ctx).pesquisar(new Configuracao());
                            if(conf != null && conf.size() > 0){
                                if (conf.get(0).getVendePorDiaSemana().toUpperCase().equals("N")){//PERMITE EXIBIR TODAS AS ROTAS
                                    if(chDgPesqTodRot.isChecked()){
                                        rot = 0;
                                    }
                                }
                            }

                            clifrag.fillListView(rot, null);
                        }else if(ControlFragment.isActiveRotaFg()){
                           /* FragmentTransaction transaction = fragmentManager.beginTransaction();
                            ClienteFragment cliFg = new ClienteFragment();
                            Bundle b = new Bundle();
                            cliFg.setArguments(b);
                            transaction.replace(R.id.pedidoContainer, cliFg, "cliFrag");*/
                        }else if(ControlFragment.isActiveHistoriFg()){
                            /*Intent it = new Intent("ATV_CALC_PED");
                            ctx.startActivityForResult(it, 1);*/

                            Log.i("Utils", ""+CurrentInfo.codCli+" - rota"+CurrentInfo.rota+" - ped "+CurrentInfo.idPedido);

                            HistoricoFragment histFg = (HistoricoFragment) fragmentManager.findFragmentByTag("historiFg");
                            Cliente c = new Cliente();
                            /*funciona c.setFantasia(edtDgCliPesq.getText().toString());//razao social
                            c.setRzSocial(edtDgCliFantasiaPesq.getText().toString());//nome fantasia*/

                            c.setFantasia(edtDgCliPesq.getText().toString());//razao social
                            c.setRzSocial(edtDgCliPesq.getText().toString());//nome fantasia
                            //histFg.setCliFilter(c);//para continuar a pesquisa no fragment com o cliente como parte do filtro
                            //long rot = histFg.getCurrentRota();//generalIntent.getLongExtra("codRota", 0);
                           Pedido pFiltro = new Pedido();
                            pFiltro.setStatus("Transmitido");
                            pFiltro.setCliente(c);
                            //pFiltro.setRota();
                            histFg.updateListView(pFiltro);
                        }
                        dialog.hide();
                    } catch (SQLException e) {
                        Log.e("erroSql", e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Limpa o status dos clientes P, N, A
     * @param ctx contexto
     */
    public void clearStatClientes(Context ctx){
        try {
            ArrayList<SeqVisitStatus> list = new SeqVisitStatusRN(ctx).getListSeqVisitStat();
            for(SeqVisitStatus seqStat: list){
                //if(!seqStat.getCodPed().equals("0")){
                if(seqStat.getStatus().equals("P")){
                    Pedido ped = new PedidoRN(ctx).getForId(Long.parseLong(seqStat.getCodPed()));
                    if(ped.getStatus().startsWith("Transmitido")){
                        Log.e("CLEAR", seqStat.getCodPed());
                        SeqVisit seqVisit = new SeqVisit();
                        seqVisit.setId(Integer.parseInt(seqStat.getSeq_id()));
                        seqStat.setStatus(seqStat.getSeq_id());
                        ArrayList<SeqVisit> seqViList = new SeqVisitRN(ctx).pesquisar(seqVisit);
                        if(seqViList.size() > 0) {
                            seqViList.get(0).setStatus("");
                            new SeqVisitRN(ctx).update(seqViList.get(0));
                            new SeqVisitStatusRN(ctx).deletar(seqStat.getSeq_id());
                        }

                    }
                }else if(seqStat.getStatus().equals("N")){
                    Log.e("TEM_NEG", seqStat.getIdNegativa()+"");
                    Negativacao neg = new NegativacaoRN(ctx).getForId(seqStat.getIdNegativa());//neste caso o campo se refere ao id da negarivação e não ao codigo do pedido
                    Log.e("TEM_NEG", neg.getStatus());
                    if(neg.getStatus().startsWith("Transmitido")){

                        SeqVisit seqVisit = new SeqVisit();
                        seqVisit.setId(Integer.parseInt(seqStat.getSeq_id()));
                        seqStat.setStatus(seqStat.getSeq_id());
                        ArrayList<SeqVisit> seqViList = new SeqVisitRN(ctx).pesquisar(seqVisit);
                        if(seqViList.size() > 0) {
                            seqViList.get(0).setStatus("");
                            new SeqVisitRN(ctx).update(seqViList.get(0));
                            new SeqVisitStatusRN(ctx).deletar(seqStat.getSeq_id());
                        }

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finish(){

    }
    public static boolean isClickOk() {
        return Utils.clickOk;
    }

    public static void setClickOk(boolean clickOk) {
        Utils.clickOk = clickOk;
    }
}