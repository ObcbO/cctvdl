import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class App {
    static ProgressBar pb = new ProgressBar();
    
    static int num;
    static String all;
    static int i = 1;// 当前要下载的分断视频
    static int done = 0;// 当前已下载

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入分断数: ");
        num = sc.nextInt();

        System.out.print("请输入第一个链接: ");
        all = sc.next();

        sc.close();

        all = all.substring(0, all.length() - 5);

        System.out.println("开始下载分断视频");
        pb.setmax(num);
        pb.print(0);
        while (i < num) {
            new Thread(() -> download(all + i + ".mp4", i)).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            i++;
        }
        while (done < num);
    }

    private static void download(String link, int list) {
        new File(System.getProperty("user.dir") + "/videos").mkdir();
        String filename = list < 10 ? "0" + list + ".mp4" : list + ".mp4";
        try (BufferedInputStream in = new BufferedInputStream(new URL(link).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "/videos/" + filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            pb.print(++done);
        } catch (IOException e) {
            System.err.println("ERROR: " + filename + " 下载失败" + e.getLocalizedMessage());
            for (int i = 1; i < num; i++) {
                new File(i + ".mp4").delete();
            }
            System.exit(1);
        }
    }
}
