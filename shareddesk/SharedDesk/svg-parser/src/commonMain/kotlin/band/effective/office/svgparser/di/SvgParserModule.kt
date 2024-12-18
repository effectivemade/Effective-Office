package band.effective.office.svgparser.di

import band.effective.office.svgparser.CanvasDataParser
import band.effective.office.svgparser.CanvasDataParserImpl
import band.effective.office.svgparser.SvgParser
import band.effective.office.svgparser.SvgParserImpl
import org.koin.dsl.module

val svgParserModule = module {
    single<SvgParser> { SvgParserImpl() }
    single<CanvasDataParser> { CanvasDataParserImpl() }
}