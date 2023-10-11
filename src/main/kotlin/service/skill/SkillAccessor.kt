package service.skill

import org.springframework.stereotype.Service
import service.skill.business.SkillService
import java.util.UUID

@Service
class SkillAccessor(
    private val service: SkillService
) {
    fun getById(skillId: UUID): SkillDto? =
        service.get(skillId)?.toDto()
}
