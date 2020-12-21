package ru.pimalex1978;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/* //////////////////////////////////////////////////////////
 * Phone - Класс-обертка созданный для удобства и практики.
 * В этом классе мы реализуем все подключения, т.е.
 * повторяющийся код в классе Server и Client.
 * Так же этот класс реализует интерфейс Closeable, для того
 * чтобы можно было использовать его в try-with-resources,
 * т.е. чтобы он мог закрывать соединения автоматически.
 * Это универсальный класс, в котором есть создание Сокетов
 * на стороне Сервера и стороне Клиента.
 * В нем есть два метода для обработки чтения сообщений и для
 * передачи сообщений. А так же метод close() для закрытия ресурсов.
 *////////////////////////////////////////////////////////////

public class Phone implements Closeable {
    /* //////////////////////////////////////////////////////////
     * Наш "телефон" работает в двух режимах:
     * отправка и получение сообщений. И его можно запустить, как
     * на Сервере, так и на Клиенте.
     * Для запуска на Клиенте нужно передать клиентский сокет, а
     * для запуска на Сервере необходимо передать ему в параметр
     * серверный сокет.
     *///////////////////////////////////////////////////////////

    /* //////////////////////////////////////////////////////////
     * Описваем в поле Сокет, с которым работаем - Socket socket.
     * Он будет final - но не такая константа, которая определена сразу
     * один раз и не меняется, а константа внутри экземпляра, т.е. значение
     * этого поля устанавливается в конструкторе и больше не меняется.
     *///////////////////////////////////////////////////////////
    private final Socket socket;

    /* //////////////////////////////////////////////////////////
     * Далее в полях описываем ридеры и врайтеры.
     * И инициализируем их в конструкторах. Но для их создания
     * для чистоты кода будем использовать отдельные методы
     * createReader() и createWriter() на
     * основе InputStream и OutputStream взятых из поля Socket socket.
     *///////////////////////////////////////////////////////////
    private final BufferedReader reader;
    private final BufferedWriter writer;

    /**
     * Конструктор клиентский. Будет использоваться для Клиента.
     *
     * @param ip   ip адрес.
     * @param port порт.
     *             Исключение пробрасывем вверх во время работы приложения.
     */
    public Phone(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Конструктор сервреный. Будет использоваться для Сервера.
     *
     * @param serverSocket серверный сокет.
     */
    public Phone(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для создания reader.
     */
    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(
                this.socket.getInputStream()));
    }

    /**
     * Метод для создания writer.
     */
    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(
                this.socket.getOutputStream()));
    }

    /* //////////////////////////////////////////////////////////
     * Далее опишем основные методы класса Phone для получения и
     * отправки сообщений: writeLine и readLine
     *///////////////////////////////////////////////////////////
    public void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush(); //пропихиваем
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
