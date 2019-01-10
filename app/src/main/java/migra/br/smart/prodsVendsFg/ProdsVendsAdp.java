package migra.br.smart.prodsVendsFg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedido;

/**
 * Created by root on 07/10/17.
 */

public class ProdsVendsAdp extends BaseAdapter {

    List<ListaPedido> list;
    Context ctx;

    public ProdsVendsAdp(Context ctx, ArrayList<ListaPedido> list){
        this.list = list;
        this.ctx = ctx;
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
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.prods_vends_adp, null);

        double qtdCaixa = 0;
        double qtdUnidade = 0;
        long convertCaixa = 0;
        final ListaPedido ped = (ListaPedido)getItem(position);

        TextView tvUnVend = (TextView) v.findViewById(R.id.tvUnVend);
        TextView tvQtdUnVend = (TextView) v.findViewById(R.id.tvQtdUnVend);
        TextView tvUnArmz = (TextView) v.findViewById(R.id.tvUnArmz);
        TextView pdVendFgNome = (TextView) v.findViewById(R.id.pdVendFgNome);
        TextView tvQtdCaixa = (TextView) v.findViewById(R.id.tvQtdCaixa);
        TextView pdVendFgTotal = (TextView) v.findViewById(R.id.pdVendFgTotal);

        pdVendFgTotal.setText(String.valueOf(ped.getItemLista().getTotal()));

        //double qtd = Double.parseDouble(ped.getItemLista().getQtd());
        //qtdCaixa = Double.parseDouble(ped.getItemLista().getQtd().split("/")[0]);
        qtdCaixa = Double.parseDouble(ped.getItemLista().getQtd());
        //qtdUnidade = Double.parseDouble(ped.getItemLista().getQtd().split("/")[1]);
        qtdUnidade = ped.getItemLista().getQtdUn();
        if(qtdUnidade >= ped.getProduto().getQtdArmazenamento()){
             convertCaixa = (long)(qtdUnidade / (long)ped.getProduto().getQtdArmazenamento());
            qtdCaixa += convertCaixa;
            qtdUnidade = qtdUnidade % ped.getProduto().getQtdArmazenamento();
        }

        //tvQtdCaixa.setText(tvQtdCaixa.getText()+ped.getItemLista().getProduto().getUnidadeVenda());
        tvQtdCaixa.setText(qtdCaixa+"");
        tvQtdUnVend.setText(qtdUnidade+"");
        pdVendFgNome.setText(ped.getProduto().getNome());
        tvUnVend.setText(ped.getProduto().getUnidadeVenda());
        tvUnArmz.setText(ped.getProduto().getUnArmazena());
        //tvQtdUnVend.setText(ped.getItemLista().getQtd().split("/")[1]);

        return v;
    }
}