package migra.br.smart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import migra.br.smart.currentInfo.CurrentInfo;
import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;

public class Obs extends AppCompatActivity {

    private Button btPedidoObs;
    private EditText edtPedidoObs;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_obs);

        btPedidoObs = (Button) findViewById(R.id.btPedidoObs);
        edtPedidoObs = (EditText) findViewById(R.id.edtPedidoObs);

        btPedidoObs.setOnClickListener(new SaveUpdate());

        try {
            pedido = new PedidoRN(this).getForId(CurrentInfo.idPedido);
            Log.i("OBS", CurrentInfo.idPedido+"--"+pedido.getObs());
            if(pedido != null){
                Log.i("OBS", pedido.getCliente().getFantasia());
                edtPedidoObs.setText(pedido.getObs());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class SaveUpdate implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if(pedido != null){
                    pedido.setObs(edtPedidoObs.getText().toString());
                    new PedidoRN(Obs.this).update(pedido);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
