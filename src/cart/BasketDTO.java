package cart;

import javafx.beans.property.BooleanProperty;
import product.ProductDetail;

public class BasketDTO {
	private String pdCode;
	private int basketnum;
	private String id;
    private String productName;
    private String size;
    private int price;
    private String image;
    private String color;
    private double totalPrice;
    private String category;
    private String description;
    private int quantity;
    private int maxQuantity;
    private boolean isSelected;

    public BasketDTO(int basketnum, String pdCode, String id, String productName, String size, int price, String image, String color, int quantity) {
    	this.basketnum = basketnum;
        this.pdCode = pdCode;
        this.id = id;
        this.productName = productName;
        this.size = size;
        this.price = price;
        this.image = image;
        this.color = color;
        this.quantity = quantity;
    }

    public BasketDTO() {
	}
    
	public int getBasketnum() {
		return basketnum;
	}

	public void setBasketnum(int basketnum) {
		this.basketnum = basketnum;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSelected(boolean selected) {
    	isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

	public String getPdCode() {
		return pdCode;
	}

	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}
    
}