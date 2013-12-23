public class BasicInfo {
	private int width;
	private int height;
	private int k;
	private String finalDis;

	//默认构造函数
	public BasicInfo() {
		width = 100;
		height = 100;
		k = 13;
		finalDis = "finalgoal";// 最终处理后的txt文件夹;
	}

	//getter&setter
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFinalDis() {
		return finalDis;
	}

	public void setFinalDis(String finalDis) {
		this.finalDis = finalDis;
	}
}
