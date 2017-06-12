package aquanote.integration

import aquanote.config.IntegrationBase
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Created by maurofilho on 03/06/17.
 */
@Rollback
class AquariumTest : IntegrationBase() {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Before
    fun setup() {
        mongoTemplate.dropCollection("aquarium")
    }

    @Test
    fun testThatAnAquariumIsSavedAndFound() {
        val aquaName = "Biótopo Asiático"
        val aquarium = """
            {
                "capacity":"200",
                "name":"$aquaName"
            }
        """
        val location = this.mvc
            .perform(
                post("/aquariums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aquarium)
            )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location",  matchesPattern("\\/aquariums\\/([0-9A-Z])\\w+")))
            .andReturn().response.getHeader("Location")

        this.mvc
            .perform(
                get(location)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.capacity").value("200"))
            .andExpect(jsonPath("$.name").value(aquaName))
            .andExpect(jsonPath("$.createdDate").isNotEmpty)
    }

    @Rollback
    @Test
    fun testThatAnAquariumIsSavedAndUpdated() {
        val aquaName = "Tropical com Plantas"
        var aquarium = """
            {
                "capacity":"100",
                "name":"$aquaName"
            }
        """
        val location = this.mvc
            .perform(
                post("/aquariums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aquarium)
            )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location",  matchesPattern("\\/aquariums\\/([0-9A-Z])\\w+")))
            .andReturn().response.getHeader("Location")

        aquarium = """
            {
                "capacity":"300",
                "name":"$aquaName"
            }
        """
        this.mvc
            .perform(
                put(location)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aquarium)
            )
            .andExpect(status().isOk)

        this.mvc
            .perform(
                get(location)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.capacity").value("300"))
            .andExpect(jsonPath("$.name").value(aquaName))
    }

    @Test
    fun testThatAnAquariumIsSavedAndDeleted() {
        val aquaName = "Marinho"
        val aquarium = """
            {
                "capacity":"1000",
                "name":"$aquaName"
            }
        """
        val location = this.mvc
            .perform(
                post("/aquariums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aquarium)
            )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location",  matchesPattern("\\/aquariums\\/([0-9A-Z])\\w+")))
            .andReturn().response.getHeader("Location")

        this.mvc
            .perform(
                delete(location)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent)

        this.mvc
            .perform(
                get(location)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound)
    }

    @Test
    fun testThatANonexistentAquariumIsNotFound() {
        this.mvc
            .perform(
                get("/aquariums/777")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound)
    }

    @Test
    fun testThatManyAquariumsAreListed() {
        val aquaName = "Biótopo Asiático"
        var aquarium = """
            {
                "capacity":"200",
                "name":"$aquaName"
            }
        """
        this.mvc
            .perform(
                post("/aquariums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aquarium)
            )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location",  matchesPattern("\\/aquariums\\/([0-9A-Z])\\w+")))

        val aquaName2 = "Biótopo Amazonense"
        aquarium = """
            {
                "capacity":"300",
                "name":"$aquaName2"
            }
        """

        this.mvc
            .perform(
                post("/aquariums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aquarium)
            )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location",  matchesPattern("\\/aquariums\\/([0-9A-Z])\\w+")))

        this.mvc
            .perform(
                get("/aquariums")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isNotEmpty)
            .andExpect(jsonPath("$[0].name").value(aquaName))
            .andExpect(jsonPath("$[1].name").value(aquaName2))
    }
}
