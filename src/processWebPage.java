import java.io.File;

public class processWebPage {
    /* 说明：
     * 1  在从网页获取训练集图片时，为了避免一个个去保存，可以将含有大量的网页保存下来，
       2  然后将包含图片的文件夹命名为web
       3  把其中的图片提取出来，有的图片保存下来后失去了后缀名，为其添加后缀名
       4  提取处理来的图片保存在webResult文件夹下。
       */
	
	public static void main(String[] args) {
		String floder = "web";
		String result = "webResult";
		File f = new File(floder);
		File[] list = f.listFiles();
		
		int length = list.length;
		for(int i =0 ;i<length;i++){
			if(list[i].getName().indexOf(".")<0)
				list[i].renameTo(new File(result+"\\"+list[i].getName()+".jpg"));
			else if(list[i].getName().indexOf(".jpg")>=0){
				list[i].renameTo(new File(result+"\\"+list[i].getName()));
			}
		}
	}
}
