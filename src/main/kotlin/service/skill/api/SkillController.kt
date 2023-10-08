package service.skill.api

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import service.skill.business.Skill
import service.skill.business.SkillService
import java.util.UUID

@RestController
@RequestMapping("/api/skills")
class SkillController(
    private val service: SkillService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): SkillRepresentation? =
        service.get(id)?.toRepresentation()

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody body: Skill.Data): SkillRepresentation =
        service.create(body).toRepresentation()

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody body: Skill.Data): SkillRepresentation? =
        service.updateData(id, { body })?.toRepresentation()

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        service.delete(id)
    }
}
