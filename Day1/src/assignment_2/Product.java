package assignment_2;

public class Product {
    private int productId;
    private String name;
    private double price;
    private boolean inStock;
    private int discountPercentage;
    private String description;
    private int gstPercentage;
    private String couponCode;
    private int couponDiscountPercentage;

    public Product(int productId, String name, double price, boolean inStock, int discountPercentage, String description) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.discountPercentage = discountPercentage;
        this.description = description;
        this.gstPercentage = 18; // Default GST 18%
        this.couponCode = null;
        this.couponDiscountPercentage = 0;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(int gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getCouponDiscountPercentage() {
        return couponDiscountPercentage;
    }

    public void setCouponDiscountPercentage(int couponDiscountPercentage) {
        this.couponDiscountPercentage = couponDiscountPercentage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", discountPercentage=" + discountPercentage +
                ", gstPercentage=" + gstPercentage +
                ", couponCode='" + couponCode + '\'' +
                ", couponDiscountPercentage=" + couponDiscountPercentage +
                ", description='" + description + '\'' +
                '}';
    }
}
