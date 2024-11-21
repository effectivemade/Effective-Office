package band.effective.office.elevator.ui.models

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object PhoneMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }

    private fun maskFilter(text: AnnotatedString): TransformedText {

        // 000-000-00-00
        val trimmed = if (text.text.length >= 13) text.text.substring(0..12) else text.text
        var out = ""

        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 2 || i == 5 || i == 7) out += "-"
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 6) return offset + 1
                if (offset <= 8) return offset + 2
                if (offset <= 10) return offset + 3
                return 13
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset - 1
                if (offset <= 10) return offset - 2
                if (offset <= 13) return offset - 3
                return 10
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}