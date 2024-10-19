package com.example.Shopping.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}