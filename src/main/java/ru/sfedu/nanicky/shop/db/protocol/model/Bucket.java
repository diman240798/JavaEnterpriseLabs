package ru.sfedu.nanicky.shop.db.protocol.model;

import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Arrays;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Bucket extends IdEntity {
    private String session;
    private String products = "";

    public static final String PRODUCTS_SEPARATOR = ":";
    private static final String PRODUCT_SEPATOR = ", ";


    public Bucket(int id, String session) {
        setId(id);
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public List<String> getProducts() {
        return Arrays.asList(products.split(PRODUCT_SEPATOR));
    }

    public boolean addProduct(int productId, Category category, BaseDao productDao) {
        if (productDao.getById(productId) != null) {
            if (products.length() > 0) {
                products += PRODUCT_SEPATOR + category + PRODUCTS_SEPARATOR + productId;
            } else {
                products = category + PRODUCTS_SEPARATOR + productId;
            }
            return true;
        }
        return false;
    }


}