package woo;

/**
 * Class for book products. These products have title, author and isbn
 * besides all default Product class attributes.
 */
public class Book extends Product {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202012040059L;

    /** Book's title. */
    private String _bookTitle;

    /** Book's author. */
    private String _bookAuthor;

    /** Book's ISBN. */
    private String _bookISBN;

    /**
     * Create book product.
     * 
     * @param supplier
     *          book supplier.
     * @param id
     *          book product ID.
     * @param price
     *          book price.
     * @param criticalLevel
     *          book critical stock level.
     * @param title
     *          book title.
     * @param author
     *          book author.
     * @param isbn
     *          book isbn.
     * @param amount
     *          book qty.
     */
    public Book(Supplier sup, String prodID, int price, int criticalLevel, String title, String author, String isbn, int amount) {
        super(sup, prodID, price, criticalLevel, amount);
        _bookTitle = title;
        _bookAuthor = author;
        _bookISBN = isbn;
    }

    /**
     * @return the book's title.
     */
    public String getTitle() {
        return _bookTitle;
    }

    /**
     * @return the book's author.
     */
    public String getAuthor() {
        return _bookAuthor;
    }

    /**
     * @return the book's isbn.
     */
    public String getISBN() {
        return _bookISBN;
    }

    /**
     * @return the book's payment period variable.
     */
    public int getN() {
        return 3;
    }

    /**
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        return "BOOK" + super.toString() + "|" + getTitle() + "|" + getAuthor() + "|" + getISBN();
    }
}
