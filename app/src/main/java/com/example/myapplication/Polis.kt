package com.example.myapplication

import java.security.InvalidKeyException

fun stringToPolis(str: String):List<String>{

    val polis = mutableListOf<String>()
    val operations = Stack()
    var m = 0
    var k = 0

    val regex = Regex("""^\d]""")
    val ifReg = Regex("""^\?\d""")
    val ifElseReg = Regex("""^\?\d\d""")
    val whileReg = Regex("""^#\d""")

    val sravn = mapOf(
        "p" to 0,
        "#" to 0,
        "?" to 0,
        "{" to 0,
        "(" to 0,
        "[" to 0,
        "}" to 1,
        "," to 1,
        ";" to 1,
        "!" to 1,
        ")" to 1,
        "]" to 1,
        "|" to 3,
        "&" to 4,
        ">" to 5,
        "<" to 5,
        "~" to 5,
        "=" to 9,
        "+" to 6,
        "-" to 6,
        "*" to 7,
        "/" to 7,
        "%" to 7,
    )
    val stack = mapOf(
        "p" to 0,
        "#" to 0,
        "?" to 0,
        "{" to 0,
        "(" to 0,
        "[" to 0,
        ")" to 1,
        "}" to 1,
        "," to 1,
        ";" to 1,
        "!" to 1,
        "|" to 3,
        "&" to 4,
        ">" to 5,
        "<" to 5,
        "~" to 5,
        "]" to 10,
        "=" to 2,
        "+" to 6,
        "-" to 6,
        "*" to 7,
        "/" to 7,
        "%" to 7,
    )
    /*
    val string =
        "i=0;j=0;t=0;#(i<size-1){#(j<size-i-1){?(arr[j+1]>arr[j]){t=arr[j+1];arr[j+1]=arr[j];arr[j]=t;}j=j+1;}j=0;i=i+1;};i=size-1;#(i>0|i~0){p(arr[i]);i=i-1}"
    */
    val operators = setOf(
        "+",
        "-",
        "*",
        "/",
        "(",
        ")",
        "%",
        "=",
        "[",
        "]",
        "{",
        "}",
        ",",
        ";",
        "?",
        "!",
        "#",
        ">",
        "<",
        "&",
        "|",
        "~",
        "p"
    )
    val arr = splitString(str)

    var index = 0
    while (index < arr.size) {

        if (arr[index] !in operators) {
            polis.add(arr[index])
            index++
            continue
        }

        if (operations.isEmpty()) {
            if (arr[index] != ";") {
                if (arr[index] == "#") {
                    m++
                    polis.add("${m}:")
                }
                operations.push(arr[index])
            }
            index++
            continue
        }
        var op = operations.peek()

        if (arr[index] == "!") {
            m++
            op = operations.pop()
            operations.push(op + "$m")
            polis.add("${m}bp")
            polis.add("${op[1]}:")
            index++
            continue
        }

        if (arr[index] == "?") {
            operations.push(arr[index])
            index++
            continue
        }

        if (arr[index] == "p") {
            operations.push(arr[index])
            index++
            continue
        }

        if (arr[index] == "#") {
            m++
            operations.push(arr[index])
            polis.add("${m}:")
            index++
            continue
        }

        if (arr[index] == "(") {
            operations.push(arr[index])
            index++
            continue
        }

        if (arr[index] == "{") {
            operations.push(arr[index])
            index++
            continue
        }

        if (arr[index] == "[") {
            operations.push(arr[index])
            index++
            continue
        }


        if (arr[index] == ")" && op == "(") {

            operations.pop()

            if (operations.peek() == "p") {
                operations.pop()
                polis.add("${k + 1}out")
            }
            if (operations.peek() == "?") {
                m++
                operations.pop()
                operations.push("?${m}")
                polis.add("${m}?")
            } else if (operations.peek() == "#") {
                m++
                operations.pop()
                operations.push("#${m}")
                polis.add("${m}?")
            }
            index++
            continue
        }

        if (arr[index] == "}" && op == "{") {
            operations.pop()

            op = operations.peek()
            if (arr.size == index + 1 || arr[index + 1] != "!") {
                if (op.matches(ifReg)) {
                    operations.pop()
                    polis.add("${op[1]}:")
                } else if (op.matches(ifElseReg)) {
                    operations.pop()
                    polis.add("${op[2]}:")
                } else if (op.matches(whileReg)) {
                    operations.pop()
                    polis.add("${op[1] - 1}bp")
                    polis.add("${op[1]}:")
                }
            }
            index++
            continue
        }

        if (arr[index] == "]" && op == "[") {
            operations.pop()
            if (operations.peek().matches(regex)) {
                operations.push("${(operations.pop()[0].code + 1).toChar()}]")
            } else {
                operations.push("2]")
            }

            //polis.add("]")
            index++
            continue
        }

        if ((sravn.getOrDefault(arr[index], 1) > stack.getOrDefault(
                op,
                10
            )) || op.matches(ifReg) || op.matches(
                ifElseReg
            ) || op.matches(whileReg)
        ) {
            if (arr[index] == ",") k++
            else if (arr[index] != ";") operations.push(arr[index])
            else if (arr.size == index + 1 || arr[index + 1] != "!") {
                if (op.matches(ifReg)) {
                    operations.pop()
                    polis.add("${op[1]}:")
                } else if (op.matches(ifElseReg)) {
                    operations.pop()
                    polis.add("${op[2]}:")
                } else if (op.matches(whileReg)) {
                    operations.pop()
                    polis.add("${op[1] - 1}bp")
                    polis.add("${op[1]}:")
                }
            }
        } else {
            op = operations.pop()

            polis.add(op)
            index--
        }
        index++
    }

    if (!operations.isEmpty()) while (!operations.isEmpty()) {
        val op = operations.pop()
        if (op.matches(ifReg)) {
            polis.add("${op[1]}:")
        } else if (op.matches(ifElseReg)) {
            polis.add("${op[2]}:")
        } else if (op.matches(whileReg)) {
            polis.add("${op[1] - 1}bp")
            polis.add("${op[1]}:")
        } else polis.add(op)
    }

    return polis
}

