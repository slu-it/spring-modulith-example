package service.skill.api

import service.skill.business.Skill
import java.util.UUID

data class SkillRepresentation(
    val id: UUID,
    val label: String,
)

fun Skill.toRepresentation(): SkillRepresentation =
    SkillRepresentation(
        id = id,
        label = data.label,
    )
