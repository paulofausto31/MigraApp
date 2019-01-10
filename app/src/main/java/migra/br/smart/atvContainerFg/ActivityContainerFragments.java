package migra.br.smart.atvContainerFg;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import migra.br.smart.ActivityCliente.ClienteFragment;
import migra.br.smart.ActivityCliente.ClienteTabFragment;
import migra.br.smart.ActivityCliente.fragNegativacao.NegativacaoListCliFrag;
import migra.br.smart.ActivityCliente.fragmentComodato.ComodatoFragment;
import migra.br.smart.Activityproduto.ProdListFragment;
import migra.br.smart.activityLogin.dgOpDownTables.DgOpDownTablesAdap;
import migra.br.smart.historiFg.HistoricoFragment;
import migra.br.smart.ActivityCliente.fragmentJustifcativa.NegativacaoFragment;
import migra.br.smart.ActivityContRec.ContRecTabFragment;
import migra.br.smart.manipulaBanco.dropDatabase.DropCreateDatabase;
import migra.br.smart.manipulaBanco.dropDatabase.DropCreateDatabaseDirectAccess;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusRN;
import migra.br.smart.prodsVendsFg.ProdsVendsFg;
import migra.br.smart.rotaFg.RotaFg;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.R;
import migra.br.smart.TaskNetWork.TaskConnectNetWork;
import migra.br.smart.TaskNetWork.transferOperations.Operations;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaRN;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.pedidoFragment.PedidoFragment;
import migra.br.smart.listaPedidoFg.ListaPedidoFragment;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoRN;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

