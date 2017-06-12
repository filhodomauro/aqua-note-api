package aquanote.repository

import aquanote.domain.Aquarium
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by maurofilho on 03/06/17.
 */
interface AquariumRepository:MongoRepository<Aquarium, String> {
}
