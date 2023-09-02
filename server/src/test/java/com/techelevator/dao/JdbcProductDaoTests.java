package com.techelevator.dao;

import com.techelevator.model.Product;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class JdbcProductDaoTests extends BaseDaoTests {
    protected static final Product PRODUCT_1 = new Product(1, "SKU-001", "Product 1", "Product description 1.", new BigDecimal("14.99"), "Product001.jpg");
    protected static final Product PRODUCT_2 = new Product(2, "SKU-002", "Product 2", "Product description 2.", new BigDecimal("21.99"), "Product002.jpg");
    protected static final Product PRODUCT_3 = new Product(3, "SKU-003", "Product name 3", "Product description 3.", new BigDecimal("3.59"), "Product003.jpg");

    private JdbcProductDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    public void getAll_returns_all_products() {
        List<Product> products = dao.getAll();

        Assert.assertNotNull(products);
        Assert.assertEquals(7, products.size());
        Assert.assertEquals(PRODUCT_1, products.get(0));
    }

    @Test
    public void getById_returns_valid_user() {
        Product product = dao.getById(1);
        Assert.assertEquals(PRODUCT_1, product);
    }

    @Test
    public void getById_bad_id_returns_null() {
        Product product = dao.getById(-1);
        Assert.assertNull("Call to getById with invalid id should return NULL", product);
    }

    @Test
    public void find_all_null_returns_all_products() {
        List<Product> products = dao.find(null, null);

        Assert.assertNotNull(products);
        Assert.assertEquals(7, products.size());
        Assert.assertEquals(PRODUCT_1, products.get(0));
        Assert.assertEquals(PRODUCT_2, products.get(1));
        Assert.assertEquals(PRODUCT_3, products.get(2));
    }

    @Test
    public void find_sku_returns_correct_products() {
        List<Product> products = dao.find("SKU-001", null);

        Assert.assertNotNull(products);
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(PRODUCT_1, products.get(0));
    }

    @Test
    public void find_blank_sku_returns_all_products() {
        List<Product> products = dao.find("", null);

        Assert.assertNotNull(products);
        Assert.assertEquals(7, products.size());
    }

    @Test
    public void find_partial_sku_doesnt_match() {
        List<Product> products = dao.find("SKU", null);

        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void find_name_returns_correct_products() {
        List<Product> products = dao.find(null, "Product name 3");

        Assert.assertNotNull(products);
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(PRODUCT_3, products.get(0));
    }

    @Test
    public void find_partial_name_does_match() {
        List<Product> products = dao.find(null, "Product name");

        Assert.assertNotNull(products);
        Assert.assertEquals(5, products.size());
        Assert.assertEquals(PRODUCT_3, products.get(0));
    }

    @Test
    public void find_sku_and_name_returns_intersection() {
        List<Product> products = dao.find("SKU-003", "Product name");

        Assert.assertNotNull(products);
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(PRODUCT_3, products.get(0));
    }

    @Test
    public void find_sku_and_name_returns_intersection_empty() {
        List<Product> products = dao.find("SKU-003", "kwd");

        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }
    // find:
    //      Both null
    //      sku null, name not
    //      sku not, name null
    //      sku not, name not
//


}