public class ActivityContainerFragments extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProdListFragment.OnFragmentInteractionListener,
        PedidoFragment.OnFragmentInteractionListener,
        ListaPedidoFragment.OnFragmentInteractionListener,
        ClienteTabFragment.OnFragmentInteractionListener,
        ClienteFragment.OnFragmentInteractionListener,
        ContRecTabFragment.OnFragmentInteractionListener,
        NegativacaoFragment.OnFragmentInteractionListener,
        ComodatoFragment.OnFragmentInteractionListener,
        HistoricoFragment.OnFragmentInteractionListener,
        NegativacaoListCliFrag.OnFragmentInteractionListener,
        RotaFg.OnFragmentInteractionListener,
        ProdsVendsFg.OnFragmentInteractionListener{
    ListView lvProdutos;

    EditText edTxPrazoPgVenda, edTxParcePgVenda, edTxDataIni, edTxHoraIni, edTxDataFim, edTxHoraFim;
    public static Button btIniciaPedido, btContinuar, actImport;
    //public static FloatingActionButton fabPedidos, fabAtvContainPedImport, fabAtvContainCliCalc;

    ArrayList<Produto> lista;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Cliente cliente;

    ArrayList<Rota> rotasDgCalcPedidos;
    Pedido pedidoCalc = new Pedido();

    String retransmite = "";

    Context ctx;

    private Intent generalIntent;

    private NavigationView navigationView;
    private boolean clearStatCliente;//status do cliente P, N, A
    /****************************PARA USO DE RETORNO DE ACTIVITY***********************************/
    private static final int RETURN_CONFIG = 0;
    public static int R_FG_HISTORICO = 1;
    public static int R_FG_CLIENTE = 2;
    public static int R_FG_PEDIDO = 3;
    public static int R_FG_PRODUTOS = 4;
    public static int R_FG_LISTA_PEDIDO = 5;
    /****************************PARA USO DE RETORNO DE ACTIVITY***********************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.fullScreen(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_container_frags);

        actImport = (Button) findViewById(R.id.actImport);

        ctx = this;

        generalIntent = getIntent();
        Cliente cli = new Cliente();
        cli.setFantasia(generalIntent.getStringExtra("nome"));
        //cli.setCodProd(b.getDoubleExtra("codigo", 0));
        setCliente(cli);

        if(savedInstanceState == null){
            if(generalIntent.getExtras() != null) {
                if (generalIntent.getStringExtra("openFrag").equals("pedidoFrag")) {
                    PedidoFragment pedidoFragment = new PedidoFragment();//pedidos
                    pedidoFragment.setArguments(generalIntent.getExtras());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.pedidoContainer, pedidoFragment, "pedidoFrag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if(generalIntent.getStringExtra("openFrag").equals("prodListFrag")){
                    ProdListFragment prodListFragment = new ProdListFragment();//lista de produtos
                    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                    fragTransaction.add(R.id.pedidoContainer, prodListFragment, "prodListFrag");
                    fragTransaction.addToBackStack(null);
                    fragTransaction.commit();
                }else if(generalIntent.getStringExtra("openFrag").equals("fragCliTab")){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ClienteTabFragment clientetabFragment = new ClienteTabFragment();
                    fragmentTransaction.add(R.id.pedidoContainer, clientetabFragment, "fragCliTab");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if(generalIntent.getStringExtra("openFrag").equals("cliFrag")){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ClienteFragment clienteFragment = new ClienteFragment();
                    fragmentTransaction.add(R.id.pedidoContainer, clienteFragment, "cliFrag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if(generalIntent.getStringExtra("openFrag").equals("negativListCliFg")){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    NegativacaoListCliFrag negListCliFg = new NegativacaoListCliFrag();
                    transaction.add(R.id.pedidoContainer, negListCliFg, "negativListCliFg");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else if(generalIntent.getStringExtra("openFrag").equals(ControlFragment.tagFgHistorico)){//"historiFg")){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    HistoricoFragment histriFg = new HistoricoFragment();
                    transaction.add(R.id.pedidoContainer, histriFg, "historiFg");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else if(generalIntent.getStringExtra("openFrag").equals("rotaFg")){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    RotaFg rotaFg = new RotaFg();
                    transaction.add(R.id.pedidoContainer, rotaFg, "rotaFg");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.RED);
        toolbar.setTitle("NOME DO CLIENTE");

        try {
            mensagemPromocional();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        fabPedidos = (FloatingActionButton) findViewById(R.id.fabPedidos);
        fabPedidos.setVisibility(View.GONE);///////trocado temporariamente por botao na actionbar
        fabPedidos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(ControlFragment.isActivePedido() || ControlFragment.isActiveProd()){
                    ListaPedidoFragment listaPedidoFragment = new ListaPedidoFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.pedidoContainer, listaPedidoFragment, "listPedFrag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    Utils.showMsg(ActivityContainerFragments.this, "ERRO", "OPERAÇÃO NÃO PERMITIDA PARA ESTA TELA", R.drawable.dialog_error);
                }
            }
        });

        fabAtvContainCliCalc = (FloatingActionButton) findViewById(R.id.fabAtvContainCliCalc);
        fabAtvContainCliCalc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });*/


        /*
        final FloatingActionButton fabPedExportar =(FloatingActionButton) findViewById(R.id.fabAtvContainPedExportar);
        fabPedExportar.setVisibility(View.GONE);///////////////////////////////////////////SUBSTITUIDO TEMPORARIAMENTE POR BOTAO NA ACTIONBAR
        fabPedExportar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {

                    Pedido ped = new Pedido();

                    if(ControlFragment.isActiveHistoriFg()) {//reenvia se estiver no histórico
                        setRetransmite("reenviar");
                        ped.setStatus("Transmitido");
                        ped.setDel("S");
                    }else {
                        ped.setStatus("Fechado");
                        setRetransmite("");
                    }
                    ArrayList<Pedido> lPed = new PedidoRN(ActivityContainerFragments.this).filtrar(ped);

                    Negativacao ne = new Negativacao();
                    if(ControlFragment.isActiveJustifica()) {//reenvia as justificativas se estiver na justificativa
                        setRetransmite("reenviar");
                        ne.setStatus("select");
                    }else {
                        ne.setStatus("Espera");
                        setRetransmite("");
                    }

                    ArrayList<Negativacao> lNegativ = new NegativacaoRN(ctx).getWithClients(ne);
                    if (lPed.size() > 0 || lNegativ.size() > 0) {//HÁ PEDIDO PARA TRANSMITIR
                        HashMap<String, String> hasTbDown = new HashMap<String, String>();*/
                        /*hasTbDown.put("P", "Produto");//produto
                        hasTbDown.put("L", "Linhas");//linhas de produto
                        hasTbDown.put("C", "Clientes");// clientes
                        hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                        hasTbDown.put("V", "Vendas");//vendedores
                        hasTbDown.put("B", "Rotas");//rotas
                        hasTbDown.put("Z", "Configurações");//configurações
                        hasTbDown.put("E", "Comodato");//comodato
                        hasTbDown.put("O", "Fornecedores");//fornecedores
                        hasTbDown.put("J", "Erro");//justificativas
                        hasTbDown.put("R", "Contas a Receber");//contas a receber
                        hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas
                        */
                        /*
                        LayoutInflater inflater = (LayoutInflater) ActivityContainerFragments.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

                        ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
                        Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
                        Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);
                        Button btDgUpInterno = (Button) vdg.findViewById(R.id.btDgUpInterno);

                        AlertDialog.Builder alb = new AlertDialog.Builder(ActivityContainerFragments.this);
                        alb.setView(vdg);
                        final AlertDialog alert = alb.show();//263
                        btDgOpDownTbWeb.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                                fabPedExportar.setEnabled(false);
                                TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fabPedExportar, null);
                                task.execute(String.valueOf(Operations.EXPORT_PEDIDOS_INTERNET), getRetransmite());
                            }
                        });
                        btDgOpDowntbLocal.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                try {
                                    //ArrayList<ConfigLocal> arLconLocal = new ConfigLocalRN(ctx).pesquisar(new ConfigLocal());
                                    if(new Utils().verifiIpAndPort(ctx) && Utils.verifiCnpjAndKey(ctx)){
                                        alert.dismiss();
                                        fabPedExportar.setEnabled(false);
                                        TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fabPedExportar, null);
                                        task.execute(String.valueOf(Operations.UPLOAD_TO_SERV_OFF), getRetransmite());

                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        btDgUpInterno.setOnClickListener(new Button.OnClickListener(){
                            public void onClick(View v){
                                try {
                                    if(new Utils().verifiIpAndPort(ActivityContainerFragments.this) && Utils.verifiCnpjAndKey(ActivityContainerFragments.this)){
                                        fabPedExportar.setEnabled(false);
                                        TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fabPedExportar, null);
                                        task.execute(String.valueOf(Operations.UPLOAD_INTERNO), getRetransmite());
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        Button btDgOpDowntbMarcDesmarc = (Button) vdg.findViewById(R.id.btDgUpInterno);

                        lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(ActivityContainerFragments.this, hasTbDown));
                    } else {
                        Utils.showMsg(ActivityContainerFragments.this, "ERRO", "NÃO HÁ PEDIDOS OU NEGATIVAÕES PARA TRANSMITIR", R.drawable.dialog_error);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        */

                        /*
        fabAtvContainPedImport = (FloatingActionButton) findViewById(R.id.fabAtvContainPedImport);
        fabAtvContainPedImport.setVisibility(View.GONE);///////////trocado temporariamente por botao na actionbar
        fabAtvContainPedImport.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                HashMap<String, String> hasTbDown = new HashMap<String, String>();*/
               /* hasTbDown.put("P", "Produto");//produto
                hasTbDown.put("L", "Linhas");//linhas de produto
                hasTbDown.put("C", "Clientes");// clientes
                hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                hasTbDown.put("V", "Vendas");//vendedores
                hasTbDown.put("B", "Rotas");//rotas
                hasTbDown.put("Z", "Configurações");//configurações
                hasTbDown.put("E", "Comodato");//comodato
                hasTbDown.put("O", "Fornecedores");//fornecedores
                hasTbDown.put("J", "Erro");//justificativas
                hasTbDown.put("R", "Contas a Receber");//contas a receber
                hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas
*/
               /*
                LayoutInflater inflater = (LayoutInflater) ActivityContainerFragments.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

                ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
                Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
                Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);

                AlertDialog.Builder alb = new AlertDialog.Builder(ActivityContainerFragments.this);
                alb.setView(vdg);
                final AlertDialog alert = alb.show();//263
                btDgOpDownTbWeb.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        try {
                            new DropCreateDatabaseDirectAccess().dropTables(ActivityContainerFragments.this, new DropCreateDatabase());
                            TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fabAtvContainPedImport, CurrentInfo.codVendedor);
                            task.execute(String.valueOf(Operations.DOWNLOAD_INTERNET));
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                btDgOpDowntbLocal.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        try {

                            if(new Utils().verifiIpAndPort(ctx) && Utils.verifiCnpjAndKey(ctx)) {
                                alert.dismiss();
                                //new DropCreateDatabaseDirectAccess().dropTables(ActivityContainerFragments.this, new DropCreateDatabase());
                                TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fabAtvContainPedImport, CurrentInfo.codVendedor);
                                task.execute(String.valueOf(Operations.downloadLocal));
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
                lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(ActivityContainerFragments.this, hasTbDown));
            }
        });
        */

               /*
        FloatingActionButton fabPesquisar = (FloatingActionButton) findViewById(R.id.fab);
        fabPesquisar.setVisibility(View.GONE);///////TROCADO TEMPORARIAMENTE POR BOTAO NA ACTIONBAR
        fabPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                new Utils().dialogFilter(ActivityContainerFragments.this, fragmentManager);//, generalIntent);
                //dialogFilter();
                //Intent it = new Intent("ATV_CALC_PED");
                //startActivityForResult(it, R_FG_HISTORICO);
            } catch (SQLException e) {
                        e.printStackTrace();
            }*/
                /*Snackbar.make(view, "TESTE...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                /*
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);//icones coloridos menu navigation drawer
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opcoes, menu);
        return true;
    }

    public boolean isClearStatCliente() {
        return clearStatCliente;
    }

    public void setClearStatCliente(boolean clearStatCliente) {
        this.clearStatCliente = clearStatCliente;
    }

    AlertDialog alertDgAlDgOperations = null;//
    private void showMenuTransfer() throws SQLException {
        setRetransmite("");//caso este conteúdo não esteja em branco, o app não conseguirá realizar uma transmissão após qualquer tipo de retransmissão
        Pedido ped = new Pedido();

        if(ControlFragment.isActiveHistoriFg()) {//reenvia se estiver no histórico
            setRetransmite("reenviar");
            ped.setStatus("Transmitido");
            ped.setDel("S");
        }else {
            ped.setStatus("Fechado");
        }
        ArrayList<Pedido> lPed = new PedidoRN(ActivityContainerFragments.this).filtrar(ped);

        Negativacao ne = new Negativacao();
        if(ControlFragment.isActiveJustifica()) {//reenvia as justificativas se estiver na justificativa
            setRetransmite("reenviar");
            ne.setStatus("select");
        }else {
            ne.setStatus("Espera");
        }

        ArrayList<Negativacao> lNegativ = new NegativacaoRN(ctx).getWithClients(ne);
        if (lPed.size() > 0 || lNegativ.size() > 0) {//HÁ PEDIDO PARA TRANSMITIR
            HashMap<String, String> hasTbDown = new HashMap<String, String>();
                        /*hasTbDown.put("P", "Produto");//produto
                        hasTbDown.put("L", "Linhas");//linhas de produto
                        hasTbDown.put("C", "Clientes");// clientes
                        hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                        hasTbDown.put("V", "Vendas");//vendedores
                        hasTbDown.put("B", "Rotas");//rotas
                        hasTbDown.put("Z", "Configurações");//configurações
                        hasTbDown.put("E", "Comodato");//comodato
                        hasTbDown.put("O", "Fornecedores");//fornecedores
                        hasTbDown.put("J", "Erro");//justificativas
                        hasTbDown.put("R", "Contas a Receber");//contas a receber
                        hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas
*/

            LayoutInflater inflater = (LayoutInflater) ActivityContainerFragments.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

            //ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
            Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
            Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);
            Button btDgUpInterno = (Button) vdg.findViewById(R.id.btDgUpInterno);
            final Spinner spEmpresas = (Spinner) vdg.findViewById(R.id.spEmpresas);

            try {
                ArrayList<Empresa> alEmp = new EmpresaRN(ActivityContainerFragments.this).list(new Empresa());
                String[] arSpempr = new String[alEmp.size()+1];
                arSpempr[0] = "0-TODAS";
                for(int i = 1; i < alEmp.size()+1; i++){
                    arSpempr[i] = alEmp.get(i-1).getId()+"-"+alEmp.get(i-1).getFantasia();
                }
                ArrayAdapter<String> arAdpspEmp = new ArrayAdapter<String>(ActivityContainerFragments.this, R.layout.spin_ped_adapter_cod_ped, arSpempr);
                spEmpresas.setAdapter(arAdpspEmp);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            AlertDialog.Builder alb = new AlertDialog.Builder(ActivityContainerFragments.this);
            alb.setTitle("ENVIAR CARGA");
            alb.setView(vdg);
            final AlertDialog alert = alb.show();//263
            btDgOpDownTbWeb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                    //fabPedExportar.setEnabled(false);
                    TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, null);
                    task.setApagaStatus(isClearStatCliente());//limpar o status do cliente P, N, A
                    task.execute(String.valueOf(Operations.EXPORT_PEDIDOS_INTERNET), getRetransmite(), spEmpresas.getSelectedItem().toString().split("-")[0]);
                }
            });
            btDgOpDowntbLocal.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    try {
                        //ArrayList<ConfigLocal> arLconLocal = new ConfigLocalRN(ctx).pesquisar(new ConfigLocal());
                        if(new Utils().verifiIpAndPort(ctx) && Utils.verifiCnpjAndKey(ctx)){
                            alert.dismiss();
                            //  fabPedExportar.setEnabled(false);
                            TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, null);
                            task.setApagaStatus(isClearStatCliente());//limpar o status do cliente P, N, A
                            task.execute(String.valueOf(Operations.UPLOAD_TO_SERV_OFF), getRetransmite(), spEmpresas.getSelectedItem().toString().split("-")[0]);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            btDgUpInterno.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    try {
                        if(new Utils().verifiIpAndPort(ActivityContainerFragments.this) && Utils.verifiCnpjAndKey(ActivityContainerFragments.this)){
                            alert.dismiss();
                            //fabPedExportar.setEnabled(false);
                            TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, null);
                            task.execute(String.valueOf(Operations.UPLOAD_INTERNO), getRetransmite(), spEmpresas.getSelectedItem().toString().split("-")[0]);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            Button btDgOpDowntbMarcDesmarc = (Button) vdg.findViewById(R.id.btDgUpInterno);

            //lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(ActivityContainerFragments.this, hasTbDown));
        } else {
            Utils.showMsg(ActivityContainerFragments.this, "ERRO", "NÃO HÁ PEDIDOS OU NEGATIVAÕES PARA TRANSMITIR", R.drawable.dialog_error);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case R.id.actExport:
                AlertDialog.Builder albClearStat = new AlertDialog.Builder(ActivityContainerFragments.this);
                albClearStat.setTitle("MANTER STATUS APÓS TRNASMISSÃO?");
                    albClearStat.setPositiveButton("SIM", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                setClearStatCliente(false);
                                showMenuTransfer();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    albClearStat.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            try {
                                setClearStatCliente(true);
                                showMenuTransfer();
                                //setClearStatCliente(false);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    albClearStat.show();
                break;
            case R.id.actImport:
                HashMap<String, String> hasTbDown = new HashMap<String, String>();
               /* hasTbDown.put("P", "Produto");//produto
                hasTbDown.put("L", "Linhas");//linhas de produto
                hasTbDown.put("C", "Clientes");// clientes
                hasTbDown.put("F", "Formas de Pagamento");//formas de pagamento
                hasTbDown.put("V", "Vendas");//vendedores
                hasTbDown.put("B", "Rotas");//rotas
                hasTbDown.put("Z", "Configurações");//configurações
                hasTbDown.put("E", "Comodato");//comodato
                hasTbDown.put("O", "Fornecedores");//fornecedores
                hasTbDown.put("J", "Erro");//justificativas
                hasTbDown.put("R", "Contas a Receber");//contas a receber
                hasTbDown.put("D", "Sequência de Visitas");//sequencia de visitas
*/
                LayoutInflater inflater = (LayoutInflater) ActivityContainerFragments.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vdg = inflater.inflate(R.layout.dg_opt_down_tables, null);

                //ListView lvDgOpDownTab = (ListView)vdg.findViewById(R.id.lvDgOptDownTables);
                Button btDgOpDownTbWeb = (Button) vdg.findViewById(R.id.btDgOpDowntbOnline);
                Button btDgOpDowntbLocal = (Button) vdg.findViewById(R.id.btDgOpDowntbLocal);

                AlertDialog.Builder alb = new AlertDialog.Builder(ActivityContainerFragments.this);
                alb.setTitle("RECEBER CARGA");
                alb.setView(vdg);
                final AlertDialog alert = alb.show();//263
                btDgOpDownTbWeb.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        try {
                            new DropCreateDatabaseDirectAccess().dropTables(ActivityContainerFragments.this, new DropCreateDatabase());
                            TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, CurrentInfo.codVendedor);
                            task.execute(String.valueOf(Operations.DOWNLOAD_INTERNET));
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                btDgOpDowntbLocal.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        try {

                            if(new Utils().verifiIpAndPort(ctx) && Utils.verifiCnpjAndKey(ctx)) {
                                alert.dismiss();
                                //new DropCreateDatabaseDirectAccess().dropTables(ActivityContainerFragments.this, new DropCreateDatabase());
                                TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, CurrentInfo.codVendedor);
                                task.execute(String.valueOf(Operations.downloadLocal));
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
                //lvDgOpDownTab.setAdapter(new DgOpDownTablesAdap(ActivityContainerFragments.this, hasTbDown));
                break;
            case R.id.actLupa:
                try {
                    new Utils().dialogFilter(ActivityContainerFragments.this, fragmentManager);//, generalIntent);
                    //dialogFilter();
                    //Intent it = new Intent("ATV_CALC_PED");
                    //startActivityForResult(it, R_FG_HISTORICO);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                /*Snackbar.make(view, "TESTE...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                break;
            case R.id.actCesta:
                if(ControlFragment.isActivePedido() || ControlFragment.isActiveProd()){
                    ListaPedidoFragment listaPedidoFragment = new ListaPedidoFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.pedidoContainer, listaPedidoFragment, "listPedFrag");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    Utils.showMsg(ActivityContainerFragments.this, "ERRO", "OPERAÇÃO NÃO PERMITIDA PARA ESTA TELA", R.drawable.dialog_error);
                }
                break;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void apagaStatusPedido() {
        /*final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ActivityContainerFragments.this);
            dialogDelete.setTitle("DESEJA MESMO DELETAR ESTES ITENS");
            dialogDelete.setIcon(R.drawable.interrogacao);
            dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                */
        AlertDialog.Builder dgb = new AlertDialog.Builder(ActivityContainerFragments.this);
        dgb.setTitle("");
    }
    private void updateListViewListaPedido() throws SQLException {//reesibir itens da lista de pedidos
        ListaPedidoFragment listaPedidoFragment = (ListaPedidoFragment) fragmentManager.findFragmentByTag("listPedFrag");
        listaPedidoFragment.updateLista("");//todos os pedidos
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

            // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.corrigeItem){
            /************************TEMPORÁRIO********************************************************/
            //TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, CurrentInfo.codVendedor);
            //task.execute(String.valueOf(Operations.DOWNLOAD_INTERNET));
            TaskConnectNetWork task = new TaskConnectNetWork(ActivityContainerFragments.this, fragmentManager, CurrentInfo.codVendedor);
            task.execute(String.valueOf(Operations.CORRIGE_ITENS));
            /************************TEMPORÁRIO********************************************************/
        }else if(id == R.id.nav_clear_stat_cli){/***************************APAGA OS STATUS DOS CLIENTES A, P, N****************/
            AlertDialog.Builder albClearStat = new AlertDialog.Builder(ActivityContainerFragments.this);
            albClearStat.setTitle("DESEJA MESMO LIMPAR OS STATUS?");
            albClearStat.setPositiveButton("SIM", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dg, int which){
                    new Utils().clearStatClientes(ActivityContainerFragments.this);
                    if(ControlFragment.isActiveClientFrag()){
                        ClienteFragment cliFg = (ClienteFragment) fragmentManager.findFragmentByTag("cliFrag");

                        Pedido pdFilter = new Pedido();
                        pdFilter.setIdFormPg("");
                        //pdFilter.setRota(cliFg.getCurrentRota());//bundle.getLong("codRota"));

                        cliFg.fillListView(pdFilter);

                        AlertDialog.Builder albOk = new AlertDialog.Builder(ActivityContainerFragments.this);
                        albOk.setTitle("OPERAÇÃO CONLÚIDA");
                        albOk.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dg, int which){
                            }
                        });
                        albOk.show();
                    }
                }
            });
            albClearStat.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dg, int which){
                }
            });

            albClearStat.show();

        /*
            try {
                ArrayList<SeqVisitStatus> list = new SeqVisitStatusRN(ActivityContainerFragments.this).getListSeqVisitStat();
                for(SeqVisitStatus seqStat: list){
                    //if(!seqStat.getCodPed().equals("0")){
                    if(seqStat.getStatus().equals("P")){
                        Pedido ped = new PedidoRN(ActivityContainerFragments.this).getForId(Long.parseLong(seqStat.getCodPed()));
                        if(ped.getStatus().startsWith("Transmitido")){
                            Log.e("CLEAR", seqStat.getCodPed());
                            SeqVisit seqVisit = new SeqVisit();
                            seqVisit.setId(Integer.parseInt(seqStat.getSeq_id()));
                            seqStat.setStatus(seqStat.getSeq_id());
                            ArrayList<SeqVisit> seqViList = new SeqVisitRN(ActivityContainerFragments.this).pesquisar(seqVisit);
                            seqViList.get(0).setStatus("");
                            new SeqVisitRN(ActivityContainerFragments.this).update(seqViList.get(0));
                            new SeqVisitStatusRN(ActivityContainerFragments.this).deletar(seqStat.getSeq_id());
                        }
                    }else if(seqStat.getStatus().equals("N")){
                        Log.e("TEM_NEG", seqStat.getIdNegativa()+"");
                        Negativacao neg = new NegativacaoRN(ActivityContainerFragments.this).getForId(seqStat.getIdNegativa());//neste caso o campo se refere ao id da negarivação e não ao codigo do pedido
                        Log.e("TEM_NEG", neg.getStatus());
                        if(neg.getStatus().startsWith("Transmitido")){

                            SeqVisit seqVisit = new SeqVisit();
                            seqVisit.setId(Integer.parseInt(seqStat.getSeq_id()));
                            seqStat.setStatus(seqStat.getSeq_id());
                            ArrayList<SeqVisit> seqViList = new SeqVisitRN(ActivityContainerFragments.this).pesquisar(seqVisit);
                            seqViList.get(0).setStatus("");
                            new SeqVisitRN(ActivityContainerFragments.this).update(seqViList.get(0));
                            new SeqVisitStatusRN(ActivityContainerFragments.this).deletar(seqStat.getSeq_id());
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        }else if(id == R.id.nav_prods_vend){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ProdsVendsFg prodsVendsFg = new ProdsVendsFg();
            Bundle b = new Bundle();
            b.putBoolean("activeSave", false);
            prodsVendsFg.setArguments(b);

            transaction.replace(R.id.pedidoContainer, prodsVendsFg, "prodVendfg");
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.nav_justifica_fg){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            NegativacaoFragment justFg = new NegativacaoFragment();
            Bundle b = new Bundle();
            b.putBoolean("activeSave", false);
            justFg.setArguments(b);

            transaction.replace(R.id.pedidoContainer, justFg, "fragJust");
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id == R.id.nav_config) {
            Intent it = new Intent(Operations.ABRIR_CONFIG);
            startActivityForResult(it, RETURN_CONFIG);
        }else if(id == R.id.nav_histori_fg) {
            try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            HistoricoFragment histFg = new HistoricoFragment();
                Bundle b = new Bundle();
                b.putLong("datIni", 0l);

                b.putLong("datFi", 900000000000000000l);//busca até o ano 287168
                //CurrentInfo.codCli = 0;//////////////////CONTINUAR AQUI TROCAR O VALOR E RESTAURAR
                SeqVisit seq = new SeqVisitRN(ActivityContainerFragments.this).getSeqVisit(0);

            b.putLong("codRota", 0);//CurrentInfo.rota);

            Log.i("RHIST", CurrentInfo.rota+"");

                b.putDouble("codCli", 0);
                b.putString("status", "Transmitido");

                histFg.setArguments(b);
            transaction.replace(R.id.pedidoContainer, histFg, "historiFg");
            transaction.addToBackStack(null);
            transaction.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(id == R.id.nav_calc_ped){
            Intent it = new Intent("ATV_CALC_PED");
            startActivityForResult(it, ControlFragment.R_ATV_CALCS_PED);
        }else if(!ControlFragment.isActiveClientFrag() && !ControlFragment.isActiveRotaFg()) {
            /*if(id == R.id.nav_pedido_fragment){
                if(!ControlFragment.isActivePedido()) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ClienteTabFragment clienteTabFragment = new ClienteTabFragment();
                    Bundle b = new Bundle();
                    b.putString("nome", CurrentInfo.nomeCli);
                    clienteTabFragment.setArguments(b);
                    fragmentTransaction.replace(R.id.pedidoContainer, clienteTabFragment, "fragCliTab");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }else*/ if(id == R.id.nav_finalizar_pedido){
                    try {
                        ArrayList<ListaPedido> listPed = new ListaPedidoRN(ctx).getForNomeProd("");
                        if(listPed.size() > 0){
                            Pedido p = new CurrentInfo().getCurrentPedido(ActivityContainerFragments.this);
                            CurrentInfo.horaFim = String.format("%tT", Calendar.getInstance());
                            CurrentInfo.dataFim = new Data(Calendar.getInstance().getTimeInMillis()).getOnlyDataInMillis();//String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance());
                            p.setDataFim(CurrentInfo.dataFim);
                            p.setHoraFim(CurrentInfo.horaFim);
                            p.setStatus("Fechado");
                            if (ControlFragment.isActivePedido()) {
                                // PedidoFragment pedidoFragment = (PedidoFragment)fragmentManager.findFragmentByTag("pedidoFrag");
//                            pedidoFragment.updateDateTimeFinsh();
                                //                new PedidoRN(ActivityContainerFragments.this).update(p);
                            } else {
                                //new PedidoRN(ActivityContainerFragments.this).update(p);
                            }
                            new PedidoRN(ActivityContainerFragments.this).update(p);
                            /********************REALIZA POSITIVAÇÃO*******************************/
                            SeqVisit c = new SeqVisit();
                            Pedido pf = new Pedido();
                            pf.setStatus("Aberto");
                            ArrayList<Pedido> pdsAbertos = new PedidoRN(ActivityContainerFragments.this).listForCliAndStstus(pf);
                            if(pdsAbertos != null && pdsAbertos.size() == 0) {//muda status para P, somente se não existir pedidos abertos
                                c.setId(p.getSeqVist_id());
                                c.setStatus("P");
                                new SeqVisitRN(ActivityContainerFragments.this).update(c);

                                SeqVisitStatus sqVStat = new SeqVisitStatus();
                                sqVStat.setSeq_id(String.valueOf(c.getId()));
                                sqVStat.setCodPed(String.valueOf(CurrentInfo.idPedido));
                                sqVStat.setStatus(c.getStatus());
                                new SeqVisitStatusRN(ctx).update(sqVStat);
                            }
                            /********************REALIZA POSITIVAÇÃO*******************************/

                            /*
                            SeqVisitStatus sqVStat = new SeqVisitStatus();
                            sqVStat.setSeq_id(String.valueOf(c.getId()));
                            sqVStat.setCodPed(String.valueOf(CurrentInfo.idPedido));
                            sqVStat.setStatus(c.getStatus());
                            new SeqVisitStatusRN(ctx).update(sqVStat);
                            */

                            if(ControlFragment.isActivePedido()){
                                /*
                                FAZER ATUALIZAÇÃO DO STATUS DO FRAGMENT DE PEDIDO
                                PedidoFragment pFrag = (PedidoFragment) fragmentManager.findFragmentByTag("fragCliTab");
                                pFrag.updateStatus("FECHADO");*/

                            }

                            //Utils.showMsg(ActivityContainerFragments.this, null, "PEDIDO " + CurrentInfo.idPedido + " Fechado", null);
                            Toast.makeText(ActivityContainerFragments.this, "PEDIDO " + CurrentInfo.idPedido + " Fechado", Toast.LENGTH_SHORT).show();
                        }else{
                            Utils.showMsg(ActivityContainerFragments.this, "ERRO", "O PEDIDO "+ CurrentInfo.idPedido +" NÃO TEM ITENS", R.drawable.dialog_error);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityContainerFragments.this, "ERRO AO FECHAR O PEDIDO " + CurrentInfo.idPedido, Toast.LENGTH_SHORT).show();
                    }
            }else if(id == R.id.nav_del_item_marcado){
                    final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ActivityContainerFragments.this);
                    if (ControlFragment.isActiveListaPedidos()) {
                        dialogDelete.setTitle("DESEJA MESMO DELETAR ESTES ITENS");
                        dialogDelete.setIcon(R.drawable.interrogacao);
                        dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                int countItens = 0;
                                try {
                                Pedido pDel = new PedidoRN(ctx).getForId(CurrentInfo.idPedido);
                                if (!pDel.getStatus().equals("Transmitido")) {
                                    ArrayList<ListaPedido> listaPed = new ListaPedidoRN(ActivityContainerFragments.this).getForNomeProd("");
                                    new ListaPedidoRN(ActivityContainerFragments.this).delForStatus(CurrentInfo.idPedido);
                                    for (ListaPedido lp : listaPed) {
                                        if (lp.getDeletar().equals("s")) {
                                            new ItemListaRN(ActivityContainerFragments.this).delForIdItem(lp.getIdItem());

                                            double totalItem = lp.getItemLista().getTotal() * Long.parseLong(lp.getItemLista().getQtd().replace("/0", "").replace("0/", ""));
                                            pDel.setTotal(pDel.getTotal() - totalItem);
                                            new PedidoRN(ActivityContainerFragments.this).update(pDel);//atualiza total
                                            countItens++;
                                        }
                                    }
                                    if (countItens == listaPed.size()) {
                                        dialogDelete.setTitle("DESEJA TAMBÉM CANCELAR ESTE PEDIDO");
                                        dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    Pedido pCancel = new PedidoRN(ActivityContainerFragments.this).getForId(CurrentInfo.idPedido);//pedido a ser cancelado
                                                    new PedidoRN(ActivityContainerFragments.this).delPedidoForId(CurrentInfo.idPedido);

                                                    SeqVisit c = new SeqVisit();
                                                    c.setId(pCancel.getSeqVist_id());
                                                    c.setStatus("");
                                                    new SeqVisitRN(ActivityContainerFragments.this).update(c);

                                                    new SeqVisitStatusRN(ctx).deletar(String.valueOf(c.getId()));
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        dialogDelete.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        dialogDelete.show();
                                    }
                                    updateListViewListaPedido();
                                } else {
                                    Utils.showMsg(ActivityContainerFragments.this, "ERRO", "ESTE PEDIDO JÁ FOI TRANSMITIDO", R.drawable.dialog_error);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        });
                        dialogDelete.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialogDelete.show();
                    } else if(ControlFragment.isActiveHistoriFg()){
                        dialogDelete.setTitle("DESEJA MESMO DELETAR ESTES ITENS");
                        dialogDelete.setIcon(R.drawable.interrogacao);
                        dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ArrayList<ListaPedido> listaPed = null;
                                    ArrayList<Pedido> lPed = new ArrayList<Pedido>();
                                    try {
                                        Pedido pFilter = new Pedido();
                                        pFilter.setStatus("Transmitido");
                                        pFilter.setDel("s");
                                        pFilter.setCodCli(CurrentInfo.codCli);
                                        lPed = new PedidoRN(ActivityContainerFragments.this).filtrar(pFilter);
                                        if(lPed.size() > 0){
                                            for(Pedido p: lPed){
                                                listaPed = new ListaPedidoRN(ActivityContainerFragments.this).getForNomeProd("");
                                                new PedidoRN(ActivityContainerFragments.this).delPedidoForId(p.getId());
                                                for(ListaPedido lp: listaPed){
                                                    new ItemListaRN(ActivityContainerFragments.this).delForIdItem(lp.getIdItem());
                                                }
                                            }
                                        }
                                        HistoricoFragment histFg = (HistoricoFragment) fragmentManager.findFragmentByTag("historiFg");
                                        Pedido pFil = new Pedido();
                                        pFil.setDataInicio(0);
                                        pFil.setDataFim(90000000000000l);
                                        pFil.setStatus("Transmitido");
                                        pFil.setCodCli(CurrentInfo.codCli);
                                        pFil.setRota(histFg.bundle.getLong("codRota"));
                                        histFg.updateListView(pFil);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                        });
                        dialogDelete.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialogDelete.show();
                    } else if(ControlFragment.isActiveJustifica()){
                        dialogDelete.setTitle("DESEJA MESMO DELETAR ESTES ITENS");
                        dialogDelete.setIcon(R.drawable.interrogacao);
                        dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    NegativacaoFragment negFrag = (NegativacaoFragment) fragmentManager.findFragmentByTag("fragJust");
                                    negFrag.deleteSelecteds();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialogDelete.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialogDelete.show();
                    }else{
                        dialogDelete.setTitle("OPERAÇÃO NÃO PERMITIDA NESTA JANELA");
                        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialogDelete.show();
                    }
            } else if (id == R.id.nav_cancelar_pedido) {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ActivityContainerFragments.this);
                dialogDelete.setTitle("DESEJA MESMO CANCELAR O PEDIDO");
                dialogDelete.setIcon(R.drawable.interrogacao);
                dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ArrayList<ListaPedido> listaPed = new ListaPedidoRN(ActivityContainerFragments.this).getForNomeProd("");
                            Log.i("DEL_ITEM_LISTA", CurrentInfo.idPedido + "---" + CurrentInfo.codCli);
                            for (ListaPedido lp : listaPed) {
                                Log.i("DEL_ITEM_LISTA", lp.getIdItem() + "");
                                new ItemListaRN(ActivityContainerFragments.this).delForIdItem(lp.getIdItem());
                            }
                            Pedido pCancel = new PedidoRN(ActivityContainerFragments.this).getForId(CurrentInfo.idPedido);//pedido a ser cancelado
                            new PedidoRN(ActivityContainerFragments.this).delPedidoForId(CurrentInfo.idPedido);

                            /********************CANCELA POSITIVAÇÃO*******************************/
                            SeqVisit c = new SeqVisit();
                            c.setId(pCancel.getSeqVist_id());
                            c.setStatus("");
                            new SeqVisitRN(ActivityContainerFragments.this).update(c);

                            new SeqVisitStatusRN(ctx).deletar(String.valueOf(c.getId()));
                            /********************CANCELA POSITIVAÇÃO*******************************/
                            if (ControlFragment.isActiveListaPedidos()) {
                                updateListViewListaPedido();
                            }

                            Toast.makeText(ActivityContainerFragments.this, "PEDIDO CANCELADO", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogDelete.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogDelete.show();
            }
        }else {//if(ControlFragment.isActiveClientFrag()) {
            Utils.showMsg(this, "ERRO", "OPERAÇÃO NÃO PERMITIDA PARA ESTA JANELA", R.drawable.dialog_error);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private void dialogFilter() throws SQLException {
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
            }else if(ControlFragment.isActiveClientFrag()){
                edtNome.setVisibility(View.GONE);
                edtCodProd.setVisibility(View.GONE);
                edtDgCliRzSoci.setVisibility(View.VISIBLE);
                //  tbLayDgCliFrag.setVisibility(View.VISIBLE);
                edtDgCliFantasiaPesq.setVisibility(View.VISIBLE);
            }else if(ControlFragment.isActiveListaPedidos()){
                edtCodProd.setVisibility(View.GONE);
                edtDgCliRzSoci.setVisibility(View.GONE);
                //  tbLayDgCliFrag.setVisibility(View.GONE);
                edtDgCliFantasiaPesq.setVisibility(View.GONE);
            }else if(ControlFragment.isActiveHistoriFg()){
                edtNome.setVisibility(View.GONE);
                edtCodProd.setVisibility(View.GONE);
                edtDgCliRzSoci.setVisibility(View.VISIBLE);
                //tbLayDgCliFrag.setVisibility(View.GONE);
                edtDgCliFantasiaPesq.setVisibility(View.VISIBLE);
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
                            c.setFantasia(edtDgCliPesq.getText().toString());//razao social
                            c.setRzSocial(edtDgCliFantasiaPesq.getText().toString());//nome fantasia
                            clifrag.setCliFilter(c);//para continuar a pesquisa no fragment com o cliente como parte do filtro
                            long rot = clifrag.getCurrentRota();//generalIntent.getLongExtra("codRota", 0);
                            Log.i("ROT_BUSC", rot+"");
                            if(chDgPesqTodRot.isChecked()){
                                rot = 0;
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
                            startActivityForResult(it, R_FG_HISTORICO);*/
                        }
                        dialog.hide();
                    } catch (SQLException e) {
                        Log.e("erroSql", e.getMessage());
                    }
                }
            });
        }
