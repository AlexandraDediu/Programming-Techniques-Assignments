package model;

public class Orders {

    private Integer id;
    private Integer customerId;
    private Integer productId;
    private Integer quantity;

    public Orders() {}
    
    public Orders(Integer idOrder, Integer customerId, Integer productId, Integer quantity) {
        this.id = idOrder;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void setId(Integer idOrder) {
        this.id = idOrder;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIdOrder() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ",customerName=" + customerId + ",productName=" + productId + ",quantity=" + quantity
                + "]";
    }

}
