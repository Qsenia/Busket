import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File saveFile = new File("basket.json");
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        //main.java.Basket basket=new main.java.Basket(products,prices);
        Basket basket = null;
        if (saveFile.exists()) {
            basket = Basket.loadFromJSONFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }

ClientLog log=new ClientLog();
        while (true) {
            System.out.println("Выберите товар и количество или введите 'end' ");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                log.exportAsCSV(new File("log.csv"));//сохраняем лог
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            int currentPrice = prices[productNumber];
            System.out.println("Вы добавили:");
            System.out.println((productNumber + 1) + ". " + products[productNumber] + " - " + productCount + "шт. на сумму: " + currentPrice * productCount + " руб.");
            basket.addToCart(productNumber, productCount);
            log.log(productNumber,productCount);
            basket.saveJSON(saveFile);
        }
        basket.printCart();
    }
}
