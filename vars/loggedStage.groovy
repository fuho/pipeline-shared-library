/**
 * Created by odolejsi on 11/9/17.
 */
def log(env, int jobStart, int jobEnd, String reason, String stageName, int stageStart, int stageEnd) {
    jobStart = jobStart / 1000
    jobEnd = jobEnd / 1000
    stageStart = stageStart / 1000
    stageEnd = stageEnd / 1000

    sh """curl -X POST https://data.shared.handy-internal.com/logger/JenkinsJobLogger \
      -H 'Content-Type: application/json' \
      --data '{\
        "cloud": "kube-integration",\
        "job_name": "${env.JOB_NAME}",\
        "job_start_at": ${jobStart},\
        "job_end_at": ${jobEnd},\
        "job_run_time": ${jobEnd - jobStart},\
        "result": "${currentBuild.currentResult}",\
        "reason": "${reason}",\
        "job_number": "${currentBuild.number}",\
        "stage_start_at": ${stageStart},\
        "stage_end_at": ${stageEnd},\
        "stage_run_time": ${stageEnd - stageStart},\
        "stage_name": "${stageName}"\
      }'
    """
}

def call(String stageName, body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    stage(stageName) {
        startTime = new Date().getTime()
        try {
            body()
        } catch (all) {
            log(env, -1, -1, "ERROR:${all.toString()}", stageName, startTime as int, new Date.getTime() as int)
            throw all
        }
        log(env, -1, -1, 'N/A', stageName, startTime as int, new Date.getTime() as int)
    }
}
