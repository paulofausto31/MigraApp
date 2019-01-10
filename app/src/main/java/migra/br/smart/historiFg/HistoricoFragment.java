package migra.br.smart.historiFg;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.listaPedidoFg.ListaPedidoFragment;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.rota.RotaRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

public class HistoricoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView lvHistoriFg;
    private Button btHistPesqFg;
    private ImageButton btHistShoHid;
    private Spinner spiHistFgRota, spEmpresas;
    private TableLayout tbLayHistFg;

    private EditText edtHistDatIni, edtHistDatFim;

    public Bundle bundle;

    private OnFragmentInteractionListener mListener;

    private boolean shoHidFilter;

    private ArrayList<Rota> rotasHistFg;
    private long currentRota;

    public HistoricoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HistoricoFragment newInstance(String param1, String param2) {
        HistoricoFragment fragment = new HistoricoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            bundle = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.histori_fg, container, false);

        btHistPesqFg = (Button) v.findViewById(R.id.btHistPesqFg);
        edtHistDatIni = (EditText) v.findViewById(R.id.edtHistDatIni);
        edtHistDatFim = (EditText) v.findViewById(R.id.edtHistDatFim);
        tbLayHistFg = (TableLayout) v.findViewById(R.id.tbLayHistFg);
        tbLayHistFg.setVisibility(View.GONE);

        btHistShoHid = (ImageButton) v.findViewById(R.id.btHistShoHid);
        spiHistFgRota = (Spinner)v.findViewById(R.id.spiHistFgRota);
        spEmpresas = (Spinner)v.findViewById(R.id.spEmpresas);

