package service.skill

import service.skill.business.Skill
import java.util.UUID

data class SkillDto(
    val id: UUID,
    val label: String
)

fun Skill.toDto(): SkillDto =
    SkillDto(
        id = id,
        label = data.label
    )
