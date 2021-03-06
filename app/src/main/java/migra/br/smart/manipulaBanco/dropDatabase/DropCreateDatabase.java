package migra.br.smart.manipulaBanco.dropDatabase;

/**
 * Created by droidr2d2 on 20/10/2016.
 */
public class DropCreateDatabase {
    private final String[] SCRIPT_TABLE_DELETE = new String[]{
            "drop table if exists fornecedor",
            "drop table if exists linha",
            "drop table if exists preco",
            "drop table if exists cliente",
            "drop table if exists vendedor",
            "drop table if exists formPgment",
            "drop table if exists contaReceb",
            "drop table if exists motivo",
            "drop table if exists configuracao",
            //"drop table if exists pedido",
            //"drop table if exists ItemLista",
            //"drop table if exists listaPedido ",
            "drop table if exists saldEstoq ",//saldo de estoque
            "drop table if exists rota ",//rota
            "drop table if exists seqVisit ",//seq visit
            "drop table if exists produto",
            "drop table if exists justificativa",
            //"drop table if exists negativacao",
            "drop table if exists comodato",
            "drop table if exists empresas"
    };

    private static final String[]SCRIPT_CREATE_TABLE = new String[]{
            "CREATE TABLE produto ("+
                    "id integer primary key autoincrement,"+
                    "codigo text default '0', "+
                    "codigoBarras text , "+
                    "nome text DEFAULT '',"+
                    "unidadeVenda text DEFAULT '', "+
                    "linha text DEFAULT '',"+
                    "subLinha text DEFAULT '',"+
                    "grupo text DEFAULT '',"+
                    "pVenda1 text DEFAULT '',"+
                    "pVenda2 text DEFAULT '',"+
                    "pVenda3 text DEFAULT '',"+
                    "pVenda4 text DEFAULT '',"+
                    "pVenda5 text DEFAULT '',"+
                    "pVenda6 text DEFAULT '',"+
                    "pVenda7 text DEFAULT '',"+
                    "pVenda8 text DEFAULT '',"+
                    "pVenda9 text DEFAULT '',"+
                    "pVendSelect text default '', "+
            /*"aliqIpi double, "+
            "aplicacao text DEFAULT '',"+
            "cest text DEFAULT '',"+
            "cfop text DEFAULT '',"+
            "codAnp text DEFAULT '',"+*/
                    "descontoMaximo text DEFAULT '',"+
                    //"exIPI text DEFAULT '',"+
                    "vendeFracionado text DEFAULT '',"+//permissao para fracionar o produto S/N
                    //"genero text DEFAULT '',"+
                    "icmsCompra text DEFAULT '',"+
                    //"irpj text DEFAULT '',"+
                    //"pis text DEFAULT '',"+
                    "controlaLote text DEFAULT '',"+
                    "marca text DEFAULT '',"+
                    "modelo text DEFAULT '',"+
                    //"mva text DEFAULT '',"+
                    //"controleNumeroSerie text DEFAULT '',"+
                    //"origem text DEFAULT '',"+
                    "pesoBruto text DEFAULT '',"+
                    "pesoLiq text DEFAULT '',"+
                    //"cst text DEFAULT '',"+
                    "tipo text DEFAULT '',"+
                    //"codTributacao text DEFAULT '',"+
                    "rzVendaMult text DEFAULT '',"+//razão de venda em multiplo
                    //"precoCompra text DEFAULT '',"+//preço de compra
                    "saldo double default '0', "+
                    //"fornecedor text DEFAULT '',"+
                    "unidadeArmazena double default '0', " +//unidade de armazenamento

                    "promo1 text not null default '0', "+
                    "promo2 text not null default '0', "+
                    "promo3 text not null default '0', "+
                    "promo4 text not null default '0', "+
                    "promo5 text not null default '0', "+
                    "promo6 text not null default '0', "+
                    "promo7 text not null default '0', "+
                    "promo8 text not null default '0', "+
                    "promo9 text not null default '0', "+
                    "valPromo long not null default '0',"+

                    "unArmazena text not null default '', "+//unidade de armazenamento
                    "qtdArmazena double not null default '1', "+//upgrade versao 6 quantidade armazenada no pacote, caixa, etc

                    "FOREIGN KEY (linha) REFERENCES linha(codigo) "+
                    // "FOREIGN KEY (fornecedor) REFERENCES fornecedor(codigo)"+
                    ");",
            "create table saldEstoq(" +
                    "id integer primary key autoincrement, deposito integer, " +
                    "produto text, saldo double"+
                    ");",
            "create table fornecedor(" +
                    "id integer primary key autoincrement, codigo text, nome text, razaoSocial text" +
                    ");",
            "create table linha(" +
                    "id integer primary key autoincrement, " +
                    "codigo text,"+
                    "descricao text, "+
                    "comissao1 text, "+
                    "comissao2 text, "+
                    "foto text, "+
                    "controlaLote "+
                    ");",
            "create table preco(" +
                    "id integer primary key autoincrement, " +
                    "codProd double, " +
                    "valor double, " +
                    "vendeAVista text, " +
                    "FOREIGN KEY (codProd)"+
                    "REFERENCES produto (codigo)"+
                    ");",
            "CREATE TABLE cliente ("+
                    "id integer primary key autoincrement,"+
                    "totalAtraso double, "+
                    "codigo double DEFAULT '0',"+
                    "nome text DEFAULT '',"+
                    "razaoSocial text DEFAULT '',"+
                    "endereco text DEFAULT '',"+
                    "bairro text DEFAULT '',"+
                    "cidade text DEFAULT '',"+
                    "limitCred double DEFAULT '0.00',"+
                    "formPagmento text DEFAULT '',"+
                    "codVendedor double"+
                    "telefone text DEFAULT '',"+
                    "totalDebito double default 0, " +
                    "condicPgment text default '', " +
                    "seqVisit text default '', " +
                    "pedeHoraVisit text default '', " +
                    "ultimaCompra int, " +
                    "cnpj text default '', "+
                    "prazoPagamento long default '0', "+
                    "foreign key(codVendedor) references vendedor(codigo)"+
                    ");",

            "create table vendedor( " +
                    "id integer primary key autoincrement, " +
                    "codigo double not null default 0 ," +
                    "nome text default '');",
            "create table formPgment(" +
                    "id integer primary key autoincrement, " +
                    "codigo text, " +
                    "descricao text," +
                    "tipo text, "+
                    "prazoPadrao text, "+
                    "consideraCredito text, "+
                    "permiteDesconto text, "+
                    "juros double," +
                    "multa double"+
                    ");",
            "create table contaReceb(" +
                    "id integer primary key autoincrement, " +
                    "codVenda text, "+
                    "vendedor long, "+
                    "codCliente double," +
                    " numTitulo text," +
                    " datEmissao text," +//date
                    " dataVenci text," +//date
                    " valor double, " +
                    "codFormPgment text," +//id do tipo de pagamento
                    "vOriginal double not null default '0', "+//valor original antes de receber baixa de qualquer valor
                    "FOREIGN KEY (codCliente) REFERENCES cliente(codigo)," +
                    "FOREIGN KEY (codFormPgment) REFERENCES formPgment(codigo)"+
                    ");",
            "create table motivo(" +
                    "id integer primary key autoincrement, " +
                    "codigo long," +
                    "descricao text);",
            "create table configuracao(" +
                    "id integer primary key autoincrement, " +
                    "descont_max text," +
                    "filtrEstoq integer default '1', "+//filtrar estoque, possiveis valores: 1, 2, 3
                    //"da_descont text," +
                    //"num_do_user text," +
                    "key text," +
                    "data_expira long default 0, "+
                    "ultima_data long default 0, "+//ultima data registrada
                    "quant_max_parc integer," +
                    "data_carga text," +//date
                    "hora_carga text," +//time
                    "mensagem text, " +
                    "venderDiaSemana text default 'n', " +
                    "prazoMaxGeral long default '0'"+
                    ");",
         /*   "create table pedido(" +//PEDIDOS
                    "id integer primary key autoincrement, " +
                    "codCli double, "+
                    "datInici long, " +
                    "horaInic text, " +
                    "datFim long, " +
                    "horaFim text, " +
                    "qtParcela long, " +
                    "prazo long, " +
                    "total double not null default '0', "+
                    "codFormpg text not null default 'DIN', " +//codigo forma de pagamento
                    // "uploaded text default 'n', "+//true se já foi transmitido//ELIMINAR ESTA COLUNA, A COLUNA status SUBSTITUI ESSA
                    "status text default 'Aberto', "+//possíveis status: aberto, fechado, transmitido
                    "del text default 'n', "+

                    "latitudeInicio double default '0', "+//local no inicio da venda
                    "longitudeInicio double default '0', " +//local no inicio da venda
                    "latitudeFim double  default '0', " +//local no fim da venda
                    "longitudeFim double  default '0', " +//local no fim da venda
                    "latitudeTransmit double  defautl '0', " +//local na transmissao
                    "longitudeTransmit double  default '0', "+//local na transmissao

                    "seqVist_id integer NOT NULL, "+
                    "rota long NOT NULL, "+
                    //"cliente double NOT NULL, "+

                    "FOREIGN KEY (seqVist_id , rota , codCli) "+
                    "REFERENCES seqVist (id , rota , cliente) "+

                    "foreign key (codFormpg) references formPgment(codigo) " +
                    ");",*/
           /* "create table ItemLista(" +//PRODUTO VENDIDO
                    "id integer primary key, " +
                    "codProd text, " +
                    "qtd long, " +
                    "total double, " +
                    "deletar text default 'n', "+
                    "pVendSelect long default '0', "+
                    "descAcrePercent double default 0, " +//porcentagem
                    "descAcreMone double default 0, " +//valor monetário
                    "foreign key (codProd) references produto(codigo)" +
                    ");",*/
          /*  "create table listaPedido(" +//LISTA DE PEDIDOS
                    "id integer primary key autoincrement, " +
                    "idPedido integer not null, " +
                    "rota long not null, "+
                    "codVendedor double not null, " +
                    "codCli double not null, " +
                    "idItem long not null, " +
                    "deletar text not null default 'n', "+//marca para deletar ou nao

                    "seqVist_id integer NOT NULL, "+

                    "FOREIGN KEY (idPedido , seqVist_id , rota , codCli) "+
                    "REFERENCES pedido (id , seqVist_id , rota , codCli), "+

                    "foreign key (codVendedor) references vendedor(codigo), " +
                    "foreign key (idItem) references ItemLista(id)"+

                    ");",*/
            "create table rota(" +
                    "id integer primary key autoincrement, "+
                    "codigo long, " +
                    "descricao text, " +
                    "vendedor double, "+
                    "diaVender text"+
            ");",
            "create table seqVisit(" +
                    "id integer primary key autoincrement, "+
                    "rota long, " +
                    "cliente double, " +//fk
                    "seqVisit long, "+//sequencia de visita
                    "status not null default '', "+
                    "foreign key(rota) references rota(codigo), "+
                    "foreign key(cliente) references cliente(codigo)"+
            ");",
            "create table justificativa(" +
                    "id integer primary key autoincrement, " +
                    "codigo text not null, " +
                    "descricao text not null" +
            ");",
            /*
            "create table negativacao(" +
                    "id integer primary key autoincrement, " +
                    "data long not null, " +
                    "hora text not null," +
                    "latitude double not null, " +
                    "longitude double not null, " +
                    "seqVist_id integer not null, " +//fk for id seqVisita
                    "rota long not null, " +
                    "cliente double not null, " +
                    "justificativa text not null, " +
                    "status text default 'Espera', "+//Espera, Transmitido
                    "foreign key(seqVist_id, rota, cliente) references seqVisita(id, rota, cliente)," +
                    "foreign key(justificativa) references justificativa(codigo)"+
                    ");",*/
            "create table comodato(" +
                    "id integer primary key autoincrement, " +
                    "produto text not null, " +
                    "saldo double not null, " +
                    "cliente double not null, " +
                    "foreign key(cliente) references cliente(codigo), " +
                    "foreign key(produto) references produto(codigo)" +
            ");",
            "create table empresas(" +
                    " id integer primary key autoincrement, " +
                    " cnpj text not null, " +
                    " fantasia text not null, " +
                    " rzSocial text not null, " +
                    " db text default 'sistemasit', " +
                    " tipo text default 'f' " +
                    ");"
    };

    public static String[] getScriptCreateTable() {
        return SCRIPT_CREATE_TABLE;
    }

    public String[] getSCRIPT_TABLE_DELETE() {
        return SCRIPT_TABLE_DELETE;
    }
}
