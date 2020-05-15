package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.property.FileStorageProperty;
import com.bridgelabz.bookstore.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminBookService implements IAdminBookService {

    private Path fileStorageLocation;

    @Autowired
    BookStoreRepository bookStoreRepository;

    @Autowired
    FileStorageProperty fileStorageProperty;

    @Override
    public BookDetails addBook(BookDTO bookDTO) {
        BookDetails bookDetails = new BookDetails(bookDTO);
        Optional<BookDetails> byBookName = bookStoreRepository.findBybookName(bookDTO.bookName);
        BookDetails info = bookStoreRepository.save(bookDetails);
        return info;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            this.fileStorageLocation = Paths.get(fileStorageProperty.getUploadDir())
                    .toAbsolutePath()
                    .normalize();
            Files.createDirectories(this.fileStorageLocation);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new BookStoreException(BookStoreException.ExceptionType.INVALID_FILE_NAME, "INVALID_FILE_NAME");
            }
            fileName = (id+"-"+fileName);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String imageResponseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("bookstore/image/")
                    .path(fileName)
                    .toUriString();
            return imageResponseUrl;
        } catch (IOException ex) {
            throw new BookStoreException(BookStoreException.ExceptionType.FILE_NOT_STORED, "FILE_NOT_STORE");
        } catch (NullPointerException n) {
            throw new BookStoreException(BookStoreException.ExceptionType.DIRECTORY_NOT_FOUND, "DIRECTORY_NOT_FOUND");
        }
    }
}
