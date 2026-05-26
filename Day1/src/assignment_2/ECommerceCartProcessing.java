package assignment_2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * E-Commerce Cart Processing
 * */
public class ECommerceCartProcessing {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product(1, "Laptop", 1000.00, true, 10, "High performance laptop"),
                new Product(2, "Smartphone", 500.00, false, 5, ""),
                new Product(3, "Headphones", 150.00, true, 20, "Noise-cancelling headphones"),
                new Product(4, "Smartwatch", 200.00, true, 15, null),
                new Product(5, "Tablet", 300.00, false, 0, "Lightweight tablet")
        );
        
        // Set coupon codes for some products
        products.get(0).setCouponCode("SAVE10");
        products.get(0).setCouponDiscountPercentage(5);
        products.get(2).setCouponCode("SALE20");
        products.get(2).setCouponDiscountPercentage(10);
        
        removeOutOfStockProducts(products);
        applyDiscountToProducts(products);
        calculateTotalCartAmount(products);
        findMostExpensiveProduct(products);
        sortProductsByPrice(products);
        displayProductsWithFinalPrice(products);
        handleNullProductDescription(products);
        applyCouponDiscount(products);
        calculateDeliveryCharges(products);
        calculateGSTOnCart(products);
        displayFinalBillSummary(products);
    }

    // 1. Remove out of stock products
    static void removeOutOfStockProducts(List<Product> products) {
        System.out.println("-------------------------------------------");
        System.out.println("Products in stock: ");
        System.out.println("--------------------------------------------");
        products.stream()
                .filter(Product::isInStock)
                .forEach(System.out::println);
    }

    // 2. Apply discount to products
    static void applyDiscountToProducts(List<Product> products) {
        System.out.println("-------------------------------------------");
        System.out.println("Products with applied discount: ");
        System.out.println("--------------------------------------------");
        products.stream()
                .map(product -> {
                    double discountedPrice = product.getPrice() * (1 - product.getDiscountPercentage() / 100.0);
                    return new Product(product.getProductId(), product.getName(), discountedPrice, product.isInStock(), product.getDiscountPercentage(), product.getDescription());
                })
                .forEach(System.out::println);
    }

    //3. Calculate total cart amount
        static void calculateTotalCartAmount(List<Product> products) {
            System.out.println("-------------------------------------------");
            System.out.println("Total cart amount after discount: ");
            System.out.println("--------------------------------------------");
            double totalAmount = products.stream()
                    .filter(Product::isInStock)
                    .mapToDouble(product -> product.getPrice() * (1 - product.getDiscountPercentage() / 100.0))
                    .sum();
            System.out.println("Total Cart Amount: $" + totalAmount);
        }

        //4. Find the most expensive product
        static void findMostExpensiveProduct(List<Product> products) {
            System.out.println("-------------------------------------------");
            System.out.println("Most expensive product after discount: ");
            System.out.println("--------------------------------------------");
            products.stream()
                    .filter(Product::isInStock)
                    .map(product -> new Product(product.getProductId(), product.getName(), product.getPrice() * (1 - product.getDiscountPercentage() / 100.0), product.isInStock(), product.getDiscountPercentage(), product.getDescription()))
                    .max(Comparator.comparingDouble(Product::getPrice))
                    .ifPresent(System.out::println);
        }

        //5. Sort all products by price irrespective of stock status
        static void sortProductsByPrice(List<Product> products) {
            System.out.println("-------------------------------------------");
            System.out.println("Products sorted by price (after discount): ");
            System.out.println("--------------------------------------------");
            products.stream()
                    .map(product -> new Product(product.getProductId(), product.getName(), product.getPrice() * (1 - product.getDiscountPercentage() / 100.0), product.isInStock(), product.getDiscountPercentage(), product.getDescription()))
                    .sorted(Comparator.comparingDouble(Product::getPrice))
                    .forEach(System.out::println);
        }

        //6. Display all products with the final discounted price
        static void displayProductsWithFinalPrice(List<Product> products) {
            System.out.println("-------------------------------------------");
            System.out.println("Products with final discounted price: ");
            System.out.println("--------------------------------------------");
            products.stream()
                    .map(product -> new Product(product.getProductId(), product.getName(), product.getPrice() * (1 - product.getDiscountPercentage() / 100.0), product.isInStock(), product.getDiscountPercentage(), product.getDescription()))
                    .forEach(System.out::println);
        }

        // 7. Handle Null product description using Optional
         static void handleNullProductDescription(List<Product> products) {
             System.out.println("-------------------------------------------");
             System.out.println("Handling null product descriptions: ");
             System.out.println("--------------------------------------------");
             products.stream()
                     .map(product -> {
                         String description = Optional.ofNullable(product.getDescription()).orElse("No description available");
                         return new Product(product.getProductId(), product.getName(), product.getPrice(), product.isInStock(), product.getDiscountPercentage(), description);
                     })
                     .forEach(System.out::println);
         }

         // 8. Apply Coupon Discount
         static void applyCouponDiscount(List<Product> products) {
             System.out.println("-------------------------------------------");
             System.out.println("Applying Coupon Discounts: ");
             System.out.println("--------------------------------------------");
             products.stream()
                     .filter(Product::isInStock)
                     .forEach(product -> {
                         if (product.getCouponCode() != null && !product.getCouponCode().isEmpty()) {
                             double priceAfterDiscount = product.getPrice() * (1 - product.getDiscountPercentage() / 100.0);
                             double priceAfterCoupon = priceAfterDiscount * (1 - product.getCouponDiscountPercentage() / 100.0);
                             double couponSavings = priceAfterDiscount - priceAfterCoupon;
                             System.out.println("Product: " + product.getName() + " | Coupon: " + product.getCouponCode() + 
                                     " | Coupon Discount: " + product.getCouponDiscountPercentage() + "% | Savings: $" + 
                                     String.format("%.2f", couponSavings) + " | Final Price: $" + String.format("%.2f", priceAfterCoupon));
                         }
                     });
         }

         // 9. Calculate Delivery Charges
         static void calculateDeliveryCharges(List<Product> products) {
             System.out.println("-------------------------------------------");
             System.out.println("Delivery Charges Calculation: ");
             System.out.println("--------------------------------------------");
             double cartTotal = products.stream()
                     .filter(Product::isInStock)
                     .mapToDouble(product -> product.getPrice() * (1 - product.getDiscountPercentage() / 100.0))
                     .sum();
             
             double deliveryCharge = cartTotal > 500 ? 0 : 10; // Free delivery for orders > $500
             System.out.println("Cart Subtotal (after discount): $" + String.format("%.2f", cartTotal));
             System.out.println("Delivery Charge: $" + String.format("%.2f", deliveryCharge));
             if (cartTotal > 500) {
                 System.out.println("Free delivery - Order is above $500!");
             }
         }

         // 10. Calculate GST on Cart
         static void calculateGSTOnCart(List<Product> products) {
             System.out.println("-------------------------------------------");
             System.out.println("GST (Goods and Services Tax) Calculation: ");
             System.out.println("--------------------------------------------");
             double cartSubtotal = products.stream()
                     .filter(Product::isInStock)
                     .mapToDouble(product -> product.getPrice() * (1 - product.getDiscountPercentage() / 100.0))
                     .sum();
             
             double cartTotalWithCoupon = products.stream()
                     .filter(Product::isInStock)
                     .mapToDouble(product -> {
                         double priceAfterDiscount = product.getPrice() * (1 - product.getDiscountPercentage() / 100.0);
                         return priceAfterDiscount * (1 - product.getCouponDiscountPercentage() / 100.0);
                     })
                     .sum();
             
             int gstPercentage = 18; // Default GST percentage
             double gstAmount = cartTotalWithCoupon * (gstPercentage / 100.0);
             double totalAfterGST = cartTotalWithCoupon + gstAmount;
             
             System.out.println("Subtotal (after product discounts): $" + String.format("%.2f", cartSubtotal));
             System.out.println("Subtotal (after coupon discounts): $" + String.format("%.2f", cartTotalWithCoupon));
             System.out.println("GST Rate: " + gstPercentage + "%");
             System.out.println("GST Amount: $" + String.format("%.2f", gstAmount));
             System.out.println("Total after GST: $" + String.format("%.2f", totalAfterGST));
         }

         // 11. Display Final Bill Summary
         static void displayFinalBillSummary(List<Product> products) {
             System.out.println("-------------------------------------------");
             System.out.println("FINAL BILL SUMMARY");
             System.out.println("-------------------------------------------");
             
             double originalPrice = products.stream()
                     .filter(Product::isInStock)
                     .mapToDouble(Product::getPrice)
                     .sum();
             
             double priceAfterProductDiscount = products.stream()
                     .filter(Product::isInStock)
                     .mapToDouble(product -> product.getPrice() * (1 - product.getDiscountPercentage() / 100.0))
                     .sum();
             
             double couponDiscountTotal = products.stream()
                     .filter(Product::isInStock)
                     .mapToDouble(product -> {
                         double priceAfterDiscount = product.getPrice() * (1 - product.getDiscountPercentage() / 100.0);
                         return priceAfterDiscount * (product.getCouponDiscountPercentage() / 100.0);
                     })
                     .sum();
             
             double cartTotal = priceAfterProductDiscount - couponDiscountTotal;
             double deliveryCharge = cartTotal > 500 ? 0 : 10;
             int gstPercentage = 18;
             double gstAmount = cartTotal * (gstPercentage / 100.0);
             double finalTotal = cartTotal + deliveryCharge + gstAmount;
             
             System.out.println("Original Price:                $" + String.format("%.2f", originalPrice));
             System.out.println("Product Discount (10-20%):     -$" + String.format("%.2f", originalPrice - priceAfterProductDiscount));
             System.out.println("Coupon Discount:               -$" + String.format("%.2f", couponDiscountTotal));
             System.out.println("Subtotal:                      $" + String.format("%.2f", cartTotal));
             System.out.println("Delivery Charges:              +$" + String.format("%.2f", deliveryCharge));
             System.out.println("GST (" + gstPercentage + "%):                    +$" + String.format("%.2f", gstAmount));
             System.out.println("-------------------------------------------");
              System.out.println("FINAL TOTAL:                   $" + String.format("%.2f", finalTotal));
              System.out.println("-------------------------------------------");
          }
}
