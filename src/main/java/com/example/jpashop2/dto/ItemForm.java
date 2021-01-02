package com.example.jpashop2.dto;


import com.example.jpashop2.domain.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemForm {
    String name;
    int price;
    int stockQuuantity;

    String sort;

    String author;
    String type;

    String artist;
    String genre;

    String director;
    String actor;

    //dto -> entity

    public Book toBook() {
        Book book = new Book();
        //new Item() 안됨. 추상클래스라서 //Item item으로 하면, book 부분 Set 안 됨
        book.setName(name);
        book.setPrice(price);
        book.setStockQuuantity(stockQuuantity);
        book.setAuthor(author);//Book
        book.setType(type);
        return book;
    }

    public Album toAlbum(){
        Album album = new Album();
        album.setName(name);
        album.setPrice(price);
        album.setStockQuuantity(stockQuuantity);
        album.setArtist(artist);//Album
        album.setGenre(genre);
        return album;
    }

    public Movie toMovie(){
        Movie movie = new Movie();
        movie.setName(name);
        movie.setPrice(price);
        movie.setStockQuuantity(stockQuuantity);
        movie.setDirector(director);//Movie
        movie.setActor(actor);
        return movie;
    }

}
