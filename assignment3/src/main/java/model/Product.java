package model;

public class Product {

    private Integer id;
    private String name;
    private String brand;
    private Integer quantity;
    private Float price;

    public Product() {
    }

    public Product(Integer id, String name, String brand, Integer quantity, Float price) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, String brand, Integer quantity, Float price) {
        super();
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ",name=" + name + ",brand=" + brand + ",price=" + price + ", quantity=" + quantity
                + "]";
    }

}
