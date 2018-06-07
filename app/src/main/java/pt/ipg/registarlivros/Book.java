package pt.ipg.registarlivros;

import java.util.Date;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class Book {

   /* public Book(String nome,String Categoria,int PL,String Estado) {

        this.nome=nome;
        this.categoria=Categoria;
        this.PaginasLidas=PL;
        this.Estado=Estado;
    }*/

    private String title;
    private int idwriter;
    private int id;
    private String discription;
    private String state;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdwriter() {
        return idwriter;
    }

    public void setIdwriter(int idwriter) {
        this.idwriter = idwriter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    // private int PaginasLidas;
  //  private String categoria;
  //  private String Estado;
   // private static Date date;




}
