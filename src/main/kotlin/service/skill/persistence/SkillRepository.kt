package service.skill.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import service.skill.business.Skill
import java.util.UUID

@Repository
interface SkillRepository : MongoRepository<Skill, UUID>
