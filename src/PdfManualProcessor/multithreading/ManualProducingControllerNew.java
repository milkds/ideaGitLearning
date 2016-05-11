package PdfManualProcessor.multithreading;

import PdfManualProcessor.Manual;
import PdfManualProcessor.service.LoginHandler;
import PdfManualProcessor.service.ManualSerializer;
import org.apache.http.client.CookieStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ManualProducingControllerNew {

    public static void main(String[] args) throws InterruptedException {
       // downloadManuals(new ArrayList<Manual>(),10);
        int totalManuals = 151;  //implement according method
        int totalPages = totalManuals/10;  //implement according method
        System.out.println(totalManuals);
    }

    public static void downloadManuals(List<Manual> rawManuals, int numberOfTreads) throws InterruptedException {
        BlockingQueue<Manual> downloadingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<List<Manual>> writingQueue = new LinkedBlockingQueue<>();
        for (Manual m: rawManuals){
            downloadingQueue.put(m);
        }
        for (int i = 0; i < numberOfTreads ; i++) {
            new Thread(new ManualDownloader(downloadingQueue,writingQueue)).start();
        }
        new Thread(new ManualToFileWriter(writingQueue, ManualSerializer.getDownloadedManualFile())).start();
    }

    public static void refreshManualList() throws IOException {
        List<Manual> temp = new ArrayList<>();
        CookieStore cookieStore = LoginHandler.getCookies("","");
        int totalManuals = 151;  //implement according method
        int totalPages = totalManuals/10;  //implement according method
        if (totalManuals%10>0)totalPages++;
        for (int i = 1; i <totalPages ; i++) {
            new HtmlPageProducer(temp,cookieStore,i).run();
        }
        while (temp.size()<totalManuals){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
