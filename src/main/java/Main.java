import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    static int[] prices = {100, 200, 300};

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        XMLPreviewReader preview = new XMLPreviewReader(new File("shop.xml"));
        File loadFile = new File(preview.loadFile);
        File saveFile = new File(preview.saveFile);
        File logFile = new File(preview.logFile);
        //File saveFile = new File("basket.json");
        Basket basket = createBasket(loadFile, preview.isLoad, preview.loadFormat);
        ClientLog log = new ClientLog();

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        //main.java.Basket basket=new main.java.Basket(products,prices);
        //Basket basket = null;
        /*if (saveFile.exists()) {
            basket = Basket.loadFromJSONFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }*/
        while (true) {
            System.out.println("Выберите товар и количество или введите 'end' ");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                if (preview.isLog) {
                    log.exportAsCSV(logFile);//сохраняем лог
                }
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            int currentPrice = prices[productNumber];
            System.out.println("Вы добавили:");
            System.out.println((productNumber + 1) + ". " + products[productNumber] + " - " + productCount + "шт. на сумму: " + currentPrice * productCount + " руб.");
            basket.addToCart(productNumber, productCount);
            if (preview.isLog) {
                log.log(productNumber, productCount);
            }
            if (preview.isSave) {
                switch (preview.saveFormat) {
                    case "json" -> basket.saveJSON(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }
        }
        basket.printCart();
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJSONFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(products, prices);
            };
        } else {
            basket = new Basket(products, prices);
        }
        return basket;
    }
}
