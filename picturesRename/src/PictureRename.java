import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PictureRename {

    public static void main(String[] args){
        System.out.println("请输入待处理图片所在文件夹路径：");
        Scanner sc= new Scanner(System.in);
        String originalDirectoryPath = sc.nextLine();
        System.out.println("请输入处理后保存图片的文件夹路径（不要与原路径相同）：");
        String newDirectoryPath = sc.nextLine();
        File directory = new File(newDirectoryPath);
        if(!directory.exists()){
            directory.mkdirs();
        }
        File file = new File(originalDirectoryPath);
        File[] pictures = file.listFiles();
        ArrayList<String> names = new ArrayList<>();  //存储已出现的文件名
        int count = 0;


        if(file.exists() && file.isDirectory()){
            for(int i = 0; i < pictures.length; i++){
                String fileName = pictures[i].getName();
                String tempName = fileName.split("-")[0];

                String newName=tempName + ".jpg";
                if(names.contains(tempName)) {
                    count = count + 1;
                    newName = tempName + "-" + count + ".jpg";
                }else{
                    count = 0;
                }
                String newPath = newDirectoryPath + "\\" + newName;
                File newFile = new File(newPath);
                names.add(tempName);
                byte[] data = null;
                // 读取图片字节数组
                try {
                    InputStream in = new FileInputStream(originalDirectoryPath + "\\" + fileName);
                    data = new byte[in.available()];
                    in.read(data);
                    for (int j = 0; j < data.length; ++j) {
                        if (data[j] < 0) {// 调整异常数据
                            data[j] += 256;
                        }
                    }
                    // 生成jpeg图片
                    OutputStream out = new FileOutputStream(newPath);
                    out.write(data);
                    out.flush();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
