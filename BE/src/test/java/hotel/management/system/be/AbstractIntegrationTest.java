package hotel.management.system.be;

import hotel.management.system.be.application.account.repository.AccountTestRepository;
import hotel.management.system.be.application.room.repository.RoomTestRepository;
import hotel.management.system.be.configuration.security.JwtTokenUtil;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application.properties")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected AccountTestRepository accountTestRepository;
    @Autowired
    protected RoomTestRepository roomTestRepository;
    @Autowired
    protected JwtTokenUtil jwtTokenUtil;


}
