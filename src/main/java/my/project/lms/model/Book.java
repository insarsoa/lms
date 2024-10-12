package my.project.lms.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    public String isbn;
    public String title;
    public String author;
    public int publicationYear;
    public int availableCopies;

    public synchronized void borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    public synchronized void returnBook() {
        availableCopies++;
    }
}