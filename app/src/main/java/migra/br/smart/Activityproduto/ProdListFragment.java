package migra.br.smart.Activityproduto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import migra.br.smart.manipulaBanco.entidades.configuracao.Configuracao;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoRN;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaRN;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.utils.ControlFragment.ControlFragment;
import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoRN;
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProdListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProdListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProdListFragment extends Fragment {
    ListView lvProdutos;
    ArrayList<Produto> lista;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double descAcrePercent;
    private double descAcreMone;
    //private static long qtd = 0;
    private double total = 0;
    private long idProd;
    private boolean radioDesc, radioAcre;

    private long qtdCaixa, qtdUnidade;

    private double qtdArmazenaProd = 0;

    private  double valor;

    ArrayList<ListaPedido> listaPedidos;//para spinDgValor, principalmente

    private OnFragmentInteractionListener mListener;

    private ArrayList<Configuracao> conf;

    public ProdListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProdListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProdListFragment newInstance(String param1, String param2) {
        ProdListFragment fragment = new ProdListFragment();
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
        View view = inflater.inflate(R.layout.prod_list_fragment, container, false);

        lvProdutos = (ListView) view.findViewById(R.id.lv);

        try {
            setConf(new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao()));
            //Utils.setExtraQuery("");//realiza query padrao
          //  Utils.setExtraQuery(" and saldo > 0 ");//////////////////////////////////////////////////////EXTRA QUERY
            lista = pesquisar();
        } catch (SQLException e) {
            Log.e("erro sql", e.getMessage());
        }

        lvProdutos.setAdapter(new ProdutoAdapter(getActivity(), lista));
        lvProdutos.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final TextView tvQtdCaixa = (TextView)view.findViewById(R.id.tvQtdCaixa);
                //final TextView tvQtdCaixa = (TextView)v.findViewById(R.id.tvQtdCaixa);
                final TextView tvUnArmz = (TextView)view.findViewById(R.id.tvUnArmz);
                final TextView tvQtdUnVend = (TextView) view.findViewById(R.id.tvQtdUnVend);
                final TextView tvUnVend = (TextView) view.findViewById(R.id.tvUnVend);

                final Produto prod = (Produto)parent.getAdapter().getItem(position);
                if(prod.getQtdArmazenamento() == 0){
                    prod.setQtdArmazenamento(1);
                }
                setQtdArmazenaProd(prod.getQtdArmazenamento());

                String[] lPedItemQtd = new String[2];

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
                final View vAlert = inflater.inflate(R.layout.dg_prod_list_calc, null);

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

                final TextView selectUnVend = (TextView) vAlert.findViewById(R.id.selectUnVend);
                final EditText edtQtdUnVend = (EditText) vAlert.findViewById(R.id.edtQtdUnVend);
                final EditText edtQtdUnArmz = (EditText)vAlert.findViewById(R.id.edtQtdUnArmz);

                edtQtdUnArmz.setText(tvQtdCaixa.getText());
                edtQtdUnVend.setText(tvQtdUnVend.getText());

                final TextView tvDgTotal = (TextView)vAlert.findViewById(R.id.tvDgTotal);

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

                edtQtdUnArmz.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        edtQtdUnArmz.setSelection(0,edtQtdUnArmz.length());
                    }
                    });

                edtQtdUnVend.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        edtQtdUnVend.setSelection(0,edtQtdUnVend.length());
                    }
                });

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
                            setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));
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

                        setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());

                        setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
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

                spiDgValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                            setQtdUnidade(Long.parseLong(edtQtdUnVend.getText().toString()));
                            setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));

                            setQtdArmazenaProd(prod.getQtdArmazenamento());
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
                        }else if(Integer.parseInt((edtQtdUnArmz.getText().toString()) + Integer.parseInt(edtQtdUnVend.getText().toString())) > prod.getSaldo()) {
                            showMsgErro("Quantidade maior que o saldo do estoque");
                        }else if (edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                                showMsgErro("PREENCHA AO MENOS UM CAMPO DE DESCONTO");
                            } else {
                                setIdProd(prod.getId());
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
                                                        if(Integer.parseInt(edtQtdUnVend.getText().toString()) < prod.getQtdArmazenamento()) {//se não passar do limite de unidade
                                                            final ItemLista itemLista = new ItemLista();
                                                            // itemLista.setQtd(edtQtdUnArmz.getText().toString());

                                                            setQtdCaixa(Long.parseLong(edtQtdUnArmz.getText().toString()));
                                                            setQtdUnidade(Long.parseLong(edtQtdUnVend.getText().toString()));
                                                            //itemLista.setQtd(getQtdCaixa() + "/" + getQtdUnidade());
                                                            itemLista.setQtd(String.valueOf(getQtdCaixa()));
                                                            itemLista.setQtdUn(getQtdUnidade());

                                                            itemLista.setDescAcrePercent(getDescAcrePercent());
                                                            itemLista.setDescAcreMone(getDescAcreMone());

                                                            setValor(Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));

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

                                                            //tvQtdUnVend.setText(itemLista.getQtd().split("/")[1]);
                                                            tvQtdUnVend.setText(String.valueOf(itemLista.getQtdUn()));

                                                            //tvQtdCaixa.setText(itemLista.getQtd().split("/")[0]);
                                                            tvQtdCaixa.setText(itemLista.getQtd());

//                                                            if (!itemLista.getQtd().split("/")[0].equals("0") || !itemLista.getQtd().split("/")[1].equals("0") || itemLista.getQtdUn() > 0) {
                                                            if (!itemLista.getQtd().equals("0") || itemLista.getQtdUn() > 0) {
                                                                tvQtdCaixa.setTextColor(Color.RED);
                                                                tvQtdUnVend.setTextColor(Color.RED);
                                                                view.setBackgroundColor(Color.GREEN);
                                                            }
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
            }
        });

        return view;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getQtdArmazenaProd() {
        return qtdArmazenaProd;
    }

    public void setQtdArmazenaProd(double qtdArmazenaProd) {
        this.qtdArmazenaProd = qtdArmazenaProd;
    }

    public void updateLista(ArrayList<Produto> list) throws SQLException{
        lvProdutos = (ListView) getActivity().findViewById(R.id.lv);
        lvProdutos.setAdapter(new ProdutoAdapter(getActivity(), list));
    }

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
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
    /*
    public long getQtd() {
        return qtd;
    }

    public void setQtd(long qtd) {
        this.qtd = qtd;
    }
    */
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

    private ArrayList<Produto> pesquisar() throws SQLException {
        ArrayList<Configuracao> conf = new ConfiguracaoRN(getActivity()).pesquisar(new Configuracao());
        Log.i("FIPRO", conf.get(0).getFiltraEstoque()+"");
        if(conf != null && conf.size() > 0){
            if(conf.get(0).getFiltraEstoque() == 3) {
                Utils.setExtraQuery(" and saldo <> 0 ");
            }
        }
        Produto p = new Produto();
        p.setTIPO("00");

        return new ProdutoRN(getActivity()).pesquisar(p);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("prodListFrag");

       // if (fragment instanceof CreditCardFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
       // }
        if(requestCode == 1){
            if(resultCode == getActivity().RESULT_OK){
                Toast.makeText(getActivity(), data.getStringExtra("teste"), Toast.LENGTH_SHORT).show();
            }

        }
    }

    // TODO: Rename method, updateForIdItem argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onStart(){
        super.onStart();

        //temporariario ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);

        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i("PROD_LIST_FRAGMENT", "PROD_LIST_FRAGMENT");
        ControlFragment.setActiveProd(true);
    }
    public void onStop(){
        super.onStop();
        ControlFragment.setActiveProd(false);
    }
    public void onResume(){
        super.onResume();

        //temporariario ActivityContainerFragments.fabPedidos.setVisibility(View.VISIBLE);

        try {
            Utils.verifiLicence(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i("PROD_LIST_FRAGMENT", "PROD_LIST_FRAGMENT");
        ControlFragment.setActiveProd(true);
        if(ControlFragment.getAtvResult().size() > 0) {
            Toast.makeText(getActivity(), (String) ControlFragment.getAtvResult().get(0), Toast.LENGTH_SHORT).show();
            ControlFragment.getAtvResult().clear();
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public ArrayList<Configuracao> getConf() {
        return conf;
    }

    public void setConf(ArrayList<Configuracao> conf) {
        this.conf = conf;
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
}