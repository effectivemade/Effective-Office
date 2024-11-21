package band.effective.office.tv.screen.duolingo.utils

import band.effective.office.tv.R


fun mapLanguagesToFlag(language: String):Int =
    when {
        language.contains("ar") -> R.drawable.ar
        language.contains("ca") -> R.drawable.ca
        language.contains("cs") -> R.drawable.cs
        language.contains("cy") -> R.drawable.cy
        language.contains("da") -> R.drawable.da
        language.contains("de") -> R.drawable.de
        language.contains("el") -> R.drawable.el
        language.contains("en") -> R.drawable.en
        language.contains("eo") -> R.drawable.eo
        language.contains("fi") -> R.drawable.fi
        language.contains("fr") -> R.drawable.fr
        language.contains("ga") -> R.drawable.ga
        language.contains("gd") -> R.drawable.gd
        language.contains("gn") -> R.drawable.gn
        language.contains("ha") -> R.drawable.ha
        language.contains("he") -> R.drawable.he
        language.contains("hi") -> R.drawable.hi
        language.contains("ht") -> R.drawable.ht
        language.contains("hu") -> R.drawable.hu
        language.contains("hv") -> R.drawable.hv
        language.contains("id") -> R.drawable.id
        language.contains("it") -> R.drawable.it
        language.contains("ja") -> R.drawable.ja
        language.contains("kl") -> R.drawable.kl
        language.contains("ko") -> R.drawable.ko
        language.contains("la") -> R.drawable.la
        language.contains("nl") -> R.drawable.nl
        language.contains("no") -> R.drawable.no_bo
        language.contains("nv") -> R.drawable.nv
        language.contains("pl") -> R.drawable.pl
        language.contains("pt") -> R.drawable.pt_copy
        language.contains("ro") -> R.drawable.ro
        language.contains("ru") -> R.drawable.ru
        language.contains("sv") -> R.drawable.sv
        language.contains("sw") -> R.drawable.sw
        language.contains("th") -> R.drawable.th
        language.contains("tr") -> R.drawable.tr
        language.contains("uk") -> R.drawable.uk
        language.contains("vi") -> R.drawable.vi
        language.contains("zh") -> R.drawable.zh
        else -> R.drawable.dualingo
    }