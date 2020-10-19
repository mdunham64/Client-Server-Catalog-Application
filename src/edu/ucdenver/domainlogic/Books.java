package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class Books extends Product{
    private String authorName;
    private LocalDate publicationDate;
    private int numOfPages;

    Books(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, String authorName, LocalDate publicationDate, int numOfPages){
        super(productID,productName,brandName,productDescription,dateofIncorporation);
        this.authorName = authorName;
        this.publicationDate = publicationDate;
        this.numOfPages = numOfPages;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
}
