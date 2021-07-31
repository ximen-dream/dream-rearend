def svn_auth = "9e1177d1-dd69-4eb8-abf4-88d1b215f66f"
def svn_url = "https://svn.datasmartlab.cn:8443/svn/moldingstar/trunk/cloudservice"
def harbor_url = "harbor.prdigital.cn"
def harbor_project_name = "moldingstar"
def harbor_auth = "211bf205-23e9-4910-af1e-da4604665b8c"
def tag = "v1"
// //def imageName = "${project_name}"
// def port = "${port}"
// def imageName = "${project_name}"
node {
	 stage('拉取代码d') { // for display purposes
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'fd7f73cb-eace-4cfb-a50c-feacab1eaa8a', url: 'https://github.com/ximen-dream/dream-rearend.git']]])
    }
}
