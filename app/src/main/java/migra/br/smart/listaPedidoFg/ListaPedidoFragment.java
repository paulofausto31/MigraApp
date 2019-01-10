package migra.br.smart.listaPedidoFg;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteRN;
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaPedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaPedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPedidoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lvProdutos;
    private ArrayList<ListaPedido> list;
    private TextView tvPedFgLstTotal, tvPedFgLstLimit;
    private Button btNovoAbre, btNovoFechado, btRetransmitir;

    private View vFragent;

    private Bundle bundle;

    private double desconto;
    private double descAcrePercent;
    private double descAcreMone;
    private long idProd;
    private boolean radioDesc, radioAcre;
    private static long qtd = 0;
    private double total = 0;
    private  double valor;

    private long qtdCaixa, qtdUnidade;

    private double qtdArmazenaProd = 0;

    private ArrayList<Configuracao> conf;
    ArrayList<ListaPedido> listaPedidos;//para spinDgValor, principalmente

    private OnFragmentInteractionListener mListener;

    public ListaPedidoFragment() {
        // Required empty public constructor
    }

    public static ListaPedidoFragment newInstance(String param1, String param2) {
        ListaPedidoFragment fragment = new ListaPedidoFragment();
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
        vFragent = inflater.inflate(R.layout.ped_fg_list, container, false);

        tvPedFgLstTotal = (TextView)vFragent.findViewById(R.id.tvPedFgLstTotal);
        btNovoAbre = (Button)vFragent.findViewById(R.id.btNovoAberto);
        btNovoFechado = (Button)vFragent.findViewById(R.id.btNovoFechado);
        btRetransmitir = (Button)vFragent.findViewById(R.id.btRetransmitir);

        tvPedFgLstLimit = (TextView)vFragent.findViewById(R.id.tvPedFgLstLimit);

        lvProdutos = (ListView) vFragent.findViewById(R.id.frag_lv_list_pedido);

        if(bundle == null || bundle.get("from") == null || !bundle.get("from").equals("HistoriFgAdp")){
            btRetransmitir.setVisibility(View.GONE);
            btNovoFechado.setVisibility(View.GONE);
            btNovoAbre.setVisibility(View.GONE);
        }
        btNovoAbre.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                try {
                    new PedidoRN(getActivity()).duplicar();
                    Toast.makeText(getActivity(), "NOVO PEDIDO "+ CurrentInfo.idPedido+" ABERTO", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btNovoFechado.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                try {
                    new PedidoRN(getActivity()).duplicar("Fechado");
                    Toast.makeText(getActivity(), "NOVO PEDIDO "+ CurrentInfo.idPedido+" FECHADO", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



        try {
            setConf(new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao()));

            updateLista("");//todos
        } catch (SQLException e) {
            Log.e("erro sql", e.getMessage());
        }

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //final TextView tvProdAdpProdQtd = (TextView)view.findViewById(R.id.tvProdAdpProdQtd);





                final ListaPedido listaPedido = (ListaPedido)parent.getAdapter().getItem(position);

                final TextView tvQtdCaixa = (TextView)view.findViewById(R.id.tvQtdCaixa);
                final TextView tvQtdUnVend = (TextView) view.findViewById(R.id.tvQtdUnVend);
                final TextView tvUnArmz = (TextView) view.findViewById(R.id.tvUnArmz);
                final TextView tvUnVend = (TextView) view.findViewById(R.id.tvUnVend);

                final Produto prod = listaPedido.getItemLista().getProduto();
                if(prod.getQtdArmazenamento() == 0){
                    prod.setQtdArmazenamento(1);
                }
                setQtdArmazenaProd(prod.getQtdArmazenamento());







                //String lPedItemQtd = "0/0";
                String[] lPedItemQtd = new String[]{"0", "0"};

                try {
                    lPedItemQtd = new ListaPedidoRN(getActivity()).getQtdItemListaPedido(prod.getCodigo(), CurrentInfo.codCli, CurrentInfo.idPedido);
                    listaPedidos = new ListaPedidoRN(getActivity()).getForNomeProd(prod.getNome());
                    //setQtdUnidade(Long.parseLong(lPedItemQtd.split("/")[1]));
                    setQtdUnidade(Long.parseLong(lPedItemQtd[1]));
                    //setQtdCaixa(Long.parseLong(lPedItemQtd.split("/")[0]));
                    setQtdCaixa(Long.parseLong(lPedItemQtd[0]));
                    //setQtd(lPedItemQtd);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //AlertDialog.Builder alBuild = new AlertDialog.Builder(getActivity());
                /*final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                Log.i("FIPRO", getConf().get(0).getFiltraEstoque()+"");
                if(getConf() != null && conf.size() > 0){
                    if(getConf().get(0).getFiltraEstoque() == 2 && prod.getSaldo() <= 0) {
                        alert.setTitle("ADVERTÊNCIA");
                        alert.setMessage("PRODUTO COM ESTOQUE ZERADO OU NEGATIVO");
                        alert.setIcon(R.drawable.dialog_error);//Utils.showMsg(getActivity(), "ADVERTÊNCIA", "PRODUTO COM ESTOQUE ZERADO OU NEGATIVO", R.drawable.dialog_error);
                    }

                    alert.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dg, int wich){
                            Intent it = new Intent("ATV_INSERE_ALTERA_ITEM_PEDIDO");
                            it.putExtra("qtdItemIncluido", tvProdAdpProdQtd.getText().toString());
                            getActivity().startActivity(it);
                        }
                    });

                    alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dg, int wich){
                        }
                    });

                    alert.show();
                }
                */

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vAlert = inflater.inflate(R.layout.dg_prod_list_calc, null);

                final RadioGroup rGroup = (RadioGroup) vAlert.findViewById(R.id.rGroup);
                rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.radDesc){
                            setRadioDesc(true);
                            setRadioAcre(false);
                        }else if(checkedId == R.id.radAcre){
                            setRadioAcre(true);
                            setRadioDesc(false);
                        }
                    }
                });

                //final EditText edtQtdUnArmz = (EditText) vAlert.findViewById(R.id.edtQtdUnVend);
                final TextView selectUnVend = (TextView) vAlert.findViewById(R.id.selectUnVend);
                final EditText edtQtdUnVend = (EditText) vAlert.findViewById(R.id.edtQtdUnVend);
                final EditText edtQtdUnArmz = (EditText)vAlert.findViewById(R.id.edtQtdUnArmz);
                //edtQtdUnArmz.setText(tvProdAdpProdQtd.getText().toString().split("/")[1]);
                //edtQtdUnArmz.setText(tvProdAdpProdQtd.getText().toString().split("/")[0]);
                //edtQtdUnVend.setText(tvProdAdpProdQtd.getText().toString().split("/")[1]);
                edtQtdUnArmz.setText(tvQtdCaixa.getText().toString());
                edtQtdUnVend.setText(tvQtdUnVend.getText().toString());

                final TextView tvDgTotal = (TextView)vAlert.findViewById(R.id.tvDgTotal);

                //final CheckBox chVendUnidade = (CheckBox)vAlert.findViewById(R.id.chVendUnidade);

                final RadioButton radDesc = (RadioButton) vAlert.findViewById(R.id.radDesc);
                final RadioButton radAcre = (RadioButton) vAlert.findViewById(R.id.radAcre);
                final EditText edtDesAcrPercentCalc = (EditText) vAlert.findViewById(R.id.edtDesAcrPercentCalc);
                final EditText edTxDescAcreDin = (EditText) vAlert.findViewById(R.id.edTxDescAcreDin);
                final Button btAlertOkCalc = (Button) vAlert.findViewById(R.id.btAlertOkCalc);
                final Button btAlertCancelCalc = (Button) vAlert.findViewById(R.id.btAlertCancelCalc);
                final Button btProdListSaldProd = (Button) vAlert.findViewById(R.id.btProdListSaldProd);
                final TextView tvDgProdListCalcSaldProd = (TextView) vAlert.findViewById(R.id.tvDgProdListCalcSaldProd);
                final TextView selectUnArmz = (TextView) vAlert.findViewById(R.id.selectUnArmz);

                final ImageButton imgBtDdIncDecAddQtd = (ImageButton) vAlert.findViewById(R.id.imgBtArmzAddQtd);

                final Spinner spiDgValor = (Spinner)vAlert.findViewById(R.id.spiDgFgLAdpVal);


                final ImageButton imgBtUnVendAddQtd = (ImageButton) vAlert.findViewById(R.id.imgBtUnVendAddQtd);
                final ImageButton imgBtUnVendSubQtd = (ImageButton) vAlert.findViewById(R.id.imgBtUnVendSubQtd);

                final ArrayAdapter<String> adapterValor = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, prod.getValores());
                spiDgValor.setAdapter(adapterValor);

                final ItemLista itemLista = new ItemLista();

                selectUnVend.setText(prod.getUnidadeVenda());
                //selectUnArmz.setText(prod.getUnArmazena());
                selectUnArmz.setText(prod.getUnArmazena());

                if(prod.getQtdArmazenamento() > 1){//vende em unidade
                    edtQtdUnVend.setVisibility(View.VISIBLE);
                    imgBtUnVendAddQtd.setVisibility(View.VISIBLE);
                    imgBtUnVendSubQtd.setVisibility(View.VISIBLE);
                    selectUnVend.setVisibility(View.VISIBLE);
                }else{
                    selectUnVend.setVisibility(View.GONE);
                    imgBtUnVendSubQtd.setVisibility(View.GONE);
                    imgBtUnVendAddQtd.setVisibility(View.GONE);
                    edtQtdUnVend.setVisibility(View.GONE);
                }

                imgBtUnVendAddQtd.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(edtQtdUnVend.getText().equals("")){
                            edtQtdUnVend.setText("0");
                        }
                        edtQtdUnVend.setText(String.valueOf(Integer.parseInt(edtQtdUnVend.getText().toString())+1));
                        if(Integer.parseInt(edtQtdUnVend.getText().toString()) == prod.getQtdArmazenamento()){
                            edtQtdUnVend.setText("0");
                            imgBtDdIncDecAddQtd.performClick();
                        }

                        setQtdUnidade(Long.parseLong(edtQtdUnVend.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));

                        setTotal(getValor() * (getQtdUnidade() + (getQtdCaixa() * getQtdArmazenaProd())) + getDescAcreMone());

                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                        itemLista.setCodProd(prod.getCodigo());
                        itemLista.setpVendSelect(spiDgValor.getSelectedItemId());
                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });
                imgBtUnVendSubQtd.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(edtQtdUnVend.getText().equals("")){
                            edtQtdUnVend.setText("0");
                        }
                        if(Integer.parseInt(edtQtdUnVend.getText().toString()) > 0){
                            edtQtdUnVend.setText(String.valueOf(Integer.parseInt(edtQtdUnVend.getText().toString())-1));
                        }

                        setQtdUnidade(Long.parseLong(edtQtdUnVend.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));

                        setTotal(getValor() * (getQtdUnidade()+(getQtdCaixa() * getQtdArmazenaProd())) + getDescAcreMone());


                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                        itemLista.setCodProd(prod.getCodigo());

                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });
                /*
                if(prod.getQtdArmazenamento() > 1){//vende em unidade
                    chVendUnidade.setChecked(true);
                }
                */

                /*
                chVendUnidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                        if(isChecked){
                            if(prod.getQtdArmazenamento() <= 1){//nao vende em unidade
                                chVendUnidade.setChecked(false);
                                Utils.showMsg(getActivity(), "ERRO", "VENDA POR UNIDADE NEGADA", R.drawable.dialog_error);
                            }else{

                                setTotal(getValor() * Long.parseLong(edtQtdUnArmz.getText().toString()) + getDescAcreMone());
                                selectUnArmz.setText(prod.getUnidadeVenda());
                            }

                        }else{

                            if(prod.getQtdArmazenamento() > 1){//vende em unidade
                                setQtdArmazenaProd(prod.getQtdArmazenamento());

                            }else{// não vende em unidade
                                setQtdArmazenaProd(1);

                            }

                            setTotal(getValor() * Long.parseLong(edtQtdUnArmz.getText().toString())*getQtdArmazenaProd() + getDescAcreMone());
                            selectUnArmz.setText(prod.getUnArmazena());
                        }


                        tvDgTotal.setText(String.format("%.2f", getTotal()).replace(",", "."));
                    }
                });
                */
                tvDgProdListCalcSaldProd.setText(String.valueOf(prod.getSaldo()));
                btProdListSaldProd.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){

                    }
                });

                imgBtDdIncDecAddQtd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtQtdUnArmz.getText().equals("")){
                            edtQtdUnArmz.setText("0");
                        }
                        edtQtdUnArmz.setText(String.valueOf(Integer.parseInt(edtQtdUnArmz.getText().toString())+1));

                        //itemLista.setQtd(edtQtdUnArmz.getText().toString());
                        setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                        /*
                        if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1) {//////em caixa
                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                        }else if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() <= 1 ||
                                (chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1)){
                            setQtdArmazenaProd(1);
                        }
                        */
                        //setTotal(getValor() * Long.parseLong(itemLista.getQtd()) * getQtdArmazenaProd() + getDescAcreMone());
                        setTotal(getValor() * (getQtdUnidade() + (getQtdCaixa() * getQtdArmazenaProd())) + getDescAcreMone());

                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                        itemLista.setCodProd(prod.getCodigo());
                        itemLista.setpVendSelect(spiDgValor.getSelectedItemId());
                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));

                    }
                });
                final ImageButton imgBtDgIncDecSubQtd = (ImageButton)vAlert.findViewById(R.id.imgBtArmzSubQtd);
                imgBtDgIncDecSubQtd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtQtdUnArmz.getText().equals("")){
                            edtQtdUnArmz.setText("0");
                        }
                        if(Integer.parseInt(edtQtdUnArmz.getText().toString()) > 0){
                            edtQtdUnArmz.setText(String.valueOf(Integer.parseInt(edtQtdUnArmz.getText().toString())-1));
                        }

                        //itemLista.setQtd(edtQtdUnArmz.getText().toString());
                        setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                        /*
                        if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1) {//////em caixa
                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                        }else if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() <= 1 ||
                                (chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1)){
                            setQtdArmazenaProd(1);
                        }
                        */
                        //setTotal(getValor() * Long.parseLong(itemLista.getQtd()) * getQtdArmazenaProd() + getDescAcreMone());
                        setTotal(getValor() * (getQtdUnidade()+(getQtdCaixa() * getQtdArmazenaProd())) + getDescAcreMone());


                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                        itemLista.setCodProd(prod.getCodigo());

                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });

                /**********************************CONFIGURAÇÕES**********************************************************/
                try {
                    ArrayList<Configuracao> configuracoes = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
                    if(configuracoes.size() > 0){
                        if(configuracoes.get(0).getDescontMax() == 0){
                            edtDesAcrPercentCalc.setEnabled(false);
                            edTxDescAcreDin.setEnabled(false);
                        }else{
                            edtDesAcrPercentCalc.setEnabled(true);
                            edTxDescAcreDin.setEnabled(true);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                /**********************************CONFIGURAÇÕES**********************************************************/

                /*
                String idItem = "0";
                try {
                    idItem = new ListaPedidoRN(getActivity()).getIdItem(prod.getCodigo(), CurrentInfo.codCli, CurrentInfo.idPedido);
                    ItemLista item = new ItemLista();
                    if(Double.parseDouble(idItem) > 0){
                        ItemLista iList = new ItemListaRN(getActivity()).getForId(idItem);

                        //if(!iList.getQtd().split("/")[1].equals("0")) {//VENDE EM UNIDADE
                          //  chVendUnidade.setChecked(true);
                        //}else if(iList.getQtd().split("/")[1].equals("0")) {//NÃO VENDE ME UNIDADE
                          //  chVendUnidade.setChecked(false);
                        //}

                        edTxDescAcreDin.setText(String.valueOf(iList.getDescAcreMone()));
                        edtDesAcrPercentCalc.setText(String.valueOf(iList.getDescAcrePercent()*100));
                        tvDgTotal.setText(String.format("%.2f", iList.getTotal()).replace(",", "."));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                */
                spiDgValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //FUNCIONA spiAdapterValor.setSelection(spiDgValor.getSelectedItemPosition());
                        //spiAdapterValor.setText(spiDgValor.getSelectedItem().toString());
                        Data dAtual = new Data(Calendar.getInstance().getTimeInMillis());
                        if(edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                            showMsgErro("PREENCHA AO MENOS UM CAMPO");
                        }else {
                            if(spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() > 0 && prod.getValPromo() > dAtual.getOnlyDataInMillis()){
                                Utils.showMsg(getActivity(), "VALIDO ATÉ", new Data(prod.getValPromo()).getStringData(), null);
                            }else if((spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() == 0)
                                    ||(spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() < dAtual.getOnlyDataInMillis())){
                                btAlertOkCalc.setEnabled(false);
                                Utils.showMsg(getActivity(), "ERRO", "PROMOÇÃO INVÁLIDA", R.drawable.dialog_error);
                            }
                            setIdProd(prod.getId());
                            //qtd = Long.parseLong(edtQtdUnArmz.getText().toString());
                            setQtdUnidade(Long.parseLong(edtQtdUnVend.getText().toString()));
                            setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));

                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                            /*
                            if(!chVendUnidade.isChecked()){

                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", ""))*getQtdArmazenaProd());
                            }else{

                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                            }
                            */
                            //setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", ""))*getQtdArmazenaProd());
                            setTotal((getQtdUnidade()+(getQtdCaixa() * getQtdArmazenaProd())) * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));

                            double desMone = 0;
                            double taxa = 0;
                            if (!edtDesAcrPercentCalc.getText().toString().equals("") && (!edTxDescAcreDin.getText().toString().equals(""))) {
                                taxa = Double.parseDouble(edtDesAcrPercentCalc.getText().toString())/100;//porcentagem

                                desMone = taxa*getTotal();
                                if (radDesc.isChecked()) {
                                    setDescAcrePercent(taxa * (-1));
                                    setDescAcreMone(desMone * (-1));
                                } else if (radAcre.isChecked()) {
                                    setDescAcrePercent(taxa * (+1));
                                    setDescAcreMone(desMone * (+1));
                                }
                            }else if((Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) == 0.0 || edtDesAcrPercentCalc.getText().toString().equals("")) && !edTxDescAcreDin.getText().toString().equals("")) {
                                desMone = Double.parseDouble(edTxDescAcreDin.getText().toString());

                                taxa = desMone/getTotal();
                                if (radDesc.isChecked()) {
                                    setDescAcrePercent(desMone * (-1));
                                    setDescAcrePercent(taxa * (-1));
                                } else if (radAcre.isChecked()) {
                                    setDescAcrePercent(taxa * (+1));
                                    setDescAcrePercent(desMone * (+1));
                                }
                            }

                            tvDgTotal.setText(String.format("%.2f", getTotal()+getDescAcreMone()).replace(",", "."));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder alBuild = new AlertDialog.Builder(getActivity());
                alBuild.setView(vAlert);
                final AlertDialog alert = alBuild.show();

                /**********************************PRODUTO SEM ESTOQUE OU ZERADO*******************************************************/
                Log.i("FIPRO", conf.get(0).getFiltraEstoque()+"");
                if(conf != null && conf.size() > 0){
                    if(conf.get(0).getFiltraEstoque() == 2 && prod.getSaldo() <= 0) {
                        Utils.showMsg(getActivity(), "ADVERTÊNCIA", "PRODUTO COM ESTOQUE ZERADO OU NEGATIVO", R.drawable.dialog_error);
                    }
                }
                /**********************************PRODUTO SEM ESTOQUE OU ZERADO*******************************************************/

                /**********************************SELECIONA O VALOR DO ITEM SELECIONADO QUE JA ESTÁ NO PEDIDO*************************/
                if(listaPedidos != null && listaPedidos.size() > 0){
                    spiDgValor.setSelection((int)listaPedidos.get(0).getItemLista().getpVendSelect());
                }else{
                    spiDgValor.setSelection(0);
                }
                /**********************************SELECIONA O VALOR DO ITEM SELECIONADO QUE JA ESTÁ NO PEDIDO*************************/

                btAlertOkCalc.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        if((edtQtdUnArmz.getText().toString().equals("") || edtQtdUnVend.getText().toString().equals(""))
                                || (edtQtdUnArmz.getText().toString().equals("0") && edtQtdUnVend.getText().toString().equals("0"))) {
                            showMsgErro("QUANTIDADE INVÁLIDA");
                        }else if (edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                                showMsgErro("PREENCHA AO MENOS UM CAMPO DE DESCONTO");
                            } else {
                                setIdProd(prod.getId());

                                //setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                                setTotal(getQtdUnidade()+(getQtdCaixa()*getQtdArmazenaProd()) * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                                double desMone = 0;
                                double taxa = 0;
                                if (!edtDesAcrPercentCalc.getText().toString().equals("") && (edTxDescAcreDin.getText().toString().equals("") || Double.parseDouble(edTxDescAcreDin.getText().toString()) == 0.0)) {
                                    taxa = Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) / 100;//porcentagem

                                    desMone = taxa * getTotal();

                                    if (radDesc.isChecked()) {
                                        setDescAcrePercent(taxa * (-1));
                                        setDescAcreMone(desMone * (-1));
                                    } else if (radAcre.isChecked()) {
                                        setDescAcrePercent(taxa * (+1));
                                        setDescAcreMone(desMone * (+1));
                                    }
                                } else if ((Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) == 0.0 || edtDesAcrPercentCalc.getText().toString().equals("")) && !edTxDescAcreDin.getText().toString().equals("")) {
                                    desMone = Double.parseDouble(edTxDescAcreDin.getText().toString());

                                    taxa = desMone / getTotal();
                                    if (radDesc.isChecked()) {
                                        setDescAcrePercent(desMone * (-1));
                                        setDescAcrePercent(taxa * (-1));
                                    } else if (radAcre.isChecked()) {
                                        setDescAcrePercent(taxa * (+1));
                                        setDescAcrePercent(desMone * (+1));
                                    }

                                }


                                new Handler().post(
                                        new Thread() {
                                            public void run() {
                                                try {
                                                    synchronized (this) {
                                                        if(Integer.parseInt(edtQtdUnVend.getText().toString()) < prod.getQtdArmazenamento()) {

                                                            final ItemLista itemLista = new ItemLista();

                                                            //tvProdAdpProdQtd.setText(edtQtdUnArmz.getText().toString());

                                                            //itemLista.setQtd(edtQtdUnArmz.getText().toString());
                                                            setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));
                                                            setQtdUnidade(Long.parseLong(edtQtdUnVend.getText().toString()));

                                                        /*
                                                        if(chVendUnidade.isChecked()){

                                                            itemLista.setQtd("0/"+itemLista.getQtd());
                                                        }else{

                                                            itemLista.setQtd(itemLista.getQtd()+"/0");
                                                        }
                                                        */
                                                            //itemLista.setQtd(getQtdCaixa() + "/" + getQtdUnidade());
                                                            itemLista.setQtd(String.valueOf(getQtdCaixa()));
                                                            itemLista.setQtdUn(getQtdUnidade());

                                                            itemLista.setDescAcrePercent(getDescAcrePercent());
                                                            itemLista.setDescAcreMone(getDescAcreMone());

                                                            setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                                                        /*
                                                        if(!chVendUnidade.isChecked()){

                                                            setTotal(getValor() * Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", "")) * getQtdArmazenaProd() + getDescAcreMone());
                                                        }else{

                                                            setTotal(getValor() * Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", "")) + getDescAcreMone());
                                                        }
                                                        */
                                                            //setTotal(getValor() * Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", "")) * getQtdArmazenaProd() + getDescAcreMone());
                                                            //Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", ""));
                                                            setTotal(getValor() * (getQtdUnidade() + (getQtdCaixa() * getQtdArmazenaProd())) + getDescAcreMone());

                                                            itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                                                            itemLista.setCodProd(prod.getCodigo());
                                                            itemLista.setpVendSelect(spiDgValor.getSelectedItemId());
                                                            tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));

                                                            itemLista.setCodProd(prod.getCodigo());
                                                            itemLista.setpVendSelect(spiDgValor.getSelectedItemPosition());

                                                            new ItemListaRN(getActivity()).salvar(itemLista);

                                                            double totalItem = 0;

                                                            ArrayList<ListaPedido> list = new ListaPedidoRN(getActivity()).getForNomeProd("");
                                                            for (ListaPedido lp : list) {
                                                                totalItem += lp.getItemLista().getTotal();
                                                            }
                                                            Pedido p = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);
                                                            p.setTotal(totalItem);
                                                            new PedidoRN(getActivity()).update(p);
                                                            tvQtdCaixa.setText(itemLista.getQtd());

                                                            updateLista("");
                                                        }else{
                                                            Utils.showMsg(getActivity(), "ERRO", tvUnVend.getText()+" NÃO PODE SER MAIOR OU IGUAL À "+
                                                                    prod.getQtdArmazenamento(), R.drawable.dialog_error);
                                                        }
                                                    }

                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                );
                            }
                        /*}else{
                            Utils.showMsg(getActivity(), "ERRO", "QUANTIDADE INVÁLIDA", R.drawable.dialog_error);
                        }*/
                        alert.dismiss();

                        setValor(0);
                    }
                });
                btAlertCancelCalc.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        alert.dismiss();
                    }
                });

                Toast.makeText(getActivity(), prod.getNome(), Toast.LENGTH_SHORT).show();

                /*
                final ListaPedido listaPedido = (ListaPedido)parent.getAdapter().getItem(position);

                final TextView tvFgListPedAdpQtd = (TextView)view.findViewById(R.id.tvFgListPedAdpQtd);

                final Produto prod = listaPedido.getItemLista().getProduto();
                if(prod.getQtdArmazenamento() == 0){
                    prod.setQtdArmazenamento(1);
                }
                setQtdArmazenaProd(prod.getQtdArmazenamento());

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vAlert = inflater.inflate(R.layout.dg_prod_list_calc, null);

                final RadioGroup rGroup = (RadioGroup) vAlert.findViewById(R.id.rGroup);
                rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.radDesc){
                            setRadioDesc(true);
                            setRadioAcre(false);
                        }else if(checkedId == R.id.radAcre){
                            setRadioAcre(true);
                            setRadioDesc(false);
                        }
                    }
                });

                final CheckBox chVendUnidade = (CheckBox) vAlert.findViewById(R.id.chVendUnidade);

                final TextView tvDgQtd = (TextView)vAlert.findViewById(R.id.edtQtdUnArmz);
                tvDgQtd.setText(tvFgListPedAdpQtd.getText());

                final TextView tvDgTotal = (TextView)vAlert.findViewById(R.id.tvDgTotal);

                final RadioButton radDesc = (RadioButton) vAlert.findViewById(R.id.radDesc);
                final RadioButton radAcre = (RadioButton) vAlert.findViewById(R.id.radAcre);
                final EditText edtDesAcrPercentCalc = (EditText) vAlert.findViewById(R.id.edtDesAcrPercentCalc);
                final EditText edTxDescAcreDin = (EditText) vAlert.findViewById(R.id.edTxDescAcreDin);
                final Button btAlertOkCalc = (Button) vAlert.findViewById(R.id.btAlertOkCalc);
                final Button btAlertCancelCalc = (Button) vAlert.findViewById(R.id.btAlertCancelCalc);
                final Button btProdListSaldProd = (Button) vAlert.findViewById(R.id.btProdListSaldProd);
                final TextView tvDgProdListCalcSaldProd = (TextView) vAlert.findViewById(R.id.tvDgProdListCalcSaldProd);
                final TextView selectVendUn = (TextView) vAlert.findViewById(R.id.selectUnArmz);

                final ImageButton imgBtDdIncDecAddQtd = (ImageButton) vAlert.findViewById(R.id.imgBtArmzAddQtd);

                final Spinner spiDgValor = (Spinner)vAlert.findViewById(R.id.spiDgFgLAdpVal);
                final ArrayAdapter<String> adapterValor = new ArrayAdapter<String>(getActivity(), R.layout.spin_ped_adapter_cod_ped, prod.getValores());
                spiDgValor.setAdapter(adapterValor);

                final ItemLista itemLista = new ItemLista();

                selectVendUn.setText(prod.getUnidadeVenda());

                if(prod.getQtdArmazenamento() > 1){//vende em unidade
                    chVendUnidade.setChecked(true);
                }

                chVendUnidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                        if(isChecked){

                            if(prod.getQtdArmazenamento() <= 1){//nao vende em unidade
                                chVendUnidade.setChecked(false);

                                Utils.showMsg(getActivity(), "ERRO", "VENDA POR UNIDADE NEGADA", R.drawable.dialog_error);
                            }else{
                                setTotal(getValor() * Long.parseLong(tvDgQtd.getText().toString()) + getDescAcreMone());
                                selectVendUn.setText(prod.getUnidadeVenda());
                            }

                        }else{
                            if(prod.getQtdArmazenamento() > 1){//vende em unidade
                                setQtdArmazenaProd(prod.getQtdArmazenamento());
                            }else{// não vende em unidade
                                setQtdArmazenaProd(1);
                            }
                            setTotal(getValor() * Long.parseLong(tvDgQtd.getText().toString())*getQtdArmazenaProd() + getDescAcreMone());
                            selectVendUn.setText(prod.getUnArmazena());
                        }

                        tvDgTotal.setText(String.format("%.2f", getTotal()).replace(",", "."));
                    }
                });

                tvDgProdListCalcSaldProd.setText(String.valueOf(prod.getSaldo()));
                btProdListSaldProd.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(getActivity(), "EM MANUTENÇÃO", Toast.LENGTH_SHORT).show();
                    }
                });

                imgBtDdIncDecAddQtd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tvDgQtd.getText().equals("")){
                            tvDgQtd.setText("0");
                        }
                        tvDgQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())+1));

                        itemLista.setQtd(tvDgQtd.getText().toString());
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));

                        if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1) {//////em caixa
                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                        }else if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() <= 1 ||
                                (chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1)){
                            setQtdArmazenaProd(1);
                        }
                        setTotal(getValor() * Long.parseLong(itemLista.getQtd()) * getQtdArmazenaProd() + getDescAcreMone());

                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                        itemLista.setCodProd(prod.getCodigo());
                        itemLista.setpVendSelect(spiDgValor.getSelectedItemId());
                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });
                final ImageButton imgBtDgIncDecSubQtd = (ImageButton)vAlert.findViewById(R.id.imgBtArmzSubQtd);
                imgBtDgIncDecSubQtd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tvDgQtd.getText().equals("")){
                            tvDgQtd.setText("0");
                        }
                        if(Integer.parseInt(tvDgQtd.getText().toString()) > 0){
                            tvDgQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())-1));
                        }

                        itemLista.setQtd(tvDgQtd.getText().toString());
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));

                        if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1) {//////em caixa
                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                        }else if(!chVendUnidade.isChecked() && prod.getQtdArmazenamento() <= 1 ||
                                (chVendUnidade.isChecked() && prod.getQtdArmazenamento() > 1)){
                            setQtdArmazenaProd(1);
                        }
                        setTotal(getValor() * Long.parseLong(itemLista.getQtd()) * getQtdArmazenaProd() + getDescAcreMone());
                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                        itemLista.setCodProd(prod.getCodigo());

                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });

                try {
                    ArrayList<Configuracao> configuracoes = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
                    if(configuracoes.size() > 0){
                        if(configuracoes.get(0).getDescontMax() == 0){
                            edtDesAcrPercentCalc.setEnabled(false);
                            edTxDescAcreDin.setEnabled(false);
                        }else{
                            edtDesAcrPercentCalc.setEnabled(true);
                            edTxDescAcreDin.setEnabled(true);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String idItem = "0";
                try {
                    idItem = new ListaPedidoRN(getActivity()).getIdItem(prod.getCodigo(), CurrentInfo.codCli, CurrentInfo.idPedido);
                    ItemLista item = new ItemLista();
                    if(Double.parseDouble(idItem) > 0){
                        ItemLista iList = new ItemListaRN(getActivity()).getForId(idItem);

                        if(!iList.getQtd().split("/")[1].equals("0")) {
                            chVendUnidade.setChecked(true);
                        }else if(iList.getQtd().split("/")[1].equals("0")) {
                            chVendUnidade.setChecked(false);
                        }

                        edTxDescAcreDin.setText(String.valueOf(iList.getDescAcreMone()));
                        edtDesAcrPercentCalc.setText(String.valueOf(iList.getDescAcrePercent()*100));
                        tvDgTotal.setText(String.format("%.2f", iList.getTotal()).replace(",", "."));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                spiDgValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Data dAtual = new Data(Calendar.getInstance().getTimeInMillis());
                        if (edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                            showMsgErro("PREENCHA AO MENOS UM CAMPO");
                        } else {
                            if (spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() > 0 && prod.getValPromo() > dAtual.getOnlyDataInMillis()) {
                                Utils.showMsg(getActivity(), "VALIDO ATÉ", new Data(prod.getValPromo()).getStringData(), null);
                            } else if ((spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() == 0)
                                    || (spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() < dAtual.getOnlyDataInMillis())) {
                                btAlertOkCalc.setEnabled(false);
                                Utils.showMsg(getActivity(), "ERRO", "PROMOÇÃO INVÁLIDA", R.drawable.dialog_error);
                            }
                            setIdProd(prod.getId());
                            qtd = Long.parseLong(tvDgQtd.getText().toString());

                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                            if (!chVendUnidade.isChecked()) {
                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")) * getQtdArmazenaProd());
                            } else {
                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                            }

                            double desMone = 0;
                            double taxa = 0;
                            if (!edtDesAcrPercentCalc.getText().toString().equals("") && (!edTxDescAcreDin.getText().toString().equals(""))) {
                                taxa = Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) / 100;//porcentagem
                                desMone = taxa * getTotal();
                                if (radDesc.isChecked()) {
                                    setDescAcrePercent(taxa * (-1));
                                    setDescAcreMone(desMone * (-1));
                                } else if (radAcre.isChecked()) {
                                    setDescAcrePercent(taxa * (+1));
                                    setDescAcreMone(desMone * (+1));
                                }
                            } else if ((Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) == 0.0 || edtDesAcrPercentCalc.getText().toString().equals("")) && !edTxDescAcreDin.getText().toString().equals("")) {
                                desMone = Double.parseDouble(edTxDescAcreDin.getText().toString());
                                taxa = desMone / getTotal();
                                if (radDesc.isChecked()) {
                                    setDescAcrePercent(desMone * (-1));
                                    setDescAcrePercent(taxa * (-1));
                                } else if (radAcre.isChecked()) {
                                    setDescAcrePercent(taxa * (+1));
                                    setDescAcrePercent(desMone * (+1));
                                }
                            }
                            tvDgTotal.setText(String.format("%.2f", getTotal() + getDescAcreMone()).replace(",", "."));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder alBuild = new AlertDialog.Builder(getActivity());
                alBuild.setView(vAlert);
                final AlertDialog alert = alBuild.show();

                if(listaPedido != null){
                    spiDgValor.setSelection((int)listaPedido.getItemLista().getpVendSelect());
                }else{
                    spiDgValor.setSelection(0);
                }

                btAlertOkCalc.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        if(!tvDgQtd.getText().toString().equals("") && Integer.parseInt(tvDgQtd.getText().toString()) != 0) {
                            if (edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                                showMsgErro("PREENCHA AO MENOS UM CAMPO DE DESCONTO");
                            } else {
                                setIdProd(prod.getId());
                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                                double desMone = 0;
                                double taxa = 0;
                                if (!edtDesAcrPercentCalc.getText().toString().equals("") && (edTxDescAcreDin.getText().toString().equals("") || Double.parseDouble(edTxDescAcreDin.getText().toString()) == 0.0)) {
                                    taxa = Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) / 100;//porcentagem
                                    desMone = taxa * getTotal();
                                    if (radDesc.isChecked()) {
                                        setDescAcrePercent(taxa * (-1));
                                        setDescAcreMone(desMone * (-1));
                                    } else if (radAcre.isChecked()) {
                                        setDescAcrePercent(taxa * (+1));
                                        setDescAcreMone(desMone * (+1));
                                    }
                                } else if ((Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) == 0.0 || edtDesAcrPercentCalc.getText().toString().equals("")) && !edTxDescAcreDin.getText().toString().equals("")) {
                                    desMone = Double.parseDouble(edTxDescAcreDin.getText().toString());
                                    taxa = desMone / getTotal();
                                    if (radDesc.isChecked()) {
                                        setDescAcrePercent(desMone * (-1));
                                        setDescAcrePercent(taxa * (-1));
                                    } else if (radAcre.isChecked()) {
                                        setDescAcrePercent(taxa * (+1));
                                        setDescAcrePercent(desMone * (+1));
                                    }
                                }
                                new Handler().post(
                                        new Thread() {
                                            public void run() {
                                                try {
                                                    synchronized (this) {
                                                        final ItemLista itemLista = new ItemLista();

                                                        tvFgListPedAdpQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())));

                                                        itemLista.setQtd(tvDgQtd.getText().toString());

                                                        if(chVendUnidade.isChecked()){
                                                            itemLista.setQtd("0/"+itemLista.getQtd());
                                                        }else{
                                                            itemLista.setQtd(itemLista.getQtd()+"/0");
                                                        }

                                                        itemLista.setDescAcrePercent(getDescAcrePercent());
                                                        itemLista.setDescAcreMone(getDescAcreMone());

                                                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                                                        if(!chVendUnidade.isChecked()){
                                                            setTotal(getValor() * Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", "")) * getQtdArmazenaProd() + getDescAcreMone());
                                                        }else{
                                                            setTotal(getValor() * Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", "")) + getDescAcreMone());
                                                        }


                                                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", getTotal()).replace(",", ".")));
                                                        itemLista.setCodProd(prod.getCodigo());
                                                        itemLista.setpVendSelect(spiDgValor.getSelectedItemId());
                                                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));

                                                        itemLista.setCodProd(prod.getCodigo());
                                                        itemLista.setpVendSelect(spiDgValor.getSelectedItemPosition());

                                                        new ItemListaRN(getActivity()).salvar(itemLista);

                                                        double totalItem = 0;

                                                        ArrayList<ListaPedido> list = new ListaPedidoRN(getActivity()).getForNomeProd("");
                                                        for(ListaPedido lp: list){
                                                            totalItem += lp.getItemLista().getTotal();
                                                        }
                                                        Pedido p = new PedidoRN(getActivity()).getForId(CurrentInfo.idPedido);
                                                        p.setTotal(totalItem);
                                                        new PedidoRN(getActivity()).update(p);

                                                        updateLista("");

                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                );
                            }
                        }else{
                            Utils.showMsg(getActivity(), "ERRO", "QUANTIDADE INVÁLIDA", R.drawable.dialog_error);
                        }
                        alert.dismiss();
                        setValor(0);

                        tvFgListPedAdpQtd.setText(String.valueOf(getQtd()));
                    }
                });
                btAlertCancelCalc.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        alert.dismiss();
                    }
                });
                */
            }

        });

        return vFragent;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getQtdArmazenaProd() {
        return qtdArmazenaProd;
    }

    public void setQtdArmazenaProd(double qtdArmazenaProd) {
        this.qtdArmazenaProd = qtdArmazenaProd;
    }

    public ArrayList<ListaPedido> updateLista(String nomeProd) throws SQLException {
        ArrayList<ListaPedido> list = new ListaPedidoRN(getActivity()).getForNomeProd(nomeProd);
        if(list.size() > 0){
            tvPedFgLstTotal = (TextView)vFragent.findViewById(R.id.tvPedFgLstTotal);
            //funciona lvProdutos = (ListView) vFragent.findViewById(R.id.frag_lv_list_pedido);
            lvProdutos.setAdapter(new ListaPedidoFragAdapter(getActivity(), list, getActivity().getSupportFragmentManager()));

            double total = 0;

            for(ListaPedido lp: list){
                total += lp.getItemLista().getTotal();
                Log.i("LSTpD", lp.getItemLista().getpVendSelect()+"tot:"+lp.getItemLista().getTotal());
            }

            tvPedFgLstTotal.setText(String.format("%.2f", total));

            Cliente cFilter = new Cliente();
            cFilter.setCodigo(list.get(0).getCodCli());
            ArrayList<Cliente> cList = new ClienteRN(getActivity()).pesquisar(cFilter);

            double limiCred = 0;
            if(cList.get(0).getLimitCred() > 0){
                limiCred = cList.get(0).getLimitCred()-Double.parseDouble(tvPedFgLstTotal.getText().toString().replace(",", "."));
                tvPedFgLstLimit.setText(String.format("%.2f", limiCred));
            }

            if(limiCred > 0){
                tvPedFgLstLimit.setBackgroundColor(Color.GREEN);
            }else{
                tvPedFgLstLimit.setBackgroundColor(Color.RED);
            }

            if(bundle != null && bundle.get("from") != null && bundle.get("from").equals("HistoriFgAdp")){
                //CurrentInfo.codCli = 0;
            }

        }
        return list;
    }

    public double getDescAcrePercent() {
        return descAcrePercent;
    }

    public void setDescAcrePercent(double descAcrePercent) {
        this.descAcrePercent = descAcrePercent;
    }

    public double getDescAcreMone() {
        return descAcreMone;
    }

    public void setDescAcreMone(double descAcreMone) {
        this.descAcreMone = descAcreMone;
    }

    public static long getQtd() {
        return qtd;
    }

    public static void setQtd(long qtd) {
        ListaPedidoFragment.qtd = qtd;
    }

    public boolean isRadioDesc() {
        return radioDesc;
    }

    public void setRadioDesc(boolean radioDesc) {
        this.radioDesc = radioDesc;
    }

    public boolean isRadioAcre() {
        return radioAcre;
    }

    public void setRadioAcre(boolean radioAcre) {
        this.radioAcre = radioAcre;
    }

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
    }

    public long getQtdCaixa() {
        return qtdCaixa;
    }

    public void setQtdCaixa(long qtdCaixa) {
        this.qtdCaixa = qtdCaixa;
    }

    public long getQtdUnidade() {
        return qtdUnidade;
    }

    public void setQtdUnidade(long qtdUnidade) {
        this.qtdUnidade = qtdUnidade;
    }

    public ArrayList<Configuracao> getConf() {
        return conf;
    }

    public void setConf(ArrayList<Configuracao> conf) {
        this.conf = conf;
    }

    AlertDialog alertErro = null;
    private void showMsgErro(String msg){
        AlertDialog.Builder alBuild = new AlertDialog.Builder(getActivity());
        alBuild.setTitle("ERRO");
        alBuild.setMessage(msg);


        alBuild.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertErro.dismiss();
            }
        });
        alertErro = alBuild.show();
    }

    // TODO: Rename method, updateForIdItem argument and hook method into UI event
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

    public void onStart(){
        super.onStart();
        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControlFragment.setActiveListaPedidos(true);
    }
    public void onStop(){
        super.onStop();
        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControlFragment.setActiveListaPedidos(false);
    }
    public void onResume(){
        super.onResume();
        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ControlFragment.setActiveListaPedidos(true);
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
}