package com.wook.toy.services.file;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wook.toy.domain.File;
import com.wook.toy.repository.FileRepository;
import com.wook.toy.utility.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FileService {
	
	@Value("${spring.servlet.multipart.location}")
	private String serverPath;

	@Autowired
	FileRepository fileRepository;
	
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal saveFile(List<MultipartFile> mpFileList, BigDecimal boardNumber, HttpServletRequest request) {
		BigDecimal rtnVal = new BigDecimal(0);
		
		for(MultipartFile mpFile : mpFileList) {
			try {
				String orgnName = mpFile.getOriginalFilename();
				String fileExt = orgnName.substring(orgnName.lastIndexOf("."));
				String fileName = UUID.randomUUID().toString() + fileExt;
				String savePath = serverPath + "\\board";
				
				if (!new java.io.File(savePath).exists()) {
	                try{
	                    new java.io.File(savePath).mkdir();
	                } catch(Exception e){
	                	e.getStackTrace();
	                }
	            }
				
				String filePath = savePath + "\\" + fileName;
	            mpFile.transferTo(new java.io.File(filePath));
				
				Date now = new Date();
				SimpleDateFormat formatYmd = new SimpleDateFormat("yyyyMMdd");
				
				File file = new File();
				file.setBoardNumber(boardNumber);
				file.setFilePath("/board/");
				file.setFileExt(fileExt);
				file.setFileName(fileName);
				file.setFileOrgnName(orgnName);
				file.setUseYn("Y");
				file.setUpdtYmd(formatYmd.format(now));
				file.setUpdusrId("ADMIN");
				file.setUpdusrIp(CommonUtil.getClientIp(request));
				
				rtnVal = fileRepository.save(file).getFileNumber();
			} catch(Exception e) {
				return new BigDecimal(0);
			}
		}
		
		return rtnVal;
	}
	
	
	@Transactional
	public File getFileInfo(BigDecimal fileNumber) {
		return fileRepository.findByFileNumber(fileNumber);
	}
	
	
	@Transactional
	public List<File> getFileList(BigDecimal boardNumber) {
		return fileRepository.findByBoardNumberAndUseYn(boardNumber, "Y");
	}
	
	
	@Transactional
	public void deleteFile(List<String> deleteFileList) {
		for(String str : deleteFileList) {
			BigDecimal fileNumber = new BigDecimal(str);
			
			File file = getFileInfo(fileNumber);
			file.setUseYn("N");
			
			fileRepository.save(file);
		}
	}
}
	