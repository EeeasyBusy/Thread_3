import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger countThree = new AtomicInteger(0);
    public static AtomicInteger countFour = new AtomicInteger(0);
    public static AtomicInteger countFive = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String text : texts) {
                StringBuilder str = new StringBuilder(text);
                String invertText = str.reverse().toString();
                if (text.equals(invertText)) {
                    if (text.length() == 3) {
                        countThree.getAndIncrement();
                    } else if (text.length() == 4) {
                        countFour.getAndIncrement();
                    } else if (text.length() == 5) {
                        countFive.getAndIncrement();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (b1(text)) {
                    if (text.length() == 3) {
                        countThree.getAndIncrement();
                    } else if (text.length() == 4) {
                        countFour.getAndIncrement();
                    } else if (text.length() == 5) {
                        countFive.getAndIncrement();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (b2(text)) {
                    if (text.length() == 3) {
                        countThree.getAndIncrement();
                    } else if (text.length() == 4) {
                        countFour.getAndIncrement();
                    } else if (text.length() == 5) {
                        countFive.getAndIncrement();
                    }
                }
            }
        }).start();

        System.out.println("Красивых слов с длиной 3: " + countThree);
        System.out.println("Красивых слов с длиной 4: " + countFour);
        System.out.println("Красивых слов с длиной 5: " + countFive);
    }

    public static boolean b2(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean b1(String text) {
        char ch = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != ch) {
                return false;
            }
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}