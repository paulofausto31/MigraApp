package migra.br.smart.historiFg;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.listaPedidoFg.ListaPedidoFragment;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.utils.data.Data;

/**
 * Created by r2d2 on 28/04/17.
 */

public class HistoriFgAdp extends BaseAdapter {
    private List<Pedido> list;
    private Context ctx;
    private FragmentManager fgMang;
    public HistoriFgAdp(Context ctx, List<Pedido> list, FragmentManager fgMang){
        this.list = list;
        this.ctx = ctx;
        this.fgMang = fgMang;
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
        View v = inflater.inflate(R.layout.histori_fg_adp, null);

        final Pedido p = (Pedido)getItem(position);

        final TextView tvHistoriFgAdpData = (TextView)v.findViewById(R.id.tvHistoriFgAdpData);
        final TextView tvHistoriFgTotal = (TextView)v.findViewById(R.id.tvHistoriFgAdpTotal);
        //TextView tvHistNomCli = (TextView)v.findViewById(R.id.tvHistNomCli);
        final TextView tvHistFgAdpHoraIni = (TextView) v.findViewById(R.id.tvHistFgAdpHoraIni);
        final TextView tvHistoriFgAdpFantasiCli = (TextView) v.findViewById(R.id.tvHistoriFgAdpFantasiCli);
        final TextView tvHistoriFgAdpRzSocCli = (TextView) v.findViewById(R.id.tvHistoriFgAdpRzSocCli);
        final TextView tvHistCodPed = (TextView)v.findViewById(R.id.tvHistFgAdpCodPed);
        final ImageButton imgBtHistFgAdpInfor = (ImageButton)v.findViewById(R.id.imgBtHistFgAdpInfor);
        final CheckBox chHistFgAdp = (CheckBox)v.findViewById(R.id.chHistFgAdp);

        tvHistoriFgAdpData.setText(new Data(p.getDataInicio()).getStringData());
        tvHistFgAdpHoraIni.setText(p.getHoraInicio());
        tvHistoriFgTotal.setText(String.format("%.2f", p.getTotal()));
        tvHistCodPed.setText(String.valueOf(p.getId()));
        //tvHistoriFgAdpFantasiCli.setText(p.getCliente().getRzSocial());
        tvHistoriFgAdpFantasiCli.setText(p.getCliente().getFantasia());
        tvHistoriFgAdpRzSocCli.setText(p.getCliente().getRzSocial());

        if(p.getDel().equals("s")){
            chHistFgAdp.setChecked(true);
        }else{
            chHistFgAdp.setChecked(false);
        }
        chHistFgAdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(chHistFgAdp.isChecked()){
                        p.setDel("s");
                    }else{
                        p.setDel("n");
                    }
                    new PedidoRN(ctx).update(p);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        imgBtHistFgAdpInfor.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                CurrentInfo.idPedido = p.getId();

                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.hist_dg_opt, null);

                AlertDialog.Builder alb = new AlertDialog.Builder(ctx);
                alb.setView(view);
                final AlertDialog alert = alb.show();

                alb.setPositiveButton("VOLTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Button btHistDgOptDetalhe = (Button)view.findViewById(R.id.btHistDgOptDetalhe);
                Button btHistDgOptReabre = (Button)view.findViewById(R.id.btHistDgOptReabre);

                btHistDgOptDetalhe.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        ListaPedidoFragment listaPedidoFragment = new ListaPedidoFragment();
                        Bundle b = new Bundle();
                        b.putString("from", "HistoriFgAdp");
                        listaPedidoFragment.setArguments(b);

                        CurrentInfo.idPedido = p.getId();
                        CurrentInfo.codCli = p.getCodCli();
                        Log.i("H_FG", CurrentInfo.codCli+"--"+CurrentInfo.idPedido);
                        FragmentTransaction fragmentTransaction = fgMang.beginTransaction();
                        fragmentTransaction.replace(R.id.pedidoContainer, listaPedidoFragment, "listPedFrag");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        alert.dismiss();
                    }
                });

                btHistDgOptReabre.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View v){
                        try {
                            new PedidoRN(ctx).duplicar();
                            Toast.makeText(ctx, "NOVO PEDIDO "+CurrentInfo.idPedido+" ABERTO", Toast.LENGTH_SHORT).show();
                            alert.dismiss();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return v;
    }
}