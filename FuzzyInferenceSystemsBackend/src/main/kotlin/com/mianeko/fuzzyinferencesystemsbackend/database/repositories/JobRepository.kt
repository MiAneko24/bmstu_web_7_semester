package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.JobDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBJob
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.JobSaveException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import io.ebean.Database
import org.springframework.stereotype.Repository
import java.util.*

interface JobRepository {
    fun getAll(systemId: Int, pageSettings: PageSettings): List<JobDTODb>

    fun get(jobId: UUID): JobDTODb?

    fun save(jobDTODb: JobDTODb): JobDTODb

    fun delete(jobId: UUID)
}

@Repository
class JobRepositoryImpl(
    private val db: Database
): JobRepository {
    override fun getAll(systemId: Int, pageSettings: PageSettings): List<JobDTODb> {
        return db
            .find(DBJob::class.java)
            .where()
            .eq("s_id", systemId)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .setMaxRows(pageSettings.size)
            .findList()
            .map { JobDTODb.fromModelDb(it) }
    }

    override fun get(jobId: UUID): JobDTODb? {
        return db.find(DBJob::class.java, jobId)
            ?.let{ JobDTODb.fromModelDb(it) }
    }



    override fun save(jobDTODb: JobDTODb): JobDTODb {
        if (db.find(DBJob::class.java, jobDTODb.id) != null)
            throw JobSaveException(jobDTODb.id)

        try {
            db.save(jobDTODb.toModelDb())
        } catch (e: Exception) {
            throw JobSaveException(jobDTODb.id)
        }

        return db.find(DBJob::class.java, jobDTODb.id)
            ?.let{ JobDTODb.fromModelDb(it) }
            ?: throw JobSaveException(jobDTODb.id)
    }

    override fun delete(jobId: UUID) {
        try {
            db.delete(DBJob::class.java, jobId)
        } catch (_: Exception) {

        }
    }

}