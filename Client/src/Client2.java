import java.io.*;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) {
        /* /////////////////////////////////////////////////////////
         * Для подключения Клиента к Серверу нам нужно знать две вещи:
         * 1 - ip адрес, к которому подключаемся
         * 2 - порт, в который будем стучаться
         * Также для чтения данных от Сервера и передачи ему данных,
         * нужно создать BufferedReader и BufferedWriter
         *//////////////////////////////////////////////////////////
        try (Socket socket = new Socket("127.0.0.1", 8000);
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()))) {
            System.out.println("Connected to server.");
            /* /////////////////////////////////////////////////////////
             * Отправим запрос на Сервер.
             *//////////////////////////////////////////////////////////
            String request = "Visaginas ";
            System.out.println("Request: " + request);
            writer.write(request);
            writer.newLine();
            writer.flush();

            /* /////////////////////////////////////////////////////////
             * Прочитаем ответ от Сервера.
             *//////////////////////////////////////////////////////////
            String response = reader.readLine();
            System.out.println("Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
