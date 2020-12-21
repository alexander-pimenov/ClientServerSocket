import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Проверку запуска Сервера можно произвести через браузел или telnet
 * < telnet 127.0.0.1 8000 > или cUrl.
 *
 * В нашем примере Сервер будет получать от Клиента название города
 * и будет возвращать температуру в этом городе (рандомное число).
 * */
public class Server2 {
    public static void main(String[] args) {

        /* /////////////////////////////////////////////////////////
         * Порты от 0 до 1023 это системные зарезервированные порты.
         * Порт - это целое число.
         * Все остальные можно использовать нам.
         * ServerSocket поместим в try-with-resources для автоматического
         * закрывания. Это возможно, т.к. ServerSocket реализует интерфейс
         * java.io.Closeable
         * ///////////////////////////////////////////////////////// */
//        try (ServerSocket server = new ServerSocket(8000)) {
//            System.out.println("Server started!");
        /* /////////////////////////////////////////////////////////
         * В результате создания подключения у нас создаться сокет.
         * Этот объект, как телефонная трубка и мы в нее может и говорить
         * и слушать оттуда. TCP/IP позволяет это делать одновременно.
         * Получаем стрим для чтения данных socket.getInputStream() и
         * стрим для записи данных socket.getOutputStream()
         * В нашем случае нужен getOutputStream() чтобы мы могли всё
         * что хотим сообщить, такой вот автоответчик.
         * ///////////////////////////////////////////////////////// */
//            Socket socket = server.accept();
//            System.out.println("Client connected");
//        } catch (IOException e) {
        /*Обработка исключения будет в виде проброса исключения в рантайме*/
//            throw new RuntimeException(e);
//            e.printStackTrace();
//        }
//        OutputStream stream = socket.getOutputStream();
        /* /////////////////////////////////////////////////////////
         * OutputStream умеет только записывать по 1 байту или массив байт,
         * но это не удобно, поэтому из OutputStream сделаем OutputStreamWriter,
         * потому что он умеет работать со строками.
         * Получается вот такая матрешка-обертка.
         * Но сделаем еще одну обертку BufferedWriter, это будет обертка над
         * OutputStreamWriter, т.к. он еще более удобный, чем OutputStreamWriter
         * ///////////////////////////////////////////////////////// */
//        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
//        BufferedWriter writer = new BufferedWriter(streamWriter);
        /* /////////////////////////////////////////////////////////
         * Или записать еще короче:
         * ///////////////////////////////////////////////////////// */
//        BufferedWriter writer2 = new BufferedWriter(
//                new OutputStreamWriter(socket.getOutputStream()));
//        writer.write("HELLO FROM SERVER");
//        writer.newLine(); //переход на новую строчку
        /* /////////////////////////////////////////////////////////
         * Потом - flush() - нужно делать, т.к. без него команда .write() может
         * быть даже не выполнена.
         * Для примера можно закомментировать writer.flush(); потом запустить
         * telnet 127.0.0.1 8000 и увидим что на него никакого сообщения
         * "HELLO FROM SERVER" не прийдет.
         * Если буффер не флешить, то он автоматически будет флешиться когда будет
         * достигнут максимальный его размер. Или когда будет закрываться
         * соединение.
         * ///////////////////////////////////////////////////////// */
//        writer.flush();
        /*Закрываем ресурсы*/
//        writer.close();
//        socket.close();
//        server.close();

        /* /////////////////////////////////////////////////////////
         * Чтобы Сервер был постоянно включен, то поместим его в
         * бесконечеый цикл.
         * Цикл создаем в первом try, чтобы каждый раз не создавался
         * объект new ServerSocket
         * ///////////////////////////////////////////////////////// */

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = server.accept();
//                System.out.println("Client connected");

                try (
                        /*Чтобы сокет мог "говорить" передавать сообщение*/
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));
                        /*Чтобы сокет мог слушать сообщения от Клиента,
                         * и читается весь входной поток*/
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()))) {
                    /*Сначала Сервер читает сообщение от Клиента, строчку*/
                    String request = reader.readLine();
                    /*Выводим в консоль что нам приходит от Клиента*/
                    System.out.println("Request: " + request);
                    /*Отвечаем Клиенту*/
//                    String response = "HELLO FROM SERVER: " + request.length();
                    /*В ответ шлем случайное целое число, приведенное к String*/
                    String response = (int) (Math.random() * 40 - 10) + " \u2103"; //" \u2103" - символ градус цельсия
//                    String response = (int) (Math.random() * 40 - 10) + " \u00B0"; //" \u00B0" - символ градуса
                    System.out.println("Response: " + response);
                    writer.write(response);
                    writer.newLine(); //переход на новую строчку
                    writer.flush();
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
