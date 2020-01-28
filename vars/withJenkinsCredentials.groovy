def call(Closure body) {
    withCredentials([ string(credentialsId: 'AWS_ACCESS_KEY_ID', variable: 'AWS_ACCESS_KEY_ID'),
                      string(credentialsId: 'AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                      string(credentialsId: 'AWS_ACCESS_KEY_ID', variable: 'RANCHER_EKS_ACCESS_KEY'),
                      string(credentialsId: 'AWS_SECRET_ACCESS_KEY', variable: 'RANCHER_EKS_SECRET_KEY'),
                      string(credentialsId: 'DO_ACCESSKEY', variable: 'DO_ACCESSKEY'),
                      string(credentialsId: 'AWS_SSH_PEM_KEY', variable: 'AWS_SSH_PEM_KEY'),
                      string(credentialsId: 'RANCHER_SSH_KEY', variable: 'RANCHER_SSH_KEY'),
                      string(credentialsId: 'AZURE_SUBSCRIPTION_ID', variable: 'AZURE_SUBSCRIPTION_ID'),
                      string(credentialsId: 'AZURE_TENANT_ID', variable: 'AZURE_TENANT_ID'), 
                      string(credentialsId: 'AZURE_CLIENT_ID', variable: 'AZURE_CLIENT_ID'),
                      string(credentialsId: 'AZURE_CLIENT_SECRET', variable: 'AZURE_CLIENT_SECRET'),
                      string(credentialsId: 'AZURE_SUBSCRIPTION_ID', variable: 'RANCHER_AKS_SUBSCRIPTION_ID'),
                      string(credentialsId: 'AZURE_TENANT_ID', variable: 'RANCHER_AKS_TENANT_ID'), 
                      string(credentialsId: 'AZURE_CLIENT_ID', variable: 'RANCHER_AKS_CLIENT_ID'),
                      string(credentialsId: 'AZURE_CLIENT_SECRET', variable: 'RANCHER_AKS_SECRET_KEY'),
                      string(credentialsId: 'RANCHER_REGISTRY_USER_NAME', variable: 'RANCHER_REGISTRY_USER_NAME'),
                      string(credentialsId: 'RANCHER_REGISTRY_PASSWORD', variable: 'RANCHER_REGISTRY_PASSWORD'),
                      string(credentialsId: 'ADMIN_PASSWORD', variable: 'ADMIN_PASSWORD'),
                      string(credentialsId: 'USER_PASSWORD', variable: 'USER_PASSWORD'),
                      string(credentialsId: 'RANCHER_GKE_CREDENTIAL', variable: 'RANCHER_GKE_CREDENTIAL')]) {
        body()
    }
}