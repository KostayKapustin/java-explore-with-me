package ru.practicum.ewmmain.dto.categories;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
<<<<<<< HEAD
=======
import org.springframework.validation.annotation.Validated;
>>>>>>> 272adb929d75788d7d308634bbc55e1727b0d605

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
<<<<<<< HEAD
=======
@Validated
>>>>>>> 272adb929d75788d7d308634bbc55e1727b0d605
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {

    @NotNull(message = "id не может быть Null!")
    private Long id;

    @Size(max = 50)
    @NotNull(message = "Name не может быть Null!")
    private String name;
}
