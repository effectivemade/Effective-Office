package band.effective.office.elevator.utils

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

fun getStringResource(
    resource: StringResource,
    context: Context
): String =  StringDesc.Resource(resource).toString(context = context)