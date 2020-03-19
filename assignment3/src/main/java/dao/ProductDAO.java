package dao;

import java.util.List;

import model.Product;

public class ProductDAO extends AbstractDAO<Product> {

    public Product findById(int id) {
        return super.findById(id, "id");
    }

    public void insert(Product product) {
        super.insert(product);

    }

    public void update(Product p) {
        super.update(p);
    }

    public void delete(Product p) {
        super.delete(p);
    }

    public List<Product> selectAll() {
        return super.selectAll();
    }
}
