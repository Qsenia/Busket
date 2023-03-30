import java.io.*;
import java.util.Arrays;
import java.io.ObjectOutputStream;

public class Basket {
    protected String[] products;
    protected int[] prices;
    protected int[] items;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.items = new int[products.length];// массив количество создается
        // исходя из размера массива продуктов
    }

    public Basket() {
    }

    public void addToCart(int productNum, int amount) {
        items[productNum] += amount;
        //метод добавления amount штук продукта номер productNum в корзину;
    }

    public void printCart() {
        //метод вывода на экран покупательской корзины.
        int sumPrice = 0;
        int totalPrice = 0;
        System.out.println("В вашей корзине: ");

        for (int i = 0; i < products.length; i++) {
            if (items[i] > 0) {
                sumPrice = items[i] * prices[i];
                System.out.println(products[i] + " " + items[i] + " шт " + " - " + prices[i] + "руб/шт" + ", всего: " + sumPrice + " руб");
            }
            totalPrice += sumPrice;
        }
        System.out.println("Итого: " + totalPrice + " руб.");
    }

    public void saveTxt(File textFile) throws IOException {
        //метод сохранения корзины в текстовый файл;
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products) {
                out.print(product + " ");
            }
            out.println();
            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();
            for (int item : items) {
                out.print(item + " ");
            }
        }
    }

    static Basket loadFromTxtFile(File textFile) {
        //статический(!)метод восстановления объекта корзины из текстового файла,
        // в который ранее была она сохранена;
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productLoad = bufferedReader.readLine();
            String priceLoad = bufferedReader.readLine();
            String itemLoad = bufferedReader.readLine();
            basket.products = productLoad.split(" ");
            basket.prices = Arrays.stream(priceLoad.split(" "))
                    .map(Integer::parseInt)
                    //преобразуем строки в инт
                    .mapToInt(Integer::intValue)
                    .toArray();
            basket.items = Arrays.stream(itemLoad.split(" "))
                    .map(Integer::parseInt)
                    //преобразуем строки в инт
                    .mapToInt(i -> i)
                    .toArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return basket;
    }

}
