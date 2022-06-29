import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class App {
    static ProgressBar pb = new ProgressBar();
    static int now = 0;

    public static void main(String[] args) {
        String[] cGet = Console.get();

        Get flvcd = new Get(cGet[0], cGet[1]);
        String[] urls = flvcd.visit();
        for (String a : urls) {
            System.out.println(a);
        }

        System.out.println(urls.length);
        pb.setmax(urls.length);
        pb.print(0);
        for (String a : urls) {
            download(a);
        }
    }

    private static void download(String link) {
        String filename = System.currentTimeMillis() + ".mp4";
        new File(System.getProperty("user.dir") + "/videos").mkdir();
        try (BufferedInputStream in = new BufferedInputStream(new URL(link).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(
                        System.getProperty("user.dir") + "/videos/" + filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            pb.print(++now);
        } catch (IOException e) {
            System.err.println("ERROR: " + filename + " 下载失败" + e.getLocalizedMessage());
            System.exit(1);
        }
    }
}
