import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;


public class Hh {
	public static void main(String args[]) throws Exception{
		int version=16;//4*15+21
		int width=263;
		int height=263;
		Qrcode qrcode=new Qrcode();
		String content="BEGIN:VCARD\n"+
		"N:��\n"+
		"FN:����\n"+
		"ORG:�ӱ��Ƽ�ʦ��ѧԺ\n"+ 
		"TITLE:Сѧ��\n"+
		"BDAY:1998-3-30\n"+
		"ADR;WORK:�ػʵ��к������ӱ��������360��\n"+
		"ADR;HOME:����Ա�\n"+
		"TEL;CELL,VOICE:15028580902\n"+
		"TEL;WORK,VOICE:��ʱû�У���������\n"+
		"URL;WORK;:http://www.icbc.com.cn\n"+
		"EMAIL;INTERNET,HOME:1045728573@qq.com\n"+
		"END:VCARD";
		qrcode.setQrcodeVersion(version);
		byte []data=content.getBytes("utf-8");
		boolean[][] qrdata=qrcode.calQrcode(data);
		//����ͼƬ������
		BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//ͼƬ�滭
		Graphics2D gs=bufferedImage.createGraphics();
		//��ά�����
//		int a=bufferedImage.getWidth();
//		int b=bufferedImage.getHeight();
		//���ñ���ɫ
		gs.setBackground(Color.WHITE);
		//gs.setColor(Color.BLACK);
		//�������
		gs.clearRect(0, 0, width, height);
		//��ά��滭
		for(int i=0;i<qrdata.length;i++)
			for(int j=0;j<qrdata.length;j++){
				if(qrdata[i][j]){
					int startR=26;int startG=253;int startB=253;
					int endR=241;int endG=37; int endB=241;
					int num1=startR+(endR-startR)*((i+j)/2)/qrdata.length;
					int num2=startG+(endG-startG)*((i+j)/2)/qrdata.length;
					int num3=startB+(endB-startB)*((i+j)/2)/qrdata.length;
					Color color=new Color(num1,num2,num3);
					gs.setColor(color);
					gs.fillRect(i*3+10,j*3+10,3,3);/*fillRect(int x, int y, int width, int height)
					i,jΪָ�����εĳ��Ϳ���widthΪ���Ŀ���i�ȱȣ�height��ͬ*/
				}
			}
		BufferedImage logo=scale("D:/logo2.png",80,80,true);
//		int logoSize=(qrdata.length)*10/4;
//		int o=((qrdata.length)*10-logoSize)/2;
		int location=(width-logo.getHeight())/2;
		gs.drawImage(logo, location, location, 80, 80,null);
		gs.dispose();//�رջ�ͼ
		bufferedImage.flush();
		try{
			ImageIO.write(bufferedImage,"png",new File("D:/qrcode.png"));
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("����������");
		}
		System.out.println("�����С���");
		
	}
		
		private static BufferedImage scale(String logoPath,int width,int height,boolean hasFiller)throws Exception{
			double ratio=0.0;//���ű���
			File file=new File(logoPath);
			BufferedImage srcImage=ImageIO.read(file);
			Image destImage =srcImage.getScaledInstance(width,height,BufferedImage.SCALE_SMOOTH);
			//�������
			if((srcImage.getHeight()>height)||(srcImage.getWidth()>width)){}
			if(srcImage.getHeight()>srcImage.getWidth()){
				ratio=(new Integer(height)).doubleValue()/srcImage.getHeight();
			}else{
				ratio =(new Integer(width)).doubleValue()/srcImage.getWidth();
			}
			AffineTransformOp op=new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage =op.filter(srcImage,null);
			
			//����
			if(hasFiller){
				BufferedImage image=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
				Graphics2D graphic =image.createGraphics();
				graphic.setColor(Color.white);
				graphic.fillRect(0, 0, width, height);
				if(width==destImage.getWidth(null)){
					graphic.drawImage(destImage, 0, (height-destImage.getHeight(null))/2,destImage.getWidth(null),
					destImage.getHeight(null),Color.white,null);
				}else{
					graphic.drawImage(destImage, 0, (width-destImage.getWidth(null))/2,destImage.getWidth(null),
					destImage.getHeight(null),Color.white,null);
				}
				graphic.dispose();
				destImage=image;
			}
			
			return (BufferedImage) destImage;
	}

}