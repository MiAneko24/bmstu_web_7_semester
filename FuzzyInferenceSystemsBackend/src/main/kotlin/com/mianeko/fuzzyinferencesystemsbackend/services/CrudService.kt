package com.mianeko.fuzzyinferencesystemsbackend.services

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings
import java.util.*

interface CrudService<Model, Template, LookupEntity> {
    fun create(model: Template) : Model

    fun get(lookupEntity: LookupEntity) : Model

    fun getAll(lookupEntity: LookupEntity, pageSettings: PageSettings): List<Model>

    fun update(model: Template) : Model

    fun delete(lookupEntity: LookupEntity)

    fun generateId(): Int {
        val uid = UUID.randomUUID()
        var str = "" + uid
        val id = str.hashCode()
        val filterStr = "" + id
        str = filterStr.replace("-".toRegex(), "")
        return str.toInt()
    }
}