import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class  Client  implements ActionListener {

    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();

    static  JFrame f = new JFrame();

    static  DataOutputStream dout;
    Client(){
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7 ,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("back_arrow_icon.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("profile.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("cam-recorder.png"));
        Image i8 = i7.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel vcall = new JLabel(i9);
        vcall.setBounds(300,20,35,35);
        p1.add(vcall);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("phone-call.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel pcall = new JLabel(i12);
        pcall.setBounds(350,20,30,30);
        p1.add(pcall);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("dots.png"));
        Image i14 = i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dots = new JLabel(i15);
        dots.setBounds(390,20,30,30);
        p1.add(dots);

        JLabel name = new JLabel("RAJE");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
        p1.add(name);

        JLabel status = new JLabel("online");
        status.setBounds(110,40,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,427,570);
        a1.setBackground(Color.WHITE);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
        f.add(text);

        JButton send = new JButton("SEND");
        send.setBounds(320,655,110,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
        send.addActionListener(this);
        f.add(send);



        f.setSize(450,740);
        f.setLocation(800,50);
        f.getContentPane().setBackground(Color.WHITE);



        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            JPanel p2 = formatLable(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public static JPanel formatLable(String out){
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+ out +"</p></html>");
        output.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args){
        new Client();

        try {
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLable(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);

                f.validate();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
