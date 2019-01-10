package migra.br.smart;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.ActivityCliente.ClienteAdapter;
import migra.br.smart.ActivityCliente.ClienteFragment;
import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgmentRN;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.rota.RotaRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

public class AtvCalcsPed extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Button btAtvCalcsPedVoltar, btTotForRot, btTotForCli, btAtvCalcsPedOk;
    private ImageButton btHidShow;
    private TextView tvTotRotAtual, tvTotTodsRotas, tvRotPositivaInt, tvRotNegativaInt,
            tvAtvCalTotCliRot, tvAtvCalFalta;
    private EditText edtDatIni, edtDatFim;
    private Spinner spinStatusPed, spCodFormPg, spDgCalcPedRotas;
    private CheckBox chTotRotAtual, chTotTodsRotas;
    private ListView lvAtvCalcPed;

    private HorizontalScrollView scrow1;
    private TableRow row2, row3;

    private Bundle bundle;

    ArrayList<Rota> rotasDgCalcPedidos;
    Pedido pedidoCalc;

    private long currentRota;

    private boolean visibleFilter = true;//para exibir e ecultar o filtro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atv_calcs_ped);

        if(getIntent() != null){
            bundle = getIntent().getExtras();
        }

        fragmentManager = getSupportFragmentManager();

        long dateToDay = Calendar.getInstance().getTimeInMillis();

        btTotForRot = (Button) findViewById(R.id.btTotForRot);
        //btAtvCalcsPedVoltar = (Button) findViewById(R.id.btAtvCalcsPedVoltar);
        btTotForCli = (Button) findViewById(R.id.btTotForCli);
        lvAtvCalcPed = (ListView) findViewById(R.id.lvAtvCalcPed);
        //btAtvCalcsPedOk = (Button) findViewById(R.id.btAtvCalcsPedOk);


        final Button btTotForRot = (Button) findViewById(R.id.btTotForRot);
        btHidShow = (ImageButton) findViewById(R.id.btHidShow);

        final TextView tvRotPositivacao = (TextView) findViewById(R.id.tvRotPositivacao);
        final TextView tvRotNegativacao  = (TextView) findViewById(R.id.tvRotNegativacao);

        tvTotTodsRotas = (TextView) findViewById(R.id.tvTotTodsRotas);//TOTAL DE TODAS AS ROTAS
        tvTotRotAtual = (TextView) findViewById(R.id.tvTotRotAtual);//TOTAL DA ROTA ATUAL
        edtDatIni = (EditText) findViewById(R.id.edtDatIni);
        edtDatFim = (EditText) findViewById(R.id.edtDatFim);
        spinStatusPed = (Spinner) findViewById(R.id.spinStatusPed);
        spCodFormPg = (Spinner) findViewById(R.id.spCodFormPg);
        spDgCalcPedRotas = (Spinner) findViewById(R.id.spDgCalcPedRotas);

        tvRotPositivaInt = (TextView)findViewById(R.id.tvRotPositivaInt);//equivalente em numero inteiro do percentual positivado
        tvRotNegativaInt = (TextView) findViewById(R.id.tvRotNegativaInt);//equilvalente em numero inteiro do percentual de negativacao
        tvAtvCalTotCliRot = (TextView) findViewById(R.id.tvAtvCalTotCliRot);//total de clientes na rota atual
        tvAtvCalFalta = (TextView) findViewById(R.id.tvAtvCalFalta);//total de clientes que faltam atender

        scrow1 = (HorizontalScrollView) findViewById(R.id.scrow1);
        row2 = (TableRow) findViewById(R.id.row2);
        row3 = (TableRow) findViewById(R.id.row3);

        btHidShow.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                if(!visibleFilter){//esta oculto
                    row2.setVisibility(View.VISIBLE);
                    row3.setVisibility(View.VISIBLE);
                    scrow1.setVisibility(View.VISIBLE);
                    visibleFilter = true;
                }else{
                    row2.setVisibility(View.GONE);
                    row3.setVisibility(View.GONE);
                    scrow1.setVisibility(View.GONE);
                    visibleFilter = false;
                }

            }
        });

        edtDatIni.setText(new Data(dateToDay).getStringData());
        edtDatFim.setText(new Data(dateToDay).getStringData());

        /***************************preenche rotas*********************************************/

        try {
            rotasDgCalcPedidos = new RotaRN(this).getRouteForSalesMan(CurrentInfo.codVendedor);
            int indexRota = 0;
            if(rotasDgCalcPedidos.size() > 0) {
                String[] arRota = new String[rotasDgCalcPedidos.size()];
                for(int i = 0; i < rotasDgCalcPedidos.size(); i++){
                    arRota[i] = rotasDgCalcPedidos.get(i).getDescricao();
                    if(rotasDgCalcPedidos.get(i).getCodigo() == CurrentInfo.rota){
                        indexRota = i;
                    }
                }
                ArrayAdapter<String> arAdp = new ArrayAdapter<String>(AtvCalcsPed.this, R.layout.spin_ped_adapter_cod_ped, arRota);
                spDgCalcPedRotas.setAdapter(arAdp);

                spDgCalcPedRotas.setSelection(indexRota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        spDgCalcPedRotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRota = rotasDgCalcPedidos.get(position).getCodigo();
                //pedidoCalc.setRota(rotasDgCalcPedidos.get(position).getHora());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /***************************preenche rotas*************************************************/

        /******************************************PREENCHE FORMA DE PAGAMENTO*********************/
        String listFormPgVenda[] = null;
        try {
            ArrayList<String> arLFormPgVenda = new FormPgmentRN(AtvCalcsPed.this).listaFormPagamento();
            arLFormPgVenda.add(0,"");//
            listFormPgVenda = arLFormPgVenda.toArray(new String[arLFormPgVenda.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapterFormPg =  new ArrayAdapter<String>(AtvCalcsPed.this, R.layout.spin_ped_adapter_cod_ped, listFormPgVenda);
        spCodFormPg.setAdapter(adapterFormPg);
        /*******************************************PREENCHE FORMA DE PAGAMENTO********************/

        /*******************************PREENCHE STATUS PEDIDO*************************************/
        String[] itensSpinStatus = new String[]{"Transmitido", "Aberto", "Fechado"};//CONTINUA AQUI
        ArrayAdapter<String> adaptString = new ArrayAdapter<String>(AtvCalcsPed.this, R.layout.spin_ped_adapter_cod_ped, itensSpinStatus);
        spinStatusPed.setAdapter(adaptString);
        spinStatusPed.setSelection(2);
        /*******************************PREENCHE STATUS PEDIDO*************************************/

        btTotForRot.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                tvTotRotAtual.setText("0000");
                tvTotTodsRotas.setText("0000");

                pedidoCalc = new Pedido();

                Log.i("datNow", new Data(edtDatIni.getText().toString()).getOnlyDataInMillis()+"");

                if(edtDatIni.getText().toString().contains("/")){
                    Calendar dIni = new Data(edtDatIni.getText().toString()).getCalendar();
                    dIni.set(Calendar.HOUR_OF_DAY, 1);
                    dIni.set(Calendar.MINUTE, 0);
                    dIni.set(Calendar.SECOND, 0);
                    dIni.set(Calendar.MILLISECOND, 0);
                    //pedidoCalc.setDataInicio(new Data(edtDatIni.getText().toString()).getOnlyDataInMillis());
                    pedidoCalc.setDataInicio(dIni.getTimeInMillis());

                    //pedidoCalc.setDataFim(new Data(edtDatFim.getText().toString()).getOnlyDataInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
                    Calendar dFim = new Data(edtDatFim.getText().toString()).getCalendar();
                    dFim.set(Calendar.HOUR_OF_DAY, 23);
                    dFim.set(Calendar.MINUTE, 59);
                    dFim.set(Calendar.SECOND, 59);
                    pedidoCalc.setDataFim(dFim.getTimeInMillis());
                    pedidoCalc.setStatus(spinStatusPed.getSelectedItem().toString());//spinStatusPed.getSelectedItem().toString());
                    pedidoCalc.setIdFormPg(spCodFormPg.getSelectedItem().toString());

                    pedidoCalc.setRota(currentRota);
                    /*Log.i("ASDF", pedidoCalc.getRota()+"");
                    fillListView(currentRota, pedidoCalc);*/

                    double percentPositive = 0;
                    double percentNegative = 0;
                    double totPed = 0;//total de pedidos efetuados
                    ArrayList<Pedido> lPed = new ArrayList<Pedido>();

                    double totalCli = 0;

                    try {
                       // totalCli = new SeqVisitStatusRN(AtvCalcsPed.this).count(pedidoCalc.getRota());
                        Log.i("CONT", totalCli+"");

                        ArrayList<SeqVisit> lSeq = new SeqVisitRN(AtvCalcsPed.this).getWithClients(pedidoCalc.getRota(), new Cliente());

                        for(SeqVisit seq:lSeq) {
                            pedidoCalc.setCodCli(seq.getCodCliente());
                            lPed = new PedidoRN(AtvCalcsPed.this).filtrar(pedidoCalc);
                            if(lPed.size() > 0){
                                ++totPed;
                            }
                            for (int i = 0; i < lPed.size(); i++) {
                                if (lPed.get(i).getTotal() > 0) {
                                    tvTotRotAtual.setText(String.format("%.2f", lPed.get(i).getTotal() + Double.parseDouble(tvTotRotAtual.getText().toString().replace(",", "."))));
                                }
                            }
                        }

                        Log.i("POS", ""+totPed);
                        Log.i("totseq", ""+lSeq.size());

                        tvAtvCalTotCliRot.setText(String.valueOf(lSeq.size()));

                        tvRotPositivaInt.setText(String.valueOf(totPed));

                        percentPositive = (totPed/lSeq.size())*100;
                        tvRotPositivacao.setText(String.format("%.2f", percentPositive)+"%");

                        Negativacao ne = new Negativacao();
                        ne.setCodRota(pedidoCalc.getRota());
                        ne.setDataInicio(pedidoCalc.getDataInicio());
                        ne.setDataFim(pedidoCalc.getDataFim());
                        Utils.setExtraQuery(" and data >= '"+ne.getDataInicio()+"' and data <= '"+ne.getDataFim()+"'");

                        ArrayList<Negativacao> arrLNe = new NegativacaoRN(AtvCalcsPed.this).filtrar(ne);
                        tvRotNegativaInt.setText(String.valueOf(arrLNe.size()));
                        Log.i("NEGCAL", arrLNe.size()+"");
                        percentNegative = ((arrLNe.size())/(double)lSeq.size())*100;
                        tvRotNegativacao.setText(String.format("%.2f", percentNegative)+"%");

                        tvAtvCalFalta.setText(String.valueOf(lSeq.size()-(totPed+arrLNe.size())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        ArrayList<Rota> lRotas = new RotaRN(AtvCalcsPed.this).getRouteForSalesMan(CurrentInfo.codVendedor);
                        for(Rota r:lRotas){
                            pedidoCalc.setRota(r.getCodigo());
                            ArrayList<SeqVisit> lSeq = new SeqVisitRN(AtvCalcsPed.this).getWithClients(r.getCodigo(), new Cliente());
                            for(SeqVisit seq:lSeq){
                                pedidoCalc.setCodCli(seq.getCodCliente());
                                /*ArrayList<Pedido> */lPed = new PedidoRN(AtvCalcsPed.this).filtrar(pedidoCalc);
                                for(int i = 0; i < lPed.size(); i++){
                                    if(lPed.get(i).getTotal() > 0){
                                        tvTotTodsRotas.setText(String.format("%.2f", lPed.get(i).getTotal() + Double.parseDouble(tvTotTodsRotas.getText().toString().replace(",", "."))));
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    fillListView();
                }else{
                    Utils.showMsg(AtvCalcsPed.this, "ERRO", "FORMATO INCORRETO DE DATA", R.drawable.dialog_error);
                }
            }
        });
    }

    public void fillListView(){
        if(edtDatIni.getText().toString().contains("/")) {
            Calendar dIni = new Data(edtDatIni.getText().toString()).getCalendar();
            dIni.set(Calendar.HOUR_OF_DAY, 1);
            dIni.set(Calendar.MINUTE, 0);
            dIni.set(Calendar.SECOND, 0);
            //pedidoCalc.setDataInicio(new Data(edtDatIni.getText().toString()).getOnlyDataInMillis());
            pedidoCalc.setDataInicio(dIni.getTimeInMillis());

            Calendar dFim = new Data(edtDatFim.getText().toString()).getCalendar();
            dFim.set(Calendar.HOUR_OF_DAY, 23);
            dFim.set(Calendar.MINUTE, 59);
            dFim.set(Calendar.SECOND, 59);
            //pedidoCalc.setDataFim(new Data(edtDatFim.getText().toString()).getOnlyDataInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
            pedidoCalc.setDataFim(dFim.getTimeInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
            pedidoCalc.setStatus(spinStatusPed.getSelectedItem().toString());//spinStatusPed.getSelectedItem().toString());
            pedidoCalc.setIdFormPg(spCodFormPg.getSelectedItem().toString());
            //Bundle b = new Bundle();
                    /*ClienteFragment cliFg = (ClienteFragment) fragmentManager.findFragmentByTag("cliFrag");

                    if(ControlFragment.isActiveClientFrag()){
                        pedidoCalc.setRota(cliFg.getCurrentRota());
                    }*/
            //Pedido p2 = pedidoCalc;

            pedidoCalc.setRota(currentRota);
            Log.i("ASDF", pedidoCalc.getRota() + "");
            //fillListView(currentRota, pedidoCalc);

            try {
                ArrayList<SeqVisit> listCli = new SeqVisitRN(this).getWithClients(currentRota, new Cliente());
                if (listCli.size() > 0) {
                    lvAtvCalcPed.setAdapter(new ClienteAdapter(this, listCli, pedidoCalc, null, this.getSupportFragmentManager()));//getActivity().getSupportFragmentManager()));
                    //Log.i("CLIFGG", pFiltro.getRota()+"");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
