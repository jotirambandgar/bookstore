package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.enumerator.SortAttribute;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.property.FileStorageProperty;
import com.bridgelabz.bookstore.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookStoreService implements IBookStoreService {

    private static final int PER_PAGE_LIMIT = 12;
    int startLimit;
    int endLimit;

    @Autowired
    BookStoreRepository bookStoreRepository;

    @Autowired
    FileStorageProperty fileStorageProperty;

    @Override
    public List<BookDetails> searchBook(String attribute, int pageNumber) {
        List<BookDetails> byAttribute = bookStoreRepository.findByAttribute(attribute);
        if (byAttribute.isEmpty())
            throw new BookStoreException(BookStoreException.ExceptionType.NO_BOOK_FOUND, "No Book Found");
        setLimits(pageNumber);
        List<BookDetails> booksForSinglePage = getBooksForSinglePage(byAttribute);
        return booksForSinglePage;
    }

    @Override
    public List<BookDetails> getAllBooks(int pageNumber) {
        setLimits(pageNumber);
        List<BookDetails> books = bookStoreRepository.findAll();
        List<BookDetails> booksForSinglePage = getBooksForSinglePage(books);
        if (booksForSinglePage.size() == 0)
            throw new BookStoreException(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, "Max Page Limit Reached");
        return booksForSinglePage;
    }

    @Override
    public int getStoredBookCount(String... attribute) {
        if(attribute.length==0) {
            return bookStoreRepository.getCountOfBooks();
        }
        return bookStoreRepository.getCountOfSearchBooks(attribute[0]);
    }

    @Override
    public SortAttribute[] getSortAttribute() {
       return SortAttribute.values();
    }

    @Override
    public List<BookDetails> getSortedBookData(SortAttribute attribute, int pageNumber) {
        List<BookDetails> sortedData=attribute.getDataSorted(bookStoreRepository);
        setLimits(pageNumber);
        List<BookDetails> booksForSinglePage = getBooksForSinglePage(sortedData);
        if(booksForSinglePage.size() == 0)
            throw new BookStoreException(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, "Enter Valid Page Number");
        return booksForSinglePage;
    }

    public Resource getImageResponse(String imageId) {
        Path path = Paths.get(this.fileStorageProperty.getUploadDir() + imageId);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        }catch (MalformedURLException e){
            throw new BookStoreException(BookStoreException.ExceptionType.INVALID_FILE_PATH,"Invalid_Path");
        }
        return resource;
    }

    private void setLimits(int pageNumber) {
        this.startLimit = ((pageNumber - 1) * PER_PAGE_LIMIT) + 1;
        this.endLimit = (pageNumber * PER_PAGE_LIMIT);
    }

    private List<BookDetails> getBooksForSinglePage(List<BookDetails> listReturned) {
        List<BookDetails> foundList = new ArrayList<>();
        for(int i = startLimit-1; i < endLimit && i < listReturned.size(); i++)
            foundList.add(listReturned.get(i));
        return foundList;
    }
}
