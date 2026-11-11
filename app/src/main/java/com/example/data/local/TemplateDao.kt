package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {
  @Query("SELECT * FROM templates ORDER BY createdAt DESC")
  fun getAllTemplates(): Flow<List<TemplateEntity>>

  @Query("SELECT * FROM templates WHERE generatorType = :type")
  fun getTemplatesByType(type: String): Flow<List<TemplateEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTemplate(template: TemplateEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(templates: List<TemplateEntity>)

  @Update
  suspend fun updateTemplate(template: TemplateEntity)

  @Delete
  suspend fun deleteTemplate(template: TemplateEntity)

  @Query("DELETE FROM templates WHERE id = :id")
  suspend fun deleteById(id: String)
}
