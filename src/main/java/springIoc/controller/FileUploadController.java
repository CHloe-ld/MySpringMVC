package springIoc.controller;

import org.apache.commons.fileupload.FileItem;
import springIoc.annotation.Controller;
import springIoc.annotation.RequestMapping;
import springIoc.service.FileUploadService;
import springIoc.view.View;

import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/myMvc/file")
public class FileUploadController {
    @RequestMapping("/upload")
    public View fileUpload(List<FileItem> list, String path){
        boolean success;
        try{
            FileUploadService fileUploadService = new FileUploadService(list, path);
            success = true;
        }
        catch(Exception e){
            success = false;
        }
        View view = new View("/WEB-INF/file_result.jsp");
        if(success){
            view.addAttr("result", "上传成功");
        }
        else{
            view.addAttr("result", "上传失败");
        }
        return view;
    }
}
