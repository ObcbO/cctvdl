import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Get {
    private URL visitURL = null;

    public Get(String destURL, String clarity) {
        try {
            visitURL = new URL("https://www.flvcd.com/parse.php?kw=" + destURL + "&format=" + clarity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String[] visit() {
        String source = getWebSource();
        if (source.contains("只能用硕鼠下载")) {
            System.out.println("该类型只能用硕鼠软件下载");
            System.exit(0);
        }
        int start = source.indexOf("下载地址");
        int end = source.indexOf(".mp4 </a>              </td>", start);

        return process(source.substring(start, end + 4));
    }

    // 获取源代码
    private String getWebSource() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(visitURL.openStream(), "gbk"))) {// 此网站使用gbk编码
            // Thread.sleep(10000);// 等待网站 10 秒
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private String[] process(String text) {
        List<String> list = new ArrayList<>();

        int start = 0;
        int end = 0;
        // text.indexOf("......");
        while (text.contains("<a href=\"http://vod.cntv.myhwcdn.cn")) {
            start = text.indexOf("<a href=\"", start) + 9;
            end = text.indexOf("\" target=\"_blank\" >", start);
            list.add(text.substring(start, end));
            System.out.print(text.substring(start, end));
            text = text.substring(end);
        }
        return list.toArray(new String[list.size()]);
    }
}
