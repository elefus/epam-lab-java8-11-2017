package lambda.part1.example.interfaces;

/**
 * 1. Методы по умолчанию предназначены для расширения существующих интерфейсов новыми возможностями.
 *
 * 2. Методы по умолчанию позволяют избежать создания служебных классов.
 *    Все необходимые методы могут быть представлены в самих интерфейсах.
 *
 * 3. Методы по умолчанию дают свободу классам выбрать метод, который нужно переопределить.
 *
 * 4. Метод по умолчанию не может переопределить метод класса java.lang.Object.
 */
public class Implementation { // implements First, Second {

    public static void main(String[] args) {
//        System.out.println(new Implementation().getValue());
    }
}
