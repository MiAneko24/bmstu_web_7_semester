package com.mianeko.fuzzyinferencesystemsbackend.objectBuilders.DTONet

import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.FuzzySystemTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTODb.enums.SpecializationTypeDTONet
import com.mianeko.fuzzyinferencesystemsbackend.DTONet.SystemTemplateDTONet

class SystemTemplateDTONetBuilder  private constructor(
    private var id: Int = defaultId,
    private var name: String = defaultName,
    private var type: FuzzySystemTypeDTONet = defaultType,
    private var specializationType: SpecializationTypeDTONet = defaultSpecializationType,
){
    fun build() =
        SystemTemplateDTONet(
            id = this.id,
            name = this.name,
            type = this.type,
            specializationType = this.specializationType
        )

    fun withId(id: Int): SystemTemplateDTONetBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): SystemTemplateDTONetBuilder {
        this.name = name
        return this
    }

    fun withType(type: FuzzySystemTypeDTONet): SystemTemplateDTONetBuilder {
        this.type = type
        return this
    }

    fun withSpecializationType(specializationType: SpecializationTypeDTONet): SystemTemplateDTONetBuilder {
        this.specializationType = specializationType
        return this
    }

    companion object {
        var defaultId: Int = 1
        var defaultName: String = "System"
        var defaultType: FuzzySystemTypeDTONet = FuzzySystemTypeDTONet.Mamdani
        var defaultSpecializationType: SpecializationTypeDTONet = SpecializationTypeDTONet.Chemistry

        fun nSystemTemplateDTONet() = SystemTemplateDTONetBuilder()
    }
}