package framework3d.window;

import javax.swing.JFrame;

import java.awt.*;
// import java.awt.event.*;
// import java.awt.image.BufferStrategy;



public class WindowHandler 
{
    /******** Dimensioni finestra e titolo *****/
    
    private int width;
    private int height;

    private String title;

    /******************************** */

    /**********Gestione finestra ******* */
    
    private JFrame frame;
    private Canvas screen;
    //private BufferStrategy buffer;

    /********************************** */


    public WindowHandler(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    //DA CONTROLLARE L'ORDINE CON CUI VENGONO ESEGUITE QUESTE OPERAZIONI.
    public void initializeWindow()
    {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initializeCanvas();

        frame.add(screen);
        frame.pack();

        screen.createBufferStrategy(3);
    }

    //Da rivedere cosa fanno le funzioni 
    private void initializeCanvas()
    {
        screen = new Canvas();
        var dimension = new Dimension(width, height);
        screen.setPreferredSize(dimension);
        screen.setMaximumSize(dimension);
        screen.setMinimumSize(dimension);
        screen.setFocusable(false);
    }


    /**************************************** GET ********************************************* */
    
    public Canvas getScreenCanvas()
    {
        return screen;
    }


    public JFrame getWindowFrame()
    {
        return frame;
    }   


    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    /**************************************** FINE GET ********************************************* */

}