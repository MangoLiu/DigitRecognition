import java.io.File;

public class Pretreated {
	public static void main(String[] args) throws Exception {
		
		int number;
		BasicInfo basic = new BasicInfo();
		int width = basic.getWidth();
		int height = basic.getHeight();

		String src; // 图片的原始文件夹
		String dis;// 图片的预处理结果bmp文件夹
		//String finalDis;// 最终处理结果txt文件夹

		String srcImg;// 图片原始文件名
		String distImg;// 预处理后的bmp文件名
		String finaldistImg;// 最终的txt文件名

		// 临时中间变量
		String name;
		File f;
		File[] list;
		int fileNum;
		Tools tool = new Tools();

		for (number = 0; number < 10; number++) {
			src = "original/" + number;
			dis = "goal";
			
			f = new File(src);
			list = f.listFiles();
			fileNum = list.length;

			// 将src文件夹下的源图片处理成指定大小的图片，保存在dis文件夹下
			for (int i = 0; i < fileNum; i++) {
				name = list[i].getName();
				// srcImg = src + "\\" + number + "\\" + name;
				srcImg = src + "\\" + name;
				distImg = dis + "\\" + number + "\\" + number + "_" + (i + 1)
						+ ".bmp";
				tool.resizeImage(srcImg, distImg, width, height);

				finaldistImg = basic.getFinalDis() + "\\" + number + "_" + (i + 1)
						+ ".txt";
				tool.imageToText(distImg, finaldistImg);
			}
		}
	}
}
