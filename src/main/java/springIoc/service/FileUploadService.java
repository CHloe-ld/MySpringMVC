package springIoc.service;

import jdk.internal.util.xml.impl.Input;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileUploadService {
    String outputFilePath = "/upload";

    public FileUploadService(List<FileItem> list, String path) throws IOException {
        for(FileItem item: list){
            // 如果fileitem为文件表单字段，则获得上传的文件名称并存储
            if(!item.isFormField()){
                String fileName = item.getName();
                System.out.println(fileName);
                if(fileName == null)
                    continue;
                // 只获取上传文件路径的文件名部分
                fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                // 获得输入流
                InputStream inputStream = item.getInputStream();
                // 创建输出流

                FileOutputStream fileOutputStream = new FileOutputStream(path + "\\" +fileName);
                // 创建缓冲区
                byte buffer[] = new byte[1024];
                int length = 0;
                // 将输入流读入到缓冲区中
                while((length = inputStream.read(buffer))>0){
                    fileOutputStream.write(buffer, 0, length);
                }
                // 关闭输入流
                inputStream.close();
                // 关闭输出流
                fileOutputStream.close();
                // 删除临时文件
                item.delete();
            }
        }
    }
}
