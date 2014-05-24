import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Stegnography {

	private BufferedImage image=null,new_image=null;
	private int pixels[][];
	private char Data[];
	private int Width,Height;
	private int Binary[];
	private int Length=0;
	private String LengthString="";
	
	public void EncodeImage(final String ImagePath,String message)
	{
		File fileName=new File(ImagePath);
		
		try
		{
			new_image = ImageIO.read(fileName);
			 
            image = new BufferedImage(new_image.getWidth(), new_image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
 
            Graphics2D graphics = image.createGraphics();
            graphics.drawRenderedImage(new_image, null);
 
            graphics.dispose();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		//ByteArrayOutputStream ba= new ByteArrayOutputStream();
		
		//ba.write()
		
		pixels= new int[image.getWidth()][image.getHeight()];
		
		//Binary= new int[8];
		
		Width=image.getWidth();
		Height=image.getHeight();
		
		for(int i=0;i<Width;i++)
		{
			for(int j=0;j<Height;j++)
			{
				pixels[i][j]=image.getRGB(i, j);
			}
		}
		
		
		Length=message.length(); // length original string ki rakhi ha take original string puri ki puri decode ho jae
		// if hum message me space insert krne k bad wali string ki length, Length variable me store krte to program space ko b encoded image
		// se decode krta and us ki jaga koi ajeeb ascii character dall deta. 
		
		message+=" ";  // for solving the problem of weird last character decoded from the encoded image which we lose because of some issue
		
		System.out.println("Message length is :"+Length);

		LengthBinary(Length);
		//LengthString= Integer.toBinaryString(Length);
		
		EncodeStringLength();
		
		//System.out.println(LengthString);
				
		Data=message.toCharArray();
		
		
		
		
		WriteLSB();

		try
		{
			ImageIO.write(image, "png", fileName);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("Encoding Completed");
	}
	
	private void WriteLSB()
	{		
		int temp,tempText,x=0,p=0,i,j=0;
		Binary((int) Data[x]);
		x++;
		
		for(i=1 ;(i < Width && x < Data.length); i++)
		{
			for(j=0 ;(j < Height && x < Data.length); j++)
			{
				int change = 0;	
				
				for(int k = 0; k < 4; k++)
				{
					//change = 1 << 8 * k;
					if(k == 0)
					{
						change = 1;
					}
					else
						if(k == 1)
						{
							change = 256;
						}
						else
							if(k == 2)
							{
								change = 65536;
							}
							else
								if(k==3)
								{
									change=16777216;
								}
							
				
					if(Binary[p] == 0)
					{
						pixels[i][j]= pixels[i][j] & ~change; // ~1 ki all bits 1 hoti ha except LSB
					}
					else
						if(Binary[p] == 1)
						{
							pixels[i][j]= pixels[i][j] | change; // only LSB ko 1 krna ha
						}
					p++;
					
					if(p == 8)
					{
						p = 0;
						Binary((int) Data[x]);
						x++;
					}
				}
				
			
			}
		}
	//	System.out.println(i);
		//System.out.println(j);
		
		for(i = 0;i < Width; i++)
		{
			for(int h = 0;h < Height; h++)
			{
				image.setRGB(i, h, pixels[i][h]);
			}
		}
		
	}
	
	private void Binary(int temp)
	{
		Binary=null;
		Binary= new int[8];
		
		for(int i=7;i>=0;i--)// cuz character is of 1 byte
		{
			Binary[i]=temp%2;
			
			temp/=2;
		}
	}
	
	private void EncodeStringLength()
	{
		int p=0;
		
		for(int i=0; i < 1 ; i++)
		{
			for(int j=0 ; (j < 8 && p < 32) ; j++)
			{
				int change = 0;	
			//	System.out.println("in j x is: "+ x);
				
				for(int k = 0; k < 4; k++)
				{
					//change = 1 << 8 * k;
					if(k == 0)
					{
						change = 1;
					}
					else
						if(k == 1)
						{
							change = 256;
						}
						else
							if(k == 2)
							{
								change = 65536;
							}
							else
								if(k==3)
								{
									change=16777216;
								}
							
					//System.out.println(x);
					//System.out.println(LengthString.length());
					//System.out.println(LengthString.charAt(x) );
					/*if(LengthString.charAt(x) == '0')
					{						
						pixels[i][j]= pixels[i][j] | change;
					}
					else
						if(LengthString.charAt(x) == '1')
						{
							pixels[i][j]= pixels[i][j] | change;
						}
				
					x++;
					
					if(x == LengthString.length())
						break;*/
					
					if(Binary[p] == 0)
					{
						pixels[i][j]= pixels[i][j] & ~change; // ~1 ki all bits 1 hoti ha except LSB
					}
					else
						if(Binary[p] == 1)
						{
							pixels[i][j]= pixels[i][j] | change; // only LSB ko 1 krna ha
						}
					p++;
					
					
				}
			//	System.out.println();
			}
		}
	}
	
	private void LengthBinary(int len)
	{
		Binary=new int[32];
		System.out.println("Encoding length: "+len);
		
		for(int i=0; (len!=0 && i<32) ;i++)
		{
			Binary[i]=len % 2;
			len/=2;
		
			System.out.print(Binary[i]);
		}
	}
}
