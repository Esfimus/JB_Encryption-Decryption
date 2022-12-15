package encryptdecrypt

import java.io.File

/**
 * Transforms all small letters in mirror sequence a->z, b->y, ...
 */
fun encryptionShift(text: String, key: Int): String {
    var encryptedText = ""
    for (ch in text) {
        encryptedText += if (
            ch.code in 97..122
        ) {
            val encChar = (((ch + key - 97).code % 26) + 97).toChar()
            encChar
        } else if (
            ch.code in 65..90
        ){
            val encChar = (((ch + key - 65).code % 26) + 65).toChar()
            encChar
        } else {
            ch
        }
    }
    return encryptedText
}

/**
 * Shifts all characters according to the given key value
 */
fun encryptionUnicode(text: String, key: Int): String {
    var encryptedText = ""
    for (ch in text) {
        val encChar = (((ch + key - 32).code % 95) + 32).toChar()
        encryptedText += encChar
    }
    return encryptedText
}

/**
 * Shifts all characters back according to the given key value
 */
fun decryptionShift(text: String, key: Int): String {
    var decryptedText = ""
    for (ch in text) {
        decryptedText += if (
            ch.code in 97..122
        ) {
            val encChar = (((ch + 26 - key - 97).code % 26) + 97).toChar()
            encChar
        } else if (
            ch.code in 65..90
        ){
            val encChar = (((ch + 26 - key - 65).code % 26) + 65).toChar()
            encChar
        } else {
            ch
        }
    }
    return decryptedText
}

/**
 * Shifts all characters back according to the given key value
 */
fun decryptionUnicode(text: String, key: Int): String {
    var decryptedText = ""
    for (ch in text) {
        val decChar = (((ch + 95 - key - 32).code % 95) + 32).toChar()
        decryptedText += decChar
    }
    return decryptedText
}

/**
 * Encrypts and decrypts any given text
 */
fun encryptionDecryption(
    mode: String,
    alg: String,
    key: Int,
    message: String,
    inFile: String,
    outFile: String
) {
    when (mode) {
        "enc" -> {
            // 8 combinations for encryption here: from/to file, from/to cmd, 2 encryption methods
            if (inFile.isNotEmpty()) {
                val textFromFile = File(inFile).readText()
                if (outFile.isNotEmpty()) {
                    if (alg == "shift") {
                        val textToFile = File(outFile).writeText(encryptionShift(textFromFile, key))
                    } else if (alg == "unicode") {
                        val textToFile = File(outFile).writeText(encryptionUnicode(textFromFile, key))
                    }
                } else {
                    if (alg == "shift") {
                        println(encryptionShift(textFromFile, key))
                    } else if (alg == "unicode") {
                        println(encryptionUnicode(textFromFile, key))
                    }
                }
            } else {
                if (outFile.isNotEmpty()) {
                    if (alg == "shift") {
                        val textToFile = File(outFile).writeText(encryptionShift(message, key))
                    } else if (alg == "unicode") {
                        val textToFile = File(outFile).writeText(encryptionUnicode(message, key))
                    }
                } else {
                    if (alg == "shift") {
                        println(encryptionShift(message, key))
                    } else if (alg == "unicode") {
                        println(encryptionUnicode(message, key))
                    }
                }
            }
        }
        "dec" -> {
            // 8 combinations for decryption here: from/to file, from/to cmd, 2 decryption methods
            if (inFile.isNotEmpty()) {
                val textFromFile = File(inFile).readText()
                if (outFile.isNotEmpty()) {
                    if (alg == "shift") {
                        val textToFile = File(outFile).writeText(decryptionShift(textFromFile, key))
                    } else if (alg == "unicode") {
                        val textToFile = File(outFile).writeText(decryptionUnicode(textFromFile, key))
                    }
                } else {
                    if (alg == "shift") {
                        println(decryptionShift(textFromFile, key))
                    } else if (alg == "unicode") {
                        println(decryptionUnicode(textFromFile, key))
                    }
                }
            } else {
                if (outFile.isNotEmpty()) {
                    if (alg == "shift") {
                        val textToFile = File(outFile).writeText(decryptionShift(message, key))
                    } else if (alg == "unicode") {
                        val textToFile = File(outFile).writeText(decryptionUnicode(message, key))
                    }
                } else {
                    if (alg == "shift") {
                        println(decryptionShift(message, key))
                    } else if (alg == "unicode") {
                        println(decryptionUnicode(message, key))
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    var modeCommand = "enc"
    var keyCommand = 0
    var dataCommand = ""
    var inCommand = ""
    var outCommand = ""
    var algCommand = "shift"
    if (args.contains("-mode")) {
        val modeIndex = args.indexOf("-mode") + 1
        if (args.size > modeIndex && args[modeIndex] == "dec") {
            modeCommand = "dec"
        }
    }
    if (args.contains("-key")) {
        val keyIndex = args.indexOf("-key") + 1
        if (args.size > keyIndex && args[keyIndex].isNotEmpty()) {
            keyCommand = try {
                args[keyIndex].toInt()
            } catch (e: Exception) {
                0
            }
        }
    }
    if (args.contains("-data")) {
        val dataIndex = args.indexOf("-data") + 1
        if (args.size > dataIndex &&
            args[dataIndex] != "-mode" &&
            args[dataIndex] != "-key" &&
            args[dataIndex] != "-in" &&
            args[dataIndex] != "-out"
        ) {
            dataCommand = args[dataIndex]
        }
    } else if (args.contains("-in")) {
        val inIndex = args.indexOf("-in") + 1
        if (args.size > inIndex) {
            inCommand = args[inIndex]
        }
    }
    if (args.contains("-out")) {
        val outIndex = args.indexOf("-out") + 1
        if (args.size > outIndex &&
            args[outIndex] != "-mode" &&
            args[outIndex] != "-key" &&
            args[outIndex] != "-in" &&
            args[outIndex] != "-data"
        ) {
            outCommand = args[outIndex]
        }
    }
    if (args.contains("-alg")) {
        val algIndex = args.indexOf("-alg") + 1
        if (args.size > algIndex && args[algIndex] == "unicode") {
            algCommand = "unicode"
        }
    }
    encryptionDecryption(modeCommand, algCommand, keyCommand, dataCommand, inCommand, outCommand)
}