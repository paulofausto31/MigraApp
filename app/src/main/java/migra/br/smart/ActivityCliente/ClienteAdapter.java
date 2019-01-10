package migra.br.smart.ActivityCliente;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import migra.br.smart.R;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.cliente.Cliente;
import migra.br.smart.manipulaBanco.entidades.negativacao.Negativacao;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoRN;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisit;
import migra.br.smart.utils.Utils;

/**
 * Created by ydxpaj on 28/07/2016.
 */
public class ClienteAdapter extends BaseAdapter{
    List<SeqVisit> lista;
    //List<Cliente> lista;
    Context ctx;
    Pedido pFiltro;
    FragmentManager fragmentManager;
    double totalCurrentRot;
    //ActivityContainerFragments container;
    private Button imbBtCliPedido;

    String from;//activity ou fragment que fez a chamada

    public ClienteAdapter(Context ctx, List<Cliente> lista){
        this.ctx = ctx;
        //this.lista = lista;
    }
/**
 * @param pFiltro para filtrar os pedidos no processo de totalização
 **/
/*
    public ClienteAdapter(Context ctx, List<Cliente> lista, Pedido pFiltro){
        this.ctx = ctx;
        //this.lista = lista;
        this.pFiltro = pFiltro;
    }
/*
    public ClienteAdapter(Context ctx, List<Cliente> lista, FragmentManager fragManager){
        this.ctx = ctx;
        this.lista = lista;
        this.fragmentManager = fragManager;
    }
*/
public ClienteAdapter(Context ctx, List<SeqVisit> lista, Pedido pFiltro, String from ,FragmentManager fragManager){
    this.ctx = ctx;
    this.lista = lista;
    this.pFiltro = pFiltro;
    this.fragmentManager = fragManager;
    this.from = from;
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

        View v = inflate.inflate(R.layout.cli_adp, null);

        final ClienteFragment clienteFragment = (ClienteFragment) fragmentManager.findFragmentByTag("cliFrag");
       // clienteFragment.tvTotRotAtual.setText("0000");

        final SeqVisit seq = lista.get(position);
        final Cliente cliente = seq.getCliente();
        //final Cliente cliente = lista.get(position);

        final TextView tvNome = (TextView) v.findViewById(R.id.tvNomeClie);
        if(cliente.getFantasia().equals("")){
            tvNome.setText(cliente.getCodigo()+" "+cliente.getRzSocial());
        }
        tvNome.setText(cliente.getCodigo()+" "+cliente.getFantasia());

        final TextView tvRZSocial = (TextView) v.findViewById(R.id.tvRZSocial);
        tvRZSocial.setText(cliente.getRzSocial());

        TextView tvEndereco = (TextView) v.findViewById(R.id.tvEndereco);
        tvEndereco.setText(cliente.getEndereco());

        TextView tvCliAdpRot = (TextView)v.findViewById(R.id.tvCliAdpRot);
        tvCliAdpRot.setText(seq.getRota().getDescricao());

        final TextView tvTotCli = (TextView) v.findViewById(R.id.tvTotCli);

        if(this.pFiltro != null){
            this.pFiltro.setCodCli(cliente.getCodigo());
            Log.i("FIL", pFiltro.getStatus());
            Log.i("FIL", pFiltro.getRota()+"");
            Log.i("FIL", pFiltro.getIdFormPg());
            Log.i("FIL", pFiltro.getDataInicio()+"");
            Log.i("FIL", pFiltro.getDataFim()+"");
            try {
                if(from != null && from.equals("rotaFg")){
                    tvTotCli.setText(seq.getStatus());//pronto para mostrar status de positivacao/negativacao
                    /*
                    if(seq.getSeqVisitStatus().getStatus().equals("P")){
                        tvTotCli.setTextColor(Color.BLUE);
                    }else if(seq.getSeqVisitStatus().getStatus().equals("N")) {
                        tvTotCli.setTextColor(Color.RED);
                    }else if(seq.getSeqVisitStatus().getStatus().equals("A")){
                        tvTotCli.setTextColor(Color.MAGENTA);
                    }
                    */
                    if(seq.getStatus().equals("P")){
                        tvTotCli.setTextColor(Color.BLUE);
                    }else if(seq.getStatus().equals("N")) {
                        tvTotCli.setTextColor(Color.RED);
                    }else if(seq.getStatus().equals("A")){
                        tvTotCli.setTextColor(Color.MAGENTA);
                    }

                    Log.i("from", from);
                    /*ArrayList<Pedido> lPOpClo = new PedidoRN(ctx).getOpenOrClose(pFiltro);
                    if(lPOpClo != null && lPOpClo.size() > 0){
                        tvTotCli.setText("P");
                        tvTotCli.setTextColor(Color.BLUE);
                    }else{

                        Calendar dIni = Calendar.getInstance();
                        dIni.set(Calendar.HOUR_OF_DAY, 1); dIni.set(Calendar.MINUTE, 0); dIni.set(Calendar.SECOND, 0);
                        dIni.set(Calendar.MILLISECOND, 0);

                        Calendar dFim = Calendar.getInstance();
                        dFim.set(Calendar.HOUR_OF_DAY, 23); dFim.set(Calendar.MINUTE, 59); dFim.set(Calendar.SECOND, 59);
                        dFim.set(Calendar.MILLISECOND, 0);

                        Negativacao neg = new Negativacao();
                        neg.setCodCli(cliente.getCodigo());
                        neg.setStatus("Espera");
                        Utils.setExtraQuery(
                                " and data >= '"+dIni.getTimeInMillis()+"' and data <= '"+dFim.getTimeInMillis()+"'"
                        );
                        ArrayList<Negativacao> arrLneg = new NegativacaoRN(ctx).filtrar(neg);
                        if(arrLneg != null && arrLneg.size() > 0){
                            tvTotCli.setText("N");
                            tvTotCli.setTextColor(Color.RED);
                        }else{
                            tvTotCli.setText("");
                        }


                    }*/

                }else {
                    tvTotCli.setText("0");//configura para calcular total por cliente e nao para mostrar status de positivacao/negativacao
                    ArrayList<Pedido> lPed = new PedidoRN(ctx).filtrar(pFiltro);
                    for (int i = 0; i < lPed.size(); i++) {
                        if (lPed.get(i).getTotal() > 0) {
                            tvTotCli.setText(String.format("%.2f", lPed.get(i).getTotal() + Double.parseDouble(tvTotCli.getText().toString().replace(",", "."))));
                            totalCurrentRot += lPed.get(i).getTotal();
                        }
                    }
                }

/*
                if(clienteFragment.chTotRotAtual.isChecked()) {
                    if (clienteFragment.isUpdateTotalRotaAtual()) {
                        Log.i("PFILTRO", tvTotCli.getText().toString());
                        clienteFragment.tvTotRotAtual.setText(String.format("%.2f", totalCurrentRot / 2));
                    }
                }
                */
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Log.i("PFILTRO", "asfdsfsffsfsd");
        }

        if(position % 2 == 0) {
            v.setBackgroundColor(Color.WHITE);
        }else{
            v.setBackgroundColor(Color.LTGRAY);
        }

        return v;
    }
}
