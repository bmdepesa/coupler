/*
Load environment variables from passed-in parameters
List paramList
*/

def call(Map params) {
    def paramsConfigFile = "jobenv.config"
    def variables = params.variables.join("\n")
    node {
        dir('.') {
            writeFile(file: "pre_${paramsConfigFile}", text: variables)

            def paramsConfigFileContents = readFile("pre_${paramsConfigFile}")
            def paramsList = paramsConfigFileContents.split('\n')

            def newParams = []

            paramsList.each { param ->
                if (param != '' && param != null) {
                    newParams << "env.${param}"
                }
            }
            finalParamsList = newParams.join('\n')

            println(finalParamsList)
            writeFile(file: paramsConfigFile, text: finalParamsList)
        }

        load paramsConfigFile
    }
}