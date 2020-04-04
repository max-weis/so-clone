package main.de.maxwell.qa.profile.domain;

import de.maxwell.qa.domain.profile.Profile;
import de.maxwell.qa.domain.profile.ProfileBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ProfileBuilderTest {

    private ProfileBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new ProfileBuilder();
    }

    @Test
    public void testBuild() {
        LocalDateTime now = LocalDateTime.now();

        Profile profile = builder
                .withUserID(0L)
                .withReputation(0L)
                .withImage(new Byte[0])
                .withFirstName("first")
                .withLastName("last")
                .withDescription("test")
                .withModifiedAt(now)
                .withCreatedAt(now)
                .build();

        assertThat(profile.getUserID()).isEqualTo(0L);
        assertThat(profile.getImage()).isEqualTo(new Byte[0]);
        assertThat(profile.getFirstName()).isEqualTo("first");
        assertThat(profile.getLastName()).isEqualTo("last");
        assertThat(profile.getDescription()).isEqualTo("test");
        assertThat(profile.getCreatedAt()).isEqualTo(now);
        assertThat(profile.getModifiedAt()).isEqualTo(now);
    }

    @Test
    public void testBuildShouldFailForNullUserID() {
        assertThatNullPointerException().isThrownBy(() -> builder.withUserID(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullImage() {
        assertThatNullPointerException().isThrownBy(() -> builder.withImage(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullReputation() {
        assertThatNullPointerException().isThrownBy(() -> builder.withReputation(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForEmptyFirstName() {
        assertThatIllegalArgumentException().isThrownBy(() -> builder.withFirstName("")
                .build());
    }

    @Test
    public void testBuildShouldFailForNullFirstName() {
        assertThatNullPointerException().isThrownBy(() -> builder.withFirstName(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForEmptyLastName() {
        assertThatIllegalArgumentException().isThrownBy(() -> builder.withLastName("")
                .build());
    }

    @Test
    public void testBuildShouldFailForNullLastName() {
        assertThatNullPointerException().isThrownBy(() -> builder.withLastName(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForEmptyDescription() {
        assertThatIllegalArgumentException().isThrownBy(() -> builder.withDescription("")
                .build());
    }

    @Test
    public void testBuildShouldFailForNullDescription() {
        assertThatNullPointerException().isThrownBy(() -> builder.withDescription(null)
                .build());
    }
}
