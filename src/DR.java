import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DR {
	public static void main(String[] args) throws Exception {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.print("please input the name of file:");
			String path = sc.next();
			
			BasicInfo basic = new BasicInfo();
			int width = basic.getWidth();
			int height = basic.getHeight();
			int k = basic.getK();
			
			String distImgPath = "temp"+"//"+path.substring(0,path.indexOf("."))+".bmp";
			String disTextPath = "temp"+"//"+path.substring(0,path.indexOf("."))+".txt";		
			Tools tool = new Tools();
			// 处理成指定大小的bmp文件
			tool.resizeImage(path, distImgPath, width, height);

			// 再处理成纯文本的01的txt文件
			tool.imageToText(distImgPath,disTextPath);

			// 创建文件输入流
			FileInputStream fis = new java.io.FileInputStream(disTextPath);
			// 将文件流包装成一个可以写基本数据类型的输出流
			@SuppressWarnings("resource")
			DataInputStream dis = new java.io.DataInputStream(fis);
			
			File f = new File(disTextPath);
			int len = (int)f.length();//不一定每个文件的长度都相等！
			byte[] b = new byte[len];
			dis.read(b, 0, len);
			
			// 和指定文件夹下的01文件进行比较：
			String finalSrc = basic.getFinalDis();
			File[] list = new File(finalSrc).listFiles();
			int fileNum = list.length;
			
			if(k>fileNum){
				System.out.println("发生错误！");
				System.exit(0);
			}
			
			//用一个int[文件总数]数组 record来记录对应关系，record[i] = 9 代表第i个文件的内容是9
			//用一个int[文件总数]数组 result来记录距离
			int[] record = new int[fileNum];
			int[] result = new int[fileNum];
			for(int i = 0 ;i<fileNum;i++){
				//提取文件名的第一字符，其实也就是对应图片的内容。
				record[i]= Integer.parseInt((String) list[i].getName().subSequence(0, 1));
				result[i]= tool.compareFiles(b, list[i]);
			}

			//对result的colne进行排序，得到第k大的数。
			int[] resultColnt = result.clone();
			Arrays.sort(resultColnt);
			int kNum  = resultColnt[k-1];
			
			//使用一个int[10] count的数组来进行统计。
			int[] count = new int[10];
			
			//用这个k去result中去逐个比对，比kNum小的(或是等于的)，按照对应关系，去record中得到内容。
			for(int i = 0 ;i <fileNum;i++){
				if(result[i]<=kNum){
					count[record[i]]++;
				}
			}
			
			//找到count 中最大的数，输出其下标。 
			int best = 0;
			for(int i = 1 ;i <10;i++){
				if(count[i]>count[best])
					best = i;
			}
			
			System.out.println("图片的内容是："+best);

		} catch (IOException e) {
			System.out.println("发生错误！");
			e.printStackTrace();
		}
	}
}
