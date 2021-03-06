import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 李綦睿 on 2017-03-08 0008.
 */
public class pdf2jpg {
    public final  static String  IMG_TYPE_JPG = "jpg";
    public final  static String  IMG_TYPE_PNG = "png";
    public static void main( String[] args ) throws IOException
    {
        pdf2jpg app = new pdf2jpg();
        app.pdf2img("E:\\temp\\test.pdf", "E:\\temp\\",IMG_TYPE_JPG);
//          PDDocument pdDocument = app.pdfInfo("D:\\api.pdf");



    }


    /**
     *
     * @param pdfPath pdf文件的路径
     * @param savePath 图片保存的地址
     * @param imgType 图片保存方式
     */
    public void pdf2img(String pdfPath,String savePath,String imgType){
        String fileName = pdfPath.substring(pdfPath.lastIndexOf("\\")+1, pdfPath.length());
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        InputStream  is = null;
        PDDocument pdDocument = null;
        try {
            is = new BufferedInputStream(new FileInputStream(pdfPath));
            PDFParser parser = new PDFParser(is);
            parser.parse();
            pdDocument = parser.getPDDocument();
            List<PDPage> pages = pdDocument.getDocumentCatalog().getAllPages();
            for (int i = 0; i < pages.size(); i++) {
                String saveFileName = savePath+"\\"+fileName+i+"."+imgType;
                PDPage page =  pages.get(i);
                pdfPage2Img(page,saveFileName,imgType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(pdDocument != null){
                try {
                    pdDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * pdf页转换成图片
     * @param page
     * @param saveFileName
     * @throws IOException
     */
    public void pdfPage2Img(PDPage page,String saveFileName,String imgType) throws IOException{
        BufferedImage img_temp  = page.convertToImage();
        Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix(imgType);
        ImageWriter writer = (ImageWriter) it.next();
        ImageOutputStream imageout = ImageIO.createImageOutputStream(new FileOutputStream(saveFileName));
        writer.setOutput(imageout);
        writer.write(new IIOImage(img_temp, null, null));
    }



    public PDDocument pdfInfo(String filePath) throws IOException{
        InputStream is  = new BufferedInputStream(new FileInputStream(filePath));
        PDFParser parser = new PDFParser(is);
        parser.parse();
        PDDocument pdDocument =  parser.getPDDocument();
        System.out.println("pageNum:"+pdDocument.getNumberOfPages());
        return pdDocument;
    }





    public void createPdf() throws COSVisitorException, IOException{
        PDDocument document = new PDDocument();
        PDPage blankPage = new PDPage();
        document.addPage(blankPage);
        document.save("E:\\temp\\test.pdf");
        document.close();
    }
}
