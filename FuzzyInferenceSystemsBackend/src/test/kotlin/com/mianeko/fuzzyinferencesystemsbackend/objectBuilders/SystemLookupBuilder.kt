package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders

import com.mianeko.fuzzyinferencesystemsbackend.lookupEntities.SystemLookup

class SystemLookupBuilder private constructor(
    private var systemId: Int? = defaultSystemId
){
    fun build() = SystemLookup(
        systemId = this.systemId
    )

    fun withSystemId(systemId: Int?): SystemLookupBuilder {
        this.systemId = systemId
        return this
    }

    companion object {
        val defaultSystemId: Int? = null

        fun nSystemLookup() = SystemLookupBuilder()
    }
}