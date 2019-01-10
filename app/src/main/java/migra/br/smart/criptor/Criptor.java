/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart_menu.criptor;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author droidr2d2
 */
public class Criptor {
    
    private int ultimoPrimo = 2;
    private BufferedInputStream bfr;

    public int getUltimoPrimo() {
        return ultimoPrimo;
    }

    public void setUltimoPrimo(int ultimoPrimo) {
        this.ultimoPrimo = ultimoPrimo;
    }
    
    public void writeFile(byte[] t) throws IOException{
        try {
            BufferedOutputStream bfw = new BufferedOutputStream(new FileOutputStream(new File("chave.txt")));
            bfw.write(t);            
            
            bfw.close();
        } catch (IOException ex) {
            Log.e("ERRO", ex.getMessage());
        } 
    }
    
    public byte[] readKeyFile(File f, long skip, int size) throws IOException{
        
        byte[] b = new byte[size];
        //ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            bfr = new BufferedInputStream(new FileInputStream(f));
            bfr.skip(skip);
            bfr.read(b);
            bfr.close();            
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        
        return b;
    }
    
    public byte[] decript(byte[] t, byte[] k){                
        int indexY = 0;
        
        byte[] c = k;//chave
        byte[] x = t;//texto limpo
        
        byte[] y = new byte[x.length];        
        
        for(int i = 0, j = 0; i < x.length; i++, j++){
            
            if(j == c.length){       
                j = 0;                
            }
            y[indexY] = (byte) ((c[j]^x[i]));
            y[indexY] = (byte) (~(y[indexY]));
          //  y[indexY] = (byte) (~(y[indexY]));
                        
            indexY++;
        }
        
        indexY = 0;
   
        return y;
    }
    /*****************************FUNCIONANDO*******************************
    public byte[] encript(String t, String k){                
        int indexY = 0;
        
        byte[] c = k.getBytes();//chave
        byte[] x = t.getBytes();//texto limpo
        
        byte[] y = new byte[x.length];        
        
        for(int i = 0, j = 0; i < x.length; i++, j++){
            
            if(j == c.length){       
                j = 0;                
            }
            x[i] = (byte) (~(x[i]));
            y[indexY] = (byte) ((c[j]^x[i]));
                        
            indexY++;
        }
        
        indexY = 0;
     
        return y;
    }    
    ******************************FUNCIONANDO*******************************/        
    
