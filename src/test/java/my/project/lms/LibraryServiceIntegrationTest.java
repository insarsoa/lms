package my.project.lms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Example book data
    private String exampleBookJson = """
        {
            "isbn": "12345",
            "title": "Test Book",
            "author": "Test Author",
            "publicationYear": 2023,
            "availableCopies": 10
        }
    """;

    @Test
    void testAddBook() throws Exception {
        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U")
                        .content(exampleBookJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Book added successfully."));
    }

    @Test
    void testFindBookByISBN() throws Exception {
        // First, add the book
        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U")
                        .content(exampleBookJson))
                .andExpect(status().isOk());

        // Now, fetch the book by ISBN
        mockMvc.perform(get("/api/library/books/12345")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("12345"))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));
    }

    @Test
    void testRemoveBook() throws Exception {
        // First, add the book
        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U")
                        .content(exampleBookJson))
                .andExpect(status().isOk());

        // Now, delete the book
        mockMvc.perform(delete("/api/library/books/12345")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book removed successfully."));
    }

    @Test
    void testBorrowBookSuccess() throws Exception {
        // First, add the book
        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U")
                        .content(exampleBookJson))
                .andExpect(status().isOk());

        // Now, borrow the book
        mockMvc.perform(post("/api/library/books/borrow/12345")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book borrowed successfully."));
    }

    @Test
    void testBorrowBookFailure() throws Exception {
        // Add a book with zero available copies
        String zeroCopyBookJson = """
            {
                "isbn": "67890",
                "title": "Unavailable Book",
                "author": "Test Author",
                "publicationYear": 2023,
                "availableCopies": 0
            }
        """;
        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U")
                        .content(zeroCopyBookJson))
                .andExpect(status().isOk());

        // Try to borrow it
        mockMvc.perform(post("/api/library/books/borrow/67890")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No available copies for book with ISBN 67890."));
    }

    @Test
    void testReturnBook() throws Exception {
        // First, add the book
        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U")
                        .content(exampleBookJson))
                .andExpect(status().isOk());

        // Now, borrow the book
        mockMvc.perform(post("/api/library/books/borrow/12345")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U"))
                .andExpect(status().isOk());

        // Finally, return the book
        mockMvc.perform(post("/api/library/books/return/12345")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzI4NzI5NzM3LCJleHAiOjE3Mjg3NjU3Mzd9.aXdRUTlLz5g7XZrvFD951C8kqj2VF6H0jecNU8fLW8U"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully."));
    }
}
