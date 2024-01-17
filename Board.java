package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	private int dots;
	private Timer timer;
	private Image apple;
	private Image dot;
	private Image head;
	
	private final int Alldots = 900;
	private final int Dotsize = 10;
	private final int RandomPosition = 29;
	
	private int apple_x;
	private int apple_y;
	
	private final int x[] = new int[Alldots];
	private final int y[] = new int[Alldots];
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	private boolean inGame = true;

	Board() 
	{
		addKeyListener(new TAdapter());
		
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300, 300));
		
		loadImages();
		initGame();
	}
	
	public void loadImages() 
	{
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
		apple = i1.getImage();
		
		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
		dot = i2.getImage();
		
		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
		head = i3.getImage();
	}
	
	public void initGame() 
	{
		dots = 3;
		for(int i=0; i<dots; i++)
		{
			y[i] = 50;
			x[i] = 50 - i * Dotsize;
		}
		
		locateApple();
		
		timer = new Timer(140, this);
		timer.start();
	}
	
	public void locateApple()
	{
		int r = (int)(Math.random() * RandomPosition);
		apple_x = r * Dotsize;
		
		r = (int)(Math.random() * RandomPosition);
		apple_y = r * Dotsize;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		if(inGame) {
		g.drawImage(apple, apple_x, apple_y, this);
		
		for(int i=0; i<dots; i++)
		{
			if(i==0)
			{
				g.drawImage(head, x[i], y[i], this);
			}
			else
			{
				g.drawImage(dot, x[i], y[i], this);
			}
		}
		
		Toolkit.getDefaultToolkit().sync();
		} 
		else {
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g) {
		String msg = "GAME OVER..";
		Font font = new Font("SAN_SERIF", Font.BOLD, 25);
		FontMetrics metrices = getFontMetrics(font);
		
		g.setColor(Color.RED);
		g.setFont(font);
		g.drawString(msg, (300 - metrices.stringWidth(msg))/2, 300/2);
	}
	
	public void move()
	{
		for(int i=dots; i>0; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if(leftDirection) {
			x[0] = x[0] - Dotsize;
		}
		
		if(rightDirection) {
			x[0] = x[0] + Dotsize;
		}
		if(upDirection) {
			y[0] = y[0] - Dotsize;
		}
		if(downDirection) {
			y[0] = y[0] + Dotsize;
		}
		
	}

	public void checkApple() {
		if((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
		}
	}
	
	public void checkCollision() {
		for(int i = dots; i > 0; i--) {
			if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
				inGame = false;
			}
		}
		
		 if(y[0] >= 300) {
			 inGame = false;
		 }
		 if(x[0] >= 300) {
			 inGame = false;
		 }
		 if(x[0] < 0) {
			 inGame = false;
		 }
		 if(y[0] < 0) {
			 inGame = false;
		 }
		 
		 
		 if(!inGame) {
			 timer.stop();
		 }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(inGame) {
		checkApple();
		checkCollision();
		move();
		}
		
		repaint();
		
	}
	
	public class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_LEFT && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key == KeyEvent.VK_UP && (!downDirection)) {
				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			if(key == KeyEvent.VK_DOWN && (!upDirection)) {
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
		}
	}

}
