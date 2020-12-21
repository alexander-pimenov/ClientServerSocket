import ru.pimalex1978.Phone;

import java.io.IOException;

/*
 * В классе Client2 много комментариев для понятия работы Клиента.
 * Сперва ознакомся с ними.
 * В этом классе Client их уже меньше, чтобы не путаться.
 * Используем здесь класс-обертку Phone. Для сранения посмотри
 * на Client2.
 * Проверку запуска Сервера можно произвести через браузел или telnet
 * < telnet 127.0.0.1 8000 >
 *
 * В нашем примере Сервер будет получать от Клиента название города
 * и будет возвращать как бы температуру в этом городе (рандомное число).
 * */
public class Client {
    public static void main(String[] args) {

        try (Phone phone = new Phone("127.0.0.1", 8000)) {
            System.out.println("Connected to server");
            /* Отправим запрос на Сервер. */
            String request = "Visaginas ";
            System.out.println("Request: " + request);

            phone.writeLine(request);

            /* Прочитаем ответ от Сервера. */
            String response = phone.readLine();
            /*Выведем в консоль, что пришло с Сервера.*/
            System.out.println("Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
