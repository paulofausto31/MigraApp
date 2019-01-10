package migra.br.smart.ActivityCliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import migra.br.smart.R;
import migra.br.smart.atvContainerFg.ActivityContainerFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClienteTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClienteTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClienteTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String BundleNome;
    private String mParam2;
    private Bundle bundle;

    private OnFragmentInteractionListener mListener;

    public ClienteTabFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ClienteTabFragment newInstance(String param1, String param2) {
        ClienteTabFragment fragment = new ClienteTabFragment();
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
            BundleNome = getArguments().getString("nome");//talvez inutilize isso
            bundle = getArguments();
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cli_tab_fg, container, false);
        //ViewPager
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.cliTabVPager);
        //viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new ClienteTabsAdapter(bundle, getContext(), getChildFragmentManager()));
        //Tabs
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.cliTabLayoutFg);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setSelectedTabIndicatorHeight(1);
        //int cor = ContextCompat.getColor(getContext(), R.color.white);

        return view;
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
        //temporariamente ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);
    }
    public void onResume(){
        super.onResume();
        //temporariamente ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);

    }
    public void onStop(){
        super.onStop();
        //ActivityContainerFragments.fabPedidos.setVisibility(View.GONE);
    }
}
