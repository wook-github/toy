package com.wook.toy.services.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.wook.toy.domain.File;
import com.wook.toy.services.file.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CommonService {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(CommonService.class);
	
	@Value("${spring.servlet.multipart.location}")
	private String serverPath;

	@Autowired
	FileService fileService;
	
	public ResponseEntity<Resource> downloadFile(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response ) throws IOException {

		File file = fileService.getFileInfo(new BigDecimal((String) param.get("fileNumber")));
		String savePath = "";
		String fileName = "";
		String fileOrgnName = "";
		String filePath = "";
		if(file == null ) {
			//해당파일의 정보가 없는경우
			savePath = serverPath;
			fileName = "no_img.png";
			fileOrgnName = "no_img.png";
			filePath = savePath + fileName;
		} else {
			savePath = serverPath + file.getFilePath();
			fileName = file.getFileName(); 
			fileOrgnName = file.getFileOrgnName();
			filePath = savePath + fileName;
		}
		
		Resource resource = new UrlResource(Paths.get(filePath).toUri());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode(fileOrgnName, "UTF-8") + "\"")
				.body(resource);
	}
}
