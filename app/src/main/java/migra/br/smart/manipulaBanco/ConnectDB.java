/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco;

import java.io.Serializable;

/**
 *
 * @author ydxpaj
 */
public class ConnectDB implements Serializable{
    private static String ip;
    private static String porta;
    private static String banco;
    private static String usuario;
    private static String senha;

    public ConnectDB(){
        setIp("localhost");

        setBanco("/sistemasit");
        setUsuario("root");
        setSenha("123456");
    }
    
    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        ConnectDB.ip = ip;
    }

    public static String getPorta() {
        return porta;
    }

    public static void setPorta(String porta) {
        ConnectDB.porta = porta;
    }

    public static String getBanco() {
        return banco;
    }

    public static void setBanco(String banco) {
        ConnectDB.banco = banco;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        ConnectDB.usuario = usuario;
    }

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        ConnectDB.senha = senha;
    }
    
    
}
