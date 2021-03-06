package ru.sbt.client;

import ru.sbt.services.Calculator;

import static java.lang.ClassLoader.getSystemClassLoader;
import static java.lang.reflect.Proxy.newProxyInstance;

public class NetClientFactory {
    private final String host;
    private final int port;

    public NetClientFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T createClient(Class<T> interfaceClass) {
        return (T) newProxyInstance(getSystemClassLoader(), new Class[]{interfaceClass}, new ClientInvocationHandler(host, port));
    }

    public static void main(String[] args) {
        NetClientFactory factory = new NetClientFactory("localhost", 5000);
        Calculator client = factory.createClient(Calculator.class);
        double calculate = client.calculate(10, 5);
        System.out.println(calculate);

        client.calculate(5, 0);
    }
}
