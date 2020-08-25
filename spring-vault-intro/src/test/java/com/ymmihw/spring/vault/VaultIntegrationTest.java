package com.ymmihw.spring.vault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URISyntaxException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * These tests are requiring the {@code vault} command to be installed and available in the
 * executing platform. So, if you intend to run them in your environment, the please install the
 * vault and then run the ignored tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CredentialsService.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = VaultTestConfiguration.class,
    loader = AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class VaultIntegrationTest {

  @Autowired
  private CredentialsService credentialsService;

  /**
   * Test to secure credentials.
   *
   * @throws URISyntaxException
   */
  @Test
  public void givenCredentials_whenSecureCredentials_thenCredentialsSecured()
      throws URISyntaxException {
    // Given
    Credentials credentials = new Credentials("username", "password");

    // When
    credentialsService.secureCredentials(credentials);

  }

  /**
   * Test to access credentials
   *
   * @throws URISyntaxException
   */
  @Test
  public void whenAccessCredentials_thenCredentialsRetrieved() throws URISyntaxException {
    Credentials credentials = new Credentials("username", "password");

    // When
    credentialsService.secureCredentials(credentials);
    // Given
    credentials = credentialsService.accessCredentials();

    // Then
    assertNotNull(credentials);
    assertEquals("username", credentials.getUsername());
    assertEquals("password", credentials.getPassword());
  }

}
