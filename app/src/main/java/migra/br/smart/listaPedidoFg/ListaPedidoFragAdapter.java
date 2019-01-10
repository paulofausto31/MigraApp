package migra.br.smart.listaPedidoFg;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.Produto.Produto;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;

public class ListaPedidoFragAdapter extends BaseAdapter {
    List<ListaPedido> list;
    Context ctx;
    private double desconto;
    private double descAcrePercent;
    private double descAcreMone;
    private long idProd;
    private boolean radioDesc, radioAcre;
    private static long qtd = 0;
    private double total = 0;
    private double qtdArmazenamento;
    private FragmentManager fm;

    public ListaPedidoFragAdapter(Context ctx, List<ListaPedido> list){
        this.ctx = ctx;
        this.list = list;
    }

    public ListaPedidoFragAdapter(Context ctx, List<ListaPedido> list, FragmentManager fm){
        this.ctx = ctx;
        this.list = list;
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflate = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.ped_fg_list_adp, null);

        final ListaPedido listaPedido = list.get(position);

        //final TextView tvFgListPediAdpTotal = (TextView) v.findViewById(R.id.tvFgListPediAdpTotal);
        final TextView tvQtdCaixa = (TextView)v.findViewById(R.id.tvQtdCaixa);
        final TextView tvTotal = (TextView) v.findViewById(R.id.tvTotal);
        //funciona final Spinner spiAdapterValor = (Spinner) v.findViewById(R.id.spiFgListPediVal);//SELECIONAR O VALOR DO PRODUTO
        final TextView spiAdapterValor = (TextView) v.findViewById(R.id.spiFgListPediVal);//SELECIONAR O VALOR DO PRODUTO
        final CheckBox chDelete = (CheckBox)v.findViewById(R.id.chDelete);
        final TextView tvUnArmz = (TextView) v.findViewById(R.id.tvUnArmz);

        final TextView tvQtdUnVend = (TextView) v.findViewById(R.id.tvQtdUnVend);
        final TextView tvUnVend = (TextView) v.findViewById(R.id.tvUnVend);

        final Produto prod = listaPedido.getItemLista().getProduto();
        if(prod.getQtdArmazenamento() <= 0){
            setQtdArmazenamento(1);
        }else{
            setQtdArmazenamento(prod.getQtdArmazenamento());
        }

        tvTotal.setText(String.format("%.2f", listaPedido.getItemLista().getTotal()).replace(",", "."));

        if(listaPedido.getDeletar().equals("s")){
            chDelete.setChecked(true);
        }else{
            chDelete.setChecked(false);
        }

        chDelete.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    listaPedido.setDeletar("s");
                }else{
                    listaPedido.setDeletar("n");
                }
                try {
                    Log.i("ID_ITEM", listaPedido.getIdItem()+"");
                    new ListaPedidoRN(ctx).updateForIdItem(listaPedido);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //tvQtdCaixa.setText(listaPedido.getItemLista().getQtd().split("/")[0]);
        tvQtdCaixa.setText(listaPedido.getItemLista().getQtd());
        //tvQtdUnVend.setText(listaPedido.getItemLista().getQtd().split("/")[1]);
        tvQtdUnVend.setText(String.valueOf(listaPedido.getItemLista().getQtdUn()));

        //funciona ArrayAdapter<String> adapterValor = new ArrayAdapter<String>(ctx, R.layout.spinner_prod_adapter, prod.getValores());
        //funciona spiAdapterValor.setAdapter(adapterValor);
        //funciona spiAdapterValor.setSelection((int)listaPedido.getItemLista().getpVendSelect());
        spiAdapterValor.setText(prod.getValores().get((int)listaPedido.getItemLista().getpVendSelect()));
        Log.i("lPedido", listaPedido.getItemLista().getpVendSelect()+"");

        Log.i("unVend", listaPedido.getItemLista().getProduto().getUnidadeVenda());

        tvUnVend.setText(listaPedido.getItemLista().getProduto().getUnidadeVenda());
        tvUnArmz.setText(listaPedido.getItemLista().getProduto().getUnArmazena());
        //if(listaPedido.getItemLista().getProduto().getVENDE_FRACIONADO().equals("S")) {
        /*
        if(prod.getQtdArmazenamento() > 1) {//vende em unidade

            if (listaPedido.getItemLista().getQtd().endsWith("/0")) {//vendedo em caixa
                tvUnArmz.setText(listaPedido.getItemLista().getProduto().getUnArmazena());
            } else {
                tvUnArmz.setText(listaPedido.getItemLista().getProduto().getUnidadeVenda());
            }
        }else{
            tvUnArmz.setText(listaPedido.getItemLista().getProduto().getUnidadeVenda());
        }
        */
        TextView tvNome = (TextView) v.findViewById(R.id.tvFgListPediNome);
        v.requestFocus();
        tvNome.setText(prod.getNome());

        //tvFgListPediAdpTotal.setText(String.valueOf(listaPedido.getItemLista().getQtd()));

        ImageButton imgBtCalculos = (ImageButton)v.findViewById(R.id.imgBtListPediAdpCalculos);
        imgBtCalculos.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){

            }

        });

        //TextView tvQtd = (TextView) v.findViewById(R.id.tvQtd);

        if(position % 2 == 0) {
            v.setBackgroundColor(Color.WHITE);
        }else{
            v.setBackgroundColor(Color.LTGRAY);
        }

        return v;
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

    public double getQtdArmazenamento() {
        return qtdArmazenamento;
    }

    public void setQtdArmazenamento(double qtdArmazenamento) {
        this.qtdArmazenamento = qtdArmazenamento;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
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
        ListaPedidoFragAdapter.qtd = qtd;
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

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
    }
}