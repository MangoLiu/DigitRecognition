import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tools {

	// 打印图片的基本信息
	public void printInformation(DataInputStream dis) throws IOException {
		int bflen = 14;
		byte bf[] = new byte[bflen];
		dis.read(bf, 0, bflen); // 读取14字节BMP文件头

		System.out.println("BMP文件头:");
		int bilen = 40;
		byte bi[] = new byte[bilen];
		dis.read(bi, 0, bilen);// 读取40字节BMP信息头

		// java int 占4个字节
		// 而在二值图中，8个像素占一个byte

		int image_heigh;
		int image_width;
		// ChangeInt是向起始点先前去4位，将4位转换为一个int

		image_width = ChangeInt(bi, 7); // 源图宽度 即取得是4,5,6,7位
		System.out.println("宽:" + image_width);

		image_heigh = ChangeInt(bi, 11); // 源图高度
		System.out.println("高:" + image_heigh);

		int nbitcount = (((int) bi[15] & 0xff) << 8) | (int) bi[14] & 0xff; // 位数
		System.out.println("位数:" + nbitcount);

		int nsizeimage = ChangeInt(bi, 23); // 源图大小 以字节为单位
		System.out.println("源图大小:" + nsizeimage);
	}

	// 将byte数组中的4位转换成int
	public int ChangeInt(byte[] bi, int start) {
		return (((int) bi[start] & 0xff) << 24)
				| (((int) bi[start - 1] & 0xff) << 16)
				| (((int) bi[start - 2] & 0xff) << 8) | (int) bi[start - 3]
				& 0xff;
	}

	// 将图片转换成01文本
	public void imageToText(String srcPath, String disPath)
			throws Exception {
		File f = new File(srcPath);
		int length = (int)f.length();
		//若是待转换的图片太大的话，可能会溢出。
		FileInputStream fis = new FileInputStream(srcPath);
		@SuppressWarnings("resource")
		DataInputStream dis = new DataInputStream(fis);
		byte[] bi= new byte[length];
		dis.read(bi, 0, length);

		// String name = srcPath.substring(0, srcPath.indexOf("."));
		FileWriter fw = new FileWriter(new File(disPath));
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < length; i++) {
			bw.write(byte2bits(bi[i]) + " ");
		}
		bw.close();
	}

	// 将byte转化为8位的bit
	public String byte2bits(byte b) {
		int z = b;
		z |= 256; // 使最后能截取到8位，而又不影响byte的原来值。
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	// 将带计算的图片的byte数组和某一样本集进行比较，输出距离。
	public int compareFiles(byte[] bf, File file) throws Exception {
		int count = 0;
		FileInputStream fs;

		fs = new FileInputStream(file);
		@SuppressWarnings("resource")
		DataInputStream ds = new DataInputStream(fs);

		byte temp[] = new byte[bf.length];
		ds.read(temp, 0, bf.length);

		for (int i = 0; i < bf.length; i++) {
			count += temp[i] ^ bf[i];
		}
		return count;
	}

	// 将图像处理为指定的大小和格式。
	public void resizeImage(String srcImgPath, String distImgPath, int width,
			int height) throws IOException {

		File srcFile = new File(srcImgPath);
		Image srcImg = ImageIO.read(srcFile);
		BufferedImage buffImg = null;
		buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);
		buffImg.getGraphics().drawImage(
				srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
				0, null);
		ImageIO.write(buffImg, "BMP", new File(distImgPath));
	}

}
