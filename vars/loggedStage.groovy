/**
 * Created by odolejsi on 11/9/17.
 */
def call(name, body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    stage{
        echo "Start executing stage of name: $name"
        body()
        echo "Done executing stage of name: $name"
    }
}
