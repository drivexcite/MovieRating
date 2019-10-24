$resourceGroup = 'MovieRatingResourceGroup'
$containerRegistry = 'MovieRatingAppContainerRegistry'

az group create --name $resourceGroup --location westus
az acr create --resource-group $resourceGroup --name $containerRegistry --admin-enabled --sku Basic 
az configure --defaults acr=$containerRegistry