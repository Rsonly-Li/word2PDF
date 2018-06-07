import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;
import java.io.IOException;

/**
 * Created by 李綦睿 on 2017-03-08 0008.
 */
public class word2pdf {
    // 将word格式的文件转换为pdf格式
      public static void Word2Pdf(String srcPath, String desPath) throws IOException {
                 // 源文件目录
                 File inputFile = new File(srcPath);
                 if (!inputFile.exists()) {
                         System.out.println("源文件不存在！");
                         return;
                     }
                 // 输出文件目录
                 File outputFile = new File(desPath);
                 if (!outputFile.getParentFile().exists()) {
                         outputFile.getParentFile().exists();
                     }
                 // 调用openoffice服务线程
                 String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
                 Process p = Runtime.getRuntime().exec(command);

                 // 连接openoffice服务
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                                 "127.0.0.1", 8100);
                 connection.connect();

                 // 转换word到pdf
                 DocumentConverter converter = new OpenOfficeDocumentConverter(
                                 connection);
                 converter.convert(inputFile, outputFile);

                 // 关闭连接
                 connection.disconnect();

                 // 关闭进程
                 p.destroy();
                 System.out.println("转换完成！");
             }

    public static void main(String[] args) throws Exception{
            String srcPath = "E:/temp/试题导入.docx";
            String desPath = "E:/temp/test.pdf";
            Word2Pdf(srcPath, desPath);

    }

}
