import java.util.Scanner;

public class Console {
    public static String[] get() {
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入下载链接: ");
        String downLink = sc.next();

        System.out.print("请选择清晰度(流畅 标清 高清 超清): ");
        String clarity = sc.next();
        clarity = switch (clarity) {// 默认 标清
            case "流畅" -> "fluent";
            case "高清" -> "high";
            case "超清" -> "super";
            default -> "normal";
        };

        sc.close();

        return new String[] { downLink, clarity };
    }
}
