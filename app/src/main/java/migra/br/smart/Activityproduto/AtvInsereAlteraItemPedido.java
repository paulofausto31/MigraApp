package migra.br.smart.Activityproduto;

import android.content.Intent;
import java.sql.SQLException;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Handler;

import migra.br.smart.R;
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
import migra.br.smart.utils.Utils;
import migra.br.smart.utils.data.Data;

public class AtvInsereAlteraItemPedido extends AppCompatActivity {

    private double total, valor;
    private double descAcrePercent;
    private double descAcreMone;
    private static long qtd = 0;
    private long idProd;
    private boolean radioDesc, radioAcre;

    private double qtdArmazenaProd = 0;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_insere_altera_item_pedido);

        final EditText tvDgQtd = (EditText) findViewById(R.id.edtQtdUnArmaz);
        Button btOk = (Button) findViewById(R.id.btOk);
        /*
        btOk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
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

                                                    tvProdAdpProdQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())));

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

                                                    new ItemListaRN(AtvInsereAlteraItemPedido.this).salvar(itemLista);

                                                    double totalItem = 0;

                                                    ArrayList<ListaPedido> list = new ListaPedidoRN(AtvInsereAlteraItemPedido.this).getForNomeProd("");
                                                    for(ListaPedido lp: list){
                                                        totalItem += lp.getItemLista().getTotal();
                                                    }
                                                    Pedido p = new PedidoRN(AtvInsereAlteraItemPedido.this).getForId(CurrentInfo.idPedido);
                                                    p.setTotal(totalItem);
                                                    new PedidoRN(AtvInsereAlteraItemPedido.this).update(p);

                                                    if(Long.parseLong(itemLista.getQtd().replace("0/", "").replace("/0", "")) > 0){
                                                        tvProdAdpProdQtd.setTextColor(Color.RED);
                                                    }


                                                }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                            );
                        }
                    }else{
                        Utils.showMsg(AtvInsereAlteraItemPedido.this, "ERRO", "QUANTIDADE INVÁLIDA", R.drawable.dialog_error);
                    }
                    alert.dismiss();

                    setValor(0);

                    tvProdAdpProdQtd.setText(String.valueOf(getQtd()));
                }


                ControlFragment.getAtvResult().add("teste");
                finish();
            }
        });

        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        final RadioGroup rGroup = (RadioGroup) findViewById(R.id.rGroup);
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

                tvDgQtd.setText(tvProdAdpProdQtd.getText());

                final TextView tvDgTotal = (TextView)findViewById(R.id.tvDgTotal);

                final CheckBox chVendUnidade = (CheckBox)findViewById(R.id.chVendUnidade);

                final RadioButton radDesc = (RadioButton) findViewById(R.id.radDesc);
                final RadioButton radAcre = (RadioButton) findViewById(R.id.radAcre);
                final EditText edtDesAcrPercentCalc = (EditText) findViewById(R.id.edtDesAcrPercentCalc);
                final EditText edTxDescAcreDin = (EditText) findViewById(R.id.edTxDescAcreDin);
                final Button btAlertOkCalc = (Button) findViewById(R.id.btAlertOkCalc);
                final Button btAlertCancelCalc = (Button) findViewById(R.id.btAlertCancelCalc);
                final Button btProdListSaldProd = (Button) findViewById(R.id.btProdListSaldProd);
                final TextView tvDgProdListCalcSaldProd = (TextView) findViewById(R.id.tvDgProdListCalcSaldProd);
                final TextView selectVendUn = (TextView) findViewById(R.id.selectVendUn);

                final ImageButton imgBtDdIncDecAddQtd = (ImageButton) findViewById(R.id.imgBtDdIncDecAddQtd);

                final Spinner spiDgValor = (Spinner)findViewById(R.id.spiDgFgLAdpVal);

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

                                Utils.showMsg(AtvInsereAlteraItemPedido.this, "ERRO", "VENDA POR UNIDADE NEGADA", R.drawable.dialog_error);
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
                final ImageButton imgBtDgIncDecSubQtd = (ImageButton)findViewById(R.id.imgBtDgIncDecSubQtd);
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
                    idItem = new ListaPedidoRN(AtvInsereAlteraItemPedido.this).getIdItem(prod.getCodigo(), CurrentInfo.codCli, CurrentInfo.idPedido);
                    ItemLista item = new ItemLista();
                    if(Double.parseDouble(idItem) > 0){
                        ItemLista iList = new ItemListaRN(AtvInsereAlteraItemPedido.this).getForId(idItem);
                        if(!iList.getQtd().split("/")[1].equals("0")) {//VENDE EM UNIDADE
                            chVendUnidade.setChecked(true);
                        }else if(iList.getQtd().split("/")[1].equals("0")) {//NÃO VENDE ME UNIDADE
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
                        //FUNCIONA spiAdapterValor.setSelection(spiDgValor.getSelectedItemPosition());
                        //spiAdapterValor.setText(spiDgValor.getSelectedItem().toString());
                        Data dAtual = new Data(Calendar.getInstance().getTimeInMillis());
                        if(edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                            showMsgErro("PREENCHA AO MENOS UM CAMPO");
                        }else {
                            if(spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() > 0 && prod.getValPromo() > dAtual.getOnlyDataInMillis()){
                                Utils.showMsg(AtvInsereAlteraItemPedido.this, "VALIDO ATÉ", new Data(prod.getValPromo()).getStringData(), null);
                            }else if((spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() == 0)
                            ||(spiDgValor.getSelectedItem().toString().contains("p") && prod.getValPromo() < dAtual.getOnlyDataInMillis())){
                                btAlertOkCalc.setEnabled(false);
                                Utils.showMsg(AtvInsereAlteraItemPedido.this, "ERRO", "PROMOÇÃO INVÁLIDA", R.drawable.dialog_error);
                            }
                                setIdProd(prod.getId());
                                qtd = Long.parseLong(tvDgQtd.getText().toString());

                            setQtdArmazenaProd(prod.getQtdArmazenamento());
                            if(!chVendUnidade.isChecked()){

                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", ""))*getQtdArmazenaProd());
                            }else{

                                setTotal(qtd * Double.parseDouble(spiDgValor.getSelectedItem().toString().replace("p", "")));
                            }


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

                /*
                AlertDialog.Builder alBuild = new AlertDialog.Builder(AtvInsereAlteraItemPedido.this);
                alBuild.setView(vAlert);
                final AlertDialog alert = alBuild.show();

                Log.i("FIPRO", conf.get(0).getFiltraEstoque()+"");
                if(conf != null && conf.size() > 0){
                    if(conf.get(0).getFiltraEstoque() == 2 && prod.getSaldo() == 0) {
                        Utils.showMsg(AtvInsereAlteraItemPedido.this, "ADVERTÊNCIA", "PRODUTO COM ESTOQUE ZERADO OU NEGATIVO", R.drawable.dialog_error);
                    }
                }


                if(listaPedidos != null && listaPedidos.size() > 0){
                    spiDgValor.setSelection((int)listaPedidos.get(0).getItemLista().getpVendSelect());
                }else{
                    spiDgValor.setSelection(0);
                }
                Toast.makeText(getActivity(), prod.getNome(), Toast.LENGTH_SHORT).show();
                */
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
        AtvInsereAlteraItemPedido.qtd = qtd;
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

    public double getQtdArmazenaProd() {
        return qtdArmazenaProd;
    }

    public void setQtdArmazenaProd(double qtdArmazenaProd) {
        this.qtdArmazenaProd = qtdArmazenaProd;
    }
}