def git_auth = "fd7f73cb-eace-4cfb-a50c-feacab1eaa8a"
def git_url = "https://github.com/ximen-dream/dream-rearend.git"
def harbor_url = "10.8.0.6:20000"
def harbor_project_name = "dream"
def harbor_auth = "b73a1878-5837-453d-a699-a80027b8e7e5"
def tag = "v1"
def selectedProjects = "${project_name}".split(',')
//def port = "${port}"
//def imageName = "${project_name}"
node {
	 stage('拉取代码d') { // for display purposes
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
    }

	//TODO "mvn -f cloudservice-common(文件路径) clean install"
	stage("安装公共模块") {
        sh "mvn clean install"
    }
    stage("打包、上传镜像") {
        withCredentials([usernamePassword(credentialsId: "${harbor_auth}", passwordVariable: 'password', usernameVariable: 'username')]) {
            //登录
            sh "docker login -u ${username} -p ${password} ${harbor_url}"
        }
       for(int i=0;i<selectedProjects.size();i++){
            //取出每个项目的名称和端口
            def currentProject = selectedProjects[i];
            //项目名称
            def currentProjectName = currentProject.split('@')[0]
            //项目启动端口
            def currentProjectPort = currentProject.split('@')[1]
            sh "mvn -f ${currentProjectName} clean package dockerfile:build"
            echo "${currentProjectName}完成打包和镜像生产"

            // 上传镜像
            // 1.打标签
            sh "docker tag ${currentProjectName} ${harbor_url}/${harbor_project_name}/${currentProjectName}:${tag}"

            //上传镜像
            sh "docker push ${harbor_url}/${harbor_project_name}/${currentProjectName}:${tag}"

            echo "${currentProjectName}镜像上传成功"
        }
    }

    /**
	stage('编译打包工程,生成镜像') {
	    // 1.删除旧镜像
	    sh "docker rmi -f ${imageName}"
	    sh "docker rmi -f ${harbor_url}/${harbor_project_name}/${imageName}:${tag}"
	    // 2.打包新镜像
        sh "mvn -f cloudservice-portal/${project_name} clean package dockerfile:build"
    }

    stage('上传镜像') {
        //1.打标签
        sh "docker tag ${imageName} ${harbor_url}/${harbor_project_name}/${imageName}:${tag}"
        //2.上传镜像
        withCredentials([usernamePassword(credentialsId: "${harbor_auth}", passwordVariable: 'password', usernameVariable: 'username')]) {
             //登录
              sh "docker login -u ${username} -p ${password} ${harbor_url}"

              //上传镜像
              sh "docker push ${harbor_url}/${harbor_project_name}/${imageName}:${tag}"

              sh "echo 上传成功"
        }
    }

    stage('部署项目') {
         sshPublisher(publishers: [sshPublisherDesc(configName: 'master_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "/opt/jenkins_shell/deploy.sh $harbor_url $harbor_project_name $project_name $tag $port", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
         sh "echo 部署成功"
    }

    stage('发送邮件') {
        emailext(
          subject: '构建通知：${PROJECT_NAME} - Build # ${BUILD_NUMBER} - ${BUILD_STATUS}!',
          body: '${FILE,path="email.html"}',
          to: '782099197@qq.com'
        )
    }
    */
}
