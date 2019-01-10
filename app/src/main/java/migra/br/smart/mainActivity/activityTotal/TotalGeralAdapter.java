package migra.br.smart.mainActivity.activityTotal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemLista;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;

/**
 * Created by droidr2d2 on 07/03/2017.
 */
public class TotalGeralAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<Pedido> list;

    public TotalGeralAdapter(Context ctx, ArrayList<Pedido> list){
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Pedido getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.ped_total_adp, null);

        final TextView tvDataPedidoTotal = (TextView) v.findViewById(R.id.tvDataPedidoTotal);
        final TextView tvHoraPedidoTotal = (TextView) v.findViewById(R.id.tvHoraPedidoTotal);
        final TextView tvValorPedidoTotal = (TextView) v.findViewById(R.id.tvValorPedidoTotal);

        Pedido pedido = getItem(position);
        ArrayList<ItemLista> arrItlist = new ArrayList<ItemLista>();
        double total = 0;
        try {

            arrItlist = new ListaPedidoRN(ctx).getItemLista(pedido.getId());
            for(ItemLista item: arrItlist){
                total += item.getTotal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tvValorPedidoTotal.setText(String.valueOf(total));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(pedido.getDataInicio());
        tvDataPedidoTotal.setText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));

        tvHoraPedidoTotal.setText(pedido.getHoraInicio());



        return v;
    }
}