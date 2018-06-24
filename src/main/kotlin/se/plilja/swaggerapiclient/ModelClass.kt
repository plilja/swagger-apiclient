package se.plilja.swaggerapiclient

class ModelClass(val className: String, val pkg: String) {
    val imports = HashSet<String>()
    val fields: MutableList<Pair<String, String>> = ArrayList();
    val getters = ArrayList<String>()
    val setters = ArrayList<String>()

    fun addField(name: String, type: Class<*>) {
        addImport(type)
        addGetter(name, type.simpleName!!)
        addSetter(name, type.simpleName!!)
        fields.add(Pair(name, type.simpleName!!))
    }

    fun addCollectionField(name: String, collectionType: Class<*>, collectionItemType: Class<*>) {
        val type = "${collectionType.simpleName}<${collectionItemType.simpleName}>"
        addImport(collectionType)
        addImport(collectionItemType)
        addGetter(name, type)
        addSetter(name, type)
        fields.add(Pair(name, type))
    }

    private fun addImport(type: Class<*>) {
        val alwaysAvailable = setOf<Class<*>>(
                Integer::class.java,
                String::class.java,
                Double::class.java,
                Long::class.java
        )
        if (!alwaysAvailable.contains(type)) {
            imports.add("import ${type.canonicalName};")
        }
    }

    fun toJavaCode(): String {
        return makePackage() +
                makeImports() +
                makeClassDeclaration() +
                indent(
                        makeFieldDeclarations() +
                                makeAccessors()
                ) +
                makeClassEnding()
    }

    private fun makePackage(): String {
        return "package ${pkg};\n\n"
    }

    private fun makeImports(): String {
        return imports.joinToString("\n") + "\n\n"
    }

    private fun makeClassDeclaration(): String {
        return "public final class ${className} {\n"
    }

    private fun makeFieldDeclarations(): String {
        return fields.map { f ->
            "private ${f.second} ${f.first};\n"
        }.joinToString("") + "\n"
    }

    private fun addGetter(name: String, type: String) {
        val s = "public ${type} get${name.capitalizeFirst()}() {\n" +
                "   return ${name};\n" +
                "}\n"
        getters.add(s)
    }

    private fun addSetter(name: String, type: String) {
        val s = "public ${className} set${name.capitalizeFirst()}(${type} name) {\n" +
                "   this.${name} = ${name};\n" +
                "   return this;\n" +
                "}\n"
        getters.add(s)
    }

    private fun makeAccessors(): String {
        return getters.union(setters).joinToString("\n") + "\n"
    }

    private fun makeClassEnding(): String {
        return "}\n"
    }

    private fun indent(s: String): String {
        return s.trim().split("\n")
                .map { if (it.trim().isEmpty()) "" else "   $it" }
                .joinToString("\n") + "\n"
    }
}

