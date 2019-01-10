package migra.br.smart.ActivityContRec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.R;
import migra.br.smart.atvContainerFg.ActivityContainerFragments;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContReceb;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContRecebRN;

public class ContRecTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView lvContRecTabFrag;

    public ContRecTabFragment(){

    }

    /*public ContRecTabFragment(double codCli) {

    }*/

    public static ContRecTabFragment newInstance(String param1, String param2) {
        ContRecTabFragment fragment = new ContRecTabFragment();
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
        View v = inflater.inflate(R.layout.cont_rec_tab_fragment, container, false);

        lvContRecTabFrag = (ListView)v.findViewById(R.id.lvContRecTabFrag);

        ArrayList< ContReceb > lista = null;
        try {
            lista = new ContRecebRN(getActivity()).searchForCli(CurrentInfo.codCli);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        Intent it = getActivity().getIntent();


        try {
            if(it != null){
                if(it.getStringExtra("codCli").equals("yes")){//filtrar por codigo do cliente
                    lista = new ContRecebRN(getActivity()).searchForCli(CurrentInfo.codCli);
                }
            }else{
                lista = new ContRecebRN(getActivity()).filtrar(new ContReceb());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        lvContRecTabFrag.setAdapter(new ContRecAdapter(getActivity(), lista));

        return v;
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
        //temporariario ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);
    }
    public void onResume(){
        super.onResume();
        //temporariario ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);
    }
    public void onStop(){
        super.onStop();
        //ActivityContainerFragments.fabPedidos.setVisibility(View.GONE);
    }
}
