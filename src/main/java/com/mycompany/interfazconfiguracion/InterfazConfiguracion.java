package com.mycompany.interfazconfiguracion;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class InterfazConfiguracion extends JFrame {

    private JSpinner margenSuperior;
    private JSpinner margenInferior;
    private JSpinner margenIzquierdo;
    private JSpinner margenDerecho;

    private JComboBox<String> orientacion;
    private GLJPanel canvas;

    private float superior = 0;
    private float inferior = 0;
    private float izquierdo = 0;
    private float derecho = 0;

    private boolean vertical = true;

    public InterfazConfiguracion() {
        setTitle("Configuración de Página");
        setSize(780, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titulo = new JLabel("Configuración de Página");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBounds(260, 15, 300, 30);
        add(titulo);

        JLabel lblSuperior = new JLabel("Margen superior:");
        lblSuperior.setBounds(30, 80, 130, 25);
        add(lblSuperior);

        margenSuperior = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        margenSuperior.setBounds(160, 80, 70, 30);
        add(margenSuperior);

        JLabel lblInferior = new JLabel("Margen inferior:");
        lblInferior.setBounds(30, 130, 130, 25);
        add(lblInferior);

        margenInferior = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        margenInferior.setBounds(160, 130, 70, 30);
        add(margenInferior);

        JLabel lblIzquierdo = new JLabel("Margen izquierdo:");
        lblIzquierdo.setBounds(30, 180, 130, 25);
        add(lblIzquierdo);

        margenIzquierdo = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        margenIzquierdo.setBounds(160, 180, 70, 30);
        add(margenIzquierdo);

        JLabel lblDerecho = new JLabel("Margen derecho:");
        lblDerecho.setBounds(30, 230, 130, 25);
        add(lblDerecho);

        margenDerecho = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        margenDerecho.setBounds(160, 230, 70, 30);
        add(margenDerecho);

        JLabel lblOrientacion = new JLabel("Orientación:");
        lblOrientacion.setBounds(30, 290, 100, 25);
        add(lblOrientacion);

        orientacion = new JComboBox<>(new String[]{"Vertical", "Horizontal"});
        orientacion.setBounds(130, 290, 130, 30);
        add(orientacion);

        JButton reset = new JButton("Reset");
        reset.setBounds(75, 360, 140, 35);
        add(reset);

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        canvas = new GLJPanel(capabilities);
        canvas.setBounds(310, 80, 420, 330);
        add(canvas);

        canvas.addGLEventListener(new VistaPagina());

        ChangeListener cambioMargenes = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                superior = ((Number) margenSuperior.getValue()).floatValue();
                inferior = ((Number) margenInferior.getValue()).floatValue();
                izquierdo = ((Number) margenIzquierdo.getValue()).floatValue();
                derecho = ((Number) margenDerecho.getValue()).floatValue();

                canvas.display();
            }
        };

        margenSuperior.addChangeListener(cambioMargenes);
        margenInferior.addChangeListener(cambioMargenes);
        margenIzquierdo.addChangeListener(cambioMargenes);
        margenDerecho.addChangeListener(cambioMargenes);

        orientacion.addActionListener(e -> {
            vertical = orientacion.getSelectedItem().equals("Vertical");
            canvas.display();
        });

        reset.addActionListener(e -> {
            margenSuperior.setValue(0);
            margenInferior.setValue(0);
            margenIzquierdo.setValue(0);
            margenDerecho.setValue(0);

            orientacion.setSelectedItem("Vertical");

            superior = 0;
            inferior = 0;
            izquierdo = 0;
            derecho = 0;

            vertical = true;
            canvas.display();
        });
    }

    class VistaPagina implements GLEventListener {

        @Override
        public void init(GLAutoDrawable drawable) {
            GL2 gl = drawable.getGL().getGL2();
            gl.glClearColor(0.93f, 0.93f, 0.93f, 1.0f);
        }

        @Override
        public void display(GLAutoDrawable drawable) {

            GL2 gl = drawable.getGL().getGL2();

            gl.glClear(GL.GL_COLOR_BUFFER_BIT);

            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadIdentity();

            float ancho;
            float alto;

            if (vertical) {
                ancho = 1.0f;
                alto = 1.414f;
            } else {
                ancho = 1.414f;
                alto = 1.0f;
            }

            float x1 = -ancho / 2;
            float x2 = ancho / 2;

            float y1 = -alto / 2;
            float y2 = alto / 2;

            gl.glColor3f(0.75f, 0.75f, 0.75f);

            gl.glBegin(GL2.GL_QUADS);

            gl.glVertex2f(x1 + 0.035f, y1 - 0.035f);
            gl.glVertex2f(x2 + 0.035f, y1 - 0.035f);
            gl.glVertex2f(x2 + 0.035f, y2 - 0.035f);
            gl.glVertex2f(x1 + 0.035f, y2 - 0.035f);

            gl.glEnd();

            // HOJA 
            gl.glColor3f(1f, 1f, 1f);

            gl.glBegin(GL2.GL_QUADS);

            gl.glVertex2f(x1, y1);
            gl.glVertex2f(x2, y1);
            gl.glVertex2f(x2, y2);
            gl.glVertex2f(x1, y2);

            gl.glEnd();

            gl.glColor3f(0f, 0f, 1f);

            gl.glLineWidth(2f);

            gl.glBegin(GL2.GL_LINE_LOOP);

            gl.glVertex2f(x1, y1);
            gl.glVertex2f(x2, y1);
            gl.glVertex2f(x2, y2);
            gl.glVertex2f(x1, y2);

            gl.glEnd();

            // MARGENES
            float escala = 0.01f;

            float mx1 = x1 + izquierdo * escala;
            float mx2 = x2 - derecho * escala;

            float my1 = y1 + inferior * escala;
            float my2 = y2 - superior * escala;

            if ((superior > 0
                    || inferior > 0
                    || izquierdo > 0
                    || derecho > 0)
                    && mx1 < mx2
                    && my1 < my2) {

                gl.glColor3f(1f, 0f, 0f);

                gl.glLineWidth(2f);

                gl.glBegin(GL2.GL_LINE_LOOP);

                gl.glVertex2f(mx1, my1);
                gl.glVertex2f(mx2, my1);
                gl.glVertex2f(mx2, my2);
                gl.glVertex2f(mx1, my2);

                gl.glEnd();
            }

            gl.glFlush();
        }

        @Override
        public void reshape(
                GLAutoDrawable drawable,
                int x,
                int y,
                int width,
                int height) {

            GL2 gl = drawable.getGL().getGL2();

            gl.glViewport(0, 0, width, height);

            gl.glMatrixMode(GL2.GL_PROJECTION);

            gl.glLoadIdentity();

            float aspect = (float) width / (float) height;

            float limite = 1.0f;

            if (aspect >= 1.0f) {

                gl.glOrtho(-limite * aspect, limite * aspect, -limite, limite, -1, 1);

            } else {

                gl.glOrtho(-limite, limite, -limite / aspect, limite / aspect, -1, 1);
            }
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            gl.glLoadIdentity();
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazConfiguracion ventana = new InterfazConfiguracion();
            ventana.setVisible(true);
        });
    }
}
