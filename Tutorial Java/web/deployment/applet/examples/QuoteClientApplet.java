package deployment.applet.examples;

/*
 * Copyright (c) 1995, 2009, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Java(TM) SE 6 version.
 * SwingWorker can be downloaded at:
 * https://swingworker.dev.java.net/
 * SwingWorker is included in Java(TM) SE 6.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class QuoteClientApplet extends JApplet
                               implements ActionListener {
    private static boolean DEBUG = false;
    private InetAddress address;
    private JTextField portField;
    private JLabel display;
    private JButton sendButton;
    private JProgressBar progressBar;
    private DatagramSocket socket;
    private String host;
    private static final int MAX_NUM_CHARS = 256;
    private static final int TIMEOUT = 500; //time out after 1/2 a second
    private static String sendButtonText = "OK";
    private QuoteGetter quoteGetter;

    public void init() {
        //Initialize networking stuff.
        host = getCodeBase().getHost();

        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.err.println("No se pudo obtener la dirección de Internet: Host desconocido");
            // ¿Que hariamos?
        }

        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT);
        } catch (IOException e) {
            System.err.println("No se puedo crear un nuevo DatagramSocket");
            return;
        }

        //configurar el IU.
        //ejecutar un trabajo en el hilo despachador de eventos:
        //creando el IGU de este applet.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI no se completó con éxito");
        }
    }

    private void createGUI() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        int numColumns = 3;

        JLabel l1 = new JLabel("Cita del Momento:", JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridwidth = numColumns;
        contentPane.add(l1, c);

        display = new JLabel("(no se recibió cita aún)", JLabel.CENTER);
        display.setForeground(Color.gray);
        c.gridy = 1;
        c.gridwidth = numColumns;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(display, c);

        JLabel l2 = new JLabel("Introduzca el puerto (en el host " + host
                               + ") para enviar la petición a:",
                               JLabel.RIGHT);
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.SOUTH;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        contentPane.add(l2, c);

        portField = new JTextField(6);
        c.gridx = 1;
        c.weightx = 1.0;
        c.insets = new Insets(0,5,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(portField, c);

        sendButton = new JButton(sendButtonText);
        c.gridx = 2;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        contentPane.add(sendButton, c);

        progressBar = new JProgressBar();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = numColumns;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,0,5,0);
        contentPane.add(progressBar, c);

        portField.addActionListener(this);
        sendButton.addActionListener(this);

        //Terminando la configuración del panel de contenido y sus bordes.
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.black),
                                    BorderFactory.createEmptyBorder(5,20,5,10)));
        setContentPane(contentPane);
    }

    private class QuoteGetter extends SwingWorker<String, String> {
        int port;
        InetAddress address;

        public QuoteGetter(int port, InetAddress address) {
            this.port = port;
            this.address = address;
        }

         @Override
         public String doInBackground() {
            DatagramPacket packet;
            byte[] sendBuf = new byte[MAX_NUM_CHARS];

            packet = new DatagramPacket(sendBuf, MAX_NUM_CHARS, address, port);

            try { // send request
                if (DEBUG) {
                    System.out.println("El applet está a punto de enviar paquetes a la dirección "
                                   + address + " en el puerto " + port);
                }
                socket.send(packet);
                if (DEBUG) {
                    System.out.println("El applet envió el paquete.");
                }
            } catch (IOException e) {
                System.err.println("El applet falló el envío del socket:\n"
                                   + e.getStackTrace());
                return null;
            }

            packet = new DatagramPacket(sendBuf, MAX_NUM_CHARS);

            try { // get response
                if (DEBUG) {
                    System.out.println("El applet está a punto de llamar a socket.receive().");
                }
                socket.receive(packet);
                if (DEBUG) {
                    System.out.println("El applet volvió de socket.receive().");
                }
            } catch (SocketTimeoutException e) {
                System.err.println("Tiempo excedido en el socket.receive del applet.");
                //Should let the user know in the UI.s
                return null;
            } catch (IOException e) {
                System.err.println("socket.receive del applet falló:\n"
                                   + e.getStackTrace());
                return null;
            }

            String received = new String(packet.getData());
            StringBuffer data = new StringBuffer(received);

            //¡Alerta de hackeo! Asumiendo que el último carácter es basura, eliminamos
            //en esta cadena todos los caracteres basura. 
            int firstGarbage = data.indexOf(Character.toString(
                                            received.charAt(MAX_NUM_CHARS - 1)));
            if (firstGarbage > -1) {
                data.delete(firstGarbage, MAX_NUM_CHARS);
            }
            return data.toString();
         }

         //Una vez el socket ha sido leído, mostrar lo que envia.
        protected void done() {
            if (DEBUG) {
                System.out.println("SwingWorker terminó.");
            }
            progressBar.setIndeterminate(false);

            try {
                String s = get();
                if (DEBUG) {
                    System.out.println("get() devolvió \"" + s + "\"");
                }
                if (s != null) {
                    //Visualizamos el texto.
                    display.setForeground(Color.gray);
                    display.setText(s);
                } else {
                    display.setForeground(Color.red);
                    display.setText("Lectura fallida (vea la consola para los detalles).");
                }
            } catch (Exception ignore) { }
         }
    }

    //invocado en el hilo de envío de eventos
    private void doIt(int port) {
        //Escucha al puerto en un hilo en segundo plano para evitar
        //Listen to the port on a background thread to avoid
        //bloquear el IGU.
        quoteGetter = new QuoteGetter(port, address);
        quoteGetter.execute();

        display.setForeground(Color.gray);
        display.setText("Leyendo el puerto #" + port);
    }

    public void actionPerformed(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
            progressBar.setIndeterminate(true);
            doIt(port);
        } catch (NumberFormatException e) {
            //No se introdujo entero.
            display.setForeground(Color.red);
            display.setText("Por favor introduzca un número en el campo de texto de debajo.");
        }
    }

    public void destroy() {
        //¿destruir el DatagramSocket?
        if (socket != null) {
            socket.close();
        }

        //Configura el IU.
        //Ejecuta un trabajo en el hilo de envío de eventos:
        //creando el IGU de este applet.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    getContentPane().removeAll();
                }
            });
        } catch (Exception e) { }
    }
}


