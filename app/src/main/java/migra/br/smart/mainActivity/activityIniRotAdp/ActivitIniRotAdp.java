package migra.br.smart.mainActivity.activityIniRotAdp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.rota.Rota;

/**
 * Created by droidr2d2 on 19/01/2017.
 */
public class ActivitIniRotAdp extends BaseAdapter {

    ArrayList<Rota> lista;
    Context ctx;

    public ActivitIniRotAdp(Context ctx, ArrayList<Rota> lista){
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Rota getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.rota_adap, null);

        Rota rt = getItem(position);

        final TextView tvAtvIniRotAdpCod = (TextView)v.findViewById(R.id.tvAtvIniRotAdpCod);
        tvAtvIniRotAdpCod.setText(String.valueOf(rt.getCodigo()));

        final TextView tvAtvIniRotAdpescrRota = (TextView)v.findViewById(R.id.tvAtvIniRotAdpescrRota);
        tvAtvIniRotAdpescrRota.setText(rt.getDescricao());

        if(position % 2 == 0){
            v.setBackgroundColor(Color.LTGRAY);
        }else{
            v.setBackgroundColor(Color.WHITE);
        }

        return v;
    }
}