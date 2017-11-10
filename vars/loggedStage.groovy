/**
 * Created by odolejsi on 11/9/17.
 */
def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    stage{
        echo "Logging stuff: ${config.projectName}@${config.serverDomain}"
        body()
    }
}
