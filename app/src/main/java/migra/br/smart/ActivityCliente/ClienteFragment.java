package migra.br.smart.ActivityCliente;

import android.content.Context;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import migra.br.smart.ActivityCliente.fragmentJustifcativa.NegativacaoFragment;
import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.historiFg.HistoricoFragment;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.R;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

public class ClienteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Cliente cliente;
    private Cliente cliFilter;
    private SeqVisit seqVisit;
    private OnFragmentInteractionListener mListener;

    public boolean updateTotalRotaAtual;

    private long currentRota;

    private ListView lvFgCli;

    TextView tvRota, tvTotTodsRotas, tvTotRotAtual;
    ImageButton imgBtDatIni, imgBtDatFim;
    EditText edtDatIni, edtDatFim;
    Spinner spinStatusPed, spCodFormPg;
    public Button btTotalizar;
    CheckBox chTotTodsRotas, chTotRotAtual;

    private FragmentManager fragmentManager;

    private Bundle bundle;

    Context ctxConainer;

    Pedido pedCalc;

    public ClienteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ClienteFragment newInstance(String param1, String param2) {
        ClienteFragment fragment = new ClienteFragment();
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
            cli.setFantasia(getArguments().getString("nomeCli"));
            cli.setCnpj(getArguments().getString("cnpj"));
            cli.setTelefone(getArguments().getString("telefone"));
            cli.setRzSocial(getArguments().getString("razaoSocial"));
            cli.setCodigo(getArguments().getDouble("codigo"));

            currentRota = getArguments().getLong("codRota", 0);

            bundle = getArguments();
        }
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cli_fg, container, false);

        lvFgCli = (ListView) v.findViewById(R.id.lvFgCli);

        long dateToDay = Calendar.getInstance().getTimeInMillis();

        setCliFilter(new Cliente());

        /*
        if(bundle != null && bundle.getBoolean("calcTotCli")){
            blabla();
        }else{*/


            //Calendar dIni = Calendar.getInstance();//new Data(edtDatIni.getText().toString()).getCalendar();
            //dIni.set(Calendar.HOUR_OF_DAY, 1); dIni.set(Calendar.MINUTE, 0); dIni.set(Calendar.SECOND, 0);
        //dIni.set(Calendar.MILLISECOND, 0);
            //pedidoCalc.setDataInicio(new Data(edtDatIni.getText().toString()).getOnlyDataInMillis());
            //pedidoCalc.setDataInicio(dIni.getTimeInMillis());

            //pedidoCalc.setDataFim(new Data(edtDatFim.getText().toString()).getOnlyDataInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
            //Calendar dFim = Calendar.getInstance();//new Data(edtDatFim.getText().toString()).getCalendar();
            //dFim.set(Calendar.HOUR_OF_DAY, 23); dFim.set(Calendar.MINUTE, 59); dFim.set(Calendar.SECOND, 59);
        //dFim.set(Calendar.MILLISECOND, 0);
            //pedidoCalc.setDataFim(dFim.getTimeInMillis());

            //pdFilter.setDataInicio(0);//dIni.getTimeInMillis());//new Data(bundle.getString("datIni")).getOnlyDataInMillis());
            //pdFilter.setDataFim(0);//dFim.getTimeInMillis());//new Data(bundle.getString("datFim")).getOnlyDataInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
            //pdFilter.setStatus("Fechado");//spinStatusPed.getSelectedItem().toString());

            //Utils.setExtraQuery(" or status = 'Aberto' ");//pesquisar qualquer pedido que não esteja fechado

        Pedido pdFilter = new Pedido();
            pdFilter.setIdFormPg("");
            pdFilter.setRota(currentRota);//bundle.getLong("codRota"));
            //setCliFilter(new Cliente());

            fillListView(currentRota, pdFilter);
        //}

        lvFgCli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater linFlaCliAdapt = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vAdapt = linFlaCliAdapt.inflate(R.layout.cli_adp, null);
                final TextView tvNome = (TextView) vAdapt.findViewById(R.id.tvNomeClie);
                TextView tvRZSocial = (TextView) vAdapt.findViewById(R.id.tvRZSocial);

                seqVisit = (SeqVisit) lvFgCli.getAdapter().getItem(position);
                cliente = seqVisit.getCliente();

                final double cod = seqVisit.getCliente().getCodigo();
                final String cnpj = seqVisit.getCliente().getCnpj();
                final String tele = seqVisit.getCliente().getTelefone();
                final long prazoCli = seqVisit.getCliente().getPrazoPagamento();

                final Intent it = new Intent();//activity que contem todos os fragments
                final Bundle b = new Bundle();
                b.putString("nome", cliente.getFantasia());
                CurrentInfo.nomeCli = cliente.getFantasia();
                b.putString("razaoSocial", tvRZSocial.getText().toString());
                b.putString("cnpj", cnpj);
                b.putString("telefone", tele);
                b.putDouble("codigo", cod);
                b.putLong("prazo", prazoCli);
                b.putDouble("cliLimiCred", getCliente().getLimitCred());
                CurrentInfo.codCli = cod;
                CurrentInfo.cnpjCliente = cnpj;

                if (bundle != null && bundle.get("cliSelect") != null) {
                    Intent inte = new Intent();
                    inte.putExtras(b);
                    getActivity().setResult(1, inte);
                    getActivity().onBackPressed();
                } else{

                    LayoutInflater inflate = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vDg = inflate.inflate(R.layout.cli_fg_dg_opt, null);

                final Button btDgContRecCli = (Button) vDg.findViewById(R.id.btDgContRecCli);
                final Button btDgCliPedido = (Button) vDg.findViewById(R.id.imgbtDgCliIncluir);
                final Button btDgCliNewPed = (Button) vDg.findViewById(R.id.btDgCliNewPed);
                final Button btHistorico = (Button) vDg.findViewById(R.id.btHistorico);
                final Button btClientDgOptJustf = (Button) vDg.findViewById(R.id.btClientFgDgOptNegativ);

                AlertDialog.Builder alDB = new AlertDialog.Builder(getActivity());
                alDB.setPositiveButton("VOLTAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {

                    }
                });
                alDB.setView(vDg);

                final AlertDialog alD = alDB.show();

                btDgCliPedido.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(seqVisit.getStatus().equals("N")) {
                            AlertDialog.Builder alDb = new AlertDialog.Builder(getActivity());
                            alDb.setTitle("CANCELAR NEGATIVAÇÃO?");
                            alDb.setPositiveButton("SIM", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface d, int which){
                                    seqVisit.setStatus("");
                                    try {
                                        /********************************TESTE DE CANCELAMENTO DE NEGATIVAÇÃO*********************/
                                        Calendar cAtual = Calendar.getInstance();
                                        cAtual.set(Calendar.HOUR_OF_DAY, 1); cAtual.set(Calendar.MINUTE, 0);
                                        cAtual.set(Calendar.SECOND, 0); cAtual.set(Calendar.MILLISECOND, 0);

                                        Calendar cFinal = Calendar.getInstance();
                                        cFinal.set(Calendar.HOUR_OF_DAY, 23); cFinal.set(Calendar.MINUTE, 59);
                                        cFinal.set(Calendar.SECOND, 59); //cFinal.set(Calendar.MILLISECOND, 59);

                                        Negativacao neg = new Negativacao();
                                        neg.setCodCli(cliente.getCodigo());
                                        neg.setSeqVisitId(seqVisit.getId());
                                        neg.setDataInicio(cAtual.getTimeInMillis());
                                        neg.setDataFim(cFinal.getTimeInMillis());
                                        neg.setCodRota(getCurrentRota());
                                        new NegativacaoRN(getActivity()).deletarForFilter(neg);

                                        Log.i("NEEEE", "cli="+neg.getCodCli()+"- seq="+neg.getSeqVisitId()+"- datI="+neg.getDataInicio()+"- datF="+neg.getDataFim()+"- rot="+
                                        neg.getCodRota());

                                        /********************************TESTE DE CANCELAMENTO DE NEGATIVAÇÃO*********************/

                                        new SeqVisitRN(getActivity()).update(seqVisit);

                                        Toast.makeText(getActivity(), "NEGATIVAÇÃO CANCELADA", Toast.LENGTH_SHORT).show();
                                        CurrentInfo.codCli = cod;
                                        CurrentInfo.cnpjCliente = cnpj;
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        ClienteTabFragment clienteTabFrag = new ClienteTabFragment();
                                        b.putLong("rota", getCurrentRota());
                                        b.putInt("seqVisitId", seqVisit.getId());
                                        b.putDouble("codCli", getCliente().getCodigo());
                                        clienteTabFrag.setArguments(b);
                                        transaction.replace(R.id.pedidoContainer, clienteTabFrag, "fragCliTab");
                                        transaction.addToBackStack(null);
                                        transaction.commit();

                                        alD.dismiss();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            alDb.setNegativeButton("NÃO", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface d, int which){
                                }
                            });
                            alDb.show();
                        }else{
                            CurrentInfo.codCli = cod;
                            CurrentInfo.cnpjCliente = cnpj;
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            ClienteTabFragment clienteTabFrag = new ClienteTabFragment();
                            b.putLong("rota", getCurrentRota());
                            b.putInt("seqVisitId", seqVisit.getId());
                            b.putDouble("codCli", getCliente().getCodigo());
                            clienteTabFrag.setArguments(b);
                            transaction.replace(R.id.pedidoContainer, clienteTabFrag, "fragCliTab");
                            transaction.addToBackStack(null);
                            transaction.commit();
                            alD.dismiss();
                        }
                    }
                });

                btClientDgOptJustf.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        NegativacaoFragment justFg = new NegativacaoFragment();
                        Bundle b = new Bundle();
                        b.putString("fantasiCli", cliente.getFantasia());
                        b.putString("rzSocCli", cliente.getRzSocial());
                        b.putDouble("cliCod", cliente.getCodigo());
                        b.putLong("rotCod", getCurrentRota());
                        b.putInt("seqVisitId", seqVisit.getId());
                        b.putBoolean("activeSave", true);

                        justFg.setArguments(b);
                        transaction.replace(R.id.pedidoContainer, justFg, "fragJust");
                        transaction.addToBackStack(null);
                        transaction.commit();

                        alD.dismiss();
                    }
                });

                btDgCliNewPed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*CurrentInfo.codCli = cod;
                        CurrentInfo.cnpjCliente = cnpj;
                        //Pedido p = new Pedido();
                      //  p.setQtParcela(0);
                       // p.setIdFormPg("DIN");
                        //p.setPrazo(0);
                        //p.setRota(seqVisit.getCodRota());
                        //p.setSeqVist_id(seqVisit.getSeq_id());
                        //p.setCodCli(CurrentInfo.codCli);
                        Calendar c = Calendar.getInstance();
                        //p.setDataInicio(new Data(Calendar.getInstance().getTimeInMillis()).getOnlyDataInMillis());
                        //Calendar.getInstance().getTimeInMillis());//String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance()));
                        //p.setHoraInicio(String.format("%tT", Calendar.getInstance()));
                        //p.setRota(getCurrentRota());

                        //Bundle b = new Bundle();
                        b.putDouble("qtdParce", 1);
                        b.putString("formPg", "DIN");
                        b.putLong("prazo", 0);
                       // b.putLong("rota", seqVisit.getCodRota());
                        b.putLong("rota", getCurrentRota());
                        b.putInt("seqVisId", seqVisit.getSeq_id());
                        b.putDouble("codCli", CurrentInfo.codCli);
                        b.putLong("datIni", new Data(Calendar.getInstance().getTimeInMillis()).getOnlyDataInMillis());
                        b.putString("horaIni", String.format("%tT", Calendar.getInstance()));
                        b.putString("vender", "vista");//vender a vista

                        //try {
                            //new PedidoRN(getActivity()).salvar(p);
                            Log.i("CODIGO_CLI", CurrentInfo.codCli+"");
                            //it.putExtra("openFrag", "prodListFrag");//identifica o fragment a exibir lista de produtos
                            b.putString("openFrag", "fragCliTab");
                            it.putExtras(b);
                            it.setAction(Operations.ACTIVITY_CONTAINER_FRAGMENTS);
                            getActivity().startActivity(it);
                        //} catch (SQLException e) {
                          //  e.printStackTrace();
                        //}
                        */
                        Toast.makeText(getActivity(), "EM DESENVOLVIMENTO", Toast.LENGTH_SHORT).show();
                    }
                });

                btDgContRecCli.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        it.setAction("ABRIR_CONTAS_RECEBER");
                        it.putExtra("codCli", "yes");
                        CurrentInfo.codCli = cod;
                        CurrentInfo.cnpjCliente = cnpj;
                        getActivity().startActivity(it);
                    }
                });

                btHistorico.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        HistoricoFragment histFrag = new HistoricoFragment();
                        Bundle b = new Bundle();
                        b.putLong("datIni", 0l);
                        b.putLong("datFi", 900000000000000000l);//busca até o ano 287168
                        b.putLong("codRota", getCurrentRota());
                        b.putDouble("codCli", CurrentInfo.codCli);
                        b.putString("status", "Transmitido");

                        histFrag.setArguments(b);
                        transaction.replace(R.id.pedidoContainer, histFrag, "historiFg");
                        transaction.addToBackStack(null);
                        transaction.commit();

                        alD.dismiss();
                    }
                });
            }

            }
        });


        return v;
    }

    private void blabla(){
        pedCalc = new Pedido();

        pedCalc.setDataInicio(new Data(bundle.getString("datIni")).getOnlyDataInMillis());
        pedCalc.setDataFim(new Data(bundle.getString("datFim")).getOnlyDataInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
        pedCalc.setStatus(bundle.getString("status"));//spinStatusPed.getSelectedItem().toString());
        pedCalc.setIdFormPg(bundle.getString("formaPg"));
        pedCalc.setRota(bundle.getLong("codRota"));
        setCliFilter(new Cliente());
        Log.i("ASDF", "rota: "+pedCalc.getRota()+" dataIni: "+bundle.getString("datIni")+" datFm: "+bundle.getString("datFim")+" pg: "+bundle.getString("formaPg")+" status: "+bundle.getString("status"));
        fillListView(pedCalc.getRota(), pedCalc);
    }

    /**
    *@param pFiltro se true, realizar calculos, se false não realizar calculos
    **/
    //public void fillListView(long codRota, Pedido pFiltro){
    public void fillListView(long codRota, Pedido pFiltro){

        boolean encontrado = false;

        try {
            //ArrayList<SeqVisit> listCli = new SeqVisitRN(getActivity()).getWithClients(codRota, getCliFilter());
            ArrayList<SeqVisit> listCli = new SeqVisitRN(getActivity()).getWithClients(currentRota, getCliFilter());

            if(listCli.size() > 0){
                lvFgCli.setAdapter(new ClienteAdapter(getActivity(), listCli, pFiltro, bundle.getString("from"), getActivity().getSupportFragmentManager()));//getActivity().getSupportFragmentManager()));
                //Log.i("CLIFGG", pFiltro.getRota()+"");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void fillListView(Pedido pFiltro){
        fillListView(currentRota, pFiltro);
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // TODO: Rename method, updateForIdItem argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public long getCurrentRota() {
        return currentRota;
    }

    public void setCurrentRota(long currentRota) {
        this.currentRota = currentRota;
    }

    public void updateTotalCurrentRot(double valor){
        tvTotRotAtual.setText(String.format("%.2f", valor+Double.parseDouble(tvTotRotAtual.getText().toString().replace(",", "."))));
    }

    public boolean isUpdateTotalRotaAtual() {
        return updateTotalRotaAtual;
    }

    public void setUpdateTotalRotaAtual(boolean updateTotalRotaAtual) {
        this.updateTotalRotaAtual = updateTotalRotaAtual;
    }

    public boolean isCalcRotRotAtual(){
        return chTotRotAtual.isChecked();
    }

    public boolean isCalcTotAllRot(){
        return chTotTodsRotas.isChecked();
    }

    public Cliente getCliFilter() {
        return cliFilter;
    }

    public void setCliFilter(Cliente cliFilter) {
        this.cliFilter = cliFilter;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onResume(){
        super.onResume();

        //TEMPORARIAMENTE ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);

        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ControlFragment.setActiveClientFrag(true);
    }
    public void onStart(){
        super.onStart();

        //TEMPORARIAMENTE ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);


        try {
            Utils.verifiLicence(getActivity());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        ControlFragment.setActiveClientFrag(true);
        CurrentInfo.codCli = 0;
    }
    public void onStop(){
        super.onStop();
        ControlFragment.setActiveClientFrag(false);
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);
    }
}