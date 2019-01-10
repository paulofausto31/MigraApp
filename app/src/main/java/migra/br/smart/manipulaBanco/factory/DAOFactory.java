/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.factory;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

import migra.br.smart.manipulaBanco.entidades.Preco.PrecoDAO;
import migra.br.smart.manipulaBanco.entidades.Preco.PrecoTransaction;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoDAO;
import migra.br.smart.manipulaBanco.entidades.SaldoEstoque.SaldoEstoqueDAO;
import migra.br.smart.manipulaBanco.entidades.SaldoEstoque.SaldoEstoqueTransaction;
import migra.br.smart.manipulaBanco.entidades.comodato.ComodatoDAO;
import migra.br.smart.manipulaBanco.entidades.comodato.ComodatoTransaction;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalDAO;
import migra.br.smart.manipulaBanco.entidades.configLocal.ConfigLocalTransaction;
import migra.br.smart.manipulaBanco.entidades.Produto.ProdutoTransaction;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoDAO;
import migra.br.smart.manipulaBanco.entidades.configuracao.ConfiguracaoTransaction;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContRecebDAO;
import migra.br.smart.manipulaBanco.entidades.contasReceber.ContRecebTransaction;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaDAO;
import migra.br.smart.manipulaBanco.entidades.empresas.EmpresaTransaction;
import migra.br.smart.manipulaBanco.entidades.erro.ErroDAO;
import migra.br.smart.manipulaBanco.entidades.erro.ErroTransaction;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgmentDAO;
import migra.br.smart.manipulaBanco.entidades.formaPagamento.FormPgmentTransaction;
import migra.br.smart.manipulaBanco.entidades.fornecedor.FornecedorDAO;
import migra.br.smart.manipulaBanco.entidades.fornecedor.FornecedorTransaction;
import migra.br.smart.manipulaBanco.entidades.justificativa.JustificativaDAO;
import migra.br.smart.manipulaBanco.entidades.justificativa.JustificativaTransaction;
import migra.br.smart.manipulaBanco.entidades.linha.LinhaDAO;
import migra.br.smart.manipulaBanco.entidades.linha.LinhaTransaction;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteDAO;
import migra.br.smart.manipulaBanco.entidades.cliente.ClienteTransaction;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoDAO;
import migra.br.smart.manipulaBanco.entidades.listaPedido.ListaPedidoTransaction;
import migra.br.smart.manipulaBanco.entidades.motivo.MotivoDAO;
import migra.br.smart.manipulaBanco.entidades.motivo.MotivoTransaction;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaDAO;
import migra.br.smart.manipulaBanco.entidades.itemLista.ItemListaTransaction;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoDAO;
import migra.br.smart.manipulaBanco.entidades.pedido.PedidoTransaction;
import migra.br.smart.manipulaBanco.entidades.registro.RegistroDAO;
import migra.br.smart.manipulaBanco.entidades.registro.RegistroTransaction;
import migra.br.smart.manipulaBanco.entidades.rota.RotaDAO;
import migra.br.smart.manipulaBanco.entidades.rota.RotaTransaction;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitDAO;
import migra.br.smart.manipulaBanco.entidades.seqVisit.SeqVisitTransaction;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusDAO;
import migra.br.smart.manipulaBanco.entidades.seqVisitStatus.SeqVisitStatusTransaction;
import migra.br.smart.manipulaBanco.entidades.valTotPed.ValTotPedDAO;
import migra.br.smart.manipulaBanco.entidades.valTotPed.ValTotPedTransaction;
import migra.br.smart.manipulaBanco.entidades.vendedor.VendedorDAO;
import migra.br.smart.manipulaBanco.entidades.vendedor.VendedorTransaction;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoDAO;
import migra.br.smart.manipulaBanco.entidades.negativacao.NegativacaoTransaction;

/**
 *
 * @author ydxpaj
 */
public class DAOFactory {
    
    public ProdutoDAO criaProduto(Context ctx){
        ProdutoTransaction fseProdTransaction = new ProdutoTransaction();
    
        try {
            fseProdTransaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException ex) {
            Log.e("banco", ex.getMessage());
        }
        
        return fseProdTransaction;
    }

