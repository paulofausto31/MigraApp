package migra.br.smart.manipulaBanco.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ydxpaj on 12/07/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    String[] scriptCreate;
    String[] scriptDelete;

    public SQLiteHelper(Context ctx, String nomeBanco, int versaoBanco,
                        String[] scriptCreate, String[] scriptDelete){
        super(ctx, nomeBanco, null, versaoBanco);

        this.scriptCreate = scriptCreate;
        this.scriptDelete = scriptDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String s:scriptCreate){
            db.execSQL(s);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        Log.i("UPDT", "ATUALIZANDO BANCO");

        if(versaoAntiga < 2){
            db.execSQL(
                "alter table itemLista add column qtdUn long not null default '0'"
            );
        }
        if(versaoAntiga < 3){
            db.execSQL(
                    "create table empresas(" +
                            " id integer primary key autoincrement, " +
                            " cnpj text not null, " +
                            " fantasia text not null, " +
                            " rzSocial text not null, " +
                            " db text default 'sistemasit', " +
                            " tipo text not null default 'f' " +
                            ");"
            );
            db.execSQL(
                    " alter table pedido add column idEmpresa integer default '0'; "//empresa que recebe o pedido, palm mumti empresa
            );
            db.execSQL(
                    "alter table con_local add column multBanco default '0'; "// 0 = false, 1 = true
            );
            db.execSQL(
                    "alter table negativacao add column idEmpresa int not null default '0'; "
            );
            /*db.execSQL(
                    "alter table empresas add column db text default 'sistemasit';"
            );*/
        }
        /*
        if(versaoAntiga < 4){
            db.execSQL(
                " alter table pedido add column idEmpresa integer default '0' "//empresa que recebe o pedido, palm mumti empresa
            );
        }
        if(versaoAntiga < 5){
            db.execSQL(
                    "alter table con_local add column multBanco default '0'; "// 0 = false, 1 = true
            );
            db.execSQL(
                    "alter table empresas add column db text default 'sistemasit';"
            );
        }

        /*
        if(versaoAntiga < 2){
            db.execSQL(
                    "alter table seqVisit add column status not null default '';"//status P=positivado, N=negativado
            );
        }
        if(versaoAntiga < 3){
            db.execSQL(
                    "alter table produto add column  promo1 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo2 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo3 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo4 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo5 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo6 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo8 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column  promo9 text not null default '0';"
            );
            db.execSQL(
                    "alter table produto add column valPromo long not null default '0';"
            );

        }


        if(versaoAntiga < 4){
            db.execSQL(
                    "create table seqVisitStatus(" +//upgrade para versao 4 do banco, status das sequencias de visitas
                            " seq_id text default '', " +
                            " status text default '', " +
                            " codPed text default ''" +
                            ");"
            );
        }
        if(versaoAntiga < 5){
            db.execSQL(
                    "alter table contaReceb add column vOriginal double not null default '0';"//upgrade para versao 5 do banco, vOriginal contem valor de receber baixa de qualquer quantidade do contas a receber
            );
        }
        if(versaoAntiga < 6){
            db.execSQL("alter table produto add column qtdArmazena double not null default '1';");//upgrade versao 6 quantidade armazenada no pacote, caixa, etc
            db.execSQL("alter table produto add column unArmazena text not null default '';");//unidade de armazenamento
            //db.execSQL("alter table ItemLista add column unFrac text not null default '';");//unidade ou fracionado no retorno do pedido
            db.execSQL("alter table ItemLista change qtd qtd text default '0/0' not null;");
        }*/
    }
}
