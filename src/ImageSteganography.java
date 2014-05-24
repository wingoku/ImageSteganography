import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;


public class ImageSteganography extends JFrame {

	private JPanel contentPane;
	
	private JPanel ImagePane;
	private JLabel ImageLabel;
	
	private JButton btnEncode;
	private JButton btnDecode;
	private JTextArea MessageArea;
	private JTextField ImagePath;
	private JLabel lblImagePath;
	private JButton btnLoadImage;
	private JButton btnAbout;
	private JButton btnReset;
	private String ImageFormat = "";

	public ImageSteganography() {
		
		super("Umer's Steganography Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 524, 464);
		setSize(570,468);
		setVisible(true);
		
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMessage = new JLabel("Message");
		
		MessageArea = new JTextArea();
		MessageArea.setWrapStyleWord(true);
		MessageArea.setLineWrap(true);
		MessageArea.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		MessageArea.setToolTipText("Enter Message Here");
		MessageArea.setRows(10);
		
		btnEncode = new JButton("Encode");
		btnEncode.setToolTipText("Encode Message in to image");
		
		btnDecode = new JButton("Decode");
		btnDecode.setToolTipText("Decode Message from image");
		
		ImagePane = new JPanel();
		
	//	ImagePane.setSize(arg0, arg1)
	
		ImagePane.setBorder(new TitledBorder(null, "Image", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ImagePane.setToolTipText("Image to be Encoded or Decoded");
		
		ImagePath = new JTextField();
		ImagePath.setToolTipText("Enter Absolute Path of Image file");
		ImagePath.setColumns(10);
		
		lblImagePath = new JLabel("Image Path");
		
		btnLoadImage = new JButton("Load Image");
		btnLoadImage.setToolTipText("Load Image from the specified path");
		
		btnAbout = new JButton("About Author");
		
		btnReset = new JButton("Reset");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblImagePath)
						.addComponent(lblMessage, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(btnEncode)
							.addGap(18)
							.addComponent(btnAbout)
							.addGap(18)
							.addComponent(btnDecode)
							.addGap(18)
							.addComponent(btnReset)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(ImagePath, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnLoadImage))
								.addComponent(ImagePane, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
								.addComponent(MessageArea, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
							.addGap(92))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(ImagePane, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ImagePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLoadImage)
						.addComponent(lblImagePath))
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(MessageArea, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMessage))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDecode)
						.addComponent(btnAbout)
						.addComponent(btnEncode)
						.addComponent(btnReset))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		Handler Handle = new Handler();
		
		btnEncode.addActionListener(Handle);
		btnDecode.addActionListener(Handle);
		btnLoadImage.addActionListener(Handle);
		btnAbout.addActionListener(Handle);
		btnReset.addActionListener(Handle);
	}
	
	public void getImage(String Path)
	{
		
		Image image = null;
		int newImageWidth = 0;
		int newImageHeigth = 0;
		
		try 
		{
			image = ImageIO.read(new File(Path));
		} 
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
		newImageWidth = image.getWidth(this);
		newImageHeigth = image.getHeight(this);
		
		// resizing code begins		
				if(image.getWidth(this) > ImagePane.getWidth())
					newImageWidth=ImagePane.getWidth();
				
				System.out.println("Image Width is :"+newImageWidth);
				if(image.getHeight(this) > ImagePane.getHeight())
					newImageHeigth = ImagePane.getHeight();
				
		Image new_Image= image.getScaledInstance(newImageWidth, newImageHeigth, image.SCALE_DEFAULT); // getting resized image.
		// this function returns are new image of the height and width and scaling method passed to it.
		
		ImageLabel = new JLabel(new ImageIcon(new_Image));
		
		
		ImageLabel.setSize(image.getWidth(this), image.getHeight(this));
		
		ImagePane.add(ImageLabel);
		
		ImagePane.revalidate();// if I don;t call this method, the image doesn't display in the JPanel unless I resize the Window and if
		// is displayed, the image is sligtly outside the Window that is picture ka kuch hisa nazar a ra hota ha, baki hisa nazar nae ata.
		
		// Revalidate, Puri window k her component me jo new stuff add kia ha ya changes ki ha, un ko check kr k, Window k hr component ko
		// update krta ha and then calls repaint
		
		// at the time of writing this comment the program windows was resizable
		
		
	}
	
	
	private class Handler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == btnLoadImage)
			{
				JFileChooser browsebtn = new JFileChooser();
				
				int ReturnValue =  browsebtn.showOpenDialog(browsebtn);
				
				//File name = browsebtn.getCurrentDirectory();
				//File select = browsebtn.getSelectedFile();
				
				//int mne = browsebtn.getApproveButtonMnemonic();
				String SelectedFilePath = browsebtn.getSelectedFile().getAbsolutePath();
				
				if(ReturnValue == browsebtn.APPROVE_OPTION)
				{
					System.out.println("Approve button");
					
					ImagePath.setText(SelectedFilePath);
				}
				
				if(!SelectedFilePath.contains(".png")) // if image is not .png image
				{
					JOptionPane.showMessageDialog(null, "Sorry!\nThis program works with .png format Images only.", "Loaded Image format not Supported", JOptionPane.ERROR_MESSAGE);
				}
				else
				if(ImagePath.getText().length() != 0)
				{
					System.out.println("Inside Load Image");
					
					String ImageAddress="";
					
					ImageAddress = ImagePath.getText();
					
					btnLoadImage.setEnabled(false);
					
					getImage(ImageAddress);
					
					int PositionOfDot = ImageAddress.indexOf(".");
					
					ImageFormat = ImageAddress.substring(PositionOfDot, ImageAddress.length());
				}
			}
			

			if(event.getSource() == btnEncode)
			{
				if(ImagePath.getText().length()!= 0 && MessageArea.getText().length()!= 0)
				{
					System.out.println("Inside encoder");
					
					String ImageAddress="";
					
					ImageAddress = ImagePath.getText();
					
					String message = MessageArea.getText();
					
					Stegnography steg = new Stegnography();
					
					steg.EncodeImage(ImageAddress, message);
					
					JOptionPane.showMessageDialog(null, "Encoding Completed", "Program Status", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else
					JOptionPane.showMessageDialog(null, "Please Load Image or Enter text to encode", "Program Status", JOptionPane.ERROR_MESSAGE);
			}
			
			
			if(event.getSource() == btnDecode)
			{
				System.out.println("Inside decoder");
				
				if(ImagePath.getText().length()!= 0)
				{
					RetreiveData data= new RetreiveData();
				
					String ImageAddress="";
				
					ImageAddress = ImagePath.getText();
				
					data.DecodeImage(ImageAddress);
				
					String decodedMessage = data.ReturnDecodedString();
				
					JOptionPane.showMessageDialog(null, decodedMessage, "Decoded Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "Please Load an Image to decode", "Program Status", JOptionPane.ERROR_MESSAGE);
			}
			
			if(event.getSource() == btnAbout)
			{
				JOptionPane.showMessageDialog(null, "This program is written by Umer Farooq.\nHe is currently pursuing his graduation in Computer System Engg.\nYou can find him at uet_subjects@yahoo.com", "About Author", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if(event.getSource() == btnReset)
			{
				btnLoadImage.setEnabled(true);
				
				ImagePath.setText("");
				
				MessageArea.setText(""); 
				
				ImagePane.removeAll(); // removes every thing inside the JPanel
				
				ImagePane.updateUI(); // updates the JPanel according to the new changes inside JPanel
			}
			
		}
		
	}
}
