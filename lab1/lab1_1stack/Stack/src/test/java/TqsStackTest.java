import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import org.example.TqsStack;
import org.junit.jupiter.api.*;

public class TqsStackTest {

	private TqsStack<String> stackDePalavras;

	@BeforeEach // Execute before each test
	public void testBeforeEach() throws Exception {
		stackDePalavras = new TqsStack<>();
	}

	@AfterEach
	public void clear() {
		stackDePalavras.clear();
	}

	@DisplayName("A stack está vazia na construção")
	@Test
	void isEmpty() {
		assertTrue(stackDePalavras.isEmpty());

		stackDePalavras.push("Teste vazio");
		assertFalse(stackDePalavras.isEmpty());

		stackDePalavras.pop();
		assertTrue(stackDePalavras.isEmpty());
	}

	@DisplayName("A Stack tem tamanho 0 na construção")
	@Test
	void size() {
		assertEquals(0, stackDePalavras.size());

		stackDePalavras.push("Test Size");
		assertEquals(1, stackDePalavras.size());

		stackDePalavras.pop();
		assertEquals(0, stackDePalavras.size());

	}

	@DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
	@Test
	void pushThenPeek() {

		int stacksize;
		String strpeek;

		strpeek = "Test Peek";
		stackDePalavras.push(strpeek);
		stacksize = stackDePalavras.size();

		assertTrue((stackDePalavras.peek() == strpeek) && (stackDePalavras.size() == stacksize));

	}

	@DisplayName("After n pushes para uma stack vazia, n > 0, a stack não está vazia e seu tamanho é n")
	@Test
	void pushFromEmpty() {

		assertTrue(stackDePalavras.isEmpty());

		stackDePalavras.push("a");
		stackDePalavras.push("b");
		stackDePalavras.push("c");

		assertEquals(stackDePalavras.size(), 3);
		assertFalse(stackDePalavras.isEmpty());
	}

	@DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
	@Test
	void pushForBound() {
		TqsStack<String> boundStack = new TqsStack<>(3);

		boundStack.push("a");
		boundStack.push("b");
		boundStack.push("c");

		assertThrows(IllegalStateException.class, () -> boundStack.push("d"));
	};

	@DisplayName("Se um push x e depois pop, o valor popado é x")
	@Test
	void pushThenPop() {

		String strpush = "Test Pop";
		stackDePalavras.push(strpush);
		assertTrue(stackDePalavras.pop().equals(strpush));
	}

	@DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
	@Test
	void emptyAfterPop() {

		stackDePalavras.push("a");
		stackDePalavras.push("b");
		stackDePalavras.push("c");

		assertEquals(stackDePalavras.size(), 3);
		assertFalse(stackDePalavras.isEmpty());

		stackDePalavras.pop();
		stackDePalavras.pop();
		stackDePalavras.pop();

		assertTrue((stackDePalavras.size() == 0) && (stackDePalavras.isEmpty()));
	}

	@DisplayName("Pop de uma stack vazia throw: NoSuchElementException")
	@Test
	void popFromEmpty() {
		assertThrows(NoSuchElementException.class, () -> stackDePalavras.pop());
	}

	@DisplayName("peek em uma stack vazia throw: NoSuchElementException")
	@Test
	void peekFromEmpty() {
		assertThrows(NoSuchElementException.class, () -> stackDePalavras.peek());
	}

}