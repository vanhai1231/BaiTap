package com.hutech.demo.model;
import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;

    public void setImage_url(String image_url) {
        this.image_url = "/images/"+image_url;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public String getImage_url() {
        return image_url;
    }

    private String image_url ;
}