package migra.br.smart.ActivityCliente.fragNegativacao;

import android.content.Context;
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
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;

public class NegativacaoListCliFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView lvNegativFg;

    public NegativacaoListCliFrag() {
        // Required empty public constructor
    }

    public static NegativacaoListCliFrag newInstance(String param1, String param2) {
        NegativacaoListCliFrag fragment = new NegativacaoListCliFrag();
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
        View v = inflater.inflate(R.layout.negativacao_list_cli_frag, container, false);

        lvNegativFg = (ListView)v.findViewById(R.id.lvNegativFg);

        ArrayList<Negativacao> list = new ArrayList<Negativacao>();
        Negativacao neg = new Negativacao();

        try {
            list = new NegativacaoRN(getActivity()).getWithClients(neg);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lvNegativFg.setAdapter(new NegativListCliFgAdp(getActivity(), list));

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onStart(){
        super.onStart();
        //temporariamente ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
    }
    public void onResume(){
        super.onResume();
        //temporariamente ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.VISIBLE);
    }
    public void onStop(){
        super.onStop();
        //ActivityContainerFragments.fabAtvContainPedImport.setVisibility(View.GONE);
    }
}
