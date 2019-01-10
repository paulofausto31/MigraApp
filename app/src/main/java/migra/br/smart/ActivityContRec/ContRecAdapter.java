package migra.br.smart.ActivityContRec;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import migra.br.smart.R;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContReceb;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgment;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgmentRN;

/**
 * Created by ARMIGRA on 10/09/2016.
 */
public class ContRecAdapter extends BaseAdapter {

    ArrayList<ContReceb> lista;
    Context ctx;

    ContRecAdapter(Context ctx, ArrayList<ContReceb> lista){
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
        ContReceb contReceb = lista.get(position);

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_contas_receber, null);

        TextView tvContRecNomeCli = (TextView) v.findViewById(R.id.tvContRecNomeCli);
        tvContRecNomeCli.setText(contReceb.getCliente().getFantasia());

        TextView tvContRecCodCli = (TextView) v.findViewById(R.id.tvContRecCodCli);
        tvContRecCodCli.setText(String.valueOf(contReceb.getCodCliente()));

        TextView tvContRecTitulo = (TextView) v.findViewById(R.id.tvContRecTitulo);
        tvContRecTitulo.setText(contReceb.getNumTitulo());

        TextView tvContRecTitul = (TextView) v.findViewById(R.id.tvContRecEmissao);
        StringBuilder dataEmissao = new StringBuilder(contReceb.getDataEmissao());
        dataEmissao.insert(2, "/");
        dataEmissao.insert(5, "/");
        tvContRecTitul.setText(dataEmissao.toString());

        TextView tvContRecVenci = (TextView) v.findViewById(R.id.tvContRecVenci);
        StringBuilder dataVenci = new StringBuilder(contReceb.getDataVenci());
        dataVenci.insert(2, "/");
        dataVenci.insert(5, "/");
        tvContRecVenci.setText(dataVenci.toString());

        TextView tvContRecValor = (TextView) v.findViewById(R.id.tvContRecValor);
        tvContRecValor.setText(String.valueOf(contReceb.getValor()));

        TextView tvContRecFormPg = (TextView) v.findViewById(R.id.tvContRecFormPg);
        tvContRecFormPg.setText(contReceb.getFormPg());

        TextView tvContJuros = (TextView) v.findViewById(R.id.tvContJuros);
        TextView tvContMulta = (TextView) v.findViewById(R.id.tvContMulta);
        TextView tvContTotal = (TextView) v.findViewById(R.id.tvContTotal);
        FormPgment formPg = new FormPgment();
        formPg.setCodigo(contReceb.getFormPg());
        try {
            ArrayList<FormPgment> lFormPg = new FormPgmentRN(ctx).filtrarForCod(formPg);
            if(lFormPg != null && lFormPg.size() > 0){

                /*********************CALCULA JUROS POR MES****************************/
                int subMeses = 0;
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();//hoje
                String[] arrData = dataVenci.toString().split("/");

                c1.set(Integer.parseInt(arrData[2]), Integer.parseInt(arrData[1])-1, Integer.parseInt(arrData[0]));
                /*
                if(c2.get(Calendar.MONTH) > c1.get(Calendar.MONTH)){
                    subMeses = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
                }else if(c2.get(Calendar.MONTH) < c1.get(Calendar.MONTH)){
                    subMeses = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
                }

                int totalMeses = (c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12 + subMeses;

                if(c2.get(Calendar.DAY_OF_MONTH) <= c1.get(Calendar.DAY_OF_MONTH)){
                    --totalMeses;
                }
                */

                long totDias = c2.getTimeInMillis()-c1.getTimeInMillis();
                totDias = totDias/(1000*60*60*24);
                /*********************CALCULA JUROS POR MES****************************/

                //double juros = tottalMeses*(contReceb.getValor()*(lFormPg.get(0).getJurus()/100));

                double juros = 0;
                double multa = 0;
                double vOrig = contReceb.getValor();

                if(contReceb.getvOriginal() > 0){
                    vOrig = contReceb.getvOriginal();
                }

                Log.i("RRR", contReceb.getvOriginal()+"");

                if(c2.getTimeInMillis() > c1.getTimeInMillis()) {
                    //juros = totDias * ((contReceb.getValor() * (lFormPg.get(0).getJurus() / 100))/30);
                    juros = totDias * ((vOrig * (lFormPg.get(0).getJurus() / 100))/30);

                    //multa = (contReceb.getValor() * (lFormPg.get(0).getMulta() / 100));
                    multa = (vOrig * (lFormPg.get(0).getMulta() / 100));
                }
                tvContJuros.setText(String.format("%.2f", juros));

                tvContMulta.setText(String.format("%.2f", multa));

                tvContTotal.setText(String.format("%.2f", juros+multa+contReceb.getValor()));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        String[] camposData = dataVenci.toString().split("/");
        Calendar cVenci = Calendar.getInstance();
        cVenci.set(Integer.parseInt(camposData[2]), Integer.parseInt(camposData[1])-1, Integer.parseInt(camposData[0]));

        int auxCor1 = Color.LTGRAY;
        int auxCor2 = Color.WHITE;

        if(c.getTimeInMillis() > cVenci.getTimeInMillis() ){
            auxCor1 = Color.RED;
            auxCor2 = Color.RED;
        }

        if(position % 2 == 0) {
            v.setBackgroundColor(auxCor2);
        }else{
            v.setBackgroundColor(auxCor1);
        }

        return v;
    }
}