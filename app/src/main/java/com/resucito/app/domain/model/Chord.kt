package com.resucito.app.domain.model

class Chord {


    companion object {
        private val chordRegex = Regex("""[ABCDEFG](maj7)?[#b]?(7)?(m)?([965])?""")

        fun getNotes(): Array<Note> {
            return Note.entries.toTypedArray()
        }

        fun getChordTypes(): Array<ChordType> {
            return ChordType.entries.toTypedArray()
        }


        fun validChord(chordStr: String): Boolean {
            return chordRegex.matches(chordStr)
        }

        fun validChordArray(chordArr: Array<String>): Boolean {
            return chordArr.all {
                println("VALID CHORD = $it")
                chordRegex.matches(it)
            }
        }

        fun chordFromString(chordArr: Array<String>) {
            for (carr in chordArr) {
                val chordStr = chordRegex.matches(carr)
                println("CHO = $carr is $chordStr")
            }

        }

        fun noteFromString(noteStr: String): Note? {
            val match = chordRegex.matchEntire(noteStr) ?: return null

            val noteMatch = match.groupValues[0]
            return try {
                Note.valueOf(noteMatch)
            } catch (ex: IllegalArgumentException) {
                null
            }

        }

    }


}

enum class Note {
    A,
    B,
    C,
    D,
    E,
    F,
    G
}

enum class ChordType(val value: String) {
    Major("maj"),
    Minor("m"),
    Seventh("7"),
    MajorSeventh("maj7"),
    MinorSeventh("m7"),

    Suspend("sus"),
    Suspend2("sus2"),
    Suspend4("sus4"),

    Diminished("dim"),
    Augmented("aug"),
    PowerChord("5"),
}