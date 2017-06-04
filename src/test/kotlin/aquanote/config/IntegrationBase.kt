package aquanote.config

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

/**
 * Created by maurofilho on 03/06/17.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
abstract class IntegrationBase {

    @Autowired
    lateinit var mvc: MockMvc

}
