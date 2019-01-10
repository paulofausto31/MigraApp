package migra.br.smart.prodsVendsFg;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.utils.data.Data;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProdsVendsFg.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProdsVendsFg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProdsVendsFg extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText edtPdVendFgDatIni, edtPdVendFgDatFim;
    private Button btPdvendFgPesquisa;
    private ImageButton btPdvendFgShoHid;

    private TableLayout tbLayPdVendsFg;

    private ListView lvProdsVendsFg;

    private boolean hidSho = false;//mostrar

    public ProdsVendsFg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProdsVendsFg.
     */
    // TODO: Rename and change types and number of parameters
    public static ProdsVendsFg newInstance(String param1, String param2) {
        ProdsVendsFg fragment = new ProdsVendsFg();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.prods_vends_fg, container, false);

        edtPdVendFgDatIni = (EditText) v.findViewById(R.id.edtPdVendFgDatIni);
        edtPdVendFgDatFim = (EditText) v.findViewById(R.id.edtPdVendFgDatFim);
        tbLayPdVendsFg = (TableLayout) v.findViewById(R.id.tbLayPdVendsFg);

        btPdvendFgShoHid = (ImageButton) v.findViewById(R.id.btPdvendFgShoHid);
        tbLayPdVendsFg.setVisibility(View.GONE);
        btPdvendFgShoHid.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                if(hidSho){
                    tbLayPdVendsFg.setVisibility(View.GONE);
                    hidSho = false;
                }else{
                    tbLayPdVendsFg.setVisibility(View.VISIBLE);
                    hidSho = true;
                }
            }
        });

        btPdvendFgPesquisa = (Button) v.findViewById(R.id.btPdvendFgPesquisa);
        btPdvendFgPesquisa.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                try {
                    fillList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Calendar c = Calendar.getInstance();
        edtPdVendFgDatIni.setText(new Data(c.getTimeInMillis()).getStringData());
        edtPdVendFgDatFim.setText(new Data(c.getTimeInMillis()).getStringData());

        lvProdsVendsFg = (ListView) v.findViewById(R.id.lvProdsVendsFg);

        try {
            fillList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }

    public void fillList() throws SQLException {
        ListaPedido pedidoCalc = new ListaPedido();
        Calendar dIni = new Data(edtPdVendFgDatIni.getText().toString()).getCalendar();
        //dIni.set(Calendar.HOUR_OF_DAY, 1);
        dIni.set(Calendar.HOUR_OF_DAY, 0);
        dIni.set(Calendar.MINUTE, 0);
        dIni.set(Calendar.SECOND, 0);
        dIni.set(Calendar.MILLISECOND, 0);
        //pedidoCalc.setDataInicio(new Data(edtDatIni.getText().toString()).getOnlyDataInMillis());
        pedidoCalc.getPedido().setDataInicio(dIni.getTimeInMillis());

        //pedidoCalc.setDataFim(new Data(edtDatFim.getText().toString()).getOnlyDataInMillis());//new Data(edtDatFim.getText().toString()).getDataInMillis());
        Calendar dFim = new Data(edtPdVendFgDatFim.getText().toString()).getCalendar();
        dFim.set(Calendar.HOUR_OF_DAY, 23);
        dFim.set(Calendar.MINUTE, 59);
        dFim.set(Calendar.SECOND, 59);
        pedidoCalc.getPedido().setDataFim(dFim.getTimeInMillis());

        ArrayList<ListaPedido> arrLPd = new ListaPedidoRN(getActivity()).listProdsVendidos(pedidoCalc);
        //ArrayList<ListaPedido> arrLPd = new ListaPedidoRN(getActivity()).getProdsVendidos(pedidoCalc);

        //if(arrLPd != null && arrLPd.size() > 0){
            lvProdsVendsFg.setAdapter(new ProdsVendsAdp(getActivity(), arrLPd));
       // }
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
}
