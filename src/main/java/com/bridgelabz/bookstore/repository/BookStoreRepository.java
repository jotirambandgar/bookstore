package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookStoreRepository extends JpaRepository<BookDetails, Integer> {

    @Query(value = "select * from book_details where author_name LIKE %:keyword% OR book_name LIKE %:keyword%", nativeQuery = true)
    List<BookDetails> findByAttribute(@Param("keyword") String keyword);

    @Query(value = "select count(*) from book_details",nativeQuery = true)
    int getCountOfBooks();

    @Query(value = "select count(*) from book_details where author_name LIKE %:keyword% OR book_name LIKE %:keyword%",nativeQuery = true)
    int getCountOfSearchBooks(@Param("keyword") String keyword);

    @Modifying
    @Transactional
    @Query(value = "update book_details set no_of_copies = no_of_copies - :noOfCopies where id = :bookIds", nativeQuery = true)
    void updateStock(@Param("noOfCopies") int noOfCopies, @Param("bookIds") int bookIds);

    Optional<BookDetails> findBybookName(String bookName);
}