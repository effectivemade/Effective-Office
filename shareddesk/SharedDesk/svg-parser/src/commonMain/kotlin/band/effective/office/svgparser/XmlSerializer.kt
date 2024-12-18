package band.effective.office.svgparser

import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig

internal val xmlSerializer = XML(config = XmlConfig(unknownChildHandler = { _, _, _, _, _ -> emptyList() }))