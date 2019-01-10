package migra.br.smart.rotaFg;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.ActivityCliente.ClienteFragment;
import migra.br.smart.R;
import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;
import migra.br.smart.manipulaBanco.entidades.rota.RotaRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;

public class RotaFg extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView lvRotaFg;

    public RotaFg() {
        // Required empty public constructor
    }

    public static RotaFg newInstance(String param1, String param2) {
        RotaFg fragment = new RotaFg();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rota_fg, container, false);
        lvRotaFg = (ListView) v.findViewById(R.id.lvRotaFg);
        lvRotaFg.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rota rota = (Rota)lvRotaFg.getAdapter().getItem(position);
               // View v = (View)parent.getAdapter().getItem(position);
                CurrentInfo.rota = rota.getCodigo();
               // CurrentInfo.codCli = rota.getHora();
                Log.i("ROTA_FG", rota.getCodigo()+"");
                FragmentTransaction fMang = getActivity().getSupportFragmentManager().beginTransaction();
                ClienteFragment cliFg = new ClienteFragment();
                Bundle b = new Bundle();
                b.putLong("codRota", rota.getCodigo());
                b.putString("from", "rotaFg");
                cliFg.setArguments(b);

                fMang.replace(R.id.pedidoContainer, cliFg, "cliFrag");
                fMang.addToBackStack(null);
                fMang.commit();
            }
        });
        try {
            updateLV();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    private void updateLV()throws SQLException {

        ArrayList<Rota> list = new RotaRN(getActivity()).getRouteForSalesMan(CurrentInfo.codVendedor);

        ArrayList<Configuracao> conf = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
        if (conf.get(0).getVendePorDiaSemana().toUpperCase().equals("S")) {
            Calendar c = Calendar.getInstance();

            ArrayList<Integer> remove = new ArrayList<Integer>();
            Log.i("hjADP", c.get(Calendar.DAY_OF_WEEK) + "--" + list.size());
            int index = 0;
            if (list.size() > 0) {
                for (Rota r : list) {
                    Log.i("BMF", r.getDiaVender());
                    if (!r.getDiaVender().equals("") && r.getDiaVender().toCharArray()[c.get(Calendar.DAY_OF_WEEK) - 1] != '1') {
                        remove.add(index);
                        --index;
                    }
                    ++index;
                }

                for (Integer i : remove) {
                    list.remove(i.intValue());
                }

                Log.i("FTFG", list.size() + "");

            }
        }

        if (list.size() > 0) {
            lvRotaFg.setAdapter(new RotaAdp(getActivity(), list));
        }
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
        ControlFragment.setActiveRotaFg(true);
        //TEMPORARIRIO ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
    }
    public void onResume(){
        super.onResume();
        ControlFragment.setActiveRotaFg(true);
        //TEMPORARIRIO ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
    }
    public void onStop(){
        super.onStop();
        ControlFragment.setActiveRotaFg(false);
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);
    }
}