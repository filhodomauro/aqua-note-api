package aquanote.controller

import aquanote.domain.Aquarium
import aquanote.service.AquariumService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * Created by maurofilho on 03/06/17.
 */
@RestController
class AquariumController {

    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var aquariumService:AquariumService

    @GetMapping("/aquariums")
    fun findAll(): ResponseEntity<List<Aquarium>> {
        return ResponseEntity.ok(aquariumService.findAll())
    }

    @GetMapping("/aquariums/{id}")
    fun find(@PathVariable id:String): ResponseEntity<Aquarium?> {
        val aquarium: Aquarium? = aquariumService.findOne(id)
        if (aquarium == null) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(aquarium)
    }

    @PostMapping("/aquariums")
    fun create(@RequestBody aquarium: Aquarium): ResponseEntity<Aquarium> {
        return ResponseEntity.created(uri(aquariumService.save(aquarium))).build()
    }

    @PutMapping("/aquariums/{id}")
    fun update(@PathVariable id:String, @RequestBody aquarium: Aquarium): ResponseEntity<Aquarium> {
        aquarium.id = id
        return ResponseEntity.ok(aquariumService.save(aquarium))
    }

    @DeleteMapping("/aquariums/{id}")
    fun delete(@PathVariable id:String): ResponseEntity<Any> {
        aquariumService.delete(id)
        return ResponseEntity.noContent().build()
    }

    fun uri(aquarium: Aquarium): URI {
        return URI.create("/aquariums/" + aquarium.id)
    }

}
