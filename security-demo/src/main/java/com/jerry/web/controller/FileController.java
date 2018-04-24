package com.jerry.web.controller;

import com.jerry.dto.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 20:47
 * Description:
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    private static final String FOLDER = "D:\\Upload";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {

        log.info("文件名:" + file.getName());
        log.info("原始文件名:" + file.getOriginalFilename());
        log.info("文件大小:" + file.getSize());

        File localFile = new File(FOLDER, new Date().getTime() + ".txt");

        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (InputStream in = new FileInputStream(new File(FOLDER, id + ".txt"));
             OutputStream out = response.getOutputStream()) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(in, out);

            out.flush();
        }
    }

}
