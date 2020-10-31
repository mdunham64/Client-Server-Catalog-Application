package edu.ucdenver.domainlogic;

import java.time.LocalDate;

public class Books extends Product{
    private String authorName;
    private LocalDate publicationDate;
    private int numOfPages;

    public Books(String productID, String productName, String brandName, String productDescription, LocalDate dateofIncorporation, String authorName, LocalDate publicationDate, int numOfPages){
        super(productID,productName,brandName,productDescription,dateofIncorporation);
        this.authorName = authorName;
        this.publicationDate = publicationDate;
        this.numOfPages = numOfPages;
        this.addCategory("BOOKS", "124", "BOOK Products");

    }
    @Override
    public String getProductDetails(){
        return String.format("---Books---%n%sAuthor: %s%nPublication Date: %s%n# of pgs: %s", super.getProductDetails(),
                this.getAuthorName(), this.getPublicationDate(), this.getNumOfPages());
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
