/**
 * Created by odolejsi on 11/9/17.
 */
def call(stageName, body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    stage(stageName){
        echo "Start executing stage of name: $stageName"
        body()
        echo "Done executing stage of name: $stageName"
    }
}