    /**
     * FUNCIONANDO*******************************
    public byte[] round(String t, String k){                
        int indexY = 0;
        
        byte[] c = k.getBytes();//chave
        byte[] x = t.getBytes();//texto limpo
        
        byte[] y = new byte[x.length];        
        
        for(int i = 0, j = 0; i < x.length; i++, j++){
            
            if(j == c.length){       
                j = 0;                
            }
            x[i] = (byte) (~(x[i]));
            y[indexY] = (byte) ((c[j]^x[i]));
                        
            indexY++;
        }
        
        indexY = 0;
     
        return y;
    }    
FUNCIONANDO
     */
    public byte[] preEncrip(byte[] t, byte[] generalKey){     
        
        //byte[] generalCriptedKey = generalKey.getBytes();//round(generalKey.getBytes(), generalKey.getBytes());        
        StringBuilder build = new StringBuilder(new String(generalKey));
        String reverse = build.reverse().toString();
        generalKey = round(generalKey, reverse.getBytes());
        byte[] subKey0 = Arrays.copyOfRange(t, 0, 14);
        System.out.println(new String(subKey0));
        byte[] subKey1 = Arrays.copyOfRange(t, 14, t.length-10);
        byte[] subKey2 = Arrays.copyOfRange(t, t.length-10, t.length);                        
        
        byte[] p0 = round(subKey0, subKey1);
        byte[] p2 = round(subKey2, p0);        
        byte[] p1 = round(subKey1, p2);
        
        byte[] preEncriptKey = new byte[p0.length+p1.length+p2.length];
        ByteArrayOutputStream bAOut = new ByteArrayOutputStream();
        try {
            bAOut.write(p0);
            bAOut.write(p1);
            bAOut.write(p2);
            preEncriptKey = bAOut.toByteArray();
            
            preEncriptKey = round(preEncriptKey, generalKey);
            
        } catch (IOException ex) {
            Logger.getLogger(Criptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        //byte[] preEncriptKeyResult = new byte[preEncriptKey.length+generalKey.length];
        
        //System.arraycopy(preEncriptKey, 0, preEncriptKeyResult, 0, preEncriptKey.length);
        //System.arraycopy(p1, 0, preEncripKey, p0.length, p1.length);
        //System.arraycopy(p2, 0, preEncripKey, p1.length, p2.length);                        
        
        
        return preEncriptKey;
    }
    
    public byte[] preDecript(byte[] t, byte[] generalKey){   
        StringBuilder build = new StringBuilder(new String(generalKey));
        String reverse = build.reverse().toString();
        generalKey = decript(generalKey, reverse.getBytes());
        //generalKey = round(generalKey, generalKey);
        byte[] generalCriptedKey = decript(t, generalKey);
        
        byte[] subKey0 = Arrays.copyOfRange(generalCriptedKey, 0, 14);
        byte[] subKey1 = Arrays.copyOfRange(generalCriptedKey, 14, generalCriptedKey.length-10);
        byte[] subKey2 = Arrays.copyOfRange(generalCriptedKey, generalCriptedKey.length-10, generalCriptedKey.length);
        
        byte[] p1 = decript(subKey1, subKey2);
        byte[] p2 = decript(subKey2, subKey0);
        byte[] p0 = decript(subKey0, p1);
                        
        byte[] preDecripKey = new byte[p0.length+p1.length+p2.length];
         ByteArrayOutputStream bAOut = new ByteArrayOutputStream();
        try {
            bAOut.write(p0);
            bAOut.write(p1);
            bAOut.write(p2);
            preDecripKey = bAOut.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Criptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       // System.arraycopy(p0, 0, preDecripKey, 0, p0.length);
        //System.arraycopy(p1, 0, preDecripKey, p0.length, p1.length);
        //System.arraycopy(p2, 0, preDecripKey, p1.length, p2.length);        
        
        
        /*byte[] p1 = subKeys[0].getBytes();
        byte[] p2 = subKeys[1].getBytes();
        byte[] p3 = subKeys[2].getBytes();
        
        preDecripKey = Arrays.copyOf(p1, p1.length);
        preDecripKey = Arrays.copyOf(p2, p2.length);
        preDecripKey = Arrays.copyOf(p3, p3.length);
        */
        //preDecripKey = decript(generalKey, subKeys);
        /*
        String 
        
        byte[] p1 = encript(subKeys[1], subKeys[2]);        
        byte[] p2 = encript(subKeys[0], subKeys[1]);
        byte[] p3 = encript(subKeys[2], subKeys[0]);
        
        preDecripKey = Arrays.copyOf(p1, p1.length);
        preDecripKey = Arrays.copyOf(p2, p2.length);
        preDecripKey = Arrays.copyOf(p3, p3.length);
        */
        return preDecripKey;
    }
    /*
    *GERA AS SUB CHAVES
    */
    private byte[] encriptSubKeyGenerate(byte[] key){
        byte[] subKey = new byte[key.length];
        String encripSubKey = "77777777777777";
        
        subKey = round(key, encripSubKey.getBytes());
        
        return subKey;                
    }
    
    public byte[] round(byte[] t, byte[] k){                
        int indexY = 0;        
        
        byte[] y = new byte[t.length];        
        
        for(int i = 0, j = 0; i < t.length; i++, j++){
            
            if(j == k.length){       
                j = 0;                
            }
            t[i] = (byte) (~(t[i]));
            y[indexY] = (byte) ((k[j]^t[i]));
                        
            indexY++;
        }
        
        indexY = 0;
     
        return y;
    }
    
    public byte[] encript(String t, String k){                
        int indexY = 0;
        
        String[] subKeys = t.split("/");
        
        byte[] c = k.getBytes();//chave
        byte[] x = t.getBytes();//texto limpo
        
        byte[] y = new byte[x.length];        
        
        for(int i = 0, j = 0; i < x.length; i++, j++){
            
            if(j == c.length){       
                j = 0;                
            }
            x[i] = (byte) (~(x[i]));
            y[indexY] = (byte) ((c[j]^x[i]));
                        
            indexY++;
        }
        
        indexY = 0;
     
        return y;
    }
    
    
    public String decript(String t, String k){                
        int indexY = 0;
        
        byte[] c = k.getBytes();//chave
        byte[] x = t.getBytes();//texto limpo
        
        byte[] y = new byte[x.length];        
        
        for(int i = 0, j = 0; i < x.length; i++, j++){
            
            if(j == c.length){       
                j = 0;                
            }
            x[i] = (byte) (~(x[i]));
            y[indexY] = (byte) ((c[j]^x[i]));            
                        
            indexY++;
        }
        
        indexY = 0;
        
        String s = "";
        try {
            s = new String(y, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Log.e("erroConvertString", ex.getMessage());
        }
                
        return s;
    }
    
    private byte[] interpolar(byte[] t, byte[] key){
        
        byte[] interpol = new byte[t.length+key.length];
        int index1 = 0, index2 = 0;
        for(int i = 0; i < interpol.length; i++){
            //if(i == ){
            if(i % 2 == 0){
                interpol[index1] = t[index1];
                index1++;
            }else{
                interpol[index2] = key[index2];
                index2++;
            }
            
        }
        
        return interpol;
        
    }
    
    public void writeKeyFileBytes(byte[] t, File f) throws IOException{
        File f2 = null;
        if(!f.getAbsoluteFile().toString().endsWith(".txt")){
            f2 = new File(f.getAbsoluteFile().toString()+".txt");
        }else{
            f2 = new File(f.getAbsoluteFile().toString());
        }
        BufferedOutputStream bfOS = new BufferedOutputStream(new FileOutputStream(f2, true));
        bfOS.write(t);
        bfOS.close();
    }
    
    public void writeKeyFileByte(byte[] t, File f) throws IOException{
        File f2 = null;
        if(!f.getAbsoluteFile().toString().endsWith(".txt")){
            f2 = new File(f.getAbsoluteFile().toString()+".txt");
        }else{
            f2 = new File(f.getAbsoluteFile().toString());
        }
        BufferedOutputStream bfOS = new BufferedOutputStream(new FileOutputStream(f2, true));
        bfOS.write(t);
        bfOS.close();
    }
    
    public String geraNumPrim(int qtd){
        int[] primos = new int[qtd];
        int indexPrim = 0;
        String valor = "";
        
        int totalRest = 0;
        int totalPrimos = 0;
        
        while(totalPrimos < qtd){
            for(int j = 1; j <= getUltimoPrimo(); j++){                
                if(getUltimoPrimo() % j == 0){
                    totalRest++;                    
                }  
            }
            if(totalRest == 2){
                primos[indexPrim] = getUltimoPrimo();  
                totalPrimos++;
                indexPrim++;
            }
            totalRest = 0;
            setUltimoPrimo(getUltimoPrimo()+1);
        }
        for(int i = 0; i < qtd; i++){
            valor += primos[i];
        }
                
        return valor;
    }
}