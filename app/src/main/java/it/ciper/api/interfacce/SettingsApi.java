package it.ciper.api.interfacce;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface SettingsApi {
    /*String serverDomain = "http://192.168.1.107:5000";    server Pi*/
    String serverDomain = "http://172.16.213.132:5000";      /*vm*/
    ExecutorService executor = Executors.newWorkStealingPool();
    String clientToken = "f0a05aabd049f6cb62f425bc793534fe24161de60199a6295944a9f2f52b8ff6";

    static String listToString(List<String> list){
        StringBuffer buf = new StringBuffer();
        buf.append("[ ");
        list.stream().forEach(s->buf.append("\""+s+"\", "));
        return buf.append(" ] ").toString();
    }
}
