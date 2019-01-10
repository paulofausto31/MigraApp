package migra.br.smart.Activityproduto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.SaldoEstoque.SaldoEstoque;
import migra.br.smart.manipulaBanco.entidades.SaldoEstoque.SaldoEstoqueRN;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaRN;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;

/**
 * Created by ydxpaj on 22/07/2016.
 */
public class ProdutoAdapter extends BaseAdapter {

    Context ctx;
    List<Produto> lista;

    Intent it;
    private double descAcrePercent;
    private double descAcreMone;
    private static long qtd = 0;
    private double total = 0;
    private long idProd;
    private boolean radioDesc, radioAcre;

    public ProdutoAdapter(Context ctx, List<Produto> lista){
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflate = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflate.inflate(R.layout.produto_drawer, null);
        View v = inflate.inflate(R.layout.prod_list_fg_adp, null);

        //final TextView tvProdAdapterSaldo = (TextView) v.findViewById(R.id.tvProdAdapterSaldo);

        final TextView tvQtdCaixa = (TextView)v.findViewById(R.id.tvQtdCaixa);
        final TextView tvUnArmz = (TextView)v.findViewById(R.id.tvUnArmz);
        final TextView tvQtdUnVend = (TextView) v.findViewById(R.id.tvQtdUnVend);
        final TextView tvUnVend = (TextView) v.findViewById(R.id.tvUnVend);

        //String lPedItemQtd = "0/0";
        String[] lPedItemQtd = new String[]{"0", "0"};

        //final Spinner spiAdapterValor = (Spinner) v.findViewById(R.id.spiAdapterValor);//SELECIONAR O VALOR DO PRODUTO
        //final TextView spiAdapterValor = (TextView) v.findViewById(R.id.spiAdapterValor);//SELECIONAR O VALOR DO PRODUTO
        final Produto prod = lista.get(position);
        try {
            lPedItemQtd = new ListaPedidoRN(ctx).getQtdItemListaPedido(prod.getCodigo(), CurrentInfo.codCli, CurrentInfo.idPedido);
            //tvQtdCaixa.setText(lPedItemQtd.split("/")[0]);
            tvQtdCaixa.setText(lPedItemQtd[0]);
            //tvQtdUnVend.setText(lPedItemQtd.split("/")[1]);
            tvQtdUnVend.setText(lPedItemQtd[1]);
            tvUnArmz.setText(prod.getUnArmazena());
            tvUnVend.setText(prod.getUnidadeVenda());
            //if(lPedItemQtd > 0){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adapterValor = new ArrayAdapter<String>(ctx, R.layout.spinner_prod_adapter, prod.getValores());
        //FUNCIONA spiAdapterValor.setAdapter(adapterValor);

        TextView tvNome = (TextView) v.findViewById(R.id.tvnome);
        tvNome.setText(prod.getNome());
        SaldoEstoque sald = new SaldoEstoque();
        sald.setProduto(prod.getCodigo());
        try {
            ArrayList<SaldoEstoque> lSald = new SaldoEstoqueRN(ctx).filter(sald);
            if(lSald.size() > 0){
                //tvProdAdapterSaldo.setText(String.valueOf(lSald.get(0).getSaldo()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        ImageButton imgBtCalculos = (ImageButton)v.findViewById(R.id.imgBtCalculos);
        imgBtCalculos.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vAlert = inflater.inflate(R.layout.dg_prod_list_calc, null);
                setQtd(Long.parseLong(tvProdAdpProdQtd.getText().toString()));
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

                final TextView tvDgQtd = (TextView)vAlert.findViewById(R.id.tvDgQtd);
                tvDgQtd.setText(tvProdAdpProdQtd.getText());

                final TextView tvDgTotal = (TextView)vAlert.findViewById(R.id.tvDgTotal);

                final RadioButton radDesc = (RadioButton) vAlert.findViewById(R.id.radDesc);
                final RadioButton radAcre = (RadioButton) vAlert.findViewById(R.id.radAcre);
                final EditText edtDesAcrPercentCalc = (EditText) vAlert.findViewById(R.id.edtDesAcrPercentCalc);
                final EditText edTxDescAcreDin = (EditText) vAlert.findViewById(R.id.edTxDescAcreDin);
                final Button btAlertOkCalc = (Button) vAlert.findViewById(R.id.btAlertOkCalc);
                final Button btAlertCancelCalc = (Button) vAlert.findViewById(R.id.btAlertCancelCalc);
                final Button btProdListSaldProd = (Button) vAlert.findViewById(R.id.btProdListSaldProd);
                final TextView tvDgProdListCalcSaldProd = (TextView) vAlert.findViewById(R.id.tvDgProdListCalcSaldProd);

                //final EditText edTxDgIncDecQtd = (EditText)vAlert.findViewById(R.id.tvDgQtd);
                //final EditText edtDgIncDecQtd = (EditText) vAlert.findViewById(R.id.tvDgQtd);
                final ImageButton imgBtDdIncDecAddQtd = (ImageButton) vAlert.findViewById(R.id.imgBtDdIncDecAddQtd);
                final ItemLista itemLista = new ItemLista();

                //edTxDgIncDecQtd.setText(String.valueOf(Integer.parseInt(tvProdQtd.getText().toString())));
                tvDgQtd.setText(String.valueOf(Integer.parseInt(tvProdAdpProdQtd.getText().toString())));
                tvDgProdListCalcSaldProd.setText(String.valueOf(prod.getSaldo()));
                btProdListSaldProd.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        TaskConnectNetWork task = new TaskConnectNetWork(ctx, null, 0, tvDgProdListCalcSaldProd, CurrentInfo.codVendedor);
                        task.execute(String.valueOf(Operations.CONSUL_SAL_PROD), prod.getHora());
                    }
                });

                imgBtDdIncDecAddQtd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tvDgQtd.getText().equals("")){
                            tvDgQtd.setText("0");
                        }
                        tvDgQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())+1));
                        itemLista.setQtd(Long.parseLong(tvDgQtd.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());
                        //FUNCIONA double valor = Double.parseDouble(String.valueOf(spiAdapterValor.getSelectedItem()));
                        double valor = Double.parseDouble(spiAdapterValor.getText().toString());
                        total = valor * itemLista.getQtd()+ getDescAcreMone();
                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", total).replace(",", ".")));
                        itemLista.setCodProd(prod.getHora());

                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });
                final ImageButton imgBtDgIncDecSubQtd = (ImageButton)vAlert.findViewById(R.id.imgBtDgIncDecSubQtd);
                imgBtDgIncDecSubQtd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tvDgQtd.getText().equals("")){
                            tvDgQtd.setText("0");
                        }
                        if(Integer.parseInt(tvDgQtd.getText().toString()) > 0){
                            tvDgQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())-1));
                        }

                        itemLista.setQtd(Long.parseLong(tvDgQtd.getText().toString()));
                        itemLista.setDescAcrePercent(getDescAcrePercent());
                        itemLista.setDescAcreMone(getDescAcreMone());
                        //FUNCIONA double valor = Double.parseDouble(String.valueOf(spiAdapterValor.getSelectedItem()));
                        double valor = Double.parseDouble(spiAdapterValor.getText().toString());
                        total = valor * itemLista.getQtd()+ getDescAcreMone();
                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", total).replace(",", ".")));
                        itemLista.setCodProd(prod.getHora());

                        tvDgTotal.setText(String.format("%.2f", itemLista.getTotal()).replace(",", "."));
                    }
                });

                try {
                    ArrayList<Configuracao> configuracoes = new ConfiguracaoRN(ctx).pesquisar(new Configuracao());
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

                long id = 0;
                try {
                    id = new ListaPedidoRN(ctx).getIdItem(prod.getHora(), CurrentInfo.codCli, CurrentInfo.idPedido);
                    ItemLista item = new ItemLista();
                    if(id > 0){
                        ItemLista iList = new ItemListaRN(ctx).getForId(id);
                        edTxDescAcreDin.setText(String.valueOf(iList.getDescAcreMone()));
                        edtDesAcrPercentCalc.setText(String.valueOf(iList.getDescAcrePercent()*100));
                        tvDgTotal.setText(String.format("%.2f", iList.getTotal()).replace(",", "."));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                final Spinner spiDgValor = (Spinner)vAlert.findViewById(R.id.spiDgFgLAdpVal);
                spiDgValor.setAdapter(adapterValor);
                spiDgValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //FUNCIONA spiAdapterValor.setSelection(spiDgValor.getSelectedItemPosition());
                        spiAdapterValor.setText(spiDgValor.getSelectedItemPosition());
                        if(edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                            showMsgErro("PREENCHA AO MENOS UM CAMPO");
                        }else {
                            setIdProd(prod.getSeq_id());
                            qtd = Long.parseLong(tvDgQtd.getText().toString());
                            total = qtd * Double.parseDouble(String.valueOf(spiDgValor.getSelectedItem()));
                            double desMone = 0;
                            double taxa = 0;
                            if (!edtDesAcrPercentCalc.getText().toString().equals("") && (!edTxDescAcreDin.getText().toString().equals(""))) {
                                taxa = Double.parseDouble(edtDesAcrPercentCalc.getText().toString())/100;//porcentagem
                                desMone = taxa*total;
                                if (radDesc.isChecked()) {
                                    setDescAcrePercent(taxa * (-1));
                                    setDescAcreMone(desMone * (-1));
                                } else if (radAcre.isChecked()) {
                                    setDescAcrePercent(taxa * (+1));
                                    setDescAcreMone(desMone * (+1));
                                }
                            }else if((Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) == 0.0 || edtDesAcrPercentCalc.getText().toString().equals("")) && !edTxDescAcreDin.getText().toString().equals("")) {
                                desMone = Double.parseDouble(edTxDescAcreDin.getText().toString());
                                taxa = desMone/total;
                                if (radDesc.isChecked()) {
                                    setDescAcrePercent(desMone * (-1));
                                    setDescAcrePercent(taxa * (-1));
                                } else if (radAcre.isChecked()) {
                                    setDescAcrePercent(taxa * (+1));
                                    setDescAcrePercent(desMone * (+1));
                                }
                                //Toast.makeText(ctx, "%"+taxa+"\n"+"din"+desMone+"\n"+qtd, Toast.LENGTH_LONG).show();
                            }
                            tvDgTotal.setText(String.format("%.2f", total+getDescAcreMone()).replace(",", "."));
//                            Toast.makeText(ctx, getDescAcrePercent()+"\n"+qtd+"\n"+total, Toast.LENGTH_LONG).show();
                           // Toast.makeText(ctx, getDescAcrePercent()+"\n"+qtd, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder alBuild = new AlertDialog.Builder(ctx);
                alBuild.setView(vAlert);
                final AlertDialog alert = alBuild.show();
                //FUNCIONA spiDgValor.setSelection(spiAdapterValor.getSelectedItemPosition());
                spiDgValor.setSelection(adapterValor.getPosition(spiAdapterValor.getText().toString()));
                btAlertOkCalc.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        if(!tvDgQtd.getText().toString().equals("")) {
                            if (edTxDescAcreDin.getText().toString().equals("") && edtDesAcrPercentCalc.getText().toString().equals("")) {
                                showMsgErro("PREENCHA AO MENOS UM CAMPO DE DESCONTO");
                            } else {
                                setIdProd(prod.getSeq_id());
                                //qtd = Long.parseLong(edtxProdQtd.getText().toString());
                                total = qtd * Double.parseDouble(String.valueOf(spiDgValor.getSelectedItem()));
                                double desMone = 0;
                                double taxa = 0;
                                if (!edtDesAcrPercentCalc.getText().toString().equals("") && (edTxDescAcreDin.getText().toString().equals("") || Double.parseDouble(edTxDescAcreDin.getText().toString()) == 0.0)) {
                                    taxa = Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) / 100;//porcentagem
                                    desMone = taxa * total;
                                    if (radDesc.isChecked()) {
                                        setDescAcrePercent(taxa * (-1));
                                        setDescAcreMone(desMone * (-1));
                                    } else if (radAcre.isChecked()) {
                                        setDescAcrePercent(taxa * (+1));
                                        setDescAcreMone(desMone * (+1));
                                    }
                                } else if ((Double.parseDouble(edtDesAcrPercentCalc.getText().toString()) == 0.0 || edtDesAcrPercentCalc.getText().toString().equals("")) && !edTxDescAcreDin.getText().toString().equals("")) {
                                    desMone = Double.parseDouble(edTxDescAcreDin.getText().toString());
                                    taxa = desMone / total;
                                    if (radDesc.isChecked()) {
                                        setDescAcrePercent(desMone * (-1));
                                        setDescAcrePercent(taxa * (-1));
                                    } else if (radAcre.isChecked()) {
                                        setDescAcrePercent(taxa * (+1));
                                        setDescAcrePercent(desMone * (+1));
                                    }
                                  //  Toast.makeText(ctx, "%" + getDescAcrePercent() + "\n" + "din" + getDescAcreMone() + "\n" + qtd, Toast.LENGTH_LONG).show();
                                }
                                //tvDgTotal.setText(String.valueOf(total+desMone));

                                new Handler().post(
                                        new Thread() {
                                            public void run() {
                                                try {
                                                    synchronized (this) {
                                                        final ItemLista itemLista = new ItemLista();
                                                        tvProdAdpProdQtd.setText(String.valueOf(Integer.parseInt(tvDgQtd.getText().toString())));
                                                        itemLista.setQtd(Long.parseLong(tvDgQtd.getText().toString()));
                                                        itemLista.setDescAcrePercent(getDescAcrePercent());
                                                        itemLista.setDescAcreMone(getDescAcreMone());

                                                        itemLista.setTotal(Double.parseDouble(String.format("%.2f", Double.parseDouble(tvDgTotal.getText().toString())).replace(",", ".")));
                                                        itemLista.setCodProd(prod.getHora());
                                                        itemLista.setpVendSelect(spiDgValor.getSelectedItemPosition()+"");

                                                        new ItemListaRN(ctx).salvar(itemLista);

                                                        double total = 0;

                                                        ArrayList<ListaPedido> list = new ListaPedidoRN(ctx).getForNomeProd("");
                                                        for(ListaPedido lp: list){
                                                            total += lp.getItemLista().getTotal();
                                                        }
                                                        Pedido p = new PedidoRN(ctx).getForId(CurrentInfo.idPedido);
                                                        p.setTotal(total);
                                                        new PedidoRN(ctx).update(p);
                                                        //new ItemListaRN(ctx).update(itemLista);
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                );
                            }
                        }else{
                            Utils.showMsg(ctx, "ERRO", "QUANTIDADE INVÁLIDA", R.drawable.dialog_error);
                        }
                        alert.dismiss();

                        tvProdAdpProdQtd.setText(String.valueOf(getQtd()));
                    }
                });
                btAlertCancelCalc.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        alert.dismiss();
                    }
                });
            }

        });*/

        //TextView tvQtd = (TextView) v.findViewById(R.id.tvQtd);

        if(position % 2 == 0) {
            v.setBackgroundColor(Color.WHITE);
        }else{
            v.setBackgroundColor(Color.LTGRAY);
        }

        //if(!lPedItemQtd.split("/")[0].equals("0") || !lPedItemQtd.split("/")[1].equals("0")){
        if(!lPedItemQtd[0].equals("0") || !lPedItemQtd[1].equals("0")){
            tvQtdCaixa.setTextColor(Color.RED);
            tvQtdUnVend.setTextColor(Color.RED);
            v.setBackgroundColor(Color.GREEN);
        }

        return v;
    }

    public class TaskAddQtdkProdutoAdapter extends AsyncTask<Integer, Integer, Boolean> {

        final ItemLista itemLista = new ItemLista();

        String valor;
        int qtd;
        String codProd;

        public TaskAddQtdkProdutoAdapter(String valor, String codProd){
            this.valor = valor;
            this.codProd = codProd;
        }

        protected void onPreExecute(){


        }
        protected void onPostExecute(Boolean result){

        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                itemLista.setDescAcrePercent(getDescAcrePercent());
                itemLista.setDescAcreMone(getDescAcreMone());
                itemLista.setQtd(String.valueOf(params[0]));
                itemLista.setTotal(Double.parseDouble(String.valueOf(this.valor)) * Long.parseLong(itemLista.getQtd())+ getDescAcreMone());
                itemLista.setCodProd(codProd);
                Log.i("Registro", itemLista.getCodProd());
                new ItemListaRN(ctx).salvar(itemLista);
                //    }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
    }


    public double getDescAcreMone() {
        return descAcreMone;
    }

    public void setDescAcreMone(double descAcreMone) {
        this.descAcreMone = descAcreMone;
    }

    public double getDescAcrePercent() {
        return descAcrePercent;
    }

    public void setDescAcrePercent(double descAcrePercent) {
        this.descAcrePercent = descAcrePercent;
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

    public static long getQtd() {
        return qtd;
    }

    public static void setQtd(long qtd) {
        ProdutoAdapter.qtd = qtd;
    }

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
    }

    AlertDialog alertErro = null;
    private void showMsgErro(String msg){
        AlertDialog.Builder alBuild = new AlertDialog.Builder(ctx);
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
}
