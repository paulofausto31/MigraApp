package migra.br.smart.ActivityCliente.fragmentJustifcativa;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.utils.data.Data;

/**
 * Created by root on 04/10/17.
 */

public class JustificaAdp extends BaseAdapter {

    List<Negativacao> list;
    Context ctx;

    public JustificaAdp(Context ctx, ArrayList<Negativacao> list){
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
        View v = inflater.inflate(R.layout.justifica_adp, null);

        final Negativacao neg = (Negativacao) getItem(position);

        TextView tvNomeCliNegativFgAdp = (TextView) v.findViewById(R.id.tvNomeCliNegativFgAdp);
        TextView tvRZSociNegativFgAdp = (TextView) v.findViewById(R.id.tvRZSociNegativFgAdp);
        TextView tvCodCliJustifAdp = (TextView) v.findViewById(R.id.tvCodCliJustifAdp);
        TextView tvDataNegativFgAdp = (TextView) v.findViewById(R.id.tvDataNegativFgAdp);
        TextView tvDesJustificaAdp = (TextView) v.findViewById(R.id.tvDesJustificaAdp);
        CheckBox chbJustfAdpMarcar = (CheckBox) v.findViewById(R.id.chbJustfAdpMarcar);

        String[] status = neg.getStatus().split("/");
        if(status.length > 1){
            Log.i("LGSTAT", status.length+"");
        }else{
            Log.i("LGSTAT", "nnnnnnnnn");
        }

        Log.i("EMPRE", neg.getEmpresa().getCnpj());

        chbJustfAdpMarcar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked){
                        neg.setStatus(neg.getStatus()+"/select");

                    }else{
                        neg.setStatus(neg.getStatus().split("/")[0]);
                    }

                    new NegativacaoRN(ctx).update(neg);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        if(neg.getStatus().endsWith("/select")) {
            chbJustfAdpMarcar.setChecked(true);
        }else{
            chbJustfAdpMarcar.setChecked(false);
        }

        /*
        if(neg.getStatus().split("/")[1].equals("retransmit")){//marcado para retransmitir
            chbJustfAdpMarcar.isChecked();
        }*/

        tvNomeCliNegativFgAdp.setText(neg.getCliente().getFantasia());
        tvRZSociNegativFgAdp.setText(neg.getCliente().getRzSocial());
        tvCodCliJustifAdp.setText(String.valueOf(neg.getCliente().getCodigo()));

        tvDataNegativFgAdp.setText(new Data(neg.getDataInicio()).getStringData());

        tvDesJustificaAdp.setText(neg.getJustificativa().getDescricao());

        return v;
    }
}
