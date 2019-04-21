/*
Load environment variables from passed-in parameters
List paramList
*/

def call(Map params) {
    def paramsConfigFile = "jobenv.config"
    def variables = params.variables.join("\n")
    dir('.') {
        writeFile(file: "pre_${paramsConfigFile}", text: variables)

        def paramsConfigFileContents = readFile("pre_${paramsConfigFile}")
        def paramsList = paramsConfigFileContents.split('\n')

        def newParams = []

        paramsList.each { param ->
            if (param != '' && param != null) {
                if (!param.startsWith('#') && !param.startsWith("//")) {
                    newParams << "env.${param}"
                }
            }
        }

        finalParamsList = newParams.join('\n')

        writeFile(file: paramsConfigFile, text: finalParamsList)
    }

    load paramsConfigFile
}