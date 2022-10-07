package com.montymobile.service

import com.montymobile.data.models.Skill
import com.montymobile.data.repository.skill.SkillRepository

class SkillService(
    private val repository: SkillRepository
) {
    suspend fun getSkills(): List<Skill> {
        return repository.getSkill()
    }
}