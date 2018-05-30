package pt.ipg.registarlivros;

import java.util.Date;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class Livro {

    public Livro(String nome,String Categoria,int PL,String Estado) {

        this.nome=nome;
        this.categoria=Categoria;
        this.PaginasLidas=PL;
        this.Estado=Estado;
    }

    private String nome;
    private String autor;
    private int PaginasLidas;
    private String categoria;
    private String Estado;
    private static Date date;

    public static Date getDate() {
        return date;
    }

    public static void setDate(Date date) {
        Livro.date = date;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginasLidas() {
        return PaginasLidas;
    }

    public void setPaginasLidas(int paginasLidas) {
        PaginasLidas = paginasLidas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
