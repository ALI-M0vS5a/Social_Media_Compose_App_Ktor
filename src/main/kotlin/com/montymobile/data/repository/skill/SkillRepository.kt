package com.montymobile.data.repository.skill

import com.montymobile.data.models.Skill

interface SkillRepository {
    suspend fun getSkill(): List<Skill>
}