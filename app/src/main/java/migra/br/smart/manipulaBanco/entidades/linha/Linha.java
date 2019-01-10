/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migra.br.smart.manipulaBanco.entidades.linha;

/**
 *
 * @author ydxpaj
 */
public class Linha {
    private long id;
    private String codigo;
    private String descricao;
    private String comissao1;
    private String comissao2;
    private String foto;
    private String controlaLote;

    public Linha(){
        setDescricao("");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getComissao1() {
        return comissao1;
    }

    public void setComissao1(String comissao1) {
        this.comissao1 = comissao1;
    }

    public String getComissao2() {
        return comissao2;
    }

    public void setComissao2(String comissao2) {
        this.comissao2 = comissao2;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getControlaLote() {
        return controlaLote;
    }

    public void setControlaLote(String controlaLote) {
        this.controlaLote = controlaLote;
    }
}
