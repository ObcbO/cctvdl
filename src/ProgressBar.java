import java.text.DecimalFormat;

public class ProgressBar {
    private int length; // 进度条最大值
    private DecimalFormat decimalFormat = new DecimalFormat("##%");// ##.00%

    public ProgressBar(int set) {
        length = set;
    }
    public ProgressBar() {
    }

    public void setmax(int set) {
        length = set;
    }

    public synchronized void print(int now) {
        if (now > length)
            return;
        for (int i = 0; i < length + 6; i++) {// 2本身 百分数6
            System.out.print("\b");
        }
        System.out.print(decimalFormat.format((float) now / (float) length * 1.0) + " ");
        System.out.print("[");
        for (int i = 0; i < now - 1; i++) {
            System.out.print("-");
        }
        if (now != 0) {
            System.out.print(">");
        }
        for (int i = 0; i < length - now; i++) {
            System.out.print("_");
        }
        System.out.print("]");
    }
}
