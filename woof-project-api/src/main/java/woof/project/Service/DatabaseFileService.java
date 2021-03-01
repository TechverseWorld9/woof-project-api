package woof.project.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import  woof.project.Exception.FileNotFoundException;
import  woof.project.Exception.FileStorageException;
import  woof.project.Entity.DatabaseFile;

import woof.project.Repository.UserRepository;

@Service
public class DatabaseFileService {

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("static-access")
    public DatabaseFile storeFile(MultipartFile file) {
		// TODO Auto-generated method stub
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

            return userRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}

	/*
	 * public DatabaseFile getFile(String fileId) { return
	 * dbFileRepository.findById(fileId) .orElseThrow(() -> new
	 * FileNotFoundException("File not found with id " + fileId)); }
	 */
}
