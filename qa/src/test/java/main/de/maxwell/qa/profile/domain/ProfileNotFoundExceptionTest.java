package main.de.maxwell.qa.profile.domain;

import de.maxwell.qa.domain.profile.ProfileNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileNotFoundExceptionTest {
    @Test
    public void testException() {
        ProfileNotFoundException exception = new ProfileNotFoundException(1L);

        assertThat(exception.getMessage()).isEqualTo("Could not find profile with id 1");
    }
}
