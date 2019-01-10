package migra.br.smart.mainActivity.activityTotal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import migra.br.smart.R;
import migra.br.smart.TaskNetWork.transferOperations.Operations;
import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;

/**
 * Created by droidr2d2 on 07/03/2017.
 */
public class TotalActivity extends Activity {
    Button btCliente;
    ImageButton btCalendarPedTotal;
    TextView tvCodCliPedTot, tvNomeCliPedTotalFrag;
    EditText tvDataPedidoTotal;
    Spinner spinStatusPed;

    static final int R_ATV_CLI_LTV = 0;//retorno da activity ClienteListView

    ListView lvTotal;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ped_total_fg);

        btCalendarPedTotal = (ImageButton)findViewById(R.id.btCalendarPedTotal);
        tvDataPedidoTotal = (EditText)findViewById(R.id.tvDataPedidoTotal);
        tvCodCliPedTot = (TextView)findViewById(R.id.tvCodCliPedTot);
        tvNomeCliPedTotalFrag = (TextView) findViewById(R.id.tvNomeCliPedTotalFrag);
        spinStatusPed = (Spinner)findViewById(R.id.spinStatusPed);
        btCliente = (Button) findViewById(R.id.btCliente);

        btCliente.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                /*Intent it = new Intent(Operations.ABRIR_LISTA_CLIENTE);
                it.putExtra("rTheselectCli", "yes");//envia controle de retorno do cliente selecionado
                startActivityForResult(it, R_ATV_CLI_LTV);*/

                Intent it = new Intent(Operations.ACTIVITY_CONTAINER_FRAGMENTS);
                it.putExtra("openFrag", "cliFrag");//identifica o fragment a exibir
                //Comodato rota = (Comodato)parent.getAdapter().getItem(position);
                //CurrentInfo.codCli = rota.getCodProd();
//                    CurrentInfo.cnpjCliente = cnpj;
                Log.i("CODIGO_CLI", CurrentInfo.codCli+"");
                //it.setAction(Operations.ACTIVITY_CONTAINER_FRAGMENTS);
                it.putExtra("codRota", 0);
                startActivity(it);
            }
        });

        String[] itensSpi = new String[]{"TODOS", "NÃO ENVIADO", "ENVIADO"};
        ArrayAdapter<String> arrAdaptSpin = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, itensSpi);
        spinStatusPed.setAdapter(arrAdaptSpin);

        lvTotal = (ListView) findViewById(R.id.lv_total);
        ArrayList<Pedido> list = null;
        try {
            filtrar();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private void filtrar() throws SQLException{
        Pedido pedFiltro = new Pedido();
        if(spinStatusPed.getSelectedItemId() == 0){//todos
            pedFiltro.setStatus("");
        }else if(spinStatusPed.getSelectedItemId() == 1){
            pedFiltro.setStatus("n");
        }else if(spinStatusPed.getSelectedItemId() == 2){
            pedFiltro.setStatus("s");
        }

        pedFiltro.setDataInicio(Long.parseLong(tvDataPedidoTotal.getText().toString()));
        if(!tvCodCliPedTot.getText().toString().equals("")){
            pedFiltro.setCodCli(Double.parseDouble(tvCodCliPedTot.getText().toString()));
        }else{
            pedFiltro.setCodCli(0);
        }
        ArrayList<Pedido> list = new PedidoRN(this).filtrar(pedFiltro);
        lvTotal.setAdapter(new TotalGeralAdapter(this, list));
    }

    protected void onActivityResult(int requestCode, int resultCod, Intent data){
        if(resultCod == R_ATV_CLI_LTV){
            if(data != null){
                tvCodCliPedTot.setText(data.getStringExtra("codCli"));
                tvNomeCliPedTotalFrag.setText(data.getStringExtra("nomeCli"));

                try {
                    filtrar();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}