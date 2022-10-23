import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;

public class GameField extends JPanel  {
    private final int size = 320;
    private final int dotSize = 16;
    private final int allDots = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private  int appleY;
    private int[] x = new int[allDots];
    private int[] y = new int[allDots];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean upp = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeylistener());
        setFocusable(true);
    }
    public void initGame(){
        dots = 3;
        for(int i = 0; i<dots; i++){
            x[i] = 48 - i*dotSize;
            y[i] = 48 - i*dotSize;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GameField.this.run();
            }
        }, new Date(), 200);
        createApple();
    }
    public void createApple(){
        appleX = new Random().nextInt(20) * dotSize;
        appleY = new Random().nextInt(20) * dotSize;
    }
    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);

            }
        }else{
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str, 125, size/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= dotSize;
        }
        if(right){
            x[0] += dotSize;
        }
        if(upp){
            y[0] -= dotSize;
        }
        if(down){
            y[0] += dotSize;
        }
    }
    public void chackApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }
    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if (i>0 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
            if(x[0] > size){
                inGame = false;
            }
            if(x[0] < 0){
                inGame = false;
            }
            if(y[0] > size){
                inGame = false;
            }
            if(y[0] < 0){
                inGame = false;
            }

        }
    }


    public void run() {
        if(inGame){
            chackApple();
            checkCollisions();
            move();
        }
        repaint();
    }
    class FieldKeylistener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                upp = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                upp = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                upp = true;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !upp){
                left = false;
                right = false;
                down = true;
            }
        }
    }
}
