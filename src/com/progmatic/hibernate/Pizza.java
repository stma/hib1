package com.progmatic.hibernate;


import jakarta.persistence.*;

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @Column(name = "pazon")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pnev")
    private String name;

    @Column(name = "par"/*, nullable=false, unique=true, length=11*/)
    private Integer price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
