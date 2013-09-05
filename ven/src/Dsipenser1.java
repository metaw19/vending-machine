public class Dsipenser1 {
	int quantity;
	int itemSold;
	FoodInfo1 foodItem;

	
	public Dsipenser1(int quantity, int itemSold, FoodInfo1 foodItem) {
		super();
		this.quantity = quantity;
		this.foodItem = foodItem;
		this.itemSold = itemSold;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity--;
	}

	public int getItemSold() {
		return itemSold;
	}

	public void setItemSold(int itemSold) {
		this.itemSold++;
	}

	public FoodInfo1 getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodInfo1 foodItem) {
		this.foodItem = foodItem;
	}

	@Override
	public String toString() {
		return "Dispenser [quantity=" + quantity + ", itemSold=" + itemSold
				+ ", foodItem=" + foodItem + "]";
	}
}