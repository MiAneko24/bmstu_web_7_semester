package com.mianeko.fuzzyinferencesystemsbackend.exceptions

import java.util.*

open class JobException(
    override val message: String?
): IllegalArgumentException(message)

data class JobSaveException(
    val errorId: UUID,
    override val message: String? = "Could not save job with id $errorId"
): JobException(message)

data class JobNotExistException(
    val errorId: UUID,
    override val message: String? = "Job with id $errorId does not exist"
): JobException(message)

data class JobNotEnoughInputParametersException(
    val lostVariables: List<String>,
    override val message: String? = "Values for variables $lostVariables are not set"
): JobException(message)

data class JobTooMuchInputParametersException(
    override val message: String? = "Too much variables for system output job"
): JobException(message)

