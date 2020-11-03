package online.ruin_of_future.sub_substituter

/**
 * Not used actually :P
 * */


class AlignmentFinder {
    private val matchScore = 10L
    private val insertScore = 1L
    private val deleteScore = 1L
    private val misMatchScore = 3L

    companion object {
        private enum class PosType {
            Match,
            Insertion,
            Deletion,
            MisMatch
        }
    }

    private fun printlnWidth(s: String, t: String, printS: Boolean, printT: Boolean) {
        if (printS) {
            for (i in s.indices) {
                print(s[i])
                if (s[i].toInt() <= 128 && t[i].toInt() > 128) {
                    print(' ')
                }
            }
            print('\n')
        }
        if (printT) {
            for (i in t.indices) {
                print(t[i])
                if (t[i].toInt() <= 128 && s[i].toInt() > 128) {
                    print(' ')
                }
            }
            print('\n')
        }
    }

    fun align(s: String, t: String, printS: Boolean = true, printT: Boolean = true): Pair<List<Int>, List<Int>> {
        val dp = Array(s.length + 1) { LongArray(t.length + 1) { 0 } }
        val posType = Array(s.length + 1) { Array(t.length + 1) { PosType.Match } }

        for (i in 1..s.length) {
            for (j in 1..t.length) {
                val v1 = matchScore + dp[i - 1][j - 1]
                val v2 = deleteScore + dp[i - 1][j]
                val v3 = insertScore + dp[i][j - 1]
                val v4 = misMatchScore + dp[i - 1][j - 1]
                if (s[i - 1] == t[j - 1]) {
                    if (v1 >= v2 && v1 >= v3) {
                        dp[i][j] = v1
                        posType[i - 1][j - 1] = PosType.Match
                    } else if (v2 >= v1 && v2 >= v3) {
                        dp[i][j] = v2
                        posType[i - 1][j - 1] = PosType.Deletion
                    } else {
                        dp[i][j] = v3
                        posType[i - 1][j - 1] = PosType.Insertion
                    }
                } else {
                    if (v4 >= v2 && v4 >= v3) {
                        dp[i][j] = v4
                        posType[i - 1][j - 1] = PosType.MisMatch
                    } else if (v2 >= v3 && v2 >= v4) {
                        dp[i][j] = v2
                        posType[i - 1][j - 1] = PosType.Deletion
                    } else {
                        dp[i][j] = v3
                        posType[i - 1][j - 1] = PosType.Insertion
                    }
                }
            }
        }
        var i = s.length - 1
        var j = t.length - 1
        val sOut = StringBuilder()
        val tOut = StringBuilder()

        while (i >= 0 && j >= 0) {
            if (posType[i][j] == PosType.Match || posType[i][j] == PosType.MisMatch) {
                sOut.append(s[i])
                tOut.append(t[j])
                i -= 1
                j -= 1
            } else if (posType[i][j] == PosType.Insertion) {
                sOut.append(' ')
                tOut.append(t[j])
                j -= 1
            } else if (posType[i][j] == PosType.Deletion) {
                tOut.append(' ')
                sOut.append(s[i])
                i -= 1
            }
        }
        sOut.reverse()
        tOut.reverse()

//        i = s.length - 1
//        j = t.length - 1
//        val ret = StringBuilder()
//        while (i >= 0 && j >= 0) {
//            if (posType[i][j] == PosType.Match) {
//                ret.append(t[j])
//                i -= 1
//                j -= 1
//            } else if (posType[i][j] == PosType.MisMatch) {
//                ret.append(s[i])
//                i -= 1
//                j -= 1
//            } else if (posType[i][j] == PosType.Insertion) {
//                j -= 1
//            } else if (posType[i][j] == PosType.Deletion) {
//                ret.append(s[i])
//                i -= 1
//            }
//        }

        printlnWidth(sOut.toString(), tOut.toString(), printS, printT)

        i = s.length - 1
        j = t.length - 1
        val ret = Pair(mutableListOf<Int>(), mutableListOf<Int>())
        while (i >= 0 && j >= 0) {
            if (posType[i][j] == PosType.Match) {
                i -= 1
                j -= 1
            } else if (posType[i][j] == PosType.MisMatch) {
                ret.first.add(0, i)
                ret.second.add(0, j)
                i -= 1
                j -= 1
            } else if (posType[i][j] == PosType.Insertion) {
                j -= 1
            } else if (posType[i][j] == PosType.Deletion) {
                i -= 1
            }
        }
        return ret
    }
}