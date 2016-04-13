package CompuChiqui;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Mouse
 */
public class JCTextArea extends JTextArea {

    private int altura_primera_hoja = 848;    
    private int altura_resto_de_hojas = 848+(16*4);    
    private int margen_izq = 30;
    private int margen_top = 0;    
    
    public JCTextArea(){
        setOpaque(true);
        //tipo  fuente la misma que en el reporte, igual el tamaño
                
//        setLineWrap(true);
        setVisible(true);
//        setContentType("text/plain");
        //margenes interiores del jtextarea
        setMargin(new Insets(margen_top, margen_izq, 0, 30));
        setWrapStyleWord(true);
        setLineWrap(true);
        
        //captura los cambios que ocurren en el jtexarea
        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void insertUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent de) {}
          
        });
        
        UndoManager manager = new UndoManager();
        getDocument().addUndoableEditListener(manager);
        
        Action undoAction = new Deshacer(manager);
        Action redoAction = new Rehacer(manager);
        
        //ASIGNAR EVENTOS DE TECLADO CTRL-Z Y CTRL-Y - DESHACER Y REHACER
        registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(
        KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
        registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(
        KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
        
    }
    
    /**
     * Se sobreescribel metodo paintComponent
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
                 
        int h = 16 + margen_top;  //16 es el tamaño en pixeles del tamaño de fuente "SansSerif 12 puntos"
        //dibuja lineas
        g.setColor( new Color(224,224,224)); 
        g.drawLine(25, 0, 25, getSize().height);
        g.drawLine(790, 0, 790, getSize().height);
        for(int i=0;i<getSize().height/16;i++)
        {
            g.drawLine(0, h, getSize().width, h);
            g.drawString(""+(i+1), 2, h-2);
            h+=16;
        }
        drawSeparadorDeHoja(g);       
      }
    
    /**
     dibuja las separaciones de cada hoja
     */
    private void drawSeparadorDeHoja( Graphics g )
    {          
        //cantidad de hojas
        int n = getSize().height / altura_primera_hoja;
//        System.out.println("cantidad hojas (n="+n+") = alto("+getSize().height+") / "+altura_primera_hoja);
        //altura hoja
        int ah= altura_primera_hoja;
//        System.out.println("Altura Hoja (ah="+ah+") = ("+altura_primera_hoja+" + "+margen_area_top+" + 16 ) ");
        //dibuja separaciones
        for(int i=1; i<=n; i++){
//            g.setColor( new Color(255,189,189,100));//rojo claro
            g.setColor( new Color(157,225,154,150));
            g.fillRect(0, ah, getSize().width, 64);//rectangulo
            g.setColor( new Color(0,0,0));            
            g.setFont(new Font("SansSerif",Font.BOLD, 35));
            g.drawString("HOJA N° " + (i+1), 30, ah+47);
//            g.setFont(new Font("Times New Roman", Font.PLAIN, 35));
            g.drawString("--- NO ESCRIBA EN ESTE ESPACIO ---", 250, ah+47);
            //la primera hoja tiene una altura diferente al resto de las hojas, esto por el encabezado
            ah += altura_resto_de_hojas;//hojas 2,3,4 ... 
        }
    }
        
}