    public ClienteDAO criaSGEClie(Context ctx) {
        ClienteTransaction sgeClieTransaction = new ClienteTransaction();
        try {
            sgeClieTransaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            Log.e("banco", e.getMessage());
        }

        return sgeClieTransaction;
    }

    public ConfigLocalDAO criaConLocal(Context ctx) {
        ConfigLocalTransaction conLocalTransaction = new ConfigLocalTransaction();
        try {
            conLocalTransaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conLocalTransaction;
    }

    public VendedorDAO criaVendedor(Context ctx) {
        VendedorTransaction transaction = new VendedorTransaction();
        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public PrecoDAO criaPreco(Context ctx) {
        PrecoTransaction transaction = new PrecoTransaction();
        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public FornecedorDAO criaFornecedor(Context ctx) {
        FornecedorTransaction transaction = new FornecedorTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public LinhaDAO criaLinhaDAO(Context ctx) {
        LinhaTransaction transaction = new LinhaTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public FormPgmentDAO criaformPgmentDAO(Context ctx) {
        FormPgmentTransaction transaction = new FormPgmentTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public ContRecebDAO criacontRecebDAO(Context ctx) {
        ContRecebTransaction transaction = new ContRecebTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public MotivoDAO criaMotivoDAO(Context ctx) {
        MotivoTransaction transaction = new MotivoTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public ConfiguracaoDAO criaConfiguracao(Context ctx) {
        ConfiguracaoTransaction transaction = new ConfiguracaoTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public ItemListaDAO criaItemListaDAO(Context ctx) {

        ItemListaTransaction transaction = new ItemListaTransaction();

        try {
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public PedidoDAO criaPedidoDAO(Context ctx) {
        PedidoTransaction pedidoTransaction = new PedidoTransaction();

        try {
            pedidoTransaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidoTransaction;
    }

    public ListaPedidoDAO criaListaPedidoDAO(Context ctx) {
        ListaPedidoTransaction listaPedidoTransaction = new ListaPedidoTransaction();

        try {
            listaPedidoTransaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPedidoTransaction;
    }

    public SaldoEstoqueDAO criaSaldoEstoqueDAO(Context ctx) {
        SaldoEstoqueTransaction transaction = new SaldoEstoqueTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            Log.e("erro", ex.getMessage());
        }
        return transaction;
    }

    public RotaDAO criaRotaDAO(Context ctx) {
        RotaTransaction transaction = new RotaTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            Log.e("erro", ex.getMessage());
        }
        return transaction;
    }

    public SeqVisitDAO criaSeqVisitDAO(Context ctx) {
        SeqVisitTransaction transaction = new SeqVisitTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            Log.e("erro", ex.getMessage());
        }
        return transaction;
    }

    public RegistroDAO criaRegistro(Context ctx) {
        RegistroTransaction transaction = new RegistroTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return transaction;
    }

    public JustificativaDAO criaJustificaDAO(Context ctx) {
        JustificativaTransaction transaction = new JustificativaTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return transaction;
    }

    public ValTotPedDAO criaValTotPedDAO(Context ctx) {
        ValTotPedTransaction transaction = new ValTotPedTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return transaction;
    }

    public ComodatoDAO criaComodatoDAO(Context ctx) {
        ComodatoTransaction transaction = new ComodatoTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return transaction;
    }

    public NegativacaoDAO criaNegativacaoDAO(Context ctx) {
        NegativacaoTransaction transaction = new NegativacaoTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return transaction;
    }

    public ErroDAO erroDAO(Context ctx) {
        ErroTransaction transaction = new ErroTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return transaction;
    }

    public SeqVisitStatusDAO criaSeqVisitStatusDAO(Context ctx) {
        SeqVisitStatusTransaction transaction = new SeqVisitStatusTransaction();
        try{
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return transaction;
    }

    public EmpresaDAO criaEmpresaDAO(Context ctx) {
        EmpresaTransaction transaction = new EmpresaTransaction();
        try{
            //transaction.setConnection(new FactoryConnection(ctx, "fil1").getConnection());
            transaction.setConnection(new FactoryConnection(ctx).getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}