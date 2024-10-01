package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherUpdateDTO {

    @NotNull(message = "Ο κωδικός δεν μπορεί να μην υπάρχει.")
    private Long id;

    @NotNull(message = "Το όνομα δεν μπορεί να μην υπάρχει.")
    @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    private String firstname;

    @NotNull(message = "Το επώνυμο δεν μπορεί να μην υπάρχει.")
    @Size(min = 2, max = 255, message = "Το επώνυμο πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    private String lastname;

    @NotNull(message = "Το ΑΦΜ δεν μπορεί να μην υπάρχει.")
    @Size(min = 9, message = "Το ΑΦΜ πρέπει να περιέχει τουλάχιστον 9 ψηφία.")
    private String vat;
}
