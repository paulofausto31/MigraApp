package migra.br.smart.ActivityCliente.fragNegativacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.utils.data.Data;

/**
 * Created by r2d2 on 24/04/17.
 */

public class NegativListCliFgAdp extends BaseAdapter {
    private Context ctx;
    private List<Negativacao> list;

    private TextView tvNomeCliNegativFgAdp, tvRZSociNegativFgAdp, tvEndereNegativFgAdp,
            tvDataNegativFgAdp, tvDesRotNegativLisCliFgAdp;

    public NegativListCliFgAdp(Context ctx, List<Negativacao> list){
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
        View v = inflater.inflate(R.layout.negativacao_list_cli_fg_adpt, null);

        final Negativacao negativacao = (Negativacao) getItem(position);

        tvNomeCliNegativFgAdp = (TextView)v.findViewById(R.id.tvNomeCliNegativFgAdp);
        tvRZSociNegativFgAdp = (TextView)v.findViewById(R.id.tvRZSociNegativFgAdp);
        tvEndereNegativFgAdp = (TextView)v.findViewById(R.id.tvEndereNegativFgAdp);
        tvDataNegativFgAdp = (TextView)v.findViewById(R.id.tvDataNegativFgAdp);
        tvDesRotNegativLisCliFgAdp = (TextView)v.findViewById(R.id.tvDesRotNegativLisCliFgAdp);

        tvNomeCliNegativFgAdp.setText(negativacao.getCliente().getFantasia());
        tvRZSociNegativFgAdp.setText(negativacao.getCliente().getRzSocial());
        tvEndereNegativFgAdp.setText(negativacao.getCliente().getEndereco());
        tvDataNegativFgAdp.setText(new Data(negativacao.getDataInicio()).getStringData());
        tvDesRotNegativLisCliFgAdp.setText(negativacao.getRota().getDescricao());

        return v;
    }
}
