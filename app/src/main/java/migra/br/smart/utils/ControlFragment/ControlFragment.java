package migra.br.smart.utils.ControlFragment;

import java.util.ArrayList;

/**
 * Created by droidr2d2 on 28/10/2016.
 */
public class ControlFragment {
    private static boolean activeProd;
    private static boolean activeListaPedidos;
    private static boolean activePedido;
    private static boolean activeLinha;
    private static boolean activeClientFrag;
    private static boolean activeJustifica;
    private static boolean activeHistoriFg;
    private static boolean activeRotaFg;

    /****************************PARA USO DE RETORNO DE ACTIVITY***********************************/
    public static final int R_FG_HISTORICO = 1;
    public static final int R_FG_CLIENTE = 2;
    public static final int R_FG_PEDIDO = 3;
    public static final int R_FG_PRODUTOS = 4;
    public static final int R_FG_LISTA_PEDIDO = 5;
    public static final int R_ATV_CALCS_PED = 6;
    /****************************PARA USO DE RETORNO DE ACTIVITY***********************************/

    /****************************TAGS PARA OS FRAGMENTS********************************************/
    public static String tagFgHistorico = "historiFg";
    /****************************TAGS PARA OS FRAGMENTS********************************************/

    private static ArrayList<Object> atvResult;

    static{
        atvResult = new ArrayList<Object>();
    }

    public static ArrayList<Object> getAtvResult() {
        return atvResult;
    }

    public static void setAtvResult(ArrayList<Object> atvResult) {
        ControlFragment.atvResult = atvResult;
    }

    public static boolean isActiveRotaFg() {
        return activeRotaFg;
    }

    public static void setActiveRotaFg(boolean activeRotaFg) {
        ControlFragment.activeRotaFg = activeRotaFg;
    }

    public static boolean isActiveHistoriFg() {
        return activeHistoriFg;
    }

    public static boolean isActiveJustifica() {
        return activeJustifica;
    }

    public static void setActiveJustifica(boolean activeJustifica) {
        ControlFragment.activeJustifica = activeJustifica;
    }

    public static boolean isActiveLinha() {
        return activeLinha;
    }

    public static boolean isActiveClientFrag() {
        return activeClientFrag;
    }

    public static void setActiveClientFrag(boolean activeClientFrag) {
        ControlFragment.activeClientFrag = activeClientFrag;
    }

    public static boolean isActiveListaPedidos() {
        return activeListaPedidos;
    }

    public static void setActiveListaPedidos(boolean activeListaPedidos) {
        ControlFragment.activeListaPedidos = activeListaPedidos;
    }

    public static boolean isActiveProd() {
        return activeProd;
    }

    public static boolean getActiveLinha() {
        return activeLinha;
    }

    public static void setActiveLinha(boolean activeLinha) {
        ControlFragment.activeLinha = activeLinha;
    }

    public static void setActiveProd(boolean status){
        ControlFragment.activeProd = status;
    }

    public static boolean isActivePedido() {
        return activePedido;
    }

    public static void setActivePedido(boolean status){
        ControlFragment.activePedido = status;
    }

    public static void setActiveHistoriFg(boolean b) {
        ControlFragment.activeHistoriFg = b;
    }


}