/*        LayoutInflater inflate = (LayoutInflater) ActivityContainerFragments.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.dg_generic_pesq, null);

        final EditText edtNome = (EditText)v.findViewById(R.id.edtNome);
        final EditText edtCodProd = (EditText)v.findViewById(R.id.edtCodProd);
        final EditText edtDgCliPesq = (EditText)v.findViewById(R.id.edtDgCliPesq);
        final CheckBox chDgPesqTodRot = (CheckBox)v.findViewById(R.id.chDgPesqTodRot);
        final TableLayout tbLayDgProd = (TableLayout)v.findViewById(R.id.tbLayDgProd);//para pesquisar produtos
        final TableLayout tbLayDgCliFrag = (TableLayout)v.findViewById(R.id.tbLayDgCliFrag);//para pesqusar clientes

        final AlertDialog dialog;

        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityContainerFragments.this);
        alerta.setTitle("COMO VOCÊ QUER PROCURAR?");
        alerta.setIcon(R.drawable.pesq16x16);
        
        if(!ControlFragment.isActiveProd() && !ControlFragment.isActiveListaPedidos() && !ControlFragment.isActiveClientFrag()) {
            Utils.showMsg(ActivityContainerFragments.this, "ERRO", "OPERAÇÃO NÃO PERMITIDA NESTA TELA", R.drawable.dialog_error);
        }else {
            if(ControlFragment.isActiveProd()){
                edtNome.setVisibility(View.VISIBLE);
                edtCodProd.setVisibility(View.VISIBLE);
                tbLayDgCliFrag.setVisibility(View.GONE);
            }else if(ControlFragment.isActiveClientFrag()){
                edtNome.setVisibility(View.GONE);
                edtCodProd.setVisibility(View.GONE);
                tbLayDgCliFrag.setVisibility(View.VISIBLE);
            }else if(ControlFragment.isActiveListaPedidos()){
                edtCodProd.setVisibility(View.GONE);
                tbLayDgCliFrag.setVisibility(View.GONE);
            }

            alerta.setView(v);
            dialog = alerta.show();

            Button btPesquisar = (Button)v.findViewById(R.id.btPesquiProd);
            btPesquisar.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    Produto fseProd = new Produto();

                    fseProd.setFantasia(edtNome.getText().toString());
                    fseProd.setLinha("");
                    fseProd.setFornecedor("");

                    fseProd.setHora(edtCodProd.getText().toString());
                    fseProd.setTIPO("00");

                    long rot = generalIntent.getLongExtra("codRota", 0);
                    if(chDgPesqTodRot.isChecked()){
                        rot = 0;
                    }

                    try {
                        if(ControlFragment.isActiveProd()){
                            ArrayList<Produto> list = new ProdutoRN(ActivityContainerFragments.this).pesquisar(fseProd);
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
                            c.setFantasia(edtDgCliPesq.getText().toString());
                            clifrag.setCliFilter(c);//para continuar a pesquisa no fragment com o cliente como parte do filtro
                            clifrag.fillListView(rot, null);
                        }else if(ControlFragment.isActiveRotaFg()){
                            FragmentTransaction transaction = fragmentManager.beginTransaction();//abre aqui
                            ClienteFragment cliFg = new ClienteFragment();
                            Bundle b = new Bundle();
                            cliFg.setArguments(b);
                            transaction.replace(R.id.pedidoContainer, cliFg, "cliFrag");//fecha aqui
                        }
                        dialog.hide();
                    } catch (SQLException e) {
                        Log.e("erroSql", e.getMessage());
                    }
                }
            });
        }*/
    }

    private void mensagemPromocional() throws SQLException {
        ArrayList<Configuracao> config = new ConfiguracaoRN(this).pesquisar(new Configuracao());
        if(config.size() > 0){
            if(!config.get(0).getMensagem().equals("")){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                //dialog.setTitle("");
                //dialog.setIcon(icon.intValue());

                dialog.setMessage(config.get(0).getMensagem());
                dialog.show();
            }
        }
    }

    private ArrayList<Produto> pesquisar() throws SQLException {

        Produto sfeProd = new Produto();
        Toast.makeText(this, sfeProd.getNome()+"  "+sfeProd.getCodigo(), Toast.LENGTH_LONG).show();

        return new ProdutoRN(this).pesquisar(new Produto());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    protected void onResume(){
        super.onResume();

        try {
            Utils.verifiLicence(ActivityContainerFragments.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void onStart(){
        super.onStart();

        try {
            Utils.verifiLicence(ActivityContainerFragments.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Intent it = getIntent();
        switch(resultCode){
            case RETURN_CONFIG:
                try {
                    if(!Utils.verifiCnpjAndKey(ActivityContainerFragments.this)){
                        Utils.showMsg(ActivityContainerFragments.this, "ERRO", "CNJ DA EMPRESA NÃO ENCONTRADO, VÁ EM CONFIGURAÇÕES", R.drawable.dialog_error);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case ControlFragment.R_FG_HISTORICO:
/*
                HistoricoFragment histFg = (HistoricoFragment) fragmentManager.findFragmentByTag("cliFrag");
                Cliente c = new Cliente();
                c.setFantasia(edtDgCliPesq.getText().toString());//razao social
                c.setRzSocial(edtDgCliFantasiaPesq.getText().toString());//nome fantasia
                //  histFg.setCliFilter(c);//para continuar a pesquisa no fragment com o cliente como parte do filtro
                //long rot = histFg.getCurrentRota();//generalIntent.getLongExtra("codRota", 0);
                Pedido pFiltro = new Pedido();
                histFg.updateListView(0, null);*/
                break;
        }
    }

    public String getRetransmite() {
        return retransmite;
    }

    public void setRetransmite(String retransmite) {
        this.retransmite = retransmite;
    }
}