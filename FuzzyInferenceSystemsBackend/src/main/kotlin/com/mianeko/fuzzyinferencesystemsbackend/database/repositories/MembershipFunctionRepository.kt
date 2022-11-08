package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionDTODb
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.MembershipFunctionTemplateDTODb
import com.mianeko.fuzzyinferencesystemsbackend.database.entities.DBMembershipFunction
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.FunctionSaveException
import com.mianeko.fuzzyinferencesystemsbackend.exceptions.SystemNotExistException
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.MembershipFunctionLookup
import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import io.ebean.Database
import io.ebean.Query
import org.springframework.stereotype.Repository

interface MembershipFunctionRepository : DatabaseRepository<MembershipFunctionDTODb, MembershipFunctionTemplateDTODb, MembershipFunctionLookup>

@Repository
class MembershipFunctionRepositoryImpl(
    val db: Database,
) : MembershipFunctionRepository {
    override fun idExists(id: Int) =
        db.find(DBMembershipFunction::class.java, id) != null

    private fun getBySystemId(systemId: Int): Query<DBMembershipFunction> {
        val sql = "select mf.* " +
                "from membership_function mf join variable v on mf.v_id = v.v_id " +
                "where v.s_id = ? " +
                "union " +
                "select mf2.* " +
                "from membership_function mf2 join consequent c on c.m_id = mf2.m_id join \"rule\" r on r.r_id = c.r_id " +
                "where mf2.v_id is null and r.s_id = ? "
        return db.findNative(DBMembershipFunction::class.java, sql)
            .setParameter(1, systemId)
            .setParameter(2, systemId)
    }

    private fun getByVariableId(variableId: Int, systemId: Int): Query<DBMembershipFunction> {
        val sql = "select mf.* " +
                "from membership_function mf join variable v on mf.v_id = v.v_id " +
                "where v.s_id = ? and v.v_id = ? "
        return db.findNative(DBMembershipFunction::class.java, sql)
                .setParameter(1, systemId)
                .setParameter(2, variableId)
    }

    override fun save(model: MembershipFunctionTemplateDTODb): MembershipFunctionDTODb {
        val lookup = MembershipFunctionLookup(
            systemId = model.systemId,
            membershipFunctionId = model.id
        )

        val dbModel = findOne(lookup)
        if (idExists(model.id) && dbModel == null)
            throw SystemNotExistException(model.id)

        try {
            if (dbModel == null)
                db.save(model.toModelDb())
            else
                db.update(model.toModelDb())
        } catch (e: Exception) {
            throw FunctionSaveException(model.id)
        }

        return findOne(lookup)
            ?: throw FunctionSaveException(model.id)
    }

    override fun delete(lookup: MembershipFunctionLookup) {
        lookup.membershipFunctionId?.let {
            db.delete(DBMembershipFunction::class.java, it)
        }
    }

    override fun findOne(lookup: MembershipFunctionLookup): MembershipFunctionDTODb? {
        val query =
            if (lookup.variableId != null) {
                val sql = "select mf.*\n" +
                        "from membership_function mf join variable v on mf.v_id = v.v_id \n" +
                        "where v.s_id = ? and v.v_id = ? and mf.m_id = ?\n"
                lookup.membershipFunctionId?.let {
                    lookup.variableId.let { it1 ->
                        db.findNative(DBMembershipFunction::class.java, sql)
                            .setParameter(1, lookup.systemId)
                            .setParameter(2, it1)
                            .setParameter(3, it)
                    }
                }
            } else {
                val sql = "select mf.*\n" +
                        "from membership_function mf join variable v on mf.v_id = v.v_id \n" +
                        "where v.s_id = ? and mf.m_id = ?\n"
                lookup.membershipFunctionId?.let {
                        db.findNative(DBMembershipFunction::class.java, sql)
                            .setParameter(1, lookup.systemId)
                            .setParameter(2, it)
                    }
            }
        return query?.findOne()?.let { MembershipFunctionDTODb.fromModelDb(it) }
    }

    override fun findAll(lookup: MembershipFunctionLookup, pageSettings: PageSettings): List<MembershipFunctionDTODb> {
        val query = if (lookup.variableId != null)
            getByVariableId(lookup.variableId, lookup.systemId)
        else
            getBySystemId(lookup.systemId)

        return query
            .setMaxRows(pageSettings.size)
            .setFirstRow(pageSettings.page * pageSettings.size)
            .findList().map { MembershipFunctionDTODb.fromModelDb(it) }
    }
}

