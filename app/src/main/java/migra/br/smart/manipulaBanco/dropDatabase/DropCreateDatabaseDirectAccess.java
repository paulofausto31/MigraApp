/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.dropDatabase;

import android.content.Context;

import java.sql.SQLException;

import migra.br.smart.manipulaBanco.factory.FactoryConnection;


/**
 *
 * @author ydxpaj
 */
public class DropCreateDatabaseDirectAccess {

    public void dropTables(Context ctx, DropCreateDatabase drop) throws SQLException {
        String[] dropTables = drop.getSCRIPT_TABLE_DELETE();
        FactoryConnection f =  new FactoryConnection(ctx);
        for(String d:dropTables){
            f.getConnection().execSQL(d);
        }
        String[] createTables = drop.getScriptCreateTable();
        for(String d:createTables){
            f.getConnection().execSQL(d);
        }

        f.getConnection().close();
    }
}