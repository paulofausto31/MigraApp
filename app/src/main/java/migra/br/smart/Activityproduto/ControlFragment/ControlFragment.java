package migra.br.smart.Activityproduto.ControlFragment;

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
}
