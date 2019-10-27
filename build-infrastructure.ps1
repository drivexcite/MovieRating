$resourceGroup = 'MovieRatingResourceGroup'
$containerRegistry = 'MovieRatingAppContainerRegistry'

az group create --name $resourceGroup --location westus
az acr create --resource-group $resourceGroup --name $containerRegistry --admin-enabled --sku Basic 
az configure --defaults acr=$containerRegistry
az aks create --resource-group $resourceGroup --name KubeCluster --node-count 8 --node-vm-size Standard_B2s --enable-addons monitoring --generate-ssh-keys

# Install Kubernetes CLI (kubectl)
az aks install-cli

# Create local configuration file to talk to the AKS Cluster
az aks get-credentials --resource-group $resourceGroup --name KubeCluster

# Kubernetes Dashboard & permissions
az aks browse --resource-group $resourceGroup --name KubeCluster
kubectl create clusterrolebinding kubernetes-dashboard --clusterrole=cluster-admin --serviceaccount=kube-system:kubernetes-dashboard

# Attach the ACR Instance to the Cluster
az aks update -n KubeCluster -g $resourceGroup --attach-acr movieratingappcontainerregistry.azurecr.io

# The metrics service is automatically installed in Kubnernetes 1.10 and above
az aks show --resource-group $resourceGroup --name KubeCluster --query kubernetesVersion