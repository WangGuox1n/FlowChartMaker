package UI;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                try {
                    new FlowChartMaker();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        );
    }
}
