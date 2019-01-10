package migra.br.smart.utils.typeExtraTransfer;

/**
 * Created by r2d2 on 3/27/18.
 */

public class TypeTransfer {
    private static boolean INTERNO;
    private static boolean WEB_SERVICE;
    private static boolean LOCAL;

    public static boolean isINTERNO() {
        return INTERNO;
    }

    public static void setINTERNO(boolean INTERNO) {
        TypeTransfer.INTERNO = INTERNO;
    }

    public static boolean isWebService() {
        return WEB_SERVICE;
    }

    public static void setWebService(boolean webService) {
        WEB_SERVICE = webService;
    }

    public static boolean isLOCAL() {
        return LOCAL;
    }

    public static void setLOCAL(boolean LOCAL) {
        TypeTransfer.LOCAL = LOCAL;
    }
}
