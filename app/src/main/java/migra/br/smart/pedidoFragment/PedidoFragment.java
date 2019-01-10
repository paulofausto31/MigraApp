package migra.br.smart.pedidoFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.controlAtv.ControlAtv;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteRN;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgment;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.Activityproduto.ProdListFragment;
import migra.br.smart.R;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgmentRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;
import migra.br.smart.utils.permissions.PermissionUtils;

import static java.lang.Long.parseLong;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            com.google.android.gms.location.LocationListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private double latitude;
    private double longitude;
    GoogleApiClient mGoogleApiCliente;
    LocationRequest mLocationRequest;
    private SupportMapFragment mapFragment;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    Bundle bundlePedFrag;

    private String mParam1;
    private String mParam2;

    private Cliente cliente;

    private OnFragmentInteractionListener mListener;

    Spinner spiCodPedido, spiDataVenda, spiFormPgVenda, spEmpresas,
            spiPrazPgVenda, spiParcVenda;
    EditText edTxPrazoPgVenda, edtParceQtdVenda, edTxPrazMaxDias,
            edtDataIni, edtHoraIni, edtDataFim, edtHoraFim;
    TextView tvNomeCliPed, tvNomeCliLimiCred;
    Button btNovoPedido, btContinuar, btStatusPed, btFragPedDuplicar, btUpdate_frag_ped, btPedidoObsPedFg;

    private final ArrayList<String> codPedidos = new ArrayList<String>();
    private ArrayList<String> arLFormPgVenda;

    public PedidoFragment() {
        // Required empty public constructor
    }

    public static PedidoFragment newInstance(String param1, String param2) {
        PedidoFragment fragment = new PedidoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cliente cli = new Cliente();
        setCliente(cli);

        if (getArguments() != null) {
            cli.setFantasia(getArguments().getString("nome"));
            cli.setPrazoPagamento(getArguments().getLong("prazo"));
            cli.setLimitCred(getArguments().getDouble("cliLimiCred"));
            bundlePedFrag = getArguments();
        }

        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        boolean ok = PermissionUtils.validate(getActivity(), 0, permissions);
        if(ok){
            mGoogleApiCliente = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(PedidoFragment.this)
                    .addOnConnectionFailedListener(PedidoFragment.this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ped_fg, container, false);

        edTxPrazoPgVenda = (EditText)view.findViewById(R.id.edTxPrazoPgVenda);
        edTxPrazMaxDias = (EditText)view.findViewById(R.id.edTxPrazMaxDias);
        edtParceQtdVenda = (EditText)view.findViewById(R.id.edTxParceQtdVenda);
        edtDataIni = (EditText)view.findViewById(R.id.edtDataIni);
        edtHoraIni = (EditText)view.findViewById(R.id.edTxHoraIni);
        edtDataFim = (EditText)view.findViewById(R.id.edTxDataFim);
        edtHoraFim = (EditText)view.findViewById(R.id.edTxHoraFim);
        btNovoPedido = (Button) view.findViewById(R.id.btNovoPedido);
        btUpdate_frag_ped = (Button) view.findViewById(R.id.btUpdate_frag_ped);
        btContinuar = (Button)view.findViewById(R.id.btContinuar);
        btPedidoObsPedFg = (Button) view.findViewById(R.id.btPedidoObs);//duplicar um pedido para outro cliente
        btStatusPed = (Button)view.findViewById(R.id.btStatusPed);
        btFragPedDuplicar = (Button)view.findViewById(R.id.btFragPedDuplicar);//duplicar pedido
        spiCodPedido = (Spinner)view.findViewById(R.id.spinCodPedido);
        spiFormPgVenda = (Spinner)view.findViewById(R.id.spiFormPgVenda);
        tvNomeCliPed = (TextView)view.findViewById(R.id.tvNomeCliPed);

        spEmpresas = (Spinner) view.findViewById(R.id.spEmpresas);

        tvNomeCliLimiCred = (TextView) view.findViewById(R.id.tvNomeCliLimiCred);
        tvNomeCliLimiCred.setText(String.valueOf(getCliente().getLimitCred()));
        //edTxPrazMaxDias.setText(String.valueOf(getCliente().getPrazoPagamento()));


        try {
            ArrayList<Empresa> alEmp = new EmpresaRN(getActivity()).list(new Empresa());
            String[] arSpempr = new String[alEmp.size()+1];
            arSpempr[0] = "0-TODAS";
            for(int i = 1; i < alEmp.size()+1; i++){
                arSpempr[i] = alEmp.get(i-1).getId()+"-"+alEmp.get(i-1).getFantasia();
            }
            ArrayAdapter<String> arAdpspEmp = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, arSpempr);
            spEmpresas.setAdapter(arAdpspEmp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        spEmpresas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        try {
            ArrayList<Cliente> config = new ClienteRN(getActivity()).pesquisar(new Cliente());
            if(config != null && config.size() > 0 && config.get(0).getPrazoPagamento() > 0){
                edTxPrazMaxDias.setText(String.valueOf(config.get(0).getPrazoPagamento()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        //b.putDouble("qtdParce", 1);
        //b.putString("formPg", "DIN");
        //b.putLong("prazo", 0);
        // b.putLong("rota", seqVisit.getCodRota());
      //  b.putLong("rota", getCurrentRota());
       // b.putInt("seqVisId", seqVisit.getSeq_id());
       // b.putDouble("codCli", CurrentInfo.codCli);



        btUpdate_frag_ped.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    //ArrayList<Cliente> c = new ClienteRN(getActivity()).pesquisar(new Cliente());

                    Log.i("LIMIT", getCliente().getPrazoPagamento()+"");
                    ArrayList<Configuracao> config = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
                    Log.i("LIMITG", config.get(0).getPrazoMaxGeral()+"");

                    FormPgment formPg = new FormPgment();
                    formPg.setCodigo(spiFormPgVenda.getSelectedItem().toString());
                    final ArrayList<FormPgment> listFormPg = new FormPgmentRN(getActivity()).filtrarForCod(formPg);

                    ArrayList<Configuracao> configuracoes = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
                    double qtdParce = Double.parseDouble(edtParceQtdVenda.getText().toString());

                    if(configuracoes.size() > 0) {//existem configurações
                        if (listFormPg.get(0).getTipo().equals("P")) {
                            if (qtdParce > configuracoes.get(0).getMaxParcelas() || qtdParce <= 0) {
                                Utils.showMsg(getActivity(), "ERRO", "QUANTIDADE INVÁLIDA DE PARCELAS", R.drawable.dialog_error);
                            } else {
                                if (Long.parseLong(edTxPrazMaxDias.getText().toString()) > 0) {
                                    if (getCliente().getPrazoPagamento() == 0) {
                                        if (Long.parseLong(edTxPrazMaxDias.getText().toString()) <= Long.parseLong(listFormPg.get(0).getPrazoPadrao())) {
                                            update();
                                        } else {
                                            Utils.showMsg(getActivity(), "ERRO", "PRAZO EXCEDEU O LIMITE DE " +
                                                    listFormPg.get(0).getPrazoPadrao() + " DIAS"/* PARA "+listFormPg.get(0).getCodigo()*/, R.drawable.dialog_error);
                                        }
                                    } else if (getCliente().getPrazoPagamento() > 0) {
                                        if (Long.parseLong(edTxPrazMaxDias.getText().toString()) <= getCliente().getPrazoPagamento()) {
                                            update();
                                        } else {
                                            Utils.showMsg(getActivity(), "ERRO", "PRAZO EXCEDEU O LIMITE DE " +
                                                    getCliente().getPrazoPagamento() + " DIAS PARA ESTE CLIENTE", R.drawable.dialog_error);
                                        }
                                    }
                                } else {
                                    Utils.showMsg(getActivity(), "ERRO", "INSIRA UM PRAZO", R.drawable.dialog_error);
                                }
                                if (getCliente().getLimitCred() <= 0) {//experimental***********************************************************
                                    Utils.showMsg(getActivity(), "", "CLIENTE SEM CRÉDITO, SERÁ TRANSFORMADO PARA À VISTA NA DESCARGA", null);
                                }
                            }
                        } else if (listFormPg.get(0).getTipo().equals("A")) {
                            edtParceQtdVenda.setText("1");
                            if (Long.parseLong(edTxPrazMaxDias.getText().toString()) == 0) {
                                update();
                            } else {
                                Utils.showMsg(getActivity(), "ERRO", "PRAZO EXCEDEU O LIMITE DE " +
                                        listFormPg.get(0).getPrazoPadrao() + " DIAS PARA " + listFormPg.get(0).getCodigo(), R.drawable.dialog_error);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btFragPedDuplicar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Handler h = new Handler();
                h.post(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            new PedidoRN(getActivity()).duplicar();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        addSpiCodPedido();
                        Toast.makeText(PedidoFragment.this.getActivity(), "PEDIDO "+CurrentInfo.idPedido+" DUPLICADO", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btPedidoObsPedFg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(ControlAtv.ATV_PED_OBS);
                startActivity(it);
            }
        });

        btContinuar.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                try {
                    final Pedido p = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);

                    if(spiCodPedido.getSelectedItem() == null){
                        Utils.showMsg(getActivity(), "ERRO", "NENHUM PEDIDO SELECIONADO", R.drawable.dialog_error);
                    }else if(spiCodPedido.getSelectedItem() != null){
                        try {

                            Pedido ped = new PedidoRN(getActivity()).getForId(Long.parseLong(spiCodPedido.getSelectedItem().toString()));
                            if(ped.getId() == 0){
                                Utils.showMsg(getActivity(), "ERRO", "ESTE PEDIDO FOI CANCELADO, CLIQUE EM NOVO", R.drawable.dialog_error);
                            }else if(p.getStatus().equals("Transmitido")){
                                Utils.showMsg(getActivity(), "ERRO", "ESTE PEDIDO JÁ FOI TRANSMITIDO, TENTE DUPLICA-LO", R.drawable.dialog_error);
                            }else{
                                if(p.getStatus().equals("Fechado")) {
                                    //if (btStatusPed.getText().equals("FECHADO")) {
                                    AlertDialog.Builder aldB = new AlertDialog.Builder(getActivity());//Utils.showMsg(getActivity(), "", "DESEJA MESMO ABRIR ESTE PEDIDO", R.drawable.interrogacao);
                                    aldB.setTitle("DESEJA MESMO ABRIR ESTE PEDIDO");
                                    aldB.setIcon(R.drawable.interrogacao);
                                    aldB.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            btStatusPed.setText("Aberto");
                                            btStatusPed.setBackgroundResource(R.drawable.retangulo_verde_aberto);
                                            //btContinuar.setText("CONTINUAR");
                                            edtDataFim.setText("");
                                            edtHoraFim.setText("");
                                            CurrentInfo.dataFim = 0;
                                            CurrentInfo.horaFim = "";

                                            //Pedido p = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);
                                            p.setQtParcela(Double.parseDouble(edtParceQtdVenda.getText().toString()));
                                            p.setDataFim(0);
                                            p.setHoraFim(edtHoraFim.getText().toString());
                                            p.setStatus("Aberto");
                                            try {
                                                new PedidoRN(getActivity()).update(p);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                            startFragmentProdutos();
                                        }
                                    });
                                    aldB.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int which){
                                        }
                                    });
                                    aldB.show();
                                }else{
                                    startFragmentProdutos();
                                    /*else if(p.getStatus().equals("Transmitido")){
                        Utils.showMsg(getActivity(), "ERRO", "ESTE PEDIDO JÁ FOI TRANSMITIDO, TENTE DUPLICA-LO", R.drawable.dialog_error);
                    }else{

                        //startFragmentProdutos();
                    }*/
                                }

                                //startFragmentProdutos();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    /*
                    if(p.getStatus().equals("Fechado")) {
                        //if (btStatusPed.getText().equals("FECHADO")) {
                            AlertDialog.Builder aldB = new AlertDialog.Builder(getActivity());//Utils.showMsg(getActivity(), "", "DESEJA MESMO ABRIR ESTE PEDIDO", R.drawable.interrogacao);
                            aldB.setTitle("DESEJA MESMO ABRIR ESTE PEDIDO");
                            aldB.setIcon(R.drawable.interrogacao);
                            aldB.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btStatusPed.setText("Aberto");
                                    btStatusPed.setBackgroundResource(R.drawable.retangulo_verde_aberto);
                                    //btContinuar.setText("CONTINUAR");
                                    edtDataFim.setText("");
                                    edtHoraFim.setText("");
                                    CurrentInfo.dataFim = 0;
                                    CurrentInfo.horaFim = "";

                                    //Pedido p = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);
                                    p.setQtParcela(Double.parseDouble(edtParceQtdVenda.getText().toString()));
                                    p.setDataFim(0);
                                    p.setHoraFim(edtHoraFim.getText().toString());
                                    p.setStatus("Aberto");
                                    try {
                                        new PedidoRN(getActivity()).update(p);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                    startFragmentProdutos();
                                }
                            });
                            aldB.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                }
                            });
                            aldB.show();
                    }/*else if(p.getStatus().equals("Transmitido")){
                        Utils.showMsg(getActivity(), "ERRO", "ESTE PEDIDO JÁ FOI TRANSMITIDO, TENTE DUPLICA-LO", R.drawable.dialog_error);
                    }else{

                        //startFragmentProdutos();
                    }*/
                } catch (SQLException e) {
                    Log.e("erroPedidoFragment", e.getMessage());
                }
            }
        });


        ArrayList<String> listNomeCli = new ArrayList<String>();

        tvNomeCliPed.setText(bundlePedFrag.getString("nome"));

       // ArrayList<String> arLFormPgVenda  new ArrayList<String>();
        try {
            arLFormPgVenda = new FormPgmentRN(getActivity()).listaFormPagamento();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] arrFormPg = arLFormPgVenda.toArray(new String[arLFormPgVenda.size()]);
        ArrayAdapter<String> adapterFormPg =  new ArrayAdapter<String>(getActivity(), R.layout.spinner_prod_adapter,arrFormPg);

        spiFormPgVenda.setPrompt("FORMA DE PAGAMENTO");
        spiFormPgVenda.setAdapter(adapterFormPg);

        spiFormPgVenda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fillSpiCodPed();
        /******************************BUSCA O PEDIDO NO CODIGO SELECIONADO************************/
        spiCodPedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(!spiCodPedido.getSelectedItem().toString().equals("")) {
                        long idPedido = Long.parseLong(spiCodPedido.getSelectedItem().toString());
                        CurrentInfo.idPedido = idPedido;
                        Pedido p = new PedidoRN(getActivity()).getForId(idPedido);
                        edTxPrazMaxDias.setText(String.valueOf(p.getPrazo()));
                        spiFormPgVenda.setSelection(arLFormPgVenda.indexOf(p.getIdFormPg()));
                        spEmpresas.setSelection(p.getIdEmpresa());
                        if(p.getDataInicio() == 0){
                            edtDataIni.setText(String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance()));
                        }else{
                            edtDataIni.setText(new Data(p.getDataInicio()).getStringData());
                        }
                        edtHoraIni.setText(p.getHoraInicio());
                        edtParceQtdVenda.setText(String.valueOf(p.getQtParcela()));
                        edTxPrazoPgVenda.setText(String.valueOf(p.getPrazo()));
                        edtDataFim.setText(String.valueOf(new Data(p.getDataFim()).getStringData()));
                        edtHoraFim.setText(p.getHoraFim());
                        if(CurrentInfo.idPedido > 0){
                            Pedido ped = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);
                            btStatusPed.setText(ped.getStatus());
                            if(ped.getStatus().equals("Aberto")){
                                btStatusPed.setBackgroundResource(R.drawable.retangulo_verde_aberto);
                            }else{
                                btStatusPed.setBackgroundResource(R.drawable.retangulo_vermelho_fechado);
                            }
                        }

                        CurrentInfo.dataInicio = p.getDataInicio();
                        CurrentInfo.horaInicio = p.getHoraInicio();
                        CurrentInfo.dataFim = p.getDataFim();
                        CurrentInfo.horaFim = p.getHoraFim();
                        CurrentInfo.qtdParcela = p.getQtParcela();
                        CurrentInfo.prazo = p.getPrazo();
                        CurrentInfo.codFormPagamento = p.getIdFormPg();

                        if (p.getDataFim() == 0) {//se limpo o pedido pode continuar
                            CurrentInfo.continuaPedido = true;
                        } else {
                            CurrentInfo.continuaPedido = false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*******************************BUSCA O PEDIDO NO CODIGO SELECIONADO***********************/


        /*******************************INICIA NOVO PEDIDO*****************************************/
        btNovoPedido.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
            try {
                if(spEmpresas.getSelectedItemPosition() != 0) {//posição 0 não é empresa
                    ArrayList<Cliente> c = new ClienteRN(getActivity()).pesquisar(new Cliente());

                    Log.i("LIMIT", getCliente().getPrazoPagamento() + "");
                    ArrayList<Configuracao> config = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
                    Log.i("LIMITG", config.get(0).getPrazoMaxGeral() + "");

                    FormPgment formPg = new FormPgment();
                    formPg.setCodigo(spiFormPgVenda.getSelectedItem().toString());
                    ArrayList<FormPgment> listFormPg = new FormPgmentRN(getActivity()).filtrarForCod(formPg);
                    ArrayList<Configuracao> configuracoes = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
                    double qtdParce = Double.parseDouble(edtParceQtdVenda.getText().toString());

                    if (configuracoes.size() > 0) {//existem configurações
                        if (listFormPg.get(0).getTipo().equals("P")) {
                            if (qtdParce > configuracoes.get(0).getMaxParcelas() || qtdParce <= 0) {
                                Utils.showMsg(getActivity(), "ERRO", "QUANTIDADE INVÁLIDA DE PARCELAS", R.drawable.dialog_error);
                            } else {
                                if (Long.parseLong(edTxPrazMaxDias.getText().toString()) > 0) {
                                    if (getCliente().getPrazoPagamento() == 0) {
                                        if (Long.parseLong(edTxPrazMaxDias.getText().toString()) <= Long.parseLong(listFormPg.get(0).getPrazoPadrao())) {
                                            novoPedido(null);
                                        } else {
                                            Utils.showMsg(getActivity(), "ERRO", "PRAZO EXCEDEU O LIMITE DE " +
                                                    listFormPg.get(0).getPrazoPadrao() + " DIAS PARA " + listFormPg.get(0).getPrazoPadrao(), R.drawable.dialog_error);
                                        }
                                    } else if (getCliente().getPrazoPagamento() > 0) {
                                        if (Long.parseLong(edTxPrazMaxDias.getText().toString()) <= getCliente().getPrazoPagamento()) {
                                            novoPedido(null);
                                        } else {
                                            Utils.showMsg(getActivity(), "ERRO", "PRAZO EXCEDEU O LIMITE DE " +
                                                    getCliente().getPrazoPagamento() + " DIAS PARA ESTE CLIENTE", R.drawable.dialog_error);
                                        }
                                    }
                                } else {
                                    Utils.showMsg(getActivity(), "ERRO", "INSIRA UM PRAZO", R.drawable.dialog_error);
                                }

                                if (getCliente().getLimitCred() <= 0) {//experimental***********************************************************
                                    Utils.showMsg(
                                            getActivity(), "", "CLIENTE SEM CRÉDITO, SERÁ TRANSFORMADO PARA À VISTA NA DESCARGA", null);
                                }//experimental***********************************************************
                            }
                        } else if (listFormPg.get(0).getTipo().equals("A")) {
                            edtParceQtdVenda.setText("1");//uma parcela porque é a vista
                            if (Long.parseLong(edTxPrazMaxDias.getText().toString()) == 0) {
                                novoPedido(null);
                            } else {
                                Utils.showMsg(getActivity(), "ERRO", "PRAZO EXCEDEU O LIMITE DE " +
                                        listFormPg.get(0).getPrazoPadrao() + " DIAS PARA " + listFormPg.get(0).getCodigo(), R.drawable.dialog_error);
                            }
                        }
                    }
                }else{
                    Utils.showMsg(getActivity(), "ERRO", "ESCOLHA UMA EMPRESA", R.drawable.dialog_error);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            }
        });

       /* if(bundlePedFrag!= null && bundlePedFrag.getString("vender").equals("vista")){
           // Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente);
            //Toast.makeText(getActivity(), "LATITUDE---LONGITUDE"+l.getLatitude()+"----"+l.getLongitude(), Toast.LENGTH_LONG).show();
          //  Log.i("LAT--LONGI", +l.getLatitude()+"----"+l.getLongitude());

            Pedido p = new Pedido();
            p.setQtParcela(bundlePedFrag.getDouble("qtdParce"));
            p.setIdFormPg(bundlePedFrag.getString("formPg"));
            p.setPrazo(bundlePedFrag.getLong("prazo"));
            p.setRota(bundlePedFrag.getLong("rota"));
            p.setSeqVist_id(bundlePedFrag.getInt("seqVisId"));
            p.setCodCli(bundlePedFrag.getDouble("codCli"));
            p.setDataInicio(bundlePedFrag.getLong("datIni"));
            p.setHoraInicio(bundlePedFrag.getString("horaIni"));
            //p.setLongitudeInicio(l.getLatitude());
            //p.setLongitudeInicio(l.getLongitude());

            try {
                new PedidoRN(getActivity()).salvar(p);
                novoPedido(p);
                /*Intent it = new Intent();
                it.putExtra("openFrag", "prodListFrag");//identifica o fragment a exibir lista de produtos
                it.setAction(Operations.ACTIVITY_CONTAINER_FRAGMENTS);
                getActivity().startActivity(it);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        return view;
    }

    public void updateStatus(String status){
        btStatusPed.setText(status);
    }

    private void update() throws SQLException {
        Pedido p = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);
        p.setQtParcela(Double.parseDouble(edtParceQtdVenda.getText().toString()));
        p.setIdFormPg(spiFormPgVenda.getSelectedItem().toString());
        p.setPrazo(Long.parseLong(edTxPrazMaxDias.getText().toString()));

        new PedidoRN(getActivity()).update(p);
        Log.i("QTD_PARCE", p.getQtParcela() + "");
        Toast.makeText(getActivity(), "DADOS ATUALIZADOS", Toast.LENGTH_SHORT).show();

    }

    private void novoPedido(Pedido ped){
        /*funciona Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente);
        Toast.makeText(getActivity(), +l.getLatitude()+"----"+l.getLongitude(), Toast.LENGTH_LONG).show();
        Log.i("LAT--LONGI", +l.getLatitude()+"----"+l.getLongitude());*/
        double latitude = 0;
        double longitude = 0;

        Calendar cal = Calendar.getInstance();
        edtHoraIni.setText(String.format("%tT", cal));
        edtDataIni.setText(new Data(cal.getInstance().getTimeInMillis()).getStringData());
        try {
            /*ArrayList<Configuracao> configuracoes = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
            if(configuracoes.size() > 0){//existem configurações*/
                if(ped == null) {
                    ped = new Pedido();
                    ped.setIdEmpresa(Integer.parseInt(spEmpresas.getSelectedItem().toString().split("-")[0]));
                    ped.setQtParcela(Double.parseDouble(edtParceQtdVenda.getText().toString()));
                    ped.setIdFormPg(spiFormPgVenda.getSelectedItem().toString());
                    ped.setPrazo(parseLong(edTxPrazoPgVenda.getText().toString()));
                    //ped.setDataInicio(new Data(edtDataIni.getText().toString()).getOnlyDataInMillis());
                    Calendar dIni = new Data(edtDataIni.getText().toString()).getCalendar();
                    dIni.set(Calendar.HOUR_OF_DAY, 1);//todas as vendas sao feitas as 1 hora de qualquer dia, depois isso e descnsiderado
                    //ped.setDataInicio(new Data(edtDataIni.getText().toString()).getDataInMillis());
                    ped.setDataInicio(dIni.getTimeInMillis());
                    ped.setHoraInicio(edtHoraIni.getText().toString());
                    ped.setCodCli(CurrentInfo.codCli);
                    ped.setRota(bundlePedFrag.getLong("rota"));
                    Log.i("ROTA_PED", ped.getRota() + "");
                    ped.setSeqVist_id(bundlePedFrag.getInt("seqVisitId"));
                    ped.setLatitudeInicio(latitude);//l.getLatitude());
                    ped.setLongitudeInicio(longitude);//l.getLongitude());
                    ped.setPrazo(Long.parseLong(edTxPrazMaxDias.getText().toString()));

                    /*FUNCIONA if (ped.getQtParcela() > configuracoes.get(0).getMaxParcelas() || ped.getQtParcela() < 0) {
                        Utils.sh owMsg(getActivity(), "ERRO", "QUANTIDADE INVÁLIDA DE PARCELAS", R.drawable.dialog_error);
                    FUNCIONA } else {*/
                        new PedidoRN(getActivity().getBaseContext()).salvar(ped);

                        SeqVisit c = new SeqVisit();
                        c.setId(bundlePedFrag.getInt("seqVisitId"));
                        c.setStatus("A");
                        new SeqVisitRN(getActivity()).update(c);

                        SeqVisitStatus seqVisitStatus = new SeqVisitStatus();
                        seqVisitStatus.setSeq_id(String.valueOf(c.getId()));
                        seqVisitStatus.setCodPed(String.valueOf(CurrentInfo.idPedido));
                        seqVisitStatus.setStatus(c.getStatus());

                        new SeqVisitStatusRN(getActivity()).salvar(seqVisitStatus);
                    //FUNCIONA }
                }else{
                    ped.setLatitudeInicio(latitude);//l.getLatitude());
                    ped.setLongitudeInicio(longitude);//l.getLongitude());
                    new PedidoRN(getActivity().getBaseContext()).salvar(ped);
                }
                CurrentInfo.idPedido = ped.getId();
                CurrentInfo.dataInicio = ped.getDataInicio();
                CurrentInfo.horaInicio = ped.getHoraInicio();
                addSpiCodPedido();

                ProdListFragment prodListFragment = new ProdListFragment();
                FragmentTransaction fragmenttransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmenttransaction.replace(R.id.pedidoContainer, prodListFragment, "prodListFrag");
                fragmenttransaction.addToBackStack(null);
                fragmenttransaction.commit();
            /*}else{
                Utils.showMsg(getActivity(), "ERRO", "CONFIGURAÇÕES AUSENTES OU INCOMPLETAS", R.drawable.dialog_error);
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void startFragmentProdutos(){
            CurrentInfo.idPedido = Long.parseLong(spiCodPedido.getSelectedItem().toString());
            ProdListFragment prodListFragment = new ProdListFragment();
            FragmentTransaction fragmenttransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmenttransaction.replace(R.id.pedidoContainer, prodListFragment, "prodListFrag");
            fragmenttransaction.addToBackStack(null);
            fragmenttransaction.commit();
    }

    private void addSpiCodPedido(){
        codPedidos.add(String.valueOf(CurrentInfo.idPedido));
        Object[] listCodVenda = codPedidos.toArray();
        ArrayAdapter<Object> adapter =  new ArrayAdapter<Object>(getActivity(), android.R.layout.simple_spinner_item,listCodVenda);
        spiCodPedido.setAdapter(adapter);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void updateDateTimeFinsh(){
        Calendar c = Calendar.getInstance();
        CurrentInfo.horaFim = String.format("%tT", c);
        CurrentInfo.dataFim = Calendar.getInstance().getTimeInMillis();//String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance());
        edtHoraFim.setText(CurrentInfo.horaFim);
        edtDataFim.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));
        btStatusPed.setText("Fechado");
        btStatusPed.setBackgroundResource(R.drawable.retangulo_vermelho_fechado);
        //btContinuar.setText("ABRIR E CONTINUAR");
    }

    private void fillSpiCodPed(){
        try {
            Pedido pFilter = new Pedido();
            String[] lCodPed = new PedidoRN(getActivity()).listForCodPedAndCli();
            if(lCodPed.length > 0) {
                ArrayAdapter<String> adpCodPed = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, lCodPed);
                spiCodPedido.setAdapter(adpCodPed);

                for (int i = 0; i < lCodPed.length; i++) {
                    if (lCodPed[i].equals(CurrentInfo.idPedido)) {
                        spiCodPedido.setSelection(i);
                    }
                }
            }else{
                CurrentInfo.idPedido = 0;//não há pedidos
            }

            Log.i("FragmentPedidoCodCli", ""+CurrentInfo.codCli);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Toast.makeText(getActivity(), "RESTORNO: sgdsgsfsf", Toast.LENGTH_SHORT).show();
        switch(requestCode){
            case 1:
                Toast.makeText(getActivity(), "RETORNO: "+intent.getExtras().get("nome"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // TODO: Rename method, updateForIdItem argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onStop(){
        /**********funciona*********************************
        stopLocationUpdates();
        mGoogleApiCliente.disconnect();
         */
        super.onStop();
    }

    public void onPause(){
        super.onPause();
        //funciona stopLocationUpdates();
        ControlFragment.setActivePedido(false);
    }

    public void onStart(){
        super.onStart();

        //temporariamente ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);
/***********funciona********************
        mGoogleApiCliente.connect();
*/
        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControlFragment.setActivePedido(true);
        fillSpiCodPed();

        Log.i("PED", "ATV_PED");
    }

    public void onResume(){
        super.onResume();

        //temporariamente ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);

        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControlFragment.setActivePedido(true);
        fillSpiCodPed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Falha na conexão gps", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
/***********funciona********************
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.i("LATTUDE, LONGITUDE", latitude+"-----"+longitude);
 /***********funciona********************/
    }

    protected void startLocationUpdate(){
/******************TESTE*****************
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiCliente, mLocationRequest, this
        );
        ***********funciona********************/
    }

    protected void stopLocationUpdates(){
        /******************TESTE*****************
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiCliente, this);
         ***********funciona********************/
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}