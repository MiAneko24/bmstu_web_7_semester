package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.FuzzySystemTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.SpecializationTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.SystemDTONet

class SystemDTONetBuilder private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var type: FuzzySystemTypeDTONet = defaultType,
    private var specializationType: SpecializationTypeDTONet = defaultSpecializationType
) {
    fun build() =
        SystemDTONet(
            id = this.id,
            name = this.name,
            type = this.type,
            specializationType = this.specializationType
        )

    fun withId(id: Int): SystemDTONetBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): SystemDTONetBuilder {
        this.name = name
        return this
    }

    fun withType(type: FuzzySystemTypeDTONet): SystemDTONetBuilder {
        this.type = type
        return this
    }

    fun withSpecializationType(specializationType: SpecializationTypeDTONet): SystemDTONetBuilder {
        this.specializationType = specializationType
        return this
    }

    companion object {
        var defaultId: Int = 1
        var defaultName: String = "System"
        var defaultType: FuzzySystemTypeDTONet = FuzzySystemTypeDTONet.Mamdani
        var defaultSpecializationType: SpecializationTypeDTONet = SpecializationTypeDTONet.Chemistry

        fun nSystemDTONet() = SystemDTONetBuilder()
    }
}