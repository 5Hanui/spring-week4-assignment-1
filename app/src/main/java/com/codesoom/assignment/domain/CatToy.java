package com.codesoom.assignment.domain;

import com.codesoom.assignment.exception.CatToyInvalidPriceException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * 고양이 장난감 객체로 장난감에 대한 정보를 담는다.
 */

@Entity
public class CatToy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    public CatToy() {}

    public CatToy(String name, String maker, Long price, String imageUrl) {
       this(null, name, maker, price, imageUrl);
    }

    public CatToy(Long id, String name, String maker, Long price, String imageUrl) {
        isValidPrice(price);

        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void isValidPrice(Long price) {
        if (price < 0) {
            throw new CatToyInvalidPriceException(price);
        }
    }

    public CatToy(Builder builder) {
        name = builder.name;
        maker = builder.maker;
        price = builder.price;
        imageUrl = builder.imageUrl;
    }

    public static CatToy of(String name, String maker, Long price, String imageUrl){
        return new CatToy(name, maker, price, imageUrl);
    }

    public static Builder builder(){
        return new Builder();
    }

    public void update(CatToy target) {
        if (updatableString(target.name)) {
            name = target.name;
        }

        if (updatableString(target.maker)) {
            maker = target.maker;
        }

        if (updatableString(target.imageUrl)) {
            imageUrl = target.imageUrl;
        }

        isValidPrice(target.price);

        price = target.price;
    }

    private boolean updatableString(String source) {
        return source != null && !source.isEmpty();
    }

    public static class Builder {
        private String name;
        private String maker;
        private Long price;
        private String imageUrl;

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder maker(String maker){
            this.maker = maker;
            return this;
        }
        public Builder price(Long price){
            this.price = price;
            return this;
        }
        public Builder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public CatToy build() {
            return new CatToy(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatToy)) return false;
        CatToy catToy = (CatToy) o;
        return Objects.equals(id, catToy.id) && Objects.equals(name, catToy.name) && Objects.equals(maker, catToy.maker) && Objects.equals(price, catToy.price) && Objects.equals(imageUrl, catToy.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maker, price, imageUrl);
    }
}
