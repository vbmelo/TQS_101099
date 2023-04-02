package feature.book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hascode.tutorial.cucumber.book.Book;
import com.hascode.tutorial.cucumber.book.Library;

import cucumber.api.Format;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;

public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();

	@ParameterType("([0-9]{2})-([0-9]{2})-([0-9]{4})")
	public LocalDate iso8601Date(String year, String month, String day){
		return Utils.localDateFromDateParts(year, month, day);
	}
	@Given("book with the title {String}, written by {String}, published in {iso8601Date}")
	public void addNewBook(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}

	@When("the customer searches for books published between (\\d+) and (\\d+)$")
	public void setSearchParameters(@Format("yyyy") final Date from, @Format("yyyy") final Date to) {
		result = library.findBooks(from, to);
	}

	@Then("(\\d+) books should have been found$")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}

	@Then("Book (\\d+) should have the title {String}$")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}

//	@DataTableType
//	public Book bookEntry(Map<String, String> tableEntry){
//		return new Book(
//				tableEntry.get("title"),
//				tableEntry.get("author"),
//				Utils.isoTextToLocalDate( tableEntry.get("published") ) );
//	}
}
