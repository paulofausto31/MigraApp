package migra.br.smart.currentInfo;

import android.content.Context;

import java.sql.SQLException;

import migra.br.smart.manipulaBanco.entidades.pedido.Pedido;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoRN;

/**
 * Created by droidr2d2 on 14/12/2016.
 */
public class CurrentInfo {
    public static double codCli;
    public static String codVendedor;
    public static long rota;
    public static long idPedido;
    public static long dataInicio;
    public static String horaInicio;
    public static long dataFim;
    public static String horaFim;
    public static double qtdParcela;
    public static long prazo;
    public static String codFormPagamento;
    public static boolean continuaPedido;
    public static boolean pedidoFinalizado;
    public static String cnpjEmpresa;
    public static String cnpjCliente;
    public static boolean finisAssincTask;
    public static String nomeCli;

    public static boolean getStatusAssincTaskProcess(){
        while(!CurrentInfo.finisAssincTask);
        return CurrentInfo.finisAssincTask;
    }

    public Pedido getCurrentPedido(Context ctx)throws SQLException {
        return new PedidoRN(ctx).getForId(CurrentInfo.idPedido);
    }
}
