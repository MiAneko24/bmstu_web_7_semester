package com.mianeko.fuzzyinferencesystemsbackend.database.repositories

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.PageSettings


interface DatabaseRepository<T, Template, LookupEntity> {
    fun idExists(id: Int): Boolean
    fun save(model: Template): T
    fun delete(lookup: LookupEntity)

    fun findOne(lookup: LookupEntity): T?
    fun findAll(lookup: LookupEntity, pageSettings: PageSettings): List<T>
}