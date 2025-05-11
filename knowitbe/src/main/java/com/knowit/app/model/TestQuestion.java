
package com.knowit.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_questions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private PracticeTest test;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String question;

    @NotBlank
    @Column(columnDefinition = "TEXT", name = "option_a")
    private String optionA;

    @NotBlank
    @Column(columnDefinition = "TEXT", name = "option_b")
    private String optionB;

    @NotBlank
    @Column(columnDefinition = "TEXT", name = "option_c")
    private String optionC;

    @NotBlank
    @Column(columnDefinition = "TEXT", name = "option_d")
    private String optionD;

    @NotBlank
    @Pattern(regexp = "[A-D]")
    @Column(name = "correct_answer")
    private String correctAnswer;

    @NotNull
    private Integer points = 1;
}