/****************************************preenche empresas*****************************************/
        try {
            ArrayList<Empresa> alEmp = new EmpresaRN(getActivity()).list(new Empresa());
            String[] arSpempr = new String[alEmp.size()];
            arSpempr[0] = "TODAS";
            for(int i = 1; i < alEmp.size(); i++){
                arSpempr[i] = alEmp.get(i).getId()+"-"+alEmp.get(i).getFantasia();
            }
            ArrayAdapter<String> arAdpspEmp = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, arSpempr);
            spEmpresas.setAdapter(arAdpspEmp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
/****************************************preenche empresas*****************************************/

/***********************************preenche rotas*************************************************/
        try {
            rotasHistFg = new RotaRN(getActivity()).getRouteForSalesMan(CurrentInfo.codVendedor);
            int indexRota = 0;
            if(rotasHistFg.size() > 0) {
                String[] arRota = new String[rotasHistFg.size()];
                for(int i = 0; i < rotasHistFg.size(); i++){
                    arRota[i] = rotasHistFg.get(i).getDescricao();
                    if(rotasHistFg.get(i).getCodigo() == CurrentInfo.rota){
                        indexRota = i;
                    }
                }
                ArrayAdapter<String> arAdp = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, arRota);
                spiHistFgRota.setAdapter(arAdp);

                spiHistFgRota.setSelection(indexRota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        spiHistFgRota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRota = rotasHistFg.get(position).getCodigo();
                //pedidoCalc.setRota(rotasHistFg.get(position).getHora());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /***************************preenche rotas*************************************************/

        Calendar cNow = Calendar.getInstance();
        edtHistDatIni.setText(new Data(cNow.getTimeInMillis()).getStringData());
        edtHistDatFim.setText(new Data(cNow.getTimeInMillis()).getStringData());

        btHistShoHid.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!shoHidFilter){
                    tbLayHistFg.setVisibility(View.VISIBLE);
                    shoHidFilter = true;
                }else{
                    tbLayHistFg.setVisibility(View.GONE);
                    shoHidFilter = false;
                }
            }
        });

        btHistPesqFg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*Pedido pFilter = new Pedido();
                Calendar dIni = new Data(edtHistDatIni.getText().toString()).getCalendar();
                //dIni.set(Calendar.HOUR_OF_DAY, 1);
                dIni.set(Calendar.HOUR_OF_DAY, 0);
                dIni.set(Calendar.SECOND, 0);
                dIni.set(Calendar.MINUTE, 0);
                dIni.set(Calendar.MILLISECOND, 0);

                Calendar dFim = new Data(edtHistDatFim.getText().toString()).getCalendar();
                dFim.set(Calendar.HOUR_OF_DAY, 23);
                dFim.set(Calendar.SECOND, 59);
                dFim.set(Calendar.MINUTE, 59);
                dFim.set(Calendar.MILLISECOND, 0);

                pFilter.setDataInicio(dIni.getTimeInMillis());
                pFilter.setDataFim(dFim.getTimeInMillis());
                pFilter.setRota(currentRota);
                pFilter.setCodCli(bundle.getDouble("codCli", 0));//todoos os clientes
                pFilter.setStatus("Transmitido");
                */

                Log.i("RH", currentRota+"");

                try {
                    //updateListView(pFilter);
                    updateListView(null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        lvHistoriFg = (ListView) v.findViewById(R.id.lvHistoriFg);

        lvHistoriFg.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Pedido p = (Pedido)parent.getAdapter().getItem(position);
                CurrentInfo.idPedido = p.getId();

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vDg = inflater.inflate(R.layout.hist_dg_opt, null);

                AlertDialog.Builder alb = new AlertDialog.Builder(getActivity());
                alb.setView(vDg);
                final AlertDialog alert = alb.show();

                alb.setPositiveButton("VOLTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Button btHistDgOptDetalhe = (Button)vDg.findViewById(R.id.btHistDgOptDetalhe);
                Button btHistDgOptReabre = (Button)vDg.findViewById(R.id.btHistDgOptReabre);

                btHistDgOptDetalhe.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        ListaPedidoFragment listaPedidoFragment = new ListaPedidoFragment();
                        Bundle b = new Bundle();
                        b.putString("from", "HistoriFgAdp");
                        listaPedidoFragment.setArguments(b);

                        CurrentInfo.idPedido = p.getId();
                        CurrentInfo.codCli = p.getCodCli();
                        Log.i("H_FG", CurrentInfo.codCli+"--"+CurrentInfo.idPedido);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.pedidoContainer, listaPedidoFragment, "listPedFrag");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        alert.dismiss();
                    }
                });

                btHistDgOptReabre.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        Utils.showMsg(getActivity(), "", "click em detalhes e depois em NOVO ABERTO ou NOVO FECHADO", R.drawable.dialog_error);
                        /*try {
                            new PedidoRN(getActivity()).duplicar();
                            Toast.makeText(getActivity(), "NOVO PEDIDO "+CurrentInfo.idPedido+" ABERTO", Toast.LENGTH_SHORT).show();
                            alert.dismiss();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
            }
        });

        Pedido pFilter = new Pedido();
        if(bundle != null){
            pFilter.setDataInicio(bundle.getLong("datIni"));
            pFilter.setDataFim(bundle.getLong("datFi"));
            pFilter.setRota(bundle.getLong("codRota"));
            pFilter.setCodCli(bundle.getDouble("codCli"));
            pFilter.setStatus(bundle.getString("status"));
        }else{
            pFilter = new Pedido();
            pFilter.setDataInicio(0);
            pFilter.setDataFim(90000000000000l);
            pFilter.setStatus("Transmitido");
            pFilter.setRota(bundle.getLong("codRota"));//teste 8/9/2017
        }

        try {
            updateListView(pFilter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }

    public ArrayList<Pedido> updateListView(Pedido pedFilter) throws SQLException {
        Pedido pFilter = new Pedido();
        if(pedFilter == null){
            Calendar dIni = new Data(edtHistDatIni.getText().toString()).getCalendar();
            //dIni.set(Calendar.HOUR_OF_DAY, 1);
            dIni.set(Calendar.HOUR_OF_DAY, 0);
            dIni.set(Calendar.SECOND, 0);
            dIni.set(Calendar.MINUTE, 0);
            dIni.set(Calendar.MILLISECOND, 0);

            Calendar dFim = new Data(edtHistDatFim.getText().toString()).getCalendar();
            dFim.set(Calendar.HOUR_OF_DAY, 23);
            dFim.set(Calendar.SECOND, 59);
            dFim.set(Calendar.MINUTE, 59);
            dFim.set(Calendar.MILLISECOND, 0);

            pFilter.setDataInicio(dIni.getTimeInMillis());
            pFilter.setDataFim(dFim.getTimeInMillis());
            pFilter.setRota(currentRota);
            pFilter.setCodCli(bundle.getDouble("codCli", 0));//todoos os clientes
            pFilter.setStatus("Transmitido");

        }else{
            pFilter = pedFilter;
        }

        ArrayList<Pedido> list = new PedidoRN(getActivity()).filtrar(pFilter);
        lvHistoriFg.setAdapter(new HistoriFgAdp(getActivity(), list, getActivity().getSupportFragmentManager()));

        return list;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public void onStart(){
        super.onStart();
        //temporariamente ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
        ControlFragment.setActiveHistoriFg(true);
    }
    public void onResume(){
        super.onResume();
        //temporariamente ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
        ControlFragment.setActiveHistoriFg(true);
    }
    public void onPause(){
        super.onPause();
        ControlFragment.setActiveHistoriFg(false);
    }
    public void onStop(){
        super.onStop();
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);
        ControlFragment.setActiveHistoriFg(false);
    }
}