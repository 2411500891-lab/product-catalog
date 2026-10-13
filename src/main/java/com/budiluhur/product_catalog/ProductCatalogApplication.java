package com.budiluhur.product_catalog;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.budiluhur.product_catalog.exception.ProductNotFoundException;
import com.budiluhur.product_catalog.model.Product;
import com.budiluhur.product_catalog.service.ProductService;

@SpringBootApplication
public class ProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }

    // Runner sederhana untuk menguji Injection
    @Bean
    public CommandLineRunner run(ProductService productService) {
        return args -> {
            System.out.println("=== SPRING BOOT PRODUCT CATALOG IS RUNNING ===");
            productService.createProduct(new Product("PRD-03", "Monitor Gaming 24 Inch", 2100000.0));

            System.out.println("Daftar Produk di Spring Container:");
            productService.getAllProducts().forEach(System.out::println);

            // Mini Challenge
            System.out.println("\n--- INFORMASI HARGA & DISKON ---");
            printPriceInfo(productService, "PRD-01", 10.0);
            printPriceInfo(productService, "PRD-02", 0.0);
        };
    }

    private static void printPriceInfo(ProductService service, String id, double discount)
            throws ProductNotFoundException {
        Product p = service.getProductById(id);
        double finalPrice = service.getDiscountedProductPrice(id, discount);
        Locale idn = Locale.of("id", "ID");

        System.out.println("[" + p.getId() + "] " + p.getName());
        System.out.println("- Harga Normal : " + String.format(idn, "Rp%,.2f", p.getPrice()));
        System.out.println("- Diskon       : "
                + (discount > 0 ? discount + "% (Ada Diskon)" : "0% (Tidak Ada Diskon)"));
        System.out.println("- Harga Akhir  : " + String.format(idn, "Rp%,.2f", finalPrice));
        System.out.println();
    }
}