package com.gajava.library.controller.dto.book;

import com.gajava.library.controller.dto.BaseDto;
import com.gajava.library.controller.dto.author.AuthorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class BookDto extends BaseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private String genre;
    private LocalDate year;
    private String description;

    @NotNull
    private Integer quantity;

    @NotNull
    private Boolean availability;

    @NotNull
    private Set<AuthorDto> authors;

}
