package aquanote.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

/**
 * Created by maurofilho on 03/06/17.
 */
@Document
data class Aquarium (
    @Id var id: String? = null,
    var capacity:Int=0,
    var name:String="",
    @CreatedDate var createdDate: LocalDate? = null)
