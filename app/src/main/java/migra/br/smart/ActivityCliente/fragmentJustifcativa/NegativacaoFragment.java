package migra.br.smart.ActivityCliente.fragmentJustifcativa;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.empresas.Empresa;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitRN;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatus;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.justificativa.Justificativa;
import migra.br.smart.manipulaBanco.entidades.justificativa.JustificativaRN;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;
import migra.br.smart.utils.permissions.PermissionUtils;

public class NegativacaoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button btSaveJustFica, btBuscaJustFica;
    private ImageButton btHidShowJustFica;
    private EditText tvJustfFgData, tvNegativFgDataFim, tvNegatFgCodCli, tvNegatFgNomeCli;

    private CheckBox chTodRotNegaFg;

    private ListView lvJustfica;
    private TableLayout tbLayNegFg;

    private Spinner spJustifFg, spEmpresas;

    private Bundle bundle;

    private ArrayList<Justificativa> listJustfi;

    private double latitude;
    private double longitude;
    GoogleApiClient mGoogleApiCliente;
    LocationRequest mLocationRequest;

    private boolean showHiddFilter = true;

    private ArrayList<Negativacao> listNegativ;

    public NegativacaoFragment() {
        // Required empty public constructor
    }

    public static NegativacaoFragment newInstance(String param1, String param2) {
        NegativacaoFragment fragment = new NegativacaoFragment();
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
            bundle = getArguments();
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
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
                    .addConnectionCallbacks(NegativacaoFragment.this)
                    .addOnConnectionFailedListener(NegativacaoFragment.this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.negativar_fg, container, false);

        ControlFragment.setActiveJustifica(true);//tela ativa

        tbLayNegFg = (TableLayout) v.findViewById(R.id.tbLayNegFg);
        btHidShowJustFica = (ImageButton) v.findViewById(R.id.btHidShowJustFica);
        btHidShowJustFica.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                if(showHiddFilter){
                    tbLayNegFg.setVisibility(View.GONE);
                    showHiddFilter = false;
                }else{
                    tbLayNegFg.setVisibility(View.VISIBLE);
                    showHiddFilter = true;
                }

            }
        });

        chTodRotNegaFg = (CheckBox) v.findViewById(R.id.chTodRotNegaFg);

        tvJustfFgData = (EditText) v.findViewById(R.id.tvNegativFgData);
        tvNegativFgDataFim = (EditText) v.findViewById(R.id.tvNegativFgDataFim);

        tvNegatFgCodCli = (EditText) v.findViewById(R.id.tvNegatFgCodCli);
        tvNegatFgNomeCli = (EditText) v.findViewById(R.id.tvNegatFgNomeCli);

        tvNegatFgNomeCli.setText(bundle.getString("fantasiCli"));
        tvNegatFgCodCli.setText(String.valueOf(bundle.getDouble("cliCod")));

        spJustifFg = (Spinner) v.findViewById(R.id.spNegativFg);

        lvJustfica = (ListView) v.findViewById(R.id.lvJustfica);

        spEmpresas = (Spinner) v.findViewById(R.id.spEmpresas);

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

        spEmpresas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setListNegativ(fillList(null));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvJustfFgData.setText(String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance()));
        tvNegativFgDataFim.setText(String.format("%1$td/%1$tm/%1$tY", Calendar.getInstance()));

        fillJustifica();

        btBuscaJustFica = (Button) v.findViewById(R.id.btBuscaJustFica);
        btBuscaJustFica.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                fillList(null);
            }
        });

        btSaveJustFica = (Button)v.findViewById(R.id.btSaveJustFica);
        if(!bundle.getBoolean("activeSave")){
            btSaveJustFica.setEnabled(false);
        }
        btSaveJustFica.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(listJustfi.size() > 0) {
                    /***********************criar metodo para isso***experimental******************/

                    //int indexSp = Integer.parseInt(String.valueOf(spJustifFg.getSelectedItemId()));
                    Justificativa justFilter = new Justificativa();

                    try{//experimental
                        justFilter.setDescricao(spJustifFg.getSelectedItem().toString());
                        ArrayList<Justificativa> arrLjust = new JustificativaRN(getActivity()).filterDescri(justFilter);

                    Negativacao neg = new Negativacao();

                    neg.setCodRota(bundle.getLong("rotCod"));
                    neg.setSeqVisitId(bundle.getInt("seqVisitId"));

                    Calendar dIni = Calendar.getInstance();
                    dIni.set(Calendar.HOUR_OF_DAY, 1);
                    dIni.set(Calendar.MINUTE, 0);
                    dIni.set(Calendar.SECOND, 0);
                    dIni.set(Calendar.MILLISECOND, 0);

                    Calendar dFim = Calendar.getInstance();
                    dFim.set(Calendar.HOUR_OF_DAY, 23);
                    dFim.set(Calendar.MINUTE, 59);
                    dFim.set(Calendar.SECOND, 59);

                    neg.setDataInicio(dIni.getTimeInMillis());
                    neg.setDataFim(dFim.getTimeInMillis());

                    Utils.setExtraQuery(" and data >= "+neg.getDataInicio()+" and data <= "+neg.getDataFim()+" ");

                    ArrayList<Negativacao> arrLNeg = new ArrayList<Negativacao>();
                    //try {
                            arrLNeg = new NegativacaoRN(getActivity()).getWithClients(neg);
                       /* }catch(SQLException ex){
                            ex.printStackTrace();
                        }*/
                    /***********************criar metodo para isso***experimental******************/

                    Log.i("BUSCnEG", arrLNeg.size()+"");

                    if(arrLNeg.size() == 1){
                        //arrLNeg.get(0).setCodJustf(listJustfi.get(indexSp).getCodigo());
                        arrLNeg.get(0).setCodJustf(String.valueOf(arrLjust.get(0).getCodigo()));
                        new NegativacaoRN(getActivity()).update(arrLNeg.get(0));
                    }else {
                        /*************************CRIA NEGATIVAÇÃO*********************************/
                    /*experimental Negativacao*/
                        neg = new Negativacao();

                        neg.setIdEmpresa(Integer.parseInt(spEmpresas.getSelectedItem().toString().split("-")[0]));

                        neg.setCodCli(bundle.getDouble("cliCod"));
                        neg.setCodRota(bundle.getLong("rotCod"));
                        neg.setSeqVisitId(bundle.getInt("seqVisitId"));
                    /*experimental Calendar*/
                        //dIni = Calendar.getInstance();
                        //dIni.set(Calendar.HOUR_OF_DAY, 1);
                        neg.setDataInicio(dIni.getTimeInMillis());//new Data(Calendar.getInstance().getTimeInMillis()).getOnlyDataInMillis());
                        neg.setHora(String.format("%tT", Calendar.getInstance()));
                        //int indexSp = Integer.parseInt(String.valueOf(spJustifFg.getSelectedItemId()));
                        //neg.setCodJustf(listJustfi.get(indexSp).getCodigo());
                        neg.setCodJustf(String.valueOf(arrLjust.get(0).getCodigo()));

                        double latitude = 0;
                        double longitude = 0;

                        //Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente);
                        //latitude = l.getLatitude();
                        //longitude = l.getLongitude();
                        // Toast.makeText(getActivity(), l.getLatitude() + "-----" + l.getLongitude(), Toast.LENGTH_LONG).show();

                        neg.setLatitude(latitude);//l.getLatitude());
                        neg.setLongitude(longitude);//l.getLongitude());

                        //experimental try {
                        new NegativacaoRN(getActivity()).salvar(neg);
                        int idNegativa = new NegativacaoRN(getActivity()).getMaxId();//id desta negativacao

                        SeqVisit seq = new SeqVisit();
                        seq.setId(neg.getSeqVisitId());
                        ArrayList<SeqVisit> arrLSeq = new SeqVisitRN(getActivity()).pesquisar(seq);
                        if (arrLSeq != null && arrLSeq.size() > 0) {
                            arrLSeq.get(0).setStatus("N");
                            new SeqVisitRN(getActivity()).update(arrLSeq.get(0));
                        }
                        //versao 4
                        SeqVisitStatus seqVisitStatus = new SeqVisitStatus();
                        seqVisitStatus.setSeq_id(String.valueOf(seq.getId()));
                        seqVisitStatus.setCodPed("0");
                        seqVisitStatus.setStatus("N");
                        seqVisitStatus.setIdNegativa(idNegativa);

                        ArrayList<SeqVisitStatus> arLSqVStat = new SeqVisitStatusRN(getActivity()).pesquisar(seqVisitStatus);
                        if(arLSqVStat != null && arLSqVStat.size() > 0){
                            new SeqVisitStatusRN(getActivity()).update(seqVisitStatus);
                        }else{
                            new SeqVisitStatusRN(getActivity()).salvar(seqVisitStatus);
                        }
                        //versao 4

                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    setListNegativ(fillList(null));
                }
            }
        });

        setListNegativ(fillList(null));
        return v;
    }

    public ArrayList<Negativacao> getListNegativ() {
        return listNegativ;
    }

    public void setListNegativ(ArrayList<Negativacao> listNegativ) {
        this.listNegativ = listNegativ;
    }

    private void fillJustifica(){
        try {
            listJustfi = new JustificativaRN(getActivity()).pesquisar(new Justificativa());
            if(listJustfi.size() > 0) {
                String[] sAdapt = new String[listJustfi.size()];
                for (int i = 0; i < listJustfi.size(); i++) {
                    sAdapt[i] = listJustfi.get(i).getDescricao();
                }
                ArrayAdapter<String> spAdp = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, sAdapt);
                spJustifFg.setAdapter(spAdp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Negativacao> fillList(ArrayList<Negativacao> list){
        Negativacao neg = new Negativacao();

        if(chTodRotNegaFg.isSelected()){
            neg.setCodRota(0);
        }else{
            neg.setCodRota(bundle.getLong("rotCod"));
        }

        neg.setIdEmpresa(Integer.parseInt(spEmpresas.getSelectedItem().toString().split("-")[0]));

        Log.i("nEmpre", neg.getIdEmpresa()+"");

        //neg.setSeqVisitId(bundle.getInt("seqVisitId"));

        Calendar dIni = new Data(tvJustfFgData.getText().toString()).getCalendar();
        dIni.set(Calendar.HOUR_OF_DAY, 1);
        dIni.set(Calendar.MINUTE, 0);
        dIni.set(Calendar.SECOND, 0);
        dIni.set(Calendar.MILLISECOND, 0);

        Calendar dFim = new Data(tvNegativFgDataFim.getText().toString()).getCalendar();
        dFim.set(Calendar.HOUR_OF_DAY, 23);
        dFim.set(Calendar.MINUTE, 59);
        dFim.set(Calendar.SECOND, 59);
        neg.setDataInicio(dIni.getTimeInMillis());
        neg.setDataFim(dFim.getTimeInMillis());

        neg.getCliente().setFantasia(tvNegatFgNomeCli.getText().toString());
        neg.getCliente().setRzSocial(tvNegatFgNomeCli.getText().toString());
        if(tvNegatFgCodCli.getText().toString().equals("")){
            tvNegatFgCodCli.setText("0");
        }
        neg.setCodCli(Double.parseDouble(tvNegatFgCodCli.getText().toString()));

        Utils.setExtraQuery(" and data >= "+neg.getDataInicio()+" and data <= "+neg.getDataFim()+" ");

        ArrayList<Negativacao> arrLNeg = new ArrayList<Negativacao>();
        try {
            if(list == null){
                arrLNeg = new NegativacaoRN(getActivity()).getWithClients(neg);
                list = arrLNeg;
            }

            lvJustfica.setAdapter(new JustificaAdp(getActivity(), list));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    public void deleteSelecteds() throws SQLException {
        for (Negativacao n : getListNegativ()) {
            if(n.getStatus().endsWith("/select")) {
                new NegativacaoRN(getActivity()).deletar(n.getId());
                SeqVisit seq = new SeqVisit();
                seq.setId(n.getSeqVisitId());
                ArrayList<SeqVisit> arrLSeqVis = new SeqVisitRN(getActivity()).pesquisar(seq);
                arrLSeqVis.get(0).setStatus("");
                new SeqVisitRN(getActivity()).update(arrLSeqVis.get(0));
            }
        }

        fillList(null);
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

    @Override
    public void onConnected(Bundle bundle) {
        //funciona startLocationUpdate();
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
        //Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente);
        /*funciona latitude = location.getLatitude();
        longitude = location.getLongitude();
        Toast.makeText(getActivity(), latitude+"-----"+longitude, Toast.LENGTH_LONG).show();
        Log.i("LATTUDE, LONGITUDE", latitude+"-----"+longitude);*/
    }

    protected void startLocationUpdate(){
/*funciona
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiCliente, mLocationRequest, this
        );
        */
    }

    protected void stopLocationUpdates(){
        //funciona LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiCliente, this);
//        mGoogleApiCliente.disconnect();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onStop(){
        /*funcionando stopLocationUpdates();
        mGoogleApiCliente.disconnect();*/
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);
        ControlFragment.setActiveJustifica(false);
        super.onStop();
    }

    public void onPause(){
        super.onPause();
        //funciona stopLocationUpdates();
        ControlFragment.setActiveJustifica(false);
    }

    public void onStart(){
        super.onStart();

        //temporariamente ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);

        //funciona mGoogleApiCliente.connect();

        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControlFragment.setActiveJustifica(true);
    }

    public void onResume(){
        super.onResume();

        //temporariamente ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
        ControlFragment.setActiveJustifica(true);

        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}