fun splitString(str: String): List<String> {
    val list: MutableList<String> = mutableListOf()
    val operators = setOf(
        '+',
        '-',
        '*',
        '/',
        '(',
        ')',
        '%',
        '=',
        '[',
        ']',
        '?',
        '!',
        ';',
        ',',
        '{',
        '}',
        '#',
        '>',
        '<',
        '&',
        '|',
        '~',
        'p'
    )
    var s = ""
    for (ch in str) {
        if(ch==' ') continue
        if (ch in operators) {
            if (s != "") {
                list.add(s)
                s = ""
            }

            list.add(ch.toString())
        } else s += ch
    }
    if (s != "") list.add(s)
    return list.toList()
}


fun translation(
    polis: List<String>,
    startM: String? = null,
    out: MutableList<String> = mutableListOf(),
    variables: MutableMap<String, Any> = mutableMapOf(),
) {
    val stack = Stack()
    var startIndex = 0
    val indexReg = Regex("""^\d]""")
    val arrayReg = Regex("""^(\w+)\[(\d+)]""")
    val arrayOfArrayReg = Regex("""^(\w+)\[(\d+)]\[(\d+)]""")
    val ifReg = Regex("""^\d\?""")
    val mReg = Regex("""^\d:""")
    val bpReg = Regex("""^\dbp""")
    val outReg = Regex("""^\dout""")

    if (startM != null) while (startM != polis[startIndex]) startIndex++

    for (i in startIndex until polis.size) {
        if (polis[i].matches(ifReg)) {
            if (stack.peek() == "0") {
                translation(polis = polis, startM = "${polis[i][0]}:", out, variables)
                break
            }
            continue
        } else if (polis[i].matches(mReg)) {
            continue
        } else if (polis[i].matches(bpReg)) {
            translation(polis = polis, startM = "${polis[i][0]}:", out, variables)
            break
        } else if (polis[i].matches(outReg)) {
            var count = polis[i][0].code - 48
            while (count > 0) {
                var a = stack.pop()
                val arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                out.add(a)
                count--
            }
            continue
        } else if (polis[i].matches(indexReg)) {

            when (polis[i][0].code - 48) {
                2 -> {
                    var index = stack.pop()
                    if (variables.containsKey(index)) index = variables[index].toString()
                    //val arr = variables[stack.pop()] as List<String>
                    stack.push("${stack.pop()}[${index.toDouble().toInt()}]")
                }

                3 -> {
                    var secondIndex = stack.pop()
                    var firstIndex = stack.pop()
                    if (variables.containsKey(secondIndex)) secondIndex =
                        variables[secondIndex].toString()
                    if (variables.containsKey(firstIndex)) firstIndex =
                        variables[firstIndex].toString()
                    //val arr = variables[stack.pop()] as List<List<String>>
                    stack.push(
                        "${stack.pop()}[${
                            firstIndex.toDouble().toInt()
                        }][${secondIndex.toDouble().toInt()}]"
                    )
                }
            }

            continue
        }
        when (polis[i]) {
            "+" -> {
                var a = stack.pop()
                var b = stack.pop()

                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                stack.push((a.toDouble() + b.toDouble()).toString())
            }

            "=" -> {
                var a = stack.pop()
                val b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as MutableList<String>
                    arr[arrayPattern.groupValues[2].toInt()] = a
                } else {
                    if(variables.containsKey(b))
                    variables[b] = a
                    else throw Exception("variable doesn't exist")
                }
                stack.push(a)
            }

            "-" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                stack.push((b.toDouble() - a.toDouble()).toString())
            }

            "*" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                stack.push((b.toDouble() * a.toDouble()).toString())
            }

            "/" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                stack.push((b.toDouble() / a.toDouble()).toString())
            }

            "%" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                stack.push((b.toDouble() % a.toDouble()).toString())
            }

            ">" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                if (b.toDouble() > a.toDouble()) stack.push("1")
                else stack.push("0")
            }

            "<" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                if (b.toDouble() < a.toDouble()) stack.push("1")
                else stack.push("0")
            }

            "&" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                if (b.toDouble() != 0.0 && a.toDouble() != 0.0) stack.push("1")
                else stack.push("0")
            }

            "|" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                if (b.toDouble() != 0.0 || a.toDouble() != 0.0) stack.push("1")
                else stack.push("0")
            }

            "~" -> {
                var a = stack.pop()
                var b = stack.pop()
                var arrayPattern = arrayReg.find(a)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    a = arr[arrayPattern.groupValues[2].toInt()]
                }
                arrayPattern = arrayReg.find(b)
                if (arrayPattern != null) {
                    val arr = variables[arrayPattern.groupValues[1]] as List<String>
                    b = arr[arrayPattern.groupValues[2].toInt()]
                }
                if (variables.containsKey(a)) a = variables[a].toString()
                if (variables.containsKey(b)) b = variables[b].toString()
                if (b.toDouble() == a.toDouble()) stack.push("1")
                else stack.push("0")
            }

            else -> stack.push(polis[i])
        }
    }

}

class Stack {
    private val stackArray = mutableListOf<String>()


    fun push(value: String) {
        stackArray.add(value)
    }

    fun pop(): String {
        return if (stackArray.size > 0) {
            val a = stackArray[stackArray.size - 1]
            stackArray.removeAt(stackArray.size - 1)
            a
        } else "empty"
    }

    fun peek(): String {
        return if (stackArray.size > 0) stackArray[stackArray.size - 1]
        else "empty"
    }

    fun isEmpty(): Boolean {
        return stackArray.size == 0
    }

}

