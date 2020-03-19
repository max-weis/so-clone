package main.de.maxwell.qa.question.domain;

import de.maxwell.qa.domain.question.QuestionBuilder;
import de.maxwell.qa.domain.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionBuilderTest {

    private QuestionBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new QuestionBuilder();
    }

    @Test
    public void testBuild(){
        Question question = builder.withUserID(0L).build();
    }
}