package com.montymobile.data.repository.skill

import com.montymobile.data.models.Skill
import org.litote.kmongo.coroutine.CoroutineDatabase

class SkillRepositoryImpl(
    private val db: CoroutineDatabase
) : SkillRepository {
    private val skills = db.getCollection<Skill>()

    override suspend fun getSkill(): List<Skill> {
        return skills.find().toList()
    }
}