package migra.br.smart.ActivityCliente.fragmentComodato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.comodato.Comodato;

/**
 * Created by r2d2 on 17/04/17.
 */

public class ComodFgAdp extends BaseAdapter {
    Context ctx;
    List<Comodato> list;

    public ComodFgAdp(Context ctx, List<Comodato> list){
        this.ctx = ctx;
        this.list = list;
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
        View v = inflater.inflate(R.layout.como_frag_adapt, null);

        final TextView tvComoFragCliNome = (TextView) v.findViewById(R.id.tvComoFragCliNome);
        final TextView tvComoFragProdNome = (TextView) v.findViewById(R.id.tvComoFragProdNome);
        final TextView tvComoFragSaldProd = (TextView) v.findViewById(R.id.tvComoFragSaldProd);

        final Comodato como = list.get(position);

        tvComoFragCliNome.setText(como.getCliente().getFantasia());
        tvComoFragProdNome.setText(como.getProduto().getNome());
        tvComoFragSaldProd.setText(String.valueOf(como.getSaldo()));
        return v;
    }
}
