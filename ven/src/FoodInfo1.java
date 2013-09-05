public class FoodInfo1 {
	double price;
	String name;
	String nutiFacts;

	

	public FoodInfo1(double price, String name, String nutiFacts, String itemType) {
		super();
		this.price = price;
		this.name = name;
		this.nutiFacts = nutiFacts;

	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNutiFacts() {
		return nutiFacts;
	}

	public void setNutiFacts(String nutiFacts) {
		this.nutiFacts = nutiFacts;
	}

	@Override
	public String toString() {
		return "FoodInfo [price=" + price + ", name=" + name + ", nutiFacts="
				+ nutiFacts + "]";
	}

}