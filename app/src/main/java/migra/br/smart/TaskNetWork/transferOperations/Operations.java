package migra.br.smart.TaskNetWork.transferOperations;

/**
 * Created by ydxpaj on 13/07/2016.
 */
public class Operations {

    public static final int CLOSE_CONNECTION = 0;
    public static final int ERRO_FILE_NOT_FOUND = 1;
    public static final int ERRO_IO = 2;
    public static final int OK = 3;
    public static final int IGNORA_PEDIDO = 4;
    public static final String VERSAO_APP = "1.0.2";//VERSÃO DO APP
    public static final int CONSUL_LICENCA_CLI = 6;//para consultar licença do erp em useo pelo cliente
    public static final int START_TABPRE = 7;
    public static final int START_TABPRO = 8;
    public static final int START_TABREC = 9;
    public static final int START_TABVEN = 10;

    public static final int downloadLocal = 20;
    public static final int UPLOAD_TO_SERV_OFF = 21;
    public static final int UPLOAD_INTERNO = 22;
    public static final int update = 23;
    public static final int delete = 24;
    public static final int DOWNLOAD_INTERNET = 25;
    public static final int EXPORT_PEDIDOS_INTERNET = 26;
    public static final int DELELAR_PEDIDO = 29;
    //public static final int EXPORT_TOD_NEGATIV_INTERNET = 28;//todos os negativados
    public static final int CONSUL_SAL_PROD = 27;
    public static final int CONSUL_KEY = 28;

    public static final int CORRIGE_ITENS = 30;

    public static final String ABRIR_MENU_PRINCIAL = "ABRIR_MENU_PRINCIPAL";
    public static final String ACTIVITY_CONTAINER_FRAGMENTS = "ACTIVITY_CONTAINER_FRAGMENTS";
    public static final String ABRIR_DETALHE_PRODUTO = "ABRIR_DETALHE_PRODUTO";
    public static final String ABRIR_LISTA_CLIENTE = "ABRIR_LISTA_CLIENTE";
    public static final String ABRIR_CONFIG = "ABRIR_CONFIG";
    public static final String ABRIR_CONTAS_RECEBER = "ABRIR_CONTAS_RECEBER";
    public static final String ABRIR_VAL_TOTAL_PEDIDOS = "ABRIR_VAL_TOTAL_PEDIDOS";
    public static final String ATV_TOTALIZAR = "ABRIR_ATV_TOTALIZAR";

}
