import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


public class RetreiveData {

	private BufferedImage image=null;
	private int pixels[][];
	private char Data[];
	private int Width,Height, Length=0;
	private String myString="";
	
	private static final String ResultPath= "C:\\Users\\Decoded.txt";
	
	public String DecodeImage(final String ImagePath) // second argument was , final String ResultPath
	{
		try
		{
			image= ImageIO.read(new File(ImagePath));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		//ByteArrayOutputStream ba= new ByteArrayOutputStream();bmp
		
		//ba.write()
		
		pixels= new int[image.getWidth()][image.getHeight()];
		
		Width=image.getWidth();
		Height=image.getHeight();
		
		//System.out.println(Width);
		//System.out.println(Height);
		
		for(int i=0;i<Width;i++)
		{
			for(int j=0;j<Height;j++)
			{
				pixels[i][j]=image.getRGB(i, j);
			}
		}
		
		FileWriter fw=null;
		File file=null;
		BufferedWriter bw=null;
		
		try
		{
			file = new File(ResultPath);
			fw = new FileWriter(file);
			
			bw= new BufferedWriter(fw);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	//	Data=file.toString().toCharArray();
		
		Length=DecodeStringLength();
		
		ReadLSB();
		
		try 
		{
			bw.write(myString);
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//System.out.println("String is :"+myString);
		return myString;
	}
	
	private void ReadLSB()
	{		
		int temp,tempText=0,x=0,p=0;
		
		for(int i=1; (i < Width && myString.length() < Length) ;i++)
		{
			for(int j=0; (j < Height && myString.length() < Length);j++)
			{
				//temp=pixels[i][j];
				
				int change=0;	
				
				for(int k=0;k<4;k++)
				{
					//change = 1 << 8 * k;
					if(k==0)
					{
						change=1;
					}
					else
						if(k==1)
						{
							change=256;
						}
						else
							if(k==2)
							{
								change=65536;
							}
							else
								if(k==3)
								{
									change=16777216;
								}
					/*if(Binary[p]==0)
					{
						pixels[i][j]= pixels[i][j] & ~change; // ~1 ki all bits 1 hoti ha except LSB
					}
					else
						if(Binary[p]==1)
						{
							pixels[i][j]= pixels[i][j] | change; // only LSB ko 1 krna ha
						}*/
					tempText<<=1;
					if((pixels[i][j] & change)!=0)	
					{
						tempText|=1 ;
					}
					
					
					
					p++;
					if(p==8)
					{
						myString += (char) tempText;// convert int to String and storing in String object
						p=0;
						//x++;
					//	if(tempText!=272)
						//System.out.println("Temp is "+tempText);
						tempText=0;
						//System.out.println("Inside");
						//System.out.print(myString);
					}
				}
				
				
			
			}
		}
	}
	
	private int DecodeStringLength()
	{
		int tempText=0, p=0;
	//	String StringLength="";
		
		int Binary[]= new int[32];
		
		for(int i=0 ; i < 1; i++)
		{
			for(int j=0; (j < 8 && p <32); j++)
			{
				int change=0;	
				
				for(int k=0;k<4;k++)
				{
					//change = 1 << 8 * k;
					if(k==0)
					{
						change=1;
					}
					else
						if(k==1)
						{
							change=256;
						}
						else
							if(k==2)
							{
								change=65536;
							}
							else
								if(k==3)
								{
									change=16777216;
								}
					
					//tempText<<=1;
					if((pixels[i][j] & change)!=0)	
					{
						Binary[p]=1 ;
					}
					else
						Binary[p]=0;
					
						
					
					p++;
					
					/*if(p==8)
					{
							StringLength += (char) tempText;// convert int to String and storing in String object
							p=0;
							tempText=0;
					}*/
				}
			}
		}
		
		int i;
		
		for(i=31;i>=0;i--)
		{
			if(Binary[i]==1)
				break;
		}
		
		for(int j=i; j>=0 ; j--)
		{
			tempText <<= 1;
			
			if(Binary[j]==1)
				tempText|=Binary[j];
		}
		
	//	System.out.println("str is "+StringLength);
		System.out.println("String length is in decoding: "+tempText);
		//return Integer.parseInt(StringLength);
		
		return tempText;
	}
	
	public String ReturnDecodedString()
	{
		return myString;
	}
}
