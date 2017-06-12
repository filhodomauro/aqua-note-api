package aquanote.service

import aquanote.domain.Aquarium
import aquanote.repository.AquariumRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by maurofilho on 03/06/17.
 */
@Service
class AquariumService {

    @Autowired
    lateinit var aquariumRepository:AquariumRepository

    fun save(aquarium: Aquarium): Aquarium {
        return aquariumRepository.save(aquarium)
    }

    fun findOne(id: String): Aquarium? {
        return aquariumRepository.findOne(id)
    }

    fun findAll(): List<Aquarium> {
        return aquariumRepository.findAll()
    }

    fun delete(id: String) {
        aquariumRepository.delete(id)
    }
}
