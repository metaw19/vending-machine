import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class VendingMachine1 {
	final int SIZE = 11;
	private Dsipenser1[] dispenser = new Dsipenser1[SIZE];
	public int machineID;
	private FoodInfo1 food;
	private String itemName;
	private double price;
	private String nutriFacts;
	private String itemType;
	private int quantity;
	private double money;
	private String file;
	private int itemSold;
	private String menu;
	private StringBuilder sb;

	public VendingMachine1(String file, int mID) {
		this.sb = new StringBuilder();
		// run main menu
		this.file = file;
		this.machineID = mID;

		// display options
		JFrame f = new JFrame("Vending Machine Application");
		f.setSize(400, 150);
		Container content = f.getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new FlowLayout());
		JButton btn1 = new JButton("Begin Transaction");
		content.add(btn1);
		JButton btn2 = new JButton("Receipt");
		content.add(btn2);
		// f.addWindowListener(new ExitListener());
		f.setVisible(true);
		// check response
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startTrans();
			}
		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printAllReceipts();
				openPrintable();
				
			}
		});

	}

	

	public void openPrintable() {
		// prints the results to an alert window

		// read from a file
		BufferedReader br = null;
		String outputStuff = "";

		try {

			String sCurrentLine;
			br = new BufferedReader(new FileReader("receipts.txt"));

			while ((sCurrentLine = br.readLine()) != null) {

				outputStuff += sCurrentLine + "\n";

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		JOptionPane.showMessageDialog(null, outputStuff);
	
	}

	public void startTrans() {
		

		this.menu = "";
		
		int i = 0, j = 0;
		while (j == 0) {
			i = 0;

			System.out.println("");
			readInventoy();
			System.out.println("");

			while (i == 0) {
				i = selectItem();
				
				if (i == -1) {
					j = 0;
					i = 1;
				}
				
				else {
					j = 1;
				}
			}

		}
	
	}

	public void printAllReceipts() {
		try {

			File file = new File("receipts.txt");

			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(this.sb.toString());
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	

	private void inputMoney() {
		
		String number;

		number = JOptionPane.showInputDialog("Please insert money:");
		this.money = Double.parseDouble(number);
		if (money > 0) {
			System.out.println(money + " has been accepted");
		} else {
			System.out.println("Please try again.");
			inputMoney();
		}
	}

	private void readInventoy() {
		 
		String fileName = "inventroy1.txt";
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.print("Cannot open the file!");
			System.exit(0);
		}
		String line[] = new String[SIZE * 5];
		int numItem = 0;
		int i = 0;
		while (fileInput.hasNextLine()) {
			line[i] = fileInput.nextLine();
			i++;
		}

		numItem = ((i + 1) / 5);

		while (numItem > 0) {
			itemName = line[(5 * numItem) - 5];
			price = Double.parseDouble(line[(5 * numItem) - 4]);
			nutriFacts = line[(5 * numItem) - 3];
			quantity = Integer.parseInt(line[(5 * numItem) - 2]);
			itemSold = Integer.parseInt(line[(5 * numItem) - 1]);
			food = new FoodInfo1(price, itemName, nutriFacts, itemType);
			dispenser[numItem - 1] = new Dsipenser1(quantity, itemSold, food);
			this.menu += food + " " + "Quantity "
					+ dispenser[numItem - 1].getQuantity() + "\n";
			numItem--;

		}

	}

	
	private int selectItem() {
		 
		int selection;
		String option;

		option = JOptionPane.showInputDialog("Our Current Menu:\n" + this.menu
				+ "Enter your choice 0-9...(Choices are in reverse order)");
		selection = Integer.parseInt(option);
		if (selection == 0) {
			System.out.println("Option " + selection + " has been selected");
			 {
				inputMoney();
				try {

					if (money < dispenser[selection].foodItem.price)
						throw new Exception("Exception: Not enough money");
				} catch (Exception e) {
					System.out.println("Money returned " + money);
					System.out.println("Please insert money and try again.");
					return -1;
				}
			}

			try {
				if (dispenser[selection].quantity < 1)
					throw new Exception("Exception: No product is left");
			} catch (Exception e) {
				System.out.println("Please make a new selection");
				return 0;
			}
			// recipt
			this.sb.append("");
			this.sb.append("RECIPT\n");
			this.sb.append("Item Selected: "
					+ dispenser[selection].foodItem.name + "\n");
			this.sb.append("Price: " + dispenser[selection].foodItem.price
					+ "\n");
			this.sb.append("Money Inserted: " + money + "\n");
			this.sb.append("Change"
					+ (money - dispenser[selection].foodItem.price) + "\n");
			this.sb.append("\n");
			dispenser[selection]
					.setQuantity(dispenser[selection].getQuantity());
			dispenser[selection]
					.setItemSold(dispenser[selection].getItemSold());
			writeTotalSales();
			writeInventory();
		} else if (selection > 0 && selection < 10) {
			System.out.println("Option " + selection + " has been selected");
			 {
				inputMoney();
				try {

					if (money < dispenser[selection].foodItem.price)
						throw new Exception("Exception: Not enough money");
				} catch (Exception e) {
					System.out.println("Money returned " + money);
					System.out.println("Please insert money and try again.");
					return -1;
				}
			}

			try {
				if (dispenser[selection].quantity < 1)
					throw new Exception("Exception: No product is left");
			} catch (Exception e) {
				System.out.println("Please make a new selection");
				return 0;
			}
			// recipt
			this.sb.append("");
			this.sb.append("RECIPT\n");
			this.sb.append("Item Selected: "
					+ dispenser[selection].foodItem.name + "\n");
			this.sb.append("Price: " + dispenser[selection].foodItem.price
					+ "\n");
			this.sb.append("Money Inserted: " + money + "\n");
			this.sb.append("Change"
					+ (money - dispenser[selection].foodItem.price) + "\n");
			this.sb.append("\n");
			dispenser[selection]
					.setQuantity(dispenser[selection].getQuantity());
			dispenser[selection]
					.setItemSold(dispenser[selection].getItemSold());
			writeTotalSales();
			writeInventory();
		} else {
			System.out.println("Please try again.");
			return 0;
		}
		return 1;
	}
		

	private void writeTotalSales() {
		 
		Date d = new Date();
		String fileName = d.toString().replace(':', '_'); // The name could be
															// read from
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter("Machine" + this.machineID + "_"
					+ fileName);
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + "Machine"
					+ this.machineID + "_" + fileName);
			System.exit(0);
		}
		for (int count = 0; count <= 9; count++) {

			// sales total
			outputStream.println("Name " + dispenser[count].foodItem.name);
			outputStream.println("Number Sold " + dispenser[count].itemSold);
			outputStream.println("Sales Total " + dispenser[count].itemSold
					* dispenser[count].foodItem.price);
			// quantity
			outputStream.println("Quantity " + dispenser[count].quantity);
			 
		}
		 
		outputStream.println("Subtotal");
		outputStream.println(dispenser[0].itemSold
				* dispenser[0].foodItem.price + dispenser[1].itemSold
				* dispenser[1].foodItem.price + dispenser[2].itemSold
				* dispenser[2].foodItem.price + dispenser[3].itemSold
				* dispenser[3].foodItem.price + dispenser[4].itemSold
				* dispenser[4].foodItem.price + dispenser[5].itemSold
				* dispenser[5].foodItem.price + dispenser[6].itemSold
				* dispenser[6].foodItem.price + dispenser[7].itemSold
				* dispenser[7].foodItem.price + dispenser[8].itemSold
				* dispenser[8].foodItem.price + dispenser[9].itemSold
				* dispenser[9].foodItem.price);
		outputStream.close();
		System.out.println("Current total sales were written to " + "Machine"
				+ this.machineID + "_" + fileName);
	}

	private void writeInventory() {
		 

		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + file);
			System.exit(0);
		}
		for (int count = 0; count <= 9; count++) {
			outputStream.println(dispenser[count].foodItem.name);
			outputStream.println(dispenser[count].foodItem.price);
			outputStream.println(dispenser[count].foodItem.nutiFacts);
			outputStream.println(dispenser[count].getQuantity());
			outputStream.println(dispenser[count].getItemSold());
		}
		outputStream.close();
		System.out.println("Inventory Updated.");

	}

	public void setFile(String file) {
		this.file = file;
	}

}