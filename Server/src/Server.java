import ru.pimalex1978.Phone;

import java.io.IOException;
import java.net.ServerSocket;

/*
 * В классе Server2 много комментариев для понятия работы Сервера.
 * Сперва ознакомся с ними.
 * В этом классе Server их уже меньше, чтобы не путаться.
 * Используем здесь класс-обертку Phone. Для сранения посмотри
 * на Server2.
 * Проверку запуска Сервера можно произвести через браузел или telnet
 * < telnet 127.0.0.1 8000 >
 *
 * В нашем примере Сервер будет получать от Клиента название города
 * и будет возвращать как бы температуру в этом городе (рандомное число).
 *
 * Сервер будет многопоточным. В многопоточности будет задействована часть,
 * которая крутится в бесконечном цикле.
 * В потоке будет обработка запроса от Клиента.
 * */
public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started!");
            while (true) {
                /*ОБъект Phone будем создавать просто в try,  а не в
                 * try-with-resources*/
//                try (Phone phone = new Phone(server)) {
                try {
                    Phone phone = new Phone(server);
                    /*Создаем поток и запускаем его.*/
                    new Thread(
                            () -> {
                                /*Сначала Сервер читает сообщение от Клиента, строчку*/
                                String request = phone.readLine();
                                /*Выводим в консоль что нам приходит от Клиента*/
                                System.out.println("Request: " + request);

                                /*Отвечаем Клиенту*/
                                /*В ответ шлем случайное целое число, как бы температуру, приведенное к String*/
                                String response = (int) (Math.random() * 40 - 10) + " \u2103"; //" \u2103" - символ градус цельсия
                                /*Сделаем задержку, как бы погода долго вычисляется.*/
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                                System.out.println("Response: " + response);
                                phone.writeLine(response);
                                /*Закроем наш телефон*/
                                try {
                                    phone.close(); //Закрываем объект phone
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            //Программа будет завершена с выбросом RuntimeException
            throw new RuntimeException(e);
        }
    }
}
