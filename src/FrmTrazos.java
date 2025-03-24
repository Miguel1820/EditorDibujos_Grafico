import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

public class FrmTrazos extends JFrame{

    private String[] tipoTrazos = new String[]{"Linea", "Rectangulo", "Ovalo"};
    JComboBox cmbTipoTrazo;
    JTextField txtInfo;
    JPanel pnlDibujo = new JPanel();
    int x, y;
    boolean trazando = false;

    public FrmTrazos(){
        setSize(500,400);
        setTitle("Editor Grafico");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Barra de herramientas
        JToolBar tbTrazos = new JToolBar();
        cmbTipoTrazo = new JComboBox();
        DefaultComboBoxModel dcm = new DefaultComboBoxModel(tipoTrazos);
        cmbTipoTrazo.setModel(dcm);
        tbTrazos.add(cmbTipoTrazo);

        txtInfo = new JTextField();
        tbTrazos.add(txtInfo);


        //Hacemos un Panel color negro
        pnlDibujo = new JPanel();
        pnlDibujo.setBackground(Color.BLACK);

        //Arriba la barra de trazos
        getContentPane().add(tbTrazos,BorderLayout.NORTH);
        //Centro el panel de dibujos
        getContentPane().add(pnlDibujo,BorderLayout.CENTER);


        //Escuchador de eventos (Evento cuando haga click en con el raton)
        pnlDibujo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                dibujar(me.getX(),me.getY());
            }
        });

        //Escuchador de eventos (Evento cuando mueva el puntero del mause)
        pnlDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me) {
                dibujarTemporal(me.getX(), me.getY());
            }
        });

        //Escuchador de eventos (Ejecutar algo despues de que la ventana de despliegue)
        cmbTipoTrazo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }

        });
    }

/* 
    private void trazar(){
        Graphics g = pnlDibujo.getGraphics();
        g.setColor(Color.RED);
        g.drawLine(50, 50, 400, 200);
    }
*/

    private void dibujar(int x, int y) {
        if (!trazando) {
            trazando = true;
            this.x = x;
            this.y = y;
            txtInfo.setText("Trazando desde x=" + this.x + ", y=" + this.y);
        } else {
            trazando = false;
            Graphics g = pnlDibujo.getGraphics();
            g.setColor(Color.RED);
            int ancho = Math.abs(this.x - x);
            int alto = Math.abs(this.y - y);            
            switch (cmbTipoTrazo.getSelectedIndex()) {
                case 0:
                    g.drawLine(this.x, this.y, x, y);
                    break;

                case 1:
                    this.x = x < this.x ? x : this.x;
                    this.y = y < this.y ? y : this.y;
                    g.drawRect(this.x, this.y, ancho, alto);
                    break;

                case 2:
                    this.x = x < this.x ? x : this.x;
                    this.y = y < this.y ? y : this.y;
                    g.drawOval(this.x, this.y, ancho, alto);
            }
           
            txtInfo.setText(""); 
         }
        }

        private void dibujarTemporal(int x, int y) {
            if (trazando) {
                Graphics g = pnlDibujo.getGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0,0,pnlDibujo.getWidth(), pnlDibujo.getHeight());
                g.setColor(Color.YELLOW);
                int ancho = Math.abs(this.x - x);
                int alto = Math.abs(this.y - y);
                int x1 = x < this.x ? x : this.x; 
                int y1 = y < this.y ? y : this.y;
                switch (cmbTipoTrazo.getSelectedIndex()) {
                    case 0:
                        g.drawLine(this.x, this.y, x, y);
                        break;
    
                    case 1:
                        g.drawRect(x1, y1, ancho, alto);
                        break;
    
                    case 2:
                        g.drawOval(x1, y1, ancho, alto);
                        break;
                }
            }
    }


}
