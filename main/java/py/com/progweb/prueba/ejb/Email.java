package py.com.progweb.prueba.ejb;

import java.util.Properties;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {


    public void sendEmail(String enviarA, String encabezado, String texto) {

        System.out.println("Enviando a: " + enviarA + "\nEncabezado: " + encabezado + "\nTexto: " + texto);
        // Recipient's email ID needs to be mentioned.
//        String to = "arturojeich@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "pruebaemailbackend@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                //nUcrc83LbPQLAbB
                return new PasswordAuthentication("pruebaemailbackend@gmail.com", "asegcccrdepdwrch");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(enviarA));

            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
            message.setSubject(encabezado);

            // Now set the actual message
//            message.setText("This is actual message");
            message.setText(texto);

            System.out.println("Enviando email...");
            // Send message
            Transport.send(message);
            System.out.println("Mensaje enviado con exito!....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}